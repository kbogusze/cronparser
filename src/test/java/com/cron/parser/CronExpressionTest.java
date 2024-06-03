package com.cron.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CronExpressionTest {

    @Test
    void isValidCronExpression() {
        String cronExample = "*/15 0 1,15 * 1-5 /usr/bin/find";
        assertTrue(CronExpression.isValidCronExpression(cronExample));
    }

    @Test
    void extractMinutes() {
        String cronExample = "*/15 0 1,15 * 1-5 /usr/bin/find";
        assertEquals("0 15 30 45", CronExpression.extractMinutes(cronExample));
    }

    @Test
    void extractHours() {
        String cronExample = "*/15 0 1,15 * 1-5 /usr/bin/find";
        assertEquals("0", CronExpression.extractHours(cronExample));
    }

    @Test
    void extractDayOfMonth() {
        String cronExample = "*/15 0 1,15 * 1-5 /usr/bin/find";
        assertEquals("1 15", CronExpression.extractDayOfMonth(cronExample));
    }

    @Test
    void extractMonth() {
        String cronExample = "*/15 0 1-15 1-10 1-5 /usr/bin/find";
        assertEquals("1 2 3 4 5 6 7 8 9 10", CronExpression.extractMonth(cronExample));
    }

    @Test
    void extractMonth2() {
        String cronExample = "*/15 0 1,15 JAN-SEP 1-5 /usr/bin/find";
        assertEquals("0 1 2 3 4 5 6 7 8", CronExpression.extractMonth(cronExample));
    }


    @Test
    void extractDayOfWeek() {
        String cronExample = "*/15 0 1,15 JAN-SEP 1-SAT /usr/bin/find";
        assertEquals("1 2 3 4 5 6 7", CronExpression.extractDayOfWeek(cronExample));
    }

    @Test
    void printCronConfiguration() {
        String cronExample = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CronExpression.printCronConfiguration(cronExample);
    }


}