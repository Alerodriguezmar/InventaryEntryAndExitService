package com.safra.InventaryEntryAndExitService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryExitAndEntry {

   private String docNum;

   @Id
   private String docEntry;

   private List<OIBT> oibtList;

   private TypeOperation typeOperation;

}

