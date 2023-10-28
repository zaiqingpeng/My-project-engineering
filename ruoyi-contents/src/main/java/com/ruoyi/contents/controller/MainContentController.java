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
import com.ruoyi.contents.domain.MainContent;
import com.ruoyi.contents.service.IMainContentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 认知与学习Controller
 *
 * @author ruoyi
 * @date 2023-03-23
 */
@RestController
@RequestMapping("/contents/content_info")
public class MainContentController extends BaseController
{
    @Autowired
    private IMainContentService mainContentService;

    /**
     * 查询认知与学习列表
     */
    @PreAuthorize("@ss.hasPermi('contents:content_info:list')")
    @GetMapping("/list")
    public TableDataInfo list(MainContent mainContent)
    {
//        startPage();
        List<MainContent> list = mainContentService.selectMainContentList(mainContent);
        return getDataTable(list);
    }

    /**
     * 导出认知与学习列表
     */
    @PreAuthorize("@ss.hasPermi('contents:content_info:export')")
    @Log(title = "认知与学习", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MainContent mainContent)
    {
        List<MainContent> list = mainContentService.selectMainContentList(mainContent);
        ExcelUtil<MainContent> util = new ExcelUtil<MainContent>(MainContent.class);
        util.exportExcel(response, list, "认知与学习数据");
    }

    /**
     * 获取认知与学习详细信息
     */
    @PreAuthorize("@ss.hasPermi('contents:content_info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(mainContentService.selectMainContentById(id));
    }

    /**
     * 新增认知与学习
     */
    @PreAuthorize("@ss.hasPermi('contents:content_info:add')")
    @Log(title = "认知与学习", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MainContent mainContent)
    {
        return toAjax(mainContentService.insertMainContent(mainContent));
    }

    /**
     * 修改认知与学习
     */
    @PreAuthorize("@ss.hasPermi('contents:content_info:edit')")
    @Log(title = "认知与学习", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MainContent mainContent)
    {
        return toAjax(mainContentService.updateMainContent(mainContent));
    }

    /**
     * 删除认知与学习
     */
    @PreAuthorize("@ss.hasPermi('contents:content_info:remove')")
    @Log(title = "认知与学习", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(mainContentService.deleteMainContentByIds(ids));
    }
}
