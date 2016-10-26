package lg.com.thirdlibstraining.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.adapter.NewsAdapter;
import lg.com.thirdlibstraining.bean.NewsBean;
import lg.com.thirdlibstraining.utils.GetJsonUtil;

public class LruCacheAty extends Activity {
    ListView mLv;
    private String url = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache_aty);
        mLv = (ListView) findViewById(R.id.lv_rlu);
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
            NewsAdapter adapter = new NewsAdapter(LruCacheAty.this, newsBeen, mLv);
            mLv.setAdapter(adapter);
        }
    }
}
