package org.haxwell.dtim.techprofile.services;

import java.util.Optional;

import org.haxwell.dtim.techprofile.config.principle.UserPrincipal;
import org.haxwell.dtim.techprofile.entities.User;
import org.haxwell.dtim.techprofile.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceDTIM implements UserDetailsService   {

	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opt = userRepo.findByName(username);
		User rtn = null;

		if (opt.isPresent())
			rtn = opt.get();
		else
			throw new UsernameNotFoundException(username);

		return new UserPrincipal(rtn);
	}
}
