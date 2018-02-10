package ecjtu.husen.controller;

import ecjtu.husen.pojo.Page;
import ecjtu.husen.pojo.StudentVO;
import ecjtu.husen.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11785
 */
@Controller
public class StudentController {
    /**
     * 成功
     */
    private static final Map<String, String> SUCCESS = new HashMap<>(1);
    /**
     * 失败
     */
    private static final Map<String, String> FAILE = new HashMap<>(1);
    /**
     * 姓名的最大长度
     */
    private static final int MAX_NAME_LENGTH = 40;
    /**
     * 平均分的最大值
     */
    private static final int MAX_AVG = 100;
    /**
     * 描述的最大长度
     */
    private static final int MAX_DESC_LENGTH = 255;
    static {
        SUCCESS.put("result", "ok");
        FAILE.put("result", "faile");
    }
    @Autowired
    private StudentService studentService;

    /**
     * 添加学生
     * @param studentVO
     * @return
     */
    @RequestMapping("/addStudent.action")
    public @ResponseBody Map<String, String> addStudent(StudentVO studentVO){
        Map<String, String> errors = new HashMap<>(5);
        //姓名
        String name = studentVO.getName();
        if(StringUtils.isBlank(name)){
            errors.put("nameError", "姓名不能为空！");
        }else if(StringUtils.length(name) > MAX_NAME_LENGTH){
            errors.put("nameError", "姓名长度不能大于40！");
        }
        //平均分，0-100之间，正整数
        Integer avgscore = studentVO.getAvgscore();
        if(avgscore == null){
            errors.put("avgscoreError", "平均分不能为空！");
        }else if(avgscore < 0 || avgscore > MAX_AVG){
            errors.put("avgscoreError", "平均分只能在0-100之间！");
        }
        //备注长度255
        String description = studentVO.getDescription();
        if(description == null){
            errors.put("descriptionError", "你不要来搞事情！");
        }else if (description.trim().length() > MAX_DESC_LENGTH){
            errors.put("descriptionError", "备注长度不能大于255！");
        }
        if(errors.size() > 0){
            //有错误，要重新输入
            return errors;
        }else {
            studentService.addStudent(studentVO);
            return SUCCESS;
        }
    }

    /**
     * 获得分页的信息
     * @param request
     * @param currentPage
     * @return
     * @throws Exception
     */
    @RequestMapping("/pageStudent.action")
    public String pageStudent(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer currentPage) throws Exception {
        Page<StudentVO> students = studentService.pageStudent(currentPage, 10);
        request.setAttribute("students", students);
        return "forward:/WEB-INF/views/jsp/index.jsp";
    }

    /**
     * 删除学生
     * @param studentId
     * @return
     */
    @RequestMapping("/deleteStudent.action")
    public @ResponseBody Map<String, String> deleteStudent(String studentId){
        if(studentService.deleteStudent(studentId)){
            return SUCCESS;
        }else {
            return FAILE;
        }
    }

    /**
     * 根据ID显示查询显示某个学生的信息
     * @param studentId
     * @return
     */
    @RequestMapping("/showStudentInfo.action")
    public @ResponseBody StudentVO showStudentInfo(String studentId){
        return studentService.showStudentInfo(studentId);
    }

    /**
     * 更新学生信息
     * @param studentVO
     * @return
     */
    @RequestMapping("/updateStudent.action")
    public@ResponseBody Map<String, String> updateStudent(StudentVO studentVO){
        if(studentService.updateStudent(studentVO)){
            return SUCCESS;
        }
        return FAILE;
    }
}
