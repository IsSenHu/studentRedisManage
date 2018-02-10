package ecjtu.husen.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import ecjtu.husen.pojo.StudentPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 11785
 */
@Repository
public class StudentDaoImpl implements StudentDao{
    private static final String STUDENT_TABLE = "t_student2";
    private static final String SCORE_TABLE = "t_score2";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加学生
     * 1，用一个名为t_student2的hash表来存学生信息，key为学生ID，value为json序列化的对象字符串
     * 2，用一个sorted zet来名为t_score来存学生的分数，score为学生的分数，key为学生的ID
     * @param studentPO
     */
    @Override
    public void addStudent(StudentPO studentPO) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        hashOperations.put(STUDENT_TABLE, studentPO.getStudentId(), JSON.toJSONString(studentPO));
        zSetOperations.add(SCORE_TABLE, studentPO.getStudentId(), studentPO.getAvgscore());
    }

    /**
     * 计算总共有多少学生
     * @return
     */
    @Override
    public Long total() {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        return hashOperations.size(STUDENT_TABLE);
    }

    /**
     * 分页查询学生，按成绩从高到底排序
     * 1，先查询成绩排序表，再根据Id去查学生hash表
     * 2，得到集合返回* @param offset
     * @param l
     * @return
     */
    @Override
    public List<StudentPO> pageStudent(long offset, long l) {
        //得到hash操作对象和sorted set操作对象
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        //Redis Zrevrange 命令返回有序集中，指定区间内的成员，从大到小排列。
        Set<String> ids = zSetOperations.reverseRange(SCORE_TABLE, offset, l);
        List<StudentPO> students = new ArrayList<>();
        for(String studentId : ids){
            students.add(JSON.parseObject(hashOperations.get(STUDENT_TABLE, studentId), new TypeReference<StudentPO>(){}));
        }
        return students;
    }

    /**
     * 删除学生
     * 首先删除hash表中的学生信息，再删除分数表中的分数信息。
     * @param studentId
     * @return
     */
    @Override
    public boolean deleteStudent(String studentId) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        try {
            hashOperations.delete(STUDENT_TABLE, studentId);
            zSetOperations.remove(SCORE_TABLE, studentId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 根据学生ID来查询学生信息
     * @param studentId
     * @return
     */
    @Override
    public StudentPO showStudentInfo(String studentId) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        return JSON.parseObject(hashOperations.get(STUDENT_TABLE, studentId), new TypeReference<StudentPO>(){});
    }

    /**
     * 更新学生
     * 首先更新学生表中的学生信息，再更新分数表中的分数信息。
     * @param studentPO
     * @return
     */
    @Override
    public boolean updateStudent(StudentPO studentPO) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        try{
            hashOperations.put(STUDENT_TABLE, studentPO.getStudentId(), JSON.toJSONString(studentPO));
            zSetOperations.add(SCORE_TABLE, studentPO.getStudentId(), studentPO.getAvgscore());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
