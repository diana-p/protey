package com.test.protey.controller;

import com.test.protey.dto.PersonDTO;
import com.test.protey.dto.PersonIdDTO;
import com.test.protey.dto.PersonWithStatusDTO;
import com.test.protey.exception.PersonNotFoundException;
import com.test.protey.exception.ValidationException;
import com.test.protey.model.Person;
import com.test.protey.model.Status;
import com.test.protey.service.PersonService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class MainController {

    private final PersonService personService;

    @ApiOperation("save person in DB and return person id")
    @PostMapping(value = "/person")
    public ResponseEntity<PersonIdDTO> addPerson(@Valid @RequestBody PersonDTO personDTO) throws ValidationException {
        PersonIdDTO personIdDTO = personService.savePerson(personDTO);
        return ResponseEntity.ok()
                .body(personIdDTO);
    }

    @ApiOperation("find person by id")
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable Long id) throws PersonNotFoundException {
        Person person = personService.findPersonById(id);
        return ResponseEntity.ok()
                .body(person);
    }

    @ApiOperation("update persons status ")
    @PutMapping("/status")
    public ResponseEntity<PersonWithStatusDTO> updatePersonStatus (@RequestParam Long personId,
                                                                   @RequestParam Status status) {
        PersonWithStatusDTO person = personService.updatePersonStatus(personId, status);
        return ResponseEntity.ok()
                .body(person);
    }


}
