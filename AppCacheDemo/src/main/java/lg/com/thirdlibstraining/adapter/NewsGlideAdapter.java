package lg.com.thirdlibstraining.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Transformation;

import java.util.List;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.bean.NewsBean;

/**
 * NewsLruCacheAdapter
 * Created by luo gang on 16-10-26.
 */
public class NewsGlideAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context mContext;
    private List<NewsBean> mNewsBeenList;
    private static String[] urls; //用来保存当前获取到的所有图片的Url地址
    private LayoutInflater inflater;

    public NewsGlideAdapter(Context context, List<NewsBean> list, ListView listView) {
        mContext = context;
        mNewsBeenList = list;
        //存入url地址
        urls = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            urls[i] = list.get(i).newsIconUrl;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView.setOnScrollListener(this);
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
        final ViewHolder holder;
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

        Glide.with(mContext)
                .load(mNewsBeenList.get(i).newsIconUrl)
                //.asBitmap()
                //.asGif()
                .skipMemoryCache(true)
                .priority(Priority.NORMAL)
                .thumbnail(0.1f)
                .centerCrop()
                .error(R.drawable.baby)
                .placeholder(R.drawable.baby)
                .crossFade()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.iv);

        return view;
    }

    //停止时加载数据,数据顺序:内存\本地\网络
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            Glide.with(mContext)
                    .resumeRequests();
        } else {
            Glide.with(mContext)
                    .pauseRequests();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private class ViewHolder {
        ImageView iv;
        TextView tvTitle;
        TextView tvContent;
    }

    class BlurTransformation implements Transformation {

        RenderScript rs;

        public BlurTransformation(Context context) {
            super();
            rs = RenderScript.create(context);
        }

        @Override
        public Bitmap transform(Bitmap bitmap) {
            // 创建一个Bitmap作为最后处理的效果Bitmap
            Bitmap blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            // 分配内存
            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(rs, input.getType());

            // 根据我们想使用的配置加载一个实例
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);

            // 设置模糊半径
            script.setRadius(10);

            //开始操作
            script.forEach(output);

            // 将结果copy到blurredBitmap中
            output.copyTo(blurredBitmap);

            //释放资源
            bitmap.recycle();

            return blurredBitmap;
        }

        @Override
        public String key() {
            return "blur";
        }
    }
}
