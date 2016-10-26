package lg.com.thirdlibstraining.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import lg.com.thirdlibstraining.R;

public class DiskLruCacheAty extends BaseActivity {

    LruCache<String, Bitmap> mMemoryCache;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_lru_cache_aty);
        initView();
        initData();

    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.iv1);
    }

    private void initData() {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        mMemoryCache = new LruCache<String, Bitmap>(memoryCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boy);
        mMemoryCache.put("iv_boy", bitmap);

        imageView.setImageBitmap(mMemoryCache.get("iv_boy"));

    }


}
