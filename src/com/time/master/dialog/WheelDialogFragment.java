package com.time.master.dialog;


import com.time.master.interfacer.WheelResultInterface;

import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * wheelDialog��������в�������
 * @author Desmond
 *
 */
public abstract class WheelDialogFragment extends DialogFragment {

	protected EditText editText;
	protected TextView confirm;
	
	private WheelResultInterface wheelInterface;
	
	public void setWheelInterface(WheelResultInterface wheelInterface) {
		this.wheelInterface = wheelInterface;
	}
	
	/**
	 * ������onCreateView�б�����
	 * ��ʼ���ؼ�����
	 */
	protected void superInit(){
        
		if(editText!=null){
		    editText.setInputType(InputType.TYPE_NULL);
		    editText.setText(getSelectedString());
		}else{
			return;
		}
        
		if(confirm!=null&&wheelInterface!=null){
			confirm.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					wheelInterface.getResult(editText.getEditableText().toString());
					WheelDialogFragment.this.dismiss();
				}
			});
		}
	}
	
	abstract protected String getSelectedString();
}
