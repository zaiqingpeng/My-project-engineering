package com.ruoyi.contents.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contents.mapper.MainContentMapper;
import com.ruoyi.contents.domain.MainContent;
import com.ruoyi.contents.service.IMainContentService;

/**
 * 认知与学习Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-23
 */
@Service
public class MainContentServiceImpl implements IMainContentService 
{
    @Autowired
    private MainContentMapper mainContentMapper;

    /**
     * 查询认知与学习
     * 
     * @param id 认知与学习主键
     * @return 认知与学习
     */
    @Override
    public MainContent selectMainContentById(Integer id)
    {
        return mainContentMapper.selectMainContentById(id);
    }

    /**
     * 查询认知与学习列表
     * 
     * @param mainContent 认知与学习
     * @return 认知与学习
     */
    @Override
    public List<MainContent> selectMainContentList(MainContent mainContent)
    {
        return mainContentMapper.selectMainContentList(mainContent);
    }

    /**
     * 新增认知与学习
     * 
     * @param mainContent 认知与学习
     * @return 结果
     */
    @Override
    public int insertMainContent(MainContent mainContent)
    {
        return mainContentMapper.insertMainContent(mainContent);
    }

    /**
     * 修改认知与学习
     * 
     * @param mainContent 认知与学习
     * @return 结果
     */
    @Override
    public int updateMainContent(MainContent mainContent)
    {
        return mainContentMapper.updateMainContent(mainContent);
    }

    /**
     * 批量删除认知与学习
     * 
     * @param ids 需要删除的认知与学习主键
     * @return 结果
     */
    @Override
    public int deleteMainContentByIds(Integer[] ids)
    {
        return mainContentMapper.deleteMainContentByIds(ids);
    }

    /**
     * 删除认知与学习信息
     * 
     * @param id 认知与学习主键
     * @return 结果
     */
    @Override
    public int deleteMainContentById(Integer id)
    {
        return mainContentMapper.deleteMainContentById(id);
    }
}
