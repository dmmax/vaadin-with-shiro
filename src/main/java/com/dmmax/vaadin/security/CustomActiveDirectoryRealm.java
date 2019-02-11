package com.dmmax.vaadin.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.activedirectory.ActiveDirectoryRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.StringUtils;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomActiveDirectoryRealm extends ActiveDirectoryRealm {

    private String personSearchFilter;

    @Override
    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, LdapContextFactory ldapContextFactory) throws NamingException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        try {
            String userDn = getUserDn(upToken.getUsername(), ldapContextFactory);
            if (userDn != null) {
                upToken.setUsername(userDn);
            }

            return super.buildAuthenticationInfo(userDn, upToken.getPassword());
        } catch (IncorrectCredentialsException e) {
            return null;
        }
    }

    private String getUserDn(String username, LdapContextFactory ldapContextFactory) throws NamingException {

        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username should be with text!");
        }

        LdapContext ctx = ldapContextFactory.getSystemLdapContext();
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setCountLimit(1);

        String searchFilter = this.personSearchFilter == null ? "(&(objectClass=person)(cn={0}))" : personSearchFilter;

        NamingEnumeration<SearchResult> search = ctx.search(searchBase, searchFilter, new String[]{username}, controls);
        if (search.hasMore()) {
            SearchResult result = search.next();

            return result.getNameInNamespace();
        } else {
            throw new IncorrectCredentialsException();
        }
    }

    @Override
    protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals, LdapContextFactory ldapContextFactory) {
        String userDn = (String) getAvailablePrincipal(principals);
        return buildAuthorizationInfo(getRolesByUserDn(userDn));
    }

    private Set<String> getRolesByUserDn(String userDn) {
        String dn = userDn;
        if (userDn.contains(",")) {
            dn = userDn.substring(userDn.indexOf(",") + 1);
        }
        return new HashSet<>(getRoleNamesForGroups(Collections.singleton(dn)));
    }

    public void setPersonSearchFilter(String personSearchFilter) {
        this.personSearchFilter = personSearchFilter;
    }
}
