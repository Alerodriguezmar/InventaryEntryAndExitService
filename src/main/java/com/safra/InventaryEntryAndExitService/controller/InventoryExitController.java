package com.safra.InventaryEntryAndExitService.controller;

import com.safra.InventaryEntryAndExitService.config.CertificateSSLConfig;
import com.safra.InventaryEntryAndExitService.entities.DocumentExit;
import com.safra.InventaryEntryAndExitService.entities.OIBT;
import com.safra.InventaryEntryAndExitService.services.InventoryExitService;
import com.safra.InventaryEntryAndExitService.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("InventoryExit")
public class InventoryExitController {

	@Autowired
	private InventoryExitService  inventoryExitService;

	@Autowired
	private LoginService loginService;


	@PostMapping("Exit")
	public String transferData(HttpServletRequest request, @RequestBody DocumentExit documentExit) {
		//loginService.login();
		HttpSession session = request.getSession();
		new CertificateSSLConfig().trustSelfSignedSSL();
		try {
			System.out.println(documentExit.toString());
			return inventoryExitService.ExitRoll((HttpHeaders) session.getAttribute("cookies"), documentExit);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}


	@PostMapping("partialExit")
	public List<OIBT> partialTransfer(HttpServletRequest request) {
		HttpSession session = request.getSession();
		new CertificateSSLConfig().trustSelfSignedSSL();
		try {
			return inventoryExitService.ExitRollsStockMin((HttpHeaders) session.getAttribute("cookies"));
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
