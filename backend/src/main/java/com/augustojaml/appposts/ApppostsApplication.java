package com.augustojaml.appposts;

import java.util.Arrays;

import com.augustojaml.appposts.models.RoleModel;
import com.augustojaml.appposts.models.UserModel;
import com.augustojaml.appposts.repositories.RolesRepository;
import com.augustojaml.appposts.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
public class ApppostsApplication implements CommandLineRunner {

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	UsersRepository UsersRepository;

	@Autowired
	BCryptPasswordEncoder bEncoder;

	@Autowired
	MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ApppostsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (!mongoTemplate.collectionExists("roles") || !mongoTemplate.collectionExists("users")) {

			RoleModel roleADMIN = new RoleModel("62193aac2409c8672769e5ac", "ROLE_ADMIN");
			RoleModel roleUSER = new RoleModel("62193ab52409c8672769e5ad", "ROLE_USER");
			rolesRepository.saveAll(Arrays.asList(roleADMIN, roleUSER));

			UserModel userADMIN = new UserModel("6219439352ef1c750d184ba4", "Admin", "admin@email.com",
					bEncoder.encode("1234"),
					null,
					Arrays.asList(roleADMIN, roleUSER));
			UsersRepository.save(userADMIN);
			System.out.println("🚀 Roles and user admin create with success 🚀");
		}
	}

}
