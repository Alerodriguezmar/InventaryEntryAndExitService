package com.safra.InventaryEntryAndExitService.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntry {
    @JsonProperty("DocumentLines")
    private List<DocumentLineEntry> documentLines;
}
