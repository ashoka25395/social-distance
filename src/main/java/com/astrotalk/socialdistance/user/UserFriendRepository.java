package com.astrotalk.socialdistance.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.astrotalk.socialdistance.entity.UserFriends;

/**
 * @author Ashok
 *
 */
@Repository
public interface UserFriendRepository extends PagingAndSortingRepository<UserFriends, Integer> {

	UserFriends findByUserIdAndFriendId(int userId, int friendId);

	@Transactional
	@Modifying
	void deleteByUserIdAndFriendId(int userId, int friendId);

	List<UserFriends> findByUserId(int userId);

}
