package com.github.scipioutils.core;

/**
 * 异常信息帮助类
 * @author Alan Scipio
 * @since 2021/4/13
 */
public class ExceptionHelper {

    /**
     * 构造完整的异常堆栈信息。
     * 包括第一行的异常类、信息和后续诸多行的异常位置
     */
    public static String getFullMsg(Throwable ex) {
        String result;
        String stackTrace = getStackTrace(ex);
        String exceptionType = ex.toString();
        String exceptionMessage = ex.getMessage();

        result = String.format("%s : %s \r\n %s", exceptionType, exceptionMessage, stackTrace);
        return result;
    }

    /**
     * 获取异常堆栈信息
     * @return 诸多行的异常位置
     */
    public static String getStackTrace(Throwable ex){
        StackTraceElement[] traceElements = ex.getStackTrace();
        StringBuilder traceBuilder = new StringBuilder();
        if (traceElements != null && traceElements.length > 0) {
            for (StackTraceElement traceElement : traceElements) {
                traceBuilder.append("\tat ").append(traceElement.toString());
                traceBuilder.append("\n");
            }
        }
        return traceBuilder.toString();
    }

}
