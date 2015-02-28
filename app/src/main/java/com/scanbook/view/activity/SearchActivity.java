package com.scanbook.view.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.scanbook.R;
import com.scanbook.adapter.SearchAdapter;
import com.scanbook.bean.Book;
import com.scanbook.common.KeyboardUtils;
import com.scanbook.net.BaseAsyncHttp;
import com.scanbook.net.HttpResponseHandler;
import com.scanbook.view.CircularProgressView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    private ListView mLvSearch;
    private SearchAdapter mAdapter;

    private List<Book> mBooks=new ArrayList<Book>();
    private EditText mEtContent;
    private CircularProgressView progressView;
    private Thread updateThread;
    private RelativeLayout mRlBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mEtContent=(EditText)findViewById(R.id.et_search_content);
        mLvSearch=(ListView)findViewById(R.id.lv_search);
        mRlBtn=(RelativeLayout)findViewById(R.id.rl_search_btn);

        mRlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimationThreadStuff(100);
                KeyboardUtils.closeKeyBoard(SearchActivity.this);
                getRequestData(mEtContent.getText().toString());
            }
        });
        progressView = (CircularProgressView) findViewById(R.id.progress_view);
        mAdapter=new SearchAdapter(this,mBooks);
        mLvSearch.setAdapter(mAdapter);

        mLvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(SearchActivity.this,BookViewActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("book", mBooks.get(i));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")){
                    mRlBtn.setVisibility(View.GONE);
                }else{
                    mRlBtn.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public void getRequestData(String str){
        RequestParams params=new RequestParams();
        params.put("q",str.trim());
        BaseAsyncHttp.getReq("/v2/book/search",params,new HttpResponseHandler() {
            @Override
            public void jsonSuccess(JSONObject resp) {
                mBooks.clear();
                progressView.setVisibility(View.GONE);
                JSONArray jsonbooks=resp.optJSONArray("books");
                for (int i=0;i<jsonbooks.length();i++){
                    Book mBook=new Book();
                    mBook.setId(jsonbooks.optJSONObject(i).optString("id"));
                    mBook.setRate(jsonbooks.optJSONObject(i).optJSONObject("rating").optDouble("average"));
                    mBook.setReviewCount(jsonbooks.optJSONObject(i).optJSONObject("rating").optInt("numRaters"));
                    String authors="";
                    for (int j=0;j<jsonbooks.optJSONObject(i).optJSONArray("author").length();j++){
                        authors=authors+" "+jsonbooks.optJSONObject(i).optJSONArray("author").optString(j);
                    }
                    mBook.setAuthor(authors);
                    String tags="";
                    for (int j=0;j<jsonbooks.optJSONObject(i).optJSONArray("tags").length();j++){
                        tags=tags+" "+jsonbooks.optJSONObject(i).optJSONArray("tags").optJSONObject(j).optString("name");
                    }
                    mBook.setTag(tags);
                    mBook.setAuthorInfo(jsonbooks.optJSONObject(i).optString("author_intro"));
                    mBook.setBitmap(jsonbooks.optJSONObject(i).optString("image"));
                    mBook.setId(jsonbooks.optJSONObject(i).optString("id"));
                    mBook.setTitle(jsonbooks.optJSONObject(i).optString("title"));
                    mBook.setPublisher(jsonbooks.optJSONObject(i).optString("publisher"));
                    mBook.setPublishDate(jsonbooks.optJSONObject(i).optString("pubdate"));
                    mBook.setISBN(jsonbooks.optJSONObject(i).optString("isbn13"));
                    mBook.setSummary(jsonbooks.optJSONObject(i).optString("summary"));
                    mBook.setPage(jsonbooks.optJSONObject(i).optString("pages"));
                    mBook.setPrice(jsonbooks.optJSONObject(i).optString("price"));
                    mBook.setContent(jsonbooks.optJSONObject(i).optString("catalog"));
                    mBook.setUrl(jsonbooks.optJSONObject(i).optString("ebook_url"));
                    mBooks.add(mBook);
                }
                updateToView();
            }
            @Override
            public void jsonFail(JSONObject resp) {
                Toast.makeText(SearchActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateToView(){
        mAdapter.setData(mBooks);
        mAdapter.notifyDataSetChanged();
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

    private void startAnimationThreadStuff(long delay)
    {
        if(updateThread != null && updateThread.isAlive())
            updateThread.interrupt();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressView.setVisibility(View.VISIBLE);
                progressView.setProgress(0f);
                progressView.startAnimation(); // Alias for resetAnimation, it's all the same
                updateThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progressView.getProgress() < progressView.getMaxProgress() && !Thread.interrupted()) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressView.setProgress(progressView.getProgress() + 10);
                                }
                            });
                            SystemClock.sleep(250);
                        }
                    }
                });
                updateThread.start();
            }
        }, delay);
    }
}
