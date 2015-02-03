package com.scanbook.view.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.scanbook.R;
import com.scanbook.adapter.AnnotationAdapter;
import com.scanbook.bean.Annotation;
import com.scanbook.net.BaseAsyncHttp;
import com.scanbook.net.HttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="http://fangjie.sinaapp.com">http://fangjie.sinaapp.com</a>
 * @version 1.0
 * @author JayFang
 * @describe 某图书的豆瓣笔记List Activity
 * 			 List采用简单的SimpleAdapter
 */
public class AnnotationListActivity extends Activity{

    private ListView mLvAnnotation;
    private List<Annotation> mAnnotations=new ArrayList<Annotation>();
    private AnnotationAdapter mAdapter;
    private String bookid;

    private int hasNum=0; //已经加载的数量

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annotation);

        bookid=getIntent().getStringExtra("id");
        RequestParams params=new RequestParams();
        BaseAsyncHttp.getReq("/v2/book/" + bookid + "/annotations", params, new HttpResponseHandler() {
            @Override
            public void jsonSuccess(JSONObject resp) {
                hasNum = resp.optInt("start") + resp.optInt("start");
                JSONArray jsons=resp.optJSONArray("annotations");
                for (int i=0;i<jsons.length();i++){
                    Annotation annotation=new Annotation();
                    annotation.setAuthor(jsons.optJSONObject(i).optJSONObject("author_user").optString("name"));
                    annotation.setAuthorHead(jsons.optJSONObject(i).optJSONObject("author_user").optString("avatar"));
                    annotation.setAbstract(jsons.optJSONObject(i).optString("abstract"));
                    annotation.setCheapter(jsons.optJSONObject(i).optString("chapter"));
                    annotation.setContent(jsons.optJSONObject(i).optString("content"));
                    annotation.setPage(jsons.optJSONObject(i).optInt("page_no"));
                    annotation.setTime(jsons.optJSONObject(i).optString("time"));
                    mAnnotations.add(annotation);
                }

                mAdapter=new AnnotationAdapter(AnnotationListActivity.this,mAnnotations);
                mLvAnnotation.setAdapter(mAdapter);
            }
        });

        mLvAnnotation=(ListView)findViewById(R.id.lv_annotation);


	}
}
