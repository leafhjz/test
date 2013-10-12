package com.cmw.android.widgets.examples;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
/** 
 * ����Handler ��ʵ��С��ʱ�ƶ�
 * Handler ���ã�
 * �ոտ�ʼ�Ӵ�android�̱߳�̵�ʱ��ϰ�ߺ���javaһ������ͼ������Ĵ���������:
 * 	 new Thread( new Runnable() {     
	    public void run() {     
	         myView.invalidate();    
	     }            
	 }).start();     
	 Ȼ�����������ǲ��еģ���Ϊ��Υ���˵��߳�ģ�ͣ�
	 	Android UI�����������̰߳�ȫ�Ĳ�����Щ����������UI�߳���ִ�С�
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
