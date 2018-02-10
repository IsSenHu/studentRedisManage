package com.husen.web.service;

import com.husen.web.dao.StudentDao;
import com.husen.web.dao.StudentDaoImpl;
import com.husen.web.pojo.Page;
import com.husen.web.pojo.Student;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDaoImpl = new StudentDaoImpl();
    /*
    * 添加学生
    * */
    public boolean addStudent(Student student) throws Exception {
        return studentDaoImpl.addStudent(student);
    }

    /*
    * 分页查看学生，按分数从高到低
    * */
    public Page<Student> pageStudent(int currrentPage, int pageSize) throws Exception {
        /*
        * 1，首先获得总记录数
        * 2，设置每页记录数
        * 3，计算从那条记录开始获取
        * 4，计算一共有多少页
        * */
        Page<Student> page = new Page<Student>();
        page.setTotal(studentDaoImpl.total());
        page.setPageSize(pageSize);
        if(page.getTotal() % 10 == 0){
            page.setTotalPage((int) (page.getTotal() / 10));
        }else{
            page.setTotalPage((int) (page.getTotal() / 10 + 1));
        }
        /*
        * currentPage必须在有效范围内，如果不在，强制为1
        * */
        if(currrentPage <= 0 || currrentPage > page.getTotalPage()){
            currrentPage = 1;
        }
        page.setCurrentPage(currrentPage);
        long offset = (currrentPage - 1) * pageSize;
        page.setPage(studentDaoImpl.pageStudent(offset, (offset + pageSize -1)));
        return page;
    }

    /*
    * 根据id查询学生
    * */
    public Student findStudentById(String studentId) throws Exception {
        return studentDaoImpl.findStudentById(studentId);
    }
    /*
    * 根据Id更新学生
    * */
    public boolean updateStudent(Student student) throws Exception {
        return studentDaoImpl.updateStudent(student);
    }

    /*
    * 根据Id删除学生
    * */
    public boolean deleteStudent(String studentId) throws Exception {
        return studentDaoImpl.deleteStudent(studentId);
    }

    /*
    * 分页查看学生，按分数从高到低
    * */
    public List<Student> pageStudent() throws Exception {
        return null;
    }
}
