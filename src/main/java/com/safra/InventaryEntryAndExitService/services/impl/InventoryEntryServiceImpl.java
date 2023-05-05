package com.safra.InventaryEntryAndExitService.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safra.InventaryEntryAndExitService.entities.*;
import com.safra.InventaryEntryAndExitService.repositories.InventoryExitAndEntryRepository;
import com.safra.InventaryEntryAndExitService.services.InventoryEntryService;
import com.safra.InventaryEntryAndExitService.services.LoginService;
import com.safra.InventaryEntryAndExitService.services.OIBTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
@Service
public class InventoryEntryServiceImpl implements InventoryEntryService {

    private int requestAttemps;

    private static final int MAX_ATTEMPS = 3;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private OIBTService oibtService;

    @Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LoginService loginService;

    @Autowired
    private InventoryExitAndEntryRepository inventoryExitAndEntryRepository;

    @Override
    public String EntryRoll(HttpHeaders cookies, DocumentEntry documentEntry) {
        requestAttemps++;
        try {

            URI url = null;


            ResponseEntity<String> response = null;
            try {

                url = new URI("https://192.168.100.193:50000/b1s/v1/InventoryGenEntries");


            } catch (URISyntaxException ex) {
                System.out.println("error");
            }
            RequestEntity<DocumentEntry> request = new RequestEntity<>(documentEntry, loginService.getAuthorizationHeader(),
                    HttpMethod.POST, url);
            try {
                response = restTemplate.exchange(request, String.class);
            } catch (RestClientException e) {
                System.out.println(e);
            }
            // retry when unauthorized
            if (response != null && response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                // refresh authorization cookies
                request = new RequestEntity<>(documentEntry, loginService.getAuthorizationHeader(), HttpMethod.POST, url);
                response = restTemplate.exchange(request, String.class);
            }

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            if (requestAttemps <= MAX_ATTEMPS) {

            }
        } finally {
            // clean requestAttemps class variable
            requestAttemps = 0;
        }
        throw new RestClientException("It was not posible connect to "+ "StockTransfers");
    }

    @Override
    public List<OIBT> EntryRollsStockMin(HttpHeaders cookies) throws JsonProcessingException {

        DocumentEntry documentEntry = new DocumentEntry();

        List<OIBT> oibts = oibtService.findAllByWhsCode("10013-2").subList(0,2);

        documentEntry.setDocumentLines(this.DocumentLinesEntryByOIBT(oibts,"comments"));

        try {

            URI url = null;

            ResponseEntity<String> response = null;
            try {
                url = new URI("https://192.168.100.193:50000/b1s/v1/InventoryGenEntries");

            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
            RequestEntity<DocumentEntry> request = new RequestEntity<>(documentEntry, loginService.getAuthorizationHeader(),
                    HttpMethod.POST, url);
            try {
                response = restTemplate.exchange(request, String.class);
            } catch (RestClientException e) {
                    e.printStackTrace();
            }
            // retry when unauthorized
            if (response != null && response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                // refresh authorization cookies
                request = new RequestEntity<>(documentEntry, loginService.getAuthorizationHeader(), HttpMethod.POST, url);
                response = restTemplate.exchange(request, String.class);
            }

            System.out.println("Entrada"+response);

            return oibts;
        } catch (Exception e) {
            e.printStackTrace();
            if (requestAttemps <= MAX_ATTEMPS) {
                System.out.println((String.format("Retring once again after %d attemps", requestAttemps)));

            }
        } finally {
            // clean requestAttemps class variable
            requestAttemps = 0;
        }
        throw new RestClientException("It was not posible connect to " + "https://192.168.100.193:50000/b1s/v1/InventoryGenExits");
    }

    @Override
    public List<OIBT> EntryRollsStockMinBy(HttpHeaders cookies, List<OIBT> oibts,String comment) throws JsonProcessingException {
        DocumentEntry documentEntry = new DocumentEntry();

        documentEntry.setDocumentLines(this.DocumentLinesEntryByOIBT(oibts,comment));


        System.out.println(documentEntry.toString());

        try {

            URI url = null;

            ResponseEntity<String> response = null;
            try {
                url = new URI("https://192.168.100.193:50000/b1s/v1/InventoryGenEntries");

            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
            RequestEntity<DocumentEntry> request = new RequestEntity<>(documentEntry, loginService.getAuthorizationHeader(),
                    HttpMethod.POST, url);
            try {
                response = restTemplate.exchange(request, String.class);
            } catch (RestClientException e) {
                    e.printStackTrace();
            }
            // retry when unauthorized
            if (response != null && response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                // refresh authorization cookies
                request = new RequestEntity<>(documentEntry, loginService.getAuthorizationHeader(), HttpMethod.POST, url);
                response = restTemplate.exchange(request, String.class);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());


            inventoryExitAndEntryRepository.save(new InventoryExitAndEntry(jsonNode.get("DocNum").asText(),jsonNode.get("DocEntry").asText(),oibts,TypeOperation.ENTRY));

            return oibts;
        } catch (Exception e) {
            e.printStackTrace();
            if (requestAttemps <= MAX_ATTEMPS) {
                System.out.println((String.format("Retring once again after %d attemps", requestAttemps)));

            }
        } finally {
            // clean requestAttemps class variable
            requestAttemps = 0;
        }
        throw new RestClientException("It was not posible connect to " + "https://192.168.100.193:50000/b1s/v1/InventoryGenExits");
    }

    public List<DocumentLineEntry> DocumentLinesEntryByOIBT(List<OIBT> oibts,String comment){

        List<DocumentLineEntry> stockTransferLines = new ArrayList<>();

        var mapItemCode = oibts.stream().collect(groupingBy(OIBT::getItemCode));


        mapItemCode.forEach((s, oibts1) -> {

            var a =oibts1.stream().map(OIBT::getQuantity).map(Double::valueOf);

            var sum =a.mapToDouble(Double::doubleValue).sum();

            var sumResult = String.format("%.3f%n", sum);

            DocumentLineEntry documentLineEntry = new DocumentLineEntry();
            documentLineEntry.setWarehouseCode("10018");
            documentLineEntry.setQuantity(Double.valueOf(sumResult));
            documentLineEntry.setItemCode(s);
            documentLineEntry.setAccountCode("14350501");
            documentLineEntry.setCostingCode("4010");
            documentLineEntry.setUEstConcEntinv("20");
            documentLineEntry.setCostingCode2("NoAplica");
            documentLineEntry.setCostingCode3("N/A");
            documentLineEntry.setUnitPrice(String.valueOf(1/Double.valueOf(sumResult)));
            documentLineEntry.setComments(comment);

            List<BatchNumber> batchNumbers = new ArrayList<>();
            for(OIBT oibt: oibts1){
                batchNumbers.add(new BatchNumber(oibt.getBatchNum(),oibt.getQuantity()));
            };
            documentLineEntry.setBatchNumbers(batchNumbers);
            stockTransferLines.add(documentLineEntry);
        });

        return  stockTransferLines;
    }


}
