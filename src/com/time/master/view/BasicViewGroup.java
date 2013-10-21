package com.time.master.view;

import com.time.master.TimeMasterApplication;
import com.time.master.interfacer.LayoutStyleableInterface;

import android.content.Context;
import android.content.res.Configuration;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * �Զ��岼���࣬
 * �趨��view֮����
 * ������Ļ��С����view�ĳ���
 * @author Desmond
 *
 */
public class BasicViewGroup extends ViewGroup{

	protected int screen_width,
	screen_height,
	unit_width,//view ��λ����
	gap,//view�ļ������
	current_margin_top=0,//��ǰ����y����
	current_margin_left=0,//��ǰ����x����
	screen_mode; //1�������� �� 2�������
	
	public BasicViewGroup(Context context) {
		super(context);
		init();
	}
	
	public BasicViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public BasicViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int num=this.getChildCount();
		int line=0;
		int width=0;
		int max_width=0;//��������ȵ���
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
		int height=line==0?0:(int)(line*unit_width*0.75+gap*(line+1));
		
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
			layoutParams.height=(int)(unit_width*0.75);
			
			view.setLayoutParams(layoutParams);
			/***������view ��С����view��onMeasure�������ص� **/
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
	 * ��ʼ�����в���
	 */
	protected void init(){
		screen_mode=TimeMasterApplication.getInstance().getScreenMode();
		screen_width=TimeMasterApplication.getInstance().getScreen_W();
		screen_height=TimeMasterApplication.getInstance().getScreen_H();
		unit_width=screen_width/6;
		gap=screen_width/36;
		current_margin_top=(int)(-0.75*unit_width);
	}
	
	

}
