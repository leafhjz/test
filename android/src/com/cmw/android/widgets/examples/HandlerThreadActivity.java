package com.cmw.android.widgets.examples;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cmw.android.widgets.R;
/** 
 * HandlerThread类来创建独立于主线程的新线程，
 * 实现异步机制，不会影响到主线程的运行。
 * 示例：可用于下载，等长时间操作的场景
 */
public class HandlerThreadActivity extends Activity {
	private ProgressBar pbar = null;
	private Button btnStart = null;
	private MyHandler myhandler = null;
	int precent = 0;
	private Runnable myprogressthread = new Runnable() {
		
		public void run() {
			precent += 5;
			Log.v("LOGV", "当前线程："+Thread.currentThread().getId());
			try {
				Message msg = new Message();
				msg.arg1 = precent;
				myhandler.sendMessage(msg);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hanlderthread_example);
		pbar = (ProgressBar)findViewById(R.id.pb_hanlderthread);
		btnStart = (Button)findViewById(R.id.btnStart);
		//使用HandlerThread来实现真正的异步线程，不会与主线程公用线程了。  
		HandlerThread hthread = new HandlerThread("progress_thread");
		 //使用getLooper()方法之前，先调start()方法  
		hthread.start();
		myhandler = new MyHandler(hthread.getLooper());
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				precent = 0;
				pbar.setVisibility(ProgressBar.VISIBLE);
				myhandler.post(myprogressthread);
			}
		});
	}
	
	
	class MyHandler extends Handler{

		public MyHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			pbar.setProgress(msg.arg1);
			Log.v("LOGV", "当前线程："+Thread.currentThread().getId());
			myhandler.post(myprogressthread);
			if(msg.arg1 >= 90){	//当百分比超过 90%时，移除进度条线程。
				myhandler.removeCallbacks(myprogressthread);
				pbar.setVisibility(ProgressBar.GONE);
			}
		}
		
	}
}
