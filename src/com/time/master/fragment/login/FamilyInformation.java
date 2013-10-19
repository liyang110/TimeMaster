package com.time.master.fragment.login;

import com.time.master.R;
import com.time.master.activity.MainActivity;
import com.time.master.view.BasicTextView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * ���Ƽ�ͥ��Ϣҳ��
 * 
 * @author Desmond
 * 
 */

public class FamilyInformation extends Fragment implements OnClickListener{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ��ȡ��ť
		View layout = inflater.inflate(R.layout.page_family_information,container,false);
		BasicTextView button=(BasicTextView)layout.findViewById(R.id.family_btnEnd);
		button.setOnClickListener(this);
		return layout;
	}

	@Override
	public void onClick(View v) {
		// �����ť�¼�
		switch (v.getId()) {
		case R.id.family_btnEnd:
			MainActivity activity=(MainActivity)getActivity();
			activity.showNext(new UnitInformation(),R.id.mainlayout);
			break;
		}
	}
}
