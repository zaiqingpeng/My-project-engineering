package com.ruoyi.contents.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 知识仓库对象 studying_contents
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
public class StudyingContents extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 类型 */
    @Excel(name = "类型")
    private String contentType;

    /** 文本 */
    @Excel(name = "文本")
    private String contentText;

    /** 图片/视频 */
    @Excel(name = "图片/视频")
    private String contentPictureVideo;

    /** 文档 */
    @Excel(name = "文档")
    private String contentFile;

    /** 管理 */
    @Excel(name = "管理")
    private String contentManager;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date contentTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
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
    public void setContentText(String contentText) 
    {
        this.contentText = contentText;
    }

    public String getContentText() 
    {
        return contentText;
    }
    public void setContentPictureVideo(String contentPictureVideo) 
    {
        this.contentPictureVideo = contentPictureVideo;
    }

    public String getContentPictureVideo() 
    {
        return contentPictureVideo;
    }
    public void setContentFile(String contentFile) 
    {
        this.contentFile = contentFile;
    }

    public String getContentFile() 
    {
        return contentFile;
    }
    public void setContentManager(String contentManager) 
    {
        this.contentManager = contentManager;
    }

    public String getContentManager() 
    {
        return contentManager;
    }
    public void setContentTime(Date contentTime) 
    {
        this.contentTime = contentTime;
    }

    public Date getContentTime() 
    {
        return contentTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("contentType", getContentType())
            .append("contentText", getContentText())
            .append("contentPictureVideo", getContentPictureVideo())
            .append("contentFile", getContentFile())
            .append("contentManager", getContentManager())
            .append("contentTime", getContentTime())
            .toString();
    }
}
