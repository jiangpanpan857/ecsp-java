package com.ruowei.ecsp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * @param url         POST请求地址
     * @param requestBody 请求体
     * @param tClass      返回数据类型class
     * @param <T>         返回数据类型
     * @return POST请求后的数据，转为指定类型
     * @apiNote author: czz; 获取碳交易rawDTO，为后续解析存储做准备
     */
    public static <T> T postForEntity(String url, Object requestBody, Class<T> tClass) {
        String errorMsg = "解析碳交易数据失败";
        String errorMsg2 = "解析碳交易数据失败，请求结果为空:\n";
        try {
            ResponseEntity<String> resultStr = restTemplate.postForEntity(url, requestBody, String.class);
            errorMsg2 += resultStr.getBody();
            return JsonUtil.toEntity(resultStr.getBody(), tClass, errorMsg);
        } catch (Exception e) {
            e.printStackTrace();
            AssertUtil.logErrorInfo(errorMsg2);
            AssertUtil.logErrorInfo(e.getMessage());
        }
        return null;
    }

    /**
     * @param url           POST请求地址
     * @param requestBody   请求体
     * @param typeReference 返回数据类型class
     * @param <T>           返回数据类型
     * @return POST请求后的数据，转为指定类型
     * @apiNote author: czz; 用于碳交易数据解析, 捕获异常后忽略不处理，继续下一次请求【防止错误数据干扰】
     */
    public static <T> T postForEntity(String url, Object requestBody, TypeReference<T> typeReference) {
        try {
            ResponseEntity<String> resultStr = restTemplate.postForEntity(url, requestBody, String.class);
            return JsonUtil.toEntity(resultStr.getBody(), typeReference, "解析碳交易数据失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postForEntity(String url, Object requestBody) {
        try {

            HttpEntity<String> requestEntity = getBrowserHeadersEntity(JsonUtil.toJsonStr(requestBody, "获取TOKEN失败"));
            ResponseEntity<String> resultStr = restTemplate.postForEntity(url, requestEntity, String.class);
            return resultStr.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url    get请求
     * @param tClass 返回数据直接转为指定类型
     * @param <T>    指定类型
     * @return 返回指定类型的数据
     * @apiNote author: czz; 最开始欲根据网页直接转为具体类型，但是碳交易数据网页结构不同，所以改为直接转为拼接字符串【for复用性】
     */
    public static <T> T getForEntity(String url, Class<T> tClass) {
        try {
            ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
            return JsonUtil.toEntity(resultStr.getBody(), tClass, "解析碳交易数据失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getForEntity(String url) {
        try {
            ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
            return resultStr.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url get请求
     * @return 返回字符串类型的网页内容
     * @apiNote author: czz; 目前爬取碳交易数据，未启用
     */
    public static String getForHtmlString(String url) {
        try {
            HttpEntity<String> requestEntity = getBrowserHeadersEntity(null);
            ResponseEntity<String> resultStr = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            String body = resultStr.getBody();
            if (body.contains("err")) return null;
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param type [POST | GET | GET_Language]
     * @return 返回带浏览器标识的HttpHeader，根据请求类型有细项调整
     * @apiNote author: czz; 主要为restTemplate增加浏览器标识，以正常访问。 其中：【POST请求，设置了ContentType】
     */
    public static HttpHeaders getBrowserHeaders(String type) {
        HttpHeaders headers = new HttpHeaders();
        if (type.equals("POST")) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        // 防止403，增加浏览器标志
        headers.add(
            "user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36"
        );
        // 为解决html内容乱码，实际效果无用，无任何影响可注释
        if (type.equals("GET_Language")) {
            headers.add("Accept-Language", "zh-CN,zh;q=0.9");
        }
        return headers;
    }

    /**
     * @param requestBody 请求体[请求类型根据是否为空判断]
     * @return 返回带浏览器标识的HttpEntity，根据请求体有细项调整
     * @apiNote author: czz;
     */
    public static HttpEntity<String> getBrowserHeadersEntity(String requestBody) {
        return getBrowserHeadersEntity(requestBody, requestBody == null ? "GET" : "POST");
    }

    /**
     * @param requestBody 请求体
     * @param type        [GET | POST]
     * @return 返回带浏览器标识的HttpEntity，根据请求类型有细项调整
     * @apiNote author: czz;
     */
    public static HttpEntity<String> getBrowserHeadersEntity(String requestBody, String type) {
        return new HttpEntity<>(requestBody, getBrowserHeaders(type));
    }
}
