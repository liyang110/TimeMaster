package com.time.master;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import com.time.master.database.TimeMasterHelper;
import com.time.master.model.CacheModel;


import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class TimeMasterApplication extends Application {

	/**
	 * ��Ļ��ȡ��߶�
	 */
	private int screen_width,screen_height;
	/**
	 * singleton instance of Application
	 */
	private static TimeMasterApplication instance;
	
	private TimeMasterHelper databaseHelper;
	/**
	 * ������ʾͼƬ��Դ����
	 * ϵͳ�ڴ治��ʱ�ᱻ������ͷ��ڴ�
	 */
	HashMap<Integer,SoftReference<Bitmap>> bitmapCache=new HashMap<Integer, SoftReference<Bitmap>>();
    
	/**
	 * ʱ�仺��
	 */
	private CacheModel cacheModel;
	/***���ݿ��Ѿ���ʼ��*/
	private boolean dataInitialized=true;
	
	/*** ��Ļ����ʽ 1�������� 2������� */
	private int screenMode=1;
	

	public static TimeMasterApplication getInstance(){
		return instance; 
	}
	
	@Override
	public void onCreate() {
		instance=this;
		DisplayMetrics displaymetrics = new DisplayMetrics();
		WindowManager window=(WindowManager)(this.getSystemService(Context.WINDOW_SERVICE));
		window.getDefaultDisplay().getMetrics(displaymetrics);
		screen_width=displaymetrics.widthPixels;
		screen_height=displaymetrics.heightPixels;
//		initialOrientation=this.getResources().getConfiguration().orientation;
//		dbHelper=new DiaryHelper(this);
//		
//		ueHandler = new UEHandler(this); 
//        Thread.setDefaultUncaughtExceptionHandler(ueHandler); 
//		FlurryAgent.onStartSession(this, Constant.FLURRY_KEY);
		setDatabaseHelper(new TimeMasterHelper(this));
		cacheModel=new CacheModel();
		super.onCreate();
	}
	
	public void setBitmap(Integer resid,Bitmap bitmap){
		SoftReference<Bitmap> softBitmap=new SoftReference<Bitmap>(bitmap);
		bitmapCache.put(resid, softBitmap);
	}
	public Bitmap getBitmap(Integer resid){
		SoftReference<Bitmap> softBitmap=bitmapCache.get(resid);
		if(softBitmap!=null){
			Bitmap bitmap=softBitmap.get();
			return bitmap;
		}
		return null;
	}
	public void freeBitmap(Integer resid){
		if(bitmapCache!=null){
			SoftReference<Bitmap> softBitmap=bitmapCache.get(resid);
			if(softBitmap!=null){
				softBitmap.clear();
			}
		}
	}

	public int getScreen_W(){
		if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT)
		   return this.screen_width;
		else
			return this.screen_height;
	}
	
	public int getScreen_H(){
		if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT)
			return this.screen_height;
		else
			return this.screen_width;
	}

	public TimeMasterHelper getDatabaseHelper() {
		return databaseHelper;
	}

	public void setDatabaseHelper(TimeMasterHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}

	public boolean isDataInitialized() {
		return dataInitialized;
	}

	public void setDataInitialized(boolean dataInitialized) {
		this.dataInitialized = dataInitialized;
	}

	public int getScreenMode() {
		return screenMode;
	}

	public void setScreenMode(int screenMode) {
		this.screenMode = screenMode;
		DisplayMetrics displaymetrics = new DisplayMetrics();
		WindowManager window=(WindowManager)(this.getSystemService(Context.WINDOW_SERVICE));
		window.getDefaultDisplay().getMetrics(displaymetrics);
		screen_width=displaymetrics.widthPixels;
		screen_height=displaymetrics.heightPixels;
	}
	
	public CacheModel getCacheModel() {
		return cacheModel;
	}
	
}
