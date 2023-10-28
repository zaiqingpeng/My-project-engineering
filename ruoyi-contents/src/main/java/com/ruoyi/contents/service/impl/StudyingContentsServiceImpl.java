package com.ruoyi.contents.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contents.mapper.StudyingContentsMapper;
import com.ruoyi.contents.domain.StudyingContents;
import com.ruoyi.contents.service.IStudyingContentsService;

/**
 * 知识仓库Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
@Service
public class StudyingContentsServiceImpl implements IStudyingContentsService 
{
    @Autowired
    private StudyingContentsMapper studyingContentsMapper;

    /**
     * 查询知识仓库
     * 
     * @param id 知识仓库主键
     * @return 知识仓库
     */
    @Override
    public StudyingContents selectStudyingContentsById(Long id)
    {
        return studyingContentsMapper.selectStudyingContentsById(id);
    }

    /**
     * 查询知识仓库列表
     * 
     * @param studyingContents 知识仓库
     * @return 知识仓库
     */
    @Override
    public List<StudyingContents> selectStudyingContentsList(StudyingContents studyingContents)
    {
        return studyingContentsMapper.selectStudyingContentsList(studyingContents);
    }

    /**
     * 新增知识仓库
     * 
     * @param studyingContents 知识仓库
     * @return 结果
     */
    @Override
    public int insertStudyingContents(StudyingContents studyingContents)
    {
        return studyingContentsMapper.insertStudyingContents(studyingContents);
    }

    /**
     * 修改知识仓库
     * 
     * @param studyingContents 知识仓库
     * @return 结果
     */
    @Override
    public int updateStudyingContents(StudyingContents studyingContents)
    {
        return studyingContentsMapper.updateStudyingContents(studyingContents);
    }

    /**
     * 批量删除知识仓库
     * 
     * @param ids 需要删除的知识仓库主键
     * @return 结果
     */
    @Override
    public int deleteStudyingContentsByIds(Long[] ids)
    {
        return studyingContentsMapper.deleteStudyingContentsByIds(ids);
    }

    /**
     * 删除知识仓库信息
     * 
     * @param id 知识仓库主键
     * @return 结果
     */
    @Override
    public int deleteStudyingContentsById(Long id)
    {
        return studyingContentsMapper.deleteStudyingContentsById(id);
    }
}
