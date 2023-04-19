package com.safra.InventaryEntryAndExitService.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentExit {

    @JsonProperty("DocumentLines")
    private List<DocumentLineExit> documentLines;
}
