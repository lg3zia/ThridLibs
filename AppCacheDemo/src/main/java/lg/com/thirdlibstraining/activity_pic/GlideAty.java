package lg.com.thirdlibstraining.activity_pic;

import android.widget.BaseAdapter;

import lg.com.thirdlibstraining.activity_cache.BaseCacheActivity;
import lg.com.thirdlibstraining.adapter.NewsGlideAdapter;
import lg.com.thirdlibstraining.adapter.NewsPicassoAdapter;

public class GlideAty extends BaseCacheActivity {
    @Override
    protected BaseAdapter getAdapter() {
        return new NewsGlideAdapter(this, newsBeanList, lv);
    }
}
