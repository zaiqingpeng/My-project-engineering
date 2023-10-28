package com.ruoyi.questions.mapper;

import java.util.List;
import com.ruoyi.questions.domain.TruefalseQuestions;

/**
 * 判断题题库Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-19
 */
public interface TruefalseQuestionsMapper 
{
    /**
     * 查询判断题题库
     * 
     * @param id 判断题题库主键
     * @return 判断题题库
     */
    public TruefalseQuestions selectTruefalseQuestionsById(Long id);

    /**
     * 查询判断题题库列表
     * 
     * @param truefalseQuestions 判断题题库
     * @return 判断题题库集合
     */
    public List<TruefalseQuestions> selectTruefalseQuestionsList(TruefalseQuestions truefalseQuestions);

    /**
     * 新增判断题题库
     * 
     * @param truefalseQuestions 判断题题库
     * @return 结果
     */
    public int insertTruefalseQuestions(TruefalseQuestions truefalseQuestions);

    /**
     * 修改判断题题库
     * 
     * @param truefalseQuestions 判断题题库
     * @return 结果
     */
    public int updateTruefalseQuestions(TruefalseQuestions truefalseQuestions);

    /**
     * 删除判断题题库
     * 
     * @param id 判断题题库主键
     * @return 结果
     */
    public int deleteTruefalseQuestionsById(Long id);

    /**
     * 批量删除判断题题库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTruefalseQuestionsByIds(Long[] ids);
}
