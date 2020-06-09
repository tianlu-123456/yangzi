package com.ltl.yangzi.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ltl.yangzi.common.R;
import com.ltl.yangzi.common.annotion.SysLog;
import com.ltl.yangzi.common.utils.excel.ExportExcel;
import com.ltl.yangzi.entity.SysRole;
import com.ltl.yangzi.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@RestController
@RequestMapping("/sysrole")
public class SysRoleController {
  @Resource
  private SysRoleService sysRoleService;

  /**
   * 获取角色 对象搜索框
   */
  @PostMapping("/search")
  public R getSearchByTable() {
    List<Map<String, Object>> o = sysRoleService.getSearch();
    return R.ok().put("data", o);
  }

  /**
   * 获取Page 角色分页查询 对象
   */
  @PostMapping("/page")
  @RequiresPermissions("sys:sysRole:page")
  public R selectPage(SysRole sysRole) {
    PageInfo<Object> pageInfo = PageHelper.startPage(sysRole.getPageNum(), sysRole.getPageSize()).
        doSelectPageInfo(() -> sysRoleService.getList(sysRole));
    return R.ok().put("data", pageInfo);
  }

  /**
   * 获取List 角色列表或单个对象
   */
  @SysLog("查看角色信息")
  @PostMapping("/list")
  @RequiresPermissions("sys:sysRole:list")
  public R selectList(@RequestBody SysRole sysRole) {
    return R.ok().put("data", sysRoleService.getList(sysRole));
  }


  /**
   * 添加角色对象
   */
  @SysLog("添加角色")
  @PostMapping("/add")
  @RequiresPermissions("sys:sysRole:add")
  public R add(@RequestBody SysRole sysRole) {
    try {
      if(sysRoleService.add(sysRole)) {
        return R.ok();
      } else {
        return R.error(200, "添加失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.error();
    }
  }

  /**
   * 修改角色
   */
  @SysLog("修改角色")
  @PostMapping("edit")
  @RequiresPermissions("sys:sysRole:edit")
  public R update(@RequestBody SysRole sysRole) {
    try {
      if(sysRoleService.updateById(sysRole)) {
        return R.ok("修改成功");
      } else {
        return R.error(0, "修改失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.error();
    }
  }

  /**
   * 删除角色
   */
  @SysLog("删除角色")
  @PostMapping("/del")
  @RequiresPermissions("sys:sysRole:del")
  public R delete(Integer id) {
    try {
      if(sysRoleService.removeById(id)) {
        return R.ok("修改成功");
      } else {
        return R.error(0, "修改失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.error();
    }
  }

  /**
   * 批量删除角色
   */
  @SysLog("批量删除角色")
  @PostMapping("/delAll")
  public R batchDelete(@RequestBody SysRole[] sysRoles) {
    R s = null;
    for (SysRole list : sysRoles) {
      s = delete(list.getId());
    }
    return s;
  }

  /**
   * 导出角色
   */
  @SysLog("导出角色")
  @PostMapping("/export")
  @RequiresPermissions("sys:sysRole:export")
  public String exportFile(@RequestBody SysRole[] sysRoles, HttpServletResponse response) {
    System.out.println("导出");
    PageInfo<Object> page = new PageInfo<>();
    String[] arr = new String[sysRoles.length];
    for (int i = 0; i < sysRoles.length; i++) {
      arr[i] = sysRoles[i].getId().toString();
    }
    try {
      String fileName = "角色" + ".xlsx";
      PageInfo<Object> pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(() -> sysRoleService.selectPages(arr));
      new ExportExcel("角色", SysRole.class).setDataList(page.getList()).write(response, fileName).dispose();
      return "ok!";
    } catch (Exception e) {
      return "on!";
    }
  }

  /**
   * 下载角色模板
   */
  @PostMapping("/download")
  public String download(HttpServletResponse response) {
    System.out.println("下载模板");
    SysRole arr = new SysRole();
    PageInfo page = new PageInfo();
    try {
      String fileName = "角色" + ".xlsx";
      PageInfo<SysRole> pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(() -> sysRoleService.selectTemplate(arr));
      new ExportExcel("角色模板", SysRole.class).setDataList(pageInfo.getList()).write(response, fileName).dispose();
      return "ok!";
    } catch (Exception e) {
      e.printStackTrace();
      return "on!";
    }
  }

  /**
   * 导入角色
   */


}
