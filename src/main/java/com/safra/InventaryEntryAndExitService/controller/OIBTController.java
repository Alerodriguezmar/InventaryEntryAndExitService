package com.safra.InventaryEntryAndExitService.controller;

import com.safra.InventaryEntryAndExitService.entities.OIBT;
import com.safra.InventaryEntryAndExitService.services.OIBTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("OIBT")

public class OIBTController {



    @Autowired
    private OIBTService oibtService;

    @Operation(summary = "Assing a transient location for a material in process", parameters = {
            @Parameter(name = "whsCode", description = "This parameter correspons to a name of machine associated to a location",example = "E2")})
    @GetMapping()
    public ResponseEntity<List<OIBT>> findAllByWhsCode(@RequestParam(name = "whsCode" , defaultValue = "10004") String whsCode){
        return ResponseEntity.status(HttpStatus.OK).body(oibtService.findAllByWhsCode(whsCode));
    }


    @Operation(summary = "Assing a transient location for a material in process", parameters = {
            @Parameter(name = "whsCode", description = "This parameter correspons to a name of machine associated to a location", required = false)})
    @GetMapping("/size")
    public ResponseEntity<Integer> findAllByWhsCodeSize(@RequestParam(name = "whsCode" , defaultValue = "10004") String whsCode){
        return ResponseEntity.status(HttpStatus.OK).body(oibtService.findAllByWhsCode(whsCode).size());
    }

    @GetMapping("/quantity")
    public ResponseEntity<List<OIBT>> findAllByBatchNumAndQuantity(@RequestParam(name = "whsCode" , defaultValue = "10013-2") String whsCode,@RequestParam(name = "whsCode" , defaultValue = "1.2") double quantity){
        return ResponseEntity.status(HttpStatus.OK).body(oibtService.findAllByBatchNumAndQuantity(whsCode,quantity));
    }

}
