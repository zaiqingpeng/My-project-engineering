package com.ruoyi.questions.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 判断题题库对象 truefalse_questions
 *
 * @author ruoyi
 * @date 2023-03-19
 */
public class TruefalseQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题号 */
    @Excel(name = "题号")
    private Long id;

    /** 题目 */
    @Excel(name = "试题")
    private String question;

    /** 答案 */
    @Excel(name = "答案")
    private String answer;

    /** 难度 */
    @Excel(name = "难度")
    private String tfLevel;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
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

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setTfLevel(String tfLevel)
    {
        this.tfLevel = tfLevel;
    }

    public String getTfLevel()
    {
        return tfLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("question", getQuestion())
            .append("answer", getAnswer())
            .append("tfLevel", getTfLevel())
            .toString();
    }
}
