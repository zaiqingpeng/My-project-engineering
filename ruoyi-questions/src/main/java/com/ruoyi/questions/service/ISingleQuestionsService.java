package com.ruoyi.questions.service;

import java.util.List;
import com.ruoyi.questions.domain.SingleQuestions;

/**
 * 单选题题库Service接口
 *
 * @author ruoyi
 * @date 2023-03-24
 */
public interface ISingleQuestionsService
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
     * 批量删除单选题题库
     *
     * @param ids 需要删除的单选题题库主键集合
     * @return 结果
     */
    public int deleteSingleQuestionsByIds(Integer[] ids);

    /**
     * 删除单选题题库信息
     *
     * @param id 单选题题库主键
     * @return 结果
     */
    public int deleteSingleQuestionsById(Integer id);

    /**
     * 导入用户数据
     *
     * @param stuList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SingleQuestions> stuList, Boolean isUpdateSupport, String operName);

}
