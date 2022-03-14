package cn.thinkit.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @Package：cn.ucaner.common.utils.sequence   
* @ClassName：GenerateSequenceUtil   
* @Description：   <p> 根据时间生成唯一序列ID<br>
*                 时间精确到秒,ID最大值为99999且循环使用</p>
 */
public class GenerateSequenceUtil {
    
    /**
     * 
     */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
    
    /** 时间：精确到秒 */
    private static final  Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
    
    private static final  NumberFormat numberFormat = new DecimalFormat("00000");
    
    private static AtomicInteger seq =  new AtomicInteger(0);
     
    private static final AtomicInteger  MAX = new AtomicInteger(99999);
    
    public static synchronized String generateSequenceNo() {
         
        Calendar rightNow = Calendar.getInstance();
       
        StringBuffer sb = new StringBuffer();
 
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
 
        numberFormat.format(seq, sb, HELPER_POSITION);
 
        if (seq.intValue() == MAX.intValue()) {
            seq = new AtomicInteger(0);
        } else {
            seq.getAndIncrement();
        }
 
        return sb.toString();
    }
}