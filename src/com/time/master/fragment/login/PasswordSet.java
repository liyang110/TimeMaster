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
 * �������ý���
 * 
 * @author Desmond
 * 
 */
public class PasswordSet extends Fragment implements OnClickListener{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//���ð�ť�����¼�
		View layout = inflater.inflate(R.layout.page_password_set,container,false);
		BasicTextView button=(BasicTextView)layout.findViewById(R.id.pwset_btnEnd);
		button.setOnClickListener(this);
		return layout;
	}

	@Override
	public void onClick(View v) {
		//��ť����¼�
		switch (v.getId()) {
		case R.id.pwset_btnEnd:
			MainActivity activity=(MainActivity)getActivity();
			activity.showNext(new PersonalInformation(),R.id.mainlayout);
			break;
		}
	}
}
