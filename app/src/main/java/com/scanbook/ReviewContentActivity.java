package com.scanbook;
import com.scanbook.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * <a href="http://fangjie.sinaapp.com">http://fangjie.sinaapp.com</a>
 * @version 1.0
 * @author JayFang
 * @describe 某图书的豆瓣笔记详细页面Activity
 */

public class ReviewContentActivity extends Activity{
	
	private TextView tv_author;
	private TextView tv_content;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.content);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);  
		tv_content=(TextView)findViewById(R.id.content_lv_content);
		tv_author= (TextView)findViewById(R.id.content_lv_author);
		
		Intent intent=getIntent();
		String content=intent.getStringExtra("content");
		String author=intent.getStringExtra("author");
		tv_content.setText(content);
		tv_author.setText("来自："+author);
	}
}
