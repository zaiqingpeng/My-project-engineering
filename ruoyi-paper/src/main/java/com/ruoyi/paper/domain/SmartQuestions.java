package com.ruoyi.paper.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 智能出卷对象 smart_questions
 *
 * @author ruoyi
 * @date 2023-04-03
 */
public class SmartQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 试题类型 */
    @Excel(name = "试题类型")
    private String questionType;

    /** 试题 */
    @Excel(name = "试题")
    private String question;

    /** 选项1 */
    @Excel(name = "选项1")
    private String optionA;

    /** 选项2 */
    @Excel(name = "选项2")
    private String optionB;

    /** 选项3 */
    @Excel(name = "选项3")
    private String optionC;

    /** 选项4 */
    @Excel(name = "选项4")
    private String optionD;

    /** 答案 */
    @Excel(name = "答案")
    private String answer;

    /** 分值 */
    @Excel(name = "分值")
    private String perScore;

    /** 难度 */
    @Excel(name = "难度")
    private String questionLevel;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:MM:SS")
    private Date beginTime;


    /** 截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd HH:MM:SS")
    private Date endTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setQuestionType(String questionType)
    {
        this.questionType = questionType;
    }

    public String getQuestionType()
    {
        return questionType;
    }
    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getQuestion()
    {
        return question;
    }
    public void setOptionA(String optionA)
    {
        this.optionA = optionA;
    }

    public String getOptionA()
    {
        return optionA;
    }
    public void setOptionB(String optionB)
    {
        this.optionB = optionB;
    }

    public String getOptionB()
    {
        return optionB;
    }
    public void setOptionC(String optionC)
    {
        this.optionC = optionC;
    }

    public String getOptionC()
    {
        return optionC;
    }
    public void setOptionD(String optionD)
    {
        this.optionD = optionD;
    }

    public String getOptionD()
    {
        return optionD;
    }
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public String getAnswer()
    {
        return answer;
    }
    public void setPerScore(String perScore)
    {
        this.perScore = perScore;
    }

    public String getPerScore()
    {
        return perScore;
    }
    public void setQuestionLevel(String questionLevel)
    {
        this.questionLevel = questionLevel;
    }

    public String getQuestionLevel()
    {
        return questionLevel;
    }

    public void setendTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getendTime()
    {
        return endTime;
    }
    public void setbeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getbeginTime()
    {
        return beginTime;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("questionType", getQuestionType())
            .append("question", getQuestion())
            .append("optionA", getOptionA())
            .append("optionB", getOptionB())
            .append("optionC", getOptionC())
            .append("optionD", getOptionD())
            .append("answer", getAnswer())
            .append("perScore", getPerScore())
            .append("questionLevel", getQuestionLevel())
            .append("beginTime", getbeginTime())
            .append("endTime", getendTime())
            .toString();
    }
}
