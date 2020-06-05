package com.ltl.yangzi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltl.yangzi.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {


  /*获取字段信息和别名*/
  List<Map<String, Object>> selectSearchBy();

  /*获取List和page*/
  List<SysRoleMenu> getList(SysRoleMenu sysRoleMenu);

  /*查找 id 在employeeIds范围内的数组*/
  List<SysRoleMenu> selectArrayList(String[] sysRoleMenu);

  /*查询id为null ，作为模板*/
  List<SysRoleMenu> selectTemplate(SysRoleMenu sysRoleMenu);
}
