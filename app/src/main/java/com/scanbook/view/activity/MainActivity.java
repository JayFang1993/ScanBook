package com.scanbook.view.activity;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scanbook.R;
import com.scanbook.bean.Book;
import com.scanbook.view.CircularProgressView;
import com.scanbook.view.MaterialDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import mp.dt.c;

/**
 * <a href="http://fangjie.sinaapp.com">http://fangjie.sinaapp.com</a>
 * @version 1.0
 * @author JayFang
 * @describe “扫扫图书”应用程序主界面Activity
 */

public class MainActivity extends Activity {
    private TextView tx1;
    private RelativeLayout mRlBtn;
    private MaterialDialog mMaterialDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //友盟自动更新
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus,final UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        //UmengUpdateAgent.showUpdateDialog(MainActivity.this, updateInfo);
                        mMaterialDialog = new MaterialDialog(MainActivity.this);
                        mMaterialDialog.setTitle("扫扫图书"+updateInfo.version)
                                .setMessage(updateInfo.updateLog)
                                .setPositiveButton(
                                        "立即更新", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                UmengUpdateAgent.startDownload(MainActivity.this,updateInfo);
                                                mMaterialDialog.dismiss();
                                            }
                                        }
                                )
                                .setNegativeButton(
                                        "稍后更新", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mMaterialDialog.dismiss();
                                            }
                                        }
                                )
                                .setCanceledOnTouchOutside(false)
                                .show();
                        break;
                }
            }
        });
        UmengUpdateAgent.update(this);
        //广告sdk
        c.s(getApplicationContext(), "1054-3-7151");

        //设置初始界面的显示
        StringBuffer str=new StringBuffer();
        str.append("1.按下扫描按钮启动摄像头，扫描并获取书籍的条形码；").append("\n");
        str.append("2.查询书籍相关介绍信息；").append("\n");
        str.append("3.显示在界面上").append("\n");
        tx1=(TextView)findViewById(R.id.main_textview01);
        tx1.setText(str.toString());
        mRlBtn=(RelativeLayout)findViewById(R.id.rl_scan);

        
        mRlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        Intent intent=new Intent(MainActivity.this,CaptureActivity.class);
                        startActivityForResult(intent,100);
                    }
                }, 1000);
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(((requestCode==100)||(resultCode==Activity.RESULT_OK))&&data!=null){
            String isbn= data.getStringExtra("result");
            Intent intent=new Intent(MainActivity.this,BookViewActivity.class);
            intent.putExtra("isbn",isbn);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        Intent intent=null;
        switch (id){
            case R.id.actionbar_search:
                intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.actionbar_aboutme:
                intent=new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.actionbar_feedback:
                mMaterialDialog = new MaterialDialog(this);
                mMaterialDialog.setTitle("感谢建议")
                        .setMessage(
                                "你可以给我邮件 JayFang1993@gmail.com，或者微博：@方杰_Jay，感谢你的反馈 ^ ^"
                        )
                        .setPositiveButton(
                                "关注我", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog.dismiss();
                                    }
                                }
                        ).setNegativeButton("取消",new View.OnClickListener(){
                                @Override
                                public void onClick(View view) {
                                    mMaterialDialog.dismiss();
                                }
                            })
                        .setCanceledOnTouchOutside(false)
                        .show();
                break;
            case R.id.actionbar_score:
                Uri uri = Uri.parse("market://details?id="+"com.scanbook");
                intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
