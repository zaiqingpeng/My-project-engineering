package com.ruoyi.classes.controller;

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
import com.ruoyi.classes.domain.ClassesInfo;
import com.ruoyi.classes.service.IClassesInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班级信息Controller
 * 
 * @author ruoyi
 * @date 2023-03-18
 */
@RestController
@RequestMapping("/classes/classes_info")
public class ClassesInfoController extends BaseController
{
    @Autowired
    private IClassesInfoService classesInfoService;

    /**
     * 查询班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('classes:classes_info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClassesInfo classesInfo)
    {
        startPage();
        List<ClassesInfo> list = classesInfoService.selectClassesInfoList(classesInfo);
        return getDataTable(list);
    }

    /**
     * 导出班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('classes:classes_info:export')")
    @Log(title = "班级信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClassesInfo classesInfo)
    {
        List<ClassesInfo> list = classesInfoService.selectClassesInfoList(classesInfo);
        ExcelUtil<ClassesInfo> util = new ExcelUtil<ClassesInfo>(ClassesInfo.class);
        util.exportExcel(response, list, "班级信息数据");
    }

    /**
     * 获取班级信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('classes:classes_info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(classesInfoService.selectClassesInfoById(id));
    }

    /**
     * 新增班级信息
     */
    @PreAuthorize("@ss.hasPermi('classes:classes_info:add')")
    @Log(title = "班级信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClassesInfo classesInfo)
    {
        return toAjax(classesInfoService.insertClassesInfo(classesInfo));
    }

    /**
     * 修改班级信息
     */
    @PreAuthorize("@ss.hasPermi('classes:classes_info:edit')")
    @Log(title = "班级信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClassesInfo classesInfo)
    {
        return toAjax(classesInfoService.updateClassesInfo(classesInfo));
    }

    /**
     * 删除班级信息
     */
    @PreAuthorize("@ss.hasPermi('classes:classes_info:remove')")
    @Log(title = "班级信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(classesInfoService.deleteClassesInfoByIds(ids));
    }
}
