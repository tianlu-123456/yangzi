package com.ltl.yangzi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ltl.yangzi.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * 角色
 *
 * @author zjl
 * @date 2019-09-16 13:18:39
 */
@TableName("sys_role")
public class SysRole extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  /**
   * 角色id
   */
  @TableId
  private Integer roleId;
  /**
   * 角色名称
   */
  private String roleName;
  /**
   * 备注
   */
  private String remark;

  /**
   * 设置：角色名称
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  /**
   * 获取：角色名称
   */
  public String getRoleName() {
    return roleName;
  }
  /**
   * 设置：备注
   */
  public void setRemark(String remark) {
    this.remark = remark;
  }
  /**
   * 获取：备注
   */
  public String getRemark() {
    return remark;
  }
}

