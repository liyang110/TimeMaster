package com.time.master.fragment;

import com.time.master.R;
import com.time.master.dialog.HumanDialogFragment;
import com.time.master.dialog.LocationDialogFragment;
import com.time.master.dialog.TimeDialogFragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * "�½�"����
 * @author Desmond
 *
 */
public class NewIssueFragment extends Fragment implements OnTouchListener{
	
	DialogFragment dateFragment,
	          locationFragment,
	          humanFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout=inflater.inflate(R.layout.new_issue, container, false);

		EditText dateSelector=(EditText) layout.findViewById(R.id.date_selector);
		dateSelector.setInputType(InputType.TYPE_NULL);
		dateSelector.setOnTouchListener(this);
		
		EditText locationSelector=(EditText) layout.findViewById(R.id.location_selector);
		locationSelector.setInputType(InputType.TYPE_NULL);
		locationSelector.setOnTouchListener(this);
		
		EditText humanSelector=(EditText) layout.findViewById(R.id.human_selector);
		humanSelector.setInputType(InputType.TYPE_NULL);
		humanSelector.setOnTouchListener(this);
		
		return layout;
	}

//	@Override
//	public void onClick(View v) {
//
//	}
//
//	
//	@Override
//	public void onFocusChange(View v, boolean hasFocus) {
//		if(hasFocus){
//			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.id.date_selector:
//				if(dateFragment==null)
//					dateFragment=new TimeDialogFragment();
//				showDialog(dateFragment);
//				break;
//			case R.id.location_selector:
//				if(locationFragment==null)
//					locationFragment=new LocationDialogFragment();
//				showDialog(locationFragment);
//				break;
//			case R.id.human_selector:
//				if(humanFragment==null)
//					humanFragment=new HumanDialogFragment();
//				showDialog(humanFragment);
//				break;
//			default:
//				break;
//			}
//		}
//		
//	}  
      
    void showDialog(DialogFragment dialogFragment) {  
    	
        // DialogFragment.show() will take care of adding the fragment  
        // in a transaction.  We also want to remove any currently showing  
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
			case R.id.date_selector:
				if (dateFragment == null)
					dateFragment = new TimeDialogFragment();
				showDialog(dateFragment);
				break;
			case R.id.location_selector:
				if (locationFragment == null)
					locationFragment = new LocationDialogFragment();
				showDialog(locationFragment);
				break;
			case R.id.human_selector:
				if (humanFragment == null)
					humanFragment = new HumanDialogFragment();
				showDialog(humanFragment);
				break;
			default:
				break;
			}
		}
		return false;
	}

      
       
}
