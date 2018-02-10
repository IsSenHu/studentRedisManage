package com.husen.web.pojo;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable{
    //40
    private String studentId;
    //40
    private String name;
    //日期
    private Date birthday;
    //int
    private Integer avgscore;
    //255
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
