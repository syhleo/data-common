package com.steer.data.common.config;


import com.steer.data.common.utils.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * 功能说明:资源工具类
 */
public final class ConfigMgr {

    private static Logger logger = LoggerFactory.getLogger(ConfigMgr.class);
    private static Properties properties = new Properties();

    public static final String LOCATIONS = "file:./config/,file:./,classpath:/config/,classpath:/";

    public static final String APPLICATION_DEV="application-dev.properties";
    public static final String APPLICATION_test="application-test.properties";
    public static final String APPLICATION_QUARTZ="application-quartz.properties";
    public static final String APPLICATION_PRD="application-prd.properties";
    public static final String APPLICATION="application.properties";

    private static String DEFAULT_MESSAGE = null;

    static {
        try {
            List<String> locs = IdUtil.splitStrings(LOCATIONS);
            for (String location : locs) {
                properties = loadProperties(location);
                if (properties != null) {
                    break;
                }
            }
        } catch (Exception e1) {
            logger.error("配置文件加载出错", e1);
        }
    }

    public static Properties loadProperties(String location) {
        try {
            String s = "Load resource file - " + location;
            logger.info(s);
            return PropertiesLoaderUtils.loadProperties(new PathMatchingResourcePatternResolver().getResource(location + APPLICATION_DEV));
        } catch (Exception e) {
            String s = "Load resource file failed - " + location;
            logger.error(s);
            return null;
        }
    }

    public static void reloadProperties() {
        Properties reloadProperties = new Properties();
        List<String> locs = IdUtil.splitStrings(LOCATIONS);
        for (String location : locs) {
            try {
                reloadProperties = PropertiesLoaderUtils.loadProperties(new PathMatchingResourcePatternResolver().getResource(location + APPLICATION_DEV));

            } catch (Exception e) {
                String s = "Load resource file failed - ";
                logger.error(s);
            }
            if (reloadProperties != null && reloadProperties.size()>0) {
                properties = reloadProperties;
                String s = "Load resource file success - application.properties";
                logger.info(s);
                break;
            }
        }

    }

    public static String getResource(String key) {
        String message = properties.getProperty(key, DEFAULT_MESSAGE);
        try {
            message = new String(message.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return DEFAULT_MESSAGE;
        }
        return message;
    }

    public static String getResource(String key, String defaultValue) {
        String message = properties.getProperty(key, defaultValue);
        try {
            message = new String(message.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return defaultValue;
        }
        return message;
    }


    /**
     * 添加配置文件信息
     * @param filePath
     * @param content
     */
    public static void addMessageFile(String filePath, String content) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "rw");
            raf.seek(raf.length());
            raf.write(content.getBytes());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void modifyFile(String key,String value,String filepath){
        Properties pro = new Properties();
        InputStream in = null;
        try{

            in = new BufferedInputStream(new FileInputStream(filepath));
            pro.load(in);
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            //pro.put(key,value);
            pro.setProperty(key,value);
            pro.store(fileOutputStream,"这次更新的备注");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                in.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static String getPropertiesValue(String name,String key){
        Properties prop = new Properties();
        String value = "";
        InputStream fis = null;
        try {
         fis = ConfigMgr.class.getResourceAsStream("/" + name + ".properties");
            OutputStream out = new FileOutputStream("/" + name + ".properties");
         prop.load(fis);
         value = (String) prop.get(key);
         prop.setProperty(key,"leoleoleo");
            value = (String) prop.get(key);
            prop.store(out, "Update " + key + " name");
        } catch (IOException e) {
        e.printStackTrace();
         } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (Exception e2) {
                }
        }
         return value;
    }



    /**
     * 备份文件，本质就是文件的复制
     * @param srcPathStr
     * @param desPathStr
     */
    public static void BackUpFile(String srcPathStr, String desPathStr) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcPathStr);
            fos = new FileOutputStream(desPathStr);

            byte datas[] = new byte[1024 * 8];

            int len = 0;

            while ((len = fis.read(datas)) != -1) {
                fos.write(datas, 0, len);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                fis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception{
        String path = ConfigMgr.class.getClassLoader().getResource("application-dev.properties").getPath();
//        InputStream is = new FileInputStream(path);
//        properties.load(is);
        modifyFile("redis.enabled","leos",path);
        //String value=getPropertiesValue("application-dev","redis.enabled");
        //System.out.println(value);
    }

}


