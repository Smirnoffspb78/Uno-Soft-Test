package com.smirnov;

import com.smirnov.model.Column;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Path.of;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

public class GroupingRow {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private static final String PATH_OUTPUT_FILE = "result.txt";

    /**
     * Группирует строки и записывает результаты группировки в файл
     *
     * @param pathInputFile Путь к файлу для чтения данных
     */
    public void groupingRow(String pathInputFile) {
        try {
            Set<String> lines = readAllLines(of(pathInputFile))
                    .parallelStream()
                    .filter(this::validLine)
                    .collect(toSet());
            List<List<String>> groups = sortByGroup(lines).stream()
                    .sorted((g1, g2) -> Integer.compare(g2.size(), g1.size()))
                    .toList();
            long numberGroups = groups.stream()
                    .filter(s -> s.size() > 1)
                    .count();
            String resultInfo = format("Количество групп c размером больше, чем одна строка: %d", numberGroups);
            logger.info(resultInfo);
            getFileWriter(resultInfo, groups);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Записывает результаты в файл
     *
     * @param resultInfo  Количество групп c размером больше, чем одна строка
     * @param sorterGroup Отсортированный по группам строки
     * @throws IOException
     */
    private void getFileWriter(String resultInfo, List<List<String>> sorterGroup) throws IOException {
        try (FileWriter writer = new FileWriter(PATH_OUTPUT_FILE, false)) {
            writer.write(resultInfo);
            writer.write("\n");
            writer.write("\n");
            int numGroup = 1;
            for (List<String> group : sorterGroup) {
                writer.write("Группа " + numGroup);
                writer.write("\n");
                for (String string : group) {
                    writer.write(string);
                    writer.write("\n");
                }
                writer.write("\n");
                numGroup++;
            }
        }
        logger.info("Результат записан в файл " + PATH_OUTPUT_FILE);
    }

    /**
     * Реализует алгоритм сортировки по группам.
     *
     * @param lines Множество строк
     * @return Строки, отсортированные по группам
     */
    private List<List<String>> sortByGroup(Set<String> lines) {
        List<List<String>> groups = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        for (String line : lines) {
            String[] words = Arrays.stream(line.split(";"))
                    .map(word -> word.replace("\"", ""))
                    .toArray(String[]::new);
            Integer numberGroup = null;
            if (!columns.isEmpty()) {
                int i = 0;
                while (i < words.length && i < columns.size() && isNull(numberGroup)) {
                    if (!words[i].isEmpty()) {
                        numberGroup = columns.get(i).getUniqueWordsMap().get(words[i]);
                        if (!isNull(numberGroup)) {
                            groups.get(numberGroup).add(line);
                        }
                    }
                    i++;
                }
                if (isNull(numberGroup)) {
                    numberGroup = createNewGroup(line, groups);
                }
                addColumns(columns, words, numberGroup);
            } else {
                numberGroup = createNewGroup(line, groups);
                addColumns(columns, words, numberGroup);
            }
        }
        return groups;
    }

    /**
     * Валидирует строку.
     *
     * @param line строка
     * @return true/false, если строка валидная/не валидная
     */
    private boolean validLine(String line) {
        String[] words = line.split(";");
        for (String word : words) {
            if (!Pattern.matches("^\"[^\"]*\"$|^\"\"$", word)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Создает новую группу из строки и добавляет ее в список групп
     *
     * @param line   строка
     * @param groups Список групп
     * @return Номер созданной группы
     */
    private int createNewGroup(String line, List<List<String>> groups) {
        int numberGroup = groups.size();
        List<String> group = new ArrayList<>();
        group.add(line);
        groups.add(group);
        return numberGroup;
    }

    /**
     * Добавляет уникальные слова в колонки.
     *
     * @param columns     Список колонок
     * @param words       Массив строк
     * @param numberGroup Номер группы, которым относятся слова
     */
    private void addColumns(List<Column> columns, String[] words, int numberGroup) {
        for (int k = 0; k < words.length; k++) {
            if (k < columns.size() && words[k].isEmpty()) {
                columns.add(new Column());
            } else if (k < columns.size() && !words[k].isEmpty()) {
                columns.get(k).addUniqueWord(words[k], numberGroup);
            } else if (k >= columns.size() && words[k].isEmpty()) {
                columns.add(new Column());
            } else {
                Column column = new Column();
                column.addUniqueWord(words[k], numberGroup);
                columns.add(column);
            }
        }
    }
}
