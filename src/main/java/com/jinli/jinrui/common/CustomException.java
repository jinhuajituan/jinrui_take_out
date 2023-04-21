package com.jinli.jinrui.common;

/**
 * 自定义业务异常类
 */
//RuntimeException 运行时异常
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }

}
