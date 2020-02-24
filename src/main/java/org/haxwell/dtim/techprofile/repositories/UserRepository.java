package org.haxwell.dtim.techprofile.repositories;

import java.util.Optional;

import org.haxwell.dtim.techprofile.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query(nativeQuery = true, value = "select u.* from user u where u.name=?1 and (u.phone=?2 or u.email=?3)")
	Optional<User> findByNamePhoneOrEmail(String name, String phone, String email);
	
	Optional<User> findByName(String name);
	Optional<User> findByNameAndPassword(String name, String password);
	
	@Query(nativeQuery = true, value = "select u.* from user u where u.phone=?1 or u.email=?1 LIMIT 1")
	Optional<User> findByPhoneOrEmail(String query);
}
