package com.ruoyi.questions.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单选题题库对象 single_questions
 *
 * @author ruoyi
 * @date 2023-03-24
 */
public class SingleQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer id;

    /** 试题 */
    @Excel(name = "试题")
    private String question;

    /** A选项 */
    @Excel(name = "A选项")
    private String optionA;

    /** B选项 */
    @Excel(name = "B选项")
    private String optionB;

    /** C选项 */
    @Excel(name = "C选项")
    private String optionC;

    /** D选项 */
    @Excel(name = "D选项")
    private String optionD;

    /** 答案 */
    @Excel(name = "答案")
    private String answer;

    /** 难度 */
    @Excel(name = "难度")
    private String level;

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
    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getLevel()
    {
        return level;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("question", getQuestion())
            .append("optionA", getOptionA())
            .append("optionB", getOptionB())
            .append("optionC", getOptionC())
            .append("optionD", getOptionD())
            .append("answer", getAnswer())
            .append("level", getLevel())
            .toString();
    }
}
