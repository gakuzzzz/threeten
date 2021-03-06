/*
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time.calendrical;

import static javax.time.DayOfWeek.FRIDAY;
import static javax.time.DayOfWeek.MONDAY;
import static javax.time.DayOfWeek.SATURDAY;
import static javax.time.DayOfWeek.SUNDAY;
import static javax.time.DayOfWeek.THURSDAY;
import static javax.time.DayOfWeek.TUESDAY;
import static javax.time.DayOfWeek.WEDNESDAY;
import static javax.time.calendrical.ChronoField.DAY_OF_WEEK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import javax.time.DayOfWeek;
import javax.time.LocalDate;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
@Test(groups={"tck"})
public class TCKISOWeeks {

    public void test_enum() {
        assertTrue(ISOWeeks.WEEK_OF_WEEK_BASED_YEAR instanceof Enum);
        assertTrue(ISOWeeks.WEEK_BASED_YEAR instanceof Enum);
        assertTrue(ISOWeeks.WEEK_BASED_YEARS instanceof Enum);
    }

    @DataProvider(name="week")
    Object[][] data_week() {
        return new Object[][] {
                {LocalDate.of(1969, 12, 29), MONDAY, 1, 1970},
                {LocalDate.of(2012, 12, 23), SUNDAY, 51, 2012},
                {LocalDate.of(2012, 12, 24), MONDAY, 52, 2012},
                {LocalDate.of(2012, 12, 27), THURSDAY, 52, 2012},
                {LocalDate.of(2012, 12, 28), FRIDAY, 52, 2012},
                {LocalDate.of(2012, 12, 29), SATURDAY, 52, 2012},
                {LocalDate.of(2012, 12, 30), SUNDAY, 52, 2012},
                {LocalDate.of(2012, 12, 31), MONDAY, 1, 2013},
                {LocalDate.of(2013, 1, 1), TUESDAY, 1, 2013},
                {LocalDate.of(2013, 1, 2), WEDNESDAY, 1, 2013},
                {LocalDate.of(2013, 1, 6), SUNDAY, 1, 2013},
                {LocalDate.of(2013, 1, 7), MONDAY, 2, 2013},
        };
    }

    //-----------------------------------------------------------------------
    // WEEK_OF_WEEK_BASED_YEAR
    //-----------------------------------------------------------------------
    @Test(dataProvider="week")
    public void test_WOWBY(LocalDate date, DayOfWeek dow, int week, int wby) {
        assertEquals(date.getDayOfWeek(), dow);
        assertEquals(ISOWeeks.WEEK_OF_WEEK_BASED_YEAR.doGet(date), week);
        assertEquals(date.get(ISOWeeks.WEEK_OF_WEEK_BASED_YEAR), week);
    }

    //-----------------------------------------------------------------------
    // WEEK_BASED_YEAR
    //-----------------------------------------------------------------------
    @Test(dataProvider="week")
    public void test_WBY(LocalDate date, DayOfWeek dow, int week, int wby) {
        assertEquals(date.getDayOfWeek(), dow);
        assertEquals(ISOWeeks.WEEK_BASED_YEAR.doGet(date), wby);
        assertEquals(date.get(ISOWeeks.WEEK_BASED_YEAR), wby);
    }

    //-----------------------------------------------------------------------
    // builder
    //-----------------------------------------------------------------------
    @Test(dataProvider="week")
    public void test_builder(LocalDate date, DayOfWeek dow, int week, int wby) {
        DateTimeBuilder builder = new DateTimeBuilder();
        builder.addFieldValue(ISOWeeks.WEEK_BASED_YEAR, wby);
        builder.addFieldValue(ISOWeeks.WEEK_OF_WEEK_BASED_YEAR, week);
        builder.addFieldValue(DAY_OF_WEEK, dow.getValue());
        builder.resolve();
        assertEquals(builder.build(LocalDate.class), date);
    }

    //-----------------------------------------------------------------------
    public void test_loop() {
        // loop round at least one 400 year cycle, including before 1970
        LocalDate date = LocalDate.of(1960, 1, 5);  // Tuseday of week 1 1960
        int year = 1960;
        int wby = 1960;
        int weekLen = 52;
        int week = 1;
        while (date.getYear() < 2400) {
            DayOfWeek loopDow = date.getDayOfWeek();
            if (date.getYear() != year) {
                year = date.getYear();
            }
            if (loopDow == MONDAY) {
                week++;
                if ((week == 53 && weekLen == 52) || week == 54) {
                    week = 1;
                    LocalDate firstDayOfWeekBasedYear = date.plusDays(14).withDayOfYear(1);
                    DayOfWeek firstDay = firstDayOfWeekBasedYear.getDayOfWeek();
                    weekLen = (firstDay == THURSDAY || (firstDay == WEDNESDAY && firstDayOfWeekBasedYear.isLeapYear()) ? 53 : 52);
                    wby++;
                }
            }
            assertEquals(ISOWeeks.WEEK_OF_WEEK_BASED_YEAR.doRange(date), DateTimeValueRange.of(1, weekLen), "Failed on " + date + " " + date.getDayOfWeek());
            assertEquals(ISOWeeks.WEEK_OF_WEEK_BASED_YEAR.doGet(date), week, "Failed on " + date + " " + date.getDayOfWeek());
            assertEquals(date.get(ISOWeeks.WEEK_OF_WEEK_BASED_YEAR), week, "Failed on " + date + " " + date.getDayOfWeek());
            assertEquals(ISOWeeks.WEEK_BASED_YEAR.doGet(date), wby, "Failed on " + date + " " + date.getDayOfWeek());
            assertEquals(date.get(ISOWeeks.WEEK_BASED_YEAR), wby, "Failed on " + date + " " + date.getDayOfWeek());
            date = date.plusDays(1);
        }
    }

    // TODO: more tests
}
