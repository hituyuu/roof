package org.roof.web.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.roof.web.role.dao.api.IRoleDao;
import org.roof.web.role.entity.BaseRole;
import org.roof.web.role.entity.Role;
import org.roof.web.user.dao.api.IUserDao;
import org.roof.web.user.entity.User;
import org.roof.web.user.service.api.AliasUsernameLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author liuxin
 * @since 2018/8/25
 */
public class DefaultUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserDetailsService.class);

    private IUserDao userDao;
    private IRoleDao roleDao;
    private AliasUsernameLoader aliasUsernameLoader;
    private Collection<BaseRole> roles;

    private Map<String, String> subUserClasses;
    private Long[] defaultRolesId;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username, "username must not be null");
        username = selectUsernameByAlias(username);
        User user;
        try {
            user = userDao.loadUserByUsername(username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new UsernameNotFoundException("load user database error", e);
        }
        if (user == null) {
            LOGGER.debug("username [{}] not found", username);
            throw new UsernameNotFoundException("username [" + username + "] not found");
        }
        addRoles(user);
        addDefaultRoles(user);

        String dtype = user.getDtype();
        String subUserClassFullName = clear(subUserClasses.get(dtype));
        if (StringUtils.isEmpty(subUserClassFullName)) {
            return user;
        }
        Class<?> subUserClass;
        try {
            subUserClass = Class.forName(subUserClassFullName);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            return user;
        }
        User subUser = (User) userDao.load(subUserClass, user.getId());
        if (subUser != null) {
            BeanUtils.copyProperties(user, subUser);
        }
        return subUser;
    }

    private String clear(String s) {
        return StringUtils.replaceEachRepeatedly(s, new String[]{" ", "\n", "\t"},
                new String[]{"", "", ""});
    }

    private String selectUsernameByAlias(String alias) {
        if (aliasUsernameLoader == null) {
            return alias;
        }
        String username = aliasUsernameLoader.load(alias);
        if (StringUtils.isEmpty(username)) {
            return alias;
        }
        return username;
    }

    private void addRoles(User user) {
        Set<?> baseRoles = roleDao.selectByUser(user.getId());
        user.setRoles((Set<BaseRole>) baseRoles);
    }

    public void addDefaultRoles(User user) {
        loadDefaultRoles();
        @SuppressWarnings("unchecked")
        Collection<BaseRole> authorities = (Collection<BaseRole>) user.getAuthorities();
        for (BaseRole grantedAuthority : roles) {
            if (!exists(authorities, grantedAuthority.getId())) {
                authorities.add(grantedAuthority);
            }
        }
    }

    private boolean exists(Collection<BaseRole> authorities, Long roleId) {
        for (BaseRole authority : authorities) {
            if (authority.getId().longValue() == roleId.longValue()) {
                return true;
            }
        }
        return false;
    }

    private void loadDefaultRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
            for (Long roleId : defaultRolesId) {
                roles.add(roleDao.load(Role.class, roleId));
            }
        }

    }

    public void setSubUserClasses(Map<String, String> subUserClasses) {
        this.subUserClasses = subUserClasses;
    }

    public void setDefaultRolesId(Long[] defaultRolesId) {
        this.defaultRolesId = defaultRolesId;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setAliasUsernameLoader(AliasUsernameLoader aliasUsernameLoader) {
        this.aliasUsernameLoader = aliasUsernameLoader;
    }
}
