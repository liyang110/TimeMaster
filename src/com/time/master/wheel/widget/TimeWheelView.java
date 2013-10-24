package com.time.master.wheel.widget;

import com.time.master.R;
import com.time.master.view.WheelItemTextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TimeWheelView extends WheelView {
	
	public TimeWheelView(Context context) {
		super(context);
		init();
	}
	
	public TimeWheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public TimeWheelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		centerDrawable = getContext().getResources().getDrawable(R.drawable.wheel_center_highlight);
	}
	
	
	@Override
	protected void drawCenterRect(Canvas canvas) {
		super.drawCenterRect(canvas);
	}
	
}
