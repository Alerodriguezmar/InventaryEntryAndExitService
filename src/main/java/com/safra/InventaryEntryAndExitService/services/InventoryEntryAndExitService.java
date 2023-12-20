package com.safra.InventaryEntryAndExitService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safra.InventaryEntryAndExitService.entities.OIBT;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface InventoryEntryAndExitService {


    List<OIBT> InventoryExitAndEntry(HttpHeaders cookies) throws JsonProcessingException;

    List<OIBT> InventoryExitAndEntryToldos(HttpHeaders cookies) throws JsonProcessingException;
}
