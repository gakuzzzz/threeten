/*
 * Copyright (c) 2011, Stephen Colebourne & Michael Nascimento Santos
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
package javax.time.calendar;

import java.util.Comparator;

import javax.time.Instant;

/**
 * The standard calendrical concepts.
 * <p>
 * In order to support interoperation between multiple classes and calendar systems
 * these common basic concepts are used.
 * These form part of the low-level API with {@code Calendrical}.
 *
 * @author Stephen Colebourne
 */
public enum CalendricalConcept {

    /**
     * The local calendrical, represented by {@code DateTimeCalendrical}.
     */
    DATE_TIME_CALENDRICAL(DateTimeCalendrical.class),
    /**
     * The local date, represented by {@code LocalDate}.
     */
    LOCAL_DATE(LocalDate.class),
    /**
     * The local time, represented by {@code LocalTime}.
     */
    LOCAL_TIME(LocalTime.class),
    /**
     * The local date-time, represented by {@code LocalDateTime}.
     */
    LOCAL_DATE_TIME(LocalDateTime.class),
    /**
     * The zone-offset, represented by {@code ZoneOffset}.
     */
    ZONE_OFFSET(ZoneOffset.class),
    /**
     * The time-zone, represented by {@code ZoneId}.
     */
    ZONE_ID(ZoneId.class),
    /**
     * The instant, represented by {@code Instant}.
     */
    INSTANT(Instant.class),
//    /**
//     * The UTC instant, represented by {@code UTCInstant}.
//     */
//    UTC_INSTANT(UTCInstant),
//    /**
//     * The TAI instant, represented by {@code TAIInstant.class}.
//     */
//    TAI_INSTANT(TAIInstant.class),
    /**
     * The chronology, represented by {@code Chronology}.
     */
    CHRONOLOGY(Chronology.class),
    ;

    /**
     * The standard class associated with the concept.
     */
    private final Class<?> clazz;

    /**
     * Restricted constructor.
     * 
     * @param clazz  the standard class associated with the concept
     */
    CalendricalConcept(Class<?> clazz) {
        this.clazz = clazz;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the class that is associated with the concept.
     * <p>
     * For example, {@code LOCAL_DATE} will return {@code LocalDate.class}.
     * 
     * @param clazz  the class associated with the concept, not null
     */
    public Class<?> getConceptClass() {
        return clazz;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a {@code Comparator} that compares the concept values extracted from
     * {@code Calendrical} instances.
     * <p>
     * The returned {@code Comparator} allows any two {@link Calendrical} implementations
     * to be compared using this concept. The comparator will extract the value of
     * this concept from each calendrical and then compare them.
     * If this concept cannot be extracted from a calendrical then an exception is thrown.
     * <p>
     * For example, to sort a list into date order when the list may contain any
     * mixture of calendricals, such as a {@code LocalDate}, {@code Instant} and {@code ZonedDateTime}:
     * <pre>
     *  List<Calendrical> list = ...
     *  Collections.sort(list, CalendricalConcept.LOCAL_DATE.comparator());
     * </pre>
     *
     * @return the comparator, not null
     */
    public Comparator<Calendrical2> comparator() {
        return new Comparator<Calendrical2>() {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            public int compare(Calendrical2 cal1, Calendrical2 cal2) {
                Comparable c1 = (Comparable) cal1.extractCalendrical(CalendricalConcept.this);
                Comparable c2 = (Comparable) cal2.extractCalendrical(CalendricalConcept.this);
                if (c1 == null || c2 == null) {
                    throw new NullPointerException("Cannot extract value to compare: " + CalendricalConcept.this);
                }
                return c1.compareTo(c2);
            }
        };
    }

    //-----------------------------------------------------------------------
    // TODO not here, doesn't depend on CalendricalConcept.this
    public static Comparator<Calendrical2> comparator(final DateTimeRule rule) {
        return new Comparator<Calendrical2>() {
            public int compare(Calendrical2 cal1, Calendrical2 cal2) {
                DateTimeCalendrical c1 = (DateTimeCalendrical) cal1.extractCalendrical(DATE_TIME_CALENDRICAL);
                DateTimeCalendrical c2 = (DateTimeCalendrical) cal2.extractCalendrical(DATE_TIME_CALENDRICAL);
                if (c1 == null || c2 == null) {
                    throw new NullPointerException("Cannot extract LocalCalendrical");
                }
                DateTimeField f1 = c1.extractLocalField(rule);
                DateTimeField f2 = c2.extractLocalField(rule);
                if (f1 == null || f2 == null) {
                    throw new NullPointerException("Cannot extract value to compare");
                }
                return f1.compareTo(f2);
            }
        };
    }

}