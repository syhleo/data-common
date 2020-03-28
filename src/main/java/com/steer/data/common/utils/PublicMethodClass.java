/***文档注释***********************************************
 * 作者               :                   易鑫
 * 创建日期      :                   2017.05.11
 * 描述               :                   公共方法汇总类
 * 注意事项      :                   无
 * 遗留BUG :                   无
 * 修改日期      :                   
 * 修改人员      :                   
 * 修改内容      :                   
 ***********************************************************/

package com.steer.data.common.utils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicMethodClass {
    /**
     * 随机数生成
     */
    public static String RandomCreate(int i) {
        String str = "";
        for (int k = 0; k < i; k++) {
            str += (int) (Math.random() * 10);//[1-10)随机数 即0-9
        }
        return str;
    }

    /**
     * 根据时间+随机数生成编号等
     */
    public static String NumCreate(int i) {
        return (new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())) + RandomCreate(i);
    }

    //左侧补0的方式 比如%在前表示前面补充，若在后则后面补充，%后的0标识的是补的是什么字符
    //4表示字符长度为4，d是正则表达式中的d,后面的1则是需要补的数值
    //String.format("%04d", 1)   结果为0001

    //------------------------------------------公共方法---------------------------------------

    /**
     * 合并byte数组
     */
    public static byte[] Concat(byte[] A, byte[] B) {
        byte[] C = new byte[A.length + B.length];
        System.arraycopy(A, 0, C, 0, A.length);
        System.arraycopy(B, 0, C, A.length, B.length);
        return C;
    }

    /**
     * 截取byte中的一部分数据
     */
    public static byte[] rtBt(byte[] bt, int iStart, int ilen) {
        byte[] rbt = new byte[ilen];
        for (int i = iStart; i < (iStart + ilen); i++) {
            rbt[i - iStart] = bt[i];
        }
        return rbt;
    }

    /**
     * Blob转byte[]
     *
     * @throws SQLException
     */
	/*public static byte[] BlobForByte(Blob bl) throws SQLException{
		ByteArrayInputStream msgContent;
		msgContent = (ByteArrayInputStream) bl.getBinaryStream();
		byte[] byte_data = new byte[msgContent.available()];
		msgContent.read(byte_data, 0,byte_data.length);
		return byte_data;
	}*/
    public static void main(String[] args) {
        System.out.println(NumCreate(3));//20190903162001428209
    }


}
