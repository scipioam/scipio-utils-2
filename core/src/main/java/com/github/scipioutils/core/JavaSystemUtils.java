package com.github.scipioutils.core;

/**
 * JRE、JDK的基础工具
 *
 * @author Alan Scipio
 * create date: 2022/9/26
 */
public class JavaSystemUtils {

    /**
     * 获取Java的详细版本号，例如1.8.0_202等
     */
    public static String getVersion() {
        return System.getProperty("java.version");
    }

    /**
     * 获取Java的标准版本 ，例如：17, 11, 1.8 , 1.7 , 1.6等
     */
    public static double getSpecVersion() {
        String specVerStr = System.getProperty("java.specification.version");
        return Double.parseDouble(specVerStr);
    }

}
