package com.test.protey.service;

import com.test.protey.dto.PersonDTO;
import com.test.protey.dto.PersonIdDTO;
import com.test.protey.dto.PersonWithStatusDTO;
import com.test.protey.exception.PersonNotFoundException;
import com.test.protey.exception.ValidationException;
import com.test.protey.model.Person;
import com.test.protey.model.Status;
import com.test.protey.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ThreadPoolTaskScheduler taskScheduler;

    public PersonIdDTO savePerson(PersonDTO personDTO) throws ValidationException {
        Person person = personRepository.savePerson(fromPersonDtoToPerson(personDTO));
        if (person.getStatus().toUpperCase().equals(Status.ONLINE.toString())) {
            changeDelay(person.getPersonId());
        }
        return PersonIdDTO.builder().id(person.getPersonId()).build();
    }

    private Person fromPersonDtoToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setEmail(personDTO.getEmail());
        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setStatus(Status.OFFLINE.toString());
        person.setLastUpdate(new Date());
        return person;
    }

    public Person findPersonById(Long personId) throws PersonNotFoundException {
        return personRepository.findPersonById(personId).orElseThrow(PersonNotFoundException::new);
    }

    public PersonWithStatusDTO updatePersonStatus(Long personId, Status status) throws PersonNotFoundException {
        Person person = personRepository.findPersonById(personId).orElseThrow(PersonNotFoundException::new);
        String oldStatus = person.getStatus() != null ? person.getStatus() : Status.OFFLINE.toString();
        person.setStatus(status.toString());
        person.setLastUpdate(new Date());
        personRepository.savePerson(person);

        if (oldStatus.equals(Status.ONLINE.toString()) || status == Status.ONLINE) {
            changeDelay(personId);
        }

        return PersonWithStatusDTO.builder()
                .personId(person.getPersonId())
                .oldStatus(oldStatus)
                .newStatus(person.getStatus())
                .build();
    }

    private void changeDelay(Long personId) {
        long delay = new Date().getTime() + Duration.ofMinutes(5).toMillis();
        taskScheduler.schedule(changeStatus(personId), new Date(delay));
    }

    private Runnable changeStatus(Long personId) {
        return () -> {
            Person person = personRepository.findPersonById(personId).orElseThrow(PersonNotFoundException::new);

            if (person.getLastUpdate().getTime() < new Date().getTime() - Duration.ofMinutes(5).toMillis()) {
                person.setStatus(Status.AWAY.toString());
                person.setLastUpdate(new Date());
                personRepository.savePerson(person);
            }
        };
    }
}
