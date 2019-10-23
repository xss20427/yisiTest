package com.wz.yisitest.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wz.yisitest.dto.ContextDto;
import com.wz.yisitest.entity.Context;
import com.wz.yisitest.service.impl.ContextServiceImpl;
import com.wz.yisitest.util.ConverterUtil;
import com.wz.yisitest.util.SnowFlake;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wz.yisitest.util.ConverterUtil.populate;

/**
 * <p>
 * 文章评论表 前端控制器
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Controller
@RequestMapping("/context")
public class ContextController {
    @Autowired
    ContextServiceImpl contextService;


    @RequestMapping(value = "/add")
    @RequiresPermissions("user:add")
    @ResponseBody
    public boolean addItems(@RequestParam(value = "title", defaultValue = "Default comment title") String title,
                            @RequestParam(value = "context") String context,
                            @RequestParam(value = "typeId") long typeId) {
        if (!title.equals("") && !context.equals("")) //文章内容以及标题不允许为空
        {
            //TypeId=-1 文章  其他为评论
            Context myContext = new Context();
            String userName = (String) SecurityUtils.getSubject().getPrincipal();//获取当前用户名
            try {
                myContext.setId(SnowFlake.generator());
                myContext.setPid(typeId);
                myContext.setTitle(title);
                myContext.setContext(context);
                myContext.setCreater(userName);
                myContext.setCreateDate(LocalDateTime.now());
                contextService.saveOrUpdate(myContext);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    //删除或修改自己的文章
    @RequestMapping(value = "/modify")
    @RequiresPermissions("user:modify")
    @ResponseBody
    public boolean delOrUpdate(@RequestParam(value = "typeId") int typeId,
                               @RequestParam(value = "id") long id,
                               @RequestParam(value = "context", required = false, defaultValue = "") String context) {
        //防止注入 还需要添加逻辑判断条件
        if (typeId == 1) {
            //typeID=1 执行删除文章
            Subject SecurityUser = SecurityUtils.getSubject();
            System.out.println(SecurityUser.getPrincipal());
            if (SecurityUser.hasRole("admin")) {
                contextService.removeById(id);//管理员可以删除所有人的文章
            } else {
                //非管理员用户需要判断一下 是否自己的文章 或者 是否为自己文章下的评论
                Context tempContext = contextService.getById(id);
                if (tempContext.getCreater().equals(SecurityUser.getPrincipal()) || contextService.list(Wrappers.<Context>lambdaQuery().eq(Context::getId, tempContext.getPid()).eq(Context::getCreater, SecurityUser.getPrincipal())).size() > 0) {
                    contextService.removeById(id);//校验成功 删除成功
                }

            }
            return true;
        } else {
            //其他typeId执行更新操作
            if (context.equals("")) return false; //不允许为空
            Context c = contextService.getById(id);
            c.setContext(context);
            contextService.saveOrUpdate(c);
            return true;
        }
    }

    /**
     * @param typeId  0:所有 不用传入值 1：查询自己发布的 2：提供单篇查询方法 3：提供分页查询
     * @param id
     * @param title
     * @param creator
     * @return
     */
    @RequestMapping(value = "/show")
    @RequiresPermissions("user:show")
    @ResponseBody
    public List<ContextDto> content(@RequestParam(value = "typeId") int typeId,
                                    @RequestParam(value = "Id", required = false, defaultValue = "-1") long id,
                                    @RequestParam(value = "title", required = false, defaultValue = "") String title,
                                    @RequestParam(value = "creator", required = false, defaultValue = "") String creator,
                                    @RequestParam(value = "date", required = false, defaultValue = "") String mDate) {
        //查询的文章保存在 contexts里
        List<ContextDto> contexts = new ArrayList<>();
        //查询所有文章
        List<Context> contextList = null;
        //查询所有评论
        List<Context> commentList = null;
        //为了减少内存开销 typeid为4 不在此做查询
        if (typeId != 4) {
            contextList = contextService.list(Wrappers.<Context>lambdaQuery().eq(Context::getPid, -1));
            commentList = contextService.list(Wrappers.<Context>lambdaQuery().ne(Context::getId, -1));
        }
        if (typeId == 0) {//查询所有文章
            for (Context context : contextList) {
                contexts.add(getComment(context, commentList));
            }
        } else if (typeId == 1) {
            //查询自己发布过的文章 进行CRUD操作
            String userName = (String) SecurityUtils.getSubject().getPrincipal();//获取当前用户名
            for (Context context : contextList) {
                if (context.getCreater().equals(userName)) { //查询文章是否为自己的
                    contexts.add(getComment(context, commentList));
                }
            }
        } else if (typeId == 2) {
            //单篇 通过ID查询
            for (Context context : contextList) {
                if (context.getId() == id) {
                    contexts.add(getComment(context, commentList));
                }
            }
        } else if (typeId == 3) {
            //分页查询 通过各种手段查询
            //物理分页 理解：执行limit 从数据库一点点取出来 减少内存占用
            //逻辑分页 理解：一次性全部取出来，进行程序的条件判断，返回对应数据
            //存在注入 全都有值的情况 则按照顺序
            //使用Lambda防止误写
            LambdaQueryWrapper<Context> wrapper = new LambdaQueryWrapper<Context>();
            Page<Context> page = new Page<>(1, 10);//可以根据前端传值 题目没有要求 写死了
            boolean isSelect = true;
            if (!title.equals("")) {
                //标题不为空 通过标题查询
                wrapper.like(Context::getTitle, title);
                isSelect = false;
            } else if (!mDate.equals("")) {
                //根据时间查询
                try {
                    //可能非法传参 添加try
                    wrapper.gt(Context::getCreateDate, mDate.split(",")[0])//大于开始时间
                            .lt(Context::getCreateDate, mDate.split(",")[1]);//小于结束时间
                    isSelect = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else if (!creator.equals("")) {
                //根据创建者查询
                isSelect = false;
                wrapper.eq(Context::getCreater, creator);
            }
            //设置过wrapper的进行传值
            if (!isSelect) {
                IPage<Context> iPage = contextService.page(page, wrapper);
                System.out.println("总页数" + iPage.getPages());
                for (Context context : iPage.getRecords()) {
                    contexts.add((ContextDto) ConverterUtil.populate(context, new ContextDto()));
                }
            }
        }
        return contexts;
    }

    public ContextDto getComment(Context context, List<Context> commentList) {
        ContextDto contextDto = (ContextDto) populate(context, new ContextDto());
        for (Context comment : commentList) {
            //每一条评论记录
            if (String.valueOf(comment.getPid()).equals(contextDto.getId())) {
                contextDto.addComment((ContextDto) populate(comment, new ContextDto()));
            }
        }
        return contextDto;
    }
}

