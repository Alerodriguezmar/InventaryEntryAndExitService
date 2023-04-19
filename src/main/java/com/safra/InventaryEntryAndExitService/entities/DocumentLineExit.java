package com.safra.InventaryEntryAndExitService.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentLineExit {

    @JsonProperty("AccountCode")
    private String accountCode;

    @JsonProperty("ItemCode")
    private String itemCode;

    @JsonProperty("Quantity")
    private Double quantity;

    @JsonProperty("CostingCode")
    private String costingCode;

    @JsonProperty("U_EST_CONC_SALINV")
    private String uEstConcSalinv;

    @JsonProperty("CostingCode2")
    private String costingCode2;

    @JsonProperty("CostingCode3")
    private String costingCode3;

    @JsonProperty("BatchNumbers")
    private List<BatchNumber> batchNumbers;

    @JsonProperty("WarehouseCode")
    private String warehouseCode;
}
