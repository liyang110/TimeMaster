package com.time.master.fragment;

import java.util.Calendar;

import com.time.master.R;
import com.time.master.fragment.DateFragment.DateModel;
import com.time.master.wheel.adapters.ArrayWheelAdapter;
import com.time.master.wheel.adapters.NumericWheelAdapter;
import com.time.master.wheel.widget.OnWheelScrollListener;
import com.time.master.wheel.widget.UIWheelView;
import com.time.master.wheel.widget.WheelView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MonthFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout=inflater.inflate(R.layout.month_layout, container, false);
		editText=(EditText)layout.findViewById(R.id.edit_location);
		model=new DateModel();
		model.province=static_location_1[static_location_1.length/2];
		model.city=static_location_2[static_location_2.length/2];
		model.district=static_location_3[static_location_3.length/2];
		model.area=static_location_4[static_location_4.length/2];
		editText.setText(getLocationString());
		
		location1 = (UIWheelView) layout.findViewById(R.id.location_1);
		location1Adapter =new ArrayWheelAdapter<String>(this.getActivity(), static_location_1);
		location1Adapter.setItemResource(R.layout.wheel_location_text_item);
		location1Adapter.setItemTextResource(R.id.location_text);
        location1.setViewAdapter(location1Adapter);
        location1.setRightLineWidth(0);
        location1.setBackground(R.drawable.wheel_bg_without_frame);
        location1.setVisibleItems(11);
        location1.setCurrentItem(static_location_1.length/2);
        location1.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {

			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				model.province=static_location_1[wheel.getCurrentItem()];
				editText.setText(getLocationString());
			}
		});
        
		location2 = (UIWheelView) layout.findViewById(R.id.location_2);
		location2Adapter =new ArrayWheelAdapter<String>(this.getActivity(), static_location_2);
		location2Adapter.setItemResource(R.layout.wheel_location_text_item);
		location2Adapter.setItemTextResource(R.id.location_text);
        location2.setViewAdapter(location2Adapter);
        location2.setRightLineWidth(0);
        location2.setBackground(R.drawable.wheel_bg_without_frame);
        location2.setVisibleItems(11);
        location2.setCurrentItem(static_location_1.length/2);
        location2.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				model.city=static_location_2[wheel.getCurrentItem()];
				editText.setText(getLocationString());
				
			}
		});
        
		location3 = (UIWheelView) layout.findViewById(R.id.location_3);
		location3Adapter =new ArrayWheelAdapter<String>(this.getActivity(), static_location_3);
		location3Adapter.setItemResource(R.layout.wheel_location_text_item);
		location3Adapter.setItemTextResource(R.id.location_text);
        location3.setViewAdapter(location3Adapter);
        location3.setRightLineWidth(0);
        location3.setBackground(R.drawable.wheel_bg_without_frame);
        location3.setVisibleItems(11);
        location3.setCurrentItem(static_location_1.length/2);       
        location3.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				model.district=static_location_3[wheel.getCurrentItem()];
				editText.setText(getLocationString());
				
			}
		});
        
		location4 = (UIWheelView) layout.findViewById(R.id.location_4);
		location4Adapter =new ArrayWheelAdapter<String>(this.getActivity(), static_location_4);
		location4Adapter.setItemResource(R.layout.wheel_location_text_item);
		location4Adapter.setItemTextResource(R.id.location_text);
        location4.setViewAdapter(location4Adapter);
        location4.setRightLineWidth(0);
        location4.setBackground(R.drawable.wheel_bg_without_frame);
        location4.setVisibleItems(11);
        location4.setCurrentItem(static_location_1.length/2);
        location4.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				model.area=static_location_4[wheel.getCurrentItem()];
				editText.setText(getLocationString());
				
			}
		});

		return layout;
	}
	
	class DateModel{
		String province,city,district,area;
	}
	DateModel model;
	EditText editText;
	Calendar calendar;
	UIWheelView location1,location2,location3,location4;
	ArrayWheelAdapter<String> location1Adapter,location2Adapter,location3Adapter,location4Adapter;

	UIWheelView year,month,day,hour,minute;
	NumericWheelAdapter yearAdapter,monthAdapter,dayAdapter,hourAdapter,minAdapter;
	
	private String getLocationString(){
		return model.province+"- "+model.city+"- "+model.district+"- "+model.area;
	}
//	static final String[] static_location_1={"����","ҽ��","����","����","���","����","��ҵ","��ҵ","����"};
//	static final String[] static_location_2={"�и�","����","˰��","�ʼ�","����","����","���","��Ժ","����"};
//	static final String[] static_location_3={"�и�","����","˰��","�ʼ�","����","����","���","��Ժ","����"};
//	static final String[] static_location_4={"�и�","����","˰��","�ʼ�","����","����","���","��Ժ","����"};
	static final String[] static_location_1={"�Ĵ�","�½�","����","����","����","�㽭","����","����","�㶫","����","����"};
	static final String[] static_location_2={"����","����","����","��","��ˮ","����","����","����","̨��","����","��ɽ"};
	static final String[] static_location_3={"¹��","걺�","����","��ͷ","����","����","��","ƽ��","����","̩˳","����"};
	static final String[] static_location_4={"�ֳ�","ʯ��","����","�彭","�㵴","����","����","�ƻ�","����","��ʯ","��ʯ"};
}
