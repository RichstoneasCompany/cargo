package com.richstone.cargo.model.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Permission {


    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    DISPATCHER_READ("dispatcher:read"),
    DISPATCHER_UPDATE("dispatcher:update"),
    DISPATCHER_CREATE("dispatcher:create"),
    DISPATCHER_DELETE("dispatcher:delete"),
    DRIVER_READ("driver:read"),
    DRIVER_UPDATE("driver:update"),
    DRIVER_CREATE("driver:create"),
    DRIVER_DELETE("driver:delete"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_DELETE("customer:delete");


    private final String permission;
}