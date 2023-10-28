package com.ruoyi.contents.service;

import java.util.List;
import com.ruoyi.contents.domain.StudyingContents;

/**
 * 知识仓库Service接口
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
public interface IStudyingContentsService 
{
    /**
     * 查询知识仓库
     * 
     * @param id 知识仓库主键
     * @return 知识仓库
     */
    public StudyingContents selectStudyingContentsById(Long id);

    /**
     * 查询知识仓库列表
     * 
     * @param studyingContents 知识仓库
     * @return 知识仓库集合
     */
    public List<StudyingContents> selectStudyingContentsList(StudyingContents studyingContents);

    /**
     * 新增知识仓库
     * 
     * @param studyingContents 知识仓库
     * @return 结果
     */
    public int insertStudyingContents(StudyingContents studyingContents);

    /**
     * 修改知识仓库
     * 
     * @param studyingContents 知识仓库
     * @return 结果
     */
    public int updateStudyingContents(StudyingContents studyingContents);

    /**
     * 批量删除知识仓库
     * 
     * @param ids 需要删除的知识仓库主键集合
     * @return 结果
     */
    public int deleteStudyingContentsByIds(Long[] ids);

    /**
     * 删除知识仓库信息
     * 
     * @param id 知识仓库主键
     * @return 结果
     */
    public int deleteStudyingContentsById(Long id);
}
