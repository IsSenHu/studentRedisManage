package ecjtu.husen.dao;

import ecjtu.husen.pojo.StudentPO;

import java.util.List;

/**
 * @author 11785
 */
public interface StudentDao {
    /**
     * 添加学生
     * @param studentPO
     */
    public void addStudent(StudentPO studentPO);

    /**
     * 查看总共有多少个学生
     * @return
     */
    public Long total();

    /**
     * 分页查询学生
     * @param offset
     * @param l
     * @return
     */
    public List<StudentPO> pageStudent(long offset, long l);

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
    public StudentPO showStudentInfo(String studentId);

    /**
     * 修改学生信息
     * @param studentPO
     * @return
     */
    public boolean updateStudent(StudentPO studentPO);
}
