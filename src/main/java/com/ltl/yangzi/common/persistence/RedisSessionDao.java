package com.ltl.yangzi.common.persistence;

import com.ltl.yangzi.common.utils.RedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * shiro的Redis缓存
 */
public class RedisSessionDao extends AbstractSessionDAO {
  private final String SHIRO_SESSION_PREFIX = "shiro_session_";
  @Resource
  private RedisUtils redisUtil;

  private String getKey(String key) {
    return SHIRO_SESSION_PREFIX + key;
  }

  @Override
  protected Serializable doCreate(Session session) {
    Serializable sessionId = generateSessionId(session);
    assignSessionId(session, sessionId);
    saveSession(session);
    return sessionId;
  }

  @Override
  protected Session doReadSession(Serializable sessionId) {
    if (sessionId == null) {
      return null;
    }
    Object val = redisUtil.get(getKey(sessionId.toString()));
    if (val != null) {
      SimpleSession session = (SimpleSession) val;
      return session;
    }
    return null;
  }

  @Override
  public void update(Session session) throws UnknownSessionException {
    saveSession(session);
  }

  @Override
  public void delete(Session session) {
    Optional.of(session).filter(se -> se.getId() != null).ifPresent(se -> redisUtil.delete(getKey(se.getId().toString())));
  }

  @Override
  public Collection<Session> getActiveSessions() {
    Set<Session> sessionSet = new HashSet<>();
    Set<String> keys = redisUtil.keysByPattern(SHIRO_SESSION_PREFIX + "*");
    if (!CollectionUtils.isEmpty(keys)) {
      keys.forEach(key -> {
        Session session = (Session) redisUtil.get(key);
        sessionSet.add(session);
      });
    }
    return sessionSet;
  }

  /**
   * 把session存入Redis，提取doCreate、update方法
   *
   * @param session
   */
  public void saveSession(Session session) {
    Optional.of(session).filter(se -> se.getId() != null).ifPresent(se -> {
      String key = getKey(se.getId().toString());
      redisUtil.set(key, se);
      redisUtil.expire(key, 60*60, TimeUnit.SECONDS);
    });
  }
}
