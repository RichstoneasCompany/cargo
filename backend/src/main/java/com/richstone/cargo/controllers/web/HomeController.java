package com.richstone.cargo.controllers.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "landing-page";
    }

}