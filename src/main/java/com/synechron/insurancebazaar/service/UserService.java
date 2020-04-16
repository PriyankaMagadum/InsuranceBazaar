package com.synechron.insurancebazaar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.insurancebazaar.modal.User;
import com.synechron.insurancebazaar.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserById(long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}
	
	public User SaveUser(User user) {
		return userRepository.saveAndFlush(user);
	}

}
