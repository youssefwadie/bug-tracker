package com.github.youssefwadie.bugtracker.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ListUtilsTest {

    @Test
    void diffListsTest() {
        List<Integer> firstList = List.of(1, 2, 3, 4, 5, 10, 50, 88);
        List<Integer> secondList = List.of(1, 2, 3, 4, 5, 6, 7);
        final ListDiff<Integer> listDiff = ListUtils.diffLists(firstList, secondList);
        assertThat(listDiff.getElementsInFirstListOnly().size()).isEqualTo(3);
        assertThat(listDiff.getElementsInSecondListOnly().size()).isEqualTo(2);
        assertThat(listDiff.getDuplicatedElements().size()).isEqualTo(5);
    }
}
