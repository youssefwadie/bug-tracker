package com.github.youssefwadie.bugtracker.util;

import java.util.Collections;
import java.util.List;

public final class ListUtils {
    private ListUtils() {

    }


    /**
     * find the difference between two sorted lists.
     * the time complexity is N*log(M) + M*log(N). where N and M is the size of the first and second list respectively.
     *
     * @param firstList  must not be {@literal null}.
     * @param secondList must not be {@literal null}.
     * @param <T>        the type of list items.
     * @return {@link ListDiff} instance with the difference details.
     * @throws IllegalArgumentException if {@code firstList} is {@literal null} or {@code secondList} is {@literal null}
     */
    public static <T> ListDiff<T> diffLists(List<? extends Comparable<? super T>> firstList, List<? extends Comparable<? super T>> secondList) {
        if (firstList == null || secondList == null) {
            throw new IllegalArgumentException();
        }
        ListDiff<T> listDiff = new ListDiff<>();
        final int firstListSize = firstList.size();
        final int secondListSize = secondList.size();
        int i = 0, j = 0;
        while (i < firstListSize && j < secondListSize) {
            final T item = (T) firstList.get(i);
            int indexInSecondList = Collections.binarySearch(secondList, item);
            if (indexInSecondList < 0) {
                listDiff.addToFirstList(item);
            } else {
                listDiff.addToDuplicatedItems(item);
                j++;
            }
            i++;
        }
        for (i = 0; i < secondListSize; i++) {
            final T item = (T) secondList.get(i);
            if (Collections.binarySearch(firstList, item) < 0) {
                listDiff.addToSecondList(item);
            }
        }

        return listDiff;
    }
}
