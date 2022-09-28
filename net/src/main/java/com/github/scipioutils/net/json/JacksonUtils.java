package com.github.scipioutils.net.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.scipioutils.core.exception.ParseException;

/**
 * 快速调用Jackson库的便捷工具
 *
 * @author Alan Scipio
 * create date: 2022/9/28
 */
public class JacksonUtils {

    //========================================== ↓↓↓↓↓↓ Base APIs ↓↓↓↓↓↓ ==========================================

    /**
     * 序列化
     *
     * @param excludeNull 序列化时排除null的属性
     */
    public static String toJson(ObjectMapper objectMapper, Object obj, boolean excludeNull) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            //序列化时排除null的属性
            if (excludeNull) {
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            }
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ParseException(e);
        }
    }

    public static String toJson(Object obj, boolean excludeNull) {
        return toJson(null, obj, excludeNull);
    }

    public static String toJson(Object obj) {
        return toJson(null, obj, false);
    }

    /**
     * 反序列化
     */
    public static <T> T fromJson(ObjectMapper objectMapper, String json, Class<T> type) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new ParseException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return fromJson(null, json, type);
    }

    //========================================== ↓↓↓↓↓↓ Advanced APIs ↓↓↓↓↓↓ ==========================================

    /**
     * 格式化的序列化
     */
    public static String toPrettyJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true);
        return toJson(objectMapper, obj, true);
    }

    /**
     * 读取指定的json节点
     */
    public static JsonNode readNode(String json, String nodeName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode objNode = objectMapper.readValue(json, ObjectNode.class);
            return objNode.findValue(nodeName);
        } catch (JsonProcessingException e) {
            throw new ParseException(e);
        }
    }

    /**
     * 读取指定的json节点 为字符串结果
     */
    public static String readNodeAsText(String json, String nodeName) {
        JsonNode node = readNode(json, nodeName);
        return node.asText();
    }

}
