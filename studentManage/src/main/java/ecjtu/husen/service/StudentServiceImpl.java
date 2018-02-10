package ecjtu.husen.service;

import ecjtu.husen.dao.StudentDao;
import ecjtu.husen.pojo.Page;
import ecjtu.husen.pojo.StudentPO;
import ecjtu.husen.pojo.StudentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 11785
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Override
    public void addStudent(StudentVO studentVO) {
        StudentPO studentPO = new StudentPO();
        BeanUtils.copyProperties(studentVO, studentPO);
        studentPO.setStudentId(UUID.randomUUID().toString());
        studentDao.addStudent(studentPO);
    }

    @Override
    public Page<StudentVO> pageStudent(int currrentPage, int pageSize) {
        /*
        * 1，首先获得总记录数
        * 2，设置每页记录数
        * 3，计算从那条记录开始获取
        * 4，计算一共有多少页
        * */
        Page<StudentVO> page = new Page<StudentVO>();
        page.setTotal(studentDao.total());
        page.setPageSize(pageSize);
        if(page.getTotal() % pageSize == 0){
            page.setTotalPage((int) (page.getTotal() / pageSize));
        }else{
            page.setTotalPage((int) (page.getTotal() / pageSize + 1));
        }
        /*
        * currentPage必须在有效范围内，如果不在，强制为1
        * */
        if(currrentPage <= 0 || currrentPage > page.getTotalPage()){
            currrentPage = 1;
        }
        page.setCurrentPage(currrentPage);
        long offset = (currrentPage - 1) * pageSize;
        List<StudentVO> studentVOS = new ArrayList<>();
        for (StudentPO studentPO :studentDao.pageStudent(offset, (offset + pageSize -1))){
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(studentPO, studentVO);
            studentVOS.add(studentVO);
        }
        page.setPage(studentVOS);
        return page;
    }

    @Override
    public boolean deleteStudent(String studentId) {
        return studentDao.deleteStudent(studentId);
    }

    @Override
    public StudentVO showStudentInfo(String studentId) {
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(studentDao.showStudentInfo(studentId), studentVO);
        return studentVO;
    }

    @Override
    public boolean updateStudent(StudentVO studentVO) {
        StudentPO studentPO = new StudentPO();
        BeanUtils.copyProperties(studentVO, studentPO);
        return studentDao.updateStudent(studentPO);
    }
}
