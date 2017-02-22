package lg.com.thirdlibstraining.activity_pic;

import android.widget.BaseAdapter;

import lg.com.thirdlibstraining.activity_cache.BaseCacheActivity;
import lg.com.thirdlibstraining.adapter.NewsPicassoAdapter;

public class PicassoAty extends BaseCacheActivity {
    @Override
    protected BaseAdapter getAdapter() {
        return new NewsPicassoAdapter(this, newsBeanList, lv);
    }
}
