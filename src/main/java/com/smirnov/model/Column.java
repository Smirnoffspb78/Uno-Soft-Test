package com.smirnov.model;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class Column {

    private final Map<String, Integer> uniqueWordsMap = new HashMap<>();

    public void addUniqueWord(String word, Integer numberGroup){
        requireNonNull(word);
            if (!word.isBlank()){
                uniqueWordsMap.put(word, numberGroup);
            }
    }

    @Override
    public String toString() {
        return "Column{" +
                ", uniqueWordsMap=" + uniqueWordsMap +
                '}';
    }

    public Map<String, Integer> getUniqueWordsMap() {
        return uniqueWordsMap;
    }
}
