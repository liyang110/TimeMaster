package com.time.master.activity;

import com.time.master.R;
import com.time.master.R.layout;
import com.time.master.R.menu;
import com.time.master.fragment.GenerationFragment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabWidget;

public class FrameActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_main);
        TabHost tabHost=(TabHost)this.findViewById(R.id.main_tab);
        tabHost.setup();
        
        tabHost.addTab(tabHost.newTabSpec("generation").setIndicator(this.getResources().getString(R.string.generation)).setContent(R.id.generation_fragment));
        tabHost.addTab(tabHost.newTabSpec("year").setIndicator(this.getResources().getString(R.string.year)).setContent(R.id.year_fragment));
        tabHost.addTab(tabHost.newTabSpec("month").setIndicator(this.getResources().getString(R.string.month)).setContent(R.id.month_fragment));
        tabHost.addTab(tabHost.newTabSpec("week").setIndicator(this.getResources().getString(R.string.week)).setContent(R.id.week_fragment));
        tabHost.addTab(tabHost.newTabSpec("date").setIndicator(this.getResources().getString(R.string.date)).setContent(R.id.date_fragment));
        tabHost.addTab(tabHost.newTabSpec("list").setIndicator(this.getResources().getString(R.string.list)).setContent(R.id.issue_list_fragment));
        tabHost.addTab(tabHost.newTabSpec("new").setIndicator(this.getResources().getString(R.string.new_item)).setContent(R.id.new_issue_fragment));
        
        tabHost.setCurrentTabByTag("generation");
        
        TabWidget tabWidget = tabHost.getTabWidget();  
        // ��ǩ�ĸ���  
        int count = tabWidget.getChildCount();  
        // ��ȡ�ֻ���Ļ�Ŀ���  
        DisplayMetrics displayMetrics = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);  
        int screenWidth = displayMetrics.widthPixels;  
        int screenheight = displayMetrics.heightPixels;  
		if (count >= 4) {
			for (int i = 0; i < count; i++) {
				// ����ÿ����ǩ�Ŀ��ȣ�Ϊ��Ļ��1/4
				tabWidget.getChildTabViewAt(i).setMinimumWidth(
						(screenWidth) / 4);
			}
		} 
    }

//    TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
//    	
//    	FragmentManager fm= FrameActivity.this.getSupportFragmentManager();
//    	AndroidFragment androidFragment = (GenerationFragment) fm.findFragmentById(arg0)findFragmentByTag("android");
//        AppleFragment appleFragment = (AppleFragment) fm.findFragmentByTag("apple");
//    	FragmentTransaction ft = fm.beginTransaction();
//		@Override
//		public void onTabChanged(String tabId) {
//			// TODO Auto-generated method stub
//			
//		}
//    	
//    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}