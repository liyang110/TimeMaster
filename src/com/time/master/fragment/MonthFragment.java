/**
 * @ע�� ������
          ʵ���߼�������
   1�����ڻ��档
   2��
 */
package com.time.master.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import com.time.master.R;
import com.time.master.TimeMasterApplication;
import com.time.master.calendar.DateWidgetDayCell;
import com.time.master.calendar.DateWidgetDayHeader;
import com.time.master.calendar.DayStyle;
import com.time.master.tool.Constant;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * �½���
 * 
 * @author Desmond 
 * 
 */
public class MonthFragment extends Fragment {

	// �����������������
	private LinearLayout layContent = null;//���岼�����Բ���ʼ��
	private ArrayList<DateWidgetDayCell> days = new ArrayList<DateWidgetDayCell>();

	// ���ڱ���
	public static Calendar calStartDate = Calendar.getInstance();  //�õ�ÿ���¿�ʼ����
	private Calendar calToday = Calendar.getInstance(); // �õ����ն���
	private Calendar calCalendar = Calendar.getInstance(); //
	private Calendar calSelected = Calendar.getInstance();  //�õ���ѡ�еĵ�Ԫ�����

	// ��ǰ��������
	private int iMonthViewCurrentMonth = 0;  //��ǰ��
	private int iMonthViewCurrentYear = 0;   //��ǰ��
	private int iFirstDayOfWeek = Calendar.MONDAY;  //ÿ�ܴ���һ��ʼ

	private int Calendar_Width = 0;  //������
	private int Cell_Width = 0;   //��Ԫ���

	// ҳ��ؼ�
	TextView Top_Date = null; //ͷ���ı�
	Button btn_pre_month = null; //�¸��°�ť
	Button btn_next_month = null;  //�ϸ��°�ť
	TextView arrange_text = null;   // 
	LinearLayout mainLayout = null;  // ������ɫΪ��ɫ����
	LinearLayout arrange_layout = null;  // ��Ԫ�񲼾�

	// ����Դ
	ArrayList<String> Calendar_Source = null;
	Hashtable<Integer, Integer> calendar_Hashtable = new Hashtable<Integer, Integer>();
	Boolean[] flag = null;
	Calendar startDate = null;//��ǰ������һ��
	Calendar endDate = null; //��ǰ�������һ��
	int dayvalue = -1;

	String UserName = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainLayout = (LinearLayout) inflater.inflate(R.layout.month_layout,
				container, false);

		// �����Ļ��͸ߣ���Ӌ�����Ļ���ȷ��ߵȷݵĴ�С
		Calendar_Width = TimeMasterApplication.getInstance().getScreen_W();
		Cell_Width = Calendar_Width / 7 + 1;

		// �����ؼ��������¼�


		Top_Date = (TextView) mainLayout.findViewById(R.id.Top_Date);
		btn_pre_month = (Button) mainLayout.findViewById(R.id.btn_pre_month);//���°�ť
		btn_next_month = (Button) mainLayout.findViewById(R.id.btn_next_month); //���°�ť
		btn_pre_month.setOnClickListener(new Pre_MonthOnClickListener());
		btn_next_month.setOnClickListener(new Next_MonthOnClickListener());

		// ���㱾�������еĵ�һ��(һ�������µ�ĳ��)������������
		calStartDate = getCalendarStartDate();  //��ǰ������һ��
		mainLayout.addView(generateCalendarMain()); //monthFragement�²����ļ�
		DateWidgetDayCell daySelected = updateCalendar();

		if (daySelected != null)//����ձ�ѡ��
			daySelected.requestFocus();//ִ����Ӧ
		//ʵ����������������
		LinearLayout.LayoutParams Param1 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);


		ScrollView view = new ScrollView(this.getActivity());
		arrange_layout = createLayout(LinearLayout.VERTICAL);
		arrange_layout.setPadding(5, 2, 0, 0);
		arrange_text = new TextView(this.getActivity());
		mainLayout.setBackgroundColor(Color.WHITE);  //����ɫΪ��ɫ����
		arrange_text.setTextColor(Color.BLACK);
		arrange_text.setTextSize(18);
		arrange_layout.addView(arrange_text); //�� �Ĳ���


		startDate = GetStartDate();
		calToday = GetTodayDate();

		endDate = GetEndDate(startDate);
		view.addView(arrange_layout, Param1);//�������м��벼��
		mainLayout.addView(view);//�񲼾��м���ؼ�

		// �½��߳�
		new Thread() {
			@Override
			public void run() {
				int day = GetNumFromDate(calToday, startDate);

				if (calendar_Hashtable != null
						&& calendar_Hashtable.containsKey(day)) {
					dayvalue = calendar_Hashtable.get(day);
				}
			}

		}.start();
