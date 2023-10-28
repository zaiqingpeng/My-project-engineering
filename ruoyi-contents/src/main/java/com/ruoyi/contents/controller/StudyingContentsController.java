package com.ruoyi.contents.controller;

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
import com.ruoyi.contents.domain.StudyingContents;
import com.ruoyi.contents.service.IStudyingContentsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 知识仓库Controller
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
@RestController
@RequestMapping("/contents/contents_bank")
public class StudyingContentsController extends BaseController
{
    @Autowired
    private IStudyingContentsService studyingContentsService;

    /**
     * 查询知识仓库列表
     */
    @PreAuthorize("@ss.hasPermi('contents:contents_bank:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudyingContents studyingContents)
    {
        startPage();
        List<StudyingContents> list = studyingContentsService.selectStudyingContentsList(studyingContents);
        return getDataTable(list);
    }

    /**
     * 导出知识仓库列表
     */
    @PreAuthorize("@ss.hasPermi('contents:contents_bank:export')")
    @Log(title = "知识仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudyingContents studyingContents)
    {
        List<StudyingContents> list = studyingContentsService.selectStudyingContentsList(studyingContents);
        ExcelUtil<StudyingContents> util = new ExcelUtil<StudyingContents>(StudyingContents.class);
        util.exportExcel(response, list, "知识仓库数据");
    }

    /**
     * 获取知识仓库详细信息
     */
    @PreAuthorize("@ss.hasPermi('contents:contents_bank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studyingContentsService.selectStudyingContentsById(id));
    }

    /**
     * 新增知识仓库
     */
    @PreAuthorize("@ss.hasPermi('contents:contents_bank:add')")
    @Log(title = "知识仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudyingContents studyingContents)
    {
        return toAjax(studyingContentsService.insertStudyingContents(studyingContents));
    }

    /**
     * 修改知识仓库
     */
    @PreAuthorize("@ss.hasPermi('contents:contents_bank:edit')")
    @Log(title = "知识仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudyingContents studyingContents)
    {
        return toAjax(studyingContentsService.updateStudyingContents(studyingContents));
    }

    /**
     * 删除知识仓库
     */
    @PreAuthorize("@ss.hasPermi('contents:contents_bank:remove')")
    @Log(title = "知识仓库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studyingContentsService.deleteStudyingContentsByIds(ids));
    }
}
