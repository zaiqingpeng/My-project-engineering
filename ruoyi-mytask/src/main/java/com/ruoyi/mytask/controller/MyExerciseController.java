package com.ruoyi.mytask.controller;

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
import com.ruoyi.mytask.domain.MyExercise;
import com.ruoyi.mytask.service.IMyExerciseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 开始练习Controller
 * 
 * @author ruoyi
 * @date 2023-05-05
 */
@RestController
@RequestMapping("/mytask/exercise")
public class MyExerciseController extends BaseController
{
    @Autowired
    private IMyExerciseService myExerciseService;

    /**
     * 查询开始练习列表
     */
    @PreAuthorize("@ss.hasPermi('mytask:exercise:list')")
    @GetMapping("/list")
    public TableDataInfo list(MyExercise myExercise)
    {
        startPage();
        List<MyExercise> list = myExerciseService.selectMyExerciseList(myExercise);
        return getDataTable(list);
    }

    /**
     * 导出开始练习列表
     */
    @PreAuthorize("@ss.hasPermi('mytask:exercise:export')")
    @Log(title = "开始练习", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MyExercise myExercise)
    {
        List<MyExercise> list = myExerciseService.selectMyExerciseList(myExercise);
        ExcelUtil<MyExercise> util = new ExcelUtil<MyExercise>(MyExercise.class);
        util.exportExcel(response, list, "开始练习数据");
    }

    /**
     * 获取开始练习详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytask:exercise:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(myExerciseService.selectMyExerciseById(id));
    }

    /**
     * 新增开始练习
     */
    @PreAuthorize("@ss.hasPermi('mytask:exercise:add')")
    @Log(title = "开始练习", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyExercise myExercise)
    {
        return toAjax(myExerciseService.insertMyExercise(myExercise));
    }

    /**
     * 修改开始练习
     */
    @PreAuthorize("@ss.hasPermi('mytask:exercise:edit')")
    @Log(title = "开始练习", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyExercise myExercise)
    {
        return toAjax(myExerciseService.updateMyExercise(myExercise));
    }

    /**
     * 删除开始练习
     */
    @PreAuthorize("@ss.hasPermi('mytask:exercise:remove')")
    @Log(title = "开始练习", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(myExerciseService.deleteMyExerciseByIds(ids));
    }
}
