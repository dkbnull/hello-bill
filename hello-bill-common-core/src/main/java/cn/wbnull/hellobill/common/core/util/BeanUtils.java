package cn.wbnull.hellobill.common.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.List;

/**
 * BeanUtils
 *
 * @author null
 * @date 2024-11-17
 * @link <a href="https://github.com/dkbnull/HelloUtil">GitHub</a>
 */
public class BeanUtils {

    private BeanUtils() {
    }

    public static <T> T copyProperties(Object source, Class<T> target) {
        return JSONObject.parseObject(JSONObject.toJSONString(source), target);
    }

    public static <T> List<T> copyToList(Collection<?> sources, Class<T> target) {
        return JSONArray.parseArray(JSONArray.toJSONString(sources), target);
    }
}
