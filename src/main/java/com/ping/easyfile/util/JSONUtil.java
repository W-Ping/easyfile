package com.ping.easyfile.util;

import com.alibaba.fastjson.JSON;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 18:46
 */
public class JSONUtil {
    public static String objectToString(Object object){
        return  JSON.toJSONString(object);
    }
}
