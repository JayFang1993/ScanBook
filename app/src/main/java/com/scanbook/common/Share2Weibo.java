package com.scanbook.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scanbook.R;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.platformtools.Util;

import java.io.File;

/**
 * Created by Jay on 15/2/4.
 */
public class Share2Weibo extends Activity implements IWeiboHandler.Response {

    private IWeiboShareAPI mWeiboShareAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name=getIntent().getStringExtra("name");
        String url=getIntent().getStringExtra("url");
        String picurl=getIntent().getStringExtra("picurl");
        String score=getIntent().getStringExtra("score");

        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constant.APP_KEY);
        mWeiboShareAPI.registerApp();

        sendMultiMessage(score,name,url,picurl);
    }

    private TextObject getTextObj(String score) {
        TextObject textObject = new TextObject();
        textObject.text ="我在@扫扫图书 发现了一本不错的书，豆瓣评分:"+score+"分";
        return textObject;
    }


    private WebpageObject getWebpageObj(String name,String url) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title =name;
        mediaObject.description = name;
        mediaObject.setThumbImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        if(url.equals(""))
            url="http://book.douban.com";
        mediaObject.actionUrl =url;
        mediaObject.defaultText = "";
        return mediaObject;
    }

    private ImageObject getImageObj(String picurl) {
        ImageObject imageObject = new ImageObject();

        String tempPath = FileUtils.getCachePath() + "/temp.jpg";
        File f= new File(tempPath);
        if(f.exists()){
            Bitmap thumb=BitmapFactory.decodeFile(tempPath);
            imageObject.setImageObject(thumb);
        }else{
            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            imageObject.setImageObject(thumb);
        }
        return imageObject;
    }

    private void sendMultiMessage(String score,String name,String url,String picurl) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        weiboMessage.textObject = getTextObj(score);
        weiboMessage.imageObject=getImageObj(picurl);
        weiboMessage.mediaObject = getWebpageObj(name,url);

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        mWeiboShareAPI.sendRequest(request);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
                finish();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
                finish();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this,"分享失败",
                        Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

}
