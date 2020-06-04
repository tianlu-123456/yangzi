package com.ltl.yangzi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ltl.yangzi.common.entity.BaseEntity;

import java.io.Serializable;

@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 用户id
   */
  private Integer roleId;
  /**
   * 菜单id
   */
  private Long menuId;

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  /**
   * 设置：菜单id
   */
  public void setMenuId(Long menuId) {
    this.menuId = menuId;
  }
  /**
   * 获取：菜单id
   */
  public Long getMenuId() {
    return menuId;
  }
}

