package com.husen.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
/*
* BaseServlet
* */
public class BaseServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("method");
        if(methodName == null || methodName.trim().isEmpty()){
            throw new RuntimeException("您没有传递method参数，无法确定要调用的方法");
        }
        Class clazz = this.getClass();
        Method method = null;
        try {
            method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("您要调用的方法"+methodName+"(HttpServletRequest,HttpServletResponse) 不存在");
        }
        String text = null;//获取返回值
        try {
            text = (String) method.invoke(this, request, response);
            /*
            * 获取请求处理方法后的字符串，它表示转发或重定向的路径
            * 如果返回值不包含“:”，表示默认方式转发
            * 如果返回值中包含“;”，截取为两部分，前一部分表示标识，forward表示转发，redirect表示重定向
            * 如果返回的字符串是null，或者“”表示什么也不干
            * */
        } catch (Exception e) {
            System.out.println("您调用的方法"+methodName+"(HttpServletRequest,HttpServletResponse) 内部抛出了异常");
            throw new RuntimeException(e);
        }
        if(text == null || text.trim().isEmpty()) return;
        if(!text.contains(":")){
            request.getRequestDispatcher(text).forward(request, response);
        }else{
            int index = text.indexOf(":");
            String howDo = text.substring(0, index);
            String path = text.substring(index+1);
            if(howDo.equalsIgnoreCase("forward")){
                request.getRequestDispatcher(path).forward(request, response);
            }else if(text.equalsIgnoreCase("redirect")){
                response.sendRedirect(path);
            }else {
                throw new RuntimeException("只能forward和redirect");
            }
        }
    }
}
