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

import java.io.IOException;
import java.util.List;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.bean.NewsBean;
import lg.com.thirdlibstraining.utils.DiskCacheUtil;
import lg.com.thirdlibstraining.utils.LruCacheUtil;

/**
 * NewsAdapter
 * Created by luo gang on 16-10-26.
 */
public class NewsAdapter2 extends BaseAdapter implements AbsListView.OnScrollListener {
    private List<NewsBean> mNewsBeenList;
    private ListView mListView;
    private LruCacheUtil mLruCacheUtil;
    private DiskCacheUtil mDiskCacheUtils;
    public static String[] urls; //用来保存当前获取到的所有图片的Url地址
    private LayoutInflater inflater;
    private int mStart;
    private int mEnd;
    private boolean mFirstIn;

    public NewsAdapter2(Context context, List<NewsBean> list, ListView listView) {
        mNewsBeenList = list;
        mListView = listView;
        mLruCacheUtil = new LruCacheUtil(listView);
        mDiskCacheUtils = new DiskCacheUtil(context, listView);
        //存入url地址
        urls = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            urls[i] = list.get(i).newsIconUrl;
        }
        mFirstIn = true;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        holder.iv.setTag(mNewsBeenList.get(i).newsIconUrl);
        //普通加载
        //new ThreadUtil().showImageByThread(holder.iv, mNewsBeenList.get(i).newsIconUrl);
        //LruCache加载
        //mLruCacheUtil.showImageByAsyncTask(holder.iv, mNewsBeenList.get(i).newsIconUrl);
        //
        try {
            mDiskCacheUtils.showImageByAsyncTask(holder.iv, mNewsBeenList.get(i).newsIconUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            //加载可见项
            try {
                mDiskCacheUtils.loadImages(mStart, mEnd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //停止加载任务
            mDiskCacheUtils.cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        //如果是第一次进入 且可见item大于0 预加载
        if (mFirstIn && visibleItemCount > 0) {
            try {
                mDiskCacheUtils.loadImages(mStart, mEnd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFirstIn = false;
        }
    }

    private class ViewHolder {
        ImageView iv;
        TextView tvTitle;
        TextView tvContent;
    }
}
