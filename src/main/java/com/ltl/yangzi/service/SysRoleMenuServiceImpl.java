package com.ltl.yangzi.service;

import com.ltl.yangzi.dao.SysRoleMenuDao;
import com.ltl.yangzi.entity.SysRoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
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
    List<Map<String, Object>> o = sysRoleMenuDao.selectSearchBy();
    Iterator<Map<String, Object>> l = o.iterator();
    while (l.hasNext()){
      Map<String, Object> map = l.next();
      if ("编号".equals(map.get("colum_comment"))){
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
   * 获取列表
   */
  public List<SysRoleMenu> getList(SysRoleMenu sysRoleMenu) {
    return sysRoleMenuDao.getList(sysRoleMenu);
  }

  /**
   * 添加
   */
  public boolean add(SysRoleMenu sysRoleMenu) {
    boolean result = super.save(sysRoleMenu);
    return result;
  }

  /**
   * 导出列表
   */
  public List<SysRoleMenu> selectPages(String[] sysRoleMenu) {
    /**
     *      不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题
     *      page.setOptimizeCountSql(false);
     *      不查询总记录数
     *      page.setSearchCount(false);
     *      注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
     */
    return sysRoleMenuDao.selectArrayList(sysRoleMenu);
  }

  /**
   * 下载模板
   */
  public List<SysRoleMenu> selectTemplate(SysRoleMenu sysRoleMenu) {
    return sysRoleMenuDao.selectTemplate(sysRoleMenu);
  }

}
