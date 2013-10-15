package com.time.master.calendar;

import java.util.Calendar;

import com.time.master.tool.Constant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout.LayoutParams;
/*ע����
 * л��
*/
/**
 * �����ؼ���Ԫ�������
 * @Description: �����ؼ���Ԫ�������
 */
public class DateWidgetDayCell extends View {
	// �����С
	private static final int fTextSize = 28;
	
	// ����Ԫ��
	private OnItemClick itemClick = null;
	private Paint pt = new Paint();//����һ�����ԣ�˽�л�ʵ����һ�����ʶ���pt
	private RectF rect = new RectF();//����һ�����Σ���ʵ�������ζ���rect
	private String sDate = "";//����һ���ַ������͵���������

	// ��ǰ����
	private int iDateYear = 0;//�������Ե�ǰ���ڣ��꣩��ʼ��
	private int iDateMonth = 0;//�������Ե�ǰ���ڣ��£���ʼ��
	private int iDateDay = 0;//�������Ե�ǰ���ڣ��죩��ʼ��

	// ��������
	private boolean bSelected = false;//�������Բ���ʼ��bSelected
	private boolean bIsActiveMonth = false;//�������Բ���ʼ��bIsActiveMonth
	private boolean bToday = false;//�������Բ���ʼ��bToday
	private boolean bTouchedDown = false;//�������Բ���ʼ��bTouchedDown
	private boolean bHoliday = false;//�������Բ���ʼ��bHoliday
	private boolean hasRecord = true;//�������Բ���ʼ��hasRecord


	public static int ANIM_ALPHA_DURATION = 100;//���ö���ʱ��Ϊ100����

	
	public interface OnItemClick {   //�Զ���ӿ��� 
		public void OnClick(DateWidgetDayCell item);  //��Ԫ��ĵ���¼�

	}

	// ���캯��
	public DateWidgetDayCell(Context context, int iWidth, int iHeight) {

		super(context);
		setFocusable(true);  // ���õ�Ԫ���н��� 
		setLayoutParams(new LayoutParams(iWidth, iHeight));  // ��Ԫ�񲼾� �� ��

	}

	// ȡ����ֵ  
	public Calendar getDate() {

		Calendar calDate = Calendar.getInstance(); //�õ�Calendar����
		calDate.clear(); // ���֮ǰcalendar�ļ� �൱��ˢ��
		calDate.set(Calendar.YEAR, iDateYear);  //������ ֵΪiDateYear
		calDate.set(Calendar.MONTH, iDateMonth);  //������ ֵΪiDateMonth
		calDate.set(Calendar.DAY_OF_MONTH, iDateDay);   //������ ֵΪiDateDay
		return calDate; //����calDate

	}

	// ���ñ���ֵ
	public void setData(int iYear, int iMonth, int iDay, Boolean bToday,
			Boolean bHoliday, int iActiveMonth, boolean hasRecord) {

		iDateYear = iYear;  
		iDateMonth = iMonth; 
		iDateDay = iDay; 

		this.sDate = Integer.toString(iDateDay); //��iDateDay int��ת��ΪString�͸�ֵ��sDate 
		this.bIsActiveMonth = (iDateMonth == iActiveMonth); //�ж��Ƿ�Ϊ��ǰ��
		this.bToday = bToday; // �ж��Ƿ�Ϊ��������
		this.bHoliday = bHoliday; // �ж��Ƿ�Ϊ�ڼ���
		this.hasRecord = hasRecord; // �ж��Ƿ��м�¼�����ţ�

	}

