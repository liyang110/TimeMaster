package com.time.master.view;

import com.time.master.TimeMasterApplication;
import com.time.master.interfacer.LayoutStyleableInterface;

import android.R.integer;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * 自定义布局类，
 * 设定子view之间间距
 * 根据屏幕大小部署view的长宽
 * @author Desmond
 *
 */
public class BasicViewGroup extends ViewGroup{
	Context context;
	protected int screen_width,
	screen_height,
	unit_width,//view 单位长度
	gap,//view的间隔长度
	unit_height,//view单位高度
	current_margin_top=0,//当前放置y坐标
	current_margin_left=0,//当前放置x坐标
	screen_mode; //1代表竖屏 ， 2代表横屏

	int multi=1;
	int chlidrenId;

	int gapNumber=0;

	public BasicViewGroup(Context context) {
		super(context);
		this.context=context;
		init();
	}
	
	public BasicViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
	}
	
	public BasicViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		init();
	}
	
	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
	}
	@Override
	public void addView(View child, int index) {
		// TODO Auto-generated method stub
		super.addView(child, index);
	}
	@Override
	public void addView(View child, int index, LayoutParams params) {
		// TODO Auto-generated method stub
		super.addView(child, index, params);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		init();
		int num=this.getChildCount();
		int line=0;
		int width=0;
		int max_width=0;//计算最大宽度的行
		for(int i=0;i<num;i++){
			View view=this.getChildAt(i);
			LayoutStyleableInterface styleable=(LayoutStyleableInterface)view;
			if(styleable.isNewLine()){
				if(width>max_width)
					max_width=width;
				width=0;
				line++;
			}
			width+=styleable.getMultiWidth()*unit_width+styleable.getMultiWidth()*gap;
		}
		max_width=max_width<width?width+gap:max_width+gap;
		int height=line==0?0:(int)(line*unit_width*0.75+gap*(line+1-gapNumber));
		
		setMeasuredDimension(max_width,height);
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		init();
		int num=this.getChildCount();
		for(int i=0;i<num;i++){
			View view=this.getChildAt(i);
			LayoutStyleableInterface styleable=(LayoutStyleableInterface)view;
			if(styleable.isNewLine()){
				current_margin_top+=0.75*unit_width+gap;
				current_margin_left=gap;
			}
			ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
			layoutParams.width=styleable.getMultiWidth()*unit_width+(styleable.getMultiWidth()-1)*gap;
			if(i==chlidrenId)
			layoutParams.height=(int)(unit_width*0.75)*multi+(gap)*(multi-1);
			else {
				layoutParams.height=(int)(unit_width*0.75);
			}
			
			view.setLayoutParams(layoutParams);
			/***设置子view 大小，子view的onMeasure方法被回调 **/
			view.measure(
					MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY), 
					MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY));
			view.layout(current_margin_left, 
					current_margin_top, 
					current_margin_left+layoutParams.width, 
					current_margin_top+layoutParams.height);
			current_margin_left+=layoutParams.width+gap;

		}

		
	}

	/***
	 * 初始化所有参数
	 */
	protected void init(){
		int screen_mode=context.getResources().getConfiguration().orientation;
		screen_width=TimeMasterApplication.getInstance().getScreen_W();
		if(screen_mode==2)
			screen_width=screen_width*7/8-screen_width/64;
		screen_height=TimeMasterApplication.getInstance().getScreen_H();
		unit_width=screen_width/6;
		gap=screen_width/36;
		current_margin_top=(int)(-0.75*unit_width);
	}

	public void setSpecialChildren(int childrenID,int multi){
		this.chlidrenId=childrenID;
		this.multi=multi;
	}
	public void cutDownBottomGap(int num){
		this.gapNumber=num;

	}
	

}
