package com.github.youssefwadie.bugtracker.security;

import com.github.youssefwadie.bugtracker.model.User;


/**
 * Associates a given {@link User} with the current execution thread.
 * The purpose of the class is to provide a convenient way to obtain the logged-in user.
 */

public final class UserContextHolder {
    private final static ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

    public static void set(User user) {
        USER_HOLDER.set(user);
    }

    public static User get() {
        return USER_HOLDER.get();
    }

    public static void remove() {
        USER_HOLDER.remove();
    }
}
