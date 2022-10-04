package com.github.youssefwadie.bugtracker.project;


import com.github.youssefwadie.bugtracker.model.Project;
import com.github.youssefwadie.bugtracker.security.exceptions.ConstraintsViolationException;
import com.github.youssefwadie.bugtracker.user.services.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProjectValidatorService {

    public void validateProject(Project project) {
        Map<String, String> errors = new HashMap<>();
        if (isBlank(project.getName())) {
            errors.put("name", UserValidatorService.BLANK_INPUT_MSG);
        }
        if (isBlank(project.getDescription())) {
            errors.put("description", UserValidatorService.BLANK_INPUT_MSG);
        }

        if (!errors.isEmpty()) {
            throw new ConstraintsViolationException(errors);
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}
