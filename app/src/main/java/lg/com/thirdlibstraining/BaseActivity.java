package lg.com.thirdlibstraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * BaseActivity
 * Created by luo gang on 16-10-25.
 */
public class BaseActivity extends AppCompatActivity {
    public void startAty(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
