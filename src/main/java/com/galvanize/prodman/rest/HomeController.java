package com.galvanize.prodman.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {
    public static final String RESPONSE = "Product Manager";

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return RESPONSE;
    }
}
