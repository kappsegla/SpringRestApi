package com.example.demo;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class HelloController {

    @RequestMapping(value = "/Hello", method = GET)
    public Person index() {
        return new Person();
    }

    @RequestMapping(value = "/Hello", method = POST, produces = "application/json")
    public Person indexPost(@RequestParam(value = "name", defaultValue = "Martin") String name) {
        Person person = new Person();
        person.setName(name);
        return person;
    }
    @RequestMapping(value = "/Hello", method = POST, produces = "text/plain")
    public String indexPostString(@RequestParam(value = "name", defaultValue = "Martin") String name) {
        return "This is a text format.";
    }

}