package com.safra.InventaryEntryAndExitService.controller;

import com.safra.InventaryEntryAndExitService.config.CertificateSSLConfig;
import com.safra.InventaryEntryAndExitService.entities.DocumentEntry;
import com.safra.InventaryEntryAndExitService.entities.DocumentExit;
import com.safra.InventaryEntryAndExitService.entities.OIBT;
import com.safra.InventaryEntryAndExitService.services.InventoryEntryService;
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
@RequestMapping("InventoryEntry")
public class InventoryEntryController {

    @Autowired
    private InventoryEntryService inventoryEntryService;

    @Autowired
    private LoginService loginService;

    @PostMapping("entry")
    public String transferData(HttpServletRequest request, @RequestBody DocumentEntry documentEntry) {
        //loginService.login();
        HttpSession session = request.getSession();
        new CertificateSSLConfig().trustSelfSignedSSL();
        try {
            System.out.println(documentEntry.toString());
            return inventoryEntryService.EntryRoll((HttpHeaders) session.getAttribute("cookies"), documentEntry);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    @PostMapping("partialEntry")
    public List<OIBT> partialTransfer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        new CertificateSSLConfig().trustSelfSignedSSL();
        try {
            return inventoryEntryService.EntryRollsStockMin((HttpHeaders) session.getAttribute("cookies"));
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