	// ���ػ��Ʒ���
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);//�̳и��๹�췽��


		rect.set(0, 0, this.getWidth(), this.getHeight());   //���þ��ο��
		rect.inset(1, 1);   //���þ��ο�߱���Ϊ1:1

		final boolean bFocused = IsViewFocused();   //��Ԫ����IsViewFocused������������

		drawDayView(canvas, bFocused);    //  ������������
		drawDayNumber(canvas);    //  ���������е�����
	}

	public boolean IsViewFocused() {//����
		return (this.isFocused() || bTouchedDown);//���ص�ǰ�Ƿ�۽��͵���

		
	}



	// ������������
	private void drawDayView(Canvas canvas, boolean bFocused) { //�ڻ����ϻ��Ʒ��� ���ж����Ƿ��н���

		if (bSelected || bFocused) {  // ��Ԫ���Ƿ�ѡ�� ���Ƿ��н���
			LinearGradient lGradBkg = null;   //

			if (bFocused) { // ����Ԫ���н���ʱ
				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
						0xffaa5500, 0xffffddbb, Shader.TileMode.CLAMP);   // ������ x�����Ծ��ε���ߵ��ұ�Ϊ����
			}

			if (bSelected) {  // ����Ԫ��ѡ��
				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
						0xff225599, 0xffbbddff, Shader.TileMode.CLAMP);  // ������ x�����Ծ��ε���ߵ��ұ�Ϊ����
			}

			if (lGradBkg != null) { //��������һ����������ʱ
				pt.setShader(lGradBkg);  // ���û�����ɫ�� ����
				canvas.drawRect(rect, pt); // �ڻ��������û��ʻ��Ƶ�Ԫ��rect
			}

			pt.setShader(null);  //������

		} else {
			pt.setColor(getColorBkg(bHoliday, bToday));  //Ϊ�ڼ��պ͵������ñ�����ɫ
			canvas.drawRect(rect, pt);
		}

		if (hasRecord) {  //�м�¼�� ����
			CreateReminder(canvas, Constant.special_Reminder);
		}
		// else if (!hasRecord && !bToday && !bSelected) {
		// CreateReminder(canvas, Calendar_TestActivity.Calendar_DayBgColor);
		// }
	}

	// ���������е���
	public void drawDayNumber(Canvas canvas) {
		// draw day number

		pt.setTypeface(null);//����������ʽ
		pt.setAntiAlias(true);//���þ���Ч��
		pt.setShader(null);//������ȾΪĬ��
		pt.setFakeBoldText(true);//��������Ϊ����
		pt.setTextSize(fTextSize);//���������С
		pt.setColor(Constant.isPresentMonth_FontColor);//���û��ʵ���ɫ
		pt.setUnderlineText(false);//���������»���
		
		if (!bIsActiveMonth)//�ж�������ǵ�ǰ��
			pt.setColor(Constant.unPresentMonth_FontColor);//������ʾ������ɫ

		if (bToday)//����ǵ���
			pt.setUnderlineText(true);//��ʾ������ʾ�»���

		pt.setTypeface(null);
		pt.setAntiAlias(true); //ȥ���
		pt.setShader(null);
		pt.setFakeBoldText(true);  //����
		pt.setTextSize(fTextSize);  
		pt.setColor(Constant.isPresentMonth_FontColor);
		pt.setUnderlineText(false);
		
		if (!bIsActiveMonth)  //������ǵ�ǰ��  ������ɫ 
			pt.setColor(Constant.unPresentMonth_FontColor);

		if (bToday) //����ǽ��� �����»���
			pt.setUnderlineText(true);

		final int iPosX = (int) rect.left + ((int) rect.width() >> 1)  //���ֵĿ�
				- ((int) pt.measureText(sDate) >> 1);

		final int iPosY = (int) (this.getHeight()           // ���ֵĸ�
				- (this.getHeight() - getTextHeight()) / 2 - pt
				.getFontMetrics().bottom);


		canvas.drawText(sDate, iPosX, iPosY, pt); // ��������


		pt.setUnderlineText(false);//���»���
	}

	// �õ�����߶�
	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent()); //���־���ױߵĳ��ȼ�ȥ����붥�߾��� �õ����ָ߶�
	}

	// �����������ز�ͬ��ɫֵ
	public static int getColorBkg(boolean bHoliday, boolean bToday) {
		if (bToday)
			return Constant.isToday_BgColor;  //  �ǵ��� ����Ϊ����ɫ
		// if (bHoliday) //������ĩ�����ⱳ��ɫ����ȥ��ע��
		// return Calendar_TestActivity.isHoliday_BgColor;

		return Constant.Calendar_DayBgColor;   // �ǽڼ������ô���ɫ

	}

	// �����Ƿ�ѡ��
	@Override
	public void setSelected(boolean bEnable) {
		if (this.bSelected != bEnable) {//���ѡ����������ڷ��ز�������ֵ
			this.bSelected = bEnable;//���ѡ��������ڷ��ز�������ֵ
			this.invalidate();//��ǰ������Ч�������»���
		}
	}

	// ���õ�Ԫ����
	public void setItemClick(OnItemClick itemClick) {
		this.itemClick = itemClick;

	}
	// ��Ԫ�����¼�
	public void doItemClick() {
		if (itemClick != null)//������ʲ�Ϊ��
			itemClick.OnClick(this);//���ʵ�ǰ
	}

	// ����¼�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean bHandled = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) { //����ʱ �˵�Ԫ��ʼ����
			bHandled = true;
			bTouchedDown = true;
			invalidate();
			startAlphaAnimIn(DateWidgetDayCell.this); 
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) { //û�ж���
			bHandled = true;
			bTouchedDown = false;
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) { //�ſ�ʱ ��Ԫ�����¼�
			bHandled = true;
			bTouchedDown = false;
			invalidate();
			doItemClick();
		}
		return bHandled;
	}

	// ����¼�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean bResult = super.onKeyDown(keyCode, event);
		if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
				|| (keyCode == KeyEvent.KEYCODE_ENTER)) {   //��������ִ�з���
			doItemClick();
		}
		return bResult;
	}

	// ��͸���Ƚ���  ��ѡ�����򶯻�
	public static void startAlphaAnimIn(View view) {  
		AlphaAnimation anim = new AlphaAnimation(0.5F, 1);
		anim.setDuration(ANIM_ALPHA_DURATION);
		anim.startNow();
		view.startAnimation(anim);
	}
	//����
	public void CreateReminder(Canvas canvas, int Color) {
		pt.setStyle(Paint.Style.FILL_AND_STROKE); //������
		pt.setColor(Color); 
		Path path = new Path();
		path.moveTo(rect.right - rect.width() / 4, rect.top); //��ʼ����
		path.lineTo(rect.right, rect.top);   
		path.lineTo(rect.right, rect.top + rect.width() / 4);
		path.lineTo(rect.right - rect.width() / 4, rect.top);
		path.close();
		canvas.drawPath(path, pt); //���û��ʻ�·��
	}
}