package com.time.master.adapter;

import com.time.master.R;
import com.time.master.TimeMasterApplication;

import android.content.Context;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.GridView;  
import android.widget.ImageView;  
    
 public class DateGridViewImageAdapter extends BaseAdapter  
 {  
     // ����Context  
     private Context     mContext;  
     // ������������ ��ͼƬԴ  
     private Integer[]   mImageIds   =   
     {   
             R.drawable.imag1,   
             R.drawable.imag2,   
             R.drawable.imag3,   
             R.drawable.imag4,   
             R.drawable.imag5,   
             R.drawable.imag6,
             R.drawable.imag7,
             R.drawable.imag8,
             R.drawable.imag9,
             R.drawable.imag10,
             R.drawable.imag11,
             R.drawable.imag12,
             R.drawable.imag13,
             R.drawable.imag14,
             R.drawable.imag15,
             R.drawable.imag16,
             R.drawable.imag17,
             
     };  
    
     public DateGridViewImageAdapter(Context c)  
     {  
        mContext = c;  
    }  
    
    // ��ȡͼƬ�ĸ���  
     public int getCount()  
    {  
        return mImageIds.length;  
     }  
   
     // ��ȡͼƬ�ڿ��е�λ��  
     public Object getItem(int position)  
     {  
         return position;  
    }  
   
    
     // ��ȡͼƬID  
     public long getItemId(int position)  
     {  
         return position;  
    }  
    
     public View getView(int position, View convertView, ViewGroup parent)  
     {  
         ImageView imageView;  
         if (convertView == null)  
         {  
             // ��ImageView������Դ  
             imageView = new ImageView(mContext);  
            // ���ò��� ͼƬ120��120��ʾ  
            imageView.setLayoutParams(new GridView.LayoutParams(
            		TimeMasterApplication.getInstance().getScreen_W()/8,
            		TimeMasterApplication.getInstance().getScreen_W()/8));  
             // ������ʾ��������  
             imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);  
         }  
        else 
         {  
            imageView = (ImageView) convertView;  
        }  
   
         imageView.setImageResource(mImageIds[position]);  
         return imageView;  
     }  
    
 } 
