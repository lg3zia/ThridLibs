package lg.com.thirdlibstraining.activity_pic;

import android.widget.BaseAdapter;

import lg.com.thirdlibstraining.activity_cache.BaseCacheActivity;
import lg.com.thirdlibstraining.adapter.NewsMyImagerLoaderAdapter;

public class MyImageLoaderAty extends BaseCacheActivity {
    @Override
    protected BaseAdapter getAdapter() {
        return new NewsMyImagerLoaderAdapter(this, newsBeanList, lv);
    }
}
