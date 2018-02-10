package com.husen.web.dao;

import com.husen.web.pojo.Student;

import java.util.List;

public interface StudentDao {
    boolean addStudent(Student student) throws Exception;
    List<Student> pageStudent(long offset, long end) throws Exception;
    Long total() throws Exception;
    Student findStudentById(String studentId) throws Exception;
    boolean updateStudent(Student student) throws Exception;
    boolean deleteStudent(String studentId) throws Exception;
}
