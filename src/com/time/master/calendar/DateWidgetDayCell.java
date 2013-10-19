package com.time.master.calendar;

import java.util.Calendar;

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
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout.LayoutParams;

import com.time.master.tool.Constant;
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
	private boolean hasRecord = false;//�������Բ���ʼ��hasRecord

	public static int ANIM_ALPHA_DURATION = 100;//���估ʱ�����Գ�ʼ��

	
	public interface OnItemClick {//�������еĽӿ�
		public void OnClick(DateWidgetDayCell item);//���еĳ��󷽷�
	}

	// ���캯��
	public DateWidgetDayCell(Context context, int iWidth, int iHeight) {
		super(context);//�̳и�������
		setFocusable(true);//���Ƽ����Ƿ���Ի�������ť�Ľ���,���Ի��
		setLayoutParams(new LayoutParams(iWidth, iHeight));//ʵ����ʹ�Զ��岼���еĿ�͸�
	}

	// ȡ����ֵ
	public Calendar getDate() {
		Calendar calDate = Calendar.getInstance();//ʵ��������calDate����getInstance()���������ñ���������ֵ
		calDate.clear();//�������
		calDate.set(Calendar.YEAR, iDateYear);//�������
		calDate.set(Calendar.MONTH, iDateMonth);//������
		calDate.set(Calendar.DAY_OF_MONTH, iDateDay);//������
		return calDate;//��������
	}

	// ���ñ���ֵ
	public void setData(int iYear, int iMonth, int iDay, Boolean bToday,
			Boolean bHoliday, int iActiveMonth, boolean hasRecord) {
		//�������ڣ������գ��Ƿ�Ϊ���죬�Ƿ�Ϊ���ڣ���ǰ��Ч�£��Ƿ��Ѽ�¼��
		iDateYear = iYear;//��ǰ��
		iDateMonth = iMonth;//��ǰ����
		iDateDay = iDay;//��ǰ��

		this.sDate = Integer.toString(iDateDay);//�ѵ�ǰ�����Ͱ�װ�����͵�iDateDayת�����ַ����Ͳ�ʵ����
		this.bIsActiveMonth = (iDateMonth == iActiveMonth);//ʵ������Ч�£���Ч��Ϊ��ǰ��
		this.bToday = bToday;//ʵ��������bToday
		this.bHoliday = bHoliday;//ʵ����bHoliday
		this.hasRecord = hasRecord;//ʵ����hasRecord
	}

	// ���ػ��Ʒ���
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);//�̳и��๹�췽��

		rect.set(0, 0, this.getWidth(), this.getHeight());//���þ��εĿ�͸�
		rect.inset(1, 1);//���þ��εĿ�͸�

		final boolean bFocused = IsViewFocused();//�ؼ��Ƿ�۽�

		drawDayView(canvas, bFocused);//�����ϵ��տؼ��Ƿ�۽�
		drawDayNumber(canvas);//�ڻ������յ�����
	}

	public boolean IsViewFocused() {//����
		return (this.isFocused() || bTouchedDown);//���ص�ǰ�Ƿ�۽��͵���
	}

	// ������������
	private void drawDayView(Canvas canvas, boolean bFocused) {

		if (bSelected || bFocused) {
			LinearGradient lGradBkg = null;

			if (bFocused) {
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

		final int iPosX = (int) rect.left + ((int) rect.width() >> 1)
				- ((int) pt.measureText(sDate) >> 1);

		final int iPosY = (int) (this.getHeight()
				- (this.getHeight() - getTextHeight()) / 2 - pt
				.getFontMetrics().bottom);

		canvas.drawText(sDate, iPosX, iPosY, pt);//��������λ�ü����

		pt.setUnderlineText(false);//���»���
	}

	// �õ�����߶�
	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());
	}

	// �����������ز�ͬ��ɫֵ
	public static int getColorBkg(boolean bHoliday, boolean bToday) {
		if (bToday)
			return Constant.isToday_BgColor;
		// if (bHoliday) //������ĩ�����ⱳ��ɫ����ȥ��ע��
		// return Calendar_TestActivity.isHoliday_BgColor;
		return Constant.Calendar_DayBgColor;//������ɫ
	}

	// �����Ƿ�ѡ��
	@Override
	public void setSelected(boolean bEnable) {
		if (this.bSelected != bEnable) {//���ѡ����������ڷ��ز�������ֵ
			this.bSelected = bEnable;//���ѡ��������ڷ��ز�������ֵ
			this.invalidate();//��ǰ������Ч�������»���
		}
	}

	public void setItemClick(OnItemClick itemClick) {//����������
		this.itemClick = itemClick;//ʵ��������itemClick
	}

	public void doItemClick() {
		if (itemClick != null)//������ʲ�Ϊ��
			itemClick.OnClick(this);//���ʵ�ǰ
	}

	// ����¼�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean bHandled = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			bHandled = true;
			bTouchedDown = true;
			invalidate();
			startAlphaAnimIn(DateWidgetDayCell.this);
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
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
				|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
			doItemClick();
		}
		return bResult;
	}

	// ��͸���Ƚ���
	public static void startAlphaAnimIn(View view) {
		AlphaAnimation anim = new AlphaAnimation(0.5F, 1);
		anim.setDuration(ANIM_ALPHA_DURATION);
		anim.startNow();
		view.startAnimation(anim);
	}
	// ��¼����
	public void CreateReminder(Canvas canvas, int Color) {
		pt.setStyle(Paint.Style.FILL_AND_STROKE);//���
		pt.setColor(Color);
		Path path = new Path();
		path.moveTo(rect.right - rect.width() / 4, rect.top);
		path.lineTo(rect.right, rect.top);
		path.lineTo(rect.right, rect.top + rect.width() / 4);
		path.lineTo(rect.right - rect.width() / 4, rect.top);
		path.close();//�յ���������
		canvas.drawPath(path, pt);
	}


}