package lg.com.thirdlibstraining.activity_cache;

import android.widget.BaseAdapter;

import lg.com.thirdlibstraining.adapter.NewsDiskLruAdapter;

public class DiskLruCacheAty extends BaseCacheActivity {

    @Override
    protected BaseAdapter getAdapter() {
        return new NewsDiskLruAdapter(this, newsBeanList, lv);
    }
}
