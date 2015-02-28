package com.scanbook.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scanbook.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Jay on 15/2/4.
 */
public class Share2Weixin extends Activity{

    private IWXAPI WXAPI;

    private static final int TIMELINE=1;
    private static final int FRIEND=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String name=getIntent().getStringExtra("name");
        final String url=getIntent().getStringExtra("url");
        final String score=getIntent().getStringExtra("score");
        final int type=getIntent().getIntExtra("type",1);
        share(Share2Weixin.this, type, name, score, url);
    }

    public void share(Context context,int type, String name,String score,String url){
        WXAPI = WXAPIFactory.createWXAPI(context, Constant.AppID);
        WXAPI.registerApp(Constant.AppID);

        WXWebpageObject webpage = new WXWebpageObject();
        if(url.equals(""))
            url="http://book.douban.com";
        webpage.webpageUrl =url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if(type==TIMELINE){
            msg.title ="我在@扫扫图书 发现了一本不错的书，《"+name+"》豆瓣评分:"+score+"分";
            msg.description =name;
        }else{
            msg.title =name;
            msg.description ="我在@扫扫图书 发现了一本不错的书，《"+name+"》豆瓣评分:"+score+"分";
        }
        String tempPath = FileUtils.getCachePath() + "/temp.jpg";
        File f= new File(tempPath);
        if(f.exists()){
            Bitmap thumb=BitmapFactory.decodeFile(tempPath);
            msg.thumbData = Util.bmpToByteArray(thumb, true);
        }else{
            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            msg.thumbData = Util.bmpToByteArray(thumb, true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction ="";
        req.message = msg;
        req.scene = type== TIMELINE? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        WXAPI.sendReq(req);
        finish();
    }

}
