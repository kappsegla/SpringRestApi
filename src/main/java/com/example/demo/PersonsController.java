package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api")
public class PersonsController {

    private final AtomicLong counter = new AtomicLong();
   // CopyOnWriteArrayList<Person> personsList = new CopyOnWriteArrayList<>();
    List<Person> personsList = Collections.synchronizedList(new ArrayList<>());
    //https://howtodoinjava.com/java/collections/arraylist/synchronize-arraylist/
    //    @GetMapping
    //    @PostMapping
    //    @PutMapping


    @RequestMapping(value = "/persons", method = GET)
    public List<Person> allPersons() {
        return personsList;
    }

    //We can (should?) use persons here also.
    //See pluralization here: https://www.restapitutorial.com/lessons/restfulresourcenaming.html
    @RequestMapping(value = "/persons/{id}", method = GET)
    public ResponseEntity<?> onePerson(@PathVariable("id") long id) {
        Optional<Person> person = personsList.stream().filter(p -> p.getId() == id).findFirst();
        ResponseEntity responseEntity;
        if (person.isPresent())
           responseEntity = new ResponseEntity<Person>(person.get(), HttpStatus.OK);

        responseEntity = new ResponseEntity(new CustomErrorType("User with id " + id
                + " not found"), HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @RequestMapping(value = "/persons", method = POST)
    public ResponseEntity<?> createPerson(@RequestBody Person person){
        personsList.add(person);
        person.setId(counter.incrementAndGet());
        personsList.add(person);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/persons/" + person.getId());
        return new ResponseEntity(person, headers, HttpStatus.CREATED);
      //  return ResponseEntity.created(new URI("/api/persons/" + person.getId())).build();
    }

//    @RequestMapping(value = "/persons/{id}", method = PUT)
//    public ResponseEntity<?> updatePerson(@PathVariable("id") long id, @RequestBody Person person) {
//
//        //http://websystique.com/spring-boot/spring-boot-rest-api-example/
//        //Should update person with id..
////        User currentUser = userService.findById(id);
////
////        if (currentUser == null) {
////            logger.error("Unable to update. User with id {} not found.", id);
////            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
////                    HttpStatus.NOT_FOUND);
////        }
////
////        currentUser.setName(user.getName());
////        currentUser.setAge(user.getAge());
////        currentUser.setSalary(user.getSalary());
////
////        userService.updateUser(currentUser);
////        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
//        return new ResponseEntity<Object>(person, HttpStatus.OK);
//    }


}
