package com.iamvioletta.yolo.v8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for main web UI.
 */
@Controller
public class WebController {
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
