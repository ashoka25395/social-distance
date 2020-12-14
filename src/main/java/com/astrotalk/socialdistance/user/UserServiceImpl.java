package com.astrotalk.socialdistance.user;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astrotalk.socialdistance.common.CustomException;
import com.astrotalk.socialdistance.entity.User;
import com.astrotalk.socialdistance.entity.UserFriends;

/**
 * @author Ashok
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserFriendRepository userFriendRepository;

	@Override
	public User createProfile(@Valid User user) {
                System.out.println("in user service impl");
		return userRepository.save(user);
	}

	@Override
	public String addUserFriend(int userId, int friendId) {
		UserFriends userFriends = userFriendRepository.findByUserIdAndFriendId(userId, friendId);
		if (userFriends != null) {
			throw new CustomException("Friend already exist!!!");
		}

		else {
			Optional<User> user = userRepository.findById(userId);
			Optional<User> friend = userRepository.findById(friendId);
			if (user.isPresent() && friend.isPresent()) {
				userFriendRepository.save(new UserFriends(userId, friendId));
				userFriendRepository.save(new UserFriends(friendId, userId));
			} else {
				throw new CustomException("Either user or friend is not present..Please create profile first !!!");
			}
		}

		return "Friend is added successfully!!";
	}

	@Override
	public String remvoeUserFriend(int userId, int friendId) {
		Optional<User> user = userRepository.findById(userId);
		Optional<User> friend = userRepository.findById(friendId);
		if (user.isPresent() && friend.isPresent()) {
			userFriendRepository.deleteByUserIdAndFriendId(userId, friendId);
			userFriendRepository.deleteByUserIdAndFriendId(friendId, userId);
		} else {
			throw new CustomException("Either user or friend doesn't exist... !!!");

		}
		return "Friend is removed successfully!!!";
	}

	@Override
	public List<User> viewFriendList(int userId) {
		List<UserFriends> userFriendsList = this.userFriendRepository.findByUserId(userId);
		Set<Integer> friendList = userFriendsList.stream().map(obj -> obj.getFriendId()).collect(Collectors.toSet());
		return userRepository.findByIdIn(friendList);
	}

	@Override
	public List<User> friendsAtDistanceK(int userId, int k) {
		if (k == 1) {
			return userRepository.findByIdIn(getOneDistanceFriendsForGivenUserId(userId));
		} else {
			return doBreadthFirstSearchOnUserList(userId, k);
		}
	}

	private Set<Integer> getOneDistanceFriendsForGivenUserId(int userId) {
		List<UserFriends> oneDistanceFriends = this.userFriendRepository.findByUserId(userId);
		return oneDistanceFriends.stream().map(obj -> obj.getFriendId()).collect(Collectors.toSet());
	}

	private List<User> doBreadthFirstSearchOnUserList(int userId, int k) {
		int[] distance = new int[10000000];
		distance[userId] = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(userId);
		boolean[] visited = new boolean[10000000];
		visited[userId] = true;
		Set<Integer> kDistanceFriendIds = new HashSet<>();
		while (!queue.isEmpty()) {
			int parent = queue.peek();
			queue.poll();
			for (int child : getOneDistanceFriendsForGivenUserId(parent)) {
				if (child == parent || visited[child] == true)
					continue;
				distance[child] = distance[parent] + 1;
				visited[parent] = true;
				if (distance[child] == k) {
					kDistanceFriendIds.add(child);
				}
				queue.add(child);
			}
		}
		return userRepository.findByIdIn(kDistanceFriendIds);

	}

}
