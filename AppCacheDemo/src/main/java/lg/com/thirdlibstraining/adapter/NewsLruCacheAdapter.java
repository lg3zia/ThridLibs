package lg.com.thirdlibstraining.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.bean.NewsBean;
import lg.com.thirdlibstraining.utils.LruCacheUtil;

/**
 * NewsLruCacheAdapter
 * Created by luo gang on 16-10-26.
 */
public class NewsLruCacheAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private List<NewsBean> mNewsBeenList;
    private ListView mListView;
    private LruCacheUtil mLruCacheUtil;
    public static String[] urls; //用来保存当前获取到的所有图片的Url地址
    private LayoutInflater inflater;
    private int mStart;
    private int mEnd;
    private boolean mFirstIn;

    public NewsLruCacheAdapter(Context context, List<NewsBean> list, ListView listView) {
        mNewsBeenList = list;
        mListView = listView;
        mLruCacheUtil = new LruCacheUtil(listView);
        //存入url地址
        urls = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            urls[i] = list.get(i).newsIconUrl;
        }
        mFirstIn = true;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //设置滑动监听
        mListView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mNewsBeenList.size();
    }

    @Override
    public Object getItem(int i) {
        return mNewsBeenList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_news_list_items, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) view.findViewById(R.id.iv_new_icon);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.tvContent = (TextView) view.findViewById(R.id.tv_sub_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(mNewsBeenList.get(i).newsTitle);
        holder.tvContent.setText(mNewsBeenList.get(i).newsContent);
        //iv设置tag,方便后面异步加载时得到tag比对,不一致时不设置,否在会出现图片加载错乱.
        holder.iv.setTag(mNewsBeenList.get(i).newsIconUrl);
        //普通加载
        //new ThreadUtil().showImageByThread(holder.iv, mNewsBeenList.get(i).newsIconUrl);
        //LruCache加载
        //此处先调用,覆盖contentView中的图片
        mLruCacheUtil.showImageByAsyncTask(holder.iv, mNewsBeenList.get(i).newsIconUrl);
        return view;
    }

    /**
     *
     * @param view
     * @param scrollState :滑动开始\滑动结束
     */
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            //加载可见项
            mLruCacheUtil.loadImages(mStart, mEnd);
        } else {
            //停止加载任务
            mLruCacheUtil.cancelAllTask();
        }
    }

    /**
     *
     * @param absListView
     * @param firstVisibleItem:第一个可见的view
     * @param visibleItemCount:可见view的数量
     * @param totalItemCount:listview全部值
     */
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        //如果是第一次进入 且可见item大于0 预加载
        if (mFirstIn && visibleItemCount > 0) {
            mLruCacheUtil.loadImages(mStart, mEnd);
            mFirstIn = false;
        }
    }

    private class ViewHolder {
        ImageView iv;
        TextView tvTitle;
        TextView tvContent;
    }
}
