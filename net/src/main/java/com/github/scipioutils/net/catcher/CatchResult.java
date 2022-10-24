package com.github.scipioutils.net.catcher;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 抓取结果
 *
 * @author Alan Scipio
 * create date: 2022/10/12
 */
@Data
public class CatchResult {

    /**
     * 抓取的字符串结果list
     */
    private List<String> strList;

    /**
     * 抓取的对象结果list
     */
    private List<Object> objList;

    /**
     * 抓取的结果map
     */
    private Map<String,Object> objMap;

    /**
     * 抓取的结果对象
     */
    private Object obj;



}
