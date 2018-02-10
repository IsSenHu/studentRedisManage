package ecjtu.husen.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 11785
 */
public class StudentPO implements Serializable{
    private String studentId;
    private String name;
    private Date birthday;
    private Integer avgscore;
    private String description;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(Integer avgscore) {
        this.avgscore = avgscore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", avgscore=" + avgscore +
                ", description='" + description + '\'' +
                '}';
    }
}
