package com.time.master.fragment.date;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.time.master.R;
import com.time.master.TimeMasterApplication;
import com.time.master.dialog.DurationTimeDialogFragment;
import com.time.master.dialog.HumanDialogFragment;
import com.time.master.dialog.LocationDialogFragment;
import com.time.master.dialog.RepeatDialogFragment;
import com.time.master.dialog.TimeDialogFragment;
import com.time.master.dialog.WheelDialogFragment;
import com.time.master.interfacer.WheelResultInterface;
import com.time.master.model.CacheModel;
import com.time.master.tool.ChineseCalendar;
import com.time.master.view.BasicEditText;
import com.time.master.view.BasicTextView;

import android.R.string;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.webkit.DateSorter;

/**
 * ��---����ѡ�����
 * 
 * @author Desmond
 * 
 */
public class DateDetailCreateFragment extends Fragment implements OnTouchListener,android.view.View.OnClickListener{
	WheelDialogFragment dateFragment, locationFragment, humanFragment,planTimePeroidFragment;
	DialogFragment repeatFragment;
	BasicEditText dateSelector,//��ʼʱ�������
	              locationSelector,
	              humanSelector,
	              planPeroidSelector,
	              lengthSelector,
	              endSelector;//����ʱ�������
	BasicTextView dateRepeat;

	BasicTextView 	startClick,//��ʼ��ť
	                tvdate, // ���� /���� ��ť
			        tvduration;// ռ��/�ڼ� ��ť

	private ChineseCalendar startChineseDate,//��ʼʱ��
	                        endChineseDate;//����ʱ��
	
	private Handler stepTimeHandler;
	private Runnable mTicker;
	long startTime = 0;
	boolean flag = true;
	
