package com.scanbook.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.scanbook.GlApplication;
import com.scanbook.R;

/**
 * Created by Jay on 15/2/27.
 */



public class SplashActivity extends Activity{
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            if(GlApplication.isFirst()){
                intent.setClass(getApplication(), GuidePageActivity.class);
            }else{
                intent.setClass(getApplication(), MainActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawableResource(R.drawable.splash);
        mMainHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onBackPressed() {

    }

}
