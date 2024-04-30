package com.richstone.cargo.model.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.richstone.cargo.model.types.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {


    ADMIN(
            Set.of(

                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    DISPATCHER_READ,
                    DISPATCHER_UPDATE,
                    DISPATCHER_DELETE,
                    DISPATCHER_CREATE
            )
    ),
    DRIVER(
            Set.of(
                    DRIVER_READ,
                    DRIVER_UPDATE,
                    DRIVER_DELETE,
                    DRIVER_CREATE
            )
    ),
    CUSTOMER(
            Set.of(
                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE,
                    CUSTOMER_CREATE
            )
    ),
    DISPATCHER(
            Set.of(

                    DISPATCHER_READ,
                    DISPATCHER_UPDATE,
                    DISPATCHER_DELETE,
                    DISPATCHER_CREATE
            )
    );


    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}