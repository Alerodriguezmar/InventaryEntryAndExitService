package com.safra.InventaryEntryAndExitService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safra.InventaryEntryAndExitService.entities.DocumentEntry;
import com.safra.InventaryEntryAndExitService.entities.DocumentExit;
import com.safra.InventaryEntryAndExitService.entities.OIBT;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface InventoryEntryService {

    String EntryRoll(HttpHeaders cookies, DocumentEntry documentEntry);


    List<OIBT> EntryRollsStockMin(HttpHeaders cookies) throws JsonProcessingException;


    List<OIBT> EntryRollsStockMinBy(HttpHeaders cookies, List<OIBT> oibts,String comment) throws JsonProcessingException;

    List<OIBT> EntryRollsStockMinByToldos(HttpHeaders cookies, List<OIBT> oibts,String comment) throws JsonProcessingException;

}
