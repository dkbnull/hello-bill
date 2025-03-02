package cn.wbnull.hellobill.common.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * BeanUtils
 *
 * @author null
 * @date 2024-11-17
 * @link <a href="https://github.com/dkbnull/HelloUtil">GitHub</a>
 */
public class BeanUtils {

    public static <T> T copyProperties(Object object, Class<T> clazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(object), clazz);
    }

    public static <T> List<T> copyPropertyList(Object object, Class<T> clazz) {
        return JSONArray.parseArray(JSONArray.toJSONString(object), clazz);
    }
}
