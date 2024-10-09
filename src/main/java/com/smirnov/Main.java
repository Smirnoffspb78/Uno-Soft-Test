package com.smirnov;


import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

public class Main {
    private final Logger logger = Logger.getLogger(getClass().getName());

    public static void main(String[] args) {
        Main main = new Main();
        long start = currentTimeMillis();
        GroupingRow solution = new GroupingRow();
        solution.groupingRow("src/main/resources/lng.txt");
        long finish = currentTimeMillis();
        main.logger.info(format("Время выполнения программы: %s", (float) (finish - start) / 1000));
    }
}
