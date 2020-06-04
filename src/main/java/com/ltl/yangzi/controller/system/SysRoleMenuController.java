package com.ltl.yangzi.controller.system;

import com.ltl.yangzi.common.R;
import com.ltl.yangzi.common.annotion.SysLog;
import com.ltl.yangzi.entity.SysRoleMenu;
import com.ltl.yangzi.service.SysRoleMenuServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
