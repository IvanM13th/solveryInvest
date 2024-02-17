package com.example.solveryInvest.entity.enums;

import org.jooq.impl.EnumConverter;

public class RoleConverter extends EnumConverter<String, Role> {

    public RoleConverter(Class<String> fromType, Class<Role> toType) {
        super(fromType, toType);
    }
}
