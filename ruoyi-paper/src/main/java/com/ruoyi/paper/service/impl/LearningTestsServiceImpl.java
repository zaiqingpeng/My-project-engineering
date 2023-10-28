package com.ruoyi.paper.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.paper.mapper.LearningTestsMapper;
import com.ruoyi.paper.domain.LearningTests;
import com.ruoyi.paper.service.ILearningTestsService;

/**
 * 组卷信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-23
 */
@Service
public class LearningTestsServiceImpl implements ILearningTestsService {
    @Autowired
    private LearningTestsMapper learningTestsMapper;

    /**
     * 查询组卷信息
     *
     * @param id 组卷信息主键
     * @return 组卷信息
     */
    @Override
    public LearningTests selectLearningTestsById(Integer id) {
        return learningTestsMapper.selectLearningTestsById(id);
    }

    /**
     * 查询组卷信息列表
     *
     * @param learningTests 组卷信息
     * @return 组卷信息
     */
    @Override
    public List<LearningTests> selectLearningTestsList(LearningTests learningTests) {
        return learningTestsMapper.selectLearningTestsList(learningTests);
    }

    //新增的方法
    @Override
    public List<LearningTests> LearningTestsList() {
        return learningTestsMapper.LearningTestsList();
    }

    /**
     * 新增组卷信息
     *
     * @param learningTests 组卷信息
     * @return 结果
     */
    @Override
    public int insertLearningTests(LearningTests learningTests) {
        return learningTestsMapper.insertLearningTests(learningTests);
    }

    /**
     * 修改组卷信息
     *
     * @param learningTests 组卷信息
     * @return 结果
     */
    @Override
    public int updateLearningTests(LearningTests learningTests) {
        return learningTestsMapper.updateLearningTests(learningTests);
    }

    /**
     * 批量删除组卷信息
     *
     * @param ids 需要删除的组卷信息主键
     * @return 结果
     */
    @Override
    public int deleteLearningTestsByIds(Integer[] ids) {
        return learningTestsMapper.deleteLearningTestsByIds(ids);
    }

    /**
     * 删除组卷信息信息
     *
     * @param id 组卷信息主键
     * @return 结果
     */
    @Override
    public int deleteLearningTestsById(Integer id) {
        return learningTestsMapper.deleteLearningTestsById(id);
    }
}
