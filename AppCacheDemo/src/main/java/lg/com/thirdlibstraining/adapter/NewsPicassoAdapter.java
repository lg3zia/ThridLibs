package lg.com.thirdlibstraining.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.util.List;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.bean.NewsBean;

/**
 * NewsLruCacheAdapter
 * Created by luo gang on 16-10-26.
 */
public class NewsPicassoAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context mContext;
    private List<NewsBean> mNewsBeenList;
    private static String[] urls; //用来保存当前获取到的所有图片的Url地址
    private LayoutInflater inflater;

    public NewsPicassoAdapter(Context context, List<NewsBean> list, ListView listView) {
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
        holder.iv.setTag(mNewsBeenList.get(i).newsIconUrl);
        //抛动作处理
        Picasso.with(mContext)
                .load(mNewsBeenList.get(i).newsIconUrl)//图片uri
                //.resize(10, 10)//重新设置大小
                //.noFade()//取消load渐入效果
                .placeholder(R.drawable.baby)//占位图片
                .error(R.drawable.baby)//错误图片
                .rotate(-10)//旋转
                //.onlyScaleDown()//小图片变成大图片时，会计算提高质量，设置屏蔽后，可加快load速度,
                // 大图转小无质量影响，建议设置。
                .tag("list")//tag，结合pauseTag()、resumeTag()、cancleTag()使用，控制图片加载过程
                //.centerCrop()//裁剪居中
                //.centerInside()//拉伸居中
                //.fit()//最合适的图片，包括内存占用
                .priority(Picasso.Priority.HIGH)//图片优先
                .transform(new BlurTransformation(mContext))
                //.memoryPolicy(MemoryPolicy.NO_CACHE)//跳过从内存缓存加载
                //.memoryPolicy(MemoryPolicy.NO_STORE)//不缓存图片到内存和本地
                //.networkPolicy(NetworkPolicy.NO_CACHE)//跳过本地缓存加载
                //.networkPolicy(NetworkPolicy.NO_STORE)//不缓存图片到本地
                //.fetch()获取图片到本地
                //.get()获取图片到本地，返回bitmap
                /*.into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        holder.iv.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });*/
                .into(holder.iv);
        return view;


    }

    //停止时加载数据,数据顺序:内存\本地\网络
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            Picasso.with(mContext)
                    .resumeTag("list");
        } else {
            Picasso.with(mContext)
                    .pauseTag("list");
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
