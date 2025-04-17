package service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Controller.dto.RegisterRequest;
import entity.RoleEntity;
import entity.User;
import entity.UserRole;
import jakarta.transaction.Transactional;
import repository.RoleRepository;
import repository.UserRepository;
import repository.UserRoleRepository;

@Service
public class UserService {

	@Autowired
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	UserRoleRepository userRoleRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void registerUser(RegisterRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user = userRepository.save(user);
		Set<RoleEntity> roles = new HashSet<>();
		for (String roleName : request.getRoles()) {
			RoleEntity role = roleRepo.findByName(roleName)
					.orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

			roles.add(role);
		}

		for (RoleEntity role : roles) {
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			userRoleRepository.save(userRole);
		}

		User updatedUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		updatedUser.getRoles().forEach(role -> System.out.println("User has role: " + role.getName()));
	}

}