package com.ltl.yangzi.controller.system;

import com.ltl.yangzi.common.R;
import com.ltl.yangzi.common.annotion.SysLog;
import com.ltl.yangzi.common.exception.RRException;
import com.ltl.yangzi.common.utils.Constant;
import com.ltl.yangzi.entity.SysMenuEntity;
import com.ltl.yangzi.entity.SysUserEntity;
import com.ltl.yangzi.service.ShiroServiceImpl;
import com.ltl.yangzi.service.SysMenuServiceImpl;
import com.ltl.yangzi.service.SysUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
  @Autowired
  private SysUserServiceImpl sysUserService;
  @Autowired
  private SysMenuServiceImpl sysMenuService;
  @Autowired
  private ShiroServiceImpl shiroService;

  /**
   * 导航菜单
   */
  @GetMapping("/nav")
  public R nav (@RequestParam("username") String username){
    //通过用户名查询用户信息
    SysUserEntity user = sysUserService.queryByUserName(username);
    int del = -1;
    int del2 = -1;
    ArrayList<Integer> list = new ArrayList<Integer>();


    //获取用户菜单列表
    List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(1);

    //获取用户权限列表
    Set<String> permissions = shiroService.getUserPermissions(1);

    if (username.equals("admin") || username.equals("13813818148")){

      if (username.equals("13813818148")) {
        A:
        for (int i = 0; i < menuList.size(); i++) {
          for (int i1 = 0; i1 < menuList.get(i).getList().size(); i1++) {
            SysMenuEntity s = (SysMenuEntity) menuList.get(i).getList().get(i1);
            if (s.getName().equals("系统日志") || s.getName().equals("后台菜单")) {

              if (del == -1) {
                del = i1;
              } else {
                del2 = i1;
              }
            }
          }
          menuList.get(i).getList().remove(del);
          menuList.get(i).getList().remove(del2 - 1);
          break A;
        }
      }
    }else{
      permissions.remove("10");
            /*for (SysMenuEntity sysMenuEntity : menuList) {
                List<SysMenuEntity> list = (List<SysMenuEntity>) sysMenuEntity.getList();
                for (SysMenuEntity menuEntity : list) {
                    if (menuEntity.getName().equals("管理员账号管理")) {
                        entity = sysMenuEntity;
                        break;
                    }
                }
                sysMenuEntity.getList().remove(entity);
            }*/

      A:
      for (int i = 0; i < menuList.size(); i++) {
        for (int i1 = 0; i1 < menuList.get(i).getList().size(); i1++) {
          SysMenuEntity s = (SysMenuEntity) menuList.get(i).getList().get(i1);
          if (s.getName().equals("管理员账号管理") || s.getName().equals("系统日志") || s.getName().equals("后台菜单")) {
            list.add(i1);
          }
        }
        break A;
      }

      for (int i = list.size() - 1; i >= 0; i--) {
        int d = (int)list.get(i);
        menuList.get(0).getList().remove(d);
      }

           /* if (del == -1 || del2 == -1) {

            } else {
                menuList.get(del).getList().remove(del2);
            }*/
    }

    return R.ok().put("menuList",menuList).put("permssions",permissions);
  }

  /**
   * 所有菜单列表
   */
  @GetMapping("/listall")
  public R listall(){
    List<SysMenuEntity> menuList = sysMenuService.selectlist();
    for (SysMenuEntity sysMenuEntity : menuList) {
      SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
      if (parentMenuEntity != null){
        sysMenuEntity.setParentName(parentMenuEntity.getName());
      } else {
        sysMenuEntity.setParentName("一级菜单");
      }
    }
    return R.ok().put("data",menuList);
  }

  /**
   * 所有菜单列表
   */
  @GetMapping("/listallByUserId")
  @RequiresPermissions("sys:menu:list")
  public R listallByUserId(){
    List<SysMenuEntity> menuList = sysMenuService.selectlist();
    for (SysMenuEntity sysMenuEntity : menuList) {
      SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
      if (parentMenuEntity != null){
        sysMenuEntity.setParentName(parentMenuEntity.getName());
      } else {
        sysMenuEntity.setParentName("一级菜单");
      }
    }
    return R.ok().put("data",menuList);
  }

  /**
   * 获取不包含按钮的菜单列表
   */
  @GetMapping("/select")
  public R select(){
    //查询 不包含按钮的菜单列表 数据
    List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();
    //添加顶级菜单
    SysMenuEntity root = new SysMenuEntity();
    root.setMenuId(0L);
    root.setName("一级菜单");
    root.setParentId(-1L);
    root.setOpen(true);
    menuList.add(root);
    return R.ok().put("menuList",menuList);
  }

  /**
   * 所有菜单集合
   */
  @RequestMapping("/getShiro")
  public R getShiro(){
    List<SysMenuEntity> sysMenuEntities = sysMenuService.queryNotButtonList();
    return R.ok().put("data",sysMenuEntities);
  }

  /**
   * 菜单信息
   * @param id
   * @return
   */
  @GetMapping("/info")
  public R info(Long id){
    //根据id查询menu
    SysMenuEntity menu = sysMenuService.getById(id);
    /*查询对应的父级menu*/
    SysMenuEntity parentMenuEntity = sysMenuService.getById(menu.getParentId());
    if (parentMenuEntity !=null){
      menu.setParentName(parentMenuEntity.getName());
    }
    return R.ok().put("menu",menu);
  }

  /**
   * 保存菜单
   * @param menu
   * @return
   */
  @SysLog("保存菜单")
  @PostMapping("/save")
  public R save(@RequestBody SysMenuEntity menu){
    verifyForm(menu);
    long time = System.currentTimeMillis();
    Date date = new Date(time);
    String ma = "yyyyMMddhhmmss";
    SimpleDateFormat format = new SimpleDateFormat(ma);
    String nwdate = format.format(date);
    menu.setMenuId(Long.parseLong(nwdate));
    sysMenuService.save(menu);
    return R.ok();
  }

  /**
   * 菜单修改
   * @param menu
   * @return
   */
  @SysLog("修改菜单")
  @PostMapping("/update")
  public R update (@RequestBody SysMenuEntity menu){
    //数据校验
    verifyForm(menu);
    sysMenuService.updateById(menu);
    return R.ok();
  }

  /**
   * 菜单删除
   * @param id
   * @return
   */
  @SysLog("删除菜单")
  @GetMapping("/delete")
  @RequiresPermissions("sys:menu:delete")
  public R delete(Long id){
    if (id <= 31){
      return R.error("系统菜单,不能删除");
    }
    //判断是否有子菜单或按钮
    List<SysMenuEntity> menuList = sysMenuService.queryListParentId(id);
    if (menuList.size() > 0){
      return R.error("请先删除子菜单或按钮");
    }
    sysMenuService.delete(id);
    return R.ok();
  }









  /*验证参数是否正确的方法*/
  private void verifyForm(SysMenuEntity menu){
    if (StringUtils.isBlank(menu.getName()))
      throw new RRException("菜单名不能为空");
    if(menu.getParentId()==null)
      throw new RRException("上级菜单不能为空");

    //菜单
    if(menu.getType() == Constant.MenuType.MENU.getValue()){
      if (StringUtils.isBlank(menu.getUrl())){
        throw new RRException("菜单URL不能为空");
      }
    }

    //上级菜单类型
    int parentType = Constant.MenuType.CATALOG.getValue();
    if (menu.getParentId() !=0 ){
      SysMenuEntity parentMenu = sysMenuService.getById(menu.getMenuId());
      parentType = parentMenu.getType();
    }

    //目录菜单
    if (menu.getType() == Constant.MenuType.MENU.getValue()){
      if (menu.getParentId() == 0) {
        throw new RRException("上级菜单不能为空");
      }
      if (parentType != Constant.MenuType.CATALOG.getValue()){
        throw new RRException("上级菜单只能为目录类型");
      }
      return;
    }
    if (menu.getType() == Constant.MenuType.CATALOG.getValue()){
      long time = System.currentTimeMillis();
      Date date = new Date(time);
      String ma = "yyyyMMddhhmmss";
      SimpleDateFormat format = new SimpleDateFormat(ma);
      String nwdate = format.format(date);
      return;
    }

    //按钮
    if (menu.getType() == Constant.MenuType.BUTTON.getValue()){
      if (parentType != Constant.MenuType.MENU.getValue()){
        throw new RRException("上级菜单只能为菜单类型");
      }
      return;
    }
  }
}
