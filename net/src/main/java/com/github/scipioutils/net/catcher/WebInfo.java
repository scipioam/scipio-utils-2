package com.github.scipioutils.net.catcher;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抓取的网页结果集
 *
 * @author Alan Scipio
 * create date: 2022/10/12
 */
@Data
@NoArgsConstructor
public class WebInfo {

    private URL url;

    /**
     * 网页的HTTP请求码
     */
    private Integer responseCode;

    /**
     * 原始的响应体内容网页的html内容
     */
    private String originalResponse;

    /**
     * 网页title标签的内容
     */
    private String webTitle;

    /**
     * 抓取结果集
     */
    private CatchResult catchResult = new CatchResult();

    public WebInfo(URL url) {
        this.url = url;
    }

    public WebInfo setResultStr(List<String> list) {
        catchResult.setStrList(list);
        return this;
    }

    public WebInfo addResultStr(String result) {
        if (catchResult.getStrList() == null) {
            catchResult.setStrList(new ArrayList<>());
        }
        catchResult.getStrList().add(result);
        return this;
    }

    public WebInfo setResultList(List<Object> list) {
        catchResult.setObjList(list);
        return this;
    }

    public WebInfo addResult(Object result) {
        if (catchResult.getObjList() == null) {
            catchResult.setObjList(new ArrayList<>());
        }
        catchResult.getObjList().add(result);
        return this;
    }

    public WebInfo setResultMap(Map<String,Object> map) {
        catchResult.setObjMap(map);
        return this;
    }

    public WebInfo addResult(String key, Object result) {
        if (catchResult.getObjMap() == null) {
            catchResult.setObjMap(new HashMap<>());
        }
        catchResult.getObjMap().put(key, result);
        return this;
    }

    public WebInfo setResult(Object result) {
        catchResult.setObj(result);
        return this;
    }

}
