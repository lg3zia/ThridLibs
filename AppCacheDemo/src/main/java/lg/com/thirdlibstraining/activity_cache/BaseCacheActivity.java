package lg.com.thirdlibstraining.activity_cache;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lg.com.thirdlibstraining.R;
import lg.com.thirdlibstraining.activity.BaseActivity;
import lg.com.thirdlibstraining.bean.NewsBean;
import lg.com.thirdlibstraining.utils.GetJsonUtil;

/**
 * BaseActivity
 * Created by luo gang on 16-10-25.
 */
public abstract class BaseCacheActivity extends BaseActivity {
    protected ListView lv;
    protected String url = "http://www.imooc.com/api/teacher?type=4&num=30";
    protected List<NewsBean> newsBeanList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_base_aty);
        ((TextView) findViewById(R.id.tv_title)).setText(getClass().getSimpleName());
        lv = (ListView) findViewById(R.id.lv);
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
            newsBeanList = newsBeen;
            BaseAdapter adapter = getAdapter();
            lv.setAdapter(adapter);
        }
    }

    protected abstract BaseAdapter getAdapter();
}
