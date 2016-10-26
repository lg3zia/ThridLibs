package lg.com.thirdlibstraining.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.bean.NewsBean;
import lg.com.thirdlibstraining.utils.ThreadUtil;

/**
 * NewsAdapter
 * Created by luo gang on 16-10-26.
 */
public class NewsAdapter extends BaseAdapter {
    private List<NewsBean> mNewsBeenList;
    public static String[] urls; //用来保存当前获取到的所有图片的Url地址
    private LayoutInflater inflater;

    public NewsAdapter(Context context, List<NewsBean> list) {
        mNewsBeenList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        new ThreadUtil().showImageByThread(holder.iv, mNewsBeenList.get(i).newsIconUrl);
        return view;
    }

    private class ViewHolder {
        ImageView iv;
        TextView tvTitle;
        TextView tvContent;
    }
}
