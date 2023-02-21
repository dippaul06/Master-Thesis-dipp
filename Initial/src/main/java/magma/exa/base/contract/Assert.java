//     _____
//    ╱     ╲┌────╭─╭──────╭─────────╮────╭─╮
//   ╱  ╲ ╱  ╲ ┌──╮ │ ╭──╮ ╮ ┬─╮ ┬─╮ ┌ ┌──╮ │
//  ╱    Y    ╲└──╰ ╵ ╰──╯ │ │ │ │ │ │ └──╰ │
//  ╲____│____╱╰──╰─┴╭───╯ ╰─╰─╯ ╰─╰─┴────╰─┴╲╲
//            ╲╭─────┘─────└────────────────╮╱╱
//
// Copyright (C) esentri.magma - All Rights Reserved.
//
// Unauthorized copying of this file, via any medium
// is strictly prohibited. Proprietary and confidential.

package magma.exa.base.contract;

import magma.exa.control.exception.Exceptions;

/**
 * Assertion statements evaluate conditions before continuing with
 * program execution. If a specified condition evaluates to false,
 * execution is halted. Assertions are used to express invariants,
 * pre-conditions, and post-conditions to validate assumptions in
 * the code and thus detect programmer errors early and unambiguously
 * at runtime. {@link Assert}-statements return {@code void} and can
 * not be used within expression contexts to validate values,
 * see {@link Require}.
 *
 * Additionally, they serve to document the inner workings of
 * the program during development and improve maintainability.
 */
public enum Assert {
    ;
    /**
     * Asserts that the actual value is true.
     *
     * @param actual value asserted to be true.
     */
    public static void isTrue(final boolean actual) {
        if (!actual) {
            throw Exceptions.illegalState("actual = false");
        }
    }

    /**
     * Asserts that the actual value is true.
     *
     * @param actual value asserted to be true.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void isTrue(final boolean actual, final String detail, final Object... args) {
        if (!actual) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is false.
     *
     * @param actual value asserted to be false.
     */
    public static void isFalse(final boolean actual) {
        if (actual) {
            throw Exceptions.illegalState("actual = true");
        }
    }

    /**
     * Asserts that the actual value is false.
     *
     * @param actual value asserted to be false.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void isFalse(final boolean actual, final String detail, final Object... args) {
        if (actual) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     */
    public static void equal(final boolean actual, final boolean expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
    }

