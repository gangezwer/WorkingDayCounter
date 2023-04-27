package main;

import main.model.Holiday;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Holiday> holidayList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            System.out.println("> Initializing Application");
            readHolidaysFromFile();
            System.out.println("> Successfully loaded holidays from text file.");
            getUserInputs();
            System.out.println("\n> Terminating Application.");
        } catch (Exception e) {
            System.out.println("\n> Unrecoverable Error Occurred!");
            System.out.println(">>" + e.getMessage());
            System.out.println("> Please fix the above error and restart the application.");
        }
    }

    public static List<Holiday> readHolidaysFromFile() throws IOException, ParseException {
        List<Holiday> holidays = new ArrayList<>();
        // Errors are not handled here and sent to main method.
        // This is done as the application cannot proceed if holidays are not loaded correctly.
        try (
                FileReader fileReader = new FileReader("src/main/resource/holidays.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String line;
            // File is being read line by line and parsed.
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineData = line.split(" : ");
                String holidayDateStr = lineData[0].trim();
                String holidayName = lineData[1].trim();
                // Data is saved to an object for future use.
                Holiday holiday = new Holiday(holidayName, holidayDateStr);
                holidays.add(holiday);
            }
            holidayList = holidays;
            return holidays;
        }
    }

    private static void getUserInputs() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Scanner scanner = new Scanner(System.in);
        // While loop is executed until user enters '-1'.
        while (true) {
            System.out.println("\n> Enter Details to Calculate Completion Date of Task. Or '-1' to Exit.");
            System.out.print("> Starting date (DD-MM-YYYY) or leave empty for today : ");
            String startDateStr = scanner.nextLine();
            if (startDateStr != null && startDateStr.trim().equals("-1")) {
                break;
            }

            Calendar startDate = Calendar.getInstance();
            // Check if entered date is parsable.
            try {
                if (startDateStr != null && !startDateStr.trim().equals("")) {
                    startDate.setTime(format.parse(startDateStr.trim()));
                    // Input in not checked if it is a previous day, to allow user to calculate for previous days.
                }
                startDate = trimCalendarTime(startDate);
            } catch (ParseException e) {
                System.out.println(">> Invalid input for date. Please enter date in format DD-MM-YYYY, example : "
                        + format.format(Calendar.getInstance().getTime()));
                continue;
            }

            int allocatedDays;
            // Implemented a nested loop to get numeric input
            // so that user can retry number of days input without entering date again.
            while (true) {
                try {
                    System.out.print("> Enter number of days allocated to task : ");
                    String allocatedDaysStr = scanner.nextLine();
                    allocatedDays = Integer.parseInt(allocatedDaysStr);
                    if (allocatedDays <= 0) {
                        System.out.println(">> Input cannot be negative nor 0.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(">> Invalid number format please try again.");
                }
            }

            startDate = calculateCompletionDate(startDate, allocatedDays);
            SimpleDateFormat formatLong = new SimpleDateFormat("MMMM dd EEEE yyyy");
            SimpleDateFormat formatShort = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(">>> Task Will be Completed On - " + formatLong.format(startDate.getTime())
                    + " (" + formatShort.format(startDate.getTime()) + ")");
        }
    }

    public static Calendar calculateCompletionDate(Calendar startDate, int allocatedDays) {
        System.out.println("\n> Calculating Completion Date");
        // Dates of holidays are collected to an array for ease of use and performance
        List<Calendar> holidays = new ArrayList<>();
        holidayList.forEach((holiday -> holidays.add(holiday.getDate())));
        // count counts the workable days
        int count = 0;
        // start date is set back by one to assess whether that the starting day is weekend or holiday.
        startDate.add(Calendar.DATE, -1);
        while (count < allocatedDays) {
            startDate.add(Calendar.DATE, 1);
            if (!checkIfDateIsHolidayOrWeekend(holidays, startDate)) {
                count++;
            }
        }
        return startDate;
    }

    public static boolean checkIfDateIsHolidayOrWeekend(List<Calendar> holidays, Calendar calendarDate) {
        return (holidays.contains(calendarDate) || calendarDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendarDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }

    // This method trim time from calendar instances making so that they can be compared with other dates.
    private static Calendar trimCalendarTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

}
