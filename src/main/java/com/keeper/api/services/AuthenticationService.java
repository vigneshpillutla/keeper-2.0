package com.keeper.api.services;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.keeper.api.dao.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.keeper.api.dto.CredentialsDto;
import com.keeper.api.dto.UserDto;
import com.keeper.api.entities.User;

import lombok.Setter;

@Service
@Setter
public class AuthenticationService {
	

	private BCryptPasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	
	@Value("${session.secretKey}")
	private String secretKey;
	
	public AuthenticationService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}
	
	public String createToken(String username) {
		return username + "&" + calculateHmac(username);
	}
	
	public UserDto findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		
		if(user==null)throw new UsernameNotFoundException("Username not found");
		
		return new UserDto(user.getId(),user.getUsername());
	}
	
	public UserDto findByToken(String token) {
		String[]  parts = token.split("&");
		String username = parts[0],hmac = parts[1];
		
		UserDto userDto = findByUsername(username);
		
		if(!hmac.equals(calculateHmac(username))) {
			throw new BadCredentialsException("Invalid cookie");
		}
		return userDto;
	}
	
	
	public UserDto authenticate(CredentialsDto credentialsDto) {
		User user = userRepository.findByUsername(credentialsDto.getUsername());
		if(user==null) throw new BadCredentialsException("Invalid username");
		
		if(!passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}
		
		return new UserDto(user.getId(),user.getUsername());
	}
	
	private String calculateHmac(String username) {
		byte[] secretKeyValue = secretKey.getBytes(StandardCharsets.UTF_8);
		byte[] userValue = username.getBytes(StandardCharsets.UTF_8);
		
		try {
			Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyValue, "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(userValue);
            return Base64.getEncoder().encodeToString(hmacBytes);

		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
}
