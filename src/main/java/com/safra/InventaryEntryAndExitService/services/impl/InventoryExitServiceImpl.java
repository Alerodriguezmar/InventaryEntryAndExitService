package com.safra.InventaryEntryAndExitService.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safra.InventaryEntryAndExitService.entities.BatchNumber;
import com.safra.InventaryEntryAndExitService.entities.DocumentExit;
import com.safra.InventaryEntryAndExitService.entities.DocumentLineExit;
import com.safra.InventaryEntryAndExitService.entities.OIBT;
import com.safra.InventaryEntryAndExitService.services.InventoryExitService;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class InventoryExitServiceImpl implements InventoryExitService {

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

    @Override
    public String ExitRoll(HttpHeaders cookies, DocumentExit documentExits) {

        requestAttemps++;
        try {

            URI url = null;


            ResponseEntity<String> response = null;
            try {

                url = new URI("https://192.168.100.193:50000/b1s/v1/InventoryGenExits");


            } catch (URISyntaxException ex) {
                System.out.println("error");
            }
            RequestEntity<DocumentExit> request = new RequestEntity<>(documentExits, loginService.getAuthorizationHeader(),
                    HttpMethod.POST, url);
            try {
                response = restTemplate.exchange(request, String.class);
            } catch (RestClientException e) {
                System.out.println(e);
            }
            // retry when unauthorized
            if (response != null && response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                // refresh authorization cookies
                request = new RequestEntity<>(documentExits, loginService.getAuthorizationHeader(), HttpMethod.POST, url);
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
    public List<OIBT> ExitRollsStockMin(HttpHeaders cookies) throws JsonProcessingException {

        DocumentExit documentExit = new DocumentExit();


        List<OIBT> oibts = oibtService.findAllByWhsCode("10013-2").subList(0,2);
        documentExit.setDocumentLines(this.DocumentLinesExitByOIBT(oibts));

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(documentExit);
        System.out.println(jsonString);

//        try {
//
//            URI url = null;
//
//
//            ResponseEntity<String> response = null;
//            try {
//                url = new URI(env.getProperty("url.sap") + "StockTransfers");
//
//                System.out.println(documentExit);
//
//            } catch (URISyntaxException ex) {
//                System.out.println(ex);
//            }
//            RequestEntity<DocumentExit> request = new RequestEntity<>(documentExit, loginService.getAuthorizationHeader(),
//                    HttpMethod.POST, url);
//            try {
//                response = restTemplate.exchange(request, String.class);
//            } catch (RestClientException e) {
//
//            }
//            // retry when unauthorized
//            if (response != null && response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
//                // refresh authorization cookies
//                request = new RequestEntity<>(documentExit, loginService.getAuthorizationHeader(), HttpMethod.POST, url);
//                response = restTemplate.exchange(request, String.class);
//            }
//
//            System.out.println(response.toString());
//
//            return oibts;
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (requestAttemps <= MAX_ATTEMPS) {
//                System.out.println((String.format("Retring once again after %d attemps", requestAttemps)));
//
//            }
//        } finally {
//            // clean requestAttemps class variable
//            requestAttemps = 0;
//        }
        throw new RestClientException("It was not posible connect to " + env.getProperty("url.sap") + "StockTransfers");
    }

    public List<DocumentLineExit> DocumentLinesExitByOIBT(List<OIBT> oibts){

        List<DocumentLineExit> stockTransferLines = new ArrayList<>();

        var mapItemCode = oibts.stream().collect(groupingBy(OIBT::getItemCode));


        mapItemCode.forEach((s, oibts1) -> {

            var a =oibts1.stream().map(OIBT::getQuantity).map(Double::valueOf);

            var sum =a.mapToDouble(Double::doubleValue).sum();

            var sumResult = String.format("%.3f%n", sum);

            DocumentLineExit documentLineExit = new DocumentLineExit();
            documentLineExit.setWarehouseCode("10013-2");
            documentLineExit.setQuantity(Double.valueOf(sumResult));
            documentLineExit.setItemCode(s);
            documentLineExit.setAccountCode("61230501");
            documentLineExit.setCostingCode("4010");
            documentLineExit.setUEstConcSalinv("47");
            documentLineExit.setCostingCode2("NoAplica");
            documentLineExit.setCostingCode3("N/A");

            List<BatchNumber> batchNumbers = new ArrayList<>();
            for(OIBT oibt: oibts1){
                batchNumbers.add(new BatchNumber(oibt.getBatchNum(),oibt.getQuantity()));
            };
            documentLineExit.setBatchNumbers(batchNumbers);
            stockTransferLines.add(documentLineExit);
        });

        return  stockTransferLines;
    }
}
