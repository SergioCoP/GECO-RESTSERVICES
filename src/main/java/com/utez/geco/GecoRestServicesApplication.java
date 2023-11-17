package com.utez.geco;

import com.utez.geco.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class GecoRestServicesApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(GecoRestServicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
//public static void main(String[] args) {
//	String secretKey = generateSecretKey();
//	System.out.println("Generated Secret Key: " + secretKey);
//}
//
//	public static String generateSecretKey() {
//		SecureRandom secureRandom = new SecureRandom();
//		byte[] key = new byte[32];
//		secureRandom.nextBytes(key);
//
//		return Base64.getEncoder().encodeToString(key);
//	}
}
