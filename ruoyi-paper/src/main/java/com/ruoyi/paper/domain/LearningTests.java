package com.ruoyi.paper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 组卷信息对象 learning_tests
 * 
 * @author ruoyi
 * @date 2023-03-23
 */
public class LearningTests extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer id;

    /** 试题类型 */
    @Excel(name = "试题类型")
    private String questionsType;

    /** 试题 */
    @Excel(name = "试题")
    private String questions;

    /** 选项A */
    @Excel(name = "选项A")
    private String optionA;

    /** 选项B */
    @Excel(name = "选项B")
    private String optionB;

    /** 选项C */
    @Excel(name = "选项C")
    private String optionC;

    /** 选项D */
    @Excel(name = "选项D")
    private String optionD;

    /** 答案 */
    @Excel(name = "答案")
    private String answer;

    /** 难度 */
    @Excel(name = "难度")
    private String questionsLevel;

    /** 分值 */
    @Excel(name = "分值")
    private Integer perScore;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setQuestionsType(String questionsType) 
    {
        this.questionsType = questionsType;
    }

    public String getQuestionsType() 
    {
        return questionsType;
    }
    public void setQuestions(String questions) 
    {
        this.questions = questions;
    }

    public String getQuestions() 
    {
        return questions;
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
    public void setQuestionsLevel(String questionsLevel) 
    {
        this.questionsLevel = questionsLevel;
    }

    public String getQuestionsLevel() 
    {
        return questionsLevel;
    }
    public void setPerScore(Integer perScore) 
    {
        this.perScore = perScore;
    }

    public Integer getPerScore() 
    {
        return perScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("questionsType", getQuestionsType())
            .append("questions", getQuestions())
            .append("optionA", getOptionA())
            .append("optionB", getOptionB())
            .append("optionC", getOptionC())
            .append("optionD", getOptionD())
            .append("answer", getAnswer())
            .append("questionsLevel", getQuestionsLevel())
            .append("perScore", getPerScore())
            .toString();
    }
}
