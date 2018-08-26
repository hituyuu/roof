package org.roof.web.user.service.api;

/**
 * 根据用户别名查询用户名
 *
 * @author liuxin
 * @since 2018/8/25
 */
public interface AliasUsernameLoader {
    /**
     * 根据用户别名查询用户名
     *
     * @param alias 用户别名
     * @return 用户名
     */
    String load(String alias);
}
