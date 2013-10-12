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
 * HandlerThread�����������������̵߳����̣߳�
 * ʵ���첽���ƣ�����Ӱ�쵽���̵߳����С�
 * ʾ�������������أ��ȳ�ʱ������ĳ���
 */
public class HandlerThreadActivity extends Activity {
	private ProgressBar pbar = null;
	private Button btnStart = null;
	private MyHandler myhandler = null;
	int precent = 0;
	private Runnable myprogressthread = new Runnable() {
		
		public void run() {
			precent += 5;
			Log.v("LOGV", "��ǰ�̣߳�"+Thread.currentThread().getId());
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
		//ʹ��HandlerThread��ʵ���������첽�̣߳����������̹߳����߳��ˡ�  
		HandlerThread hthread = new HandlerThread("progress_thread");
		 //ʹ��getLooper()����֮ǰ���ȵ�start()����  
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
			Log.v("LOGV", "��ǰ�̣߳�"+Thread.currentThread().getId());
			myhandler.post(myprogressthread);
			if(msg.arg1 >= 90){	//���ٷֱȳ��� 90%ʱ���Ƴ��������̡߳�
				myhandler.removeCallbacks(myprogressthread);
				pbar.setVisibility(ProgressBar.GONE);
			}
		}
		
	}
}
