package com.ltl.yangzi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ltl.yangzi.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author zjl
 * @date 2019-06-28 11:21:08
 */
@TableName("sys_user_menu")
public class SysUserMenu extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 账号
   */
  @TableField(exist = false)
  private String mobile;

  /**
   * 密码
   */
  @TableField(exist = false)
  private String password;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * 用户ID
   */
  private Integer userId;
  /**
   * 菜单ID
   */
  private Long menuId;

  /**
   * 菜单id集合
   */
  @TableField(exist = false)
  private List menuList;

  public List getMenuList() {
    return menuList;
  }

  public void setMenuList(List menuList) {
    this.menuList = menuList;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  /**
   * 设置：菜单ID
   */
  public void setMenuId(Long menuId) {
    this.menuId = menuId;
  }
  /**
   * 获取：菜单ID
   */
  public Long getMenuId() {
    return menuId;
  }

  /**
   * 权限集合
   */
  @TableField(exist = false)
  private SysUserMenu permsList[];

  public SysUserMenu[] getPermsList() {
    return permsList;
  }

  public void setPermsList(SysUserMenu[] permsList) {
    this.permsList = permsList;
  }

  /**
   * 菜单名称
   */
  @TableField(exist = false)
  private String name;

  /**
   * 菜单父级id
   */
  @TableField(exist = false)
  private Long parentId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }
}
