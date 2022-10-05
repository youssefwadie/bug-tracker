package com.github.youssefwadie.bugtracker.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListDiff<T> {
    private final List<T> elementsInFirstListOnly = new ArrayList<>();
    private final List<T> elementsInSecondListOnly = new ArrayList<>();
    private final List<T> duplicatedElements = new ArrayList<>();

    public void addToFirstList(T item) {
        this.elementsInFirstListOnly.add(item);
    }

    public void addToSecondList(T item) {
        this.elementsInSecondListOnly.add(item);
    }

    public void addToDuplicatedItems(T item) {
        this.duplicatedElements.add(item);
    }
}
