package com.time.master.view;

import com.time.master.TimeMasterApplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.Gravity;

public class TabTextView extends SelectedTextView {

	protected int screen_width,
	screen_height,
	unit_width,//view ��λ����
	gap,//view�ļ������
	screen_mode; //1�������� �� 2�������
	
	Paint mPaint,marginPaint,linePaint;
	boolean hasRightEdge=false;
	float strokeWdith=10f;
	
	public TabTextView(Context context) {
		super(context);
		init();
	}
	public TabTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public TabTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	/***
	 * ��ʼ�����в���
	 */
	protected void init(){
		screen_mode=TimeMasterApplication.getInstance().getScreenMode();
		screen_width=TimeMasterApplication.getInstance().getScreen_W();
		screen_height=TimeMasterApplication.getInstance().getScreen_H();
		unit_width=screen_width/6;
		gap=screen_width/36;
		mPaint=new Paint();
		mPaint.setColor(0xFFCCCCCC);
		marginPaint=new Paint();
		marginPaint.setColor(0xFFFFFFFF);
		linePaint=new Paint();
		linePaint.setColor(0xFFCCCCCC);
		linePaint.setStyle(Style.STROKE);
		linePaint.setStrokeWidth(strokeWdith);
		linePaint.setAntiAlias(true); // �������   
		linePaint.setFlags(Paint.ANTI_ALIAS_FLAG); // �������  
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(!hasRightEdge)
		    this.setMeasuredDimension(gap+unit_width, (int)(unit_width*0.75)+(int)strokeWdith);
		else
			this.setMeasuredDimension(gap+unit_width+gap, (int)(unit_width*0.75)+(int)strokeWdith);
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRect(0, 0, gap, unit_width*0.75f, marginPaint);//��߿�
        canvas.drawRect(gap, 0, gap+unit_width, unit_width*0.75f, mPaint);//���о���
		if(hasRightEdge)
			canvas.drawRect(gap+unit_width, 0, gap+gap+unit_width, unit_width*0.75f, marginPaint);//�ұ߿�
		
		canvas.drawLine(0, unit_width*0.75f+strokeWdith/2, unit_width+gap+(hasRightEdge?gap:0), unit_width*0.75f+strokeWdith/2, linePaint);
		super.onDraw(canvas);
	}
	
	public TabTextView setCenterText(String text){
		this.setText(text);
		this.setTextColor(0xFF000000);
		this.setGravity(Gravity.CENTER);
		return this;
	}
	
	public void setCenterBackgroud(int color){
		this.naturalColor=color;
		mPaint.setColor(naturalColor);
	}
	
	public void setRightMargin(){
		hasRightEdge=true;
	}

	@Override
	protected void actionDown() {
		
	}
	@Override
	protected void actionUp() {
		
	}
}
