/***文档注释***********************************************
 * 作者               :                   易鑫
 * 创建日期      :                   2017.05.18
 * 描述               :                   读取配置文件基础数据类
 * 注意事项      :                   无
 * 遗留BUG :                   无
 * 修改日期      :                   
 * 修改人员      :                   
 * 修改内容      :                   
 ***********************************************************/

package com.steer.data.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties getProperties() {

        Properties p = new Properties();

        try {
            InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("RestAPIConfig.properties");

            p.load(inputStream);

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return p;
    }

    public static Properties getDbProperties() {

        Properties p = new Properties();

        try {
            InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("dev/db.properties");

            p.load(inputStream);

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return p;
    }
}
