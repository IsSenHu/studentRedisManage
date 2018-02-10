package com.husen.web.dao;

import com.husen.util.RedisUtil;
import com.husen.util.SerializeUtil;
import com.husen.web.pojo.Student;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentDaoImpl implements StudentDao {
    /*
    * 添加学生
    * 1，用一个hash来存学生，key相当于表名，键值对的键为学生Id，值为学生的对象（序列化）
    * 2，再用一个sorted set来存学生的平均分排名，key为表名，值为学生Id，分数为平均分
    * @filed:STUDENT_TABLE
    * @filed:SCORE_TABLE
    * */
    private static final String STUDENT_TABLE = "t_student";
    private static final String SCORE_TABLE = "t_score";


    @Override
    public boolean addStudent(Student student) throws Exception {
        //先存学生
        Jedis jedis = RedisUtil.getJedis();
        long result1 = jedis.hset(STUDENT_TABLE.getBytes(), student.getStudentId().getBytes(), SerializeUtil.serialize(student));
        if(result1 == 1){
            //再存成绩
            long result2 = jedis.zadd(SCORE_TABLE, (double)student.getAvgscore(), student.getStudentId());
            if(result2 == 1){
                jedis.close();
                return true;
            }else {
                //存失败了，回滚事务
                jedis.hdel(STUDENT_TABLE.getBytes(),student.getStudentId().getBytes());
                jedis.close();
            }
        }
        return false;
    }

    /*
    * 分页查询学生，按成绩从高到底排序
    * 1，先查询成绩排序表，再根据Id去查学生hash表
    * 2，得到集合返回
    * */
    @Override
    public List<Student> pageStudent(long offset, long end) throws Exception {
        Jedis jedis = RedisUtil.getJedis();
        Set<String> ids = jedis.zrevrange(SCORE_TABLE, offset, end);
        List<Student> students = new ArrayList<Student>();
        for(String studentId : ids){
            byte[] student = jedis.hget(STUDENT_TABLE.getBytes(), studentId.getBytes());
            students.add((Student) SerializeUtil.unserialize(student));
        }
        jedis.close();
        return students;
    }

    /*
    * 获得总记录数
    * */
    @Override
    public Long total() throws Exception {
        Jedis jedis = RedisUtil.getJedis();
        long total = jedis.hlen(STUDENT_TABLE.getBytes());
        jedis.close();
        return total;
    }

    /*
    * 根据学生id查询学生
    * */
    @Override
    public Student findStudentById(String studentId) throws Exception {
        Jedis jedis = RedisUtil.getJedis();
        Student student = (Student) SerializeUtil.unserialize(jedis.hget(STUDENT_TABLE.getBytes(), studentId.getBytes()));
        jedis.close();
        return student;
    }

    /*
    * 修改学生
    * */
    @Override
    public boolean updateStudent(Student student) throws Exception {
        Jedis jedis = RedisUtil.getJedis();
        Student student1 = findStudentById(student.getStudentId());
        try{
            jedis.hset(STUDENT_TABLE.getBytes(), student.getStudentId().getBytes(), SerializeUtil.serialize(student));
            try{
                jedis.zadd(SCORE_TABLE, student.getAvgscore(), student.getStudentId());
                jedis.close();
                return true;
            }catch (Exception e){
                //修改失败了，回滚事务
                e.printStackTrace();
                jedis.hset(STUDENT_TABLE.getBytes(), student1.getStudentId().getBytes(), SerializeUtil.serialize(student1));
                jedis.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /*
    * 根据Id删除学生，要删两个表
    * */
    @Override
    public boolean deleteStudent(String studentId) throws Exception {
        Jedis jedis = RedisUtil.getJedis();
        Student student = findStudentById(studentId);
        long result1 = jedis.hdel(STUDENT_TABLE.getBytes(), studentId.getBytes());
        if(result1 == 1){
            long result2 = jedis.zrem(SCORE_TABLE, studentId);
            if(result2 == 1){
                jedis.close();
                return true;
            }else {
                //回滚事务
                jedis.hset(STUDENT_TABLE.getBytes(), studentId.getBytes(), SerializeUtil.serialize(student));
            }
        }
        return false;
    }

    /*
    * 分页查询学生，按成绩从高到底排序
    * 1，先查询成绩排序表，再根据Id去查学生hash表
    * 2，得到集合返回
    * */
    public List<Student> pageStudent() throws Exception {
        Jedis jedis = RedisUtil.getJedis();
        Set<String> ids = jedis.zrevrange(SCORE_TABLE, 0, 9);
        List<Student> students = new ArrayList<Student>();
        for(String studentId : ids){
            byte[] student = jedis.hget(STUDENT_TABLE.getBytes(), studentId.getBytes());
            students.add((Student) SerializeUtil.unserialize(student));
        }
        return students;
    }
}
