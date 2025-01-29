package com.mkv.devcatalog.domain.role;

public record RoleDTO(Long id, String authority) {

    public RoleDTO(Role role) {
        this(role.getId(), role.getAuthority());
    }
}