//�Ѹ����Ե���ɫ�����ڲ�����
		Constant.Calendar_WeekBgColor = this.getResources().getColor(
				R.color.Calendar_WeekBgColor);
		Constant.Calendar_DayBgColor = this.getResources().getColor(
				R.color.Calendar_DayBgColor);
		Constant.isHoliday_BgColor = this.getResources().getColor(
				R.color.isHoliday_BgColor);
		Constant.unPresentMonth_FontColor = this.getResources().getColor(
				R.color.unPresentMonth_FontColor);
		Constant.isPresentMonth_FontColor = this.getResources().getColor(
				R.color.isPresentMonth_FontColor);
		Constant.isToday_BgColor = this.getResources().getColor(
				R.color.isToday_BgColor);
		Constant.special_Reminder = this.getResources().getColor(
				R.color.specialReminder);
		Constant.common_Reminder = this.getResources().getColor(
				R.color.commonReminder);
		Constant.Calendar_WeekFontColor = this.getResources().getColor(
				R.color.Calendar_WeekFontColor);
		return mainLayout;
	}
	// �õ�ȫ��
	protected String GetDateShortString(Calendar date) {
		String returnString = date.get(Calendar.YEAR) + "/";
		returnString += date.get(Calendar.MONTH) + 1 + "/";
		returnString += date.get(Calendar.DAY_OF_MONTH);

		return returnString;
	}

	// �õ������������е����
	private int GetNumFromDate(Calendar now, Calendar returnDate) {
		Calendar cNow = (Calendar) now.clone();  //������
		Calendar cReturnDate = (Calendar) returnDate.clone();   //����
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);

		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;  //�����
		int index = millisecondsToDays(intervalMs);

		return index;
	}

//�ֱ���

	//�õ����

	private int millisecondsToDays(long intervalMs) {
		return Math.round((intervalMs / (1000 * 86400)));
	}
//����ʱ��
	private void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	// ���ɲ���
	private LinearLayout createLayout(int iOrientation) {
		LinearLayout lay = new LinearLayout(this.getActivity());
		lay.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		lay.setOrientation(iOrientation);

		return lay;
	}

	// ��������ͷ��
	private View generateCalendarHeader() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);  //ˮƽ����
		// layRow.setBackgroundColor(Color.argb(255, 207, 207, 205));

