package com.husen.web.service;

import com.husen.web.pojo.Page;
import com.husen.web.pojo.Student;


public interface StudentService {
    boolean addStudent(Student student) throws Exception;
    Page<Student> pageStudent(int currrentPage, int pageSize) throws Exception;
    Student findStudentById(String studentId) throws Exception;
    boolean updateStudent(Student student) throws Exception;
    boolean deleteStudent(String studentId) throws Exception;
}
