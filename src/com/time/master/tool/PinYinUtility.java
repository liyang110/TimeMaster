package com.time.master.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtility {
	public static String getPinYinHeadChar(String str) {

		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// ��ȡ���ֵ�����ĸ
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}
	
    // �ж��ַ����Ƿ����������   
    public static boolean isChinese(String str) {    
        String regex = "[\\u4e00-\\u9fa5]";    
        Pattern pattern = Pattern.compile(regex);    
        Matcher matcher = pattern.matcher(str);    
        return matcher.find();    
    }    
    //ʹ��PinYin4j.jar������ת��Ϊƴ��  
    public static String chineneToSpell(String chineseStr){  
        try {
			return PinyinHelper.toHanyuPinyinString(chineseStr , new HanyuPinyinOutputFormat() ,"");
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			return null;
		}  
    }  
}
