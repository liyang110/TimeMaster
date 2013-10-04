package com.time.master.database;

import java.io.File;

import com.time.master.TimeMasterApplication;


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
		
		//ExcelParseTool.initNationalLocation(this.context,db);
		
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

	/*********************************��̬���ݿ���������**********************************************************************/
	/**���ݿ����*/
	public interface Tables {
		/**@table �й�ʡ�����ر�*/
		public static final String T_NATIONAL_DISTRICT = "t_national_district";
				
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

	}
	/*******************************������sql����***************************************************/
	public static final String CREATE_TABLE_NATIONAL_DISTRICT = 
			"CREATE TABLE IF NOT EXISTS " +  Tables.T_NATIONAL_DISTRICT + "("
			+ Columns.NationDistrictColumn._ID + " integer primary key,"	
			+ Columns.NationDistrictColumn._NAME + " text,"
			+ Columns.NationDistrictColumn._LEVEL + " integer,"
			+ Columns.NationDistrictColumn._UPID + " integer"
			+ ")";

	/***************************************ɾ����sql����*****************************************************************/
	public static final String DROP_TABLE_NATIONAL_DISTRICT = "DROP TABLE IF EXISTS " + Tables.T_NATIONAL_DISTRICT+" ";

}
