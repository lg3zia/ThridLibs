package lg.com.thirdlibstraining.activity;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.squareup.picasso.Picasso;

/**
 * MyApplication
 * Created by luo gang on 16-10-25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initPicasso();
        initGlide();
    }

    private void initGlide() {
    }

    private void initPicasso() {
        //蓝色 - 从内存中获取,是最佳性能展示
        //绿色 - 从本地获取,性能一般
        //红色 - 从网络加载,性能最差
        Picasso.with(getApplicationContext())
                .setIndicatorsEnabled(true);
        Picasso.with(getApplicationContext()).setLoggingEnabled(true);//显示加载log
        //自定义picasso，设置后，调用Picasso.with(mContext)则返回自己的picasso
        //Picasso.Builder picassoBuilder = new Picasso.Builder(getApplicationContext());
        //picassoBuilder.downloader(new OkHttpDownloader(getApplicationContext()));
        //Picasso.setSingletonInstance(picassoBuilder.build());
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                //.taskExecutor()
                //.taskExecutorForCachedImages(...)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                //.diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
                //.imageDecoder(new BaseImageDecoder()) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
}
