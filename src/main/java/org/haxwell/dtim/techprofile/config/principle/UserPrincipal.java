package org.haxwell.dtim.techprofile.config.principle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.haxwell.dtim.techprofile.entities.User;
import org.haxwell.dtim.techprofile.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 12303434L;
	
	private User user;
	
	public UserPrincipal(User user) {
		this.user = user;
	}
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Set<UserRole> roles = user != null ? user.getRoles() : null;
        final List<GrantedAuthority> authorities = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roles)) {
            roles.forEach(role -> {
                if (role != null) {
                	System.out.println("User has role [" + role.getName() + "]");
                	authorities.add(new SimpleGrantedAuthority(role.getName()));
                }
            });
        }
        return authorities;
    }
    
    public User getUser() {
    	return this.user;
    }

    public Long getId() {
    	return user != null ? user.getId() : null;
    }
    
    @Override
    public String getPassword() {
    	return user != null ? user.getPassword() : null;
    }

    @Override
    public String getUsername() {
        return user != null ? user.getName() : null;
    }

	@Override
	public boolean isAccountNonExpired() {
		return isEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isEnabled();
	}

	@Override
	public boolean isEnabled() {
		return user != null ? user.getEnabled() == 1 : false;
	}
}
