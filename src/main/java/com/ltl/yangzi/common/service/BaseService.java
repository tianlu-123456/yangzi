package com.ltl.yangzi.common.service;

import com.ltl.yangzi.common.entity.BaseEntity;
import com.ltl.yangzi.common.persistence.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 业务逻辑层基类
 *
 * @param <T>
 * @author shengsen
 * @date 2018-11-28
 */
public class BaseService<D extends BaseDao<T>, T extends BaseEntity> {
  @Autowired
  private D dao;

  public T getOne(Integer id) {
    return dao.getOne(id);
  }

  public T getOne(T t) {
    return dao.getOne(t);
  }

  public List<T> getList(T t) {
    return dao.getList(t);
  }

  public Integer insert(T t) {
    return dao.insert(t);
  }

  public Integer update(T t) {
    return dao.update(t);
  }

  public Integer delete(Integer id) {
    return dao.delete(id);
  }
}

