package com.iamvioletta.yolo.v8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
