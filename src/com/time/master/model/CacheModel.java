package com.time.master.model;

import java.util.HashMap;

import com.time.master.tool.ChineseCalendar;
/**
 * Ӧ�û���ģ��
 * @author desmond.duan
 *
 */
public class CacheModel {

	public ChineseCalendar  startTime,//ʱ��ģ�鿪ʼʱ��
	                        endTime,//ʱ��ģ�����ʱ��
	                        currentTime;//��ǰѡ��ʱ��,�仯Ƶ����ȱ�ٲο���
	
	public long tickingTime=0;//��ʱʱ�䣬long��

   
     public HashMap<String, String> tmpResultsCache=new HashMap<String, String>();//dialog ���ؽ�����ݻ���
    

   
}