package com.ruoyi.paper.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.paper.mapper.HardControllerMapper;
import com.ruoyi.paper.domain.HardController;
import com.ruoyi.paper.service.IHardControllerService;

/**
 * 出题难度控制Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-04
 */
@Service
public class HardControllerServiceImpl implements IHardControllerService 
{
    @Autowired
    private HardControllerMapper hardControllerMapper;

    /**
     * 查询出题难度控制
     * 
     * @param id 出题难度控制主键
     * @return 出题难度控制
     */
    @Override
    public HardController selectHardControllerById(Long id)
    {
        return hardControllerMapper.selectHardControllerById(id);
    }

    /**
     * 查询出题难度控制列表
     * 
     * @param hardController 出题难度控制
     * @return 出题难度控制
     */
    @Override
    public List<HardController> selectHardControllerList(HardController hardController)
    {
        return hardControllerMapper.selectHardControllerList(hardController);
    }

    /**
     * 新增出题难度控制
     * 
     * @param hardController 出题难度控制
     * @return 结果
     */
    @Override
    public int insertHardController(HardController hardController)
    {
        return hardControllerMapper.insertHardController(hardController);
    }

    /**
     * 修改出题难度控制
     * 
     * @param hardController 出题难度控制
     * @return 结果
     */
    @Override
    public int updateHardController(HardController hardController)
    {
        return hardControllerMapper.updateHardController(hardController);
    }

    /**
     * 批量删除出题难度控制
     * 
     * @param ids 需要删除的出题难度控制主键
     * @return 结果
     */
    @Override
    public int deleteHardControllerByIds(Long[] ids)
    {
        return hardControllerMapper.deleteHardControllerByIds(ids);
    }

    /**
     * 删除出题难度控制信息
     * 
     * @param id 出题难度控制主键
     * @return 结果
     */
    @Override
    public int deleteHardControllerById(Long id)
    {
        return hardControllerMapper.deleteHardControllerById(id);
    }
}
