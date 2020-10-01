package com.astrotalk.socialdistance.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.astrotalk.socialdistance.entity.User;

/**
 * @author Ashok
 *
 */
@Service
public interface UserService {

	User createProfile(User user);

	String addUserFriend(int userId, int friendId);

	String remvoeUserFriend(int userId, int friendId);

	List<User> viewFriendList(int userId);

	List<User> friendsAtDistanceK(int userId, int k);

}
