package com.cron.parser;

import java.util.regex.Pattern;

public class CronExpression {

    private static final String CRON_REGEX =
            "^(" +
                    "(((([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?,)*([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?)|(([\\*]|[0-9]|[0-5][0-9])/([0-9]|[0-5][0-9]))|([\\?])|([\\*]))[\s]"+
                    "(((([0-9]|[0-1][0-9]|[2][0-3])(-([0-9]|[0-1][0-9]|[2][0-3]))?,)*([0-9]|[0-1][0-9]|[2][0-3])(-([0-9]|[0-1][0-9]|[2][0-3]))?)|(([\\*]|[0-9]|[0-1][0-9]|[2][0-3])/([0-9]|[0-1][0-9]|[2][0-3]))|([\\?])|([\\*]))[\s]"+
                    "(((([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(-([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1]))?,)*([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(-([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1]))?(C)?)|(([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])/([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(C)?)|(L(-[0-9])?)|(L(-[1-2][0-9])?)|(L(-[3][0-1])?)|(LW)|([1-9]W)|([1-3][0-9]W)|([\\?])|([\\*]))[\s]"+
                    "(((([1-9]|0[1-9]|1[0-2])(-([1-9]|0[1-9]|1[0-2]))?,)*([1-9]|0[1-9]|1[0-2])(-([1-9]|0[1-9]|1[0-2]))?)|(([1-9]|0[1-9]|1[0-2])/([1-9]|0[1-9]|1[0-2]))|(((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?,)*(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)|((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)/(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))|([\\?])|([\\*]))[\s]"+
                    "((([1-7](-([1-7]))?,)*([1-7])(-([1-7]))?)|([1-7]/([1-7]))|(((MON|TUE|WED|THU|FRI|SAT|SUN)(-(MON|TUE|WED|THU|FRI|SAT|SUN))?,)*(MON|TUE|WED|THU|FRI|SAT|SUN)(-(MON|TUE|WED|THU|FRI|SAT|SUN))?(C)?)|((MON|TUE|WED|THU|FRI|SAT|SUN)/(MON|TUE|WED|THU|FRI|SAT|SUN)(C)?)|(([1-7]|(MON|TUE|WED|THU|FRI|SAT|SUN))?(L|LW)?)|(([1-7]|MON|TUE|WED|THU|FRI|SAT|SUN)#([1-7])?)|([\\?])|([\\*]))([\s]" +
                    "(.*)" +
            "))$";

    private static final Pattern CRON_PATTERN = Pattern.compile(CRON_REGEX, Pattern.CASE_INSENSITIVE);

    public static boolean isValidCronExpression(String cronExpression) {
        return CRON_PATTERN.matcher(cronExpression).matches();
    }

    public static String extractMinutes(String cronExpression){
        String s = substringBySpaces(cronExpression,0);
        return extractTimeUnit(s, 0,60);
    }

    public static String extractHours(String cronExpression){
        String s = substringBySpaces(cronExpression,1);
        return extractTimeUnit(s, 0,24);
    }

    public static String extractDayOfMonth(String cronExpression){
        String s = substringBySpaces(cronExpression,2);
        return extractTimeUnit(s, 1,32);
    }

    public static String extractMonth(String cronExpression){

        String s = substringBySpaces(cronExpression,3);
        String st = translateMonths(s);
        return extractTimeUnit(st, 1,13);
    }

    public static String extractDayOfWeek(String cronExpression){
        String s = substringBySpaces(cronExpression,4);
        String st = translateDayOfWeek(s);
        return extractTimeUnit(st,1, 7);
    }

    private static String extractCommand(String cronExpression) {
        return substringBySpaces(cronExpression,5);
    }

    private static String extractTimeUnit(String st,int init, int limit) {
        StringBuilder buffer = new StringBuilder();
        String[] split = st.split(",");
        for (String sx : split)
        {
            if (sx.contains("-")) {
                String[] splitx = sx.split("-");
                for (int i = Integer.valueOf(splitx[0]); i <= Integer.valueOf(splitx[1]); i++) {
                    buffer.append(i +" ");
                }
            } else if (sx.equals("*")){
                for (int i = init; i < limit; i++) {
                    buffer.append(i +" ");
                }
            } else if (sx.contains("*/")) {
                String[] splitx = sx.split("/");
                int endPosition = Integer.valueOf(splitx[1]);
                for (int i = init; i<limit; i = i + endPosition) {
                    buffer.append(i +" ");
                }
            } else if (sx.contains("/")) {
                String[] splitx = sx.split("/");
                for (int i = Integer.valueOf(splitx[0]); i < limit; i=i+Integer.valueOf(splitx[1])) {
                    buffer.append(i +" ");
                }
            } else {
                buffer.append(sx +" ");
            }

        }

        return buffer.deleteCharAt(buffer.length() - 1).toString();
    }

    private static String translateMonths(String s) {
        String result;
        result = s.toUpperCase().replace("JAN","0");
        result = result.toUpperCase().replace("FEB","1");
        result = result.toUpperCase().replace("MAR","2");
        result = result.toUpperCase().replace("APR","3");
        result = result.toUpperCase().replace("MAY","4");
        result = result.toUpperCase().replace("JUN","5");
        result = result.toUpperCase().replace("JUL","6");
        result = result.toUpperCase().replace("AUG","7");
        result = result.toUpperCase().replace("SEP","8");
        result = result.toUpperCase().replace("OCT","9");
        result = result.toUpperCase().replace("NOV","10");
        result = result.toUpperCase().replace("DEC","11");

        return result;
    }

    private static String translateDayOfWeek(String s) {
        String result;
        result = s.toUpperCase().replace("MON","2");
        result = result.toUpperCase().replace("TUE","3");
        result = result.toUpperCase().replace("WED","4");
        result = result.toUpperCase().replace("THU","5");
        result = result.toUpperCase().replace("FRI","6");
        result = result.toUpperCase().replace("SAT","7");
        result = result.toUpperCase().replace("SUN","1");

        return result;
    }

    private static String substringBySpaces(String str, int index ) {
        String[] split = str.split(" ");
        return split[index];
    }

    public static void printCronConfiguration(String cronExpression){
        if(isValidCronExpression(cronExpression)) {
            System.out.println("minute          " + extractMinutes(cronExpression));
            System.out.println("hour            " + extractHours(cronExpression));
            System.out.println("day of month    " + extractDayOfMonth(cronExpression));
            System.out.println("month           " + extractMonth(cronExpression));
            System.out.println("day of week     " + extractDayOfWeek(cronExpression));
            System.out.println("command         " + extractCommand(cronExpression));
        } else {
            System.out.println("Invalid Cron Expression");
        }
    }

}
