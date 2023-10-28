package com.ruoyi.contents.mapper;

import java.util.List;
import com.ruoyi.contents.domain.MainContent;

/**
 * 认知与学习Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-23
 */
public interface MainContentMapper 
{
    /**
     * 查询认知与学习
     * 
     * @param id 认知与学习主键
     * @return 认知与学习
     */
    public MainContent selectMainContentById(Integer id);

    /**
     * 查询认知与学习列表
     * 
     * @param mainContent 认知与学习
     * @return 认知与学习集合
     */
    public List<MainContent> selectMainContentList(MainContent mainContent);

    /**
     * 新增认知与学习
     * 
     * @param mainContent 认知与学习
     * @return 结果
     */
    public int insertMainContent(MainContent mainContent);

    /**
     * 修改认知与学习
     * 
     * @param mainContent 认知与学习
     * @return 结果
     */
    public int updateMainContent(MainContent mainContent);

    /**
     * 删除认知与学习
     * 
     * @param id 认知与学习主键
     * @return 结果
     */
    public int deleteMainContentById(Integer id);

    /**
     * 批量删除认知与学习
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMainContentByIds(Integer[] ids);
}
