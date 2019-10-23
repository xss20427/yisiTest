package com.wz.yisitest.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wz.yisitest.entity.Context;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：yisi
 * @date：21/10/2019 --------------
 * @return：自定义返回前端属性 entity对应数据库字段 所以单独创立一个实体
 */

public class ContextDto extends Model<Context> {
    private static final long serialVersionUID = 1L;
    // 返回前端的属性：id、标题、内容的前10个字符（超过10个的，加上省略号）、发布人、发布时间。
    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(pattern = "")
    private Long id;
    //返回StringID
    private String idS;

    public void setUserIdStr(String idS) {
        this.idS = idS;
    }

    /**
     * 标题
     */
    private String title;
    private String context;
    private String creater;
    private List<ContextDto> comment = new ArrayList<>();//评论
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createDate;

    public String getId() {
        return idS;
    }

    public List<ContextDto> getComment() {
        return comment;
    }

    public void addComment(ContextDto comment) {
        this.comment.add(comment);
    }

    public void setId(Long id) {
        this.id = id;
        setUserIdStr(String.valueOf(this.id));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
