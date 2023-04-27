import main.Main;
import main.model.Holiday;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayCounterTest {

    @Test
    public void holidayFileReadTest() {
        try {
            List<Holiday> holidayList = Main.readHolidaysFromFile();
            Assert.assertFalse(holidayList.isEmpty());
        } catch (Exception e) {
            Assert.fail("Error when parsing holiday.txt file");
        }
    }

    @Test
    public void completionDateCalculatorTest() {
        try {
            Main.readHolidaysFromFile();

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(format.parse("19-08-2022"));

            Calendar calculatedDays = Main.calculateCompletionDate(startDate, 5);

            Calendar endDateDate = Calendar.getInstance();
            endDateDate.setTime(format.parse("26-08-2022"));

            Assert.assertEquals(endDateDate, calculatedDays);
        } catch (Exception e) {
            Assert.fail("Error when executing completionDateCalculatorTest");
        }
    }

    @Test
    public void holidayTest() {
        try {
            List<Holiday> holidayList = Main.readHolidaysFromFile();
            List<Calendar> holidays = new ArrayList<>();
            holidayList.forEach((holiday -> holidays.add(holiday.getDate())));

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendarDate = Calendar.getInstance();
            calendarDate.setTime(format.parse("23-08-2022"));

            Assert.assertTrue(Main.checkIfDateIsHolidayOrWeekend(holidays, calendarDate));
        } catch (Exception e) {
            Assert.fail("Error when executing holidayTest");
        }
    }

    @Test
    public void weekendTest() {
        try {
            List<Holiday> holidayList = Main.readHolidaysFromFile();
            List<Calendar> holidays = new ArrayList<>();
            holidayList.forEach((holiday -> holidays.add(holiday.getDate())));

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendarDate = Calendar.getInstance();

            calendarDate.setTime(format.parse("20-08-2022"));
            Assert.assertTrue(Main.checkIfDateIsHolidayOrWeekend(holidays, calendarDate));
            calendarDate.setTime(format.parse("21-08-2022"));
            Assert.assertTrue(Main.checkIfDateIsHolidayOrWeekend(holidays, calendarDate));
        } catch (Exception e) {
            Assert.fail("Error when executing weekendTest");
        }
    }

    @Test
    public void weekdayTest() {
        try {
            List<Holiday> holidayList = Main.readHolidaysFromFile();
            List<Calendar> holidays = new ArrayList<>();
            holidayList.forEach((holiday -> holidays.add(holiday.getDate())));

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendarDate = Calendar.getInstance();

            calendarDate.setTime(format.parse("19-08-2022"));
            Assert.assertFalse(Main.checkIfDateIsHolidayOrWeekend(holidays, calendarDate));
            calendarDate.setTime(format.parse("22-08-2022"));
            Assert.assertFalse(Main.checkIfDateIsHolidayOrWeekend(holidays, calendarDate));
        } catch (Exception e) {
            Assert.fail("Error when executing weekdayTest");
        }
    }

}
