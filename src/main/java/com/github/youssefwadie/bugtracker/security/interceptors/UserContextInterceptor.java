package com.github.youssefwadie.bugtracker.security.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.youssefwadie.bugtracker.model.User;
import com.github.youssefwadie.bugtracker.security.UserContextHolder;
import com.github.youssefwadie.bugtracker.security.service.BugTrackerUserDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * Sets and removes the logged-in {@link User} on the current execution thread.
 * The purpose of the interceptor is to manage setting and removing the current logged-in user
 */
@Slf4j
public class UserContextInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("setting user");
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return true;

        Authentication authentication = context.getAuthentication();
        if (authentication == null) return true;

        Object principal = authentication.getPrincipal();
        if (principal instanceof BugTrackerUserDetails userDetails) {
            UserContextHolder.set(userDetails.getUser());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("removing user");
        UserContextHolder.remove();
    }

}
