package com.ruoyi.questions.mapper;

import java.util.List;
import com.ruoyi.questions.domain.FreeQuestions;

/**
 * 简答题题库Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-15
 */
public interface FreeQuestionsMapper 
{
    /**
     * 查询简答题题库
     * 
     * @param id 简答题题库主键
     * @return 简答题题库
     */
    public FreeQuestions selectFreeQuestionsById(Long id);

    /**
     * 查询简答题题库列表
     * 
     * @param freeQuestions 简答题题库
     * @return 简答题题库集合
     */
    public List<FreeQuestions> selectFreeQuestionsList(FreeQuestions freeQuestions);

    /**
     * 新增简答题题库
     * 
     * @param freeQuestions 简答题题库
     * @return 结果
     */
    public int insertFreeQuestions(FreeQuestions freeQuestions);

    /**
     * 修改简答题题库
     * 
     * @param freeQuestions 简答题题库
     * @return 结果
     */
    public int updateFreeQuestions(FreeQuestions freeQuestions);

    /**
     * 删除简答题题库
     * 
     * @param id 简答题题库主键
     * @return 结果
     */
    public int deleteFreeQuestionsById(Long id);

    /**
     * 批量删除简答题题库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFreeQuestionsByIds(Long[] ids);
}
