package com.ruoyi.student.controller;

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
import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.service.IStudentInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学生基本信息Controller
 *
 * @author ruoyi
 * @date 2023-03-18
 */
@RestController
@RequestMapping("/student/student_info")
public class StudentInfoController extends BaseController
{
    @Autowired
    private IStudentInfoService studentInfoService;

    /**
     * 查询学生基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:student_info:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentInfo studentInfo)
    {
       //startPage();
        List<StudentInfo> list = studentInfoService.selectStudentInfoList(studentInfo);
        return getDataTable(list);
    }

    /**
     * 导出学生基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:student_info:export')")
    @Log(title = "学生基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentInfo studentInfo)
    {
        List<StudentInfo> list = studentInfoService.selectStudentInfoList(studentInfo);
        ExcelUtil<StudentInfo> util = new ExcelUtil<StudentInfo>(StudentInfo.class);
        util.exportExcel(response, list, "学生基本信息数据");
    }

    @Log(title = "学生基本信息", businessType = BusinessType.IMPORT) // todo
    @PreAuthorize("@ss.hasPermi('student:student_info:import')") // todo
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<StudentInfo> util = new ExcelUtil<>(StudentInfo.class); // todo
        List<StudentInfo> stuList = util.importExcel(file.getInputStream()); // todo
        String operName = getUsername();
        String message = studentInfoService.importUser(stuList, updateSupport, operName); // todo
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<StudentInfo> util = new ExcelUtil<>(StudentInfo.class); // todo
        util.importTemplateExcel(response, "学生基本信息");
    }


    /**
     * 获取学生基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:student_info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(studentInfoService.selectStudentInfoById(id));
    }

    /**
     * 新增学生基本信息
     */
    @PreAuthorize("@ss.hasPermi('student:student_info:add')")
    @Log(title = "学生基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentInfo studentInfo)
    {
        return toAjax(studentInfoService.insertStudentInfo(studentInfo));
    }

    /**
     * 修改学生基本信息
     */
    @PreAuthorize("@ss.hasPermi('student:student_info:edit')")
    @Log(title = "学生基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentInfo studentInfo)
    {
        return toAjax(studentInfoService.updateStudentInfo(studentInfo));
    }

    /**
     * 删除学生基本信息
     */
    @PreAuthorize("@ss.hasPermi('student:student_info:remove')")
    @Log(title = "学生基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(studentInfoService.deleteStudentInfoByIds(ids));
    }
}
