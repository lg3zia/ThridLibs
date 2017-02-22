package lg.com.thirdlibstraining.activity_pic;

import android.widget.BaseAdapter;

import lg.com.thirdlibstraining.activity_cache.BaseCacheActivity;
import lg.com.thirdlibstraining.adapter.NewsImageLoaderAdapter;

public class ImageLoaderAty extends BaseCacheActivity {
    @Override
    protected BaseAdapter getAdapter() {
        return new NewsImageLoaderAdapter(this, newsBeanList, lv);
    }
}
