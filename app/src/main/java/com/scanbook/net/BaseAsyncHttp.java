package com.scanbook.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.scanbook.common.FileUtils;

import org.apache.http.Header;
import org.apache.http.client.ResponseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Jim on 2015/2/3.
 */
public class BaseAsyncHttp extends AsyncHttpClient {

    public static final String HOST = "https://api.douban.com";

    public static void postReq(String host, String url, RequestParams params, JsonHttpResponseHandler hander) {
        new AsyncHttpClient().post(host + url, params, hander);
    }

    public static void postReq(String url, RequestParams params, JsonHttpResponseHandler hander) {
        Log.i("fangjie", HOST + url);
        new AsyncHttpClient().post(HOST + url, params, hander);
    }

    public static void getReq(String host, String url, RequestParams params, JsonHttpResponseHandler hander) {
        new AsyncHttpClient().get(host + url, params, hander);
    }

    public static void getReq(String url, RequestParams params, JsonHttpResponseHandler hander) {
        Log.i("fangjie", HOST + url);
        new AsyncHttpClient().get(HOST + url, params, hander);
    }

    public static void downloadFile(String url){
        AsyncHttpClient client = new AsyncHttpClient();
        String[] allowedContentTypes = new String[]{"image/png", "image/jpeg"};
        client.get(url, new BinaryHttpResponseHandler(allowedContentTypes) {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] binaryData) {
                String tempPath = FileUtils.getCachePath() + "/temp.jpg";
                Bitmap bmp = BitmapFactory.decodeByteArray(binaryData, 0, binaryData.length);
                File file = new File(tempPath);
                // 压缩格式
                Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                // 压缩比例
                int quality = 100;
                try {
                    if (file.exists())
                        file.delete();
                    file.createNewFile();
                    OutputStream stream = new FileOutputStream(file);
                    bmp.compress(format, quality, stream);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] binaryData, Throwable error) {
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }

        });

    }
}
