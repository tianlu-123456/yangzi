package com.ltl.yangzi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltl.yangzi.common.R;
import com.ltl.yangzi.common.TokenGenerator;
import com.ltl.yangzi.dao.SysUserTokenDao;
import com.ltl.yangzi.entity.SysUserTokenEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> {
  //2小时后过期
  private final static int EXPIRE = 3600 *2;

  /**
   * 根据userId创建token
   * @param userId
   * @return
   */
  public R createToken(long userId) {
    //生成一个token
    String token = TokenGenerator.generateValue();
    //当前时间
    Date now = new Date();

    //过期时间
    Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

    //判断是否生成过token

    //根据userId查询tokenEntity
    SysUserTokenEntity tokenEntity = this.getById(userId);

    if (tokenEntity == null){
      tokenEntity = new SysUserTokenEntity();
      tokenEntity.setUserId(userId);
      tokenEntity.setToken(token);
      tokenEntity.setUpdateTime(now);
      tokenEntity.setExpireTime(expireTime);

      //保存token
      this.save(tokenEntity);
    }else {
      tokenEntity.setToken(token);
      tokenEntity.setUpdateTime(now);
      tokenEntity.setExpireTime(expireTime);

      //更新token
      this.updateById(tokenEntity);
    }

    //返回token
    R r = R.ok().put("token", token).put("expire", EXPIRE);

    return r;
  }

  public void logout(long userId) {
    //生成一个token

    //修改token
  }
}

