package com.safra.InventaryEntryAndExitService.services;

import org.springframework.http.HttpHeaders;

public interface LoginService {
    
    HttpHeaders login();
    
    HttpHeaders getAuthorizationHeader();
    
}
