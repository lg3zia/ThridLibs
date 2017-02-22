package lg.com.thirdlibstraining.activity_cache;

import android.widget.BaseAdapter;

import lg.com.thirdlibstraining.adapter.NewsLruCacheAdapter;

public class LruCacheAty extends BaseCacheActivity {

    @Override
    protected BaseAdapter getAdapter() {
        return new NewsLruCacheAdapter(this, newsBeanList, lv);
    }
}
