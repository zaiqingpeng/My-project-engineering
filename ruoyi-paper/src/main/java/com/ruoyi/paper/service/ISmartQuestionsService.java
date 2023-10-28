package com.ruoyi.paper.service;

import java.util.List;
import com.ruoyi.paper.domain.SmartQuestions;

/**
 * 智能出卷Service接口
 * 
 * @author ruoyi
 * @date 2023-04-03
 */
public interface ISmartQuestionsService 
{
    /**
     * 查询智能出卷
     * 
     * @param id 智能出卷主键
     * @return 智能出卷
     */
    public SmartQuestions selectSmartQuestionsById(Long id);

    /**
     * 查询智能出卷列表
     * 
     * @param smartQuestions 智能出卷
     * @return 智能出卷集合
     */
    public List<SmartQuestions> selectSmartQuestionsList(SmartQuestions smartQuestions);

    /**
     * 新增智能出卷
     * 
     * @param smartQuestions 智能出卷
     * @return 结果
     */
    public int insertSmartQuestions(SmartQuestions smartQuestions);

    /**
     * 修改智能出卷
     * 
     * @param smartQuestions 智能出卷
     * @return 结果
     */
    public int updateSmartQuestions(SmartQuestions smartQuestions);

    /**
     * 批量删除智能出卷
     * 
     * @param ids 需要删除的智能出卷主键集合
     * @return 结果
     */
    public int deleteSmartQuestionsByIds(Long[] ids);

    /**
     * 删除智能出卷信息
     * 
     * @param id 智能出卷主键
     * @return 结果
     */
    public int deleteSmartQuestionsById(Long id);
}
