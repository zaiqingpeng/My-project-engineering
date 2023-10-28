package com.ruoyi.questions.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 简答题题库对象 free_questions
 * 
 * @author ruoyi
 * @date 2023-05-15
 */
public class FreeQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 题干 */
    @Excel(name = "题干")
    private String question;

    /** 答案 */
    @Excel(name = "答案")
    private String answer;

    /** 解析 */
    @Excel(name = "解析")
    private String questionDescribe;

    /** 关键词1 */
    @Excel(name = "关键词1")
    private String keyword1;

    /** 关键词2 */
    @Excel(name = "关键词2")
    private String keyword2;

    /** 关键词3 */
    @Excel(name = "关键词3")
    private String keyword3;

    /** 关键词4 */
    @Excel(name = "关键词4")
    private String keyword4;

    /** 关键词5 */
    @Excel(name = "关键词5")
    private String keyword5;

    /** 难度等级 */
    @Excel(name = "难度等级")
    private Long questionLevel;

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
    public void setQuestionDescribe(String questionDescribe) 
    {
        this.questionDescribe = questionDescribe;
    }

    public String getQuestionDescribe() 
    {
        return questionDescribe;
    }
    public void setKeyword1(String keyword1) 
    {
        this.keyword1 = keyword1;
    }

    public String getKeyword1() 
    {
        return keyword1;
    }
    public void setKeyword2(String keyword2) 
    {
        this.keyword2 = keyword2;
    }

    public String getKeyword2() 
    {
        return keyword2;
    }
    public void setKeyword3(String keyword3) 
    {
        this.keyword3 = keyword3;
    }

    public String getKeyword3() 
    {
        return keyword3;
    }
    public void setKeyword4(String keyword4) 
    {
        this.keyword4 = keyword4;
    }

    public String getKeyword4() 
    {
        return keyword4;
    }
    public void setKeyword5(String keyword5) 
    {
        this.keyword5 = keyword5;
    }

    public String getKeyword5() 
    {
        return keyword5;
    }
    public void setQuestionLevel(Long questionLevel) 
    {
        this.questionLevel = questionLevel;
    }

    public Long getQuestionLevel() 
    {
        return questionLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("question", getQuestion())
            .append("answer", getAnswer())
            .append("questionDescribe", getQuestionDescribe())
            .append("keyword1", getKeyword1())
            .append("keyword2", getKeyword2())
            .append("keyword3", getKeyword3())
            .append("keyword4", getKeyword4())
            .append("keyword5", getKeyword5())
            .append("questionLevel", getQuestionLevel())
            .toString();
    }
}
