package com.scanbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.scanbook.R;
import com.scanbook.bean.Review;
import com.scanbook.util.BookUtil;

/**
 * <a href="http://fangjie.sinaapp.com">http://fangjie.sinaapp.com</a>
 * @version 1.0
 * @author JayFang
 * @describe 某图书的豆瓣笔记List Activity
 * 			 List采用简单的SimpleAdapter
 */
public class ReviewListActivity extends Activity{
	
	private ListView list;
	//笔记数据List
	private List<Review> reviews;
	//List中的数据
	private List<HashMap<String,String>> ListData;
	//加载更多的TextView
	private TextView tv_review_more;
	
	public SimpleAdapter listadpter;
	private AsyncLoadReview loadnews;
	private ProgressDialog progressDialog;
	
	private Button btn_back;
	private TextView review_no,title;
	//某图书的 id
	private static String book_id;
	//某图书的名字
	private static String book_name;
	//分页加载的页数
	private static int curr_num;
	//某图书豆瓣笔记总条数
	private int total;
	private static final int NO_DATA = 0;
	private static final int OK = 1;	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.review);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);  
        
        curr_num=0;
		Intent intent=getIntent();
		book_id=intent.getExtras().getString("id");
		book_name=intent.getExtras().getString("name");	
		
		ListData=new ArrayList<HashMap<String,String>>();
		reviews=new ArrayList<Review>();
		list.setAdapter(listadpter);
		list.setEnabled(true);
		
		review_no=(TextView)findViewById(R.id.review_no);
		title=(TextView)findViewById(R.id.titlebar_lv_title);
		list =(ListView)findViewById(R.id.main_list);			
		progressDialog=new ProgressDialog(this);
		progressDialog.setMessage("正在加载，请稍候...");

		loadnews=new AsyncLoadReview();
		loadnews.execute();
		
		//List Item 的单击事件
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Review re=reviews.get(position);
				Intent in=new Intent(ReviewListActivity.this,ReviewContentActivity.class);
				in.putExtra("content", re.getContent());
				in.putExtra("author", re.getAuthor());
				startActivity(in);
			}
		});
		
        LayoutInflater mInflater =(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE); 
        View LoadMoreView=mInflater.inflate(R.layout.loadmore,null);
        list.addFooterView(LoadMoreView);        
        tv_review_more=(TextView)LoadMoreView.findViewById(R.id.review_more);
        tv_review_more.setClickable(true);
        tv_review_more.setFocusable(true);  
        
        tv_review_more.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadnews=new AsyncLoadReview();
				loadnews.execute();
			}
		});
        
        //返回按钮
        btn_back=(Button)findViewById(R.id.titlebar_bt_back);
        btn_back.setVisibility(View.VISIBLE);       
        btn_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent1=new Intent(ReviewListActivity.this,MainActivity.class);
				startActivity(intent1);
				finish();
			}
		});
	}

	//异步加载数据的类
	public class AsyncLoadReview extends AsyncTask<Object, Integer, Void>
	{
		//异步操作之前
	    protected void onPreExecute()
	    {
	    	progressDialog.show();
	    }

	    //异步操作中
		protected Void doInBackground(Object... params) {
			if(getData(ListData)==NO_DATA)
				ListData=null;
			return null;
		}

		//异步操作之后（上一步的返回值就是这一步的 参数）
	    protected void onPostExecute(Void result)
	    {
	    	//当没有笔记信息时做的控件显示操作
	    	if(ListData!=null){
	    		listadpter = new SimpleAdapter(ReviewListActivity.this, ListData, R.layout.main_listview_item,
						new String[]{"author","abstract"}, 
						new int[]{R.id.main_lv_title,R.id.main_lv_abstract});
	    	}
	    	//当有笔记信息时做的控件显示操作
	    	else{
	    		Toast.makeText(ReviewListActivity.this, "当前没有任何笔记,请返回", Toast.LENGTH_LONG).show();
	    		review_no.setVisibility(View.VISIBLE);
	    	}
	    	if(total==curr_num)
	    		tv_review_more.setVisibility(View.GONE);
	    	title.setText("「"+book_name+"」的"+total+"条笔记");
	    	title.setTextSize(12);
	    	progressDialog.dismiss();
	    }
	 }
	
	/**
	 * 发送get方式的HTTP请求获取JSON数据并解析填充List<Review>和List<HashMap<String,String>>内容
	 * @param List<HashMap<String,String>>
	 * @return int
	 */
	private int getData(List<HashMap<String,String>> ListData){
		int count=5;
		total=0;
		String url="https://api.douban.com/v2/book/"+book_id+"/annotations?start="+curr_num+"&count=5";
		//发送get请求，获取返回数据
		String result=BookUtil.getHttpRequest(url);
		//解析get请求返回的JSON数据
		try {
			JSONObject data_obj = new JSONObject(result);
			total=Integer.parseInt(data_obj.getString("total"));
			if(total==0)
				return NO_DATA;
			if(Integer.parseInt(data_obj.getString("total"))-Integer.parseInt(data_obj.getString("start"))<5)
				count=Integer.parseInt(data_obj.getString("total"))-Integer.parseInt(data_obj.getString("start"));
			
			JSONArray data_arr=data_obj.getJSONArray("annotations");
			for(int i=0;i<count;i++)
			{
				//将JSON数据解析放到List<Review>中
				Review review=new Review();
				JSONObject data_arr_obj=data_arr.getJSONObject(i);
				review.setAuthor(data_arr_obj.getJSONObject("author_user").getString("name"));
				review.setAbstract(data_arr_obj.getString("abstract").replace("\n", " ").replace("\n", " ").replace("<原文开始>", "     "));
				review.setContent(data_arr_obj.getString("content").replace("<原文开始>", "     ").replace("</原文结束>", ""));
				reviews.add(review);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//将List<Review>的内容赋到List<HashMap<String,String>>中
		for(int i=curr_num;i<reviews.size();i++){
			HashMap<String,String> ListMap=new HashMap<String,String>();
			ListMap.put("author", reviews.get(i).getAuthor());
			ListMap.put("abstract", reviews.get(i).getAbstract());
			ListMap.put("content",reviews.get(i).getContent());
			ListData.add(ListMap);
		}
		curr_num+=count;
		return OK;
	}
}
