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

package magma.exa.base;

import magma.exa.control.exception.Exceptions;

/**
 * Narrowing conversion treats expressions of a reference type {@code S}
 * as expressions of a different reference type {@code T}, where S <: T.
 *
 * @param <S> supertype of {@code T}.
 * @param <T>   subtype if {@code S}.
 */
public final class Narrow<S, T extends S> {

    /**
     * Return the given value of type {@code S} narrowed to type {@code T}.
     *
     * @param s value of type {@code S},
     * @return value narrowed to type {@code T}.
     */
    @SuppressWarnings("unchecked")
    public T apply(final S s) {
        return (T) s;
    }

    /**
     * Returns narrow-cast operator as first-class.
     *
     * @param <S> supertype of {@code T}.
     * @param <T>   subtype if {@code S}.
     * @return cast operator.
     */
    public static <S, T extends S> T cast(final S s) {
        return Narrow.<S, T> cast().apply(s);
    }

    /**
     * Returns narrow-cast operator as first-class.
     *
     * @param <S> supertype of {@code T}.
     * @param <T>   subtype if {@code S}.
     * @return cast operator.
     */
    @SuppressWarnings("unchecked")
    public static <S, T extends S> Narrow<S, T> cast() {
        return (Narrow<S, T>) operator;
    }

    // ----------------------------------------------------------

    /// OVERFLOW SENSITIVEN CONVERSIONS.

    /**
     * Returns the narrow converted {@code long -> short} result if no
     * over/underflow has occurred, otherwise an exception is thrown.
     *
     * @param val value to be narrowed.
     * @return equal {@code short} value.
     */
    public static byte I8(final short val) {
        final byte ret;
        if ((ret = (byte) val) != val) {
            throw overflow(short.class, byte.class, val);
        }
        return ret;
    }

    /**
     * Returns the narrow converted {@code int -> short} result if no
     * over/underflow has occurred, otherwise an exception is thrown.
     *
     * @param val value to be narrowed.
     * @return equal {@code short} value.
     */
    public static byte I8(final int val) {
        final byte ret;
        if ((ret = (byte) val) != val) {
            throw overflow(int.class, byte.class, val);
        }
        return ret;
    }

    /**
     * Returns the narrow converted {@code long -> byte} result if no
     * over/underflow has occurred, otherwise an exception is thrown.
     *
     * @param val value to be narrowed.
     * @return equal {@code short} value.
     */
    public static byte I8(final long val) {
        final byte ret;
        if ((ret = (byte) val) != val) {
            throw overflow(long.class, byte.class, val);
        }
        return ret;
    }

    // ----------------------------------------------------------

    /**
     * Returns the narrow converted {@code int -> short} result if no
     * over/underflow has occurred, otherwise an exception is thrown.
     *
     * @param val value to be narrowed.
     * @return equal {@code short} value.
     */
    public static short I16(final int val) {
        final short ret;
        if ((ret = (short) val) != val) {
            throw overflow(int.class, short.class, val);
        }
        return ret;
    }

    /**
     * Returns the narrow converted {@code long -> short} result if no
     * over/underflow has occurred, otherwise an exception is thrown.
     *
     * @param val value to be narrowed.
     * @return equal {@code short} value.
     */
    public static short I16(final long val) {
        final short ret;
        if ((ret = (short) val) != val) {
            throw overflow(long.class, short.class, val);
        }
        return ret;
    }

    // ----------------------------------------------------------

    /**
     * Returns the narrow converted {@code int -> char} result if no
     * over/underflow has occurred, otherwise an exception is thrown.
     *
     * @param val value to be narrowed.
     * @return equal {@code char} value.
     */
    public static char C16(final int val) {
        final char ret;
        if ((ret = (char) val) != val) {
            throw overflow(int.class, char.class, val);
        }
        return ret;
    }

    /**
     * Returns the narrow converted {@code long -> char} result if no
     * over/underflow has occurred, otherwise an exception is thrown.
     *
     * @param val value to be narrowed.
     * @return equal {@code char} value.
     */
    public static char C16(final long val) {
        final char ret;
        if ((ret = (char) val) != val) {
            throw overflow(long.class, char.class, val);
        }
        return ret;
    }

    // ----------------------------------------------------------

    /**
     * Returns the narrow converted {@code long -> int} result if no
     * over/underflow has occurred, otherwise an exception is thrown.
     *
     * @param val value to be narrowed.
     * @return equal {@code int} value.
     */
    public static int I32(final long val) {
        final int ret;
        if ((ret = (int) val) != val) {
            throw overflow(long.class, int.class, val);
        }
        return ret;
    }

    // ----------------------------------------------------------

    /**
     * Returns a numeric overflow exception.
     */
    private static
    IllegalArgumentException overflow(Class<?> src, Class<?> dst, Number val) {
        return Exceptions.illegalArgument("numeric overflow: %s(%s) => %s",
                src.getName(), val, dst.getName());
    }

    // ----------------------------------------------------------

    /** Singleton. */
    private Narrow() { }
    static { operator = new Narrow<>(); }
    private static final Narrow<?, ?> operator;
}
