package com.github.youssefwadie.bugtracker.util;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class JdbcUtils {
    private JdbcUtils() {
    }

    public static String buildSortString(Stream<Sort.Order> orderStream) {
        final List<String> sorts = new ArrayList<>(8);
        orderStream.forEach(order -> {
            final String sort = String.format("%s %s", order.getProperty(), order.getDirection());
            sorts.add(sort);
        });
        return String.join(", ", sorts);
    }
}

