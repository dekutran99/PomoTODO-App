package parsers;

import model.DueDate;
import model.Task;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;

public class AdvancedTagParser extends TagParser {

    private static final String[] DAYS_OF_WEEK = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday",
            "saturday"};
    private ArrayList<String> daysOfWeek = new ArrayList<String>();
    private Hashtable<String, Integer> mapDaysOfWeek = new Hashtable<String, Integer>();

    public AdvancedTagParser() {
        for (int i = 0; i < DAYS_OF_WEEK.length; i++) {
            daysOfWeek.add(DAYS_OF_WEEK[i]);
            mapDaysOfWeek.put(DAYS_OF_WEEK[i], i + 1);
        }
    }

    public ArrayList<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public Hashtable<String, Integer> getMapDaysOfWeek() {
        return mapDaysOfWeek;
    }

    @Override
    protected void parseTag(String tag, Task task) {
        if (tag.toLowerCase().equals("important") || tag.toLowerCase().equals("urgent")) {
            parsePriority(tag, task);
        } else if (tag.toLowerCase().equals("today") || tag.toLowerCase().equals("tomorrow")
                || tag.toLowerCase().contains("next ")) {
            parseDate(tag, task);
        } else if (tag.toLowerCase().equals("to do") || tag.toLowerCase().equals("up next")
                || tag.toLowerCase().equals("in progress") || tag.toLowerCase().equals("done")) {
            parseStatus(tag, task);
        } else if (!tag.equals("")) {
            task.addTag(tag);
        }
    }

    @Override
    protected void parseDate(String date, Task task) {
        DueDate dueDate = new DueDate();
        if (date.toLowerCase().equals("today")) {
            if (task.getDueDate() == null) {
                task.setDueDate(dueDate);
            } else if (!task.getDueDate().isDueToday()) {
                task.addTag(date);
            }
        } else if (date.toLowerCase().equals("tomorrow")) {
            if (task.getDueDate() == null) {
                dueDate.postponeOneDay();
                task.setDueDate(dueDate);
            } else if (!task.getDueDate().isDueTomorrow()) {
                task.addTag(date);
            }
        } else if (date.toLowerCase().contains("next")) {
            parseDayOfWeek(date, task);
        }
    }

    protected void parseDayOfWeek(String date, Task task) {
        String[] splitStrDate = date.split(" ");
        String dayOfWeek = splitStrDate[1].trim();
        Date currDate = new Date();
        DueDate dueDate = new DueDate(currDate);
        if (dayOfWeek.contains(dayOfWeek)) {
            if (task.getDueDate() == null) {
                while (dueDate.getGregorianDate().get(GregorianCalendar.DAY_OF_WEEK)
                        != mapDaysOfWeek.get(dayOfWeek.toLowerCase())) {
                    dueDate.postponeOneDay();
                }
                task.setDueDate(dueDate);
            }
            else if (!(task.getDueDate().getGregorianDate().get(GregorianCalendar.DAY_OF_WEEK)
                    == mapDaysOfWeek.get(dayOfWeek.toLowerCase()))) {
                task.addTag(date);
            }
        }
    }

    public static void main(String[] args) {
        AdvancedTagParser atp = new AdvancedTagParser();
        System.out.println(atp.getDaysOfWeek());
        System.out.println(atp.getMapDaysOfWeek());
    }
}
