package com.bryant.controller;

import com.bryant.constants.Web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DoorController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getNameByUserId")
    public String getNameByUserId(
            @RequestParam(value = "userId") String userId
    ) {
        String uri = Web.SERVICE_USERS_URL + "/getNameByUserId";
        return restTemplate.getForObject(uri, String.class, userId);
    }

}
