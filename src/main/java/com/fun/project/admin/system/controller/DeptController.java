package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.StringUtils;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.framework.web.entity.Ztree;
import com.fun.project.admin.system.entity.Dept;
import com.fun.project.admin.system.entity.role.Role;
import com.fun.project.admin.system.service.IDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fun.common.result.CommonResult.*;

/**
 * @author DJun
 * @date 2019/11/1
 */
@Api(tags = {"admin部门"})
@Controller
@RequestMapping("admin/system/dept")
public class DeptController extends AdminBaseController {
    private String prefix = "system/dept";

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept() {
        return view(prefix + "/dept");
    }

    @ApiOperation("获取全部部门")
    @RequiresPermissions("system:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Dept> list(Dept dept) {
        return deptService.selectDeptList(dept);
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        mmap.put("dept", deptService.selectDeptById(parentId));
        return view(prefix + "/add");
    }

    @ApiOperation("新增部门")
    @Log("新增部门")
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult addSave(@Validated Dept dept) {
        if (Constants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return failed("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        return success(deptService.insertDept(dept));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        Dept dept = deptService.selectDeptById(deptId);
        if (StringUtils.isNotNull(dept) && 100L == deptId) {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return view(prefix + "/edit");
    }

    @ApiOperation("编辑部门")
    @Log("编辑部门")
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(@Validated Dept dept) {
        if (Constants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return failed("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return failed("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        return success(deptService.updateDept(dept));
    }


    @ApiOperation("删除部门")
    @Log("删除部门")
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public CommonResult remove(@PathVariable("deptId") Long deptId) {
        if (deptService.selectDeptCount(deptId) > 0) {
            return warn("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return warn("部门存在用户,不允许删除");
        }
        return success(deptService.deleteDeptById(deptId));
    }

    @ApiOperation("校验部门名称")
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(Dept dept) {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return view(prefix + "/tree");
    }

    @ApiOperation("加载部门列表树")
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        return deptService.selectDeptTree(new Dept());
    }

    @ApiOperation("加载角色部门（数据权限）列表树")
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData(Role role) {
        return deptService.roleDeptTreeData(role);
    }

}
