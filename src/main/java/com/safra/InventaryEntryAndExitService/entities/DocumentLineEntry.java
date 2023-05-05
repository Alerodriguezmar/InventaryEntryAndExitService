package com.safra.InventaryEntryAndExitService.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentLineEntry {

    @JsonProperty("ItemCode")
    private String itemCode;

    @JsonProperty("Quantity")
    private double quantity;

    @JsonProperty("WarehouseCode")
    private String warehouseCode;

    @JsonProperty("AccountCode")
    private String accountCode;

    @JsonProperty("UnitPrice")
    private String unitPrice;

    @JsonProperty("CostingCode")
    private String costingCode;

    @JsonProperty("U_EST_CONC_ENTINV")
    private String uEstConcEntinv;

    @JsonProperty("CostingCode2")
    private String costingCode2;

    @JsonProperty("CostingCode3")
    private String costingCode3;

    @JsonProperty("Comments")
    private String Comments;

    @JsonProperty("BatchNumbers")
    private List<BatchNumber> batchNumbers;
}
