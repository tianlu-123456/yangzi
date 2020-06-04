package com.ltl.yangzi.common.persistence;

import java.util.List;

/**
 * 持久化层基接口
 * @param <T>
 * @author shengsen
 * @date 2018-11-28
 */
public interface BaseDao<T> {
  /**
   * 根据id取出一条记录
   * @param id
   * @return
   */
  T getOne(Integer id);

  /**
   * 根据条件取出一条记录
   * @param t
   * @return
   */
  T getOne(T t);

  /**
   * 根据条件筛选批量数据
   * @param t 实体类
   * @return
   */
  List<T> getList(T t);

  /**
   * 插入一条记录
   * @param t 实体类
   * @return
   */
  Integer insert(T t);

  /**
   * 根据id逻辑删除一条记录
   * @param id
   * @return
   */
  Integer delete(Integer id);

  /**
   * 更新一条记录
   * @param t 实体类
   * @return
   */
  Integer update(T t);
}

