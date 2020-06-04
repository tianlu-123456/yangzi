package com.ltl.yangzi.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 实体类基类
 *
 * @author shengsen
 * @date 2018-11-28
 */

public class BaseEntity {
  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.AUTO)
  protected Integer id;
  /**
   * 创建者
   */
  @TableField(value = "create_by", fill = FieldFill.INSERT)
  protected Integer createBy;
  /**
   * 更新者
   */
  @TableField(value = "update_by",fill = FieldFill.UPDATE)
  protected Integer updateBy;
  /**
   * 创建日期
   *
   */
  @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @TableField(value = "create_date", fill = FieldFill.INSERT)
  protected Date createDate;
  /**
   * 修改日期
   */
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  @TableField(value = "update_date",fill = FieldFill.UPDATE )
  protected Date updateDate;
  /**
   * 备注
   */
  protected String remarks;
  /**
   * 删除标记
   */
  @TableLogic
  protected Integer delFlag;
  /**
   * 排序
   */
  protected Integer sort;
  /**
   * 页数
   */
  @TableField(exist = false)
  private Integer pageNum;
  /**
   * 每页数量
   */
  @TableField(exist = false)
  private Integer pageSize;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCreateBy() {
    return createBy;
  }

  public void setCreateBy(Integer createBy) {
    this.createBy = createBy;
  }

  public Integer getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(Integer updateBy) {
    this.updateBy = updateBy;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public Integer getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(Integer delFlag) {
    this.delFlag = delFlag;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public Integer getPageNum() {
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
