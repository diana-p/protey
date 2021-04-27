package com.test.protey.controller;

import com.test.protey.dto.PersonWithStatusDTO;
import com.test.protey.exception.PersonNotFoundException;
import com.test.protey.exception.ValidationException;
import com.test.protey.model.Person;
import com.test.protey.model.Status;
import com.test.protey.service.PersonService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MainController {

    private final PersonService personService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @ApiOperation("save person in DB and return person id")
    @PostMapping("/add")
    public ResponseEntity<Long> addPerson(@RequestBody Person person) throws ValidationException {
        Long personId = personService.savePerson(person);
        return ResponseEntity.ok()
                .body(personId);
    }

    @ApiOperation("find person by id")
    @GetMapping("/find")
    public ResponseEntity<Person> findPersonById(@RequestParam Long personId) throws PersonNotFoundException {
        Person person = personService.findPersonById(personId);
        return ResponseEntity.ok()
                .body(person);
    }

    @ApiOperation("update persons status ")
    @PutMapping("/updateStatus")
    public ResponseEntity<PersonWithStatusDTO> updatePersonStatus (@RequestParam Long personId,
                                                                   @RequestParam Status status) {
        PersonWithStatusDTO person = personService.updatePersonStatus(personId, status);
        return ResponseEntity.ok()
                .body(person);
    }


}
