package com.ruoyi.paper.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.paper.mapper.SmartQuestionsMapper;
import com.ruoyi.paper.domain.SmartQuestions;
import com.ruoyi.paper.service.ISmartQuestionsService;

/**
 * 智能出卷Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-03
 */
@Service
public class SmartQuestionsServiceImpl implements ISmartQuestionsService 
{
    @Autowired
    private SmartQuestionsMapper smartQuestionsMapper;

    /**
     * 查询智能出卷
     * 
     * @param id 智能出卷主键
     * @return 智能出卷
     */
    @Override
    public SmartQuestions selectSmartQuestionsById(Long id)
    {
        return smartQuestionsMapper.selectSmartQuestionsById(id);
    }

    /**
     * 查询智能出卷列表
     * 
     * @param smartQuestions 智能出卷
     * @return 智能出卷
     */
    @Override
    public List<SmartQuestions> selectSmartQuestionsList(SmartQuestions smartQuestions)
    {
        return smartQuestionsMapper.selectSmartQuestionsList(smartQuestions);
    }

    /**
     * 新增智能出卷
     * 
     * @param smartQuestions 智能出卷
     * @return 结果
     */
    @Override
    public int insertSmartQuestions(SmartQuestions smartQuestions)
    {
        return smartQuestionsMapper.insertSmartQuestions(smartQuestions);
    }

    /**
     * 修改智能出卷
     * 
     * @param smartQuestions 智能出卷
     * @return 结果
     */
    @Override
    public int updateSmartQuestions(SmartQuestions smartQuestions)
    {
        return smartQuestionsMapper.updateSmartQuestions(smartQuestions);
    }

    /**
     * 批量删除智能出卷
     * 
     * @param ids 需要删除的智能出卷主键
     * @return 结果
     */
    @Override
    public int deleteSmartQuestionsByIds(Long[] ids)
    {
        return smartQuestionsMapper.deleteSmartQuestionsByIds(ids);
    }

    /**
     * 删除智能出卷信息
     * 
     * @param id 智能出卷主键
     * @return 结果
     */
    @Override
    public int deleteSmartQuestionsById(Long id)
    {
        return smartQuestionsMapper.deleteSmartQuestionsById(id);
    }
}
