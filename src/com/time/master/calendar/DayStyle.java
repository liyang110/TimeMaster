/**
 * @������
 */

package com.time.master.calendar;

import java.util.Calendar;

/**
 * �����ؼ���ʽ������
 * @Description: �����ؼ���ʽ������
 */
public class DayStyle {
	private final static String[] vecStrWeekDayNames = getWeekDayNames();

	private static String[] getWeekDayNames() {
		String[] vec = new String[10];

		vec[Calendar.SUNDAY] = "����";
		vec[Calendar.MONDAY] = "��һ";
		vec[Calendar.TUESDAY] = "�ܶ�";
		vec[Calendar.WEDNESDAY] = "����";
		vec[Calendar.THURSDAY] = "����";
		vec[Calendar.FRIDAY] = "����";
		vec[Calendar.SATURDAY] = "����";
		
		return vec;
	}

	public static String getWeekDayName(int iDay) {
		return vecStrWeekDayNames[iDay];
	}
	/**
	 * �жϵ��������ڼ���
	 * @param index  ָ������  ����ָ����һ����ַ�����ڼ���
	 * @param iFirstDayOfWeek  ���ܵĵ�һ��
	 * @return
	 */
	public static int getWeekDay(int index, int iFirstDayOfWeek) {
		
		//һ����7��  , iweekday��������ȡ���������ڼ�
		int iWeekDay = -1;
       //�ж� ���ܵĵ�һ��������һ����ʱiweekday=����һ��һֱ�������Ƕ����� ����>����ʱ��������һ���жϡ�
		if (iFirstDayOfWeek == Calendar.MONDAY) {
			iWeekDay = index + Calendar.MONDAY;
			 //iweekday>��������6�����ͽ�iweekday=0(��ʾ������) 
			if (iWeekDay > Calendar.SATURDAY)
				iWeekDay = Calendar.SUNDAY;
		}
		 //�ж� ���ܵĵ�һ���������գ���iweekday=0�������գ�
		if (iFirstDayOfWeek == Calendar.SUNDAY) {
			iWeekDay = index + Calendar.SUNDAY;
		}
        
		return iWeekDay;//�����ܼ���
	}
}
