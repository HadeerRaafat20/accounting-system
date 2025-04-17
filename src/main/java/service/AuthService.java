package service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import repository.UserRepository;

@Service
public class AuthService {
	@Autowired
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private static final long EXPIRATION_TIME = 864_000_00; // 1 day in milliseconds

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public String generateJwtToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, generateSecretKey()).compact();
	}

	public static String generateSecretKey() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[32];
		secureRandom.nextBytes(key);
		return Base64.getEncoder().encodeToString(key);
	}

	public boolean authenticateUser(String username, String rawPassword) {
		Optional<User> user = userRepository.findByUsername(username);

		if (user.isPresent()) {
			return passwordEncoder.matches(rawPassword, user.get().getPassword());
		}
		return false;
	}
}