package org.haxwell.dtim.techprofile.enums;

import java.util.HashSet;
import java.util.Set;

public enum Role {
 
	ADMIN,
	CANDIDATE,
	CLIENT;

    private static final Set<String> roles;

    static {
        roles = new HashSet<>(Role.values().length);
        for (Role r : Role.values()) {
            roles.add(r.name());
        }
    }

    public static Set<String> getRoles() {
        return roles;
    }

}
