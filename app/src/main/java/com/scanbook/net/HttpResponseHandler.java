package com.scanbook.net;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jim on 2015/2/3.
 */
public abstract class HttpResponseHandler extends JsonHttpResponseHandler {

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        Log.i("fangjie", response.toString());
        jsonSuccess(response);
    }
    public abstract void jsonSuccess(JSONObject resp);
    public abstract void jsonFail(JSONObject resp);
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        Log.i("fangjie", "statusCode:"+statusCode);
        jsonFail(errorResponse);
    }
}
