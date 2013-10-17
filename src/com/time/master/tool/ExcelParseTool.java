package com.time.master.tool;

import java.io.File;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.time.master.database.TimeMasterHelper;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * excel ��������
 * 
 * @author Desmond
 * 
 */
public class ExcelParseTool {

	/**
	 * java application for testing out side of project
	 * 
	 * @param args
	 */
	// public static void main(String[] args) {
	// int maxRow,maxColumn;
	// Sheet sheet;
	// Workbook book;
	// Cell[] cells;
	// try {
	//
	// StringBuffer sb = new StringBuffer();
	// //t.xlsΪҪ��ȡ��excel�ļ���
	// //String file=ExcelTest.class.getResource("/").getPath()+"jxlrwtest.xls";
	// book= Workbook.getWorkbook(new
	// File("assets"+File.separator+"location.xls"));
	//
	// //��õ�һ�����������(ecxel��sheet�ı�Ŵ�0��ʼ,0,1,2,3,....)
	// sheet=book.getSheet(0);
	// //��ȡ���Ͻǵĵ�Ԫ��
	//
	// //System.out.println("���⣺"+cell.getContents());
	//
	// maxRow=sheet.getRows();
	//
	// maxColumn=sheet.getColumns();
	// System.out.println(maxRow);
	// System.out.println(maxColumn);
	//
	// for(int i=0;i<maxRow;i++){
	//
	// cells=sheet.getRow(i);
	// /*while(true)
	// {
	// //��ȡÿһ�еĵ�Ԫ��
	// cell1=sheet.getCell(0,i);//���У��У�
	// cell2=sheet.getCell(1,i);
	// cell3=sheet.getCell(2,i);
	// if("".equals(cell1.getContents())==true) //�����ȡ������Ϊ��
	// break;
	// System.out.println(cell1.getContents()+"\t"+cell2.getContents()+"\t"+cell3.getContents());
	// i++;
	// }*/
	// sb.append("insert "+TimeMasterHelper.Tables.T_NATIONAL_DISTRICT +" ("
	// +TimeMasterHelper.Columns.NationDistrictColumn._ID+", "
	// +TimeMasterHelper.Columns.NationDistrictColumn._NAME+", "
	// +TimeMasterHelper.Columns.NationDistrictColumn._LEVEL+", "
	// +TimeMasterHelper.Columns.NationDistrictColumn._UPID+""
	// +") values ("
	// +cells[0].getContents()+", "
	// +cells[1].getContents()+", "
	// +cells[2].getContents()+", "
	// +cells[3].getContents()+""
	// +")");
	// sb.append("\r\n");
	// System.out.print("\n");
	//
	// }
	// book.close();
	//
	// File backupFile = new File("assets"+File.separator+"location.sql");
	// if (backupFile.exists()) {
	// // ���֮ǰ�ļ�¼
	// backupFile.delete();
	// } else {
	// backupFile.getParentFile().mkdirs();
	// }
	// backupFile.createNewFile();
	// FileOutputStream fos = new FileOutputStream(backupFile);
	// fos.write(sb.toString().getBytes());
	// fos.close();
	// }
	// catch(Exception e) {
	// e.printStackTrace();
	// }
	// }

	/***
	 * ��json�ļ��������ݿ�
	 * @param context
	 * @param db
	 */
	public static void initNationalLocationByJson(Context context,
			SQLiteDatabase db) {
		try {
			InputStream in = context.getAssets().open("location.json");
			JSONArray array = new JSONArray(Function.ReadFile(in));
			int length = array.length();
			for (int i = 0; i < length; i++) {

				JSONObject json = array.getJSONObject(i);

				StringBuffer sb = new StringBuffer();
				sb.append("insert into ")
						.append(TimeMasterHelper.Tables.T_NATIONAL_DISTRICT)
						.append(" (")
						.append(TimeMasterHelper.Columns.NationDistrictColumn._ID)
						.append(", ")
						.append(TimeMasterHelper.Columns.NationDistrictColumn._NAME)
						.append(", ")
						.append(TimeMasterHelper.Columns.NationDistrictColumn._LEVEL)
						.append(", ")
						.append(TimeMasterHelper.Columns.NationDistrictColumn._UPID)
						.append("").append(") values (").append(json.get("id"))
						.append(", ").append(json.get("name")).append(", ")
						.append(json.get("level")).append(", ")
						.append(json.get("upid")).append("").append(")");
				Log.e("db insert", sb.toString());
				db.execSQL(sb.toString());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��xls�ļ��������ݿ�
	 * @param context
	 * @param db
	 */
	public static void initNationalLocationByExcel(Context context,
			SQLiteDatabase db) {
		int maxRow, maxColumn;
		Sheet sheet;
		Workbook book;
		Cell[] cells;
		try {

			// t.xlsΪҪ��ȡ��excel�ļ���
			book = Workbook.getWorkbook(context.getAssets().open("location.xls"));

			// ��õ�һ�����������(ecxel��sheet�ı�Ŵ�0��ʼ,0,1,2,3,....)
			sheet = book.getSheet(0);
			// ��ȡ���Ͻǵĵ�Ԫ��

			// System.out.println("���⣺"+cell.getContents());

			maxRow = sheet.getRows();

			maxColumn = sheet.getColumns();
			System.out.println(maxRow);
			System.out.println(maxColumn);

			for (int i = 0; i < maxRow; i++) {
				StringBuffer sb = new StringBuffer();
				cells = sheet.getRow(i);
				sb.append("insert into ")
						.append(TimeMasterHelper.Tables.T_NATIONAL_DISTRICT)
						.append(" (")
						.append(TimeMasterHelper.Columns.NationDistrictColumn._ID)
						.append(", ")
						.append(TimeMasterHelper.Columns.NationDistrictColumn._NAME)
						.append(", ")
						.append(TimeMasterHelper.Columns.NationDistrictColumn._LEVEL)
						.append(", ")
						.append(TimeMasterHelper.Columns.NationDistrictColumn._UPID)
						.append("").append(") values (")
						.append(cells[0].getContents()).append(", ")
						.append(cells[1].getContents()).append(", ")
						.append(cells[2].getContents()).append(", ")
						.append(cells[3].getContents()).append("").append(")");
				Log.e("db insert", sb.toString());
				db.execSQL(sb.toString());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
