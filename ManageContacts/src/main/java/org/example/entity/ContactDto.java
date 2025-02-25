package org.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactDto {
    @JsonProperty("id")
    final long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("surname")
    String surname;

    @JsonProperty("phoneNumber")
    long phoneNumber;

    @JsonProperty("email")
    String email;

    @JsonCreator
    public ContactDto(@JsonProperty("id") long id,
                      @JsonProperty("name") String name, @JsonProperty("surname") String surname,
                      @JsonProperty("phoneNumber") long phoneNumber, @JsonProperty("email") String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
