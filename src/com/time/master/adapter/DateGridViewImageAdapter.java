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
             R.drawable.img1,   
             R.drawable.img2,   
             R.drawable.img3,   
             R.drawable.img4,   
             R.drawable.img5,   
             R.drawable.img6,      
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
            		TimeMasterApplication.getInstance().getScreen_W()/12,
            		TimeMasterApplication.getInstance().getScreen_W()/12));  
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
