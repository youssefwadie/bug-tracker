package com.github.youssefwadie.bugtracker.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;

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
