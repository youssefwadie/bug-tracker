package com.github.youssefwadie.bugtracker;

import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;

import java.util.Comparator;
import java.util.Iterator;


@ComponentScan("com.github.youssefwadie.bugtracker.jdbc")
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractJdbcTest {


    protected <T> boolean isSorted(final Iterable<T> iterable, final Comparator<T> comparator) {
        Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return true;
        }

        T current, previous = iterator.next();
        while (iterator.hasNext()) {
            current = iterator.next();
            if (comparator.compare(previous, current) > 0) return false;
            previous = current;
        }
        return true;
    }

}
