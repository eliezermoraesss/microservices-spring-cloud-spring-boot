package br.com.eliezer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class EliezerConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EliezerConfigServerApplication.class, args);
	}

}
