package com.ltl.yangzi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltl.yangzi.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {
  /*获取字段信息和别名*/
  List<Map<String, Object>> selectSearchBy();


  /*获取角色列表*/
  List<SysRole> getList(SysRole sysRole);

  /*查找 id 在employeeIds范围内的数组*/
  List<SysRole> selectArrayList(String[] sysRole);

  /*查询id为null ，作为模板*/
  List<SysRole> selectTemplate(SysRole sysRole);
}
