package com.ltl.yangzi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.yangzi.common.utils.PageUtils;
import com.ltl.yangzi.common.utils.Query;
import com.ltl.yangzi.dao.SysLogDao;
import com.ltl.yangzi.entity.SysLogEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> {
  @Resource
  private SysLogDao sysLogDao;


  public PageUtils queryPage(Map<String, Object> params) {
    String key = (String)params.get("username");

    IPage<SysLogEntity> page = this.page(
        new Query<SysLogEntity>(params).getPage(),
        new QueryWrapper<SysLogEntity>().
            like(StringUtils.isNoneBlank(key),"username",key).
            orderByDesc("create_date")
    );

    return new PageUtils(page);
  }
}
