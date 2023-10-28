package com.ruoyi.questions.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 多选题题库对象 multiply_questions
 *
 * @author ruoyi
 * @date 2023-03-19
 */
public class MultiplyQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题号 */
    private Integer id;

    /** 试题 */
    @Excel(name = "试题")
    private String question;

    /** 选项A */
    @Excel(name = "A选项")
    private String questionA;

    /** 选项B */
    @Excel(name = "B选项")
    private String questionB;

    /** 选项C */
    @Excel(name = "C选项")
    private String questionC;

    /** 选项D */
    @Excel(name = "D选项")
    private String questionD;

    /** 答案 */
    @Excel(name = "答案")
    private String mulAnswer;

    /** 难度 */
    @Excel(name = "难度")
    private String questionLevel;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getQuestion()
    {
        return question;
    }
    public void setQuestionA(String questionA)
    {
        this.questionA = questionA;
    }

    public String getQuestionA()
    {
        return questionA;
    }
    public void setQuestionB(String questionB)
    {
        this.questionB = questionB;
    }

    public String getQuestionB()
    {
        return questionB;
    }
    public void setQuestionC(String questionC)
    {
        this.questionC = questionC;
    }

    public String getQuestionC()
    {
        return questionC;
    }
    public void setQuestionD(String questionD)
    {
        this.questionD = questionD;
    }

    public String getQuestionD()
    {
        return questionD;
    }
    public void setMulAnswer(String mulAnswer)
    {
        this.mulAnswer = mulAnswer;
    }

    public String getMulAnswer()
    {
        return mulAnswer;
    }
    public void setQuestionLevel(String questionLevel)
    {
        this.questionLevel = questionLevel;
    }

    public String getQuestionLevel()
    {
        return questionLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("question", getQuestion())
            .append("questionA", getQuestionA())
            .append("questionB", getQuestionB())
            .append("questionC", getQuestionC())
            .append("questionD", getQuestionD())
            .append("mulAnswer", getMulAnswer())
            .append("questionLevel", getQuestionLevel())
            .toString();
    }
}
