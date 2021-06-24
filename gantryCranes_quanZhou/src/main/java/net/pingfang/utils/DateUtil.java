package net.pingfang.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DateUtil {
 /*
	public static void main(String[] args) { 
		System.out.println(DateUtil.getDate("yyyyMMddHHmmss"));// new Date()为获取当前系统时间
		System.out.println(DateUtil.getDate("yyyyMMddHHmmsssss"));// new Date()为获取当前系统时间
		System.out.println("20191103132542069");
		
		//2019-11-03 13:25:42 00
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(new Date()));
		try {
			//Date dd = ;
			System.out.println(df.format(df.parse("2019-11-03 13:25:42 00")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	/**
	 * 获取当前系统时间并按指定的日期类型格式化
	 * @param dateType  设置日期格式
	 * @return
	 */
	public static String getDate(String dateType) {
		SimpleDateFormat df = new SimpleDateFormat(dateType);//设置日期格式
		return df.format(new Date());
	}
	/**
	 * 日期排序从大到小
	 * @param list
	 */
	public static void dateSort(List<String> list) {
		//对不带时间类型的集合进行排序
        Collections.sort(list, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
           @Override
           public int compare(String o1, String o2) {
               try {
            	   return f.parse(o2).compareTo(f.parse(o1));
               } catch (ParseException e) {
                   throw new IllegalArgumentException(e);
               }
           }
       });
	}
	/**
	 * 日期格式转换
	 * @param list
	 */
	public static String dateParse(String type, String dateStr) {
		if(null !=type && null !=dateStr) {
			SimpleDateFormat df = new SimpleDateFormat(type);
			try {
				return df.format(df.parse(dateStr));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}else {
			return null;
		}		
	}
	/*
	 public static void main(String[] args) {
		 List<String> list = new ArrayList<String>();
		 list.add("2018-04-06 08:34:24");
		 list.add("2018-11-26 22:38:29");
		 list.add("2018-09-22 22:06:52");
		 list.add("2018-10-15 07:23:48");
		 list.add("2018-08-03 10:45:41");
		 list.add("2018-08-02 14:18:08");
		//对不带时间类型的集合进行排序
         Collections.sort(list, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o2).compareTo(f.parse(o1));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
         for(String s:list) {
        	 System.out.println(s);
         }
	}
	 */
}
