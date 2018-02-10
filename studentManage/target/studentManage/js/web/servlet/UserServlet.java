package com.husen.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user.do")
public class UserServlet extends BaseServlet {
    /*
    * 转发到登录页面
    * */
    public String loginf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "forward:/WEB-INF/jsp/login.jsp";
    }
    /*
    * 重定向到登录页面
    * */
    public String loginr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "redirect:/WEB-INF/jsp/login.jsp";
    }
    /*
   * 转发的注册页面
   * */
    public String registf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "forward:/WEB-INF/jsp/regist.jsp";
    }
    /*
    * 重定向的注册页面
    * */
    public String registr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "redirect:/WEB-INF/jsp/regist.jsp";
    }
    /*
    * 用户登录
    * */
    public void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
