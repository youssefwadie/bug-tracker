package com.github.youssefwadie.bugtracker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.model.Role;
import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.project.ProjectService;
import com.github.youssefwadie.bugtracker.user.dao.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Component
public class DefaultBugTrackerBootStrap implements CommandLineRunner {
	private final UserRepository userRepository;
	private final ProjectService projectService;
	private	final Random random = new Random();
	
	@Override
	public void run(String... args) throws Exception {
		final List<User> users = generateUsers(1, 50);
		userRepository.saveAllAndFlush(users);
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
		for(int i = start; i <= end; i++) {
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
		for(int i = start; i < end; i++) {
			Project project = new Project();
			project.setName(String.format("Project %d title", i));
			project.setDescription(String.format("Project %d description", i));
			projects.add(project);
		}
		
		return projects;
	}

}