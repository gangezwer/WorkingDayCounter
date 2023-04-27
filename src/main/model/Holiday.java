package main.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Holiday {

    private String name;
    private Calendar date;

    // custom constructor is used to parse and create holidays.
    public Holiday(String name, String dateStr) throws ParseException {
        this.name = name;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        calendar.setTime(format.parse(dateStr));
        date = calendar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}


