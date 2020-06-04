package com.ltl.yangzi.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ltl.yangzi.common.R;
import com.ltl.yangzi.common.annotion.SysLog;
import com.ltl.yangzi.entity.SysLogEntity;
import com.ltl.yangzi.entity.SysRoleMenu;
import com.ltl.yangzi.service.SysLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/log")
public class SysLogController {
  @Autowired
  private SysLogServiceImpl sysLogService;

  /**
   * 列表
   */
  @ResponseBody
  @GetMapping("/list")
  public R list(@RequestParam Map<String, Object> params){
    String num = (String) params.get("pageNum");
    String size = (String) params.get("pageSize");
    int n = Integer.parseInt(num);
    int s = Integer.parseInt(size);
    PageInfo<SysLog> page = PageHelper.startPage(n, s).doSelectPageInfo(()->sysLogService.queryPage(params));
    return R.ok().put("page", page);
  }

  /**
   * 删除日志对象
   */
  @GetMapping("/del")
  public R delete(Long id){
    try {
      if (sysLogService.removeById(id)){
        return R.ok();
      } else {
        return R.error(0,"删除失败");
      }
    } catch (Exception e){
      e.printStackTrace();
      return R.error();
    }
  }

  /**
   * 批量 删除
   */
  //@SysLog("批量删除")
  @PostMapping("/delAll")
  public R batchDelete(@RequestBody SysLogEntity[] sysLogEntity){
    for (SysLogEntity list: sysLogEntity){
      delete(list.getId());
    }
    return R.ok();
  }



}
