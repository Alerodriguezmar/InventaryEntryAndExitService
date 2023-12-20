package com.safra.InventaryEntryAndExitService.services;


import com.safra.InventaryEntryAndExitService.entities.OIBT;

import java.util.List;

public interface OIBTService {

    List<OIBT> findAllByWhsCode(String whsCode);

    List<OIBT> findAllByBatchNumAndQuantity(String whsCode, double quantity);
}
