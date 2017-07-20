package org.bumishi.admin.infrastructure;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * Created by xieqiang on 2017/1/8.
 */
public class RequestMapToJsonUtil {

    public static String toJson(Map<String, String[]> params) {
        Map<String,String> dataMap=new HashedMap(params.size()-1);
        for(String key : params.keySet()) {
            if (key.equals("_csrf")) {
                continue;
            }
            dataMap.put(key,params.get(key)[0]);
        }
        return JSON.toJSONString(dataMap);
    }

}
