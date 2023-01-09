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
 * Require expressions evaluate conditions before continuing with
 * program execution. If a specified condition evaluates to false,
 * execution is halted. Require expressions are used to express
 * invariants, pre-conditions, and post-conditions to validate
 * assumptions in the code and thus detect programmer errors early
 * and unambiguously at runtime. {@link Require}-expressions return
 * their inputs and are to be used within expression contexts to
 * validate values.
 *
 * Additionally, they serve to document the inner workings of
 * the program during development and improve maintainability.
 */
public enum Require {
    ;
    /**
     * Requires that the actual value is true.
     *
     * @param actual value asserted to be true.
     * @return true.
     */
    public static boolean isTrue(final boolean actual) {
        if (!actual) {
            throw Exceptions.illegalState("actual = false");
        }
        return true;
    }
    
    /**
     * Requires that the actual value is false.
     *
     * @param actual value asserted to be false.
     * @return false.            
     */
    public static boolean isFalse(final boolean actual) {
        if (actual) {
            throw Exceptions.illegalState("actual = true");
        }
        return false;
    }
    
    // ----------------------------------------------------------
    
    /**
     * Requires that the actual {@code boolean} value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static boolean equal(final boolean actual, final boolean expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code byte} value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static byte equal(final byte actual, final byte expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code short} value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static short equal(final short actual, final short expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code char} value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static char equal(final char actual, final char expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code int} value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static int equal(final int actual, final int expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code long} value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static long equal(final long actual, final long expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code float} value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static float equal(final float actual, final float expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code double} value is equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static double equal(final double actual, final double expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual reference value of type {@code A} is identical 
     * to the expected reference value.
     *
     * @param actual   value asserted to be identical.
     * @param expected value to which actual must be identical.
     * @return {@code actual}.
     */
    public static <A> A same(final A actual, final A expected) {
        if (actual != expected) {
            throw Exceptions.illegalState("expected(%s) != actual(%s)", expected, actual);
        }
        return actual;
    }
    
    // ----------------------------------------------------------

