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
import lg.com.thirdlibstraining.utils.MyImageLoader;

/**
 * NewsLruCacheAdapter
 * Created by luo gang on 16-10-26.
 */
public class NewsMyImagerLoaderAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private List<NewsBean> mNewsBeenList;
    private ListView mListView;
    MyImageLoader myImageLoader;
    public static String[] urls; //用来保存当前获取到的所有图片的Url地址
    private LayoutInflater inflater;
    private int mStart;
    private int mEnd;
    private boolean mFirstIn;
    private boolean listFiling = false;

    public NewsMyImagerLoaderAdapter(Context context, List<NewsBean> list, ListView listView) {
        mNewsBeenList = list;
        mListView = listView;
        myImageLoader = MyImageLoader.build(context);
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
        //此处只从内存中拿数据,没有则不加载
        //holder.iv.setImageBitmap(myImageLoader.loadBitmapFromMemCache(mNewsBeenList.get(i).newsIconUrl));
        if(!listFiling){
            myImageLoader.bindBitmap(mNewsBeenList.get(i).newsIconUrl,
                    (ImageView) holder.iv.findViewWithTag(mNewsBeenList.get(i).newsIconUrl));
        }else {
            holder.iv.setImageBitmap(null);
        }
        return view;
    }

    //停止时加载数据,数据顺序:内存\本地\网络
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            //加载可见项
            for (int j = mStart; j < mEnd; j++) {
                myImageLoader.bindBitmap(mNewsBeenList.get(j).newsIconUrl, (ImageView) view.findViewWithTag(mNewsBeenList.get(j).newsIconUrl));
            }
            listFiling = false;
        } else if(scrollState == SCROLL_STATE_FLING){
            listFiling = true;
        } else if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
            listFiling = false;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        //如果是第一次进入 且可见item大于0 预加载
        /*if (mFirstIn && visibleItemCount > 0) {
            for (int j = mStart; j < mEnd; j++) {
                myImageLoader.bindBitmap(mNewsBeenList.get(j).newsIconUrl, (ImageView) absListView.findViewWithTag(mNewsBeenList.get(j).newsIconUrl));
            }
            mFirstIn = false;
        }*/
    }

    private class ViewHolder {
        ImageView iv;
        TextView tvTitle;
        TextView tvContent;
    }
}
