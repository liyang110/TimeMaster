package com.time.master.view;

import com.time.master.R;
import com.time.master.interfacer.LayoutStyleableInterface;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
/***
 * ����TextView��BasicViewGroup����view
 * @author Desmond
 *
 */
public class BasicTextView extends TextView implements LayoutStyleableInterface{


	/**��λ��ȵı���*/
	protected int multi_width;
	/**��λ�߶ȵı���*/
	protected int multi_height;
	/**��ʼ�е�λ�ռ�*/
	protected boolean isNewLine;
	/**Ĭ�ϱ���ɫ*/
	protected int naturalColor;
	/**�����ؼ���*/
	protected int group;
	/**�ж��Ƿ�ѡ��*/
	protected boolean isSelected=false;

	protected int status=0;//״ֵ̬
	
	public BasicTextView(Context context) {
		super(context);
	}
	
	public BasicTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewGroupType);
		multi_width = a.getInt(R.styleable.ViewGroupType_width_multi, 1);

		isNewLine=a.getBoolean(R.styleable.ViewGroupType_new_line, false);
		naturalColor=a.getInt(R.styleable.ViewGroupType_default_bg, -1);
		group=a.getInt(R.styleable.ViewGroupType_attr_group, -1);
        a.recycle();
        init();
	}
	
	public BasicTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewGroupType, defStyle, 0);
		multi_width = a.getInt(R.styleable.ViewGroupType_width_multi, 1);
		isNewLine=a.getBoolean(R.styleable.ViewGroupType_new_line, false);
		naturalColor=a.getInt(R.styleable.ViewGroupType_default_bg, -1);
        a.recycle();
        init();
	}

	/**��ԭ��δѡ��״̬��ɫ*/
	public void setNaturalBackground(){
		isSelected=false;
		if(naturalColor!=1){
			this.setBackgroundColor(naturalColor);
		}
	}

	/**��ť�Ƿ�ѡ��*/
	public boolean isSelected(){
		return isSelected;
	}
	
	protected void init(){
		if(naturalColor!=-1)
			setBackgroundColor(naturalColor);
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			/**���������ɫ�仯*/
			actionDown();
			break;
		case MotionEvent.ACTION_UP:
			actionUp();
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	protected void actionDown(){
		if(naturalColor!=-1)
			this.setBackgroundColor(0xFFFFFFFF);
	}
	protected void actionUp(){
		if(naturalColor!=-1)
			this.setBackgroundColor(naturalColor);
	}
	@Override
	public int getMultiWidth() {
		return multi_width;
	}

	@Override
	public boolean isNewLine() {
		return isNewLine;
	}

	@Override
	public int getGroup() {
		// TODO Auto-generated method stub
		return group;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

}
