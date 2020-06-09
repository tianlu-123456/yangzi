package com.ltl.yangzi.service;

import com.ltl.yangzi.dao.SysRoleDao;
import com.ltl.yangzi.entity.SysRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleService extends ServiceImpl<SysRoleDao, SysRole>{
  @Resource
  private SysRoleDao sysRoleDao;

  /**
   * 获取角色 搜素框
   */
  public List<Map<String, Object>> getSearch() {
    //获取字段信息和别名
    List<Map<String, Object>> o = sysRoleDao.selectSearchBy();
    //遍历
    Iterator<Map<String, Object>> l = o.iterator();
    while (l.hasNext()) {
      Map<String, Object> map = l.next();
      if ("编号".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("删除标记".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("备注信息".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("更新时间".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("更新者".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("创建时间".equals(map.get("column_comment"))){
        l.remove();
      }
      if ("创建者".equals(map.get("column_comment"))){
        l.remove();
      }
    }
    return o;
  }


  /**
   * 获取角色列表
   */
  public List<SysRole> getList(SysRole sysRole) {
    return sysRoleDao.getList(sysRole);
  }

  /*添加角色*/
  public boolean add(SysRole sysRole) {
    boolean save = super.save(sysRole);
    return save;
  }

  /*角色表列表列*/
  public List<SysRole> selectPages(String[] sysRole) {
    // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题
    // page.setOptimizeCountSql(false);
    // 不查询总记录数
    // page.setSearchCount(false);
    // 注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
    return sysRoleDao.selectArrayList(sysRole);
  }

  /*下载模板*/
  public List<SysRole> selectTemplate(SysRole sysRole) {
    return sysRoleDao.selectTemplate(sysRole);
  }
}
