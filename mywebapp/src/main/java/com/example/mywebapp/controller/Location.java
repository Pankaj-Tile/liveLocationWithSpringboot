package com.example.mywebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Location {

     @RequestMapping("/user")
    public String user(){
        return "user-location";
    }
    
}
