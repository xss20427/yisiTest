package com.wz.yisitest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章评论表
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@TableName("yisi_context")
public class Context extends Model<Context> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * -1文章表 如不为-1 则为对应ID评论
     */
    private Long pid;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String context;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 创建者
     */
    private String creater;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Context{" +
                "id=" + id +
                ", pid=" + pid +
                ", title=" + title +
                ", context=" + context +
                ", updateDate=" + updateDate +
                ", updater=" + updater +
                ", createDate=" + createDate +
                ", creater=" + creater +
                "}";
    }
}
