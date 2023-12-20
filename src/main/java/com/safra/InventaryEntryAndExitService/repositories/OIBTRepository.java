package com.safra.InventaryEntryAndExitService.repositories;


import com.safra.InventaryEntryAndExitService.entities.OIBT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OIBTRepository extends JpaRepository<OIBT,String> {

    String consult = """
                        
            select distinct
                  bt.ItemCode,
                  bt.ItemName,
                  bt.BatchNum,
                  bt.Quantity ,
                  tm.validFor,
                  CONCAT(bt.ItemCode,'.',bt.BatchNum) Identifier
                                  
            from OIBT AS bt
                  INNER JOIN OITM as tm ON bt.ItemCode = tm.ItemCode
                        
            where  U_GrupoProd = '06'-- Telas
                  AND tm.ItemCode Like 'I_%%'
                  AND bt.WhsCode = :whsCode
                  AND bt.quantity <> 0
                  AND bt.Quantity <= 1.2
                  AND tm.validFor = 'Y'
            """;

    @Query(nativeQuery = true, value = consult)
    List<OIBT> findAllByBatchNum(@Param("whsCode") String whsCode);


    String consultQuantity = """
                        
            select distinct
                  bt.ItemCode,
                  bt.ItemName,
                  bt.BatchNum,
                  bt.Quantity ,
                  tm.validFor,
                  CONCAT(bt.ItemCode,'.',bt.BatchNum) Identifier
                                  
            from OIBT AS bt
                  INNER JOIN OITM as tm ON bt.ItemCode = tm.ItemCode
                        
            where  U_GrupoProd = '06'-- Telas
                  AND bt.WhsCode = :whsCode
                  AND bt.quantity <> 0
                  AND bt.Quantity <= :quantity
                  AND tm.validFor = 'Y'
            """;

    @Query(nativeQuery = true, value = consultQuantity)
    List<OIBT> findAllByBatchNumAndQuantity(@Param("whsCode") String whsCode, @Param("quantity") double quantity);

}
