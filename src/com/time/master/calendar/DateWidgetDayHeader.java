/**
 * @������
 */
package com.time.master.calendar;
/*
 * л��
*/
import com.time.master.tool.Constant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

/**
 * �����ؼ�ͷ��������
 * @Descriptio.n: �����ؼ�ͷ��������
 */
public class DateWidgetDayHeader extends View {
	// �����С
	private final static int fTextSize = 22;
	private Paint pt = new Paint();//����һ�����Ի��ʣ�ʵ�������ʶ���pt
	private RectF rect = new RectF();//����һ�����Σ���ʵ�������ζ���rect
	private int iWeekDay = -1;//��ʼ��������ݼ�

	public DateWidgetDayHeader(Context context, int iWidth, int iHeight) {//��������ͷ���ؼ����ݣ���͸ߵķ���
		super(context);//�̳и�������
		setLayoutParams(new LayoutParams(iWidth, iHeight));//�񲼾ִ�����������߲�ʵ�������Ŀ�͸�
	}

	@Override
	protected void onDraw(Canvas canvas) {//��д����������Ϊ�������͵Ķ���canvas
		super.onDraw(canvas);//�̳и���

		// ���þ��δ�С
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(1, 1);//��ߣ�Ϊ����ʱ�����ڱߣ�Ϊ����ʱ:����ߡ�

		// ��������ͷ��
		drawDayHeader(canvas);
	}

	private void drawDayHeader(Canvas canvas) {
		// �����Σ������þ��λ��ʵ���ɫ
		pt.setColor(Constant.Calendar_WeekBgColor);
		canvas.drawRect(rect, pt);//�ڻ������û��ʻ��ƾ�������

		// д������ͷ�������û��ʲ���
		pt.setTypeface(null);//������ʽĬ��
		pt.setTextSize(fTextSize);//���ʴ�С
		pt.setAntiAlias(true);//�����������Ƿ񿹾�ݣ�����Ϊtrue�������
		pt.setFakeBoldText(true);//��������
		pt.setColor(Constant.Calendar_WeekFontColor);//������ɫ
		
		// draw day name
		final String sDayName = DayStyle.getWeekDayName(iWeekDay);//�����������ڼ�

		final int iPosX = (int) rect.left + ((int) rect.width() >> 1)//����������С���ȷ�Ķ�λ��������

				- ((int) pt.measureText(sDayName) >> 1);
		final int iPosY = (int) (this.getHeight()
				- (this.getHeight() - getTextHeight()) / 2 - pt
				.getFontMetrics().bottom);
		canvas.drawText(sDayName, iPosX, iPosY, pt);
	}

	// �õ�����߶�
	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());//��
	}

	// �õ�һ���ڵĵڼ�����ı����
	public void setData(int iWeekDay) {
		this.iWeekDay = iWeekDay;
	}
}