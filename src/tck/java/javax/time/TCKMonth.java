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
package javax.time;

import static javax.time.Month.DECEMBER;
import static javax.time.Month.JANUARY;
import static javax.time.Month.JUNE;
import static javax.time.calendrical.ChronoField.MONTH_OF_YEAR;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.time.calendrical.ChronoField;
import javax.time.calendrical.DateTimeAccessor;
import javax.time.calendrical.DateTimeAccessor.Query;
import javax.time.calendrical.DateTimeField;
import javax.time.calendrical.JulianDayField;
import javax.time.chrono.ISOChrono;
import javax.time.format.TextStyle;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test Month.
 */
@Test
public class TCKMonth extends AbstractDateTimeTest {

    private static final int MAX_LENGTH = 12;

    //-----------------------------------------------------------------------
    @Override
    protected List<DateTimeAccessor> samples() {
        DateTimeAccessor[] array = {JANUARY, JUNE, DECEMBER, };
        return Arrays.asList(array);
    }

    @Override
    protected List<DateTimeField> validFields() {
        DateTimeField[] array = {
            MONTH_OF_YEAR,
        };
        return Arrays.asList(array);
    }

    @Override
    protected List<DateTimeField> invalidFields() {
        List<DateTimeField> list = new ArrayList<>(Arrays.<DateTimeField>asList(ChronoField.values()));
        list.removeAll(validFields());
        list.add(JulianDayField.JULIAN_DAY);
        list.add(JulianDayField.MODIFIED_JULIAN_DAY);
        list.add(JulianDayField.RATA_DIE);
        return list;
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_factory_int_singleton() {
        for (int i = 1; i <= MAX_LENGTH; i++) {
            Month test = Month.of(i);
            assertEquals(test.getValue(), i);
        }
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void test_factory_int_tooLow() {
        Month.of(0);
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void test_factory_int_tooHigh() {
        Month.of(13);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_factory_CalendricalObject() {
        assertEquals(Month.from(LocalDate.of(2011, 6, 6)), JUNE);
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void test_factory_CalendricalObject_invalid_noDerive() {
        Month.from(LocalTime.of(12, 30));
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_factory_CalendricalObject_null() {
        Month.from((DateTimeAccessor) null);
    }

    //-----------------------------------------------------------------------
    // query(Query)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_query_chrono() {
        assertEquals(Month.JUNE.query(Query.CHRONO), ISOChrono.INSTANCE);
    }

    @Test(groups={"tck"})
    public void test_query_zone() {
        assertEquals(Month.JUNE.query(Query.ZONE_ID), null);
    }

    @Test(groups={"tck"})
    public void test_query_timePrecision() {
        assertEquals(Month.JUNE.query(Query.TIME_PRECISION), null);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_query_null() {
        Month.JUNE.query(null);
    }

    //-----------------------------------------------------------------------
    // getText()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_getText() {
        assertEquals(Month.JANUARY.getText(TextStyle.SHORT, Locale.US), "Jan");
    }

    @Test(expectedExceptions = NullPointerException.class, groups={"tck"})
    public void test_getText_nullStyle() {
        Month.JANUARY.getText(null, Locale.US);
    }

    @Test(expectedExceptions = NullPointerException.class, groups={"tck"})
    public void test_getText_nullLocale() {
        Month.JANUARY.getText(TextStyle.FULL, null);
    }

    //-----------------------------------------------------------------------
    // plus(long), plus(long,unit)
    //-----------------------------------------------------------------------
    @DataProvider(name="plus")
    Object[][] data_plus() {
        return new Object[][] {
            {1, -13, 12},
            {1, -12, 1},
            {1, -11, 2},
            {1, -10, 3},
            {1, -9, 4},
            {1, -8, 5},
            {1, -7, 6},
            {1, -6, 7},
            {1, -5, 8},
            {1, -4, 9},
            {1, -3, 10},
            {1, -2, 11},
            {1, -1, 12},
            {1, 0, 1},
            {1, 1, 2},
            {1, 2, 3},
            {1, 3, 4},
            {1, 4, 5},
            {1, 5, 6},
            {1, 6, 7},
            {1, 7, 8},
            {1, 8, 9},
            {1, 9, 10},
            {1, 10, 11},
            {1, 11, 12},
            {1, 12, 1},
            {1, 13, 2},

            {1, 1, 2},
            {2, 1, 3},
            {3, 1, 4},
            {4, 1, 5},
            {5, 1, 6},
            {6, 1, 7},
            {7, 1, 8},
            {8, 1, 9},
            {9, 1, 10},
            {10, 1, 11},
            {11, 1, 12},
            {12, 1, 1},

            {1, -1, 12},
            {2, -1, 1},
            {3, -1, 2},
            {4, -1, 3},
            {5, -1, 4},
            {6, -1, 5},
            {7, -1, 6},
            {8, -1, 7},
            {9, -1, 8},
            {10, -1, 9},
            {11, -1, 10},
            {12, -1, 11},
        };
    }

    @Test(dataProvider="plus", groups={"tck"})
    public void test_plus_long(int base, long amount, int expected) {
        assertEquals(Month.of(base).plus(amount), Month.of(expected));
    }

    //-----------------------------------------------------------------------
    // minus(long), minus(long,unit)
    //-----------------------------------------------------------------------
    @DataProvider(name="minus")
    Object[][] data_minus() {
        return new Object[][] {
            {1, -13, 2},
            {1, -12, 1},
            {1, -11, 12},
            {1, -10, 11},
            {1, -9, 10},
            {1, -8, 9},
            {1, -7, 8},
            {1, -6, 7},
            {1, -5, 6},
            {1, -4, 5},
            {1, -3, 4},
            {1, -2, 3},
            {1, -1, 2},
            {1, 0, 1},
            {1, 1, 12},
            {1, 2, 11},
            {1, 3, 10},
            {1, 4, 9},
            {1, 5, 8},
            {1, 6, 7},
            {1, 7, 6},
            {1, 8, 5},
            {1, 9, 4},
            {1, 10, 3},
            {1, 11, 2},
            {1, 12, 1},
            {1, 13, 12},
        };
    }

    @Test(dataProvider="minus", groups={"tck"})
    public void test_minus_long(int base, long amount, int expected) {
        assertEquals(Month.of(base).minus(amount), Month.of(expected));
    }

    //-----------------------------------------------------------------------
    // length(boolean)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_length_boolean_notLeapYear() {
        assertEquals(Month.JANUARY.length(false), 31);
        assertEquals(Month.FEBRUARY.length(false), 28);
        assertEquals(Month.MARCH.length(false), 31);
        assertEquals(Month.APRIL.length(false), 30);
        assertEquals(Month.MAY.length(false), 31);
        assertEquals(Month.JUNE.length(false), 30);
        assertEquals(Month.JULY.length(false), 31);
        assertEquals(Month.AUGUST.length(false), 31);
        assertEquals(Month.SEPTEMBER.length(false), 30);
        assertEquals(Month.OCTOBER.length(false), 31);
        assertEquals(Month.NOVEMBER.length(false), 30);
        assertEquals(Month.DECEMBER.length(false), 31);
    }

    @Test(groups={"tck"})
    public void test_length_boolean_leapYear() {
        assertEquals(Month.JANUARY.length(true), 31);
        assertEquals(Month.FEBRUARY.length(true), 29);
        assertEquals(Month.MARCH.length(true), 31);
        assertEquals(Month.APRIL.length(true), 30);
        assertEquals(Month.MAY.length(true), 31);
        assertEquals(Month.JUNE.length(true), 30);
        assertEquals(Month.JULY.length(true), 31);
        assertEquals(Month.AUGUST.length(true), 31);
        assertEquals(Month.SEPTEMBER.length(true), 30);
        assertEquals(Month.OCTOBER.length(true), 31);
        assertEquals(Month.NOVEMBER.length(true), 30);
        assertEquals(Month.DECEMBER.length(true), 31);
    }

    //-----------------------------------------------------------------------
    // minLength()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minLength() {
        assertEquals(Month.JANUARY.minLength(), 31);
        assertEquals(Month.FEBRUARY.minLength(), 28);
        assertEquals(Month.MARCH.minLength(), 31);
        assertEquals(Month.APRIL.minLength(), 30);
        assertEquals(Month.MAY.minLength(), 31);
        assertEquals(Month.JUNE.minLength(), 30);
        assertEquals(Month.JULY.minLength(), 31);
        assertEquals(Month.AUGUST.minLength(), 31);
        assertEquals(Month.SEPTEMBER.minLength(), 30);
        assertEquals(Month.OCTOBER.minLength(), 31);
        assertEquals(Month.NOVEMBER.minLength(), 30);
        assertEquals(Month.DECEMBER.minLength(), 31);
    }

    //-----------------------------------------------------------------------
    // maxLength()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_maxLength() {
        assertEquals(Month.JANUARY.maxLength(), 31);
        assertEquals(Month.FEBRUARY.maxLength(), 29);
        assertEquals(Month.MARCH.maxLength(), 31);
        assertEquals(Month.APRIL.maxLength(), 30);
        assertEquals(Month.MAY.maxLength(), 31);
        assertEquals(Month.JUNE.maxLength(), 30);
        assertEquals(Month.JULY.maxLength(), 31);
        assertEquals(Month.AUGUST.maxLength(), 31);
        assertEquals(Month.SEPTEMBER.maxLength(), 30);
        assertEquals(Month.OCTOBER.maxLength(), 31);
        assertEquals(Month.NOVEMBER.maxLength(), 30);
        assertEquals(Month.DECEMBER.maxLength(), 31);
    }

    //-----------------------------------------------------------------------
    // firstDayOfYear(boolean)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_firstDayOfYear_notLeapYear() {
        assertEquals(Month.JANUARY.firstDayOfYear(false), 1);
        assertEquals(Month.FEBRUARY.firstDayOfYear(false), 1 + 31);
        assertEquals(Month.MARCH.firstDayOfYear(false), 1 + 31 + 28);
        assertEquals(Month.APRIL.firstDayOfYear(false), 1 + 31 + 28 + 31);
        assertEquals(Month.MAY.firstDayOfYear(false), 1 + 31 + 28 + 31 + 30);
        assertEquals(Month.JUNE.firstDayOfYear(false), 1 + 31 + 28 + 31 + 30 + 31);
        assertEquals(Month.JULY.firstDayOfYear(false), 1 + 31 + 28 + 31 + 30 + 31 + 30);
        assertEquals(Month.AUGUST.firstDayOfYear(false), 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31);
        assertEquals(Month.SEPTEMBER.firstDayOfYear(false), 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31);
        assertEquals(Month.OCTOBER.firstDayOfYear(false), 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30);
        assertEquals(Month.NOVEMBER.firstDayOfYear(false), 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31);
        assertEquals(Month.DECEMBER.firstDayOfYear(false), 1 + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30);
    }

    @Test(groups={"tck"})
    public void test_firstDayOfYear_leapYear() {
        assertEquals(Month.JANUARY.firstDayOfYear(true), 1);
        assertEquals(Month.FEBRUARY.firstDayOfYear(true), 1 + 31);
        assertEquals(Month.MARCH.firstDayOfYear(true), 1 + 31 + 29);
        assertEquals(Month.APRIL.firstDayOfYear(true), 1 + 31 + 29 + 31);
        assertEquals(Month.MAY.firstDayOfYear(true), 1 + 31 + 29 + 31 + 30);
        assertEquals(Month.JUNE.firstDayOfYear(true), 1 + 31 + 29 + 31 + 30 + 31);
        assertEquals(Month.JULY.firstDayOfYear(true), 1 + 31 + 29 + 31 + 30 + 31 + 30);
        assertEquals(Month.AUGUST.firstDayOfYear(true), 1 + 31 + 29 + 31 + 30 + 31 + 30 + 31);
        assertEquals(Month.SEPTEMBER.firstDayOfYear(true), 1 + 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31);
        assertEquals(Month.OCTOBER.firstDayOfYear(true), 1 + 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 30);
        assertEquals(Month.NOVEMBER.firstDayOfYear(true), 1 + 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31);
        assertEquals(Month.DECEMBER.firstDayOfYear(true), 1 + 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30);
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_toString() {
        assertEquals(Month.JANUARY.toString(), "JANUARY");
        assertEquals(Month.FEBRUARY.toString(), "FEBRUARY");
        assertEquals(Month.MARCH.toString(), "MARCH");
        assertEquals(Month.APRIL.toString(), "APRIL");
        assertEquals(Month.MAY.toString(), "MAY");
        assertEquals(Month.JUNE.toString(), "JUNE");
        assertEquals(Month.JULY.toString(), "JULY");
        assertEquals(Month.AUGUST.toString(), "AUGUST");
        assertEquals(Month.SEPTEMBER.toString(), "SEPTEMBER");
        assertEquals(Month.OCTOBER.toString(), "OCTOBER");
        assertEquals(Month.NOVEMBER.toString(), "NOVEMBER");
        assertEquals(Month.DECEMBER.toString(), "DECEMBER");
    }

    //-----------------------------------------------------------------------
    // generated methods
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_enum() {
        assertEquals(Month.valueOf("JANUARY"), Month.JANUARY);
        assertEquals(Month.values()[0], Month.JANUARY);
    }

}
