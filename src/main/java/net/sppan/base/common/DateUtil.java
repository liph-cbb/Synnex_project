package net.sppan.base.common;/**
 * Created by windsor on 2017/6/25.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 李杨洲
 * @create 2017-06-25 0:31
 **/
public class DateUtil {
    /**
     * 倆個日期的時差計算，不足一小时不累计如小时数
     * @param startTime
     * @param endTime
     * @param format
     * @param str
     * @return
     */
    public  static Long dateDiff(String startTime, String endTime,
                              String format, String str) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + day * 24;// 计算差多少小时
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"
                    + (min - day * 24 * 60) + "分钟" + sec + "秒。");
            System.out.println("hour=" + hour + ",min=" + min);
            if (str.equalsIgnoreCase("h")) {
                return hour;
            } else {
                return min;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (str.equalsIgnoreCase("h")) {
            return hour;
        } else {
            return min;
        }
    }



    public static void main(String[] args) {
        System.out.println(dateDiff("2016-01-09 17:40:00",
                "2016-01-09 19:30:00","yyyy-MM-dd hh:mm:ss","h"
                ));
    }
}
