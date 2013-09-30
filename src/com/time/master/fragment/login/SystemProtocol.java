package com.time.master.fragment.login;

/**
 * ϵͳЭ�����
 * 
 * @author Desmond
 * 
 */
import java.util.HashMap;

import com.time.master.R;
import com.time.master.activity.MainActivity;
import com.time.master.view.BasicTextView;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class SystemProtocol extends Fragment implements OnClickListener{
	BasicTextView tvagree,tvdisagree;
	
	HashMap<Integer, Boolean> viewStatus = new HashMap<Integer, Boolean>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ����textview����
		View layout = inflater.inflate(R.layout.page_system_protocol,container,false);
		 tvagree=(BasicTextView)layout.findViewById(R.id.protocol_tvAgree);
		 tvdisagree=(BasicTextView)layout.findViewById(R.id.protocol_tvDisagree);
		
		 viewStatus.put(tvagree.getId(), false);
		 viewStatus.put(tvdisagree.getId(), false);
		 
		 tvagree.setOnClickListener(this);
		 tvdisagree.setOnClickListener(this);
		return layout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//����¼�����ͬ��Э�飬�޷���ת
	case R.id.protocol_tvDisagree:
		if(viewStatus.get(R.id.protocol_tvDisagree)) {
			viewStatus.put(R.id.protocol_tvDisagree, true);
			Drawable drawable=getResources().getDrawable(R.drawable.checkbox);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tvdisagree.setCompoundDrawables(drawable, null, null, null);
		}
		else {
			viewStatus.put(R.id.protocol_tvDisagree, false);
			Drawable drawable=getResources().getDrawable(R.drawable.checkboxonclick);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tvdisagree.setCompoundDrawables(drawable, null, null, null);
		}
		break;
		//����¼���ͬ��Э�飬��ת����һ��fragment
	case R.id.protocol_tvAgree:
		if(viewStatus.get(R.id.protocol_tvAgree)) {
			viewStatus.put(R.id.protocol_tvAgree, true);
			break;
		}
		else {
			viewStatus.put(R.id.protocol_tvAgree, false);
			Drawable drawable=getResources().getDrawable(R.drawable.checkboxonclick);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tvagree.setCompoundDrawables(drawable, null, null, null);
			MainActivity activity=(MainActivity)getActivity();
			activity.showNext(new RegisterInformation(),R.id.mainlayout);
			
		}
		break;// TODO Auto-generated method stub
		}
	}
}
