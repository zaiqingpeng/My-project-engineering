package com.ruoyi.mytask.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 开始考试对象 my_task
 * 
 * @author ruoyi
 * @date 2023-04-19
 */
public class MyTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer questionId;

    /** 题目 */
    @Excel(name = "题目")
    private String question;

    /** A */
    @Excel(name = "A")
    private String optionA;

    /** B */
    @Excel(name = "B")
    private String optionB;

    /** C */
    @Excel(name = "C")
    private String optionC;

    /** D */
    @Excel(name = "D")
    private String optionD;

    /** 标准答案 */
    @Excel(name = "标准答案")
    private String answer;

    /** 考生答案 */
    @Excel(name = "考生答案")
    private String choose;

    public void setQuestionId(Integer questionId) 
    {
        this.questionId = questionId;
    }

    public Integer getQuestionId() 
    {
        return questionId;
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
    public void setChoose(String choose) 
    {
        this.choose = choose;
    }

    public String getChoose() 
    {
        return choose;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("questionId", getQuestionId())
            .append("question", getQuestion())
            .append("optionA", getOptionA())
            .append("optionB", getOptionB())
            .append("optionC", getOptionC())
            .append("optionD", getOptionD())
            .append("answer", getAnswer())
            .append("choose", getChoose())
            .toString();
    }
}
