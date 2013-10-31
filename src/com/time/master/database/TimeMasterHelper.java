package com.time.master.database;

import java.io.File;

import com.time.master.TimeMasterApplication;
import com.time.master.database.TimeMasterHelper.Columns.RepeatEveryMonthTimeColumn;
import com.time.master.database.TimeMasterHelper.Columns.RepeatEveryYearTimeColumn;
import com.time.master.database.TimeMasterHelper.Columns.RepeatIntervalMonthTimeColumn;
import com.time.master.database.TimeMasterHelper.Columns.RepeatIntervalYearTimeColumn;
import com.time.master.database.TimeMasterHelper.Columns.ScheduleRecordsColumn;
import com.time.master.tool.ChineseCalendar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

public class TimeMasterHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "schedule.db";// DB name
	private static final int DB_VERSION = 1; // DB version
	
	private SQLiteDatabase db;
	private Context context;

	/**
	 * ���캯��
	 * 
	 * @param context
	 */
	public TimeMasterHelper(Context context) {
		super(context, getDataBasePath(), null, DB_VERSION);
		this.context = context;
		setDb(getWritableDatabase());
	}

	/**
	 * ��ȡ�������ݿ�����·��
	 * 
	 * @return ���ݿ�·��
	 */
	public static String getDataBasePath() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File f = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "schedule");
			if (!f.exists()) {
				if (f.mkdirs())
					return f.getAbsolutePath() + File.separator + DB_NAME;
			} else
				return f.getAbsolutePath() + File.separator + DB_NAME;
		}
		return DB_NAME;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		TimeMasterApplication.getInstance().setDataInitialized(false);
		db.execSQL(CREATE_TABLE_NATIONAL_DISTRICT);
		db.execSQL(CREATE_TABLE_SCHEDULE_RECORDS);
		db.execSQL(CREATE_TABLE_REPEAT_EVERY_MONTH_TIME);
		db.execSQL(CREATE_TABLE_REPEAT_EVERY_YEAR_TIME);
		db.execSQL(CREATE_TABLE_REPEAT_INTERVAL_MONTH_TIME);
		db.execSQL(CREATE_TABLE_REPEAT_INTERVAL_YEAR_TIME);

		// ExcelParseTool.initNationalLocationByExcel(this.context,db);

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

	/** ��ʱ���¼��(_SCHEDULE_RECORDS)�в��뿪ʼʱ��(_START_TIME) */
	public void insertScheduleRecord(ChineseCalendar starttime) {
		ContentValues values = new ContentValues();
		values.put(ScheduleRecordsColumn._START_TIME,
				starttime.getTimeInMillis());
		System.out.println("���뿪ʼʱ��"+starttime.getTimeInMillis());
		db.insert(Tables.T_SCHEDULE_RECORDS, null, values);
	}
	/** ��ʱ���¼��(_SCHEDULE_RECORDS)�и��¿�ʼʱ��(_END_TIME) */
	public void updateEndTime(ChineseCalendar endtime) {
		ContentValues values = new ContentValues();
		values.put(ScheduleRecordsColumn._END_TIME, endtime.getTimeInMillis());
		String laststarttime=String.valueOf(getLastStartTime());
		String whereClause = "_start_time=?";
		String[] whereArgs = {laststarttime};
		System.out.println("���½���ʱ��"+endtime.getTimeInMillis());
		db.update(Tables.T_SCHEDULE_RECORDS, values, whereClause, whereArgs);
	}
	/** ��ʱ���¼��(_SCHEDULE_RECORDS)�и��³���ʱ��(_DURATION_TIME) */
	public void updateDurationTime(ChineseCalendar durationtime) {
		ContentValues values = new ContentValues();
		values.put(ScheduleRecordsColumn._DURATION_TIME,
				durationtime.getTimeInMillis());
		String laststarttime=String.valueOf(getLastStartTime());
		String whereClause = "_start_time=?";
		String[] whereArgs = {laststarttime};
		System.out.println("���³���ʱ��"+durationtime.getTimeInMillis());
		db.update(Tables.T_SCHEDULE_RECORDS, values, whereClause, whereArgs);
	}
	
	/** ��_START_TIMEΪ�ؼ��ֽ������򣬻�ȡ�������Ŀ�ʼʱ��*/
	public long getLastStartTime(){
		Cursor cursor = db
				.query(Tables.T_SCHEDULE_RECORDS,
						new String[] { "_id,_start_time,_duration_time,_end_time" },
						null,null, null, null,
						"_start_time desc");
		if(cursor.getCount()==0){
			return 0;
		}
		long starttime=0;
		
			if(cursor.moveToFirst()){
				int id=cursor.getInt(cursor.getColumnIndex("_id"));
				starttime=cursor.getLong(cursor.getColumnIndex("_start_time"));
				System.out.println("��ȡ����Ŀ�ʼʱ��"+id+" "+starttime);
		}
		
		return starttime;
		
	}
	/** ��_END_TIMEΪ�ؼ��ֽ������򣬻�ȡ�������Ŀ�ʼʱ��*/
	public long getLastEndTime() {
		Cursor cursor = db
				.query(Tables.T_SCHEDULE_RECORDS,
						new String[] { "_id,_start_time,_duration_time,_end_time" },
						null,null, null, null,
						"_end_time desc");
		if(cursor.getCount()==0){
			return 0;
		}
		long endtime=0;
		
			if(cursor.moveToFirst()){
				
				int id=cursor.getInt(cursor.getColumnIndex("_id"));
				endtime=cursor.getLong(cursor.getColumnIndex("_end_time"));
				System.out.println("��ȡ����Ľ���ʱ��"+id+" "+endtime);
			}
		return endtime;
	}
	/** ��_END_TIMEΪ�ؼ��ֽ������򣬻�ȡ�ڶ����Ŀ�ʼʱ�䣬�����ǰʱ����ʼʱ���Ϊ��ǰʱ��Ľ���ʱ������߼�����*/
	public long getLastSecondEndTime() {
		Cursor cursor = db
				.query(Tables.T_SCHEDULE_RECORDS,
						new String[] { "_id,_start_time,_duration_time,_end_time" },
						null,null, null, null,
						"_end_time desc");
		long endtime=0;
		if(cursor.getCount()<2){
			return 0;
		}
		if(cursor.moveToFirst()){
				cursor.moveToNext();
				int id=cursor.getInt(cursor.getColumnIndex("_id"));
				endtime=cursor.getLong(cursor.getColumnIndex("_end_time"));
				System.out.println("��ȡ����ҷǵ�ǰ�Ľ���ʱ��"+id+" "+endtime);
			}
		return endtime;
	}
	
	
	
	public void insertIntervalMonthTime(ChineseCalendar time) {
		ContentValues values = new ContentValues();
		values.put(RepeatIntervalMonthTimeColumn._INTERVAL_MONTH_TIME,
				time.getTimeInMillis());
		db.insert(Tables.T_REPEAT_INTERVAL_MONTH_TIME, null, values);
		System.out.println(time.getTimeInMillis());
	}
	public void updateIntervalMonthTime(ChineseCalendar time) {
		ContentValues values = new ContentValues();
		values.put(RepeatIntervalMonthTimeColumn._INTERVAL_MONTH_TIME,
				time.getTimeInMillis());
		db.update(Tables.T_REPEAT_INTERVAL_MONTH_TIME, values,
				RepeatIntervalMonthTimeColumn._ID+"=?",
				new String[]{"1"});
		System.out.println("up"+time.getTimeInMillis());
	}
	public ChineseCalendar getIntervalMonthTime(){
		String sql="select * from "+Tables.T_REPEAT_INTERVAL_MONTH_TIME;
		Cursor cursor=db.rawQuery(sql, null);
		if(cursor.moveToNext()){
			cursor.moveToFirst();
			String 	nameString=cursor.getColumnName(1);
			long value=cursor.getLong(cursor.getColumnIndex(nameString));
			ChineseCalendar calendar=new ChineseCalendar();
			calendar.setTimeInMillis(value);
			return calendar;
		}else {
			return null;
		}
	}
	
	public void insertEveryMonthTime(ChineseCalendar time) {
		ContentValues values = new ContentValues();
		values.put(RepeatEveryMonthTimeColumn._EVERY_MONTH_TIME,
				time.getTimeInMillis());
		db.insert(Tables.T_REPEAT_EVERY_MONTH_TIME, null, values);
		System.out.println(time.getTimeInMillis());
	}
	public void updateEveryMonthTime(ChineseCalendar time) {
		ContentValues values = new ContentValues();
		values.put(RepeatEveryMonthTimeColumn._EVERY_MONTH_TIME,
				time.getTimeInMillis());
		db.update(Tables.T_REPEAT_EVERY_MONTH_TIME, values,
				RepeatEveryMonthTimeColumn._ID+"=?",
				new String[]{"1"});
		System.out.println("up"+time.getTimeInMillis());
	}
	
	public ChineseCalendar getEveryMonthTime(){
		String sql="select * from "+Tables.T_REPEAT_EVERY_MONTH_TIME;
		Cursor cursor=db.rawQuery(sql, null);
		if(cursor.moveToNext()){
			cursor.moveToFirst();
			String 	nameString=cursor.getColumnName(1);
			long value=cursor.getLong(cursor.getColumnIndex(nameString));
			ChineseCalendar calendar=new ChineseCalendar();
			calendar.setTimeInMillis(value);
			return calendar;
		}else {
			return null;
		}
	}
	
	public void insertIntervalYearTime(ChineseCalendar time) {
		ContentValues values = new ContentValues();
		values.put(RepeatIntervalYearTimeColumn._INTERVAL_YEAR_TIME,
				time.getTimeInMillis());
		db.insert(Tables.T_REPEAT_INTERVAL_YEAR_TIME, null, values);
		System.out.println(time.getTimeInMillis());
	}
	
	public void updateIntervalYearTime(ChineseCalendar time) {
		ContentValues values = new ContentValues();
		values.put(RepeatIntervalYearTimeColumn._INTERVAL_YEAR_TIME,
				time.getTimeInMillis());
		db.update(Tables.T_REPEAT_INTERVAL_YEAR_TIME, values,
				RepeatIntervalYearTimeColumn._ID+"=?",
				new String[]{"1"});
		System.out.println("up"+time.getTimeInMillis());
	}
	
	public ChineseCalendar getIntervalYearTime(){
		String sql="select * from "+Tables.T_REPEAT_INTERVAL_MONTH_TIME;
		Cursor cursor=db.rawQuery(sql, null);
		if(cursor.moveToNext()){
			cursor.moveToFirst();
			String 	nameString=cursor.getColumnName(1);
			long value=cursor.getLong(cursor.getColumnIndex(nameString));
			
			ChineseCalendar calendar=new ChineseCalendar();
			calendar.setTimeInMillis(value);
			return calendar;
		}else {
			return null;
		}
	}
	
	public void insertEveryYearTime(ChineseCalendar time) {
		ContentValues values = new ContentValues();
		values.put(RepeatEveryYearTimeColumn._EVERY_YEAR_TIME,
				time.getTimeInMillis());
		db.insert(Tables.T_REPEAT_EVERY_YEAR_TIME, null, values);
		System.out.println(time.getTimeInMillis());
	}
	
	public void updateEveryYearTime(ChineseCalendar time) {
		ContentValues values = new ContentValues();
		values.put(RepeatEveryYearTimeColumn._EVERY_YEAR_TIME,
				time.getTimeInMillis());
		db.update(Tables.T_REPEAT_EVERY_YEAR_TIME, values,
				RepeatEveryYearTimeColumn._ID+"=?",
				new String[]{"1"});
		System.out.println("up"+time.getTimeInMillis());
	}
	public ChineseCalendar getEveryYearTime(){
		String sql="select * from "+Tables.T_REPEAT_EVERY_YEAR_TIME;
		Cursor cursor=db.rawQuery(sql, null);
		if(cursor.moveToNext()){
			cursor.moveToFirst();
			String 	nameString=cursor.getColumnName(1);
			long value=cursor.getLong(cursor.getColumnIndex(nameString));
			ChineseCalendar calendar=new ChineseCalendar();
			calendar.setTimeInMillis(value);
			return calendar;
		}else {
			return null;
		}
	}
	

	/********************************* ��̬���ݿ��������� **********************************************************************/
	/** ���ݿ���� */
	public interface Tables {
		/** @table �й�ʡ�����ر� */
		public static final String T_NATIONAL_DISTRICT = "t_national_district";
		/** @table ʱ������¼�� */
		public static final String T_SCHEDULE_RECORDS = "t_schedule_records";
		/** @table �ظ�����¼�¼��*/
		public static final String T_REPEAT_INTERVAL_MONTH_TIME="t_repeat_interval_month_time";
		/** @table �ظ�������¼��*/
		public static final String T_REPEAT_EVERY_MONTH_TIME="t_repeat_every_month_time";
		/** @table �ظ�ÿ�¼�¼��*/
		public static final String T_REPEAT_INTERVAL_YEAR_TIME="t_repeat_interval_year_time";
		/** @table �ظ�ÿ���¼��*/
		public static final String T_REPEAT_EVERY_YEAR_TIME="t_repeat_every_year_time";

	}

	/** ���ݿ���ͼ */
	public interface Views {

	}

	/** ���ݱ����� */
	public interface Columns {
		/** ʡ��ֱϽ������ */
		public interface NationDistrictColumn {
			public static final String _ID = "_id";// ����
			public static final String _NAME = "_name";// ����
			public static final String _LEVEL = "_level";// ���򼶱�
			public static final String _UPID = "_upid";// ������������
		}

		/** ʱ������¼ ���� */
		public interface ScheduleRecordsColumn {
			public static final String _ID = "_id";
			public static final String _START_TIME = "_start_time";
			public static final String _DURATION_TIME = "_duration_time";
			public static final String _END_TIME = "_end_time";
		}
		
		/**�ظ�����¼�¼ */
		public interface RepeatIntervalMonthTimeColumn{
			public static final String _ID = "_id";// ����
			public static final String _INTERVAL_MONTH_TIME = "_interval_month_time";// ����
		}
		/**�ظ�ÿ�¼�¼ */
		public interface RepeatEveryMonthTimeColumn{
			public static final String _ID = "_id";// ����
			public static final String _EVERY_MONTH_TIME = "_every_month_time";// ÿ��
			
		}
		/**�ظ�������¼ */
		public interface RepeatIntervalYearTimeColumn{
			public static final String _ID = "_id";// ����
			public static final String _INTERVAL_YEAR_TIME = "_interval_year_time";// ����
		}
		/**�ظ�ÿ���¼ */
		public interface RepeatEveryYearTimeColumn{
			public static final String _ID = "_id";// ����
			public static final String _EVERY_YEAR_TIME = "_every_year_time";//ÿ��
		}
		
		
	}
	
	/******************************* ������sql���� ***************************************************/
	public static final String CREATE_TABLE_NATIONAL_DISTRICT = "CREATE TABLE IF NOT EXISTS "
			+ Tables.T_NATIONAL_DISTRICT
			+ "("
			+ Columns.NationDistrictColumn._ID
			+ " integer primary key,"
			+ Columns.NationDistrictColumn._NAME
			+ " text,"
			+ Columns.NationDistrictColumn._LEVEL
			+ " integer,"
			+ Columns.NationDistrictColumn._UPID + " integer" + ")";

	public static final String CREATE_TABLE_SCHEDULE_RECORDS = "CREATE TABLE IF NOT EXISTS "
			+ Tables.T_SCHEDULE_RECORDS
			+ "("
			+ Columns.ScheduleRecordsColumn._ID
			+ " integer primary key,"
			+ Columns.ScheduleRecordsColumn._START_TIME
			+ " real,"
			+ Columns.ScheduleRecordsColumn._DURATION_TIME
			+ " real,"
			+ Columns.ScheduleRecordsColumn._END_TIME + " real" + ")";
	
	public static final String CREATE_TABLE_REPEAT_INTERVAL_MONTH_TIME = "CREATE TABLE IF NOT EXISTS "
			+ Tables.T_REPEAT_INTERVAL_MONTH_TIME
			+ "("
			+ Columns.RepeatIntervalMonthTimeColumn._ID
			+ " integer primary key,"
			+ Columns.RepeatIntervalMonthTimeColumn._INTERVAL_MONTH_TIME + " real" + ")";
	
	public static final String CREATE_TABLE_REPEAT_EVERY_MONTH_TIME = "CREATE TABLE IF NOT EXISTS "
			+ Tables.T_REPEAT_EVERY_MONTH_TIME
			+ "("
			+ Columns.RepeatEveryMonthTimeColumn._ID
			+ " integer primary key,"
			+ Columns.RepeatEveryMonthTimeColumn._EVERY_MONTH_TIME + " real" + ")";
	
	public static final String CREATE_TABLE_REPEAT_INTERVAL_YEAR_TIME = "CREATE TABLE IF NOT EXISTS "
			+ Tables.T_REPEAT_INTERVAL_YEAR_TIME
			+ "("
			+ Columns.RepeatIntervalYearTimeColumn._ID
			+ " integer primary key,"
			+ Columns.RepeatIntervalYearTimeColumn._INTERVAL_YEAR_TIME + " real" + ")";
	
	public static final String CREATE_TABLE_REPEAT_EVERY_YEAR_TIME = "CREATE TABLE IF NOT EXISTS "
			+ Tables.T_REPEAT_EVERY_YEAR_TIME
			+ "("
			+ Columns.RepeatEveryYearTimeColumn._ID
			+ " integer primary key,"
			+ Columns.RepeatEveryYearTimeColumn._EVERY_YEAR_TIME + " real" + ")";

	/*************************************** ɾ����sql���� *****************************************************************/
	public static final String DROP_TABLE_NATIONAL_DISTRICT = "DROP TABLE IF EXISTS "
			+ Tables.T_NATIONAL_DISTRICT + " ";
	public static final String DROP_TABLE_SCHEDULE_RECORDS = "DROP TABLE IF EXISTS "
			+ Tables.T_SCHEDULE_RECORDS + " ";
	public static final String DROP_TABLE_REPEAT_INTERVAL_MONTH_TIME =  "DROP TABLE IF EXISTS "
			+ Tables.T_REPEAT_INTERVAL_MONTH_TIME + " ";
	public static final String DROP_TABLE_REPEAT_EVERY_MONTH_TIME =  "DROP TABLE IF EXISTS "
			+ Tables.T_REPEAT_EVERY_MONTH_TIME + " ";
	public static final String DROP_TABLE_REPEAT_INTERVAL_YEAR_TIME =  "DROP TABLE IF EXISTS "
			+ Tables.T_REPEAT_INTERVAL_YEAR_TIME + " ";
	public static final String DROP_TABL_REPEAT_EVERY_YEAR_TIME =  "DROP TABLE IF EXISTS "
			+ Tables.T_REPEAT_EVERY_YEAR_TIME + " ";
	

}
