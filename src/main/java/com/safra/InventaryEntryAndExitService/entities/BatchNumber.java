package com.safra.InventaryEntryAndExitService.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchNumber {
    @JsonProperty("BatchNumber")
    private String BatchNumber;
    @JsonProperty("Quantity")
    private String Quantity;
}
