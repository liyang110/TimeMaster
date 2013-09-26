package com.time.master.calendar;

import java.util.Calendar;
/*
 * л��
 */
/**
 * �����ؼ���ʽ������
 * @Description: �����ؼ���ʽ������
 */
public class DayStyle {
	private final static String[] vecStrWeekDayNames = getWeekDayNames();
//����һ��˽�о�̬���ַ����������vecStrWeekDayNames�����ú���getWeekDayNames()�����������ֵ����ÿ������ֻ�ܸ�һ��ֵ
	private static String[] getWeekDayNames() {//���췽��
		String[] vec = new String[10];//ʵ�����ַ����������vec,�ַ�����������0~10һ��11������

		vec[Calendar.SUNDAY] = "����";
		vec[Calendar.MONDAY] = "��һ";
		vec[Calendar.TUESDAY] = "�ܶ�";
		vec[Calendar.WEDNESDAY] = "����";
		vec[Calendar.THURSDAY] = "����";
		vec[Calendar.FRIDAY] = "����";
		vec[Calendar.SATURDAY] = "����";
		//��ֵ�ַ����������vec��6����������һ��Calendar.SUNDAY����String[10]���String[0]��ֻ�г�7��������
		return vec;//���ض���vec
	}

	public static String getWeekDayName(int iDay) {//��������õ�ǰ����
		return vecStrWeekDayNames[iDay];//�����ַ������� ��[]
	}
	
	public static int getWeekDay(int index, int iFirstDayOfWeek) {
		//��������õ������ڼ�
		int iWeekDay = -1;//iWeekDay��ʼ��ֵΪ-1��Ϊ���������������ݼ�-1

		if (iFirstDayOfWeek == Calendar.MONDAY) {//��������յ�һ��Ϊ��һ
			iWeekDay = index + Calendar.MONDAY;//�����������ڷ�ΧΪ��һ����������������һ��������ĳһ��
			
			if (iWeekDay > Calendar.SATURDAY)//����������ڴ��������
				iWeekDay = Calendar.SUNDAY;//����������
		}

		if (iFirstDayOfWeek == Calendar.SUNDAY) {//��������յ�һ��Ϊ�����
			iWeekDay = index + Calendar.SUNDAY;//�����������ڷ�ΧΪ����������һ��ݵ�������
		}

		return iWeekDay;//���ص�ǰ���ڼ�
	}
}
