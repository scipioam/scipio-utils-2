package com.github.scipioutils.core;

import com.github.scipioutils.core.exception.TestException;

/**
 * 测试相关工具类
 *
 * @author alan scipio
 * @since 2021/7/23
 */
public class Test {

    private Test() {
    }

    /**
     * 专门用于测试时中断
     *
     * @param isNeedBreak 是否需要中断，true代表需要中断
     */
    public static void breakHere(boolean isNeedBreak, String exceptionMsg) {
        if (isNeedBreak) {
            throw new TestException(exceptionMsg);
        }
    }

    public static void breakHere(String exceptionMsg) {
        breakHere(true, exceptionMsg);
    }

    public static void breakHere() {
        breakHere(true, "***************** break here for specific test purpose *****************");
    }

}
