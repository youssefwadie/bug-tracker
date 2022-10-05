package com.github.youssefwadie.bugtracker.user.dao;


import com.github.youssefwadie.bugtracker.AbstractJdbcTest;
import com.github.youssefwadie.bugtracker.model.Role;
import com.github.youssefwadie.bugtracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryTests extends AbstractJdbcTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void findTeamMembersTests() {
        final Sort.Order[] orders = new Sort.Order[]{
                Sort.Order.asc("first_name"),
                Sort.Order.desc("last_name"),
        };

        final int pageNumber = 0;
        final int pageSize = 5;
        final long projectId = 2L;

        final Comparator<User> comparator = Comparator.comparing(User::getFirstName)
                .thenComparing(Comparator.comparing(User::getLastName).reversed());

        final Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAllTeamMembers(projectId, pageable);
        boolean sorted = super.isSorted(page.getContent(), comparator);
        assertThat(page.getContent().size()).isEqualTo(pageSize);
        assertThat(sorted).isTrue();

    }

    @Test
    void countTeamMembersByProjectIdTests() {
        final long projectId = 1L;
        final long expectedTeamMembersCount = 1L;
        final long actualTeamMembersCount = userRepository.countTeamMembersByProjectId(projectId);
        assertThat(actualTeamMembersCount).isEqualTo(expectedTeamMembersCount);
    }

    @Test
    void saveTest() {
        final String email = "youssefwadie@mail.com";
        final String password = "11223344";
        final String firstName = "Youssef";
        final String lastName = "Wadie";
        final Role role = Role.ROLE_ADMIN;
        final boolean emailVerified = true;
        User user = new User(null, email, password, firstName, lastName, role, emailVerified);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getPassword()).isEqualTo(password);
        assertThat(savedUser.getFirstName()).isEqualTo(firstName);
        assertThat(savedUser.getLastName()).isEqualTo(lastName);
        assertThat(savedUser.getRole()).isEqualTo(role);
        assertThat(savedUser.isEmailVerified()).isEqualTo(emailVerified);
    }

    @Test
    void updateTest() {
        final String email = "youssefwadie@mail.com";
        final String password = "11223344";
        final String firstName = "Youssef";
        final String lastName = "Wadie";
        final Role role = Role.ROLE_ADMIN;
        final boolean emailVerified = true;
        User user = new User(1L, email, password, firstName, lastName, role, emailVerified);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getPassword()).isEqualTo(password);
        assertThat(savedUser.getFirstName()).isEqualTo(firstName);
        assertThat(savedUser.getLastName()).isEqualTo(lastName);
        assertThat(savedUser.getRole()).isEqualTo(role);
        assertThat(savedUser.isEmailVerified()).isEqualTo(emailVerified);
    }

    @Test
    void existsAllById() {
        List<Long> ids = List.of(1L);
        for (Long id : ids) {
            boolean exists = userRepository.existsById(id);
            assertThat(exists).isTrue();
        }
//        assertThat(exists).isFalse();
    }

}
