package com.scanbook.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scanbook.R;
import com.scanbook.view.MaterialDialog;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * Created by FangJie on 15/2/9.
 */
public class AboutActivity extends Activity {

    private RelativeLayout mRlAuthor,mRlBlog,mRlPro,mRlUpdate;
    private MaterialDialog mMaterialDialog;
    private TextView mTvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mRlAuthor=(RelativeLayout)findViewById(R.id.rl_about_author);
        mRlBlog=(RelativeLayout)findViewById(R.id.rl_about_blog);
        mRlPro=(RelativeLayout)findViewById(R.id.rl_about_pro);
        mRlUpdate=(RelativeLayout)findViewById(R.id.rl_about_update);
        mTvVersion=(TextView)findViewById(R.id.tv_about_version);
        mRlAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialDialog = new MaterialDialog(AboutActivity.this);
                mMaterialDialog.setTitle("关于作者")
                        .setMessage(
                                "花名：Jay.Fang\n" +
                                        "博客：http://fangjie.info\n" +
                                        "Weibo：@方杰_Jay\n" +
                                        "Email：JayFang1993#gmail\n"
                        )
                        .setPositiveButton(
                                "去TA博客", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog.dismiss();
                                        Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse("http://fangjie.info");
                                        intent.setData(content_url);
                                        startActivity(intent);
                                    }
                                }
                        )
                        .setNegativeButton(
                                "算了", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog.dismiss();
                                    }
                                }
                        )
                        .setCanceledOnTouchOutside(false)
                        .show();

            }
        });
        mRlBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://fangjie.info");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        mRlPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMaterialDialog = new MaterialDialog(AboutActivity.this);
                mMaterialDialog.setTitle("GitHub")
                        .setMessage(
                                " 别忘了star项目哦 ^ ^"
                        )
                        .setPositiveButton(
                                "去GitHub", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog.dismiss();
                                        Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse("http://github.com/JayFang1993/ScanBook");
                                        intent.setData(content_url);
                                        startActivity(intent);
                                    }
                                }
                        )
                        .setNegativeButton(
                                "算了", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog.dismiss();
                                    }
                                }
                        )
                        .setCanceledOnTouchOutside(false)
                        .show();
            }
        });
        mRlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus,final UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                mMaterialDialog = new MaterialDialog(AboutActivity.this);
                                mMaterialDialog.setTitle("扫扫图书"+updateInfo.version)
                                        .setMessage(updateInfo.updateLog)
                                        .setPositiveButton(
                                                "立即更新", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        UmengUpdateAgent.startDownload(AboutActivity.this,updateInfo);
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
                            case UpdateStatus.No:
                                mTvVersion.setText("已经是最新版本");
                                break;
                        }
                    }
                });
                UmengUpdateAgent.update(AboutActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActionBar().setHomeButtonEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