<<<<<<< HEAD
		for (int iDay = 0; iDay < 7; iDay++) { //����
			DateWidgetDayHeader day = new DateWidgetDayHeader(this.getActivity(), Cell_Width,
					35);
=======
		for (int iDay = 0; iDay < 7; iDay++) {
			DateWidgetDayHeader day = new DateWidgetDayHeader(
					this.getActivity(), Cell_Width, 35);
>>>>>>> c6668860d3c549eb3fbe02ed695310be2cf65344

			final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);  //����һ��ʼ  ��7��
			day.setData(iWeekDay); //��������
			layRow.addView(day); //���
		}

		return layRow;
	}

	// ������������
	private View generateCalendarMain() {
		layContent = createLayout(LinearLayout.VERTICAL); //��ֱ����
		// layContent.setPadding(1, 0, 1, 0);
		layContent.setBackgroundColor(Color.argb(255, 105, 105, 103)); //��ȫ��ʾ
		layContent.addView(generateCalendarHeader()); //���ͷ��
		days.clear();

		for (int iRow = 0; iRow < 6; iRow++) {
			layContent.addView(generateCalendarRow()); //�������6��ͷ������
		}

		return layContent;
	}

	// ���������е�һ�У���������
	private View generateCalendarRow() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);// ����ˮƽ�����Բ��֡�
		// �����е�ÿһ�еķ���Ϊ7����
		for (int iDay = 0; iDay < 7; iDay++) {
<<<<<<< HEAD
			DateWidgetDayCell dayCell = new DateWidgetDayCell(this.getActivity(), Cell_Width,
					Cell_Width);
			dayCell.setItemClick(mOnDayCellClick); //���õ���¼� 
			days.add(dayCell);
			layRow.addView(dayCell); //Ϊ�� ��Ӿ��� 
=======
			// �����ؼ���Ԫ�������ʵ�����������û����εķ������Լ����εĿ�͸ߡ�
			DateWidgetDayCell dayCell = new DateWidgetDayCell(
					this.getActivity(), Cell_Width, Cell_Width);
			dayCell.setItemClick(mOnDayCellClick);// ���õ���¼�
			days.add(dayCell);// �ѵ�Ԫ��ӵ����鼯���С�
			layRow.addView(dayCell);// �����鼯���е�Ԫ��ӵ������
>>>>>>> c6668860d3c549eb3fbe02ed695310be2cf65344
		}

		return layRow;
	}

	// ���õ������ںͱ�ѡ������
	private Calendar getCalendarStartDate() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);

		if (calSelected.getTimeInMillis() == 0) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}

		UpdateStartDateForMonth();
		return calStartDate;
	}

	// ���ڱ������ϵ����ڶ��Ǵ���һ��ʼ�ģ��˷���������������ڱ�����������ʾ������
	private void UpdateStartDateForMonth() {
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);  //��ǰ��
		iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR); //��ǰ��
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);  //��ǰ��
		calStartDate.set(Calendar.HOUR_OF_DAY, 0);
		calStartDate.set(Calendar.MINUTE, 0);
		calStartDate.set(Calendar.SECOND, 0);
		// update days for week
		UpdateCurrentMonthDisplay();
		int iDay = 0;
		int iStartDay = iFirstDayOfWeek;

		if (iStartDay == Calendar.MONDAY) { { //����һ��ʼ �õ����� �;��뱾������--���ɵõ�����
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}

		if (iStartDay == Calendar.SUNDAY) { //�����տ�ʼ �õ����� �;��뱾������--���ɵõ�����
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}

		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
		}
	}

	// ��������
	private DateWidgetDayCell updateCalendar() {
		DateWidgetDayCell daySelected = null;
<<<<<<< HEAD
		boolean bSelected = false;  //Ĭ��ûѡ��
		final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
		final int iSelectedYear = calSelected.get(Calendar.YEAR); //��ѡ��������
		final int iSelectedMonth = calSelected.get(Calendar.MONTH);  //��ѡ��������
		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH); //��ѡ��������
		calCalendar.setTimeInMillis(calStartDate.getTimeInMillis()); //����������ǰ

		for (int i = 0; i < days.size(); i++) {
			final int iYear = calCalendar.get(Calendar.YEAR);  //�õ���Ԫ��������
			final int iMonth = calCalendar.get(Calendar.MONTH);  //��Ԫ��������
			final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);  //��Ԫ��������
			final int iDayOfWeek = calCalendar.get(Calendar.DAY_OF_WEEK); //��������
			DateWidgetDayCell dayCell = days.get(i);  //�����ڷ��ڶ�Ӧ��Ԫ����

			// �ж��Ƿ���
			boolean bToday = false; //��֤
=======
		boolean bSelected = false;// ��ǰû��ѡ�еĵ�Ԫ��
		final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
		// Field number for get and set indicating the year.
		final int iSelectedYear = calSelected.get(Calendar.YEAR);
		// Field number for get and set indicating(ָʾ������) the month.
		final int iSelectedMonth = calSelected.get(Calendar.MONTH);
		// Field number for get and set indicating the day of the month.
		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
		// Sets the time of this Calendar.(the time of this Calendar.)
		calCalendar.setTimeInMillis(calStartDate.getTimeInMillis());
		// array=object[60],forѭ����������ø���ʱ��
		for (int i = 0; i < days.size(); i++) {
			final int iYear = calCalendar.get(Calendar.YEAR);
			final int iMonth = calCalendar.get(Calendar.MONTH);
			final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);
			// Field number for get and set indicating the day of the week.
			final int iDayOfWeek = calCalendar.get(Calendar.DAY_OF_WEEK);
			DateWidgetDayCell dayCell = days.get(i);//���ػ�õ�Ԫ��

			// �ж��Ƿ���
			boolean bToday = false;//�� ���������Ϊ�����ʱ�� ��������bToday = true��
