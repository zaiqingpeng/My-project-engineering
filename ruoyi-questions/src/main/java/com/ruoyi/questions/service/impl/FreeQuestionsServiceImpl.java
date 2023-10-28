package com.ruoyi.questions.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.questions.mapper.FreeQuestionsMapper;
import com.ruoyi.questions.domain.FreeQuestions;
import com.ruoyi.questions.service.IFreeQuestionsService;

/**
 * 简答题题库Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-15
 */
@Service
public class FreeQuestionsServiceImpl implements IFreeQuestionsService 
{
    @Autowired
    private FreeQuestionsMapper freeQuestionsMapper;

    /**
     * 查询简答题题库
     * 
     * @param id 简答题题库主键
     * @return 简答题题库
     */
    @Override
    public FreeQuestions selectFreeQuestionsById(Long id)
    {
        return freeQuestionsMapper.selectFreeQuestionsById(id);
    }

    /**
     * 查询简答题题库列表
     * 
     * @param freeQuestions 简答题题库
     * @return 简答题题库
     */
    @Override
    public List<FreeQuestions> selectFreeQuestionsList(FreeQuestions freeQuestions)
    {
        return freeQuestionsMapper.selectFreeQuestionsList(freeQuestions);
    }

    /**
     * 新增简答题题库
     * 
     * @param freeQuestions 简答题题库
     * @return 结果
     */
    @Override
    public int insertFreeQuestions(FreeQuestions freeQuestions)
    {
        return freeQuestionsMapper.insertFreeQuestions(freeQuestions);
    }

    /**
     * 修改简答题题库
     * 
     * @param freeQuestions 简答题题库
     * @return 结果
     */
    @Override
    public int updateFreeQuestions(FreeQuestions freeQuestions)
    {
        return freeQuestionsMapper.updateFreeQuestions(freeQuestions);
    }

    /**
     * 批量删除简答题题库
     * 
     * @param ids 需要删除的简答题题库主键
     * @return 结果
     */
    @Override
    public int deleteFreeQuestionsByIds(Long[] ids)
    {
        return freeQuestionsMapper.deleteFreeQuestionsByIds(ids);
    }

    /**
     * 删除简答题题库信息
     * 
     * @param id 简答题题库主键
     * @return 结果
     */
    @Override
    public int deleteFreeQuestionsById(Long id)
    {
        return freeQuestionsMapper.deleteFreeQuestionsById(id);
    }
}
