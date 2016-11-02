package lg.com.thirdlibstraining.activity;

import android.os.Bundle;
import android.view.View;

import lg.com.thirdlibstraining.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.btn_disk_lru_cache).setOnClickListener(this);
        this.findViewById(R.id.btn_lru_cache).setOnClickListener(this);
        this.findViewById(R.id.btn_image_loader).setOnClickListener(this);
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
        }
    }

}
