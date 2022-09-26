package com.github.youssefwadie.bugtracker.project;

public class ProjectNotFoundException extends IllegalArgumentException {
	public ProjectNotFoundException() {
		super();
	}
	public ProjectNotFoundException(String message) {
		super(message);
	}
	
	public ProjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
