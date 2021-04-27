package com.test.protey.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long personId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String status;
    private Date lastUpdate;


}
