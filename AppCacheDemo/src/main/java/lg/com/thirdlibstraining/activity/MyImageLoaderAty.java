package lg.com.thirdlibstraining.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.adapter.NewsDiskLruAdapter;
import lg.com.thirdlibstraining.adapter.NewsMyImagerLoaderAdapter;
import lg.com.thirdlibstraining.bean.NewsBean;
import lg.com.thirdlibstraining.utils.GetJsonUtil;
import lg.com.thirdlibstraining.utils.MyImageLoader;

public class MyImageLoaderAty extends Activity {
    private MyImageLoader myImageLoader;
    ListView mLoaderLv;
    private String url = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_image_loader_aty);
        mLoaderLv = (ListView) findViewById(R.id.lv_my_image_loader);
        new getJsonAsyncTask().execute(url);
    }

    private class getJsonAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(String... strings) {

            return GetJsonUtil.getJson(strings[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeen) {
            super.onPostExecute(newsBeen);
            NewsMyImagerLoaderAdapter adapter = new NewsMyImagerLoaderAdapter(MyImageLoaderAty.this, newsBeen, mLoaderLv);
            mLoaderLv.setAdapter(adapter);
        }
    }
}
