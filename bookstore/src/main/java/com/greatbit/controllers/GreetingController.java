package com.greatbit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue ="World") String name,
                           Model model) {//required - не обязательный параметр, Model - как хешмапа (ключ-строка, объект-любая строка)
        model.addAttribute("name", name);
        return "greeting";//спринг понимает, что нужно найти шаблон с такой строкой
    }
}
