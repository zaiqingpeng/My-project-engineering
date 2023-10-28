package com.ruoyi.questions.mapper;

import java.util.List;
import com.ruoyi.questions.domain.SingleQuestions;

/**
 * 单选题题库Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-24
 */
public interface SingleQuestionsMapper 
{
    /**
     * 查询单选题题库
     * 
     * @param id 单选题题库主键
     * @return 单选题题库
     */
    public SingleQuestions selectSingleQuestionsById(Integer id);

    /**
     * 查询单选题题库列表
     * 
     * @param singleQuestions 单选题题库
     * @return 单选题题库集合
     */
    public List<SingleQuestions> selectSingleQuestionsList(SingleQuestions singleQuestions);

    /**
     * 新增单选题题库
     * 
     * @param singleQuestions 单选题题库
     * @return 结果
     */
    public int insertSingleQuestions(SingleQuestions singleQuestions);

    /**
     * 修改单选题题库
     * 
     * @param singleQuestions 单选题题库
     * @return 结果
     */
    public int updateSingleQuestions(SingleQuestions singleQuestions);

    /**
     * 删除单选题题库
     * 
     * @param id 单选题题库主键
     * @return 结果
     */
    public int deleteSingleQuestionsById(Integer id);

    /**
     * 批量删除单选题题库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSingleQuestionsByIds(Integer[] ids);
}