    /**
     * Asserts that the actual value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void equal(final boolean actual, final boolean expected,
                             final String detail, final Object... args) {
        if (actual != expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is equal to the expected value.
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     */
    public static void equal(final long actual, final long expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%d) != actual(%d)", expected, actual);
        }
    }

    /**
     * Asserts that the actual value is equal to the expected value.
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void equal(final long actual, final long expected,
                             final String detail, final Object... args) {
        if (actual != expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is equal to the expected value.
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     */
    public static void equal(final double actual, final double expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%d) != actual(%d)", expected, actual);
        }
    }

    /**
     * Asserts that the actual value is equal to the expected value.
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void equal(final double actual, final double expected,
                             final String detail, final Object... args) {
        if (actual != expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @apiNote equality according to {@link java.util.Objects#equals(Object, Object)}
     */
    public static void equal(final Object actual, final Object expected) {
        if (java.util.Objects.equals(actual, expected)) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
    }

    /**
     * Asserts that the actual value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     * @apiNote equality according to {@link java.util.Objects#equals(Object, Object)}
     */
    public static void equal(final Object actual, final Object expected,
                             final String detail, final Object... args) {
        if (java.util.Objects.equals(actual, expected)) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     */
    public static void notEqual(final boolean actual, final boolean expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%d) == actual(%d)", expected, actual);
        }
    }

    /**
     * Asserts that the actual value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void notEqual(final boolean actual, final boolean expected,
                                final String detail, final Object... args) {
        if (actual == expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is not equal to the expected value.
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     */
    public static void notEqual(final long actual, final long expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%d) == actual(%d)", expected, actual);
        }
    }

    /**
     * Asserts that the actual value is not equal to the expected value.
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void notEqual(final long actual, final long expected,
                                final String detail, final Object... args) {
        if (actual == expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is not equal to the expected value.
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     */
    public static void notEqual(final double actual, final double expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%d) == actual(%d)", expected, actual);
        }
    }

    /**
     * Asserts that the actual value is not equal to the expected value.
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void notEqual(final double actual, final double expected,
                                final String detail, final Object... args) {
        if (actual == expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @apiNote equality according to {@link java.util.Objects#equals(Object, Object)}
     */
    public static void notEqual(final Object actual, final Object expected) {
        if (!java.util.Objects.equals(actual, expected)) {
            throw Exceptions.illegalState("expected(%d) == actual(%d)", expected, actual);
        }
    }

    /**
     * Asserts that the actual value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     * @apiNote equality according to {@link java.util.Objects#equals(Object, Object)}
     */
    public static void notEqual(final Object actual, final Object expected,
                                final String detail, final Object... args) {
        if (!java.util.Objects.equals(actual, expected)) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is strictly positive (actual > 0L).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual value required to be positive.
     */
    public static void positive(final long actual) {
        if (actual <= 0L) {
            throw Exceptions.illegalState("actual(%d) <= 0", actual);
        }
    }

    /**
     * Asserts that the actual value is strictly positive (actual > 0L).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual value required to be positive.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void positive(final long actual, final String detail, final Object... args) {
        if (actual <= 0L) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is strictly positive (actual > 0.0).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual value required to be positive.
     */
    public static void positive(final double actual) {
        if (actual <= 0.0) {
            throw Exceptions.illegalState("actual(%d) <= 0.0", actual);
        }
    }

    /**
     * Asserts that the actual value is strictly positive (actual > 0.0).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual value required to be positive.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void positive(final double actual, final String detail, final Object... args) {
        if (actual <= 0.0) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is non-negative (actual >= 0L).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual value required to be non-negative.
     */
    public static void notNegative(final long actual) {
        if (actual < 0L) {
            throw Exceptions.illegalState("actual(%d) < 0", actual);
        }
    }

    /**
     * Asserts that the actual value is non-negative (actual >= 0L).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual value required to be non-negative.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void notNegative(final long actual, final String detail, final Object... args) {
        if (actual < 0L) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is non-negative (actual >= 0.0).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual value required to be non-negative.
     */
    public static void notNegative(final double actual) {
        if (actual < 0.0) {
            throw Exceptions.illegalState("actual(%d) < 0.0", actual);
        }
    }

    /**
     * Asserts that the actual value is non-negative (actual >= 0.0).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual value required to be non-negative.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void notNegative(final double actual, final String detail, final Object... args) {
        if (actual < 0.0) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is strictly negative (actual < 0L).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual value required to be negative.
     */
    public static void negative(final long actual) {
        if (actual >= 0L) {
            throw Exceptions.illegalState("actual(%d) >= 0", actual);
        }
    }

    /**
     * Asserts that the actual value is strictly negative (actual < 0L).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual value required to be negative.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void negative(final long actual, final String detail, final Object... args) {
        if (actual >= 0L) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is strictly negative (actual < 0.0).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual value required to be negative.
     */
    public static void negative(final double actual) {
        if (actual >= 0.0) {
            throw Exceptions.illegalState("actual(%d) >= 0.0", actual);
        }
    }

    /**
     * Asserts that the actual value is strictly negative (actual < 0.0).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual value required to be negative.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void negative(final double actual, final String detail, final Object... args) {
        if (actual >= 0.0) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is non-positive (actual <= 0L).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual value required to be non-positive.
     */
    public static void notPositive(final long actual) {
        if (actual > 0L) {
            throw Exceptions.illegalState("actual(%d) > 0", actual);
        }
    }

    /**
     * Asserts that the actual value is non-positive (actual <= 0L).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual value required to be non-positive.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void notPositive(final long actual, final String detail, final Object... args) {
        if (actual > 0L) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is non-positive (actual <= 0.0).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual value required to be non-positive.
     */
    public static void notPositive(final double actual) {
        if (actual > 0.0) {
            throw Exceptions.illegalState("actual(%d) > 0.0", actual);
        }
    }

    /**
     * Asserts that the actual value is non-positive (actual <= 0.0).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual value required to be non-positive.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void notPositive(final double actual, final String detail, final Object... args) {
        if (actual > 0.0) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is less than the expected value (actual < expected).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     */
    public static void lessThan(final long actual, final long expected) {
        if (actual >= expected) {
            throw Exceptions.illegalState("actual(%d) >= expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual value is less than the expected value (actual < expected).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void lessThan(final long actual, final long expected,
                                final String detail, final Object... args) {
        if (actual >= expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is less than the expected value (actual < expected).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     */
    public static void lessThan(final double actual, final double expected) {
        if (actual >= expected) {
            throw Exceptions.illegalState("actual(%d) >= expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual value is less than the expected value (actual < expected).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void lessThan(final double actual, final double expected,
                                final String detail, final Object... args) {
        if (actual >= expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is less equal than the expected value (actual <= expected).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     */
    public static void lessEqual(final long actual, final long expected) {
        if (actual > expected) {
            throw Exceptions.illegalState("actual(%d) > expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual value is less equal than the expected value (actual <= expected).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void lessEqual(final long actual, final long expected,
                                 final String detail, final Object... args) {
        if (actual > expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is less equal than the expected value (actual <= expected).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     */
    public static void lessEqual(final double actual, final double expected) {
        if (actual > expected) {
            throw Exceptions.illegalState("actual(%d) > expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual value is less equal than the expected value (actual <= expected).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void lessEqual(final double actual, final double expected,
                                 final String detail, final Object... args) {
        if (actual > expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is greater than the expected value (actual > expected).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less or equal to actual.
     */
    public static void greaterThan(final long actual, final long expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual value is greater than the expected value (actual > expected).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less or equal to actual.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void greaterThan(final long actual, final long expected,
                                   final String detail, final Object... args) {
        if (actual < expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is greater than the expected value (actual > expected).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less or equal to actual.
     */
    public static void greaterThan(final double actual, final double expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual value is greater than the expected value (actual > expected).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less or equal to actual.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void greaterThan(final double actual, final double expected,
                                   final String detail, final Object... args) {
        if (actual > expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is greater or equal to
     * the expected value (actual >= expected).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   required to be greater or equal.
     * @param expected to be less than given value.
     */
    public static void greaterEqual(final long actual, final long expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual value is greater or equal to
     * the expected value (actual >= expected).
     * <p>
     * Covers all integral types due to implicit widening conversion.
     *
     * @param actual   required to be greater or equal.
     * @param expected value to be less than actual.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void greaterEqual(final long actual, final long expected,
                                    final String detail, final Object... args) {
        if (actual < expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual value is greater or equal to
     * the expected value (actual >= expected).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   required to be greater or equal.
     * @param expected value to be less than actual.
     */
    public static void greaterEqual(final double actual, final double expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual value is greater or equal to
     * the expected value (actual >= expected).
     * <p>
     * Covers all floating types due to implicit widening conversion.
     *
     * @param actual   value required to be greater or equal.
     * @param expected value to be less than actual.
     * @param detail   provided iff assertion fails.
     * @param args     arguments for the formatted detail.
     */
    public static void greaterEqual(final double actual, final double expected,
                                    final String detail, final Object... args) {
        if (actual < expected) {
            throw Exceptions.illegalState(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual reference value is identical to
     * the expected reference value.
     *
     * @param actual   value asserted to be the same.
     * @param expected value to which actual must be identical.
     */
    public static void same(final Object actual, final Object expected)    {
        if (actual != expected) {
            throw Exceptions.illegalState("actual(%d) != expected(%d)", actual, expected);
        }
    }

    /**
     * Asserts that the actual reference value is not identical to
     * the expected reference value.
     *
     * @param actual   value asserted to be not the same.
     * @param expected value to which actual must be different.
     */
    public static void notSame(final Object actual, final Object expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("actual(%d) != expected(%d)", actual, expected);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual argument != null.
     *
     * @param a 1st argument asserted to be not null.
     */
    public static void notNull(final Object a) {
        if (null == a) throw Exceptions.nullPointer("1st argument == null");
    }

    /**
     * Asserts that the actual argument != null.
     *
     * @param a 1st argument asserted to be not null.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void notNull(final Object a, final String detail, final Object... args) {
        if (null == a) throw Exceptions.nullPointer(detail, args);
    }

    /**
     * Asserts that the 2 arguments given » are not null «.
     *
     * @param a 1st argument asserted to be not null.
     * @param b 2nd argument asserted to be not null.
     */
    public static void notNull(final Object a, final Object b) {
        if (null == a) throw Exceptions.nullPointer("1st argument == null");
        if (null == b) throw Exceptions.nullPointer("2nd argument == null");
    }

    /**
     * Asserts that all 3 given arguments given » are not null «.
     *
     * @param a 1st argument asserted to be not null.
     * @param b 2nd argument asserted to be not null.
     * @param c 3rd argument asserted to be not null.
     */
    public static void notNull(final Object a, final Object b, final Object c) {
        if (null == a) throw Exceptions.nullPointer("1st argument == null");
        if (null == b) throw Exceptions.nullPointer("2nd argument == null");
        if (null == c) throw Exceptions.nullPointer("3th argument == null");
    }

    /**
     * Asserts that all 4 given arguments given » are not null «.
     *
     * @param a 1st argument asserted to be not null.
     * @param b 2nd argument asserted to be not null.
     * @param c 3rd argument asserted to be not null.
     * @param d 4th argument asserted to be not null.
     */
    public static void notNull(final Object a, final Object b, final Object c,
                               final Object d) {
        if (null == a) throw Exceptions.nullPointer("1st argument == null");
        if (null == b) throw Exceptions.nullPointer("2nd argument == null");
        if (null == c) throw Exceptions.nullPointer("3th argument == null");
        if (null == d) throw Exceptions.nullPointer("4th argument == null");
    }

    /**
     * Asserts that all 5 given arguments given » are not null «.
     *
     * @param a 1st argument asserted to be not null.
     * @param b 2nd argument asserted to be not null.
     * @param c 3rd argument asserted to be not null.
     * @param d 4th argument asserted to be not null.
     * @param e 5th argument asserted to be not null.
     */
    public static void notNull(final Object a, final Object b, final Object c,
                               final Object d, final Object e) {
        if (null == a) throw Exceptions.nullPointer("1st argument == null");
        if (null == b) throw Exceptions.nullPointer("2nd argument == null");
        if (null == c) throw Exceptions.nullPointer("3th argument == null");
        if (null == d) throw Exceptions.nullPointer("4th argument == null");
        if (null == e) throw Exceptions.nullPointer("5th argument == null");
    }

    /**
     * Asserts that all 6 given arguments given » are not null «.
     *
     * @param a 1st argument asserted to be not null.
     * @param b 2nd argument asserted to be not null.
     * @param c 3rd argument asserted to be not null.
     * @param d 4th argument asserted to be not null.
     * @param e 5th argument asserted to be not null.
     * @param f 6th argument asserted to be not null.
     */
    public static void notNull(final Object a, final Object b, final Object c,
                               final Object d, final Object e, final Object f) {
        if (null == a) throw Exceptions.nullPointer("1st argument == null");
        if (null == b) throw Exceptions.nullPointer("2nd argument == null");
        if (null == c) throw Exceptions.nullPointer("3th argument == null");
        if (null == d) throw Exceptions.nullPointer("4th argument == null");
        if (null == e) throw Exceptions.nullPointer("5th argument == null");
        if (null == f) throw Exceptions.nullPointer("6th argument == null");
    }

    /**
     * Asserts that all 7 given arguments given » are not null «.
     *
     * @param a 1st argument asserted to be not null.
     * @param b 2nd argument asserted to be not null.
     * @param c 3rd argument asserted to be not null.
     * @param d 4th argument asserted to be not null.
     * @param e 5th argument asserted to be not null.
     * @param f 6th argument asserted to be not null.
     * @param g 7th argument asserted to be not null.
     */
    public static void notNull(final Object a, final Object b, final Object c,
                               final Object d, final Object e, final Object f,
                               final Object g) {
        if (null == a) throw Exceptions.nullPointer("1st argument == null");
        if (null == b) throw Exceptions.nullPointer("2nd argument == null");
        if (null == c) throw Exceptions.nullPointer("3th argument == null");
        if (null == d) throw Exceptions.nullPointer("4th argument == null");
        if (null == e) throw Exceptions.nullPointer("5th argument == null");
        if (null == f) throw Exceptions.nullPointer("6th argument == null");
        if (null == g) throw Exceptions.nullPointer("7th argument == null");
    }

    /**
     * Asserts that all 8 given arguments given » are not null «.
     *
     * @param a 1st argument asserted to be not null.
     * @param b 2nd argument asserted to be not null.
     * @param c 3rd argument asserted to be not null.
     * @param d 4th argument asserted to be not null.
     * @param e 5th argument asserted to be not null.
     * @param f 6th argument asserted to be not null.
     * @param g 7th argument asserted to be not null.
     * @param h 8th argument asserted to be not null.
     */
    public static void notNull(final Object a, final Object b, final Object c,
                               final Object d, final Object e, final Object f,
                               final Object g, final Object h) {
        if (null == a) throw Exceptions.nullPointer("1st argument == null");
        if (null == b) throw Exceptions.nullPointer("2nd argument == null");
        if (null == c) throw Exceptions.nullPointer("3th argument == null");
        if (null == d) throw Exceptions.nullPointer("4th argument == null");
        if (null == e) throw Exceptions.nullPointer("5th argument == null");
        if (null == f) throw Exceptions.nullPointer("6th argument == null");
        if (null == g) throw Exceptions.nullPointer("7th argument == null");
        if (null == h) throw Exceptions.nullPointer("8th argument == null");
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual iterable contains no null values.
     *
     * @param actual iterable to be checked.
     */
    public static void noNulls(final Iterable<?> actual) {
        if (null == actual) {
            throw Exceptions.nullPointer("actual = null");
        }
        final var it = actual.iterator();
        for (int i = 0; it.hasNext(); ++i) {
            if (null == it.next()) {
                throw Exceptions.nullPointer("element[%d] = null", i);
            }
        }
    }

    /**
     * Asserts that the actual array contains no null values.
     *
     * @param actual array to be checked.
     */
    public static void noNulls(final Object[] actual) {
        if (null == actual) {
            throw Exceptions.nullPointer("actual = null");
        }
        final var length = actual.length;
        for (int i = 0; i < length; ++i) {
            if (null == actual[i]) {
                throw Exceptions.nullPointer("element[%d] = null", i);
            }
        }
    }
    
    // ----------------------------------------------------------

    /**
     * Asserts that the actual reference is null.
     *
     * @param actual reference asserted to be null.
     */
    public static void isNull(final Object actual) {
        if (null != actual) {
            throw Exceptions.nullPointer("null != actual");
        }
    }

    /**
     * Asserts that the actual reference is null.
     *
     * @param actual reference asserted to be not null.
     * @param detail provided iff assertion fails.
     * @param args   arguments for the formatted detail.
     */
    public static void isNull(final Object actual,
                              final String detail,
                              final Object... args) {
        if (null != actual) {
            throw Exceptions.nullPointer(detail, args);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that given 2 arguments given » are null «.
     *
     * @param a 1st argument asserted to be null.
     * @param b 2nd argument asserted to be null.
     */
    public static void isNull(final Object a, final Object b) {
        if (null != a) throw Exceptions.nullPointer("1st argument != null");
        if (null != b) throw Exceptions.nullPointer("2nd argument != null");
    }

    /**
     * Asserts that all 3 given arguments given » are null «.
     *
     * @param a 1st argument asserted to be null.
     * @param b 2nd argument asserted to be null.
     * @param c 3rd argument asserted to be null.
     */
    public static void isNull(final Object a, final Object b, final Object c) {
        if (null != a) throw Exceptions.nullPointer("1st argument != null");
        if (null != b) throw Exceptions.nullPointer("2nd argument != null");
        if (null != c) throw Exceptions.nullPointer("3th argument != null");
    }

    /**
     * Asserts that all 4 given arguments given » are null «.
     *
     * @param a 1st argument asserted to be null.
     * @param b 2nd argument asserted to be null.
     * @param c 3rd argument asserted to be null.
     * @param d 4th argument asserted to be null.
     */
    public static void isNull(final Object a, final Object b, final Object c, final Object d) {
        if (null != a) throw Exceptions.nullPointer("1st argument != null");
        if (null != b) throw Exceptions.nullPointer("2nd argument != null");
        if (null != c) throw Exceptions.nullPointer("3th argument != null");
        if (null != d) throw Exceptions.nullPointer("4th argument != null");
    }

    /**
     * Asserts that all 5 given arguments given » are null «.
     *
     * @param a 1st argument asserted to be null.
     * @param b 2nd argument asserted to be null.
     * @param c 3rd argument asserted to be null.
     * @param d 4th argument asserted to be null.
     * @param e 5th argument asserted to be null.
     */
    public static void isNull(final Object a, final Object b, final Object c,
                              final Object d, final Object e) {
        if (null != a) throw Exceptions.nullPointer("1st argument != null");
        if (null != b) throw Exceptions.nullPointer("2nd argument != null");
        if (null != c) throw Exceptions.nullPointer("3th argument != null");
        if (null != d) throw Exceptions.nullPointer("4th argument != null");
        if (null != e) throw Exceptions.nullPointer("5th argument != null");
    }

    /**
     * Asserts that all 6 given arguments given » are null «.
     *
     * @param a 1st argument asserted to be null.
     * @param b 2nd argument asserted to be null.
     * @param c 3rd argument asserted to be null.
     * @param d 4th argument asserted to be null.
     * @param e 5th argument asserted to be null.
     * @param f 6th argument asserted to be null.
     */
    public static void isNull(final Object a, final Object b, final Object c,
                              final Object d, final Object e, final Object f) {
        if (null != a) throw Exceptions.nullPointer("1st argument != null");
        if (null != b) throw Exceptions.nullPointer("2nd argument != null");
        if (null != c) throw Exceptions.nullPointer("3th argument != null");
        if (null != d) throw Exceptions.nullPointer("4th argument != null");
        if (null != e) throw Exceptions.nullPointer("5th argument != null");
        if (null != f) throw Exceptions.nullPointer("6th argument != null");
    }

    /**
     * Asserts that all 7 given arguments given » are null «.
     *
     * @param a 1st argument asserted to be null.
     * @param b 2nd argument asserted to be null.
     * @param c 3rd argument asserted to be null.
     * @param d 4th argument asserted to be null.
     * @param e 5th argument asserted to be null.
     * @param f 6th argument asserted to be null.
     * @param g 7th argument asserted to be null.
     */
    public static void isNull(final Object a, final Object b, final Object c,
                              final Object d, final Object e, final Object f,
                              final Object g) {
        if (null != a) throw Exceptions.nullPointer("1st argument != null");
        if (null != b) throw Exceptions.nullPointer("2nd argument != null");
        if (null != c) throw Exceptions.nullPointer("3th argument != null");
        if (null != d) throw Exceptions.nullPointer("4th argument != null");
        if (null != e) throw Exceptions.nullPointer("5th argument != null");
        if (null != f) throw Exceptions.nullPointer("6th argument != null");
        if (null != g) throw Exceptions.nullPointer("7th argument != null");
    }

    /**
     * Asserts that all 8 given arguments given » are null «.
     *
     * @param a 1st argument asserted to be null.
     * @param b 2nd argument asserted to be null.
     * @param c 3rd argument asserted to be null.
     * @param d 4th argument asserted to be null.
     * @param e 5th argument asserted to be null.
     * @param f 6th argument asserted to be null.
     * @param g 7th argument asserted to be null.
     * @param h 8th argument asserted to be null.
     */
    public static void isNull(final Object a, final Object b, final Object c,
                              final Object d, final Object e, final Object f,
                              final Object g, final Object h) {
        if (null != a) throw Exceptions.nullPointer("1st argument != null");
        if (null != b) throw Exceptions.nullPointer("2nd argument != null");
        if (null != c) throw Exceptions.nullPointer("3th argument != null");
        if (null != d) throw Exceptions.nullPointer("4th argument != null");
        if (null != e) throw Exceptions.nullPointer("5th argument != null");
        if (null != f) throw Exceptions.nullPointer("6th argument != null");
        if (null != g) throw Exceptions.nullPointer("7th argument != null");
        if (null != h) throw Exceptions.nullPointer("8th argument != null");
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual index is within the bounds of the range from
     * 0 (inclusive) to {@code count} (exclusive). The index is defined to
     * be out of bounds if any of the following inequalities is true:
     * <ul>
     *  <li>{@code idx < 0}</li>
     *  <li>{@code idx >= count}</li>
     *  <li>{@code count < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * @param idx index to be asserted.
     * @param count upper-bound (exclusive) of the index-range.
     */
    public static void index(final long idx, final long count) {
        if (idx < 0L || idx >= count) {
            throw Exceptions.outOfBounds("index(%d) is out of bounds for count(%d)", idx, count);
        }
    }

    /**
     * Asserts that the actual index is within the bounds of the actual range
     * from {@code lo} (inclusive) to {@code hi} (exclusive). The index is
     * defined to be out of bounds if any of the following inequalities is true:
     * <ul>
     *  <li>{@code idx < lo}</li>
     *  <li>{@code idx >= hi - lo}</li>
     *  <li>{@code hi - lo < 0} which is implied from the former inequalities</li>
     * </ul>
     *
     * @param idx index to be asserted.
     * @param lo lower-bound (inclusive) of the index-range.
     * @param hi upper-bound (exclusive) of the index-range.
     */
    public static void index(final long idx, final long lo, final long hi) {
        if (idx < lo || idx >= hi) {
            throw Exceptions.outOfBounds("index(%d) is out of bounds for range [%d:%d)", idx, lo, hi);
        }
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final Object[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final boolean[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final byte[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final short[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final char[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final int[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final long[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final float[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     */
    public static void index(final long idx, final double[] ary) {
        Assert.index(idx, ary.length);
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code col.size()} (exclusive).
     *
     * @param idx index to be asserted.
     * @param col collection for which the index must be valid.
     */
    public static void index(final long idx, final java.util.Collection<?> col) {
        Assert.index(idx, col.size());
    }

    /**
     * Asserts that the actual index is within the bounds of the
     * range from 0 (inclusive) to {@code csq.length()} (exclusive).
     *
     * @param idx index to be asserted.
     * @param csq sequence for which the index must be valid.
     */
    public static void index(final long idx, final CharSequence csq) {
        Assert.index(idx, csq.length());
    }

    // ----------------------------------------------------------

    /**
     * Asserts tha the actual position is within the bounds of the range from
     * 0 (inclusive) to {@code count} (inclusive). The position is defined to
     * be out of bounds if any of the following inequalities is true:
     * <ul>
     *  <li>{@code pos < 0}</li>
     *  <li>{@code pos > length}</li>
     *  <li>{@code count < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * @param pos to be checked.
     * @param count upper-bound (inclusive) of the index-range.
     */
    public static void position(final long pos, final long count) {
        if (pos < 0L || pos > count) {
            throw Exceptions.outOfBounds("position(%d) is out of bounds for count(%d)", pos, count);
        }
    }

    /**
     * Asserts that the actual position is within the bounds of the actual range
     * from {@code lo} (inclusive) to {@code hi} (exclusive). The position is
     * defined to be out of bounds if any of the following inequalities is true:
     * <ul>
     *  <li>{@code pos < lo}</li>
     *  <li>{@code pos > hi - lo}</li>
     *  <li>{@code hi - lo <= 0} which is implied from the former inequalities</li>
     * </ul>
     *
     * @param pos position to be asserted.
     * @param lo lower-bound (inclusive) of the index-range.
     * @param hi upper-bound (inclusive) of the index-range.
     */
    public static void position(final long pos, final long lo, final long hi) {
        if (pos < lo || pos > hi) {
            throw Exceptions.outOfBounds("position(%d) is out of bounds for range[%d:%d)", pos, lo, hi);
        }
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final Object[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final boolean[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final byte[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final short[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final char[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final int[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final long[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final float[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     */
    public static void position(final long pos, final double[] ary) {
        Assert.position(pos, ary.length);
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code col.size()} (inclusive).
     *
     * @param pos position to be asserted.
     * @param col collection for which the position must be valid.
     */
    public static void position(final long pos, final java.util.Collection<?> col) {
        Assert.position(pos, col.size());
    }

    /**
     * Asserts that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code csq.length()} (inclusive).
     *
     * @param pos position to be asserted.
     * @param csq sequence for which the position must be valid.
     */
    public static void position(final long pos, final CharSequence csq) {
        Assert.position(pos, csq.length());
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the range from {@code lo} (inclusive) to {@code hi} (exclusive)
     * is within in the range implicitly defined by the actual count from {@code 0}
     * (inclusive) to {@code count} (exclusive). A range is defined to be out of
     * bounds if any of the following inequalities is true:
     * <ul>
     *  <li>{@code lo < 0}</li>
     *  <li>{@code lo > hi}</li>
     *  <li>{@code hi > count}</li>
     *  <li>{@code count < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * @param lo lower-bound (inclusive).
     * @param hi upper-bound (exclusive).
     * @param count that is available.
     */
    public static void range(final long lo, final long hi, final long count) {
        if (lo < 0 || lo > hi || hi > count) {
            throw Exceptions.outOfBounds("range[%d:%d) is out of bounds for count(%d)", lo, hi, count);
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual {@code boolean[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static boolean[] notEmpty(final boolean[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
        return actual;
    }

    /**
     * Asserts that the actual {@code byte[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static void notEmpty(final byte[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
    }

    /**
     * Asserts that the actual {@code short[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static void notEmpty(final short[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
    }

    /**
     * Asserts that the actual {@code char[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static void notEmpty(final char[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
    }

    /**
     * Asserts that the actual {@code int[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static void notEmpty(final int[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
    }

    /**
     * Asserts that the actual {@code long[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static void notEmpty(final long[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
    }

    /**
     * Asserts that the actual {@code float[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static void notEmpty(final float[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
    }

    /**
     * Asserts that the actual {@code double[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static void notEmpty(final double[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
    }

    /**
     * Asserts that the actual {@code Object[]} array is not empty.
     *
     * @param actual array to be checked.
     */
    public static void notEmpty(final Object[] actual) {
        if (null == actual || 0 == actual.length) {
            throw Exceptions.illegalState("empty");
        }
    }
    
    /**
     * Asserts that the actual character sequence is empty.
     *
     * @param actual character sequence to be checked.
     */
    public static void notEmpty(final CharSequence actual) {
        if (null == actual || actual.isEmpty()) {
            throw Exceptions.illegalState("empty");
        }
    }

    /**
     * Asserts that the actual {@link Iterable} sequence is not empty.
     *
     * @param actual iterable to be checked.
     */
    public static void notEmpty(final Iterable<?> actual) {
        if (null == actual || actual instanceof java.util.Collection<?> col && col.isEmpty() || !actual.iterator().hasNext()) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("empty");
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual {@code boolean[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final boolean[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@code byte[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final byte[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@code short[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final short[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@code char[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final char[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@code int[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final int[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@code long[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final long[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@code float[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final float[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@code double[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final double[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@code Object[]} array is empty.
     *
     * @param actual array to be checked.
     */
    public static void isEmpty(final Object[] actual) {
        if (null == actual || actual.length > 0) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual character sequence is empty.
     *
     * @param actual character sequence to be checked.
     */
    public static void isEmpty(final CharSequence actual) {
        if (null == actual || !actual.isEmpty()) {
            throw Exceptions.illegalState("not empty");
        }
    }

    /**
     * Asserts that the actual {@link Iterable} sequence is empty.
     *
     * @param actual iterable to be checked.
     */
    public static void isEmpty(final Iterable<?> actual) {
        if (null == actual || actual instanceof java.util.Collection<?> col && !col.isEmpty() || actual.iterator().hasNext()) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("not empty");
        }
    }

    // ----------------------------------------------------------

    /**
     * Asserts that the actual string is not blank.
     *
     * @param actual string to be checked.
     */
    public static void notBlank(final String actual) {
        if (null == actual || 0 == actual.length()) {
            throw (null == actual) ? Exceptions.nullPointer()
                    : Exceptions.illegalState("blank string");
        }
    }

    /**
     * Asserts that the actual string matches the expected prefix.
     *
     * @param actual string to be checked.
     * @param prefix expected to be matched.
     */
    public static void hasPrefix(final String actual, final String prefix) {
        if (null == actual || null == prefix || !actual.startsWith(prefix)) {
            throw (null == actual || null == prefix)
                    ? Exceptions.nullPointer()
                    : Exceptions.illegalState("missing prefix '%s'", prefix);
        }
    }

    /**
     * Asserts that the actual string matches the expected suffix.
     * 
     * @param actual string to be checked.
     * @param suffix expected to be matched.
     */
    public static void hasSuffix(final String actual, final String suffix) {
        if (null == actual || null == suffix || !actual.endsWith(suffix)) {
            throw (null == actual || null == suffix)
                    ? Exceptions.nullPointer()
                    : Exceptions.illegalState("missing suffix '%s'", suffix);
        }
    }
}