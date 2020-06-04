package com.ltl.yangzi.service;

import com.ltl.yangzi.dao.SysRoleMenuDao;
import com.ltl.yangzi.entity.SysRoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenu>{
  @Resource
  private SysRoleMenuDao sysRoleMenuDao;


  /**
   * 获取搜索框
   */
  public List<Map<String, Object>> getSearch() {
    sysRoleMenuDao.selectSearchBy()
  }
}
