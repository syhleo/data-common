package com.steer.data.common.utils;

import java.util.ArrayList;
import java.util.List;

public class IdUtil {
    public static List<String> splitStrings(String str) {
        return splitStrings(str, ",");
    }

    public static List<String> splitStrings(String ids, String splitStr) {
        if (StringUtil.isEmpty(ids))
            return null;
        if (StringUtil.isEmpty(splitStr)) {
            splitStr = ",";
        }
        String[] idS = ids.split(splitStr);
        List<String> list = new ArrayList<String>(idS.length);
        for (String id : idS) {
            if (StringUtil.isNotEmpty(id))
                try {
                    list.add(id.trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
        }
        return list;
    }
}
