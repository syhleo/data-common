package com.steer.data.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具类
 *
 * @author Administrator
 */
public class DateUtil {

    /**
     * 日期对象转字符串
     *
     * @param date
     * @param format
     * @return
     */
    //如： DateUtil.formatDate(new Date(),"yyyy-MM-dd"); Date为java.util.Date;
    public static String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            result = sdf.format(date);
        }
        return result;
    }

    /**
     * 字符串转日期对象
     *
     * @param str
     * @param format
     * @return
     * @throws Exception
     */
    public static Date formatString(String str, String format) throws Exception {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }


    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到几天前的时间
     *
     * @param d   天数
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar no = Calendar.getInstance();
        no.setTime(d);
        no.set(Calendar.DATE, no.get(Calendar.DATE) - day);
        return no.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d   天数
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar no = Calendar.getInstance();
        no.setTime(d);
        no.set(Calendar.DATE, no.get(Calendar.DATE) + day);
        return no.getTime();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm"));
        //2018-03-16 20:19
        System.out.println(DateUtil.getMonthLastDay(2018, 8));//31
        Date v_end_date_time = DateUtil.getDateBefore(new Date(), 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String end_date_str = df.format(v_end_date_time);
        Date v_end_date = DateUtil.formatString(end_date_str, "yyyy-MM-dd");
        System.out.println(v_end_date);//

        Double d = null;
        Double dd = 0d;
        double v2 = 345.234;
        System.out.println(getDateBefore(new Date(), 3));//假如当前日期是2019-02-15  Tue Feb 12 17:11:59 CST 2019
        System.out.println(getDateAfter(new Date(), 2));//假如当前日期是2019-02-15  Sun Feb 17 17:14:21 CST 2019
        System.out.println(getDateBefore(new Date(), 15));//假如当前日期是2019-02-15  Thu Jan 31 17:14:21 CST 2019
        System.out.println(getDateAfter(new Date(), 14));//假如当前日期是2019-02-15   Fri Mar 01 17:15:48 CST 2019


        /**
         * 表达时间戳
         */
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String format = date.format(new Date(System.currentTimeMillis()));
        System.out.println("当前时间：" + format);// 当前时间：20191028161141386

        Date date22 = new Date();
        Timestamp timeStamp = new Timestamp(date22.getTime());
        System.out.println("当前时间戳：" + timeStamp);//2019-10-28 16:11:41.386

		/*
		可以直接 INSERT INTO logging_event SET create_time =  '2017-10-27 12:15:42.664' ;
		 */

    }
}

