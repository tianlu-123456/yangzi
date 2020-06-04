package com.ltl.yangzi.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ltl.yangzi.common.R;
import com.ltl.yangzi.entity.SysMenuEntity;
import com.ltl.yangzi.entity.SysUserEntity;
import com.ltl.yangzi.entity.SysUserMenu;
import com.ltl.yangzi.service.ShiroServiceImpl;
import com.ltl.yangzi.service.SysUserMenuService;
import com.ltl.yangzi.service.SysUserServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色与菜单对应关系
 */
@RestController
@RequestMapping("/sysusermenu")
public class SysUserMenuController {
  @Resource
  private SysUserMenuService sysUserMenuService;
  @Autowired
  private SysUserServiceImpl sysUserService;
  @Autowired
  private ShiroServiceImpl shiroService;

  /**
   * 获取角色与菜单对应关系 对象搜索框
   */
  @PostMapping("/search")
  public R getSearchByTable() {
    List<Map<String, Object>> o = sysUserMenuService.getSearch();
    return R.ok().put("data", o);
  }

  /**
   * 查询出所有可选的权限
   */
  @RequestMapping("/permsList")
  public R permsList() {
    List<SysMenuEntity> list = sysUserMenuService.permsList();
    return R.ok().put("data", list);
  }

  /**
   * 用户权限管理
   */
  @RequestMapping("/list")
  public R getList(@RequestBody SysUserMenu sysUserMenu) {
    /*查询用户所有权限*/
    SysUserEntity sysUserEntity = new SysUserEntity();
    PageInfo<SysUserEntity> pages = PageHelper.startPage(sysUserMenu.getPageNum(),
        sysUserMenu.getPageSize()).
        doSelectPageInfo(() -> sysUserService.queryPage(sysUserEntity));
    for (SysUserEntity sysUserEntitys : pages.getList()) {
      /*查询用户所有权限*/
      Set<String> permissions = shiroService.getUserPermissions(sysUserEntitys.getUserId());
      String join = String.join(",", permissions);
      sysUserEntitys.setPermis(join);
    }
    return R.ok().put("data", pages);
  }

  /**
   * 查看用户所有权限
   *
   * @param sysUserMenu
   * @return
   */
  @RequestMapping("/userPerms")
  public R userPerms(@RequestBody SysUserMenu sysUserMenu) {
    SysUserEntity sysUserEntity = new SysUserEntity();
    SysUserEntity pages = sysUserService.getInfoById(sysUserMenu.getUserId());
    /*查询用户的所有权限*/
    Set<SysUserEntity> permissions = sysUserMenuService.getUserPermissions(sysUserMenu.getUserId());
    sysUserEntity.setPermisList(permissions);
    sysUserEntity.setMobile(pages.getMobile());
    sysUserEntity.setUnencryptedPassword(pages.getUnencryptedPassword());
    return R.ok().put("data", sysUserEntity);
  }

  /**
   * 删除用户
   *
   * @param userId
   * @return
   */
  @RequestMapping("/del")
  public R del(Integer userId) {
    //删除角色
    int i = sysUserService.delUser(userId);
    if (i > 0) {
      return R.ok().put("data", "success");
    } else {
      return R.ok().put("data", "error");
    }
  }


  /**
   * 分配权限
   */
  @RequestMapping("/distribution")
  public R distribution(@RequestBody SysUserMenu sysUserMenu) {
    if (sysUserMenu.getUserId() != null) {
      String salt = RandomStringUtils.randomAlphanumeric(20);
      String passwordSalt = new Sha256Hash(sysUserMenu.getPassword(), salt).toHex();
      int res = sysUserService.updatePassword(passwordSalt, sysUserMenu.getUserId(), sysUserMenu.getPassword(), salt);
      //删除用户的所有权限
      int result = sysUserMenuService.delAll(sysUserMenu.getUserId());
      //将首页添加到导航栏
      BigInteger bigInteger = new BigInteger("20190623093249");
      long menuidsss = 30;
      long menuIds = bigInteger.longValue();
      int resultss = sysUserMenuService.addMenu(menuIds, sysUserMenu.getUserId());
      int resultssss = sysUserMenuService.addMenu(menuIds, sysUserMenu.getUserId());
      for (int i = 0; i < sysUserMenu.getMenuList().size(); i++) {

        Long menuId = Long.valueOf(String.valueOf(sysUserMenu.getMenuList().get(i))).longValue();
        //查询这个菜单的父级菜单
        Long parentId = sysUserMenuService.getParentId(menuId);
        System.out.println(parentId);

        //添加菜单目录到导航栏
        int resultsss = sysUserMenuService.addMenu(menuId, sysUserMenu.getUserId());
        if (parentId == null) {
          System.out.println("是空的");
        } else {
          int results = sysUserMenuService.addMenu(parentId, sysUserMenu.getUserId());
        }
      }
      return R.ok().put("data", "success");
    } else {
      //账号注册
      SysUserEntity user = sysUserService.queryByUserName(sysUserMenu.getMobile());
      if (user == null) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String passwordSalt = new Sha256Hash(sysUserMenu.getPassword(), salt).toHex();
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setPassword(passwordSalt);
        sysUserEntity.setMobile(sysUserMenu.getMobile());
        sysUserEntity.setSalt(salt);
        sysUserEntity.setUnencryptedPassword(sysUserMenu.getPassword());
        if (sysUserService.save(sysUserEntity)) {
          //首页添加到导航栏
          BigInteger b = new BigInteger("20190623093249");
          long menuidsss = 30;
          long menuIds = b.longValue();
          int resultss = sysUserMenuService.addMenu(menuIds, sysUserEntity.getUserId());
          int resultssss = sysUserMenuService.addMenu(menuidsss, sysUserEntity.getUserId());
          for (int i = 0; i < sysUserMenu.getMenuList().size(); i++) {
            Long menuId = Long.valueOf(String.valueOf(sysUserMenu.getMenuList().get(i))).longValue();
            /**
             * 查询这个菜单的父级菜单
             */
            Long parentId = sysUserMenuService.getParentId(menuId);
            sysUserMenu.setMenuId(menuId);
            /**
             * 添加菜单目录至导航栏
             */
            int result = sysUserMenuService.addMenu(menuId, sysUserEntity.getUserId());
            int results = sysUserMenuService.addMenus(parentId, sysUserEntity.getUserId());
            List<SysUserMenu> sysUserMenus = sysUserMenuService.getList(sysUserMenu);
          }
          return R.ok().put("data", "success");
        } else {
          return R.ok().put("data", "error");
        }
      } else {
        return R.ok().put("data", "该账号已被注册");
      }
    }
  }






}









