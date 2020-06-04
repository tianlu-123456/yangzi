package com.ltl.yangzi.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltl.yangzi.common.annotion.ExcelField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class SysUserEntity {
  /**
   * 用户ID
   */
  @TableId(type = IdType.AUTO)
  private Integer userId;

  /**
   * 用户名
   */
  @ExcelField(title = "账号", align = 2, sort = 20)
  private String userName;

  /**
   * 密码
   */
  //@NotBlank(message = "密码不能为空", groups = AddGroup.class)
  private String password;

  /**
   * 盐
   */
  private String salt;

  /**
   * 邮箱
   */
//    @ExcelField(title = "邮箱", align = 2, sort = 20)
  //@Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
  private String email;

  /**
   * 手机号
   */
//    @ExcelField(title = "手机号", align = 2, sort = 20)
  private String mobile;

  /**
   * 验证码
   */
  @TableField(exist = false)
  private String code;


  /**
   * 创建者ID
   */
  private int createBy;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createDate;

  /**
   * 出生日期
   */
//    @ExcelField(title = "出生日期", align = 2, sort = 20)
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date borth;

  /**
   * 身份认证
   */
  private String isCertified;

  /**
   * 身份
   */
  private Integer identity;

  /**
   * 登录名
   */
//    @ExcelField(title = "登录名", align = 2, sort = 20)
  private String loginName;
  /**
   * 性别 男：1 女：2
   */
//    @ExcelField(title = "性别", align = 2, sort = 20)
  private Integer gender;
  /**
   * 身份证号
   */
//    @ExcelField(title = "身份证", align = 2, sort = 20)
  private String idNumber;

  /**
   * 用户地址
   */
//    @ExcelField(title = "地址", align = 2, sort = 20)
  private String userAddress;

  /**
   * 最后登录ip
   */
//    @ExcelField(title = "最后登录ip", align = 2, sort = 20)
  private String lastLogonIp;
  /**
   * 最后登录时间
   */
//    @ExcelField(title = "最后登录时间", align = 2, sort = 20)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastLoginTime;

  /**
   * 头像
   */
  @TableField(exist = false)
  private String photo;

  public int getCreateBy() {
    return createBy;
  }

  public void setCreateBy(int createBy) {
    this.createBy = createBy;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getIsCertified() {
    return isCertified;
  }

  public void setIsCertified(String isCertified) {
    this.isCertified = isCertified;
  }

  /**
   * 插件返回值
   *
   * @return
   */
  @TableField(exist = false)
  private String value;

  public Date getCreateDate() {
    return createDate;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

//    public Date getBorth() {
//        return borth;
//    }
//
//    public void setBorth(Date borth) {
//        this.borth = borth;
//    }

  public Integer getIdentity() {
    return identity;
  }

  public void setIdentity(Integer identity) {
    this.identity = identity;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public String getUserAddress() {
    return userAddress;
  }

  public void setUserAddress(String userAddress) {
    this.userAddress = userAddress;
  }

  public String getLastLogonIp() {
    return lastLogonIp;
  }

  public void setLastLogonIp(String lastLogonIp) {
    this.lastLogonIp = lastLogonIp;
  }

  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getWxnumber() {
    return wxnumber;
  }

  public void setWxnumber(String wxnumber) {
    this.wxnumber = wxnumber;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getUnencryptedPassword() {
    return unencryptedPassword;
  }

  public void setUnencryptedPassword(String unencryptedPassword) {
    this.unencryptedPassword = unencryptedPassword;
  }

  /**
   * 关联角色 为树状图铺垫
   */
  @TableField(exist = false)
  private Integer parentId;

  /**
   * 微信号
   *
   * @return
   */
  @TableField(exist = false)
  private String wxnumber;

  /**
   * 城市
   */
  private String city;


  /**
   * 未加密密码
   */
  @ExcelField(title = "密码", align = 2, sort = 20)
  private String unencryptedPassword;

  /**
   * 分页
   */
  @TableField(exist = false)
  private Integer pageNum;

  @TableField(exist = false)
  private Integer pageSize;

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

  /**
   * 用户权限数据
   */
  @TableField(exist = false)
  private String permis;

  public String getPermis() {
    return permis;
  }

  public void setPermis(String permis) {
    this.permis = permis;
  }

  public Set<SysUserEntity> getPermisList() {
    return permisList;
  }

  public void setPermisList(Set<SysUserEntity> permisList) {
    this.permisList = permisList;
  }

  /**
   * 用户权限集合
   */
  @TableField(exist = false)
  private Set<SysUserEntity> permisList;

  /**
   * 删除标识
   */
  private Integer delFlag;

  public Integer getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(Integer delFlag) {
    this.delFlag = delFlag;
  }

  /**
   * 年份
   */
  @TableField(exist = false)
  private Integer year;

  /**
   * 月份
   */
  @TableField(exist = false)
  private Integer month;

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  @TableField(exist = false)
  private Integer num;

  /**
   * 日
   */
  @TableField(exist = false)
  private Integer day;

  public Integer getDay() {
    return day;
  }

  public void setDay(Integer day) {
    this.day = day;
  }

  /**
   * sessionId
   */
  @TableField(exist = false)
  private String sessionId;

  public String getSessionId() {
    return sessionId;
  }

//  public List<HouseLoan> getHouseLoan() {
//    return houseLoan;
//  }
//
//  public void setHouseLoan(List<HouseLoan> houseLoan) {
//    this.houseLoan = houseLoan;
//  }
//
//  public List<SpecialAssets> getSpecialAssets() {
//    return specialAssets;
//  }
//
//  public void setSpecialAssets(List<SpecialAssets> specialAssets) {
//    this.specialAssets = specialAssets;
//  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

//  /**
//   * 房贷业务
//   */
//  @TableField(exist = false)
//  private List<HouseLoan> houseLoan;

//  /**
//   * 特殊资产
//   */
//  @TableField(exist = false)
//  private List<SpecialAssets> specialAssets;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * 手机号未隐藏
   */
  @TableField(exist = false)
  private String phone;

  private int level;

  private String levelOne;

  private String levelTwo;

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getLevelOne() {
    return levelOne;
  }

  public void setLevelOne(String levelOne) {
    this.levelOne = levelOne;
  }

  public String getLevelTwo() {
    return levelTwo;
  }

  public void setLevelTwo(String levelTwo) {
    this.levelTwo = levelTwo;
  }

  @TableField(exist = false)
  private List<String> staff;

  public List<String> getStaff() {
    return staff;
  }

  public void setStaff(List<String> staff) {
    this.staff = staff;
  }

  @TableField(exist = false)
  private String staffSingle;

  public String getStaffSingle() {
    return staffSingle;
  }

  public void setStaffSingle(String staffSingle) {
    this.staffSingle = staffSingle;
  }
}
