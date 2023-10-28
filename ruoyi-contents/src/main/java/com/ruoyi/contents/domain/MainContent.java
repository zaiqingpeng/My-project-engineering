package com.ruoyi.contents.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 认知与学习对象 main_content
 *
 * @author ruoyi
 * @date 2023-03-23
 */
public class MainContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer id;

    /** 类型 */
    @Excel(name = "类型")
    private String contentType;

    /** 文本 */
    @Excel(name = "文本")
    private String textContent;

    /** 视频 */
    @Excel(name = "文件")
    private String fileContent;

    /** 图片 */
    @Excel(name = "图片")
    private String pictureContent;

    /** 图文 */
    @Excel(name = "图文")
    private String allContent;

    /** 教师 */
    @Excel(name = "教师")
    private String techer;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getContentType()
    {
        return contentType;
    }
    public void setTextContent(String textContent)
    {
        this.textContent = textContent;
    }

    public String getTextContent()
    {
        return textContent;
    }
    public void setfileContent(String fileContent)
    {
        this.fileContent = fileContent;
    }

    public String getfileContent()
    {
        return fileContent;
    }
    public void setPictureContent(String pictureContent)
    {
        this.pictureContent = pictureContent;
    }

    public String getPictureContent()
    {
        return pictureContent;
    }
    public void setAllContent(String allContent)
    {
        this.allContent = allContent;
    }

    public String getAllContent()
    {
        return allContent;
    }
    public void setTecher(String techer)
    {
        this.techer = techer;
    }

    public String getTecher()
    {
        return techer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("contentType", getContentType())
            .append("textContent", getTextContent())
            .append("fileContent", getfileContent())
            .append("pictureContent", getPictureContent())
            .append("allContent", getAllContent())
            .append("techer", getTecher())
            .toString();
    }
}
