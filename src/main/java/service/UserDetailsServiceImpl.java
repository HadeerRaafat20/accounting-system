package service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import entity.User;
import repository.UserRepository;

@Primary
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository repository;

	public UserDetailsServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		try {
			return repository.findByUsername(userName).map(user -> {
				Hibernate.initialize(user.getUserRoles());
				List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
						.map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName()))
						.collect(Collectors.toList());

				return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
						authorities);
			}).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
		} catch (Exception e) {
			System.err.println("Failed login for username: " + userName);
			throw e;
		}
	}
}