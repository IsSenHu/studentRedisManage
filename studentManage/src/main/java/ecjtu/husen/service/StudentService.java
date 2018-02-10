package ecjtu.husen.service;

import ecjtu.husen.pojo.Page;
import ecjtu.husen.pojo.StudentVO;

/**
 * @author 11785
 */
public interface StudentService {
    /**
     * 添加学生
     * @param studentVO
     */
    public void addStudent(StudentVO studentVO);

    /**
     * 分页查询学生
     * @param currrentPage
     * @param pageSize
     * @return
     */
    public Page<StudentVO> pageStudent(int currrentPage, int pageSize);

    /**
     * 删除学生
     * @param studentId
     * @return
     */
    public boolean deleteStudent(String studentId);

    /**
     * 根据ID查看学生信息
     * @param studentId
     * @return
     */
    public StudentVO showStudentInfo(String studentId);

    /**
     * 修改学生信息
     * @param studentVO
     * @return
     */
    public boolean updateStudent(StudentVO studentVO);
}
