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
import static javax.time.calendrical.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static javax.time.calendrical.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static javax.time.calendrical.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static javax.time.calendrical.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static javax.time.calendrical.ChronoField.AMPM_OF_DAY;
import static javax.time.calendrical.ChronoField.CLOCK_HOUR_OF_AMPM;
import static javax.time.calendrical.ChronoField.CLOCK_HOUR_OF_DAY;
import static javax.time.calendrical.ChronoField.DAY_OF_MONTH;
import static javax.time.calendrical.ChronoField.DAY_OF_WEEK;
import static javax.time.calendrical.ChronoField.DAY_OF_YEAR;
import static javax.time.calendrical.ChronoField.EPOCH_DAY;
import static javax.time.calendrical.ChronoField.EPOCH_MONTH;
import static javax.time.calendrical.ChronoField.ERA;
import static javax.time.calendrical.ChronoField.HOUR_OF_AMPM;
import static javax.time.calendrical.ChronoField.HOUR_OF_DAY;
import static javax.time.calendrical.ChronoField.INSTANT_SECONDS;
import static javax.time.calendrical.ChronoField.MICRO_OF_DAY;
import static javax.time.calendrical.ChronoField.MICRO_OF_SECOND;
import static javax.time.calendrical.ChronoField.MILLI_OF_DAY;
import static javax.time.calendrical.ChronoField.MILLI_OF_SECOND;
import static javax.time.calendrical.ChronoField.MINUTE_OF_DAY;
import static javax.time.calendrical.ChronoField.MINUTE_OF_HOUR;
import static javax.time.calendrical.ChronoField.MONTH_OF_YEAR;
import static javax.time.calendrical.ChronoField.NANO_OF_DAY;
import static javax.time.calendrical.ChronoField.NANO_OF_SECOND;
import static javax.time.calendrical.ChronoField.OFFSET_SECONDS;
import static javax.time.calendrical.ChronoField.SECOND_OF_DAY;
import static javax.time.calendrical.ChronoField.SECOND_OF_MINUTE;
import static javax.time.calendrical.ChronoField.YEAR;
import static javax.time.calendrical.ChronoField.YEAR_OF_ERA;
import static javax.time.calendrical.ChronoUnit.NANOS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.time.calendrical.ChronoField;
import javax.time.calendrical.ChronoUnit;
import javax.time.calendrical.DateTime;
import javax.time.calendrical.DateTime.WithAdjuster;
import javax.time.calendrical.DateTimeAccessor;
import javax.time.calendrical.DateTimeAccessor.Query;
import javax.time.calendrical.DateTimeField;
import javax.time.calendrical.JulianDayField;
import javax.time.calendrical.MockFieldNoValue;
import javax.time.chrono.ISOChrono;
import javax.time.format.DateTimeFormatter;
import javax.time.format.DateTimeFormatters;
import javax.time.format.DateTimeParseException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test OffsetDateTime.
 */
@Test
public class TCKOffsetDateTime extends AbstractDateTimeTest {

    private static final ZoneId ZONE_PARIS = ZoneId.of("Europe/Paris");
    private static final ZoneId ZONE_GAZA = ZoneId.of("Asia/Gaza");
    private static final ZoneOffset OFFSET_PONE = ZoneOffset.ofHours(1);
    private static final ZoneOffset OFFSET_PTWO = ZoneOffset.ofHours(2);
    private static final ZoneOffset OFFSET_MONE = ZoneOffset.ofHours(-1);
    private static final ZoneOffset OFFSET_MTWO = ZoneOffset.ofHours(-2);
    private OffsetDateTime TEST_2008_6_30_11_30_59_000000500;

