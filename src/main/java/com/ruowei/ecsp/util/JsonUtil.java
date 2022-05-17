package com.ruowei.ecsp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruowei.ecsp.web.rest.dto.ForestDataEncDTO;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

    // new ObjectMapper and allow the JSON-origin-data has more data than entity needed!
    private static ObjectMapper mapper;

    public JsonUtil(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ForestDataEncDTO getEncodedTransUnit(Object o, String title) {
        return new ForestDataEncDTO(encode(o, title));
    }

    public static String encode(Object obj, String title) {
        String encodeStr = toJsonStr(obj, title);
        String result = null;
        try {
            result = DESUtil.encrypt(encodeStr);
        } catch (Exception e) {
            AssertUtil.logError(e, title);
        }
        return result;
    }

    /**
     * @param obj
     * @param title
     * @return
     * @apiNote author: czz; 转化实体为JSON字符串
     */
    public static String toJsonStr(Object obj, String title) {
        String result = null;
        if (obj == null) {
            return result;
        }
        title = "实体: " + title + " 转化为JSON字符串失败!";
        try {
            result = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            AssertUtil.logError(e, title);
        }
        return result;
    }

    /**
     * @param str
     * @param clazz
     * @param title
     * @param <T>
     * @return
     * @apiNote author: czz; 转化JSON字符串为实体
     */
    public static <T> T toEntity(String str, Class<T> clazz, String title) {
        T result = null;
        if (str == null) {
            return result;
        }
        //        title = "JSON字符串转化为实体: " + title + " 失败!";
        try {
            result = mapper.readValue(str, clazz);
        } catch (Exception e) {
            AssertUtil.logError(e, title);
        }
        return result;
    }

    /**
     * @param str
     * @param clazz
     * @param title
     * @param detail
     * @param <T>
     * @return
     * @apiNote author: czz; 核算时VM转为后台计算实体, 用来提示页面输入缺失的信息!
     */
    public static <T> T toEntity(String str, Class<T> clazz, String title, String detail) {
        T result = null;
        if (str == null) {
            return result;
        }
        title = title != null ? title : "核算失败!";
        try {
            result = mapper.readValue(str, clazz);
        } catch (Exception e) {
            AssertUtil.logError(e, title, detail);
        }
        return result;
    }

    public static <T> T toEntity(String str, TypeReference<T> type, String title, String detail) {
        T result = null;
        if (str == null) {
            return result;
        }
        try {
            result = mapper.readValue(str, type);
        } catch (Exception e) {
            AssertUtil.logError(e, title, detail);
        }
        return result;
    }

    public static <T> T toEntity(String str, TypeReference<T> type, String title) {
        T result = null;
        if (str == null) {
            return result;
        }
        title = "JSON字符串转化为实体: " + title + " 失败!";
        try {
            result = mapper.readValue(str, type);
        } catch (Exception e) {
            AssertUtil.logError(e, title);
        }
        return result;
    }

    /**
     * @param source
     * @param tClass
     * @param title
     * @param <T>
     * @return
     * @apiNote author: czz; 通过JSON实现实体的深拷贝.
     */
    public static <T> T copy(T source, Class<T> tClass, String title) {
        // 一千万次  15.3秒
        return toEntity(toJsonStr(source, title), tClass, title);
    }

    public static JsonNode toJsonNode(Object paramObject) {
        return mapper.valueToTree(paramObject);
    }
}
