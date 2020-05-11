package com.tech.mkblogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AccountJspWithUsersApplication  {

	public static void main(String[] args) {
		SpringApplication.run(AccountJspWithUsersApplication.class, args);
	}
}
