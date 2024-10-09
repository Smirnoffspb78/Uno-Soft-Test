package com.smirnov.model;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Колонка таблицы.
 */
public class Column {
    /**
     * Хранилище уникальных строк и их номера группы.
     */
    private final Map<String, Integer> uniqueWordsMap = new HashMap<>();

    /**
     * Добавляет уникальное слово в хранилище.
     * @param word Слово
     * @param numberGroup Номер группы
     */
    public void addUniqueWord(String word, Integer numberGroup) {
        requireNonNull(word);
        if (!word.isBlank()) {
            uniqueWordsMap.put(word, numberGroup);
        }
    }

    public Map<String, Integer> getUniqueWordsMap() {
        return uniqueWordsMap;
    }
}
