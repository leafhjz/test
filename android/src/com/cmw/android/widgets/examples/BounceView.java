package com.cmw.android.widgets.examples;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
/**
 * ‘≤–Œ«ÚÃÂ ”Õº
 * @author chengmingwei
 *
 */
public class BounceView extends View {
	float x = 40;
	boolean isJian = false;
	public BounceView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(x==200){
			isJian = true;
		}
		if(x == 40) isJian = false;
		x = isJian ? x-10 : x+10;
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		canvas.drawCircle(x, 40, 40, paint);
		Log.v(this.toString(), "x="+x);
	}

}
