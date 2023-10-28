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
import com.ruoyi.paper.domain.LearningTests;
import com.ruoyi.paper.service.ILearningTestsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 组卷信息Controller
 *
 * @author ruoyi
 * @date 2023-03-23
 */
@RestController
@RequestMapping("/paper/paper_info")
public class LearningTestsController extends BaseController
{
    @Autowired
    private ILearningTestsService learningTestsService;

    /**
     * 查询组卷信息列表
     */
    @PreAuthorize("@ss.hasPermi('paper:paper_info:list')")
    @GetMapping("/list")
    public TableDataInfo list(LearningTests learningTests)
    {
        startPage();
        List<LearningTests> list = learningTestsService.selectLearningTestsList(learningTests);
        return getDataTable(list);
    }
    //新增方法
    @PreAuthorize("@ss.hasPermi('paper:paper_info:list1')")
    @GetMapping("/list1")
    public TableDataInfo list()
    {
        //删除了startPage()后，可以获取到数据库中所有数据了
        List<LearningTests> list = learningTestsService.LearningTestsList();
        return getDataTable(list);
    }

    /**
     * 导出组卷信息列表
     */
    @PreAuthorize("@ss.hasPermi('paper:paper_info:export')")
    @Log(title = "组卷信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LearningTests learningTests)
    {
        List<LearningTests> list = learningTestsService.selectLearningTestsList(learningTests);
        ExcelUtil<LearningTests> util = new ExcelUtil<LearningTests>(LearningTests.class);
        util.exportExcel(response, list, "组卷信息数据");
    }

    /**
     * 获取组卷信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('paper:paper_info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(learningTestsService.selectLearningTestsById(id));
    }

    /**
     * 新增组卷信息
     */
    @PreAuthorize("@ss.hasPermi('paper:paper_info:add')")
    @Log(title = "组卷信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LearningTests learningTests)
    {
        return toAjax(learningTestsService.insertLearningTests(learningTests));
    }

    /**
     * 修改组卷信息
     */
    @PreAuthorize("@ss.hasPermi('paper:paper_info:edit')")
    @Log(title = "组卷信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LearningTests learningTests)
    {
        return toAjax(learningTestsService.updateLearningTests(learningTests));
    }

    /**
     * 删除组卷信息
     */
    @PreAuthorize("@ss.hasPermi('paper:paper_info:remove')")
    @Log(title = "组卷信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(learningTestsService.deleteLearningTestsByIds(ids));
    }
}
