package com.scanbook;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.scanbook.R;
import com.scanbook.bean.Book;
import com.scanbook.util.BookUtil;

/**
 * <a href="http://fangjie.sinaapp.com">http://fangjie.sinaapp.com</a>
 * @version 1.0
 * @author JayFang
 * @describe “扫扫图书”应用程序主界面Activity
 */

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
    private TextView tx1;
    private Button btn;
    private Handler handler;
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);  
        
        //设置初始界面的显示
        StringBuffer str=new StringBuffer();
        str.append("1.按下扫描按钮启动摄像头，扫描并获取书籍的条形码；").append("\n");
        str.append("2.查询书籍相关介绍信息；").append("\n");
        str.append("3.显示在界面上").append("\n");
        tx1=(TextView)findViewById(R.id.main_textview01);
        tx1.setText(str.toString());
        btn=(Button)findViewById(R.id.main_button01);
        
        //接收来自下载线程的消息
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                Book book= (Book)msg.obj;
                //进度条消失
                progressDialog.dismiss();
                if(book==null){
                	Toast.makeText(MainActivity.this, "没有找到这本书", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent=new Intent(MainActivity.this,BookViewActivity.class);
                    //通过Intent 传递 Object，需要让该实体类实现Parceable接口
                    intent.putExtra(Book.class.getName(),book);
                    startActivity(intent);
                }
            }
        };
        
        //“开始扫描”按钮的监听事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent intent=new Intent(MainActivity.this,CaptureActivity.class);
            	startActivityForResult(intent,100);
            }
        });
    }
    
    /*
     *	从MainActivity 开启扫描跳到 CaptureActivity，扫描到ISBN 返回到 MainActivity 
     *	返回的 ISBN码 绑定在Intent中
     */
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(((requestCode==100)||(resultCode==Activity.RESULT_OK))&&data!=null){
        	//判断网络是否连接
        	if(BookUtil.isNetworkConnected(this)){
	        	progressDialog=new ProgressDialog(this);
	        	progressDialog.setMessage("请稍候，正在读取信息...");
	        	progressDialog.show();
	            String urlstr="https://api.douban.com/v2/book/isbn/"+data.getExtras().getString("result");
	            //扫到ISBN后，启动下载线程下载图书信息
	            new LoadParseBookThread(urlstr).start();
        	}
        	else {
				Toast.makeText(this, "网络异常，请检查你的网络连接", Toast.LENGTH_LONG).show();
			}
        }
    }

    /**
     * <a href="http://fangjie.sinaapp.com">http://fangjie.sinaapp.com</a>
     * @version 1.0
     * @author JayFang
     * @describe 异步下载并解析图书信息的线程类，线程结束后会发送Message消息，带有解析之后的Book对象
     */
    private class LoadParseBookThread extends Thread
    {
        private String url;
        
        //通过构造函数传递url地址
        public LoadParseBookThread(String urlstr)
        {
            url=urlstr;
        }
        
        public void run()
        {
            Message msg=Message.obtain(); 
            String result=BookUtil.getHttpRequest(url);
            try {
	            Book book=new BookUtil().parseBookInfo(result);
	            //给主线程UI界面发消息，提醒下载信息，解析信息完毕
	            msg.obj=book;
			} catch (Exception e) {
				e.printStackTrace();
			}
            handler.sendMessage(msg);
        }
    }
}
