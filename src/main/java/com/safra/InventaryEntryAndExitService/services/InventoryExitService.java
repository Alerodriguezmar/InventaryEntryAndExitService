package com.safra.InventaryEntryAndExitService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safra.InventaryEntryAndExitService.entities.DocumentExit;
import com.safra.InventaryEntryAndExitService.entities.OIBT;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface InventoryExitService {



    String ExitRoll(HttpHeaders cookies, DocumentExit documentExits);


    List<OIBT> ExitRollsStockMin(HttpHeaders cookies) throws JsonProcessingException;

    String ExitRollsStockMinBy(HttpHeaders cookies,List<OIBT> oibts) throws JsonProcessingException;

}
