package com.excel.test;

import java.io.File;
import java.io.ObjectInputStream.GetField;

import jxl.*; 

public class ExcelTest{
    public static void main(String[] args) {
        int i,j,maxRow,maxColumn;
        Sheet sheet;
        Workbook book;
        Cell[] cell;
        try {
            //t.xlsΪҪ��ȡ��excel�ļ���
        	//String file=ExcelTest.class.getResource("/").getPath()+"jxlrwtest.xls";
            book= Workbook.getWorkbook(new File("D:\\�й���������.xls")); 
            
            //��õ�һ�����������(ecxel��sheet�ı�Ŵ�0��ʼ,0,1,2,3,....)
            sheet=book.getSheet(0); 
            //��ȡ���Ͻǵĵ�Ԫ��
            
            //System.out.println("���⣺"+cell.getContents()); 
            
            maxRow=sheet.getRows();
            
            maxColumn=sheet.getColumns();
            System.out.println(maxRow);
            System.out.println(maxColumn);
            /*while(true)
            {
                //��ȡÿһ�еĵ�Ԫ�� 
                cell1=sheet.getCell(0,i);//���У��У�
                cell2=sheet.getCell(1,i);
                cell3=sheet.getCell(2,i);
                if("".equals(cell1.getContents())==true)    //�����ȡ������Ϊ��
                    break;
                System.out.println(cell1.getContents()+"\t"+cell2.getContents()+"\t"+cell3.getContents()); 
                i++;
            }*/
            for(i=0;i<maxRow;i++){
            	
            		cell=sheet.getRow(i);
            		for(j=0;j<cell.length;j++){
            			
            		if("".equals(cell[j].getContents())==true){
            			System.out.print("\t\t");
            			continue;
            		}
            			
            			System.out.print(cell[j].getContents()+"\t");
            		}
            		
            		System.out.print("\n");
            	
            }
            book.close(); 
        }
        catch(Exception e)  { 
        	e.printStackTrace();
        } 
    }
}
