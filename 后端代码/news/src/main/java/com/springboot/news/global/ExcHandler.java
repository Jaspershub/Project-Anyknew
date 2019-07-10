package com.springboot.news.global;

import com.springboot.news.model.JSONResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
@ControllerAdvice
public class ExcHandler {

    /**
     * 捕获参数验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(ConstraintViolationException e){
        String str="";
        for(ConstraintViolation<?> s:e.getConstraintViolations()){

            str=str+s.getMessage()+" ";

        }
        return JSONResult.exception(1001,str);
    }

    /**
     * 捕获缺少参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public String requestParameterMissing(MissingServletRequestParameterException e){
        return JSONResult.exception(1001,e.getParameterName()+"缺少参数");
    }

    /**
     * 捕获参数类型不正确异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public String requestParameterMismatch(MethodArgumentTypeMismatchException e){
        return JSONResult.exception(1001,e.getParameter().getParameterName()+"参数类型不正确");
    }

   /* *//**
     * 其他异常
     * @param e
     * @return
     *//*
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String otherException(Exception e){
        return JSONResult.exception(1001,e.getMessage());
    }*/
}
