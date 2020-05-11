package com.tech.mkblogs.security.hardcoderole;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import static com.tech.mkblogs.security.hardcoderole.AccountUserPermission.ACCOUNT_READ;
import static com.tech.mkblogs.security.hardcoderole.AccountUserPermission.ACCOUNT_WRITE;
import static com.tech.mkblogs.security.hardcoderole.AccountUserPermission.ADMIN_READ;
import static com.tech.mkblogs.security.hardcoderole.AccountUserPermission.ADMIN_WRITE;


public enum AccountUserRole {
    USER(Sets.newHashSet(ACCOUNT_READ,ACCOUNT_WRITE)),
    USER_READ_ONLY(Sets.newHashSet(ACCOUNT_READ)),
    ADMIN(Sets.newHashSet(ADMIN_READ,ADMIN_WRITE)),
    ADMIN_READ_ONLY(Sets.newHashSet(ADMIN_READ));

    private final Set<AccountUserPermission> permissions;

    AccountUserRole(Set<AccountUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AccountUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
