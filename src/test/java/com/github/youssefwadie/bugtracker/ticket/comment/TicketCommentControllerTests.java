package com.github.youssefwadie.bugtracker.ticket.comment;

import com.github.youssefwadie.bugtracker.config.SecurityConfig;
import com.github.youssefwadie.bugtracker.dto.mappers.TicketCommentMapper;
import com.github.youssefwadie.bugtracker.security.TokenProperties;
import com.github.youssefwadie.bugtracker.security.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class TicketCommentControllerTests {
    @MockBean
    TicketCommentService commentService;

    @MockBean
    TicketCommentMapper commentMapper;

    @MockBean
    AuthService authService;

    @MockBean
    TokenProperties tokenProperties;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser(username = "youssef@mail.com", roles = "ADMIN")
    void editCommentTestWithNoId() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/tickets/comments")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }



}