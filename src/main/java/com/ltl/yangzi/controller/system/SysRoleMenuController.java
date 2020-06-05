package com.ltl.yangzi.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ltl.yangzi.common.R;
import com.ltl.yangzi.common.annotion.SysLog;
import com.ltl.yangzi.common.utils.excel.ExportExcel;
import com.ltl.yangzi.entity.SysRoleMenu;
import com.ltl.yangzi.service.SysRoleMenuServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sysrolemenu")
public class SysRoleMenuController {
  @Autowired
  private SysRoleMenuServiceImpl sysRoleMenuService;

  /**
   * 获取 对象搜索框
   */
  @PostMapping("/search")
  public R getSearchByTable(){
    List<Map<String, Object>> o = sysRoleMenuService.getSearch();
    return R.ok().put("data", o);
  }

  /**
   * 获取Page 分页查询 对象
   */
  @PostMapping("/page")
  @RequiresPermissions("sys:sysRoleMenu:page")
  public R selectPage(@RequestBody SysRoleMenu sysRoleMenu){
    PageInfo<Object> pageInfo = PageHelper.startPage(sysRoleMenu.getPageNum(),
        sysRoleMenu.getPageSize()).doSelectPageInfo(() -> sysRoleMenuService.getList(sysRoleMenu));
    return R.ok().put("data", pageInfo);
  }

  /**
   * 获取list 列表或单个对象
   */
  @SysLog("查看信息")
  @PostMapping("/list")
  @RequiresPermissions("sys:sysRoleMenu:list")
  public R selectList(@RequestBody SysRoleMenu sysRoleMenu){
    return R.ok().put("data", sysRoleMenuService.getList(sysRoleMenu));
  }

  /**
   * 添加 对象
   */
  @SysLog("/添加")
  @PostMapping("/add")
  @RequiresPermissions("sys:sysRoleMenu:add")
  public R add(@RequestBody SysRoleMenu sysRoleMenu){
    try {
      if (sysRoleMenuService.add(sysRoleMenu)){
        return R.ok();
      } else {
        return R.error(200, "添加失败");
      }
    } catch (Exception e){
      e.printStackTrace();
      return R.error();
    }
  }

  /**
   * 修改 对象
   */
  @SysLog("修改")
  @PostMapping("/edit")
  @RequiresPermissions("sys:sysRoleMenu:edit")
  public R update(@RequestBody SysRoleMenu sysRoleMenu){
    try {
      if (sysRoleMenuService.updateById(sysRoleMenu)){
        return R.ok("修改成功");
      } else {
        return R.error(0, "添加失败");
      }
    } catch (Exception e){
      e.printStackTrace();
      return R.error();
    }
  }






  /**
   * 删除对象
   */
  @SysLog("删除")
  @GetMapping("/del")
  @RequiresPermissions("sys:sysRoleMenu:del")
  public R delete(Integer id){
    try {
      if (sysRoleMenuService.removeById(id)){
        return R.ok();
      } else {
        return R.error(0,"删除失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return R.error();
    }
  }

  /**
   * 批量删除
   */
  @SysLog("批量删除")
  @PostMapping("/delAll")
  public R batchDelete(@RequestBody SysRoleMenu[] sysRoleMenu){
    R s = null;
    for (SysRoleMenu list : sysRoleMenu) {
      s = delete(list.getId());
    }
    return s;
  }

  /**
   * 导出对象
   */
  @SysLog("导出")
  @PostMapping("/export")
  @RequiresPermissions("sys:sysRoleMenu:export")
  public String exportFile(@RequestBody SysRoleMenu[] sysRoleMenus,
                           HttpServletResponse response){
    System.out.println("导出");
    PageInfo<Object> page = new PageInfo<>();
    String[] arr = new String[sysRoleMenus.length];
    for (int i = 0; i < sysRoleMenus.length; i++) {
      arr[i] = sysRoleMenus[i].getId().toString();
    }
    try {
      String fileName = "" + ".xlsx";
      PageInfo<Object> pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(() ->
          sysRoleMenuService.selectPages(arr));
      new ExportExcel("",SysRoleMenu.class).setDataList(pageInfo.getList()).write(response, fileName).dispose();
      return "ok!";
    } catch (Exception e){
      return "on!";
    }
  }

  /**
   * 下载模板
   */
  @PostMapping("download")
  public String download(HttpServletResponse response){
    System.out.println("下载模板");
    SysRoleMenu arr = new SysRoleMenu();
    PageInfo page = new PageInfo();
    try {
      String fileName = "" + ".xlsx";
      PageInfo<SysRoleMenu> pages =
          PageHelper.startPage(1, 10).
              doSelectPageInfo(() ->
                  sysRoleMenuService.selectTemplate(arr));
      new ExportExcel("模板", SysRoleMenu.class).setDataList(pages.getList()).write(response, fileName).dispose();
      return "ok!";
    } catch (Exception e){
      e.printStackTrace();
      return "file!";
    }
  }


}
