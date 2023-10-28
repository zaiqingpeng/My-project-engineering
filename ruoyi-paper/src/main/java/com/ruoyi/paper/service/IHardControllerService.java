package com.ruoyi.paper.service;

import java.util.List;
import com.ruoyi.paper.domain.HardController;

/**
 * 出题难度控制Service接口
 * 
 * @author ruoyi
 * @date 2023-04-04
 */
public interface IHardControllerService 
{
    /**
     * 查询出题难度控制
     * 
     * @param id 出题难度控制主键
     * @return 出题难度控制
     */
    public HardController selectHardControllerById(Long id);

    /**
     * 查询出题难度控制列表
     * 
     * @param hardController 出题难度控制
     * @return 出题难度控制集合
     */
    public List<HardController> selectHardControllerList(HardController hardController);

    /**
     * 新增出题难度控制
     * 
     * @param hardController 出题难度控制
     * @return 结果
     */
    public int insertHardController(HardController hardController);

    /**
     * 修改出题难度控制
     * 
     * @param hardController 出题难度控制
     * @return 结果
     */
    public int updateHardController(HardController hardController);

    /**
     * 批量删除出题难度控制
     * 
     * @param ids 需要删除的出题难度控制主键集合
     * @return 结果
     */
    public int deleteHardControllerByIds(Long[] ids);

    /**
     * 删除出题难度控制信息
     * 
     * @param id 出题难度控制主键
     * @return 结果
     */
    public int deleteHardControllerById(Long id);
}
