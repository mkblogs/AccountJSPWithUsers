package com.tech.mkblogs.security.hardcoderole;

public enum AccountUserPermission {
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");

    private final String permission;

    AccountUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
