package com.utez.geco.DTO;

import com.utez.geco.model.Person;
import com.utez.geco.model.Role;

public class SignupRequest {
    private String email;
    private String password;
    private Person idPerson;
    private Role idRol;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    public Role getIdRol() {
        return idRol;
    }

    public void setIdRol(Role idRol) {
        this.idRol = idRol;
    }
}