    @BeforeMethod(groups={"tck","implementation"})
    public void setUp() {
        TEST_2008_6_30_11_30_59_000000500 = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 500, OFFSET_PONE);
    }

    //-----------------------------------------------------------------------
    @Override
    protected List<DateTimeAccessor> samples() {
        DateTimeAccessor[] array = {TEST_2008_6_30_11_30_59_000000500, };
        return Arrays.asList(array);
    }

    @Override
    protected List<DateTimeField> validFields() {
        DateTimeField[] array = {
            NANO_OF_SECOND,
            NANO_OF_DAY,
            MICRO_OF_SECOND,
            MICRO_OF_DAY,
            MILLI_OF_SECOND,
            MILLI_OF_DAY,
            SECOND_OF_MINUTE,
            SECOND_OF_DAY,
            MINUTE_OF_HOUR,
            MINUTE_OF_DAY,
            CLOCK_HOUR_OF_AMPM,
            HOUR_OF_AMPM,
            CLOCK_HOUR_OF_DAY,
            HOUR_OF_DAY,
            AMPM_OF_DAY,
            DAY_OF_WEEK,
            ALIGNED_DAY_OF_WEEK_IN_MONTH,
            ALIGNED_DAY_OF_WEEK_IN_YEAR,
            DAY_OF_MONTH,
            DAY_OF_YEAR,
            EPOCH_DAY,
            ALIGNED_WEEK_OF_MONTH,
            ALIGNED_WEEK_OF_YEAR,
            MONTH_OF_YEAR,
            EPOCH_MONTH,
            YEAR_OF_ERA,
            YEAR,
            ERA,
            OFFSET_SECONDS,
            INSTANT_SECONDS,
            JulianDayField.JULIAN_DAY,
            JulianDayField.MODIFIED_JULIAN_DAY,
            JulianDayField.RATA_DIE,
        };
        return Arrays.asList(array);
    }

    @Override
    protected List<DateTimeField> invalidFields() {
        List<DateTimeField> list = new ArrayList<>(Arrays.<DateTimeField>asList(ChronoField.values()));
        list.removeAll(validFields());
        return list;
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_serialization() throws ClassNotFoundException, IOException {
        assertSerializable(TEST_2008_6_30_11_30_59_000000500);
    }

    @Test(groups={"tck"})
    public void test_serialization_format() throws ClassNotFoundException, IOException {
        LocalDate date = LocalDate.of(2012, 9, 16);
        LocalTime time = LocalTime.of(22, 17, 59, 464 * 1000000);
        ZoneOffset offset = ZoneOffset.of("+01:00");
        assertEqualsSerialisedForm(OffsetDateTime.of(date, time, offset));
    }

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void now() {
        OffsetDateTime expected = OffsetDateTime.now(Clock.systemDefaultZone());
        OffsetDateTime test = OffsetDateTime.now();
        long diff = Math.abs(test.getTime().toNanoOfDay() - expected.getTime().toNanoOfDay());
        if (diff >= 100000000) {
            // may be date change
            expected = OffsetDateTime.now(Clock.systemDefaultZone());
            test = OffsetDateTime.now();
            diff = Math.abs(test.getTime().toNanoOfDay() - expected.getTime().toNanoOfDay());
        }
        assertTrue(diff < 100000000);  // less than 0.1 secs
    }

    //-----------------------------------------------------------------------
    // now(Clock)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void now_Clock_allSecsInDay_utc() {
        for (int i = 0; i < (2 * 24 * 60 * 60); i++) {
            Instant instant = Instant.ofEpochSecond(i).plusNanos(123456789L);
            Clock clock = Clock.fixed(instant, ZoneOffset.UTC);
            OffsetDateTime test = OffsetDateTime.now(clock);
            assertEquals(test.getYear(), 1970);
            assertEquals(test.getMonth(), Month.JANUARY);
            assertEquals(test.getDayOfMonth(), (i < 24 * 60 * 60 ? 1 : 2));
            assertEquals(test.getHour(), (i / (60 * 60)) % 24);
            assertEquals(test.getMinute(), (i / 60) % 60);
            assertEquals(test.getSecond(), i % 60);
            assertEquals(test.getNano(), 123456789);
            assertEquals(test.getOffset(), ZoneOffset.UTC);
        }
    }

    @Test(groups={"tck"})
    public void now_Clock_allSecsInDay_offset() {
        for (int i = 0; i < (2 * 24 * 60 * 60); i++) {
            Instant instant = Instant.ofEpochSecond(i).plusNanos(123456789L);
            Clock clock = Clock.fixed(instant.minusSeconds(OFFSET_PONE.getTotalSeconds()), OFFSET_PONE);
            OffsetDateTime test = OffsetDateTime.now(clock);
            assertEquals(test.getYear(), 1970);
            assertEquals(test.getMonth(), Month.JANUARY);
            assertEquals(test.getDayOfMonth(), (i < 24 * 60 * 60) ? 1 : 2);
            assertEquals(test.getHour(), (i / (60 * 60)) % 24);
            assertEquals(test.getMinute(), (i / 60) % 60);
            assertEquals(test.getSecond(), i % 60);
            assertEquals(test.getNano(), 123456789);
            assertEquals(test.getOffset(), OFFSET_PONE);
        }
    }

    @Test(groups={"tck"})
    public void now_Clock_allSecsInDay_beforeEpoch() {
        LocalTime expected = LocalTime.MIDNIGHT.plusNanos(123456789L);
        for (int i =-1; i >= -(24 * 60 * 60); i--) {
            Instant instant = Instant.ofEpochSecond(i).plusNanos(123456789L);
            Clock clock = Clock.fixed(instant, ZoneOffset.UTC);
            OffsetDateTime test = OffsetDateTime.now(clock);
            assertEquals(test.getYear(), 1969);
            assertEquals(test.getMonth(), Month.DECEMBER);
            assertEquals(test.getDayOfMonth(), 31);
            expected = expected.minusSeconds(1);
            assertEquals(test.getTime(), expected);
            assertEquals(test.getOffset(), ZoneOffset.UTC);
        }
    }

    @Test(groups={"tck"})
    public void now_Clock_offsets() {
        OffsetDateTime base = OffsetDateTime.of(1970, 1, 1, 12, 0, ZoneOffset.UTC);
        for (int i = -9; i < 15; i++) {
            ZoneOffset offset = ZoneOffset.ofHours(i);
            Clock clock = Clock.fixed(base.toInstant(), offset);
            OffsetDateTime test = OffsetDateTime.now(clock);
            assertEquals(test.getHour(), (12 + i) % 24);
            assertEquals(test.getMinute(), 0);
            assertEquals(test.getSecond(), 0);
            assertEquals(test.getNano(), 0);
            assertEquals(test.getOffset(), offset);
        }
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void now_Clock_nullZoneId() {
        OffsetDateTime.now((ZoneId) null);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void now_Clock_nullClock() {
        OffsetDateTime.now((Clock) null);
    }

    //-----------------------------------------------------------------------
    private void check(OffsetDateTime test, int y, int mo, int d, int h, int m, int s, int n, ZoneOffset offset) {
        assertEquals(test.getYear(), y);
        assertEquals(test.getMonth().getValue(), mo);
        assertEquals(test.getDayOfMonth(), d);
        assertEquals(test.getHour(), h);
        assertEquals(test.getMinute(), m);
        assertEquals(test.getSecond(), s);
        assertEquals(test.getNano(), n);
        assertEquals(test.getOffset(), offset);
    }

    //-----------------------------------------------------------------------
    // dateTime factories
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_of_intMonthIntHM() {
        OffsetDateTime test = OffsetDateTime.of(2008, Month.JUNE, 30, 11, 30, OFFSET_PONE);
        check(test, 2008, 6, 30, 11, 30, 0, 0, OFFSET_PONE);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_of_intMonthIntHMS() {
        OffsetDateTime test = OffsetDateTime.of(2008, Month.JUNE, 30, 11, 30, 10, OFFSET_PONE);
        check(test, 2008, 6, 30, 11, 30, 10, 0, OFFSET_PONE);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_of_intMonthIntHMSN() {
        OffsetDateTime test = OffsetDateTime.of(2008, Month.JUNE, 30, 11, 30, 10, 500, OFFSET_PONE);
        check(test, 2008, 6, 30, 11, 30, 10, 500, OFFSET_PONE);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_of_intsHM() {
        OffsetDateTime test = OffsetDateTime.of(2008, 6, 30, 11, 30, OFFSET_PONE);
        check(test, 2008, 6, 30, 11, 30, 0, 0, OFFSET_PONE);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_of_intsHMS() {
        OffsetDateTime test = OffsetDateTime.of(2008, 6, 30, 11, 30, 10, OFFSET_PONE);
        check(test, 2008, 6, 30, 11, 30, 10, 0, OFFSET_PONE);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_of_intsHMSN() {
        OffsetDateTime test = OffsetDateTime.of(2008, 6, 30, 11, 30, 10, 500, OFFSET_PONE);
        check(test, 2008, 6, 30, 11, 30, 10, 500, OFFSET_PONE);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_of_LocalDateLocalTimeZoneOffset() {
        LocalDate date = LocalDate.of(2008, 6, 30);
        LocalTime time = LocalTime.of(11, 30, 10, 500);
        OffsetDateTime test = OffsetDateTime.of(date, time, OFFSET_PONE);
        check(test, 2008, 6, 30, 11, 30, 10, 500, OFFSET_PONE);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_of_LocalDateLocalTimeZoneOffset_nullLocalDate() {
        LocalTime time = LocalTime.of(11, 30, 10, 500);
        OffsetDateTime.of((LocalDate) null, time, OFFSET_PONE);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_of_LocalDateLocalTimeZoneOffset_nullLocalTime() {
        LocalDate date = LocalDate.of(2008, 6, 30);
        OffsetDateTime.of(date, (LocalTime) null, OFFSET_PONE);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_of_LocalDateLocalTimeZoneOffset_nullOffset() {
        LocalDate date = LocalDate.of(2008, 6, 30);
        LocalTime time = LocalTime.of(11, 30, 10, 500);
        OffsetDateTime.of(date, time, (ZoneOffset) null);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_of_LocalDateTimeZoneOffset() {
        LocalDateTime dt = LocalDateTime.of(2008, 6, 30, 11, 30, 10, 500);
        OffsetDateTime test = OffsetDateTime.of(dt, OFFSET_PONE);
        check(test, 2008, 6, 30, 11, 30, 10, 500, OFFSET_PONE);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_of_LocalDateTimeZoneOffset_nullProvider() {
        OffsetDateTime.of((LocalDateTime) null, OFFSET_PONE);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_of_LocalDateTimeZoneOffset_nullOffset() {
        LocalDateTime dt = LocalDateTime.of(2008, 6, 30, 11, 30, 10, 500);
        OffsetDateTime.of(dt, (ZoneOffset) null);
    }

    //-----------------------------------------------------------------------
    // ofEpochSecond()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_ofEpochSecond_longOffset_afterEpoch() {
        OffsetDateTime base = OffsetDateTime.of(1970, 1, 1, 2, 0, 0, 500, OFFSET_PTWO);
        for (int i = 0; i < 100000; i++) {
            OffsetDateTime test = OffsetDateTime.ofEpochSecond(i, 500, OFFSET_PTWO);
            assertEquals(test, base.plusSeconds(i));
        }
    }

    @Test(groups={"tck"})
    public void factory_ofEpochSecond_longOffset_beforeEpoch() {
        OffsetDateTime base = OffsetDateTime.of(1970, 1, 1, 2, 0, 0, 500, OFFSET_PTWO);
        for (int i = 0; i < 100000; i++) {
            OffsetDateTime test = OffsetDateTime.ofEpochSecond(-i, 500, OFFSET_PTWO);
            assertEquals(test, base.minusSeconds(i));
        }
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void factory_ofEpochSecond_longOffset_tooBig() {
        OffsetDateTime.ofEpochSecond(Long.MAX_VALUE, 500, OFFSET_PONE);  // TODO: better test
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void factory_ofEpochSecond_longOffset_tooSmall() {
        OffsetDateTime.ofEpochSecond(Long.MIN_VALUE, 500, OFFSET_PONE);  // TODO: better test
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void factory_ofEpochSecond_badNanos_tooBig() {
        OffsetDateTime.ofEpochSecond(0, 1_000_000_000, OFFSET_PONE);
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void factory_ofEpochSecond_badNanos_tooSmall() {
        OffsetDateTime.ofEpochSecond(0, -1, OFFSET_PONE);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_ofEpochSecond_longOffset_nullOffset() {
        OffsetDateTime.ofEpochSecond(0L, 500, null);
    }

    //-----------------------------------------------------------------------
    // from()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_factory_CalendricalObject() {
        assertEquals(OffsetDateTime.from(OffsetDateTime.of(2007, 7, 15, 17, 30, OFFSET_PONE)), OffsetDateTime.of(2007, 7, 15, 17, 30, OFFSET_PONE));
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void test_factory_CalendricalObject_invalid_noDerive() {
        OffsetDateTime.from(LocalTime.of(12, 30));
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_factory_Calendricals_null() {
        OffsetDateTime.from((DateTimeAccessor) null);
    }

    //-----------------------------------------------------------------------
    // parse()
    //-----------------------------------------------------------------------
    @Test(dataProvider="sampleToString", groups={"tck"})
    public void test_parse(int y, int month, int d, int h, int m, int s, int n, String offsetId, String text) {
        OffsetDateTime t = OffsetDateTime.parse(text);
        assertEquals(t.getYear(), y);
        assertEquals(t.getMonth().getValue(), month);
        assertEquals(t.getDayOfMonth(), d);
        assertEquals(t.getHour(), h);
        assertEquals(t.getMinute(), m);
        assertEquals(t.getSecond(), s);
        assertEquals(t.getNano(), n);
        assertEquals(t.getOffset().getId(), offsetId);
    }

    @Test(expectedExceptions=DateTimeParseException.class, groups={"tck"})
    public void factory_parse_illegalValue() {
        OffsetDateTime.parse("2008-06-32T11:15+01:00");
    }

    @Test(expectedExceptions=DateTimeParseException.class, groups={"tck"})
    public void factory_parse_invalidValue() {
        OffsetDateTime.parse("2008-06-31T11:15+01:00");
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_parse_nullText() {
        OffsetDateTime.parse((String) null);
    }

    //-----------------------------------------------------------------------
    // parse(DateTimeFormatter)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void factory_parse_formatter() {
        DateTimeFormatter f = DateTimeFormatters.pattern("y M d H m s XXX");
        OffsetDateTime test = OffsetDateTime.parse("2010 12 3 11 30 0 +01:00", f);
        assertEquals(test, OffsetDateTime.of(2010, 12, 3, 11, 30, ZoneOffset.ofHours(1)));
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_parse_formatter_nullText() {
        DateTimeFormatter f = DateTimeFormatters.pattern("y M d H m s");
        OffsetDateTime.parse((String) null, f);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void factory_parse_formatter_nullFormatter() {
        OffsetDateTime.parse("ANY", null);
    }

    //-----------------------------------------------------------------------
    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void constructor_nullTime() throws Throwable  {
        Constructor<OffsetDateTime> con = OffsetDateTime.class.getDeclaredConstructor(LocalDateTime.class, ZoneOffset.class);
        con.setAccessible(true);
        try {
            con.newInstance(null, OFFSET_PONE);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void constructor_nullOffset() throws Throwable  {
        Constructor<OffsetDateTime> con = OffsetDateTime.class.getDeclaredConstructor(LocalDateTime.class, ZoneOffset.class);
        con.setAccessible(true);
        try {
            con.newInstance(LocalDateTime.of(2008, 6, 30, 11, 30), null);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }

    //-----------------------------------------------------------------------
    // basics
    //-----------------------------------------------------------------------
    @DataProvider(name="sampleTimes")
    Object[][] provider_sampleTimes() {
        return new Object[][] {
            {2008, 6, 30, 11, 30, 20, 500, OFFSET_PONE},
            {2008, 6, 30, 11, 0, 0, 0, OFFSET_PONE},
            {2008, 6, 30, 23, 59, 59, 999999999, OFFSET_PONE},
            {-1, 1, 1, 0, 0, 0, 0, OFFSET_PONE},
        };
    }

    @Test(dataProvider="sampleTimes", groups={"tck"})
    public void test_get(int y, int o, int d, int h, int m, int s, int n, ZoneOffset offset) {
        LocalDate localDate = LocalDate.of(y, o, d);
        LocalTime localTime = LocalTime.of(h, m, s, n);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        OffsetDateTime a = OffsetDateTime.of(localDateTime, offset);

        assertEquals(a.getYear(), localDate.getYear());
        assertEquals(a.getMonth(), localDate.getMonth());
        assertEquals(a.getDayOfMonth(), localDate.getDayOfMonth());
        assertEquals(a.getDayOfYear(), localDate.getDayOfYear());
        assertEquals(a.getDayOfWeek(), localDate.getDayOfWeek());

        assertEquals(a.getHour(), localDateTime.getHour());
        assertEquals(a.getMinute(), localDateTime.getMinute());
        assertEquals(a.getSecond(), localDateTime.getSecond());
        assertEquals(a.getNano(), localDateTime.getNano());

        assertEquals(a.toOffsetDate(), OffsetDate.of(localDate, offset));
        assertEquals(a.toOffsetTime(), OffsetTime.of(localTime, offset));
        assertEquals(a.toString(), localDateTime.toString() + offset.toString());
    }

    //-----------------------------------------------------------------------
    // get(DateTimeField)
    //-----------------------------------------------------------------------
    @DataProvider(name="invalidFields")
    Object[][] data_invalidFields() {
        return new Object[][] {
            {MockFieldNoValue.INSTANCE},
        };
    }

    @Test(groups={"tck"})
    public void test_get_DateTimeField() {
        OffsetDateTime test = OffsetDateTime.of(2008, 6, 30, 12, 30, 40, 987654321, OFFSET_PONE);
        assertEquals(test.getLong(ChronoField.YEAR), 2008);
        assertEquals(test.getLong(ChronoField.MONTH_OF_YEAR), 6);
        assertEquals(test.getLong(ChronoField.DAY_OF_MONTH), 30);
        assertEquals(test.getLong(ChronoField.DAY_OF_WEEK), 1);
        assertEquals(test.getLong(ChronoField.DAY_OF_YEAR), 182);

        assertEquals(test.getLong(ChronoField.HOUR_OF_DAY), 12);
        assertEquals(test.getLong(ChronoField.MINUTE_OF_HOUR), 30);
        assertEquals(test.getLong(ChronoField.SECOND_OF_MINUTE), 40);
        assertEquals(test.getLong(ChronoField.NANO_OF_SECOND), 987654321);
        assertEquals(test.getLong(ChronoField.HOUR_OF_AMPM), 0);
        assertEquals(test.getLong(ChronoField.AMPM_OF_DAY), 1);

        assertEquals(test.getLong(ChronoField.INSTANT_SECONDS), test.toEpochSecond());
        assertEquals(test.getLong(ChronoField.OFFSET_SECONDS), 3600);
    }

    @Test(dataProvider="invalidFields", expectedExceptions=DateTimeException.class, groups={"tck"} )
    public void test_get_DateTimeField_invalidField(DateTimeField field) {
        TEST_2008_6_30_11_30_59_000000500.getLong(field);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"} )
    public void test_get_DateTimeField_null() {
        OffsetDateTime test = OffsetDateTime.of(2008, 6, 30, 12, 30, 40, 987654321, OFFSET_PONE);
        test.getLong((DateTimeField) null);
    }

    //-----------------------------------------------------------------------
    // query(Query)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_query_chrono() {
        assertEquals(TEST_2008_6_30_11_30_59_000000500.query(Query.CHRONO), ISOChrono.INSTANCE);
    }

    @Test(groups={"tck"})
    public void test_query_zone() {
        assertEquals(TEST_2008_6_30_11_30_59_000000500.query(Query.ZONE_ID), null);
    }

    @Test(groups={"tck"})
    public void test_query_timePrecision() {
        assertEquals(TEST_2008_6_30_11_30_59_000000500.query(Query.TIME_PRECISION), NANOS);
    }

    @Test(groups={"tck"})
    public void test_query_offset() {
        assertEquals(TEST_2008_6_30_11_30_59_000000500.query(Query.OFFSET), OFFSET_PONE);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_query_null() {
        TEST_2008_6_30_11_30_59_000000500.query(null);
    }

    //-----------------------------------------------------------------------
    // with(WithAdjuster)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_with_adjustment() {
        final OffsetDateTime sample = OffsetDateTime.of(2012, 3, 4, 23, 5, OFFSET_PONE);
        WithAdjuster adjuster = new WithAdjuster() {
            @Override
            public DateTime doWithAdjustment(DateTime dateTime) {
                return sample;
            }
        };
        assertEquals(TEST_2008_6_30_11_30_59_000000500.with(adjuster), sample);
    }

    @Test(groups={"tck"})
    public void test_with_adjustment_LocalDate() {
        OffsetDateTime test = TEST_2008_6_30_11_30_59_000000500.with(LocalDate.of(2012, 9, 3));
        assertEquals(test, OffsetDateTime.of(2012, 9, 3, 11, 30, 59, 500, OFFSET_PONE));
    }

    @Test(groups={"tck"})
    public void test_with_adjustment_LocalTime() {
        OffsetDateTime test = TEST_2008_6_30_11_30_59_000000500.with(LocalTime.of(19, 15));
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 19, 15, OFFSET_PONE));
    }

    @Test(groups={"tck"})
    public void test_with_adjustment_LocalDateTime() {
        OffsetDateTime test = TEST_2008_6_30_11_30_59_000000500.with(LocalDateTime.of(2012, 9, 3, 19, 15));
        assertEquals(test, OffsetDateTime.of(2012, 9, 3, 19, 15, OFFSET_PONE));
    }

    @Test(groups={"tck"})
    public void test_with_adjustment_OffsetDate() {
        OffsetDateTime test = TEST_2008_6_30_11_30_59_000000500.with(OffsetDate.of(2012, 9, 3, OFFSET_PTWO));
        assertEquals(test, OffsetDateTime.of(2012, 9, 3, 11, 30, 59, 500, OFFSET_PTWO));
    }

    @Test(groups={"tck"})
    public void test_with_adjustment_OffsetTime() {
        OffsetDateTime test = TEST_2008_6_30_11_30_59_000000500.with(OffsetTime.of(19, 15, OFFSET_PTWO));
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 19, 15, OFFSET_PTWO));
    }

    @Test(groups={"tck"})
    public void test_with_adjustment_OffsetDateTime() {
        OffsetDateTime test = TEST_2008_6_30_11_30_59_000000500.with(OffsetDateTime.of(2012, 9, 3, 19, 15, OFFSET_PTWO));
        assertEquals(test, OffsetDateTime.of(2012, 9, 3, 19, 15, OFFSET_PTWO));
    }

    @Test(groups={"tck"})
    public void test_with_adjustment_Month() {
        OffsetDateTime test = TEST_2008_6_30_11_30_59_000000500.with(DECEMBER);
        assertEquals(test, OffsetDateTime.of(2008, 12, 30, 11, 30, 59, 500, OFFSET_PONE));
    }

    @Test(groups={"tck"})
    public void test_with_adjustment_ZoneOffset() {
        OffsetDateTime test = TEST_2008_6_30_11_30_59_000000500.with(OFFSET_PTWO);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 500, OFFSET_PTWO));
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_with_adjustment_null() {
        TEST_2008_6_30_11_30_59_000000500.with((WithAdjuster) null);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_withOffsetSameLocal_null() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        base.withOffsetSameLocal(null);
    }

    //-----------------------------------------------------------------------
    // withOffsetSameInstant()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withOffsetSameInstant() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.withOffsetSameInstant(OFFSET_PTWO);
        OffsetDateTime expected = OffsetDateTime.of(2008, 6, 30, 12, 30, 59, OFFSET_PTWO);
        assertEquals(test, expected);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_withOffsetSameInstant_null() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        base.withOffsetSameInstant(null);
    }

    //-----------------------------------------------------------------------
    // withYear()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withYear_normal() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.withYear(2007);
        assertEquals(test, OffsetDateTime.of(2007, 6, 30, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // withMonth()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withMonth_normal() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.withMonth(1);
        assertEquals(test, OffsetDateTime.of(2008, 1, 30, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // withDayOfMonth()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withDayOfMonth_normal() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.withDayOfMonth(15);
        assertEquals(test, OffsetDateTime.of(2008, 6, 15, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // withDayOfYear(int)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withDayOfYear_normal() {
        OffsetDateTime t = TEST_2008_6_30_11_30_59_000000500.withDayOfYear(33);
        assertEquals(t, OffsetDateTime.of(2008, 2, 2, 11, 30, 59, 500, OFFSET_PONE));
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void test_withDayOfYear_illegal() {
        TEST_2008_6_30_11_30_59_000000500.withDayOfYear(367);
    }

    @Test(expectedExceptions=DateTimeException.class, groups={"tck"})
    public void test_withDayOfYear_invalid() {
        OffsetDateTime.of(2007, 2, 2, 11, 30, OFFSET_PONE).withDayOfYear(366);
    }

    //-----------------------------------------------------------------------
    // withHour()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withHour_normal() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.withHour(15);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 15, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // withMinute()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withMinute_normal() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.withMinute(15);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 15, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // withSecond()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withSecond_normal() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.withSecond(15);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 30, 15, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // withNano()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withNanoOfSecond_normal() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 1, OFFSET_PONE);
        OffsetDateTime test = base.withNano(15);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 15, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // withTime()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withTime_HM() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 0, OFFSET_PONE);
        OffsetDateTime test = base.withTime(12, 10);
        OffsetDateTime expected = OffsetDateTime.of(2008, 6, 30, 12, 10, 0, 0, OFFSET_PONE);
        assertEquals(test, expected);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withTime_HMS() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 0, OFFSET_PONE);
        OffsetDateTime test = base.withTime(12, 10, 9);
        OffsetDateTime expected = OffsetDateTime.of(2008, 6, 30, 12, 10, 9, 0, OFFSET_PONE);
        assertEquals(test, expected);
    }

    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_withTime_HMSN() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.withTime(12, 10, 9, 8);
        OffsetDateTime expected = OffsetDateTime.of(2008, 6, 30, 12, 10, 9, 8, OFFSET_PONE);
        assertEquals(test, expected);
    }

    //-----------------------------------------------------------------------
    // plus(Period)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plus_Period() {
        MockSimplePeriod period = MockSimplePeriod.of(7, ChronoUnit.MONTHS);
        OffsetDateTime t = TEST_2008_6_30_11_30_59_000000500.plus(period);
        assertEquals(t, OffsetDateTime.of(2009, 1, 30, 11, 30, 59, 500, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // plus(Duration)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plus_Duration() {
        Duration dur = Duration.ofSeconds(62, 3);
        OffsetDateTime t = TEST_2008_6_30_11_30_59_000000500.plus(dur);
        assertEquals(t, OffsetDateTime.of(2008, 6, 30, 11, 32, 1, 503, OFFSET_PONE));
    }

    @Test(groups={"tck"})
    public void test_plus_Duration_zero() {
        OffsetDateTime t = TEST_2008_6_30_11_30_59_000000500.plus(Duration.ZERO);
        assertEquals(t, TEST_2008_6_30_11_30_59_000000500);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_plus_Duration_null() {
        TEST_2008_6_30_11_30_59_000000500.plus((Duration) null);
    }

    //-----------------------------------------------------------------------
    // plusYears()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plusYears() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.plusYears(1);
        assertEquals(test, OffsetDateTime.of(2009, 6, 30, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // plusMonths()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plusMonths() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.plusMonths(1);
        assertEquals(test, OffsetDateTime.of(2008, 7, 30, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // plusWeeks()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plusWeeks() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.plusWeeks(1);
        assertEquals(test, OffsetDateTime.of(2008, 7, 7, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // plusDays()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plusDays() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.plusDays(1);
        assertEquals(test, OffsetDateTime.of(2008, 7, 1, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // plusHours()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plusHours() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.plusHours(13);
        assertEquals(test, OffsetDateTime.of(2008, 7, 1, 0, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // plusMinutes()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plusMinutes() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.plusMinutes(30);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 12, 0, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // plusSeconds()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plusSeconds() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.plusSeconds(1);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 31, 0, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // plusNanos()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_plusNanos() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 0, OFFSET_PONE);
        OffsetDateTime test = base.plusNanos(1);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 1, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minus(Period)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minus_Period() {
        MockSimplePeriod period = MockSimplePeriod.of(7, ChronoUnit.MONTHS);
        OffsetDateTime t = TEST_2008_6_30_11_30_59_000000500.minus(period);
        assertEquals(t, OffsetDateTime.of(2007, 11, 30, 11, 30, 59, 500, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minus(Duration)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minus_Duration() {
        Duration dur = Duration.ofSeconds(62, 3);
        OffsetDateTime t = TEST_2008_6_30_11_30_59_000000500.minus(dur);
        assertEquals(t, OffsetDateTime.of(2008, 6, 30, 11, 29, 57, 497, OFFSET_PONE));
    }

    @Test(groups={"tck"})
    public void test_minus_Duration_zero() {
        OffsetDateTime t = TEST_2008_6_30_11_30_59_000000500.minus(Duration.ZERO);
        assertEquals(t, TEST_2008_6_30_11_30_59_000000500);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_minus_Duration_null() {
        TEST_2008_6_30_11_30_59_000000500.minus((Duration) null);
    }

    //-----------------------------------------------------------------------
    // minusYears()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minusYears() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.minusYears(1);
        assertEquals(test, OffsetDateTime.of(2007, 6, 30, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minusMonths()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minusMonths() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.minusMonths(1);
        assertEquals(test, OffsetDateTime.of(2008, 5, 30, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minusWeeks()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minusWeeks() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.minusWeeks(1);
        assertEquals(test, OffsetDateTime.of(2008, 6, 23, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minusDays()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minusDays() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.minusDays(1);
        assertEquals(test, OffsetDateTime.of(2008, 6, 29, 11, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minusHours()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minusHours() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.minusHours(13);
        assertEquals(test, OffsetDateTime.of(2008, 6, 29, 22, 30, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minusMinutes()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minusMinutes() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.minusMinutes(30);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 0, 59, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minusSeconds()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minusSeconds() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        OffsetDateTime test = base.minusSeconds(1);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 30, 58, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // minusNanos()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_minusNanos() {
        OffsetDateTime base = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 0, OFFSET_PONE);
        OffsetDateTime test = base.minusNanos(1);
        assertEquals(test, OffsetDateTime.of(2008, 6, 30, 11, 30, 58, 999999999, OFFSET_PONE));
    }

    //-----------------------------------------------------------------------
    // atZoneSameInstant()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_atZone() {
        OffsetDateTime t = OffsetDateTime.of(2008, 6, 30, 11, 30, OFFSET_MTWO);
        assertEquals(t.atZoneSameInstant(ZONE_PARIS),
                ZonedDateTime.of(LocalDateTime.of(2008, 6, 30, 15, 30), ZONE_PARIS));
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_atZone_nullTimeZone() {
        OffsetDateTime t = OffsetDateTime.of(2008, 6, 30, 11, 30, OFFSET_PTWO);
        t.atZoneSameInstant((ZoneId) null);
    }

    //-----------------------------------------------------------------------
    // atZoneSimilarLocal()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_atZoneSimilarLocal() {
        OffsetDateTime t = OffsetDateTime.of(2008, 6, 30, 11, 30, OFFSET_MTWO);
        assertEquals(t.atZoneSimilarLocal(ZONE_PARIS),
                ZonedDateTime.of(LocalDateTime.of(2008, 6, 30, 11, 30), ZONE_PARIS));
    }

    @Test(groups={"tck"})
    public void test_atZoneSimilarLocal_dstGap() {
        OffsetDateTime t = OffsetDateTime.of(2007, 4, 1, 0, 0, OFFSET_MTWO);
        assertEquals(t.atZoneSimilarLocal(ZONE_GAZA),
                ZonedDateTime.of(LocalDateTime.of(2007, 4, 1, 1, 0), ZONE_GAZA));
    }

    @Test(groups={"tck"})
    public void test_atZone_dstOverlapSummer() {
        OffsetDateTime t = OffsetDateTime.of(2007, 10, 28, 2, 30, OFFSET_PTWO);
        assertEquals(t.atZoneSimilarLocal(ZONE_PARIS).getDateTime(), t.getDateTime());
        assertEquals(t.atZoneSimilarLocal(ZONE_PARIS).getOffset(), OFFSET_PTWO);
        assertEquals(t.atZoneSimilarLocal(ZONE_PARIS).getZone(), ZONE_PARIS);
    }

    @Test(groups={"tck"})
    public void test_atZone_dstOverlapWinter() {
        OffsetDateTime t = OffsetDateTime.of(2007, 10, 28, 2, 30, OFFSET_PONE);
        assertEquals(t.atZoneSimilarLocal(ZONE_PARIS).getDateTime(), t.getDateTime());
        assertEquals(t.atZoneSimilarLocal(ZONE_PARIS).getOffset(), OFFSET_PONE);
        assertEquals(t.atZoneSimilarLocal(ZONE_PARIS).getZone(), ZONE_PARIS);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_atZoneSimilarLocal_nullTimeZone() {
        OffsetDateTime t = OffsetDateTime.of(2008, 6, 30, 11, 30, OFFSET_PTWO);
        t.atZoneSimilarLocal((ZoneId) null);
    }

    //-----------------------------------------------------------------------
    // toEpochSecond()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_toEpochSecond_afterEpoch() {
        for (int i = 0; i < 100000; i++) {
            OffsetDateTime a = OffsetDateTime.of(1970, 1, 1, 0, 0, ZoneOffset.UTC).plusSeconds(i);
            assertEquals(a.toEpochSecond(), i);
        }
    }

    @Test(groups={"tck"})
    public void test_toEpochSecond_beforeEpoch() {
        for (int i = 0; i < 100000; i++) {
            OffsetDateTime a = OffsetDateTime.of(1970, 1, 1, 0, 0, ZoneOffset.UTC).minusSeconds(i);
            assertEquals(a.toEpochSecond(), -i);
        }
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_compareTo_timeMins() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 29, 3, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 30, 2, OFFSET_PONE);  // a is before b due to time
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
        assertEquals(a.toInstant().compareTo(b.toInstant()) < 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_timeSecs() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 29, 2, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 29, 3, OFFSET_PONE);  // a is before b due to time
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
        assertEquals(a.toInstant().compareTo(b.toInstant()) < 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_timeNanos() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 29, 40, 4, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 29, 40, 5, OFFSET_PONE);  // a is before b due to time
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
        assertEquals(a.toInstant().compareTo(b.toInstant()) < 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_offset() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 30, OFFSET_PTWO);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 30, OFFSET_PONE);  // a is before b due to offset
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
        assertEquals(a.toInstant().compareTo(b.toInstant()) < 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_offsetNanos() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 30, 40, 6, OFFSET_PTWO);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 30, 40, 5, OFFSET_PONE);  // a is before b due to offset
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
        assertEquals(a.toInstant().compareTo(b.toInstant()) < 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_both() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 50, OFFSET_PTWO);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 20, OFFSET_PONE);  // a is before b on instant scale
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
        assertEquals(a.toInstant().compareTo(b.toInstant()) < 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_bothNanos() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 20, 40, 4, OFFSET_PTWO);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 10, 20, 40, 5, OFFSET_PONE);  // a is before b on instant scale
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
        assertEquals(a.toInstant().compareTo(b.toInstant()) < 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_hourDifference() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 10, 0, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 0, OFFSET_PTWO);  // a is before b despite being same time-line time
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
        assertEquals(a.toInstant().compareTo(b.toInstant()) == 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_max() {
        OffsetDateTime a = OffsetDateTime.of(Year.MAX_YEAR, 12, 31, 23, 59, OFFSET_MONE);
        OffsetDateTime b = OffsetDateTime.of(Year.MAX_YEAR, 12, 31, 23, 59, OFFSET_MTWO);  // a is before b due to offset
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
    }

    @Test(groups={"tck"})
    public void test_compareTo_min() {
        OffsetDateTime a = OffsetDateTime.of(Year.MIN_YEAR, 1, 1, 0, 0, OFFSET_PTWO);
        OffsetDateTime b = OffsetDateTime.of(Year.MIN_YEAR, 1, 1, 0, 0, OFFSET_PONE);  // a is before b due to offset
        assertEquals(a.compareTo(b) < 0, true);
        assertEquals(b.compareTo(a) > 0, true);
        assertEquals(a.compareTo(a) == 0, true);
        assertEquals(b.compareTo(b) == 0, true);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_compareTo_null() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        a.compareTo(null);
    }

    @Test(expectedExceptions=ClassCastException.class, groups={"tck"})
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void compareToNonOffsetDateTime() {
       Comparable c = TEST_2008_6_30_11_30_59_000000500;
       c.compareTo(new Object());
    }

    //-----------------------------------------------------------------------
    // isAfter() / isBefore() / isEqual()
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_isBeforeIsAfterIsEqual1() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 30, 58, 3, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 2, OFFSET_PONE);  // a is before b due to time
        assertEquals(a.isBefore(b), true);
        assertEquals(a.isEqual(b), false);
        assertEquals(a.isAfter(b), false);

        assertEquals(b.isBefore(a), false);
        assertEquals(b.isEqual(a), false);
        assertEquals(b.isAfter(a), true);

        assertEquals(a.isBefore(a), false);
        assertEquals(b.isBefore(b), false);

        assertEquals(a.isEqual(a), true);
        assertEquals(b.isEqual(b), true);

        assertEquals(a.isAfter(a), false);
        assertEquals(b.isAfter(b), false);
    }

    @Test(groups={"tck"})
    public void test_isBeforeIsAfterIsEqual2() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 2, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, 3, OFFSET_PONE);  // a is before b due to time
        assertEquals(a.isBefore(b), true);
        assertEquals(a.isEqual(b), false);
        assertEquals(a.isAfter(b), false);

        assertEquals(b.isBefore(a), false);
        assertEquals(b.isEqual(a), false);
        assertEquals(b.isAfter(a), true);

        assertEquals(a.isBefore(a), false);
        assertEquals(b.isBefore(b), false);

        assertEquals(a.isEqual(a), true);
        assertEquals(b.isEqual(b), true);

        assertEquals(a.isAfter(a), false);
        assertEquals(b.isAfter(b), false);
    }

    @Test(groups={"tck"})
    public void test_isBeforeIsAfterIsEqual_instantComparison() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 10, 0, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(2008, 6, 30, 11, 0, OFFSET_PTWO);  // a is same instant as b
        assertEquals(a.isBefore(b), false);
        assertEquals(a.isEqual(b), true);
        assertEquals(a.isAfter(b), false);

        assertEquals(b.isBefore(a), false);
        assertEquals(b.isEqual(a), true);
        assertEquals(b.isAfter(a), false);

        assertEquals(a.isBefore(a), false);
        assertEquals(b.isBefore(b), false);

        assertEquals(a.isEqual(a), true);
        assertEquals(b.isEqual(b), true);

        assertEquals(a.isAfter(a), false);
        assertEquals(b.isAfter(b), false);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_isBefore_null() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        a.isBefore(null);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_isEqual_null() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        a.isEqual(null);
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_isAfter_null() {
        OffsetDateTime a = OffsetDateTime.of(2008, 6, 30, 11, 30, 59, OFFSET_PONE);
        a.isAfter(null);
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test(dataProvider="sampleTimes", groups={"tck"})
    public void test_equals_true(int y, int o, int d, int h, int m, int s, int n, ZoneOffset ignored) {
        OffsetDateTime a = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PONE);
        assertEquals(a.equals(b), true);
        assertEquals(a.hashCode() == b.hashCode(), true);
    }
    @Test(dataProvider="sampleTimes", groups={"tck"})
    public void test_equals_false_year_differs(int y, int o, int d, int h, int m, int s, int n, ZoneOffset ignored) {
        OffsetDateTime a = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(y + 1, o, d, h, m, s, n, OFFSET_PONE);
        assertEquals(a.equals(b), false);
    }
    @Test(dataProvider="sampleTimes", groups={"tck"})
    public void test_equals_false_hour_differs(int y, int o, int d, int h, int m, int s, int n, ZoneOffset ignored) {
        h = (h == 23 ? 22 : h);
        OffsetDateTime a = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(y, o, d, h + 1, m, s, n, OFFSET_PONE);
        assertEquals(a.equals(b), false);
    }
    @Test(dataProvider="sampleTimes", groups={"tck"})
    public void test_equals_false_minute_differs(int y, int o, int d, int h, int m, int s, int n, ZoneOffset ignored) {
        m = (m == 59 ? 58 : m);
        OffsetDateTime a = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(y, o, d, h, m + 1, s, n, OFFSET_PONE);
        assertEquals(a.equals(b), false);
    }
    @Test(dataProvider="sampleTimes", groups={"tck"})
    public void test_equals_false_second_differs(int y, int o, int d, int h, int m, int s, int n, ZoneOffset ignored) {
        s = (s == 59 ? 58 : s);
        OffsetDateTime a = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(y, o, d, h, m, s + 1, n, OFFSET_PONE);
        assertEquals(a.equals(b), false);
    }
    @Test(dataProvider="sampleTimes", groups={"tck"})
    public void test_equals_false_nano_differs(int y, int o, int d, int h, int m, int s, int n, ZoneOffset ignored) {
        n = (n == 999999999 ? 999999998 : n);
        OffsetDateTime a = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(y, o, d, h, m, s, n + 1, OFFSET_PONE);
        assertEquals(a.equals(b), false);
    }
    @Test(dataProvider="sampleTimes", groups={"tck"})
    public void test_equals_false_offset_differs(int y, int o, int d, int h, int m, int s, int n, ZoneOffset ignored) {
        OffsetDateTime a = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PONE);
        OffsetDateTime b = OffsetDateTime.of(y, o, d, h, m, s, n, OFFSET_PTWO);
        assertEquals(a.equals(b), false);
    }

    @Test(groups={"tck"})
    public void test_equals_itself_true() {
        assertEquals(TEST_2008_6_30_11_30_59_000000500.equals(TEST_2008_6_30_11_30_59_000000500), true);
    }

    @Test(groups={"tck"})
    public void test_equals_string_false() {
        assertEquals(TEST_2008_6_30_11_30_59_000000500.equals("2007-07-15"), false);
    }

    @Test(groups={"tck"})
    public void test_equals_null_false() {
        assertEquals(TEST_2008_6_30_11_30_59_000000500.equals(null), false);
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @DataProvider(name="sampleToString")
    Object[][] provider_sampleToString() {
        return new Object[][] {
            {2008, 6, 30, 11, 30, 59, 0, "Z", "2008-06-30T11:30:59Z"},
            {2008, 6, 30, 11, 30, 59, 0, "+01:00", "2008-06-30T11:30:59+01:00"},
            {2008, 6, 30, 11, 30, 59, 999000000, "Z", "2008-06-30T11:30:59.999Z"},
            {2008, 6, 30, 11, 30, 59, 999000000, "+01:00", "2008-06-30T11:30:59.999+01:00"},
            {2008, 6, 30, 11, 30, 59, 999000, "Z", "2008-06-30T11:30:59.000999Z"},
            {2008, 6, 30, 11, 30, 59, 999000, "+01:00", "2008-06-30T11:30:59.000999+01:00"},
            {2008, 6, 30, 11, 30, 59, 999, "Z", "2008-06-30T11:30:59.000000999Z"},
            {2008, 6, 30, 11, 30, 59, 999, "+01:00", "2008-06-30T11:30:59.000000999+01:00"},
        };
    }

    @Test(dataProvider="sampleToString", groups={"tck"})
    public void test_toString(int y, int o, int d, int h, int m, int s, int n, String offsetId, String expected) {
        OffsetDateTime t = OffsetDateTime.of(y, o, d, h, m, s, n, ZoneOffset.of(offsetId));
        String str = t.toString();
        assertEquals(str, expected);
    }

    //-----------------------------------------------------------------------
    // toString(DateTimeFormatter)
    //-----------------------------------------------------------------------
    @Test(groups={"tck"})
    public void test_toString_formatter() {
        DateTimeFormatter f = DateTimeFormatters.pattern("y M d H m s");
        String t = OffsetDateTime.of(2010, 12, 3, 11, 30, OFFSET_PONE).toString(f);
        assertEquals(t, "2010 12 3 11 30 0");
    }

    @Test(expectedExceptions=NullPointerException.class, groups={"tck"})
    public void test_toString_formatter_null() {
        OffsetDateTime.of(2010, 12, 3, 11, 30, OFFSET_PONE).toString(null);
    }

}
