package lg.com.thirdlibstraining.activity;

import android.os.Bundle;
import android.view.View;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.activity_cache.DiskLruCacheAty;
import lg.com.thirdlibstraining.activity_cache.LruCacheAty;
import lg.com.thirdlibstraining.activity_pic.GlideAty;
import lg.com.thirdlibstraining.activity_pic.ImageLoaderAty;
import lg.com.thirdlibstraining.activity_pic.MyImageLoaderAty;
import lg.com.thirdlibstraining.activity_pic.PicassoAty;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.btn_disk_lru_cache).setOnClickListener(this);
        this.findViewById(R.id.btn_lru_cache).setOnClickListener(this);
        this.findViewById(R.id.btn_image_loader).setOnClickListener(this);
        this.findViewById(R.id.btn_image_loader2).setOnClickListener(this);
        this.findViewById(R.id.btn_image_picasso).setOnClickListener(this);
        this.findViewById(R.id.btn_image_glide).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lru_cache:
                startAty(LruCacheAty.class);
                break;
            case R.id.btn_disk_lru_cache:
                startAty(DiskLruCacheAty.class);
                break;
            case R.id.btn_image_loader:
                startAty(MyImageLoaderAty.class);
                break;
            case R.id.btn_image_loader2:
                startAty(ImageLoaderAty.class);
                break;
            case R.id.btn_image_picasso:
                startAty(PicassoAty.class);
                break;
            case R.id.btn_image_glide:
                startAty(GlideAty.class);
                break;
        }
    }

}
