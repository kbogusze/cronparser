package com.cron.parser;

public class App 
{
    public static void main( String[] args )
    {
        for (String cronExample : args) {
            CronExpression.printCronConfiguration(cronExample);
        }
    }
}
