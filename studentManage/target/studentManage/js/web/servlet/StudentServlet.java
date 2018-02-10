package com.husen.web.servlet;

import com.husen.web.pojo.Page;
import com.husen.web.pojo.Student;
import com.husen.web.service.StudentService;
import com.husen.web.service.StudentServiceImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
@WebServlet("/student.do")
public class StudentServlet extends BaseServlet {
    private StudentService studentServiceImpl = new StudentServiceImpl();
    /*
    * 学生信息字段验证,验证通过才可进行添加
    * */
    public void addStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, String> errors = new HashMap<String, String>();
        //姓名
        String name = request.getParameter("name");
        if(name == null || name.trim().isEmpty()){
            errors.put("nameError", "姓名不能为空！");
        }else if (name.trim().length() > 40){
            errors.put("nameError", "姓名长度不能大于40！");
        }
        //生日
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayForm = request.getParameter("birthday");
        Date birthday = null;
        if(birthdayForm == null || birthdayForm.trim().isEmpty()){
            errors.put("birthdayError", "日期不能为空！");
        }else {
            try {
                birthday = dateFormat.parse(birthdayForm);
            } catch (ParseException e) {
                errors.put("birthdayError", "日期格式不正确！");
            }
        }
        //平均分，0-100之间，正整数
        String avgscoreForm = request.getParameter("avgscore");
        Integer avgscore = null;
        if(avgscoreForm == null || avgscoreForm.trim().isEmpty()){
            errors.put("avgscoreError", "平均分不能为空！");
        }else if (!Pattern.matches("^[1-9]\\d*|0$", avgscoreForm)){
            errors.put("avgscoreError", "平均分只能为正整数！");
        }else {
            avgscore = Integer.parseInt(avgscoreForm);
            if(avgscore < 0 || avgscore > 100){
                errors.put("avgscoreError", "平均分只能在0-100之间！");
            }
        }
        //备注长度255
        String description = request.getParameter("description");
        if(description == null){
            errors.put("descriptionError", "你不要来搞事情！");
        }else if (description.trim().length() > 255){
            errors.put("descriptionError", "备注长度不能大于255！");
        }
        if(errors.size() > 0){
            //有错误，要重新输入
            JSONObject jsonObject = JSONObject.fromObject(errors);

            response.getWriter().print(jsonObject);
            return;
        }else {
            //输入无错误，开始保存学生
            Student student = new Student();
            student.setStudentId(UUID.randomUUID().toString());
            student.setName(name);
            student.setBirthday(birthday);
            student.setAvgscore(avgscore);
            student.setDescription(description);
            boolean result = false;
            try {
                result = studentServiceImpl.addStudent(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(result){
                //添加成功
                response.getWriter().print("ok");
            }else {
                //发生未知错误，添加失败
                response.getWriter().print("no");
            }
        }
    }

    public String pageStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String currentPage = request.getParameter("currentPage");
        if(currentPage == null){
            currentPage = "1";
        }
        Page<Student> students = studentServiceImpl.pageStudent(Integer.parseInt(currentPage), 10);
        request.setAttribute("students", students);
        return "forward:/WEB-INF/jsp/home/index.jsp";
    }

    public void findStudentById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String studentId = request.getParameter("studentId");
        Student student = studentServiceImpl.findStudentById(studentId);
        response.getWriter().print(JSONObject.fromObject(student));
    }

    public void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, String> errors = new HashMap<String, String>();
        //姓名
        String name = request.getParameter("name");
        if(name == null || name.trim().isEmpty()){
            errors.put("nameError", "姓名不能为空！");
        }else if (name.trim().length() > 40){
            errors.put("nameError", "姓名长度不能大于40！");
        }
        //生日
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayForm = request.getParameter("birthday");
        Date birthday = null;
        if(birthdayForm == null || birthdayForm.trim().isEmpty()){
            errors.put("birthdayError", "日期不能为空！");
        }else {
            try {
                birthday = dateFormat.parse(birthdayForm);
            } catch (ParseException e) {
                errors.put("birthdayError", "日期格式不正确！");
            }
        }
        //平均分，0-100之间，正整数
        String avgscoreForm = request.getParameter("avgscore");
        Integer avgscore = null;
        if(avgscoreForm == null || avgscoreForm.trim().isEmpty()){
            errors.put("avgscoreError", "平均分不能为空！");
        }else if (!Pattern.matches("^[1-9]\\d*|0$", avgscoreForm)){
            errors.put("avgscoreError", "平均分只能为正整数！");
        }else {
            avgscore = Integer.parseInt(avgscoreForm);
            if(avgscore < 0 || avgscore > 100){
                errors.put("avgscoreError", "平均分只能在0-100之间！");
            }
        }
        //备注长度255
        String description = request.getParameter("description");
        if(description == null){
            errors.put("descriptionError", "你不要来搞事情！");
        }else if (description.trim().length() > 255){
            errors.put("descriptionError", "备注长度不能大于255！");
        }
        if(errors.size() > 0){
            //有错误，要重新输入
            JSONObject jsonObject = JSONObject.fromObject(errors);
            response.getWriter().print(jsonObject);
            return;
        }else {
            //输入无错误，开始修改学生
            Student student = new Student();
            student.setStudentId(request.getParameter("studentId"));
            student.setName(name);
            student.setBirthday(birthday);
            student.setAvgscore(avgscore);
            student.setDescription(description);
            boolean result = false;
            try {
                result = studentServiceImpl.updateStudent(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(result){
                //修改成功
                response.getWriter().print("ok");
            }else {
                //发生未知错误，修改失败
                response.getWriter().print("no");
            }
        }
    }

    public void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        try {
            boolean result = studentServiceImpl.deleteStudent(request.getParameter("studentId"));
            if(result == true){
                //删除成功
                response.getWriter().print("ok");
            }else {
                //发生未知异常，删除失败
                response.getWriter().print("no");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
