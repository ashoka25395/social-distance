package com.astrotalk.socialdistance.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astrotalk.socialdistance.entity.User;



/**
 * @author Ashok
 *
 */
@RestController
public class UserController {

	
	
	/**
	 * injected user service
	 */
	@Autowired
	private UserService userService;

	/**
	 * @param user user
	 * @return user
	 */
	@PostMapping("/createProfile")
	public User createProfile(@Valid @RequestBody User user) {
		return userService.createProfile(user);
	}

	/**
	 * @param userId userId
	 * @param friendId friendId
	 * @return string
	 */
	@GetMapping("/addUserFriend")
	public String addUserFriend(@RequestParam int userId, @RequestParam int friendId) {
		return userService.addUserFriend(userId, friendId);
	}

	/**
	 * @param userId userId
	 * @param friendId friendId
	 * @return string
	 */
	@GetMapping("/remvoeUserFriend")
	public String remvoeUserFriend(@RequestParam int userId, @RequestParam int friendId) {
		return userService.remvoeUserFriend(userId, friendId);
	}

	/**
	 * @param userId userId
	 * @return userList
	 */
	@GetMapping("/viewFriendList")
	public List<User> viewFriendList(@RequestParam int userId) {
		return userService.viewFriendList(userId);
	}

	/**
	 * @param userId userId
	 * @param k k
	 * @return friendList at distance k
	 */
	@GetMapping("/friendsAtDistanceK")
	public List<User> friendsAtDistanceK(@RequestParam int userId,@RequestParam int k) {
		return userService.friendsAtDistanceK(userId,k);
	}

}
