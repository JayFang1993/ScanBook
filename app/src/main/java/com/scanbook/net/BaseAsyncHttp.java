package com.scanbook.net;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.ResponseHandler;

/**
 * Created by Jim on 2015/2/3.
 */
public class BaseAsyncHttp extends AsyncHttpClient{

    public static final String HOST="https://api.douban.com";

    public static void postReq(String host,String url,RequestParams params,JsonHttpResponseHandler hander){
        new AsyncHttpClient().post(host+url,params,hander);
    }
    public static void postReq(String url,RequestParams params,JsonHttpResponseHandler hander){
        Log.i("fangjie",HOST+url);
        new AsyncHttpClient().post(HOST+url,params,hander);
    }
    public static void getReq(String host,String url,RequestParams params,JsonHttpResponseHandler hander){
        new AsyncHttpClient().get(host+url,params,hander);
    }
    public static void getReq(String url,RequestParams params,JsonHttpResponseHandler hander){
        Log.i("fangjie",HOST+url);
        new AsyncHttpClient().get(HOST+url,params,hander);
    }

    public static void downLoadFile(String url,FileAsyncHttpResponseHandler handler){
        new AsyncHttpClient().get(url,handler);
    }

}
