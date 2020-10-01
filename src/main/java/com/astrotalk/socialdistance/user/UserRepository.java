package com.astrotalk.socialdistance.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.astrotalk.socialdistance.entity.User;

/**
 * @author Ashok
 *
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	List<User> findByIdIn(Set<Integer> friendList);

}
