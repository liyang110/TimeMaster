

package com.time.master.calendar;

import java.util.Calendar;

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
	/**
	 * �жϵ��������ڼ���
	 * @param index  ָ������  ����ָ����һ����ַ�����ڼ���
	 * @param iFirstDayOfWeek  ���ܵĵ�һ��
	 * @return
	 */
	public static int getWeekDay(int index, int iFirstDayOfWeek) {
<<<<<<< HEAD

		int iWeekDay = -1;  //����һ���õ�һλ

		if (iFirstDayOfWeek == Calendar.MONDAY) {  //���һ�ܵĵ�һ������һ
			iWeekDay = index + Calendar.MONDAY;

			
			if (iWeekDay > Calendar.SATURDAY)//����������ڴ��������
				iWeekDay = Calendar.SUNDAY;//����������
		}


		if (iFirstDayOfWeek == Calendar.SUNDAY) {  //���һ�ܵĵ�һ��������
			iWeekDay = index + Calendar.SUNDAY;

=======

		
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
>>>>>>> c6668860d3c549eb3fbe02ed695310be2cf65344
		}
        
		return iWeekDay;//�����ܼ���

	}
}
