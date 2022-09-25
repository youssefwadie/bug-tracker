package com.github.youssefwadie.bugtracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(Long id) {
        this.id = id;
    }

    @Column(name = "role", nullable = true)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;


    private String email;

    private String password;

    private boolean enabled;

    @Transient
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }


    public void setFullName(String fullName) {
        int whiteSpaceIndex = fullName.indexOf(' ');
        if (whiteSpaceIndex == -1) {
            this.firstName = fullName;
        } else {
            this.firstName = fullName.substring(0, whiteSpaceIndex);
            this.lastName = fullName.substring(whiteSpaceIndex + 1);
        }
    }

}
