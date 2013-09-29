package com.time.master.dialog;
/**
 * ����ʱ�����
 * @author ZhouYongJian
 *
 */
import com.time.master.R;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;


public  class WorldTimeDialogFragment extends android.support.v4.app.DialogFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_FRAME,android.R.style.Theme_Light);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setCanceledOnTouchOutside(true);//���dialog�������򣬹ر�dialog
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);  //�˴���������dialog��ʾ��λ��  
        window.setWindowAnimations(R.style.wheelAnimation);  //��Ӷ��� 
        WindowManager.LayoutParams para=(WindowManager.LayoutParams)window.getAttributes();
        para.height=LayoutParams.WRAP_CONTENT;
        para.width=LayoutParams.MATCH_PARENT;
        window.setAttributes(para);
        window.clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        
        View layout=inflater.inflate(R.layout.world_time_layout, container, false);
        View context=layout.findViewById(R.id.worldtime);
		return layout;
	}

}
