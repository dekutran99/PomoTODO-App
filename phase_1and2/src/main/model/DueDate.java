package model;

import model.exceptions.InvalidTimeException;
import model.exceptions.NullArgumentException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

// Note: Any library in JDK 8 may be used to implement this class, however,
//     you must not use any third-party library in your implementation
// Hint: Explore https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html

// Represents the due date of a Task
public class DueDate {

    private GregorianCalendar dueDate;

    // MODIFIES: this
    // EFFECTS: creates a DueDate with deadline at end of day today (i.e., today at 11:59 PM)
    public DueDate() {
        dueDate = new GregorianCalendar();
        dueDate.set(GregorianCalendar.HOUR_OF_DAY, 23);
        dueDate.set(GregorianCalendar.MINUTE, 59);
        dueDate.set(GregorianCalendar.SECOND, 0);
    }

    // REQUIRES: date != null
    // MODIFIES: this
    // EFFECTS: creates a DueDate with deadline of the given date
    public DueDate(Date date) {
        try {
            this.dueDate = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate(),
                    date.getHours(), date.getMinutes(), date.getSeconds());
        } catch (NullPointerException n) {
            throw new NullArgumentException("Date is null.");
        }
    }

    // REQUIRES: date != null
    // MODIFIES: this
    // EFFECTS: changes the due date to the given date
    public void setDueDate(Date date) {
        try {
            this.dueDate = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate(),
                    date.getHours(), date.getMinutes(), date.getSeconds());
        } catch (NullPointerException n) {
            throw new NullArgumentException("Date is null.");
        }
    }

    // REQUIRES: 0 <= hh <= 23 && 0 <= mm <= 59
    // MODIFIES: this
    // EFFECTS: changes the due time to hh:mm leaving the month, day and year the same
    public void setDueTime(int hh, int mm) {
        if (hh < 0 || hh > 23 || mm < 0 || mm > 59) {
            throw new InvalidTimeException("Invalid time.");
        }
        dueDate.set(GregorianCalendar.HOUR_OF_DAY, hh);
        dueDate.set(GregorianCalendar.MINUTE, mm);
    }

    // MODIFIES: this
    // EFFECTS: postpones the due date by one day (leaving the time the same as
    //     in the original due date) based on the rules of the Gregorian calendar.
    public void postponeOneDay() {
        dueDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
    }

    // MODIFIES: this
    // EFFECTS: postpones the due date by 7 days
    //     (leaving the time the same as in the original due date)
    //     based on the rules of the Gregorian calendar.
    public void postponeOneWeek() {
        dueDate.add(GregorianCalendar.WEEK_OF_MONTH, 1);
    }

    // EFFECTS: returns the due date
    public Date getDate() {
        return new Date(dueDate.get(GregorianCalendar.YEAR) - 1900, dueDate.get(GregorianCalendar.MONTH),
                dueDate.get(GregorianCalendar.DAY_OF_MONTH), dueDate.get(GregorianCalendar.HOUR_OF_DAY),
                dueDate.get(GregorianCalendar.MINUTE), dueDate.get(GregorianCalendar.SECOND));
    }

    //EFFECTS: returns the due date in GregorianCalendar class
    public GregorianCalendar getGregorianDate() {
        return dueDate;
    }

    // EFFECTS: returns true if due date (and due time) is passed
    public boolean isOverdue() {
        GregorianCalendar currDate = new GregorianCalendar();
        return (currDate.compareTo(dueDate) > 0);
    }

    // EFFECTS: returns true if due date is at any time today, and false otherwise
    public boolean isDueToday() {
        GregorianCalendar currDate = new GregorianCalendar();
        currDate.set(GregorianCalendar.HOUR_OF_DAY, dueDate.get(GregorianCalendar.HOUR_OF_DAY));
        currDate.set(GregorianCalendar.MINUTE, dueDate.get(GregorianCalendar.MINUTE));
        currDate.set(GregorianCalendar.SECOND, dueDate.get(GregorianCalendar.SECOND));
        currDate.set(GregorianCalendar.MILLISECOND, dueDate.get(GregorianCalendar.MILLISECOND));
        return (currDate.compareTo(dueDate) == 0);
    }

    // EFFECTS: returns true if due date is at any time tomorrow, and false otherwise
    public boolean isDueTomorrow() {
        GregorianCalendar tmr = new GregorianCalendar(); //initialized tmr as currDate
        tmr.add(GregorianCalendar.DAY_OF_MONTH, 1);
        tmr.set(GregorianCalendar.HOUR_OF_DAY, dueDate.get(GregorianCalendar.HOUR_OF_DAY));
        tmr.set(GregorianCalendar.MINUTE, dueDate.get(GregorianCalendar.MINUTE));
        tmr.set(GregorianCalendar.SECOND, dueDate.get(GregorianCalendar.SECOND));
        tmr.set(GregorianCalendar.MILLISECOND, dueDate.get(GregorianCalendar.MILLISECOND));
        return (tmr.compareTo(dueDate) == 0);
    }

    // EFFECTS: returns true if due date is within the next seven days, irrespective of time of the day,
    // and false otherwise
    // Example, assume it is 8:00 AM on a Monday
    // then any task with due date between 00:00 AM today (Monday) and 11:59PM the following Sunday is due within a week
    public boolean isDueWithinAWeek() {
        GregorianCalendar currDate = new GregorianCalendar();
        currDate.set(GregorianCalendar.HOUR_OF_DAY, 0);
        currDate.set(GregorianCalendar.MINUTE, 0);
        currDate.set(GregorianCalendar.SECOND, 0);
        currDate.set(GregorianCalendar.MILLISECOND, 0);
        GregorianCalendar currDateAdd6 = new GregorianCalendar();
        currDateAdd6.add(GregorianCalendar.DAY_OF_MONTH, 6);
        currDateAdd6.set(GregorianCalendar.HOUR_OF_DAY, 23);
        currDateAdd6.set(GregorianCalendar.MINUTE, 59);
        currDateAdd6.set(GregorianCalendar.SECOND, 59);
        currDateAdd6.set(GregorianCalendar.MILLISECOND, 999);
        return (dueDate.compareTo(currDate) >= 0 && dueDate.compareTo(currDateAdd6) <= 0);
    }

    // EFFECTS: returns a string representation of due date in the following format
    //     day-of-week month day year hour:minute
    //  example: Sun Jan 25 2019 10:30 AM
    @Override
    public String toString() {
        String format = new SimpleDateFormat("EEE MMM d yyyy hh:mm a", Locale.CANADA).format(this.getDate().getTime());
        return format;
    }
}