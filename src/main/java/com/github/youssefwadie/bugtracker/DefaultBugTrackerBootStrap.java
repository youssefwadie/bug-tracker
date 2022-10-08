package com.github.youssefwadie.bugtracker;

import com.github.youssefwadie.bugtracker.model.*;
import com.github.youssefwadie.bugtracker.project.ProjectService;
import com.github.youssefwadie.bugtracker.ticket.TicketRepository;
import com.github.youssefwadie.bugtracker.ticket.comment.dao.TicketCommentRepository;
import com.github.youssefwadie.bugtracker.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.*;

@RequiredArgsConstructor
//@Component
public class DefaultBugTrackerBootStrap implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProjectService projectService;

    private final TicketCommentRepository ticketCommentRepository;
    private final TicketRepository ticketRepository;


    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
//        generateAndSaveUsersAndProjects();
//        generateAndSaveTicketComments();
    }

    private void generateAndSaveTicketComments() {
        final List<Ticket> tickets = ticketRepository.findAll();
        final List<User> users = userRepository.findAll();
        final int usersCount = users.size();
        final String commentContentTemplate = "Comment %d on ticket %s by %s";

        for (Ticket ticket : tickets) {
            final int commentsCount = random.nextInt(10);
            for (int i = 0; i < commentsCount; ++i) {
                final User user = users.get(random.nextInt(usersCount));
                final TicketComment comment = new TicketComment();
                comment.setCommenter(user);
                comment.setTicketId(ticket.getId());
                comment.setContent(String.format(commentContentTemplate, i + 1, ticket.getTitle(), user.getFullName()));
                ticketCommentRepository.save(comment);
            }
        }
    }


    private void generateAndSaveUsersAndProjects() {
        final List<User> users = generateUsers(1, 50);
        userRepository.saveAll(users);
        final List<Project> projects = generateProjects(2, 50);
        setTeamMembersForProjects(projects, users);
        for (Project project : projects) {
            projectService.save(project);
        }

    }

    private void setTeamMembersForProjects(List<Project> projects, List<User> users) {
        for (Project project : projects) {
            final Set<User> projectUsers = getRandomUsers(users);
            project.setTeamMembers(projectUsers);
        }

    }

    private Set<User> getRandomUsers(List<User> users) {
        final int numberOfUsers = random.nextInt(users.size() / 4);
        final Set<User> setOfUsers = new HashSet<>();

        for (int i = 0; i < numberOfUsers; i++) {
            final int userIndex = random.nextInt(users.size());
            setOfUsers.add(users.get(userIndex));
        }
        return setOfUsers;
    }

    private List<User> generateUsers(int start, int end) {
        final List<User> users = new ArrayList<>(end - start + 1);
        for (int i = start; i <= end; i++) {
            User user = new User();
            user.setEmail(String.format("user%d@mail.com", i));
            user.setPassword("12345");
            user.setFirstName("Developer");
            user.setLastName(Integer.toString(i));
            user.setRole(Role.ROLE_DEVELOPER);
            user.setEmailVerified(false);
            users.add(user);
        }
        return users;
    }

    private List<Project> generateProjects(int start, int end) {
        final List<Project> projects = new ArrayList<>(end - start + 1);
        for (int i = start; i < end; i++) {
            Project project = new Project();
            project.setName(String.format("Project %d title", i));
            project.setDescription(String.format("Project %d description", i));
            projects.add(project);
        }

        return projects;
    }

}
