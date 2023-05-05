package com.safra.InventaryEntryAndExitService.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safra.InventaryEntryAndExitService.entities.OIBT;
import com.safra.InventaryEntryAndExitService.services.InventoryEntryAndExitService;
import com.safra.InventaryEntryAndExitService.services.InventoryEntryService;
import com.safra.InventaryEntryAndExitService.services.InventoryExitService;
import com.safra.InventaryEntryAndExitService.services.OIBTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InventoryEntryAndExitServiceImpl implements InventoryEntryAndExitService {

    @Autowired
    private InventoryEntryService inventoryEntryService;

    @Autowired
    private InventoryExitService inventoryExitService;


    @Autowired
    private OIBTService oibtService;


    @Override
    public List<OIBT> InventoryExitAndEntry(HttpHeaders cookies) throws JsonProcessingException {

        //Consulta los primeros 2 Rollos inferiores a 1.2 mt en la bodega 10013-2
        List<OIBT> oibts = oibtService.findAllByWhsCode("10013-2");

        System.out.println(oibts);

        //Realiza la salida de los rollos consultado
       var docNum = inventoryExitService.ExitRollsStockMinBy(cookies,oibts);

        //realiza el ingreso de los rollos consultados
        inventoryEntryService.EntryRollsStockMinBy(cookies,oibts,docNum);

        return oibts;
    }
}