	HashMap<Integer, Boolean> viewStatus = new HashMap<Integer, Boolean>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.date_detail_create_page, container, false);

		dateSelector = (BasicEditText) layout.findViewById(R.id.plan_time_start);
		dateSelector.setInputType(InputType.TYPE_NULL);
		dateSelector.setOnTouchListener(this);

	    locationSelector = (BasicEditText) layout.findViewById(R.id.plan_location);
		locationSelector.setInputType(InputType.TYPE_NULL);
		locationSelector.setOnTouchListener(this);

		humanSelector = (BasicEditText) layout.findViewById(R.id.plan_human);
		humanSelector.setInputType(InputType.TYPE_NULL);
		humanSelector.setOnTouchListener(this);

		lengthSelector = (BasicEditText) layout.findViewById(R.id.plan_length);
		
		endSelector = (BasicEditText) layout.findViewById(R.id.plan_time_end);
		
		startClick = (BasicTextView) layout.findViewById(R.id.plan_start);
		startClick.setOnClickListener(this);


		planPeroidSelector=(BasicEditText)layout.findViewById(R.id.plan_length);
		planPeroidSelector.setInputType(InputType.TYPE_NULL);
		planPeroidSelector.setOnTouchListener(this);
		
		dateRepeat=(BasicTextView)layout.findViewById(R.id.plan_repeat);

		dateRepeat.setOnClickListener(this);

		tvdate = (BasicTextView) layout.findViewById(R.id.plan_model);
		tvdate.setOnClickListener(this);

		viewStatus.put(tvdate.getId(), false);

		// tvdate.setBackgroundColor(R.color.dateforcolor);
		String dateString = (String) getText(R.string.date_layout_plan_model_1);
		SpannableStringBuilder datestyle = new SpannableStringBuilder(
				dateString);
		datestyle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		datestyle.setSpan(new ForegroundColorSpan(Color.WHITE), 3, 5,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tvdate.setText(datestyle);
		
		tvduration = (BasicTextView) layout.findViewById(R.id.plan_time_period);
		tvduration.setOnClickListener(this);
		viewStatus.put(tvduration.getId(), false);
		// tvduration.setBackgroundColor(R.color.dateforcolor);
		String durationString = (String) getText(R.string.date_plan_time_period_1);
		SpannableStringBuilder durationstyle = new SpannableStringBuilder(
				durationString);
		durationstyle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		durationstyle.setSpan(new ForegroundColorSpan(Color.WHITE), 3, 5,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tvduration.setText(durationstyle);
		return layout;
	}

	void showDialog(DialogFragment dialogFragment) {

		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		dialogFragment.show(ft, "dialog");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			switch (v.getId()) {
			case R.id.plan_time_start:
				if (dateFragment == null) {
					dateFragment = new TimeDialogFragment();
					/** �趨��ȡ�������ݽӿ� */
					dateFragment.setWheelInterface(new WheelResultInterface() {

						@Override
						public void getResult(String result) {
							dateSelector.setText(result);
							CacheModel model=TimeMasterApplication.getInstance().getCacheModel();
							startChineseDate=model.currentTime;
							model.startTime=startChineseDate;
						}
					});
				}
				showDialog(dateFragment);
				break;
			case R.id.plan_location:
				if (locationFragment == null) {
					locationFragment = new LocationDialogFragment();
					/** �趨��ȡ�������ݽӿ� */
					locationFragment
							.setWheelInterface(new WheelResultInterface() {

								@Override
								public void getResult(String result) {
									locationSelector.setText(result);
								}
							});
				}
				showDialog(locationFragment);
				break;
			case R.id.plan_human:
				if (humanFragment == null) {
					humanFragment = new HumanDialogFragment();
					/** �趨��ȡ�������ݽӿ� */
					humanFragment.setWheelInterface(new WheelResultInterface() {

						@Override
						public void getResult(String result) {
							humanSelector.setText(result);
						}
					});
				}
				showDialog(humanFragment);
				break;
			case R.id.plan_length:
				if(planTimePeroidFragment==null){
					planTimePeroidFragment=new DurationTimeDialogFragment();
					planTimePeroidFragment.setWheelInterface(new WheelResultInterface() {
						
						@Override
						public void getResult(String result) {
							// TODO Auto-generated method stub
							planPeroidSelector.setText(result);
						}
					});
				}
				showDialog(planTimePeroidFragment);
			default:
				break;
			}
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.plan_repeat:
			repeatFragment = new RepeatDialogFragment();
			repeatFragment.setShowsDialog(true);
			showDialog(repeatFragment);
			break;
		case R.id.plan_model:
			CacheModel model=TimeMasterApplication.getInstance().getCacheModel();
			if (viewStatus.get(R.id.plan_model)) {
				viewStatus.put(R.id.plan_model, false);
				dateSelector.setText(getChineseDateString(model.startTime));
				endSelector.setText(getChineseDateString(model.endTime));
			} else {
				viewStatus.put(R.id.plan_model, true);
				dateSelector.setText(getCountdownDateString(model.startTime));
				endSelector.setText(getCountdownDateString(model.endTime));
			}
//			} else {
//				viewStatus.put(R.id.plan_model,true);
//
//			}

			break;
		case R.id.plan_time_period:
			if (viewStatus.get(R.id.plan_time_period)) {

				viewStatus.put(R.id.plan_time_period, false);
			} else {
				viewStatus.put(R.id.plan_time_period, true);
			}
//			else {
//				viewStatus.put(R.id.plan_time_period,true);
//
//			}
			break;
		case R.id.plan_start:

			if (flag) {
				flag = false;
				lengthSelector.setText("00:00:00");
				startClick.setText("��ͣ");
				stepTimeHandler = new Handler();
				/**
				 * ʱ����: System.currentTimeMillis()��1970��1��1�� UTC��Э������ʱ�������ڵĺ�����
				 * ��¼��ʼʱ�� startTime��Ȼ��ÿ�λص�ʱ����ȡ��ǰʱ��  currentTime��
				 * �����ֵ = currentTime - startTime������ȡ��ǰʱ�䡣
				 * ���ص����Ժ���Ϊ��λ�ĵ�ǰʱ�䣬��ʱ���֣��룬�ĵ�λ��ת��Ϊ����
				 */
				startTime = System.currentTimeMillis();
				mTicker = new Runnable() {
					public void run() {
						String content = showTimeCount(System
								.currentTimeMillis() - startTime);
						lengthSelector.setText(content);
						/**
						 * ʱ������SystemClock.uptimeMillis()�ӿ��������ڵĺ�����
						 */
						long now = SystemClock.uptimeMillis();
						long next = now + (1000 - now % 1000);
						//// ��ָ����ʱ�䣨uptimeMillis��ִ��Runnable���� 
						stepTimeHandler.postAtTime(mTicker, next);
					}
				};
				mTicker.run();
			} else {
				flag = true;
				startClick.setText("����");
				stepTimeHandler.removeCallbacks(mTicker);//ɾ�����е���δִ�е��̶߳���
				String text=addTime(System.currentTimeMillis()-startTime);
				endSelector.setText(text);
			}
			break;
		}
		}
	//ʱ������������ֻ�ܵ�99Сʱ
	public String showTimeCount(long time) {
		if (time >= 36000000) {
			return "00:00:00";
		}
		String timeCount = "";
		long hourc = time / 3600000;
		String hour = "0" + hourc;
		/**
		 * substring():�����ڽ�ȡ�ַ���
		 * ������start:��ȡ�ִ��Ŀ�ʼλ��
		 *       end:��ȡ�ִ��Ľ���λ��
		 */
		hour = hour.substring(hour.length() - 2, hour.length());

		long minutec = (time - hourc * 360000) / (60000);
		String minute = "0" + minutec;
		minute = minute.substring(minute.length() - 2, minute.length());

		long secc = (time - hourc * 3600000 - minutec * 60000) / 1000;
		String sec = "0" + secc;
		sec = sec.substring(sec.length() - 2, sec.length());

		timeCount = hour + ":" + minute + ":" + sec;

		return timeCount;
	}
	//ʱ���
	Calendar calendar;
	public String addTime(long time){
		String timeAdd="";
		/*
		 *1. dateSelector ��ʱ��
		 *2. ʱ����꣬�£��գ�ʱ���֣���
		 *3. �����ж�;��ӽ�λ
		 *4. �õ�end����
		 */
	   
		calendar=Calendar.getInstance();
		long year=calendar.get(Calendar.YEAR);
		long month =calendar.get(Calendar.MONTH);
		long day = calendar.get(Calendar.DAY_OF_MONTH);
		long hour =calendar.get(Calendar.HOUR_OF_DAY);
		long min = calendar.get(Calendar.MINUTE);
		String res=year+"/"+month+"/"+day+" "+hour+":"+min;
		dateSelector.setText(res);
		
		
		long hourc = time/3600000;
		String hou = "0"+hourc;
		hou = hou.substring(hou.length()-2, hou.length());
		
		
		long minutec = (time-hourc*360000)/(60000);
		String minute="0"+minutec;
		minute = minute.substring(minute.length()-2, minute.length());
		
		
		long secc = (time-hourc*3600000-minutec*60000)/1000;
		String sec = "0"+secc;
		sec = sec.substring(sec.length()-2, sec.length());
		
		long endMin =min+minutec;
		long endHour=hourc+hour;
		long endDay =day;
		long endMon=month;
		long endYear=year;
		if(secc==30){
			endMin++;
		}
		timeAdd=endYear+"/"+endMon+"/"+endDay+" "+endHour+":"+endMin;
		return timeAdd;
		
	}
	
	private String getChineseDateString(ChineseCalendar date) {
		if(date==null)
			date=new ChineseCalendar(new Date());
		int minute=date.get(ChineseCalendar.MINUTE);
		return date.get(ChineseCalendar.YEAR)+"/"
	            +(date.get(ChineseCalendar.MONTH)+1)+"/"
				+date.get(ChineseCalendar.DAY_OF_MONTH)
				+"  "+date.get(ChineseCalendar.HOUR_OF_DAY)+":"
				+(minute<10?"0"+minute:minute);
	}
	
	private String getCountdownDateString(ChineseCalendar date){
		if(date==null)
			return "0��  00:00";
		else{
			StringBuffer stringB=new StringBuffer();
			long now=new Date().getTime(),
			future=date.getTime().getTime(),
			between=future-now;
			if(between<0)
				stringB.append("-");
			between=between>0?between:-between;
			long day = between / (24 * 60 * 60 * 1000);
	        long hour = (between / (60 * 60 * 1000) - day * 24);
	        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
	        //day + "��" + hour + "Сʱ" + min + "��" + s + "��" + ms;
	        stringB.append(day).append("��  ").append(hour<10?"0"+hour:hour).append(":").append(min<10?"0"+min:min);
	        return stringB.toString();
		}
	}
}


