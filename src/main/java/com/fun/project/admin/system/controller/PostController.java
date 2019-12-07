package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.system.entity.Post;
import com.fun.project.admin.system.service.IPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fun.common.result.CommonResult.failed;
import static com.fun.common.result.CommonResult.success;

/**
 * @author DJun
 * @date 2019/11/3
 */
@Api(tags = {"管理员岗位信息"})
@Controller
@RequestMapping("/admin/system/post")
public class PostController extends AdminBaseController {
    private String prefix = "system/post";

    @Autowired
    private IPostService postService;

    @RequiresPermissions("system:post:view")
    @GetMapping()
    public String post() {
        return view(prefix + "/post");
    }

    @ApiOperation("分页获取岗位列表")
    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult list(Post post) {
        startPage();
        List<Post> list = postService.selectPostList(post);
        return success(CommonPage.restPage(list));
    }

    /**
     * 新增岗位
     */
    @GetMapping("/add")
    public String add() {
        return view(prefix + "/add");
    }

    @ApiOperation("新增岗位")
    @RequiresPermissions("system:post:add")
    @Log("新增岗位")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult addSave(@Validated Post post) {
        if (Constants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return failed("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (Constants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return failed("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        return success(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
        mmap.put("post", postService.selectPostById(postId));
        return view(prefix + "/edit");
    }

    @ApiOperation("修改岗位信息")
    @RequiresPermissions("system:post:edit")
    @Log("修改岗位信息")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(@Validated Post post) {
        if (Constants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return failed("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (Constants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return failed("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        return success(postService.updatePost(post));
    }

    @ApiOperation("异步校验岗位名称")
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public String checkPostNameUnique(Post post) {
        return postService.checkPostNameUnique(post);
    }

    @ApiOperation("校验岗位编码")
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public String checkPostCodeUnique(Post post)
    {
        return postService.checkPostCodeUnique(post);
    }
}
