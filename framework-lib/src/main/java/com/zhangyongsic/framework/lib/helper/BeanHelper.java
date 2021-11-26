package com.zhangyongsic.framework.lib.helper;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanHelper extends BeanUtils {

    public static <T> T copyProperties(Object object, Class<T> tClass) {
        if (object != null) {
            return JSON.parseObject(JSON.toJSONString(object), tClass);
        }
        return null;
    }

    public static <T> List<T> copyProperties(List list, Class<T> tClass) {
        if (list != null && list.size() != 0) {
            return JSON.parseArray(JSON.toJSONString(list), tClass);
        }
        return new ArrayList<>();
    }
}
