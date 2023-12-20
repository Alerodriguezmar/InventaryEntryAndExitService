package com.safra.InventaryEntryAndExitService.services.impl;

import com.safra.InventaryEntryAndExitService.entities.OIBT;
import com.safra.InventaryEntryAndExitService.repositories.OIBTRepository;
import com.safra.InventaryEntryAndExitService.services.OIBTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OIBTServiceImpl implements OIBTService {

    @Autowired
    private OIBTRepository oibtRepository;

    @Override
    public List<OIBT> findAllByWhsCode(String whsCode) {
        return oibtRepository.findAllByBatchNum(whsCode);
    }


    @Override
    public List<OIBT> findAllByBatchNumAndQuantity(String whsCode , double quantity) {
        return oibtRepository.findAllByBatchNumAndQuantity(whsCode,quantity);
    }
}
