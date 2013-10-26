package com.time.master.database;

import java.io.File;

import com.time.master.TimeMasterApplication;
import com.time.master.database.TimeMasterHelper.Columns.ScheduleRecordsColumn;
import com.time.master.tool.ChineseCalendar;
import com.time.master.tool.ExcelParseTool;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class TimeMasterHelper extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "schedule.db";//DB name
	private static final int DB_VERSION = 1;	//DB version
	
	
	private SQLiteDatabase db;
	private Context context;
	
	
    /**
     * ���캯��
     * @param context
     */
	public TimeMasterHelper(Context context){
		super(context, getDataBasePath(), null, DB_VERSION);
		this.context=context;
		setDb(getWritableDatabase());
	}
	/**
	 * ��ȡ�������ݿ�����·��
	 * @return ���ݿ�·��
	 */
	public static String getDataBasePath(){
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "schedule");
			if(!f.exists()){
				if(f.mkdirs())
					return f.getAbsolutePath()+File.separator+DB_NAME;
			}
			else
				return f.getAbsolutePath()+File.separator+DB_NAME;
		}
		return DB_NAME;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		TimeMasterApplication.getInstance().setDataInitialized(false);
		db.execSQL(CREATE_TABLE_NATIONAL_DISTRICT);
		db.execSQL(CREATE_TABLE_SCHEDULE_RECORDS);
		
		//ExcelParseTool.initNationalLocationByExcel(this.context,db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public SQLiteDatabase getDb() {
		return db;
	}
	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	/**��ʱ���¼��(_SCHEDULE_RECORDS)�в��뿪ʼʱ��(_START_TIME)*/
	public void insertScheduleRecord(ChineseCalendar starttime){
		ContentValues values=new ContentValues();
		values.put(ScheduleRecordsColumn._START_TIME, starttime.getTimeInMillis());
		//values.put(ScheduleRecordsColumn._DURATION_TIME, durationtime.getTimeInMillis());
		//values.put(ScheduleRecordsColumn._END_TIME, endtime.getTimeInMillis());
		System.out.println(starttime.getTimeInMillis());
		//System.out.println(durationtime.getTimeInMillis());
		//System.out.println(endtime.getTimeInMillis());
		db.insert(Tables.T_SCHEDULE_RECORDS, null, values);
	}
	public void updateEndTime(ChineseCalendar endtime){
		ContentValues values=new ContentValues();
		values.put(ScheduleRecordsColumn._END_TIME, endtime.getTimeInMillis());
		String whereClause = null;
		String[] whereArgs = null;
		System.out.println(endtime.getTimeInMillis());
		db.update(Tables.T_SCHEDULE_RECORDS, values, whereClause, whereArgs);
	}
	public void updateDurationTime(ChineseCalendar durationtime){
		ContentValues values=new ContentValues();
		values.put(ScheduleRecordsColumn._DURATION_TIME ,durationtime.getTimeInMillis());
		String whereClause = null;
		String[] whereArgs = null;
		System.out.println(durationtime.getTimeInMillis());
		db.update(Tables.T_SCHEDULE_RECORDS, values, whereClause, whereArgs);
	}
	/*********************************��̬���ݿ���������**********************************************************************/
	/**���ݿ����*/
	public interface Tables {
		/**@table �й�ʡ�����ر�*/
		public static final String T_NATIONAL_DISTRICT = "t_national_district";
		/**@table ʱ������¼��*/
		public static final String T_SCHEDULE_RECORDS = "t_schedule_records";
				
	}
	/**���ݿ���ͼ*/
	public interface Views {
		
	}
    /**���ݱ�����*/
	public interface Columns{
		/**ʡ��ֱϽ������*/
		public interface NationDistrictColumn{
			public static final String _ID = "_id";//����
			public static final String _NAME = "_name";//����
			public static final String _LEVEL = "_level";//���򼶱�
			public static final String _UPID = "_upid";//������������
		}
		
		/**ʱ������¼ ����*/
		public interface ScheduleRecordsColumn{
			public static final String _ID = "_id";
			public static final String _START_TIME = "_start_time";
			public static final String _DURATION_TIME = "_duration_time";
			public static final String _END_TIME = "_end_time";
		}

	}
	/*******************************������sql����***************************************************/
	public static final String CREATE_TABLE_NATIONAL_DISTRICT = 
			"CREATE TABLE IF NOT EXISTS " +  Tables.T_NATIONAL_DISTRICT + "("
			+ Columns.NationDistrictColumn._ID + " integer primary key,"	
			+ Columns.NationDistrictColumn._NAME + " text,"
			+ Columns.NationDistrictColumn._LEVEL + " integer,"
			+ Columns.NationDistrictColumn._UPID + " integer"
			+ ")";
	
	public static final String CREATE_TABLE_SCHEDULE_RECORDS = 
			"CREATE TABLE IF NOT EXISTS " +  Tables.T_SCHEDULE_RECORDS + "("
			+ Columns.ScheduleRecordsColumn._ID + " integer primary key,"	
			+ Columns.ScheduleRecordsColumn._START_TIME + " real,"
			+ Columns.ScheduleRecordsColumn._DURATION_TIME + " real,"
			+ Columns.ScheduleRecordsColumn._END_TIME + " real"
			+ ")";

	/***************************************ɾ����sql����*****************************************************************/
	public static final String DROP_TABLE_NATIONAL_DISTRICT = "DROP TABLE IF EXISTS " + Tables.T_NATIONAL_DISTRICT+" ";
	public static final String DROP_TABLE_SCHEDULE_RECORDS = "DROP TABLE IF EXISTS " + Tables.T_SCHEDULE_RECORDS+" ";

}
