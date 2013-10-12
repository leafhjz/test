package com.cmw.android.widgets.examples;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
/** 
 * 利用Handler 来实现小球定时移动
 * Handler 作用：
 * 刚刚开始接触android线程编程的时候，习惯好像java一样，试图用下面的代码解决问题:
 * 	 new Thread( new Runnable() {     
	    public void run() {     
	         myView.invalidate();    
	     }            
	 }).start();     
	 然而发现这样是不行的，因为它违背了单线程模型：
	 	Android UI操作并不是线程安全的并且这些操作必须在UI线程中执行。
 * @author chengmingwei
 *
 */
public class HandlerActivity extends Activity {
	 protected static final int GUIUPDATEIDENTIFIER = 0x101;   
	BounceView bounceView = null;
	
	Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GUIUPDATEIDENTIFIER:
				bounceView.invalidate();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		bounceView = new BounceView(this);
		setContentView(bounceView);
		new Thread(new MyThread()).start();
	}

	class MyThread implements Runnable{

		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				Message msg = new Message();
				msg.what = GUIUPDATEIDENTIFIER;
				myHandler.sendMessage(msg);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