>>>>>>> c6668860d3c549eb3fbe02ed695310be2cf65344

			if (calToday.get(Calendar.YEAR) == iYear) {   // calToday�������Ƿ�Ϊ����
				if (calToday.get(Calendar.MONTH) == iMonth) {   //�Ƿ�Ϊ����
					if (calToday.get(Calendar.DAY_OF_MONTH) == iDay) {  //�Ƿ�Ϊ����
						bToday = true; //�������������������Ϊ���� ����true
					}
				}
			}

			// check holiday
			boolean bHoliday = false;//��ȡ�����ڵ������������죬���ж�Ϊholiday
			if ((iDayOfWeek == Calendar.SATURDAY)
					|| (iDayOfWeek == Calendar.SUNDAY))  //������������Ϊ���������� ��ô����Ϊ����
				bHoliday = true;
<<<<<<< HEAD
			if ((iMonth == Calendar.JANUARY) && (iDay == 1)) // ����Ϊ1��1��Ϊ����
=======
			if ((iMonth == Calendar.JANUARY) && (iDay == 1))//��ȡ�����ڵ���1��1�գ����ж�Ϊholiday
>>>>>>> c6668860d3c549eb3fbe02ed695310be2cf65344
				bHoliday = true;

			// �Ƿ�ѡ��
			bSelected = false;
<<<<<<< HEAD

			if (bIsSelection) //����п��ܱ�ѡ��
=======
            //��ѡ�е�������Ϊ��ǰ������  ���жϱ�ѡ��
			if (bIsSelection)
