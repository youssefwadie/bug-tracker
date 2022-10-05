package com.github.youssefwadie.bugtracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String password;

	@Column(name = "first_name", length = 255)
	private String firstName;

	@Column(name = "last_name", length = 255)
	private String lastName;

	@Column(name = "role", nullable = true)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "email_verified")
	private boolean emailVerified;

	public User(Long id) {
		this.id = id;
	}

	@Transient
	public String getFullName() {
		return String.format("%s %s", firstName, lastName);
	}

	public void setFullName(String fullName) {
		if (fullName == null)
			return;
		
		int whiteSpaceIndex = fullName.indexOf(' ');
		if (whiteSpaceIndex == -1) {
			this.firstName = fullName;
		} else {
			this.firstName = fullName.substring(0, whiteSpaceIndex);
			this.lastName = fullName.substring(whiteSpaceIndex + 1);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
