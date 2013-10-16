package com.time.master.view;

import com.time.master.TimeMasterApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * ���ֻ���Сitem�ؼ�
 * @author Desmond
 *
 */
public class WheelItemTextView extends TextView {

	public WheelItemTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public WheelItemTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public WheelItemTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	/**����view�ĸ߶�Ϊ��Ļ���1/10*/
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		heightMeasureSpec=MeasureSpec.makeMeasureSpec(TimeMasterApplication.getInstance().getScreen_W()*6/6/10, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
