/**
 * @������
 */
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

/**
 * �����ؼ���Ԫ�������
 * 
 * @Description: �����ؼ���Ԫ�������
 */
public class DateWidgetDayCell extends View {
	// �����С
	private static final int fTextSize = 28;

	// ����Ԫ��
	private OnItemClick itemClick = null;
	// The Paint class holds the style and color information about how to draw
	// geometries, text and bitmaps.

	private Paint pt = new Paint();// Create a new paint with default settings
	// The rectangle is represented by the coordinates of its 4 edges (left,
	// top, right bottom). These fields can be accessed directly.
	private RectF rect = new RectF();
	private String sDate = "";

	// ��ǰ����
	private int iDateYear = 0;// ��
	private int iDateMonth = 0;// ��
	private int iDateDay = 0;// ��

	// ��������
	private boolean bSelected = false;
	private boolean bIsActiveMonth = false;
	private boolean bToday = false;
	private boolean bTouchedDown = false;
	private boolean bHoliday = false;
	private boolean hasRecord = false;

	public static int ANIM_ALPHA_DURATION = 100;

	// ����Ԫ�����õ���¼���
	public interface OnItemClick {
		public void OnClick(DateWidgetDayCell item);
	}

	// ���캯��
	public DateWidgetDayCell(Context context, int iWidth, int iHeight) {
		super(context);
		/**
		 * Set whether this view can receive the focus. Setting this to false
		 * will also ensure that this view is not focusable in touch mode.
		 */
		setFocusable(true);
		setLayoutParams(new LayoutParams(iWidth, iHeight));// ָ����Ԫ��Ĵ�С
	}

	// ȡ����ֵ
	public Calendar getDate() {
		/**
		 * Constructs a new instance of the Calendar subclass appropriate for
		 * the default Locale.
		 */
		Calendar calDate = Calendar.getInstance();
		calDate.clear();// Clears all of the fields of this Calendar. All fields
						// are initialized to zero
		calDate.set(Calendar.YEAR, iDateYear);
		calDate.set(Calendar.MONTH, iDateMonth);
		calDate.set(Calendar.DAY_OF_MONTH, iDateDay);
		return calDate;
	}

	/**
	 * ���ñ���ֵ
	 * 
	 * @param iYear
	 *            ���
	 * @param iMonth
	 *            �·�
	 * @param iDay
	 *            ����
	 * @param bToday
	 *            ��������
	 * @param bHoliday
	 *            ����
	 * @param iActiveMonth
	 *            ��ǰ�·�=8
	 * @param hasRecord
	 *            ��¼
	 */
	public void setData(int iYear, int iMonth, int iDay, Boolean bToday,
			Boolean bHoliday, int iActiveMonth, boolean hasRecord) {
		iDateYear = iYear;// ����Ϊ��ǰ�����Ϊ2013
		iDateMonth = iMonth;// ����Ϊ��ǰ���·�Ϊ��ǰ����7�·ݡ�
		iDateDay = iDay;// ����Ϊ��ǰ����Ϊ��ǰ����26��

		this.sDate = Integer.toString(iDateDay);// ��õ�ǰ����
		this.bIsActiveMonth = (iDateMonth == iActiveMonth);//
		this.bToday = bToday;// ��ǰ����Ĭ�ϲ��ǵ���
		this.bHoliday = bHoliday;// ��ǰ����Ĭ�ϲ��Ǽ���
		this.hasRecord = hasRecord;// ��ǰ����Ĭ��û�м�¼���
	}

	// ���ػ��Ʒ���
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		/**
		 * Set the rectangle's coordinates(����) to the specified values. left The
		 * X coordinate of the left side of the rectangle top The Y coordinate
		 * of the top of the rectangle right The X coordinate of the right side
		 * of the rectangle bottom The Y coordinate of the bottom of the
		 * rectangle
		 */
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(1, 1);

		final boolean bFocused = IsViewFocused();

		drawDayView(canvas, bFocused);
		drawDayNumber(canvas);
	}

	// view��ý��㷽��������isFocused()���������¡�
	public boolean IsViewFocused() {
		// Returns true if this view has focus
		return (this.isFocused() || bTouchedDown);
	}

	// ������������
	private void drawDayView(Canvas canvas, boolean bFocused) {
		// û�б�ѡ�� Ҳû�л�ý���
		if (bSelected || bFocused) {
			LinearGradient lGradBkg = null;

			if (bFocused) {
				/**
				 * Create a shader(��ɫ����) that draws a linear gradient���ݶȣ� along
				 * a line.
				 *  x0 The x-coordinate for the start of the gradient line 
				 *  y0 The y-coordinate for the start of the gradient line
				 *  x1 The x- for the end of the gradient line 
				 *  y1 The y-coordinate for the end of the gradient line
				 *  color0 The color at the start of the gradient line. 
				 *  color1 The color at the end of the gradient line. 
				 *  tile The Shader tiling mode
				 * 
				 */
				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
						0xffaa5500, 0xffffddbb, Shader.TileMode.CLAMP);
			}

			if (bSelected) {
				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
						0xff225599, 0xffbbddff, Shader.TileMode.CLAMP);
			}

			if (lGradBkg != null) {
				pt.setShader(lGradBkg);
				canvas.drawRect(rect, pt);
			}

			pt.setShader(null);

		} else {
			pt.setColor(getColorBkg(bHoliday, bToday));
			canvas.drawRect(rect, pt);
		}

		if (hasRecord) {
			CreateReminder(canvas, Constant.special_Reminder);
		}
		// else if (!hasRecord && !bToday && !bSelected) {
		// CreateReminder(canvas, Calendar_TestActivity.Calendar_DayBgColor);
		// }
	}

	// ���������е�����
	public void drawDayNumber(Canvas canvas) {
		// draw day number
		pt.setTypeface(null);//Set or clear the typeface(����) object. 
		pt.setAntiAlias(true);//  true to set the antialias bit in the flags, false to clear it  
		pt.setShader(null);//May be null. the new shader(��ɫ��) to be installed in the paint
		pt.setFakeBoldText(true);//���ô���
		pt.setTextSize(fTextSize);//��������
		pt.setColor(Constant.isPresentMonth_FontColor);//The new color (including alpha) to set in the paint. 
		pt.setUnderlineText(false);//�����»���

		if (!bIsActiveMonth) //������Ǳ���  ������ɫ 
			pt.setColor(Constant.unPresentMonth_FontColor);

		if (bToday)//����ǽ��� �����»���
			pt.setUnderlineText(true);

		final int iPosX = (int) rect.left + ((int) rect.width() >> 1)//�������ֵĿ�
				- ((int) pt.measureText(sDate) >> 1);

		final int iPosY = (int) (this.getHeight()
				- (this.getHeight() - getTextHeight()) / 2 - pt//�������ֵĸ�
				.getFontMetrics().bottom);

		canvas.drawText(sDate, iPosX, iPosY, pt);//��������

		pt.setUnderlineText(false);
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
		if (this.bSelected != bEnable) {
			this.bSelected = bEnable;
			this.invalidate();
		}
	}
	// ���õ�Ԫ����
	public void setItemClick(OnItemClick itemClick) {
		this.itemClick = itemClick;
	}
	// ��Ԫ�����¼�
	public void doItemClick() {
		if (itemClick != null)
			itemClick.OnClick(this);
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