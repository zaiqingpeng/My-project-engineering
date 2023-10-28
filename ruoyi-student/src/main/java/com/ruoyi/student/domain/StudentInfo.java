package com.ruoyi.student.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生基本信息对象 student_info
 * 
 * @author ruoyi
 * @date 2023-03-18
 */
public class StudentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer id;

    /** 学号 */
    @Excel(name = "学号")
    private String studentId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String studentName;

    /** 性别 */
    @Excel(name = "性别")
    private String studentGender;

    /** 班级 */
    @Excel(name = "班级")
    private String classesId;

    /** 账号 */
    @Excel(name = "账号")
    private String learningId;

    /** 密码 */
    @Excel(name = "密码")
    private String learningPassword;

    /** 得分 */
    @Excel(name = "得分")
    private BigDecimal studentScore;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setStudentId(String studentId) 
    {
        this.studentId = studentId;
    }

    public String getStudentId() 
    {
        return studentId;
    }
    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }
    public void setStudentGender(String studentGender) 
    {
        this.studentGender = studentGender;
    }

    public String getStudentGender() 
    {
        return studentGender;
    }
    public void setClassesId(String classesId) 
    {
        this.classesId = classesId;
    }

    public String getClassesId() 
    {
        return classesId;
    }
    public void setLearningId(String learningId) 
    {
        this.learningId = learningId;
    }

    public String getLearningId() 
    {
        return learningId;
    }
    public void setLearningPassword(String learningPassword) 
    {
        this.learningPassword = learningPassword;
    }

    public String getLearningPassword() 
    {
        return learningPassword;
    }
    public void setStudentScore(BigDecimal studentScore) 
    {
        this.studentScore = studentScore;
    }

    public BigDecimal getStudentScore() 
    {
        return studentScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentId", getStudentId())
            .append("studentName", getStudentName())
            .append("studentGender", getStudentGender())
            .append("classesId", getClassesId())
            .append("learningId", getLearningId())
            .append("learningPassword", getLearningPassword())
            .append("studentScore", getStudentScore())
            .toString();
    }
}
