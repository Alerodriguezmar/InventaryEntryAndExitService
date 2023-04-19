package com.safra.InventaryEntryAndExitService;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
@OpenAPIDefinition(info=@Info(title="Inventory Entry And Exit Service"))
public class InventoryEntryAndExitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryEntryAndExitServiceApplication.class, args);
	}

}
