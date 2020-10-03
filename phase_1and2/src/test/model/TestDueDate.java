package model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import model.exceptions.InvalidTimeException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDueDate {
    private DueDate dueDate1;
    private DueDate dueDate2;

    @BeforeEach
    public void runBefore() {
        dueDate1 = new DueDate();
        Date date = new Date(2018 - 1900, 11, 31);
        dueDate2 = new DueDate(date);
    }

    @Test
    void testConstructor() {
        Date currDate = new Date();
        currDate.setHours(23);
        currDate.setMinutes(59);
        currDate.setSeconds(0);
        assertEquals(currDate.getYear(), dueDate1.getDate().getYear());
        assertEquals(currDate.getMonth(), dueDate1.getDate().getMonth());
        assertEquals(currDate.getDate(), dueDate1.getDate().getDate());
        assertEquals(currDate.getHours(), dueDate1.getDate().getHours());
        assertEquals(currDate.getMinutes(), dueDate1.getDate().getMinutes());
        assertEquals(currDate.getSeconds(), dueDate1.getDate().getSeconds());
        assertEquals("Mon Dec 31 2018 12:00 AM", dueDate2.toString());
    }

    @Test
    void testSetters() {
        Date date1 = new Date(2023 - 1900, 5, 28);
        Date date2 = new Date(2022 - 1900, 2, 28, 11, 11);
        dueDate1.setDueDate(date1);
        dueDate2.setDueDate(date2);
        assertEquals("Wed Jun 28 2023 12:00 AM", dueDate1.toString());
        assertEquals("Mon Mar 28 2022 11:11 AM", dueDate2.toString());
        dueDate1.setDueTime(0, 2);
        dueDate2.setDueTime(12, 1);
        assertEquals("Wed Jun 28 2023 12:02 AM", dueDate1.toString());
        assertEquals("Mon Mar 28 2022 12:01 PM", dueDate2.toString());
    }

    @Test
    void testPostPones() {
        dueDate1.postponeOneDay();
        dueDate2.postponeOneDay();
        assertEquals("Sat Feb 16 2019 11:59 PM", dueDate1.toString());
        assertEquals("Tue Jan 1 2019 12:00 AM", dueDate2.toString());
        dueDate1.postponeOneWeek();
        dueDate2.postponeOneWeek();
        assertEquals("Sat Feb 23 2019 11:59 PM", dueDate1.toString());
        assertEquals("Tue Jan 8 2019 12:00 AM", dueDate2.toString());
    }

    @Test
    void testIsDues() {
        assertFalse(dueDate1.isOverdue());
        assertTrue(dueDate2.isOverdue());
        assertTrue(dueDate1.isDueToday());
        assertFalse(dueDate2.isDueToday());
        assertFalse(dueDate1.isDueTomorrow());
        assertFalse(dueDate2.isDueTomorrow());
        Date tmr = new Date(2019 - 1900, 2, 8);
        dueDate2.setDueDate(tmr);
        assertFalse(dueDate2.isDueTomorrow());
        assertFalse(dueDate2.isDueWithinAWeek());
        Date date = new Date(2019 - 1900, 1, 16);
        dueDate2.setDueDate(date);
        assertTrue(dueDate2.isDueTomorrow());
        assertTrue(dueDate2.isDueWithinAWeek());
        date = new Date(2020 - 1900, 11, 31);
        dueDate2.setDueDate(date);
        assertFalse(dueDate2.isDueWithinAWeek());
    }

    @Test
    void testNullDate() {
        Date date = null;
        try {
            DueDate dueDate = new DueDate(date);
            fail();
        } catch (NullArgumentException n) {
            System.out.println("Great!");
        }
        try {
            dueDate1.setDueDate(date);
            fail();
        } catch (NullArgumentException n) {
            System.out.println("Great!");
        }
        date = new Date();
        try {
            DueDate dueDate = new DueDate(date);
        } catch (NullArgumentException n) {
            fail("Not expecting exception");
        }
        try {
            dueDate1.setDueDate(date);
        } catch (NullArgumentException n) {
            fail("Not expecting exception");
        }
    }

    @Test
    void testInvalidTime() {
        try {
            dueDate1.setDueTime(-1, 0);
            fail();
        } catch (InvalidTimeException i) {}

        try {
            dueDate1.setDueTime(24,0);
            fail();
        } catch (InvalidTimeException i) {}

        try {
            dueDate1.setDueTime(0, -1);
            fail();
        } catch (InvalidTimeException i) {}

        try {
            dueDate1.setDueTime(0, 60);
            fail();
        } catch (InvalidTimeException i) {}

        try {
            dueDate1.setDueTime(0, 0);
        } catch (InvalidTimeException i) {
            fail();
        }
    }
}