    /**
     * Requires that the actual {@code boolean} value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static boolean notEqual(final boolean actual, final boolean expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code byte} value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static byte notEqual(final byte actual, final byte expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code short} value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static short notEqual(final short actual, final short expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code char} value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static char notEqual(final char actual, final char expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code int} value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static int notEqual(final int actual, final int expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code long} value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static long notEqual(final long actual, final long expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code float} value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static float notEqual(final float actual, final float expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual {@code double} value is not equal to the expected value.
     *
     * @param actual   value asserted to be equal.
     * @param expected value to which actual must be equal.
     * @return {@code actual}.
     */
    public static double notEqual(final double actual, final double expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    /**
     * Requires that the actual reference value of type {@code A} is not identical 
     * to the expected reference value.
     *
     * @param actual   value asserted to be identical.
     * @param expected value to which actual must be identical.
     * @return {@code actual}.
     */
    public static <A> A notSame(final A actual, final A expected) {
        if (actual == expected) {
            throw Exceptions.illegalState("expected(%s) == actual(%s)", expected, actual);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code byte} value is strictly positive (actual > 0).
     *
     * @param actual value required to be positive.
     * @return {@code actual}.
     */
    public static byte positive(final byte actual) {
        if (actual <= 0) {
            throw Exceptions.illegalState("actual(%d) <= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code short} value is strictly positive (actual > 0).
     *
     * @param actual value required to be positive.
     * @return {@code actual}.
     */
    public static short positive(final short actual) {
        if (actual <= 0) {
            throw Exceptions.illegalState("actual(%d) <= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code int} value is strictly positive (actual > 0).
     *
     * @param actual value required to be positive.
     * @return {@code actual}.
     */
    public static int positive(final int actual) {
        if (actual <= 0) {
            throw Exceptions.illegalState("actual(%d) <= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code long} value is strictly positive (actual > 0).
     *
     * @param actual value required to be positive.
     * @return {@code actual}.
     */
    public static long positive(final long actual) {
        if (actual <= 0) {
            throw Exceptions.illegalState("actual(%d) <= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code float} value is strictly positive (actual > 0).
     *
     * @param actual value required to be positive.
     * @return {@code actual}.
     */
    public static float positive(final float actual) {
        if (actual <= 0) {
            throw Exceptions.illegalState("actual(%d) <= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code double} value is strictly positive (actual > 0).
     *
     * @param actual value required to be positive.
     * @return {@code actual}.
     */
    public static double positive(final double actual) {
        if (actual <= 0) {
            throw Exceptions.illegalState("actual(%d) <= 0", actual);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code byte} value is not positive (actual <= 0).
     *
     * @param actual value required to be not positive.
     * @return {@code actual}.
     */
    public static byte notPositive(final byte actual) {
        if (actual > 0) {
            throw Exceptions.illegalState("actual(%d) > 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code short} value is not positive (actual <= 0).
     *
     * @param actual value required to be not positive.
     * @return {@code actual}.
     */
    public static short notPositive(final short actual) {
        if (actual > 0) {
            throw Exceptions.illegalState("actual(%d) > 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code int} value is not positive (actual <= 0).
     *
     * @param actual value required to be not positive.
     * @return {@code actual}.
     */
    public static int notPositive(final int actual) {
        if (actual > 0) {
            throw Exceptions.illegalState("actual(%d) > 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code long} value is not positive (actual <= 0).
     *
     * @param actual value required to be not positive.
     * @return {@code actual}.
     */
    public static long notPositive(final long actual) {
        if (actual > 0) {
            throw Exceptions.illegalState("actual(%d) > 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code float} value is not positive (actual <= 0).
     *
     * @param actual value required to be not positive.
     * @return {@code actual}.
     */
    public static float notPositive(final float actual) {
        if (actual > 0) {
            throw Exceptions.illegalState("actual(%d) > 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code double} value is not positive (actual <= 0).
     *
     * @param actual value required to be not positive.
     * @return {@code actual}.
     */
    public static double notPositive(final double actual) {
        if (actual > 0) {
            throw Exceptions.illegalState("actual(%d) > 0", actual);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code byte} value is strictly negative (actual < 0).
     *
     * @param actual value required to be negative.
     * @return {@code actual}.
     */
    public static byte negative(final byte actual) {
        if (actual >= 0) {
            throw Exceptions.illegalState("actual(%d) >= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code short} value is strictly negative (actual < 0).
     *
     * @param actual value required to be negative.
     * @return {@code actual}.
     */
    public static short negative(final short actual) {
        if (actual >= 0) {
            throw Exceptions.illegalState("actual(%d) >= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code int} value is strictly negative (actual < 0).
     *
     * @param actual value required to be negative.
     * @return {@code actual}.
     */
    public static int negative(final int actual) {
        if (actual >= 0) {
            throw Exceptions.illegalState("actual(%d) >= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code long} value is strictly negative (actual < 0).
     *
     * @param actual value required to be negative.
     * @return {@code actual}.
     */
    public static long negative(final long actual) {
        if (actual >= 0) {
            throw Exceptions.illegalState("actual(%d) >= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code float} value is strictly negative (actual < 0).
     *
     * @param actual value required to be negative.
     * @return {@code actual}.
     */
    public static float negative(final float actual) {
        if (actual >= 0) {
            throw Exceptions.illegalState("actual(%d) >= 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code double} value is strictly negative (actual < 0).
     *
     * @param actual value required to be negative.
     * @return {@code actual}.
     */
    public static double negative(final double actual) {
        if (actual >= 0) {
            throw Exceptions.illegalState("actual(%d) >= 0", actual);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code byte} value is not negative (actual >= 0).
     *
     * @param actual value required to be not negative.
     * @return {@code actual}.
     */
    public static byte notNegative(final byte actual) {
        if (actual < 0) {
            throw Exceptions.illegalState("actual(%d) < 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code short} value is not negative (actual >= 0).
     *
     * @param actual value required to be not negative.
     * @return {@code actual}.
     */
    public static short notNegative(final short actual) {
        if (actual < 0) {
            throw Exceptions.illegalState("actual(%d) < 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code int} value is not negative (actual >= 0).
     *
     * @param actual value required to be not negative.
     * @return {@code actual}.
     */
    public static int notNegative(final int actual) {
        if (actual < 0) {
            throw Exceptions.illegalState("actual(%d) < 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code long} value is not negative (actual >= 0).
     *
     * @param actual value required to be not negative.
     * @return {@code actual}.
     */
    public static long notNegative(final long actual) {
        if (actual < 0) {
            throw Exceptions.illegalState("actual(%d) < 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code float} value is not negative (actual >= 0).
     *
     * @param actual value required to be not negative.
     * @return {@code actual}.
     */
    public static float notNegative(final float actual) {
        if (actual < 0) {
            throw Exceptions.illegalState("actual(%d) < 0", actual);
        }
        return actual;
    }

    /**
     * Requires that the given {@code double} value is not negative (actual >= 0).
     *
     * @param actual value required to be not negative.
     * @return {@code actual}.
     */
    public static double notNegative(final double actual) {
        if (actual < 0) {
            throw Exceptions.illegalState("actual(%d) < 0", actual);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code byte} value is less than the expected value (actual < expected).
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     * @return {@code actual}.            
     */
    public static byte lessThan(final byte actual, final byte expected) {
        if (actual >= expected) {
            throw Exceptions.illegalState("actual(%d) >= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code short} value is less than the expected value (actual < expected).
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     * @return {@code actual}.            
     */
    public static short lessThan(final short actual, final short expected) {
        if (actual >= expected) {
            throw Exceptions.illegalState("actual(%d) >= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code int} value is less than the expected value (actual < expected).
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     * @return {@code actual}.            
     */
    public static int lessThan(final int actual, final int expected) {
        if (actual >= expected) {
            throw Exceptions.illegalState("actual(%d) >= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code long} value is less than the expected value (actual < expected).
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     * @return {@code actual}.            
     */
    public static long lessThan(final long actual, final long expected) {
        if (actual >= expected) {
            throw Exceptions.illegalState("actual(%d) >= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code float} value is less than the expected value (actual < expected).
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     * @return {@code actual}.            
     */
    public static float lessThan(final float actual, final float expected) {
        if (actual >= expected) {
            throw Exceptions.illegalState("actual(%d) >= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code double} value is less than the expected value (actual < expected).
     *
     * @param actual   value required to be less than.
     * @param expected value to be greater or equal to actual.
     * @return {@code actual}.            
     */
    public static double lessThan(final double actual, final double expected) {
        if (actual >= expected) {
            throw Exceptions.illegalState("actual(%d) >= expected(%d)", actual, expected);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code byte} value is less than or equal to the expected value (actual <= expected).
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     * @return {@code actual}.   
     */
    public static byte lessEqual(final byte actual, final byte expected) {
        if (actual > expected) {
            throw Exceptions.illegalState("actual(%d) > expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code short} value is less than or equal to the expected value (actual <= expected).
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     * @return {@code actual}.   
     */
    public static short lessEqual(final short actual, final short expected) {
        if (actual > expected) {
            throw Exceptions.illegalState("actual(%d) > expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code int} value is less than or equal to the expected value (actual <= expected).
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     * @return {@code actual}.   
     */
    public static int lessEqual(final int actual, final int expected) {
        if (actual > expected) {
            throw Exceptions.illegalState("actual(%d) > expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code long} value is less than or equal to the expected value (actual <= expected).
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     * @return {@code actual}.   
     */
    public static long lessEqual(final long actual, final long expected) {
        if (actual > expected) {
            throw Exceptions.illegalState("actual(%d) > expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code float} value is less than or equal to the expected value (actual <= expected).
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     * @return {@code actual}.   
     */
    public static float lessEqual(final float actual, final float expected) {
        if (actual > expected) {
            throw Exceptions.illegalState("actual(%d) > expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code double} value is less than or equal to the expected value (actual <= expected).
     *
     * @param actual   value required to be less or equal.
     * @param expected value to be greater than actual.
     * @return {@code actual}.   
     */
    public static double lessEqual(final double actual, final double expected) {
        if (actual > expected) {
            throw Exceptions.illegalState("actual(%d) > expected(%d)", actual, expected);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code byte} value is greater than the expected value (actual > expected).
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less than actual.
     * @return {@code actual}.   
     */
    public static byte greaterThan(final byte actual, final byte expected) {
        if (actual <= expected) {
            throw Exceptions.illegalState("actual(%d) <= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code short} value is greater than the expected value (actual > expected).
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less than actual.
     * @return {@code actual}.   
     */
    public static short greaterThan(final short actual, final short expected) {
        if (actual <= expected) {
            throw Exceptions.illegalState("actual(%d) <= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code int} value is greater than the expected value (actual > expected).
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less than actual.
     * @return {@code actual}.   
     */
    public static int greaterThan(final int actual, final int expected) {
        if (actual <= expected) {
            throw Exceptions.illegalState("actual(%d) <= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code long} value is greater than the expected value (actual > expected).
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less than actual.
     * @return {@code actual}.   
     */
    public static long greaterThan(final long actual, final long expected) {
        if (actual <= expected) {
            throw Exceptions.illegalState("actual(%d) <= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code float} value is greater than the expected value (actual > expected).
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less than actual.
     * @return {@code actual}.   
     */
    public static float greaterThan(final float actual, final float expected) {
        if (actual <= expected) {
            throw Exceptions.illegalState("actual(%d) <= expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code double} value is greater than the expected value (actual > expected).
     *
     * @param actual   value required to be greater than.
     * @param expected value to be less than actual.
     * @return {@code actual}.   
     */
    public static double greaterThan(final double actual, final double expected) {
        if (actual <= expected) {
            throw Exceptions.illegalState("actual(%d) <= expected(%d)", actual, expected);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code byte} value is greater than or equal to the expected value (actual >= expected).
     *
     * @param actual   value required to be greater than or equal.
     * @param expected value to be less or equal to actual.
     * @return {@code actual}.   
     */
    public static byte greaterEqual(final byte actual, final byte expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code short} value is greater than or equal to the expected value (actual >= expected).
     *
     * @param actual   value required to be greater than or equal.
     * @param expected value to be less or equal to actual.
     * @return {@code actual}.   
     */
    public static short greaterEqual(final short actual, final short expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code int} value is greater than or equal to the expected value (actual >= expected).
     *
     * @param actual   value required to be greater than or equal.
     * @param expected value to be less or equal to actual.
     * @return {@code actual}.   
     */
    public static int greaterEqual(final int actual, final int expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code long} value is greater than or equal to the expected value (actual >= expected).
     *
     * @param actual   value required to be greater than or equal.
     * @param expected value to be less or equal to actual.
     * @return {@code actual}.   
     */
    public static long greaterEqual(final long actual, final long expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code float} value is greater than or equal to the expected value (actual >= expected).
     *
     * @param actual   value required to be greater than or equal.
     * @param expected value to be less or equal to actual.
     * @return {@code actual}.   
     */
    public static float greaterEqual(final float actual, final float expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
        return actual;
    }

    /**
     * Requires that the given {@code double} value is greater than or equal to the expected value (actual >= expected).
     *
     * @param actual   value required to be greater than or equal.
     * @param expected value to be less or equal to actual.
     * @return {@code actual}.   
     */
    public static double greaterEqual(final double actual, final double expected) {
        if (actual < expected) {
            throw Exceptions.illegalState("actual(%d) < expected(%d)", actual, expected);
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given value is not null.
     *
     * @param actual argument, which must not be null.
     * @return {@code actual}.
     */
    public static <A> A notNull(final A actual) {
        if (null == actual) {
            throw Exceptions.nullPointer("actual == null");
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given value is not null.
     *
     * @param actual argument, which must not be null.
     * @return null.
     */
    public static <A> A isNull(final A actual) {
        if (actual != null) {
            throw Exceptions.illegalState("actual != null");
        }
        return null;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given {@code int} index is within the bounds of 
     * the range from 0 (inclusive) to {@code count} (exclusive). The index
     * is defined to be out of bounds if any of the following inequalities
     * is true:
     * <ul>
     *  <li>{@code idx < 0}</li>
     *  <li>{@code idx >= count}</li>
     *  <li>{@code count < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * @param idx index to be checked.
     * @param count upper-bound (exclusive) of the index-range.
     * @return {@code idx}.
     */
    public static int index(final int idx, final long count) {
        if (idx < 0L || idx >= count) {
            throw Exceptions.outOfBounds("index(%d) is out of bounds for count(%d)", idx, count);
        }
        return idx;
    }

    /**
     * Requires that the given {@code long} index is within the bounds of 
     * the range from 0 (inclusive) to {@code count} (exclusive). The index
     * is defined to be out of bounds if any of the following inequalities
     * is true:
     * <ul>
     *  <li>{@code idx < 0}</li>
     *  <li>{@code idx >= count}</li>
     *  <li>{@code count < 0}, which is implied from the former inequalities</li>
     * </ul>
     *
     * @param idx index to be checked.
     * @param count upper-bound (exclusive) of the index-range.
     * @return {@code idx}.
     */
    public static long index(final long idx, final long count) {
        if (idx < 0L || idx >= count) {
            throw Exceptions.outOfBounds("index(%d) is out of bounds for count(%d)", idx, count);
        }
        return idx;
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final Object[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final boolean[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final byte[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final short[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final char[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final int[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final long[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final float[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (exclusive).
     *
     * @param idx index to be asserted.
     * @param ary array for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final double[] ary) {
        return Require.index(idx, ary.length);
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code col.size()} (exclusive).
     *
     * @param idx index to be asserted.
     * @param col collection for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final java.util.Collection<?> col) {
        return Require.index(idx, col.size());
    }

    /**
     * Requires that the given index is within the bounds of the
     * range from 0 (inclusive) to {@code csq.length()} (exclusive).
     *
     * @param idx index to be asserted.
     * @param csq sequence for which the index must be valid.
     * @return {@code idx}.
     */
    public static int index(final int idx, final CharSequence csq) {
        return Require.index(idx, csq.length());
    }
    
    // ----------------------------------------------------------
    
    /**
     * Requires that the given {@code int} index is within the bounds of the 
     * given range from {@code lo} (inclusive) to {@code hi} (exclusive). The 
     * index is defined to be out of bounds if any of the following inequalities
     * is true:
     * <ul>
     *  <li>{@code idx < lo}</li>
     *  <li>{@code idx >= hi - lo}</li>
     *  <li>{@code hi - lo < 0} which is implied from the former inequalities</li>
     * </ul>
     *
     * @param idx index to be checked.
     * @param lo lower-bound (inclusive) of the index-range.
     * @param hi upper-bound (exclusive) of the index-range.
     * @return {@code idx}.
     */
    public static int index(final int idx, final long lo, final long hi) {
        if (idx < lo || idx >= hi) {
            throw Exceptions.outOfBounds("index(%d) is out of bounds for range [%d:%d)", idx, lo, hi);
        }
        return idx;
    }

    /**
     * Requires that the given {@code long} index is within the bounds of the 
     * given range from {@code lo} (inclusive) to {@code hi} (exclusive). The 
     * index is defined to be out of bounds if any of the following inequalities
     * is true:
     * <ul>
     *  <li>{@code idx < lo}</li>
     *  <li>{@code idx >= hi - lo}</li>
     *  <li>{@code hi - lo < 0} which is implied from the former inequalities</li>
     * </ul>
     *
     * @param idx index to be checked.
     * @param lo lower-bound (inclusive) of the index-range.
     * @param hi upper-bound (exclusive) of the index-range.
     * @return {@code idx}.
     */
    public static long index(final long idx, final long lo, final long hi) {
        if (idx < lo || idx >= hi) {
            throw Exceptions.outOfBounds("index(%d) is out of bounds for range [%d:%d)", idx, lo, hi);
        }
        return idx;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the actual position is within the bounds of the range from
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
     * @return {@code pos}.
     */
    public static int position(final int pos, final long count) {
        if (pos < 0L || pos > count) {
            throw Exceptions.outOfBounds("position(%d) is out of bounds for count(%d)", pos, count);
        }
        return pos;
    }

    /**
     * Requires that the actual position is within the bounds of the actual range
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
     * @return {@code pos}.
     */
    public static int position(final int pos, final long lo, final long hi) {
        if (pos < lo || pos > hi) {
            throw Exceptions.outOfBounds("position(%d) is out of bounds for range[%d:%d)", pos, lo, hi);
        }
        return pos;
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final Object[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final boolean[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final byte[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final short[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final char[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final int[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final long[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final float[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code ary.length} (inclusive).
     *
     * @param pos position to be asserted.
     * @param ary array for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final double[] ary) {
        return Require.position(pos, ary.length);
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code col.size()} (inclusive).
     *
     * @param pos position to be asserted.
     * @param col collection for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final java.util.Collection<?> col) {
        return Require.position(pos, col.size());
    }

    /**
     * Requires that the actual position is within the bounds of the
     * range from 0 (inclusive) to {@code csq.length()} (inclusive).
     *
     * @param pos position to be asserted.
     * @param csq sequence for which the position must be valid.
     * @return {@code pos}.
     */
    public static int position(final int pos, final CharSequence csq) {
        return Require.position(pos, csq.length());
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given array of type {@code A} is not empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static <A> A[] notEmpty(final A[] actual) {
        if (null == actual || actual.length == 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code boolean[]} array is not empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static boolean[] notEmpty(final boolean[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code byte[]} array is not empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static byte[] notEmpty(final byte[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code short[]} array is not empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static short[] notEmpty(final short[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code char[]} array is not empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static char[] notEmpty(final char[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code int[]} array is not empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static int[] notEmpty(final int[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code long[]} array is not empty
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static long[] notEmpty(final long[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code float[]} array is not empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static float[] notEmpty(final float[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code double[]} array is not empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static double[] notEmpty(final double[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@link Iterable} sequence is not empty.
     *
     * @param actual iterable to be checked.
     * @return {@code actual}.
     */
    public static <C extends Iterable<?>> C notEmpty(final C actual) {
        if (null == actual || actual instanceof java.util.Collection<?> col && col.isEmpty() || !actual.iterator().hasNext()) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    /**
     * Requires that the given character sequence is not empty.
     *
     * @param actual character sequence to be checked.
     * @return {@code actual}.
     */
    public static <C extends CharSequence> C notEmpty(final C actual) {
        if (null == actual || actual.isEmpty()) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is empty");
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given array of type {@code A} is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static <A> A[] empty(final A[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code boolean[]} array is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static boolean[] empty(final boolean[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code byte[]} array is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static byte[] empty(final byte[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code short[]} array is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static short[] empty(final short[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code char[]} array is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static char[] empty(final char[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code int[]} array is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static int[] empty(final int[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code long[]} array is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static long[] empty(final long[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code float[]} array is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static float[] empty(final float[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@code double[]} array is empty.
     *
     * @param actual array to be checked.
     * @return {@code actual}.
     */
    public static double[] empty(final double[] actual) {
        if (null == actual || actual.length > 0) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given {@link Iterable} sequence is empty.
     *
     * @param actual iterable to be checked.
     * @return {@code actual}.
     */
    public static <C extends Iterable<?>> C empty(final C actual) {
        if (null == actual || actual instanceof java.util.Collection<?> col && !col.isEmpty() || actual.iterator().hasNext()) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    /**
     * Requires that the given character sequence is empty.
     *
     * @param actual character sequence to be checked.
     * @return {@code actual}.
     */
    public static <C extends CharSequence> C empty(final C actual) {
        if (null == actual || !actual.isEmpty()) {
            throw (null == actual) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not empty");
        }
        return actual;
    }

    // ----------------------------------------------------------

    /**
     * Requires that the given object is an instance of the given type.
     *
     * @param actual object to be checked.
     * @param type of required to be an instance of.
     * @param <T> type of checked object.
     * @return {@code actual}.
     */
    public static <T> T instanceOf(final T actual, final Class<?> type) {
        if (null == type || !type.isInstance(actual)) {
            throw (null == type) ? Exceptions.nullPointer() : Exceptions.illegalState("actual is not instance of '%s'", type);
        }
        return actual;
    }
}
