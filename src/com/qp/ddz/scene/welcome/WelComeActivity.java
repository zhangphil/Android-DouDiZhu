package com.qp.ddz.scene.welcome;

import com.example.ddzserver.DDZBotService;
import com.qp.ddz.GameActivity;
import com.qp.ddz.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class WelComeActivity extends Activity {

    public static String TAG = "WelComeActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome);
     
		
        GameActivity.getInstance().onStartConfigAsyncTask((TextView) findViewById(R.id.welcome_loading_info),
                (ProgressBar) findViewById(R.id.welcome_loading));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