>>>>>>> c6668860d3c549eb3fbe02ed695310be2cf65344
				if ((iSelectedDay == iDay) && (iSelectedMonth == iMonth)
						&& (iSelectedYear == iYear)) {   //���ձ�ѡ��
					bSelected = true;
				}

			dayCell.setSelected(bSelected);//���õ�Ԫ��ѡ��

			// �Ƿ��м�¼

			boolean hasRecord = true;//�жϼ�¼�����Ƿ�Ϊ�գ���Ϊ����Ϊ�м�¼��


			if (flag != null && flag[i] == true && calendar_Hashtable != null
					&& calendar_Hashtable.containsKey(i)) {
				// hasRecord = flag[i];
				hasRecord = Calendar_Source.get(calendar_Hashtable.get(i))
						.contains(UserName);  // ��־�����Ҳ�Ϊ�� ��¼���ݺ�i�γɼ�ֵ�� ��¼���ݰ����û���
			}

			if (bSelected)
				daySelected = dayCell; //Ϊ��ѡ�и�����

			dayCell.setData(iYear, iMonth, iDay, bToday, bHoliday,
					iMonthViewCurrentMonth, hasRecord); //������������ 

			calCalendar.add(Calendar.DAY_OF_MONTH, 1); //Ϊ������� ��
		}

		layContent.invalidate();

		return daySelected;
	}

	// ����������������ʾ������
	private void UpdateCurrentMonthDisplay() {
		String date = calStartDate.get(Calendar.YEAR) + "��"
				+ (calStartDate.get(Calendar.MONTH) + 1) + "��";
		Top_Date.setText(date); //�����ı�����
	}

	// ������°�ť�������¼�
	class Pre_MonthOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			arrange_text.setText(""); //�� �����ı�
			calSelected.setTimeInMillis(0);
			iMonthViewCurrentMonth--; //���һ�� �·ݼ�һ

			if (iMonthViewCurrentMonth == -1) { //�����·ݶ��� ���һ
				iMonthViewCurrentMonth = 11;
				iMonthViewCurrentYear--;
			}

			calStartDate.set(Calendar.DAY_OF_MONTH, 1); //������
			calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth); //������
			calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);  //������
			calStartDate.set(Calendar.HOUR_OF_DAY, 0);
			calStartDate.set(Calendar.MINUTE, 0);
			calStartDate.set(Calendar.SECOND, 0);
			calStartDate.set(Calendar.MILLISECOND, 0);
			UpdateStartDateForMonth(); //�����·ݿ�ʼ����

			startDate = (Calendar) calStartDate.clone(); //��calStartDate��ͬ
			endDate = GetEndDate(startDate); //��startDate��Ӧ

			// �½��߳�
			new Thread() {
				@Override
				public void run() {

					int day = GetNumFromDate(calToday, startDate);//�õ��������

					if (calendar_Hashtable != null
							&& calendar_Hashtable.containsKey(day)) {
						dayvalue = calendar_Hashtable.get(day);
					}
				}
			}.start();  //�ϸ���������µ�����

			updateCalendar(); //��������
		}

	}

	// ������°�ť�������¼�
	class Next_MonthOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			arrange_text.setText("");
			calSelected.setTimeInMillis(0);
			iMonthViewCurrentMonth++;   //���һ�� �·ݼ�һ

			if (iMonthViewCurrentMonth == 12) {
				iMonthViewCurrentMonth = 0;
				iMonthViewCurrentYear++;  //�����·ݶ��� ���һ
			}

			calStartDate.set(Calendar.DAY_OF_MONTH, 1);
			calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
			calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
			UpdateStartDateForMonth();

			startDate = (Calendar) calStartDate.clone();
			endDate = GetEndDate(startDate);

			// �½��߳�
			new Thread() {
				@Override
				public void run() {
					int day = 5;

					if (calendar_Hashtable != null
							&& calendar_Hashtable.containsKey(day)) {
						dayvalue = calendar_Hashtable.get(day);
					}
				}
			}.start();

			updateCalendar();
		}
	}

	// ��������������¼�
	private DateWidgetDayCell.OnItemClick mOnDayCellClick = new DateWidgetDayCell.OnItemClick() {
		public void OnClick(DateWidgetDayCell item) {
			// Sets the time of this Calendar.
			calSelected.setTimeInMillis(item.getDate().getTimeInMillis());
<<<<<<< HEAD
			int day = GetNumFromDate(calSelected, startDate); //��ѡ����������ʼ�����������

=======
			int day = GetNumFromDate(calSelected, startDate);
			// Returns true if this Hashtable contains the specified object as a
			// key of one of the key/value pairs.
			// �����������ָ������Ϊ������������ָ���������
>>>>>>> c6668860d3c549eb3fbe02ed695310be2cf65344
			if (calendar_Hashtable != null
					&& calendar_Hashtable.containsKey(day)) {
				// ��ָ����ֵ��ʾ����
				arrange_text.setText(Calendar_Source.get(calendar_Hashtable
						.get(day)));  //Ϊ��Ԫ���ı���������
			} else {
				// ������ʾ�������ݼ�¼��
				arrange_text.setText("�������ݼ�¼");
			}

			item.setSelected(true); //��Ԫ����Ա�ѡ��
			updateCalendar();
		}
	};
	//�õ���������
	public Calendar GetTodayDate() {
		Calendar cal_Today = Calendar.getInstance();
		cal_Today.set(Calendar.HOUR_OF_DAY, 0);
		cal_Today.set(Calendar.MINUTE, 0);
		cal_Today.set(Calendar.SECOND, 0);
		cal_Today.setFirstDayOfWeek(Calendar.MONDAY);

		return cal_Today;
	}

	// �õ���ǰ�����еĵ�һ��
	public Calendar GetStartDate() {
		int iDay = 0;
		Calendar cal_Now = Calendar.getInstance();
		cal_Now.set(Calendar.DAY_OF_MONTH, 1); //ֻ������
		cal_Now.set(Calendar.HOUR_OF_DAY, 0);
		cal_Now.set(Calendar.MINUTE, 0);
		cal_Now.set(Calendar.SECOND, 0);
		cal_Now.setFirstDayOfWeek(Calendar.MONDAY);

		iDay = cal_Now.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY; 

		if (iDay < 0) {
			iDay = 6;
		}

		cal_Now.add(Calendar.DAY_OF_WEEK, -iDay);  //�õ����ں;��뱾������--���ɵõ�����

		return cal_Now;
	}

	public Calendar GetEndDate(Calendar startDate) {
		// Calendar end = GetStartDate(enddate);
		Calendar endDate = Calendar.getInstance();
		endDate = (Calendar) startDate.clone();  
		endDate.add(Calendar.DAY_OF_MONTH, 41);  //��startDate�õ�����endDate���� ����λ�ӵ�42��
		return endDate;
	}

}
