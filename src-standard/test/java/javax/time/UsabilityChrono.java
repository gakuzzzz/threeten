/*
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
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

import static javax.time.calendrical.ChronoField.DAY_OF_MONTH;
import static javax.time.calendrical.ChronoField.DAY_OF_WEEK;
import static javax.time.calendrical.ChronoField.EPOCH_DAY;

import java.io.PrintStream;
import java.util.Set;

import javax.time.calendrical.ChronoField;
import javax.time.calendrical.ChronoUnit;
import javax.time.calendrical.JulianDayField;
import javax.time.chrono.Chrono;
import javax.time.chrono.ChronoLocalDate;
import javax.time.chrono.ISOChrono;
import javax.time.chrono.global.HijrahChrono;
import javax.time.chrono.global.JapaneseChrono;
import javax.time.chrono.global.MinguoChrono;
import javax.time.chrono.global.ThaiBuddhistChrono;

/**
 * Usability class for package.
 */
public final class UsabilityChrono {

    public static void main(String[] args) {
        System.out.println("------");
        newPackagePluggable();
        System.out.println("------");
        epochDays();
        System.out.println("------");
        printMinguoCal();
        System.out.println("------");
        example1();
    }

    private UsabilityChrono() {
    }

    static {
        Chrono<?> c = JapaneseChrono.INSTANCE;
        c = MinguoChrono.INSTANCE;
        c = ThaiBuddhistChrono.INSTANCE;
        c = JapaneseChrono.INSTANCE;
        c = MinguoChrono.INSTANCE;
        c = HijrahChrono.INSTANCE;
        c = ISOChrono.INSTANCE;
        c.toString();
    }

    private static void newPackagePluggable() {
        Chrono<?> chrono = MinguoChrono.INSTANCE;

        ChronoLocalDate<?> date = chrono.dateNow();
        System.out.printf("now: %s%n", date);

        date = date.with(DAY_OF_MONTH, 1);
        System.out.printf("first of month: %s%n", date);

        int month = (int) date.get(ChronoField.MONTH_OF_YEAR);
        date = date.with(DAY_OF_WEEK, 1);
        System.out.printf("start of first week: %s%n", date);

        while (date.get(ChronoField.MONTH_OF_YEAR) <= month) {
            String row = "";
            for (int i = 0; i < 7; i++) {
                row += date.get(ChronoField.DAY_OF_MONTH) + " ";
                date = date.plus(1, ChronoUnit.DAYS);
            }
            System.out.println(row);
        }
    }

    private static void epochDays() {
        output(LocalDate.now());
        output(LocalDate.of(1945, 11, 12));
        output(LocalDate.of(-4713, 11, 24));
        output(LocalDate.of(1858, 11, 17));
        output(LocalDate.of(1970, 1, 1));
        output(LocalDate.of(1, 1, 1));
    }

    protected static void output(LocalDate date) {
        System.out.println(date);
        System.out.println("EPOCH_DAY " + date.getLong(EPOCH_DAY));
        System.out.println("JDN " + date.getLong(JulianDayField.JULIAN_DAY));
        System.out.println("MJD " + date.getLong(JulianDayField.MODIFIED_JULIAN_DAY));
        System.out.println("RD  " + date.getLong(JulianDayField.RATA_DIE));
        System.out.println();
    }


    /**
     * Example code used in the ChronoDate and package.html of javax.microedition.chrono.
     */
    static void example1() {
        System.out.printf("Available Calendars%n");

        // Print the Minguo date
        ChronoLocalDate<MinguoChrono> now1 = MinguoChrono.INSTANCE.dateNow();
        int day = now1.get(ChronoField.DAY_OF_MONTH);
        int dow = now1.get(ChronoField.DAY_OF_WEEK);
        int month = now1.get(ChronoField.MONTH_OF_YEAR);
        int year = now1.get(ChronoField.YEAR);
        System.out.printf("  Today is %s %s %d-%s-%d%n", now1.getChrono().getId(),
                DayOfWeek.of(dow), year, month, day);

        // Print today's date and the last day of the year for the Minguo Calendar.
        ChronoLocalDate<MinguoChrono> first = now1
                .with(ChronoField.DAY_OF_MONTH, 1)
                .with(ChronoField.MONTH_OF_YEAR, 1);
        ChronoLocalDate<MinguoChrono> last = first
                .plus(1, ChronoUnit.YEARS)
                .minus(1, ChronoUnit.DAYS);
        System.out.printf("  1st of year: %s; end of year: %s%n", first, last);

        // Enumerate the list of available calendars and print today for each
        LocalDate  before = LocalDate.of(-500, 1, 1);
        Set<Chrono<?>> chronos = Chrono.getAvailableChronologies();
        for (Chrono<?> chrono : chronos) {
            ChronoLocalDate<?> date = chrono.dateNow();
            ChronoLocalDate<?> date2 = chrono.date(before);
            System.out.printf("   %20s: %22s, %22s%n", chrono.getId(), date, date2);
        }
    }

    /**
     * Prints a Minguo calendar for the current month.
     */
    private static void printMinguoCal() {
        String chronoName = "Minguo";
        Chrono<?> chrono = Chrono.of(chronoName);
        ChronoLocalDate<?> today = chrono.dateNow();
        printMonthCal(today, System.out);
    }

    /**
     * Print a month calendar with complete week rows.
     * @param date A date in some calendar
     * @param out a PrintStream
     */
    private static <C extends Chrono<C>> void printMonthCal(ChronoLocalDate<C> date, PrintStream out) {

        int lengthOfMonth = (int) date.lengthOfMonth();
        ChronoLocalDate<C> end = date.with(ChronoField.DAY_OF_MONTH, lengthOfMonth);
        end = end.plus(7 - end.get(ChronoField.DAY_OF_WEEK), ChronoUnit.DAYS);
        // Back up to the beginning of the week including the 1st of the month
        ChronoLocalDate<C> start = date.with(ChronoField.DAY_OF_MONTH, 1);
        start = start.minus(start.get(ChronoField.DAY_OF_WEEK), ChronoUnit.DAYS);

        out.printf("%9s Month %2d, %4d%n", date.getChrono().getId(),
                date.get(ChronoField.MONTH_OF_YEAR),
                date.get(ChronoField.YEAR));
        String[] colText = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        printMonthRow(colText, " ", out);

        String[] cell = new String[7];
        for ( ; start.compareTo(end) <= 0; start = start.plus(1, ChronoUnit.DAYS)) {
            int ndx = start.get(ChronoField.DAY_OF_WEEK) - 1;
            cell[ndx] = Integer.toString((int) start.get(ChronoField.DAY_OF_MONTH));
            if (ndx == 6) {
                printMonthRow(cell, "|", out);
            }
        }
    }

    private static void printMonthRow(String[] cells, String delim, PrintStream out) {
        for (int i = 0; i < cells.length; i++) {
            out.printf("%s%3s ", delim, cells[i]);
        }
        out.println(delim);
    }

}
