package com.test.protey.service;

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

import static java.util.Objects.isNull;

@Service
@Transactional
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ThreadPoolTaskScheduler taskScheduler;

    public Long savePerson(Person person) throws ValidationException {
        validatePersonDto(person);
        return personRepository.save(person).getPersonId();
    }

    private void validatePersonDto(Person person) throws ValidationException {
        if (isNull(person)) {
            throw new ValidationException("This person is incorrect");
        }
        if ((isNull(person.getFirstName()) || person.getFirstName().isEmpty()) &&
                (isNull(person.getLastName()) || person.getLastName().isEmpty())) {
            throw new ValidationException("Name is empty");
        }
    }

    public Person findPersonById(Long personId) throws PersonNotFoundException {
        return personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
    }

    public PersonWithStatusDTO updatePersonStatus(Long personId, Status status) throws PersonNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
        String oldStatus = person.getStatus() != null ? person.getStatus() : Status.OFFLINE.toString();
        person.setStatus(status.toString());
        person.setLastUpdate(new Date());

        if (oldStatus.equals(Status.ONLINE.toString())) {
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
            Person person = personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);

            if (person.getLastUpdate().getTime() < new Date().getTime() - Duration.ofMinutes(5).toMillis()) {
                person.setStatus(Status.AWAY.toString());
                person.setLastUpdate(new Date());
                personRepository.save(person);
            }
        };
    }
}
