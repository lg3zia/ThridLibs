package lg.com.thirdlibstraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * BaseActivity
 * Created by luo gang on 16-10-25.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void startAty(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
