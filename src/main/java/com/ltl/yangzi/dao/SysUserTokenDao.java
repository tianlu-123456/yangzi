package com.ltl.yangzi.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltl.yangzi.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    /*根据token查询信息*/
    SysUserTokenEntity queryByToken(String token);

}
