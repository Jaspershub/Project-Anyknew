package com.springboot.news.interceptor;

import com.springboot.news.model.JSONResult;
import com.springboot.news.global.ExceptionMessage;
import com.springboot.news.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class LoginCheckInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
       String token = request.getParameter("token");

        if (token != null && !"".equals(token)) {

            if (RedisUtil.hasKey(token)) {
                return true;
            } else {
                returnExceptionMsg(response, ExceptionMessage.NO_LOGIN);
                return false;
            }

        } else {
            returnExceptionMsg(response, ExceptionMessage.TOKEN_BLANK);
            return false;
        }


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    public void returnExceptionMsg(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSONResult.exception(1001, msg));
        printWriter.close();
        printWriter.flush();
    }

}
