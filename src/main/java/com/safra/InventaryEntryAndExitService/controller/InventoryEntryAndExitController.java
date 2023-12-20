package com.safra.InventaryEntryAndExitService.controller;

import com.safra.InventaryEntryAndExitService.config.CertificateSSLConfig;
import com.safra.InventaryEntryAndExitService.entities.OIBT;
import com.safra.InventaryEntryAndExitService.services.InventoryEntryAndExitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("InventoryEntryAndExit")
public class InventoryEntryAndExitController {

    @Autowired
    private InventoryEntryAndExitService inventoryEntryAndExitService;

    @PostMapping("partialProcess")
    public List<OIBT> partialTransfer(HttpServletRequest request) {
        //Adjunta cookies de session
        HttpSession session = request.getSession();

        //Configura el certificado SSL(autofirmado)
        new CertificateSSLConfig().trustSelfSignedSSL();
        try {
            //consume service
            return inventoryEntryAndExitService.InventoryExitAndEntry((HttpHeaders) session.getAttribute("cookies"));
        } catch (Exception e) {
            //capture error
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("partialProcessToldos")
    public List<OIBT> partialTransferToldos(HttpServletRequest request) {
        //Adjunta cookies de session
        HttpSession session = request.getSession();

        //Configura el certificado SSL(autofirmado)
        new CertificateSSLConfig().trustSelfSignedSSL();
        try {
            //consume service
            return inventoryEntryAndExitService.InventoryExitAndEntryToldos((HttpHeaders) session.getAttribute("cookies"));
        } catch (Exception e) {
            //capture error
            e.printStackTrace();
        }
        return null;
    }
}
