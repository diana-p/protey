package com.test.protey.repository;

import com.test.protey.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    default Person savePerson(Person person) {
        if (person.getPersonId() != null && findPersonById(person.getPersonId()).isPresent()) {
            updatePerson(person.getEmail(), person.getFirstName(), person.getLastName(), person.getPhoneNumber(),
                    person.getStatus(), person.getLastUpdate(), person.getPersonId());
            return findPersonById(person.getPersonId()).get();
        } else {
            return save(person);
        }
    }

    @Modifying
    @Transactional
    @Query(value = "update person set email=?1, first_name=?2, last_name=?3, phone_number=?4, status=?5, last_update=?6 " +
            "where person_id=?7", nativeQuery = true)
    void updatePerson(String email, String firstName, String lastName, String phoneNumber, String status,
                      Date lastUpdate, Long id);

    @Transactional
    @Query(value = "select * from person p where p.person_id=?", nativeQuery = true)
    Optional<Person> findPersonById(Long id);
}
