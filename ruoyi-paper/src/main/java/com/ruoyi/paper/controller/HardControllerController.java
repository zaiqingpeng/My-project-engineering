package com.ruoyi.paper.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.paper.domain.HardController;
import com.ruoyi.paper.service.IHardControllerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出题难度控制Controller
 * 
 * @author ruoyi
 * @date 2023-04-04
 */
@RestController
@RequestMapping("/paper/hard_controller")
public class HardControllerController extends BaseController
{
    @Autowired
    private IHardControllerService hardControllerService;

    /**
     * 查询出题难度控制列表
     */
    @PreAuthorize("@ss.hasPermi('paper:hard_controller:list')")
    @GetMapping("/list")
    public TableDataInfo list(HardController hardController)
    {
        startPage();
        List<HardController> list = hardControllerService.selectHardControllerList(hardController);
        return getDataTable(list);
    }

    /**
     * 导出出题难度控制列表
     */
    @PreAuthorize("@ss.hasPermi('paper:hard_controller:export')")
    @Log(title = "出题难度控制", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HardController hardController)
    {
        List<HardController> list = hardControllerService.selectHardControllerList(hardController);
        ExcelUtil<HardController> util = new ExcelUtil<HardController>(HardController.class);
        util.exportExcel(response, list, "出题难度控制数据");
    }

    /**
     * 获取出题难度控制详细信息
     */
    @PreAuthorize("@ss.hasPermi('paper:hard_controller:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(hardControllerService.selectHardControllerById(id));
    }

    /**
     * 新增出题难度控制
     */
    @PreAuthorize("@ss.hasPermi('paper:hard_controller:add')")
    @Log(title = "出题难度控制", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HardController hardController)
    {
        return toAjax(hardControllerService.insertHardController(hardController));
    }

    /**
     * 修改出题难度控制
     */
    @PreAuthorize("@ss.hasPermi('paper:hard_controller:edit')")
    @Log(title = "出题难度控制", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HardController hardController)
    {
        return toAjax(hardControllerService.updateHardController(hardController));
    }

    /**
     * 删除出题难度控制
     */
    @PreAuthorize("@ss.hasPermi('paper:hard_controller:remove')")
    @Log(title = "出题难度控制", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hardControllerService.deleteHardControllerByIds(ids));
    }
}
