package com.ruoyi.paper.service;

import java.util.List;
import com.ruoyi.paper.domain.LearningTests;

/**
 * 组卷信息Service接口
 *
 * @author ruoyi
 * @date 2023-03-23
 */
public interface ILearningTestsService
{
    /**
     * 查询组卷信息
     *
     * @param id 组卷信息主键
     * @return 组卷信息
     */
    public LearningTests selectLearningTestsById(Integer id);

    /**
     * 查询组卷信息列表
     *
     * @param learningTests 组卷信息
     * @return 组卷信息集合
     */
    public List<LearningTests> selectLearningTestsList(LearningTests learningTests);
    //新增的方法查询
    public List<LearningTests> LearningTestsList();
    /**
     * 新增组卷信息
     *
     * @param learningTests 组卷信息
     * @return 结果
     */
    public int insertLearningTests(LearningTests learningTests);

    /**
     * 修改组卷信息
     *
     * @param learningTests 组卷信息
     * @return 结果
     */
    public int updateLearningTests(LearningTests learningTests);

    /**
     * 批量删除组卷信息
     *
     * @param ids 需要删除的组卷信息主键集合
     * @return 结果
     */
    public int deleteLearningTestsByIds(Integer[] ids);

    /**
     * 删除组卷信息信息
     *
     * @param id 组卷信息主键
     * @return 结果
     */
    public int deleteLearningTestsById(Integer id);
}
