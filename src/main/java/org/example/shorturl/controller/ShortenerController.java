package org.example.shorturl.controller;

import org.example.shorturl.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenerController {

    @Autowired
    ShortenerService shortenerService;

    @RequestMapping("/shorten")
    public String shorten(@RequestParam String original) {
        return shortenerService.shorten(original);
    }

    @RequestMapping("/retrieve")
    public String retrieve(@RequestParam String shortened) {
        String original;
        try{
            original = shortenerService.retrieve(shortened);
            }catch (Exception e) {
            return "NOT FOUND";
        }
        return original;
    }

    @RequestMapping("/assign")
    public String assign(@RequestParam String original, @RequestParam String shortened) {
        return shortenerService.assignIfAvailable(original, shortened).toString();
    }
}
