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

package magma.exa.data;

import magma.exa.base.Bit;
import magma.exa.base.Force;
import magma.exa.base.Narrow;
import magma.exa.base.contract.Assert;
import magma.exa.control.exception.Exceptions;
import magma.exa.control.exception.TODO;
import magma.exa.control.exception.Throw;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn2;
import magma.exa.data.misc.Sorting;
import magma.exa.data.index.Range;
import magma.exa.value.scalar.*;

/**
 * Collection of array query and manipulation operations
 */
public enum Array {
    ;
    /** A constant holding the maximum number of array dimensions. */
    public static final int MAX_RANK = 255;

    /** A constant holding the maximum number of allocatable array cells. */
    public static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;

    /** A constant holding the default capacity for array allocations. */
    public static final int INITIAL_CAPACITY = 8;

    // ----------------------------------------------------------

    /** A constant holding the empty {@code Object[]} array. */
    public static final Object[] empty = { };

    /** A constant holding the empty {@link boolean[]} array. */
    public static final boolean[] emptyBool = { };

    /** A constant holding the empty {@link byte[]} array. */
    public static final byte[] emptyI8 = { };

    /** A constant holding the empty {@link byte[]} array. */
    public static final short[] emptyI16 = { };

    /** A constant holding the empty {@link char[]} array. */
    public static final char[] emptyC16 = { };

    /** A constant holding the empty {@link int[]} array. */
    public static final int[] emptyI32 = { };

    /** A constant holding the empty {@link long[]} array. */
    public static final long[] emptyI64 = { };

    /** A constant holding the empty {@link float[]} array. */
    public static final float[] emptyF32 = { };

    /** A constant holding the empty {@link double[]} array. */
    public static final double[] emptyF64 = { };


    // ----------------------------------------------------------

    /** ´Returns the empty array of type {@code A[]}. */
    public static <A> A[] empty() { return Force.cast(empty); }

    public static <A> A[] empty(final Object[] src)  { return empty(src.getClass()); }

    public static <A> A[] empty(final Class<?> type) { return !type.isArray() ? Force.cast(java.lang.reflect.Array.newInstance(type, 0)) : empty(type.componentType()); }

    // ----------------------------------------------------------

    @SafeVarargs public static <A> A[] def(final A... ary) { return Array.copy(ary); }

    // ----------------------------------------------------------

    /** Returns the length of the queried {@code Object[]} array or -1 iff null. */
    public static int length(final Object[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried {@code boolean[]} array or -1 iff null. */
    public static int length(final boolean[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried {@code byte[]} array or -1 iff null. */
    public static int length(final byte[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried {@code short[]} array or -1 iff null. */
    public static int length(final short[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried {@code char[]} array or -1 iff null. */
    public static int length(final char[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried {@code int[]} array or -1 iff null. */
    public static int length(final int[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried {@code long[]} array or -1 iff null. */
    public static int length(final long[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried {@code float[]} array or -1 iff null. */
    public static int length(final float[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried {@code double[]} array or -1 iff null. */
    public static int length(final double[] src) { return src == null ? -1 : src.length; }

    /** Returns the length of the queried array or -1 iff null or not of array type. */
    public static int length(final Object src) {
        return src == null || !src.getClass().isArray() ? -1 : java.lang.reflect.Array.getLength(src);
    }

    // ----------------------------------------------------------

    /** Determines whether the queried {@code Object[]} array is empty or null. */
    public static boolean isNullOrEmpty(final Object[] src) { return length(src) <= 0; }

    /** Determines whether the queried {@code boolean[]} array is empty or null. */
    public static boolean isNullOrEmpty(final boolean[] src) { return length(src) <= 0; }

    /** Determines whether the queried {@code byte[]} array is empty or null. */
    public static boolean isNullOrEmpty(final byte[] src) { return length(src) <= 0; }

    /** Determines whether the queried {@code short[]} array is empty or null. */
    public static boolean isNullOrEmpty(final short[] src) { return length(src) <= 0; }

    /** Determines whether the queried {@code char[]} array is empty or null. */
    public static boolean isNullOrEmpty(final char[] src) { return length(src) <= 0; }

    /** Determines whether the queried {@code int[]} array is empty or null. */
    public static boolean isNullOrEmpty(final int[] src) { return length(src) <= 0; }

    /** Determines whether the queried {@code long[]} array is empty or null. */
    public static boolean isNullOrEmpty(final long[] src) { return length(src) <= 0; }

    /** Determines whether the queried {@code float[]} array is empty or null. */
    public static boolean isNullOrEmpty(final float[] src) { return length(src) <= 0; }

    /** Determines whether the queried {@code double[]} array is empty or null. */
    public static boolean isNullOrEmpty(final double[] src) { return length(src) <= 0; }

    // ----------------------------------------------------------

    /** Determines whether the queried {@code Object[]} array has length 0. */
    public static boolean isEmpty(final Object[] src) { return length(src) == 0; }

    /** Determines whether the queried {@code boolean[]} array has length 0. */
    public static boolean isEmpty(final boolean[] src) { return length(src) == 0; }

    /** Determines whether the queried {@code byte[]} array has length 0. */
    public static boolean isEmpty(final byte[] src) { return length(src) == 0; }

    /** Determines whether the queried {@code short[]} array has length 0. */
    public static boolean isEmpty(final short[] src) { return length(src) == 0; }

    /** Determines whether the queried {@code char[]} array has length 0. */
    public static boolean isEmpty(final char[] src) { return length(src) == 0; }

    /** Determines whether the queried {@code int[]} array has length 0. */
    public static boolean isEmpty(final int[] src) { return length(src) == 0; }

    /** Determines whether the queried {@code long[]} array has length 0. */
    public static boolean isEmpty(final long[] src) { return length(src) == 0; }

    /** Determines whether the queried {@code float[]} array has length 0. */
    public static boolean isEmpty(final float[] src) { return length(src) == 0; }

    /** Determines whether the queried {@code double[]} array has length 0. */
    public static boolean isEmpty(final double[] src) { return length(src) == 0; }

    // ----------------------------------------------------------

    /** Determines whether the queried {@code Object[]} array contains any values. */
    public static boolean isPresent(final Object[] src) { return length(src) > 0; }

    /** Determines whether the queried {@code boolean[]} array contains any values. */
    public static boolean isPresent(final boolean[] src) { return length(src) > 0; }

    /** Determines whether the queried {@code byte[]} array contains any values. */
    public static boolean isPresent(final byte[] src) { return length(src) > 0; }

    /** Determines whether the queried {@code short[]} array contains any values. */
    public static boolean isPresent(final short[] src) { return length(src) > 0; }

    /** Determines whether the queried {@code char[]} array contains any values. */
    public static boolean isPresent(final char[] src) { return length(src) > 0; }

    /** Determines whether the queried {@code int[]} array contains any values. */
    public static boolean isPresent(final int[] src) { return length(src) > 0; }

    /** Determines whether the queried {@code long[]} array contains any values. */
    public static boolean isPresent(final long[] src) { return length(src) > 0; }

    /** Determines whether the queried {@code float[]} array contains any values. */
    public static boolean isPresent(final float[] src) { return length(src) > 0; }

    /** Determines whether the queried {@code double[]} array contains any values. */
    public static boolean isPresent(final double[] src) { return length(src) > 0; }

    // ----------------------------------------------------------

    /**
     * Returns the boxed {@link Boolean[]} array conversion
     * of the given primitive {@code boolean[]} array.
     */
    public static Boolean[] box(final boolean[] src) {
        final var out = new Boolean[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i];
        }
        return out;
    }

    /**
     * Returns the boxed {@link Byte[]} array conversion
     * of the given primitive {@code byte[]} array.
     */
    public static Byte[] box(final byte[] src) {
        final var out = new Byte[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i];
        }
        return out;
    }

    /**
     * Returns the boxed {@link Short[]} array conversion
     * of the given primitive {@code short[]} array.
     */
    public static Short[] box(final short[] src) {
        final var out = new Short[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i];
        }
        return out;
    }

    /**
     * Returns the boxed {@link Character[]} array conversion
     * of the given primitive {@code char[]} array.
     */
    public static Character[] box(final char[] src) {
        final var out = new Character[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i];
        }
        return out;
    }

    /**
     * Returns the boxed {@link Integer[]} array conversion
     * of the given primitive {@code int[]} array.
     */
    public static Integer[] box(final int[] src) {
        final var out = new Integer[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i];
        }
        return out;
    }

    /**
     * Returns the boxed {@link Long[]} array conversion
     * of the given primitive {@code long[]} array.
     */
    public static Long[] box(final long[] src) {
        final var out = new Long[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i];
        }
        return out;
    }

    /**
     * Returns the boxed {@link Float[]} array conversion
     * of the given primitive {@code float[]} array.
     */
    public static Float[] box(final float[] src) {
        final var out = new Float[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i];
        }
        return out;
    }

    /**
     * Returns the boxed {@link Double[]} array conversion
     * of the given primitive {@code double[]} array.
     */
    public static Double[] box(final double[] src) {
        final var out = new Double[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i];
        }
        return out;
    }

    // ----------------------------------------------------------

    /**
     * Returns the primitive {@code boolean[]} array conversion
     * of the given boxed {@link Boolean[]} array.
     * <p>
     * {@code null} values are interpreted as false.
     */
    public static boolean[] unbox(final Boolean[] src) {
        final var out = new boolean[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i] != null && src[i];
        }
        return out;
    }

    /**
     * Returns the primitive {@code byte[]} array conversion
     * of the given boxed {@link Byte[]} array.
     * <p>
     * {@code null} values are replaced by {@code 0}.
     */
    public static byte[] unbox(final Byte[] src) {
        final var out = new byte[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i] == null ? 0 : src[i];
        }
        return out;
    }

    /**
     * Returns the primitive {@code short[]} array conversion
     * of the given boxed {@link Short[]} array.
     * <p>
     * {@code null} values are replaced by {@code 0}.
     */
    public static short[] unbox(final Short[] src) {
        final var out = new short[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i] == null ? 0 : src[i];
        }
        return out;
    }

    /**
     * Returns the primitive {@code char[]} array conversion
     * of the given boxed {@link Character[]} array.
     * <p>
     * {@code null} values are replaced by {@code 0}.
     */
    public static char[] unbox(final Character[] src) {
        final var out = new char[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i] == null ? 0 : src[i];
        }
        return out;
    }

    /**
     * Returns the primitive {@code int[]} array conversion
     * of the given boxed {@link Integer[]} array.
     * <p>
     * {@code null} values are replaced by {@code 0}.
     */
    public static int[] unbox(final Integer[] src) {
        final var out = new int[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i] == null ? 0 : src[i];
        }
        return out;
    }

    /**
     * Returns the primitive {@code long[]} array conversion
     * of the given boxed {@link Long[]} array.
     * <p>
     * {@code null} values are replaced by {@code 0}.
     */
    public static long[] unbox(final Long[] src) {
        final var out = new long[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i] == null ? 0 : src[i];
        }
        return out;
    }

    /**
     * Returns the primitive {@code float[]} array conversion
     * of the given boxed {@link Float[]} array.
     * <p>
     * {@code null} values are replaced by {@code 0}.
     */
    public static float[] unbox(final Float[] src) {
        final var out = new float[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i] == null ? 0 : src[i];
        }
        return out;
    }

    /**
     * Returns the primitive {@code double[]} array conversion
     * of the given boxed {@link Double[]} array.
     * <p>
     * {@code null} values are replaced by {@code 0}.
     */
    public static double[] unbox(final Double[] src) {
        final var out = new double[src.length];
        for (int i = 0; i < src.length; ++i) {
            out[i] = src[i] == null ? 0 : src[i];
        }
        return out;
    }

    // ----------------------------------------------------------

    @SafeVarargs
    public static <A> A[] merge(final A[]... arys) {
        if (arys.length == 0) return empty(arys);
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        if (n == 0) return empty(arys.clone());
        final A[] out = Array.alloc(arys[0], n);
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    public static boolean[] merge(final boolean[]... arys) {
        if (arys.length == 0) return emptyBool;
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        final var out = new boolean[n];
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    public static byte[] merge(final byte[]... arys) {
        if (arys.length == 0) return emptyI8;
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        final var out = new byte[n];
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    public static short[] merge(final short[]... arys) {
        if (arys.length == 0) return emptyI16;
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        final var out = new short[n];
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    public static char[] merge(final char[]... arys) {
        if (arys.length == 0) return emptyC16;
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        final var out = new char[n];
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    public static int[] merge(final int[]... arys) {
        if (arys.length == 0) return emptyI32;
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        final var out = new int[n];
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    public static long[] merge(final long[]... arys) {
        if (arys.length == 0) return emptyI64;
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        final var out = new long[n];
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    public static float[] merge(final float[]... arys) {
        if (arys.length == 0) return emptyF32;
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        final var out = new float[n];
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    public static double[] merge(final double[]... arys) {
        if (arys.length == 0) return emptyF64;
        int n = 0;
        for (final var ary : arys) {
            n += ary.length;
        }
        final var out = new double[n];
        int i = 0;
        for (final var ary : arys) {
            System.arraycopy(ary, 0, out, i, ary.length);
            i += ary.length;
        }
        return out;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the queried {@link Object[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final Object[] src, final Object val) {
        return contains(src, range(src), val);
    }

    /**
     * Determines whether the queried {@link boolean[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final boolean[] src, final boolean val) {
        return contains(src, range(src), val);
    }

    /**
     * Determines whether the queried {@link byte[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final byte[] src, final byte val) {
        return contains(src, range(src), val);
    }

    /**
     * Determines whether the queried {@link short[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final short[] src, final short val) {
        return contains(src, range(src), val);
    }

    /**
     * Determines whether the queried {@link char[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final char[] src, final char val) {
        return contains(src, range(src), val);
    }

    /**
     * Determines whether the queried {@link int[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final int[] src, final int val) {
        return contains(src, range(src), val);
    }

    /**
     * Determines whether the queried {@link long[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final long[] src, final long val) {
        return contains(src, range(src), val);
    }

    /**
     * Determines whether the queried {@link float[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final float[] src, final float val) {
        return contains(src, range(src), val);
    }

    /**
     * Determines whether the queried {@link double[]} array contains
     * the given value.
     *
     * @param src array to be queried.
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final double[] src, final double val) {
        return contains(src, range(src), val);
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the queried {@link Object[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final Object[] src, final Range range, final Object val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (val != null) {
            for (int i = lo; i < hi; ++i) {
                if (val.equals(src[i])) {
                    return true;
                }
            }
        } else {
            for (int i = lo; i < hi; ++i) {
                if (null == src[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines whether the queried {@link boolean[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final boolean[] src, final Range range, final boolean val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (val == src[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the queried {@link byte[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final byte[] src, final Range range, final byte val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (val == src[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the queried {@link short[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final short[] src, final Range range, final short val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (val == src[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the queried {@link char[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final char[] src, final Range range, final char val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (val == src[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the queried {@link int[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final int[] src, final Range range, final int val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (val == src[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the queried {@link long[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final long[] src, final Range range, final long val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (val == src[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the queried {@link float[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final float[] src, final Range range, final float val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (val == src[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the queried {@link double[]} array contains
     * the given value within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    public static boolean contains(final double[] src, final Range range, final double val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (val == src[i]) {
                return true;
            }
        }
        return false;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the queried {@link Object[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final Object[] src, final Object... ary) {
        return contains(src, range(src), ary);
    }

    /**
     * Determines whether the queried {@link boolean[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final boolean[] src, final boolean... ary) {
        return contains(src, range(src), ary);
    }

    /**
     * Determines whether the queried {@link byte[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final byte[] src, final byte... ary) {
        return contains(src, range(src), ary);
    }

    /**
     * Determines whether the queried {@link short[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final short[] src, final short... ary) {
        return contains(src, range(src), ary);
    }

    /**
     * Determines whether the queried {@link char[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final char[] src, final char... ary) {
        return contains(src, range(src), ary);
    }

    /**
     * Determines whether the queried {@link int[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final int[] src, final int... ary) {
        return contains(src, range(src), ary);
    }

    /**
     * Determines whether the queried {@link long[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final long[] src, final long... ary) {
        return contains(src, range(src), ary);
    }

    /**
     * Determines whether the queried {@link float[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final float[] src, final float... ary) {
        return contains(src, range(src), ary);
    }

    /**
     * Determines whether the queried {@link double[]} array contains all
     * values provided by the second array.
     *
     * @param src array to be queried.
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final double[] src, final double... ary) {
        return contains(src, range(src), ary);
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the queried {@link Object[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final Object[] src, final Range range,
                                   final Object... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Determines whether the queried {@link boolean[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final boolean[] src, final Range range,
                                   final boolean... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (v == src[i]) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Determines whether the queried {@link byte[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final byte[] src, final Range range,
                                   final byte... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (v == src[i]) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Determines whether the queried {@link short[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final short[] src, final Range range,
                                   final short... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (v == src[i]) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Determines whether the queried {@link char[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final char[] src, final Range range,
                                   final char... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (v == src[i]) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Determines whether the queried {@link int[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final int[] src, final Range range,
                                   final int... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (v == src[i]) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Determines whether the queried {@link long[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final long[] src, final Range range,
                                   final long... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (v == src[i]) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Determines whether the queried {@link float[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final float[] src, final Range range,
                                   final float... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (v == src[i]) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Determines whether the queried {@link double[]} array contains all
     * values provided by the second array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param ary   array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final double[] src, final Range range,
                                   final double... ary) {
        precondition(src, range, ary);
        final int n = ary.length;
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || n == 0 || n > src.length || lo >= hi) {
            return false;
        }
        outer:
        for (int j = 0; j < n; ++j) {
            final var v = ary[j];
            for (int i = lo; i < hi; ++i) {
                if (v == src[i]) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the queried {@link Object[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final Object[] src, final Iterable<?> itr) {
        return contains(src, range(src), itr);
    }

    /**
     * Determines whether the queried {@link boolean[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final boolean[] src, final Iterable<Boolean> itr) {
        return contains(src, range(src), itr);
    }

    /**
     * Determines whether the queried {@link byte[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final byte[] src, final Iterable<Byte> itr) {
        return contains(src, range(src), itr);
    }

    /**
     * Determines whether the queried {@link short[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final short[] src, final Iterable<Short> itr) {
        return contains(src, range(src), itr);
    }

    /**
     * Determines whether the queried {@link char[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final char[] src, final Iterable<Character> itr) {
        return contains(src, range(src), itr);
    }

    /**
     * Determines whether the queried {@link int[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final int[] src, final Iterable<Integer> itr) {
        return contains(src, range(src), itr);
    }

    /**
     * Determines whether the queried {@link long[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final long[] src, final Iterable<Long> itr) {
        return contains(src, range(src), itr);
    }

    /**
     * Determines whether the queried {@link float[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final float[] src, final Iterable<Float> itr) {
        return contains(src, range(src), itr);
    }

    /**
     * Determines whether the queried {@link double[]} array contains all
     * values provided by the given iterable.
     *
     * @param src array to be queried.
     * @param itr iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final double[] src, final Iterable<Double> itr) {
        return contains(src, range(src), itr);
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the queried {@link Object[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final Object[] src, final Range range,
                                   final Iterable<?> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    /**
     * Determines whether the queried {@link boolean[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final boolean[] src, final Range range,
                                   final Iterable<Boolean> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    /**
     * Determines whether the queried {@link byte[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final byte[] src, final Range range,
                                   final Iterable<Byte> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    /**
     * Determines whether the queried {@link short[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final short[] src, final Range range,
                                   final Iterable<Short> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    /**
     * Determines whether the queried {@link char[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final char[] src, final Range range,
                                   final Iterable<Character> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    /**
     * Determines whether the queried {@link int[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final int[] src, final Range range,
                                   final Iterable<Integer> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    /**
     * Determines whether the queried {@link long[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final long[] src, final Range range,
                                   final Iterable<Long> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    /**
     * Determines whether the queried {@link float[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final float[] src, final Range range,
                                   final Iterable<Float> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    /**
     * Determines whether the queried {@link double[]} array contains all
     * values provided by the given iterable within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param itr   iterable over values whose presence is to be determined.
     * @return true iff all values are present.
     */
    public static boolean contains(final double[] src, final Range range,
                                   final Iterable<Double> itr) {
        precondition(src, range, itr);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi) {
            return false;
        }
        final var it = itr.iterator();
        if (!it.hasNext()) return false;
        outer:
        do {
            final var v = it.next();
            for (int i = lo; i < hi; ++i) {
                if (java.util.Objects.equals(v, src[i])) {
                    continue outer;
                }
            }
            return false;
        } while (it.hasNext());
        return true;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether any value in the queried {@link A[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ A[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       type of array values.
     * @return true iff any value satisfies the predicate.
     */
    public static <A> boolean anyMatch(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return anyMatch(src, range(src), predicate);
    }

    /**
     * Determines whether any value in the queried {@link boolean[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ boolean[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final boolean[] src, final Bool.Predicate predicate) {
        return anyMatch(src, range(src), predicate);
    }

    /**
     * Determines whether any value in the queried {@link byte[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ byte[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final byte[] src, final I8.Predicate predicate) {
        return anyMatch(src, range(src), predicate);
    }

    /**
     * Determines whether any value in the queried {@link short[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ short[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final short[] src, final I16.Predicate predicate) {
        return anyMatch(src, range(src), predicate);
    }

    /**
     * Determines whether any value in the queried {@link char[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ char[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final char[] src, final C16.Predicate predicate) {
        return anyMatch(src, range(src), predicate);
    }

    /**
     * Determines whether any value in the queried {@link int[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ int[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final int[] src, final I32.Predicate predicate) {
        return anyMatch(src, range(src), predicate);
    }

    /**
     * Determines whether any value in the queried {@link long[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ long[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final long[] src, final I64.Predicate predicate) {
        return anyMatch(src, range(src), predicate);
    }

    /**
     * Determines whether any value in the queried {@link float[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ float[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final float[] src, final F32.Predicate predicate) {
        return anyMatch(src, range(src), predicate);
    }

    /**
     * Determines whether any value in the queried {@link double[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ double[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final double[] src, final F64.Predicate predicate) {
        return anyMatch(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Determines whether any value in the queried {@link A[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ A[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       type of array values.
     * @return true iff any value satisfies the predicate.
     */
    public static <A> boolean anyMatch(final A[] src, final Range range,
                                       final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether any value in the queried {@link boolean[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ boolean[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final boolean[] src, final Range range,
                                   final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether any value in the queried {@link byte[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ byte[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final byte[] src, final Range range,
                                   final I8.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether any value in the queried {@link short[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ short[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final short[] src, final Range range,
                                   final I16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether any value in the queried {@link char[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ char[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final char[] src, final Range range,
                                   final C16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether any value in the queried {@link int[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ int[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final int[] src, final Range range,
                                   final I32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether any value in the queried {@link long[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ long[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final long[] src, final Range range,
                                   final I64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether any value in the queried {@link float[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ float[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final float[] src, final Range range,
                                   final F32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether any value in the queried {@link double[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to evaluating the existential quantification of the
     * given predicate over the array {@code '∃(x) ∈ double[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, false is returned
     * and the predicate is not evaluated.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff any value satisfies the predicate.
     */
    public static boolean anyMatch(final double[] src, final Range range,
                                   final F64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Determines whether all values in the queried {@link A[]} array satisfies
     * the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ A[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       type of array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static <A> boolean allMatch(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return allMatch(src, range(src), predicate);
    }

    /**
     * Determines whether all values in the queried {@link boolean[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the predicate over the array {@code '∀(x) ∈ boolean[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final boolean[] src, final Bool.Predicate predicate) {
        return allMatch(src, range(src), predicate);
    }

    /**
     * Determines whether all values in the queried {@link byte[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the predicate over the array {@code '∀(x) ∈ byte[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final byte[] src, final I8.Predicate predicate) {
        return allMatch(src, range(src), predicate);
    }

    /**
     * Determines whether all values in the queried {@link short[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the predicate over the array {@code '∀(x) ∈ short[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final short[] src, final I16.Predicate predicate) {
        return allMatch(src, range(src), predicate);
    }

    /**
     * Determines whether all values in the queried {@link char[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the predicate over the array {@code '∀(x) ∈ char[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final char[] src, final C16.Predicate predicate) {
        return allMatch(src, range(src), predicate);
    }

    /**
     * Determines whether all values in the queried {@link int[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the predicate over the array {@code '∀(x) ∈ int[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final int[] src, final I32.Predicate predicate) {
        return allMatch(src, range(src), predicate);
    }

    /**
     * Determines whether all values in the queried {@link long[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the predicate over the array {@code '∀(x) ∈ long[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final long[] src, final I64.Predicate predicate) {
        return allMatch(src, range(src), predicate);
    }

    /**
     * Determines whether all values in the queried {@link float[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the predicate over the array {@code '∀(x) ∈ float[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final float[] src, final F32.Predicate predicate) {
        return allMatch(src, range(src), predicate);
    }

    /**
     * Determines whether all values in the queried {@link double[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the predicate over the array {@code '∀(x) ∈ double[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final double[] src, final F64.Predicate predicate) {
        return allMatch(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Determines whether all values in the queried {@link A[]} array satisfies
     * the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ A[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       type of array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static <A> boolean allMatch(final A[] src, final Range range,
                                       final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether all values in the queried {@link boolean[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ boolean[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final boolean[] src, final Range range,
                                   final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether all values in the queried {@link byte[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ byte[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final byte[] src, final Range range,
                                   final I8.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether all values in the queried {@link short[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ short[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final short[] src, final Range range,
                                   final I16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether all values in the queried {@link char[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ char[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final char[] src, final Range range,
                                   final C16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether all values in the queried {@link int[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ int[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final int[] src, final Range range,
                                   final I32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether all values in the queried {@link long[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ long[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final long[] src, final Range range,
                                   final I64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether all values in the queried {@link float[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ float[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final float[] src, final Range range,
                                   final F32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether all values in the queried {@link double[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the array {@code '∀(x) ∈ double[] P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff all values satisfy the predicate or the array is empty.
     */
    public static boolean allMatch(final double[] src, final Range range,
                                   final F64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (!predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Determines whether no value in the queried {@link A[]} array satisfies
     * the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ A[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       type of array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static <A> boolean noneMatch(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return noneMatch(src, range(src), predicate);
    }

    /**
     * Determines whether no value in the queried {@link boolean[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ boolean[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final boolean[] src, final Bool.Predicate predicate) {
        return noneMatch(src, range(src), predicate);
    }

    /**
     * Determines whether no value in the queried {@link byte[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ byte[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final byte[] src, final I8.Predicate predicate) {
        return noneMatch(src, range(src), predicate);
    }

    /**
     * Determines whether no value in the queried {@link short[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ short[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final short[] src, final I16.Predicate predicate) {
        return noneMatch(src, range(src), predicate);
    }

    /**
     * Determines whether no value in the queried {@link char[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ char[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final char[] src, final C16.Predicate predicate) {
        return noneMatch(src, range(src), predicate);
    }

    /**
     * Determines whether no value in the queried {@link int[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ int[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final int[] src, final I32.Predicate predicate) {
        return noneMatch(src, range(src), predicate);
    }

    /**
     * Determines whether no value in the queried {@link long[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ long[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final long[] src, final I64.Predicate predicate) {
        return noneMatch(src, range(src), predicate);
    }

    /**
     * Determines whether no value in the queried {@link float[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ float[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final float[] src, final F32.Predicate predicate) {
        return noneMatch(src, range(src), predicate);
    }

    /**
     * Determines whether no value in the queried {@link double[]} array
     * satisfies the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ double[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final double[] src, final F64.Predicate predicate) {
        return noneMatch(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Determines whether no value in the queried {@link A[]} array satisfies
     * the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ A[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       type of array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static <A> boolean noneMatch(final A[] src, final Range range,
                                        final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether no value in the queried {@link boolean[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ boolean[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final boolean[] src, final Range range,
                                    final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether no value in the queried {@link byte[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ byte[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final byte[] src, final Range range,
                                    final I8.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether no value in the queried {@link short[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ short[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final short[] src, final Range range,
                                    final I16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether no value in the queried {@link char[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ char[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final char[] src, final Range range,
                                    final C16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether no value in the queried {@link int[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ int[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final int[] src, final Range range,
                                    final I32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether no value in the queried {@link long[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ long[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final long[] src, final Range range,
                                    final I64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether no value in the queried {@link float[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ float[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final float[] src, final Range range,
                                    final F32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Determines whether no value in the queried {@link double[]} array
     * satisfies the given predicate within the specified index range.
     * <p>
     * This corresponds to the evaluation of the universal quantification of
     * the negated predicate over the array {@code '∀(x) ∈ double[] ¬P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If the array is empty, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return true iff the predicate is never satisfied or the array is empty.
     */
    public static boolean noneMatch(final double[] src, final Range range,
                                    final F64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    // ----------------------------------------------------------

    /**
     * Returns the optional first value contained in the queried {@link A[]}
     * array which satisfies the given predicate. If no value satisfies the
     * predicate or the array is empty, the returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       component-type of the source array.
     * @return option containing the first matching value.
     */
    public static <A> Option<A> find(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return find(src, range(src), predicate);
    }

    /**
     * Returns the optional first {@code boolean} value contained in the
     * queried {@link boolean[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code boolean} value.
     */
    public static Bool.Option find(final boolean[] src, final Bool.Predicate predicate) {
        return find(src, range(src), predicate);
    }

    /**
     * Returns the optional first {@code byte} value contained in the
     * queried {@link byte[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code byte} value.
     */
    public static I8.Option find(final byte[] src, final I8.Predicate predicate) {
        return find(src, range(src), predicate);
    }

    /**
     * Returns the optional first {@code short} value contained in the
     * queried {@link short[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code short} value.
     */
    public static I16.Option find(final short[] src, final I16.Predicate predicate) {
        return find(src, range(src), predicate);
    }

    /**
     * Returns the optional first {@code char} value contained in the
     * queried {@link char[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code char} value.
     */
    public static C16.Option find(final char[] src, final C16.Predicate predicate) {
        return find(src, range(src), predicate);
    }

    /**
     * Returns the optional first {@code int} value contained in the
     * queried {@link int[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code int} value.
     */
    public static I32.Option find(final int[] src, final I32.Predicate predicate) {
        return find(src, range(src), predicate);
    }

    /**
     * Returns the optional first {@code long} value contained in the
     * queried {@link long[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code long} value.
     */
    public static I64.Option find(final long[] src, final I64.Predicate predicate) {
        return find(src, range(src), predicate);
    }

    /**
     * Returns the optional first {@code float} value contained in the
     * queried {@link float[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code float} value.
     */
    public static F32.Option find(final float[] src, final F32.Predicate predicate) {
        return find(src, range(src), predicate);
    }

    /**
     * Returns the optional first {@code double} value contained in the
     * queried {@link double[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code double} value.
     */
    public static F64.Option find(final double[] src, final F64.Predicate predicate) {
        return find(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Returns the optional first value contained in the queried {@link A[]}
     * array which satisfies the given predicate within the specified index
     * range. If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       component-type of the source array.
     * @return option containing the first matching value.
     */
    public static <A> Option<A> find(final A[] src, final Range range,
                                     final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return Option.some(v);
                }
            }
            return Option.none();
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional first {@code boolean} value contained in the
     * queried {@link boolean[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code boolean} value.
     */
    public static Bool.Option find(final boolean[] src, final Range range,
                                   final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return Bool.some(v);
                }
            }
            return Bool.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional first {@code byte} value contained in the
     * queried {@link byte[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code byte} value.
     */
    public static I8.Option find(final byte[] src, final Range range,
                                 final I8.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return I8.some(v);
                }
            }
            return I8.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional first {@code short} value contained in the
     * queried {@link short[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code short} value.
     */
    public static I16.Option find(final short[] src, final Range range,
                                  final I16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return I16.some(v);
                }
            }
            return I16.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional first {@code char} value contained in the
     * queried {@link char[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code char} value.
     */
    public static C16.Option find(final char[] src, final Range range,
                                  final C16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return C16.some(v);
                }
            }
            return C16.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional first {@code int} value contained in the
     * queried {@link int[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code int} value.
     */
    public static I32.Option find(final int[] src, final Range range,
                                  final I32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return I32.some(v);
                }
            }
            return I32.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional first {@code long} value contained in the
     * queried {@link long[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code long} value.
     */
    public static I64.Option find(final long[] src, final Range range,
                                  final I64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return I64.some(v);
                }
            }
            return I64.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional first {@code float} value contained in the
     * queried {@link float[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code float} value.
     */
    public static F32.Option find(final float[] src, final Range range,
                                  final F32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return F32.some(v);
                }
            }
            return F32.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional first {@code double} value contained in the
     * queried {@link double[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional first matching {@code double} value.
     */
    public static F64.Option find(final double[] src, final Range range,
                                  final F64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            for (int i = Range.lo(range); i < hi; ++i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return F64.some(v);
                }
            }
            return F64.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns the optional last value contained in the queried {@link A[]}
     * array which satisfies the given predicate. If no value satisfies the
     * predicate or the array is empty, the returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       component-type of the source array.
     * @return option containing the last matching value.
     */
    public static <A> Option<A> findLast(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return findLast(src, range(src), predicate);
    }

    /**
     * Returns the optional last {@code boolean} value contained in the
     * queried {@link boolean[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code boolean} value.
     */
    public static Bool.Option findLast(final boolean[] src, final Bool.Predicate predicate) {
        return findLast(src, range(src), predicate);
    }

    /**
     * Returns the optional last {@code byte} value contained in the
     * queried {@link byte[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code byte} value.
     */
    public static I8.Option findLast(final byte[] src, final I8.Predicate predicate) {
        return findLast(src, range(src), predicate);
    }

    /**
     * Returns the optional last {@code short} value contained in the
     * queried {@link short[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code short} value.
     */
    public static I16.Option findLast(final short[] src, final I16.Predicate predicate) {
        return findLast(src, range(src), predicate);
    }

    /**
     * Returns the optional last {@code char} value contained in the
     * queried {@link char[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code char} value.
     */
    public static C16.Option findLast(final char[] src, final C16.Predicate predicate) {
        return findLast(src, range(src), predicate);
    }

    /**
     * Returns the optional last {@code int} value contained in the
     * queried {@link int[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code int} value.
     */
    public static I32.Option findLast(final int[] src, final I32.Predicate predicate) {
        return findLast(src, range(src), predicate);
    }

    /**
     * Returns the optional last {@code long} value contained in the
     * queried {@link long[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code long} value.
     */
    public static I64.Option findLast(final long[] src, final I64.Predicate predicate) {
        return findLast(src, range(src), predicate);
    }

    /**
     * Returns the optional last {@code float} value contained in the
     * queried {@link float[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code float} value.
     */
    public static F32.Option findLast(final float[] src, final F32.Predicate predicate) {
        return findLast(src, range(src), predicate);
    }

    /**
     * Returns the optional last {@code double} value contained in the
     * queried {@link double[]} array which satisfies the given predicate.
     * If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     *
     * @param src       source array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code double} value.
     */
    public static F64.Option findLast(final double[] src, final F64.Predicate predicate) {
        return findLast(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Returns the optional last value contained in the queried {@link A[]}
     * array which satisfies the given predicate within the specified index
     * range. If no value satisfies the predicate or the array is empty, the
     * returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       component-type of the source array.
     * @return option containing the last matching value.
     */
    public static <A> Option<A> findLast(final A[] src, final Range range,
                                         final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return Option.some(v);
                }
            }
            return Option.none();
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional last {@code boolean} value contained in the
     * queried {@link boolean[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code boolean} value.
     */
    public static Bool.Option findLast(final boolean[] src, final Range range,
                                       final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return Bool.some(v);
                }
            }
            return Bool.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional last {@code byte} value contained in the
     * queried {@link byte[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code byte} value.
     */
    public static I8.Option findLast(final byte[] src, final Range range,
                                     final I8.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return I8.some(v);
                }
            }
            return I8.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional last {@code short} value contained in the
     * queried {@link short[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code short} value.
     */
    public static I16.Option findLast(final short[] src, final Range range,
                                      final I16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return I16.some(v);
                }
            }
            return I16.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional last {@code char} value contained in the
     * queried {@link char[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code char} value.
     */
    public static C16.Option findLast(final char[] src, final Range range,
                                      final C16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return C16.some(v);
                }
            }
            return C16.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional last {@code int} value contained in the
     * queried {@link int[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code int} value.
     */
    public static I32.Option findLast(final int[] src, final Range range,
                                      final I32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return I32.some(v);
                }
            }
            return I32.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional last {@code long} value contained in the
     * queried {@link long[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code long} value.
     */
    public static I64.Option findLast(final long[] src, final Range range,
                                      final I64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return I64.some(v);
                }
            }
            return I64.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional last {@code float} value contained in the
     * queried {@link float[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code float} value.
     */
    public static F32.Option findLast(final float[] src, final Range range,
                                      final F32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return F32.some(v);
                }
            }
            return F32.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the optional last {@code double} value contained in the
     * queried {@link double[]} array which satisfies the given predicate
     * within the specified index range. If no value satisfies the predicate
     * or the array is empty, the returned option is empty.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return optional last matching {@code double} value.
     */
    public static F64.Option findLast(final double[] src, final Range range,
                                      final F64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                final var v = src[i];
                if (predicate.onEval(v)) {
                    return F64.some(v);
                }
            }
            return F64.none;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code Object[]} array that is equal to the given value, according
     * to the {@link java.util.Objects#equals(Object, Object)} contract.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final Object[] src, final Object val) {
        return indexOf(src, range(src), val);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code boolean[]} array that is equal to the given {@code boolean}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final boolean[] src, final boolean val) {
        return indexOf(src, range(src), val);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code byte[]} array that is equal to the given {@code byte}
     * value. If no value occurrence can be determined, then -1 is
     * returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final byte[] src, final byte val) {
        return indexOf(src, range(src), val);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code short[]} array that is equal to the given {@code short}
     * value. If no value occurrence can be determined, then -1 is
     * returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final short[] src, final short val) {
        return indexOf(src, range(src), val);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code char[]} array that is equal to the given {@code char}
     * value. If no value occurrence can be determined, then -1 is
     * returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final char[] src, final char val) {
        return indexOf(src, range(src), val);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code int[]} array that is equal to the given {@code int}
     * value. If no value occurrence can be determined, then -1 is
     * returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final int[] src, final int val) {
        return indexOf(src, range(src), val);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code long[]} array that is equal to the given {@code long}
     * value. If no value occurrence can be determined, then -1 is
     * returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final long[] src, final long val) {
        return indexOf(src, range(src), val);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code float[]} array that is equal to the given {@code float}
     * value. If no value occurrence can be determined, then -1 is
     * returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final float[] src, final float val) {
        return indexOf(src, range(src), val);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code double[]} array that is equal to the given {@code double}
     * value. If no value occurrence can be determined, then -1 is
     * returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final double[] src, final double val) {
        return indexOf(src, range(src), val);
    }

    // ----------------------------------------------------------

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code Object[]} array that is equal to the given value, according
     * to the {@link java.util.Objects#equals(Object, Object)} contract
     * within the specified index range. If no value occurrence can be
     * determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final Object[] src, final Range range, final Object val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (null != val) {
            for (int i = lo; i < hi; ++i) {
                if (val.equals(src[i])) {
                    return i - lo;
                }
            }
        } else {
            for (int i = lo; i < hi; ++i) {
                if (null == src[i]) {
                    return i - lo;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code boolean[]} array that is equal to the given {@code boolean}
     * value within the specified index range. If no value occurrence can
     * be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final boolean[] src, final Range range, final boolean val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code byte[]} array that is equal to the given {@code byte}
     * value within the specified index range. If no value occurrence
     * can be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final byte[] src, final Range range, final byte val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code short[]} array that is equal to the given {@code short}
     * value within the specified index range. If no value occurrence
     * can be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final short[] src, final Range range, final short val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code char[]} array that is equal to the given {@code char}
     * value within the specified index range. If no value occurrence
     * can be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final char[] src, final Range range, final char val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code int[]} array that is equal to the given {@code int}
     * value within the specified index range. If no value occurrence
     * can be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final int[] src, final Range range, final int val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code long[]} array that is equal to the given {@code long}
     * value within the specified index range. If no value occurrence
     * can be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final long[] src, final Range range, final long val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code float[]} array that is equal to the given {@code float}
     * value within the specified index range. If no value occurrence
     * can be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final float[] src, final Range range, final float val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code double[]} array that is equal to the given {@code double}
     * value within the specified index range. If no value occurrence
     * can be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the first value occurrence or -1.
     */
    public static int indexOf(final double[] src, final Range range, final double val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        for (int i = lo; i < hi; ++i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    // ----------------------------------------------------------

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code Object[]} array that is equal to the given value, according
     * to the {@link java.util.Objects#equals(Object, Object)} contract.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final Object[] src, final Object val) {
        return lastIndexOf(src, range(src), val);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code boolean[]} array that is equal to the given {@code boolean}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final boolean[] src, final boolean val) {
        return lastIndexOf(src, range(src), val);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code byte[]} array that is equal to the given {@code byte}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final byte[] src, final byte val) {
        return lastIndexOf(src, range(src), val);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code short[]} array that is equal to the given {@code short}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final short[] src, final short val) {
        return lastIndexOf(src, range(src), val);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code char[]} array that is equal to the given {@code char}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final char[] src, final char val) {
        return lastIndexOf(src, range(src), val);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code int[]} array that is equal to the given {@code int}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final int[] src, final int val) {
        return lastIndexOf(src, range(src), val);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code long[]} array that is equal to the given {@code long}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final long[] src, final long val) {
        return lastIndexOf(src, range(src), val);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code float[]} array that is equal to the given {@code float}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final float[] src, final float val) {
        return lastIndexOf(src, range(src), val);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code double[]} array that is equal to the given {@code double}
     * value. If no value occurrence can be determined, then -1 is returned.
     *
     * @param src array to be queried.
     * @param val value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final double[] src, final double val) {
        return lastIndexOf(src, range(src), val);
    }

    // ----------------------------------------------------------

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code Object[]} array that is equal to the given value, according
     * to the {@link java.util.Objects#equals(Object, Object)} contract
     * within the specified index range. If no value occurrence can be
     * determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final Object[] src, final Range range, final Object val) {
        precondition(src, range);
        final int last = Range.hi(range) - 1;
        final int lo = Range.lo(range);
        if (null != val) {
            for (int i = last; i >= lo; --i) {
                if (val.equals(src[i])) {
                    return i - lo;
                }
            }
        } else {
            for (int i = last; i >= lo; --i) {
                if (null == src[i]) {
                    return i - lo;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code boolean[]} array that is equal to the given {@code boolean}
     * value within the specified index range. If no value occurrence can
     * be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final boolean[] src, final Range range, final boolean val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        for (int i = Range.hi(range) - 1; i >= lo; --i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code byte[]} array that is equal to the given {@code byte} value
     * within the specified index range. If no value occurrence can be
     * determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final byte[] src, final Range range, final byte val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        for (int i = Range.hi(range) - 1; i >= lo; --i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code short[]} array that is equal to the given {@code short} value
     * within the specified index range. If no value occurrence can be
     * determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final short[] src, final Range range, final short val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        for (int i = Range.hi(range) - 1; i >= lo; --i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code char[]} array that is equal to the given {@code char} value
     * within the specified index range. If no value occurrence can be
     * determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final char[] src, final Range range, final char val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        for (int i = Range.hi(range) - 1; i >= lo; --i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code int[]} array that is equal to the given {@code int} value
     * within the specified index range. If no value occurrence can be
     * determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final int[] src, final Range range, final int val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        for (int i = Range.hi(range) - 1; i >= lo; --i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code long[]} array that is equal to the given {@code long} value
     * within the specified index range. If no value occurrence can be
     * determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final long[] src, final Range range, final long val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        for (int i = Range.hi(range) - 1; i >= lo; --i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code float[]} array that is equal to the given {@code float} value
     * within the specified index range. If no value occurrence can be
     * determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final float[] src, final Range range, final float val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        for (int i = Range.hi(range) - 1; i >= lo; --i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code double[]} array that is equal to the given {@code double}
     * value within the specified index range. If no value occurrence can
     * be determined, then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   value to be located in the queried array.
     * @return index of the last value occurrence or -1.
     */
    public static int lastIndexOf(final double[] src, final Range range, final double val) {
        precondition(src, range);
        final int lo = Range.lo(range);
        for (int i = Range.hi(range) - 1; i >= lo; --i) {
            if (src[i] == val) {
                return i - lo;
            }
        }
        return -1;
    }

    // ----------------------------------------------------------

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code A[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       component-type of the source array.
     * @return index of the matching first value occurrence or -1.
     */
    public static <A> int indexWhere(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return indexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code boolean[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final boolean[] src, final Bool.Predicate predicate) {
        return indexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code byte[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final byte[] src, final I8.Predicate predicate) {
        return indexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code short[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final short[] src, final I16.Predicate predicate) {
        return indexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code char[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final char[] src, final C16.Predicate predicate) {
        return indexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code int[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final int[] src, final I32.Predicate predicate) {
        return indexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code long[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final long[] src, final I64.Predicate predicate) {
        return indexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code float[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final float[] src, final F32.Predicate predicate) {
        return indexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code double[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final double[] src, final F64.Predicate predicate) {
        return indexWhere(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code A[]} array that satisfies the given predicate within the
     * specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       component-type of the source array.
     * @return index of the matching first value occurrence or -1.
     */
    public static <A> int indexWhere(final A[] src, final Range range,
                                     final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code boolean[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final boolean[] src, final Range range,
                                 final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code byte[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final byte[] src, final Range range,
                                 final I8.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code short[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final short[] src, final Range range,
                                 final I16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code char[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final char[] src, final Range range,
                                 final C16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code int[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final int[] src, final Range range,
                                 final I32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code long[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final long[] src, final Range range,
                                 final I64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code float[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final float[] src, final Range range,
                                 final F32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the first value occurrence in the queried
     * {@code double[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching first value occurrence or -1.
     */
    public static int indexWhere(final double[] src, final Range range,
                                 final F64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int hi = Range.hi(range);
            final int lo = Range.lo(range);
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code A[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       component-type of the source array.
     * @return index of the matching last value occurrence or -1.
     */
    public static <A> int lastIndexWhere(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code boolean[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final boolean[] src, final Bool.Predicate predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code byte[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final byte[] src, final I8.Predicate predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code short[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final short[] src, final I16.Predicate predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code char[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final char[] src, final C16.Predicate predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code int[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final int[] src, final I32.Predicate predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code long[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final long[] src, final I64.Predicate predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code float[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final float[] src, final F32.Predicate predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code double[]} array that satisfies the given predicate.
     * If no value occurrence can be determined, then -1 is returned.
     *
     * @param src       array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final double[] src, final F64.Predicate predicate) {
        return lastIndexWhere(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code A[]} array that satisfies the given predicate within the
     * specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @param <A>       component-type of the source array.
     * @return index of the matching last value occurrence or -1.
     */
    public static <A> int lastIndexWhere(final A[] src, final Range range,
                                         final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code boolean[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final boolean[] src, final Range range,
                                     final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code byte[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final byte[] src, final Range range,
                                     final I8.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code short[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final short[] src, final Range range,
                                     final I16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code char[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final char[] src, final Range range,
                                     final C16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code int[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final int[] src, final Range range,
                                     final I32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code long[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final long[] src, final Range range,
                                     final I64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code float[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final float[] src, final Range range,
                                     final F32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the index of the last value occurrence in the queried
     * {@code double[]} array that satisfies the given predicate within
     * the specified index range. If no value occurrence can be determined,
     * then -1 is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to be evaluated on array values.
     * @return index of the matching last value occurrence or -1.
     */
    public static int lastIndexWhere(final double[] src, final Range range,
                                     final F64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            for (int i = Range.hi(range) - 1; i >= lo; --i) {
                if (predicate.onEval(src[i])) {
                    return i - lo;
                }
            }
            return -1;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code Object[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final Object[] src, final Object val) {
        return indicesOf(src, range(src), val);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code boolean[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final boolean[] src, final boolean val) {
        return indicesOf(src, range(src), val);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code long[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final byte[] src, final byte val) {
        return indicesOf(src, range(src), val);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code short[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final short[] src, final short val) {
        return indicesOf(src, range(src), val);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code char[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final char[] src, final char val) {
        return indicesOf(src, range(src), val);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code int[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final int[] src, final int val) {
        return indicesOf(src, range(src), val);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code long[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final long[] src, final long val) {
        return indicesOf(src, range(src), val);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code float[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final float[] src, final float val) {
        return indicesOf(src, range(src), val);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code double[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     *
     * @param src array to be queried.
     * @param val to identify value occurrences whose indices are
     *            to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final double[] src, final double val) {
        return indicesOf(src, range(src), val);
    }

    // ----------------------------------------------------------

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code Object[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final Object[] src, final Range range, final Object val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code boolean[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final boolean[] src, final Range range, final boolean val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code byte[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final byte[] src, final Range range, final byte val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code short[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final short[] src, final Range range, final short val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code char[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final char[] src, final Range range, final char val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code int[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final int[] src, final Range range, final int val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code long[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final long[] src, final Range range, final long val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code float[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final float[] src, final Range range, final float val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code double[]} array that are equal to the given value
     * within the given index range. If no occurrences can be determined, an
     * empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   array to be queried.
     * @param range of the array to be queried.
     * @param val   to identify value occurrences whose indices are
     *              to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesOf(final double[] src, final Range range, final double val) {
        precondition(src, range);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexOf(src, Range.of(i, hi), val);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    // ----------------------------------------------------------

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried array of type {@code A[]} that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static <A> long[] indicesWhere(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code boolean[]} array that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final boolean[] src, final Bool.Predicate predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code byte[]} array that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final byte[] src, final I8.Predicate predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code short[]} array that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final short[] src, final I16.Predicate predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code char[]} array that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final char[] src, final C16.Predicate predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code int[]} array that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final int[] src, final I32.Predicate predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code long[]} array that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final long[] src, final I64.Predicate predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code float[]} array that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final float[] src, final F32.Predicate predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code double[]} array that satisfy the given predicate.
     * If no occurrences can be determined, an empty index bitmap is returned.
     *
     * @param src       array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final double[] src, final F64.Predicate predicate) {
        return indicesWhere(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code Object[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static <A> long[] indicesWhere(final A[] src, final Range range,
                                          final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code boolean[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final boolean[] src, final Range range,
                                      final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code byte[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final byte[] src, final Range range,
                                      final I8.Predicate predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code short[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final short[] src, final Range range,
                                      final I16.Predicate predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code char[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final char[] src, final Range range,
                                      final C16.Predicate predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code int[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final int[] src, final Range range,
                                      final I32.Predicate predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code long[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final long[] src, final Range range,
                                      final I64.Predicate predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code float[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final float[] src, final Range range,
                                      final F32.Predicate predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    /**
     * Returns an index bitmap containing the indices of all value occurrences
     * in the queried {@code double[]} array that satisfy the given predicate
     * within the specified index range. If no occurrences can be determined,
     * an empty index bitmap is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       array to be queried.
     * @param range     of the array to be queried.
     * @param predicate to identify value occurrences whose indices are
     *                  to be included in the returned bitmap index.
     * @return index-bitmap that locates all value occurrences.
     */
    public static long[] indicesWhere(final double[] src, final Range range,
                                      final F64.Predicate predicate) {
        precondition(src, range, predicate);
        final var lo = Range.lo(range);
        final var hi = Range.hi(range);
        final var n = hi - lo;
        if (src.length == 0 || n <= 0)
            return Array.emptyI64;
        final long[] bitmap = Bit.Vec.make(n);
        int i = lo;
        while (i < n) {
            i = indexWhere(src, Range.of(i, hi), predicate);
            if (i == -1) break;
            Bit.Vec.set(bitmap, i - lo);
            ++i;
        }
        return bitmap;
    }

    // ----------------------------------------------------------

    /**
     * Returns the first value of the given {@code A[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @param <A> type of array values.
     * @return optional first value of type {@code A}.
     */
    public static <A> Option<A> first(final A[] src) {
        return Option.when(isPresent(src), () -> src[0]);
    }

    /**
     * Returns the first value of the given {@code boolean[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional first {@code boolean} value.
     */
    public static Bool.Option first(final boolean[] src) {
        return Bool.when(isPresent(src), () -> src[0]);
    }

    /**
     * Returns the first value of the given {@code byte[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional first {@code byte} value.
     */
    public static I8.Option first(final byte[] src) {
        return I8.when(isPresent(src), () -> src[0]);
    }

    /**
     * Returns the first value of the given {@code short[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional first {@code short} value.
     */
    public static I16.Option first(final short[] src) {
        return I16.when(isPresent(src), () -> src[0]);
    }

    /**
     * Returns the first value of the given {@code char[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional first {@code char} value.
     */
    public static C16.Option first(final char[] src) {
        return C16.when(isPresent(src), () -> src[0]);
    }

    /**
     * Returns the first value of the given {@code int[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional first {@code int} value.
     */
    public static I32.Option first(final int[] src) {
        return I32.when(isPresent(src), () -> src[0]);
    }

    /**
     * Returns the first value of the given {@code long[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional first {@code long} value.
     */
    public static I64.Option first(final long[] src) {
        return I64.when(isPresent(src), () -> src[0]);
    }

    /**
     * Returns the first value of the given {@code float[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional first {@code float} value.
     */
    public static F32.Option first(final float[] src) {
        return F32.when(isPresent(src), () -> src[0]);
    }

    /**
     * Returns the first value of the given {@code double[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional first {@code double} value.
     */
    public static F64.Option first(final double[] src) {
        return F64.when(isPresent(src), () -> src[0]);
    }

    // ----------------------------------------------------------

    /**
     * Returns the last value of the given {@code A[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @param <A> type of array values.
     * @return optional last value of type {@code A}.
     */
    public static <A> Option<A> last(final A[] src) {
        return Option.when(isPresent(src), () -> src[src.length - 1]);
    }

    /**
     * Returns the last value of the given {@code boolean[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional last {@code boolean} value.
     */
    public static Bool.Option last(final boolean[] src) {
        return Bool.when(isPresent(src), () -> src[src.length - 1]);
    }

    /**
     * Returns the last value of the given {@code byte[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional last {@code byte} value.
     */
    public static I8.Option last(final byte[] src) {
        return I8.when(isPresent(src), () -> src[src.length - 1]);
    }

    /**
     * Returns the last value of the given {@code short[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional last {@code short} value.
     */
    public static I16.Option last(final short[] src) {
        return I16.when(isPresent(src), () -> src[src.length - 1]);
    }

    /**
     * Returns the last value of the given {@code char[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional last {@code char} value.
     */
    public static C16.Option last(final char[] src) {
        return C16.when(isPresent(src), () -> src[src.length - 1]);
    }

    /**
     * Returns the last value of the given {@code int[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional last {@code int} value.
     */
    public static I32.Option last(final int[] src) {
        return I32.when(isPresent(src), () -> src[src.length - 1]);
    }

    /**
     * Returns the last value of the given {@code long[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional last {@code long} value.
     */
    public static I64.Option last(final long[] src) {
        return I64.when(isPresent(src), () -> src[src.length - 1]);
    }

    /**
     * Returns the last value of the given {@code float[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional last {@code float} value.
     */
    public static F32.Option last(final float[] src) {
        return F32.when(isPresent(src), () -> src[src.length - 1]);
    }

    /**
     * Returns the last value of the given {@code double[]} array if present.
     * Otherwise, if the array is empty, the empty option is returned.
     *
     * @param src source array to be queried.
     * @return optional last {@code double} value.
     */
    public static F64.Option last(final double[] src) {
        return F64.when(isPresent(src), () -> src[src.length - 1]);
    }

    // ----------------------------------------------------------

    /**
     * Returns a shallow copy of the given {@code A[]} array.
     */
    public static <A> A[] copy(final A[] src) {
        return src.clone();
    }

    /**
     * Returns a copy of the given {@code boolean[]} array.
     */
    public static boolean[] copy(final boolean[] src) {
        return isPresent(src) ? src.clone() : emptyBool;
    }

    /**
     * Returns a copy of the given {@code byte[]} array.
     */
    public static byte[] copy(final byte[] src) {
        return isPresent(src) ? src.clone() : emptyI8;
    }

    /**
     * Returns a copy of the given {@code short[]} array.
     */
    public static short[] copy(final short[] src) {
        return isPresent(src) ? src.clone() : emptyI16;
    }

    /**
     * Returns a copy of the given {@code char[]} array.
     */
    public static char[] copy(final char[] src) {
        return isPresent(src) ? src.clone() : emptyC16;
    }

    /**
     * Returns a copy of the given {@code int[]} array.
     */
    public static int[] copy(final int[] src) {
        return isPresent(src) ? src.clone() : emptyI32;
    }

    /**
     * Returns a copy of the given {@code long[]} array.
     */
    public static long[] copy(final long[] src) {
        return isPresent(src) ? src.clone() : emptyI64;
    }

    /**
     * Returns a copy of the given {@code float[]} array.
     */
    public static float[] copy(final float[] src) {
        return isPresent(src) ? src.clone() : emptyF32;
    }

    /**
     * Returns a copy of the given {@code double[]} array.
     */
    public static double[] copy(final double[] src) {
        return isPresent(src) ? src.clone() : emptyF64;
    }

    // ----------------------------------------------------------

    /**
     * Returns a copy the given {@code A[]} array, truncating or padding
     * with nulls (if necessary) so the copy has the given length. The
     * returned array has the specified runtime type.
     *
     * @param src    source array to be copied.
     * @param type   of the result array.
     * @param length of the copy to be returned.
     * @param <A>    type of the source array values.
     * @param <R>    type of the result array values.
     * @return array copy of type {@code R[]}.
     */
    public static <R, A> R[] copy(final A[] src, final Class<? extends R[]> type, final int length) {
        return java.util.Arrays.copyOf(src, length, type);
    }

    /**
     * Returns a copy the given {@code A[]} array, truncating or padding
     * with nulls (if necessary) so the copy has the given length. The
     * returned array has the same runtime type as the source array.
     *
     * @param <A>    type of the source array.
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return array copy of type {@code A[]}.
     */
    public static <A> A[] copy(final A[] src, final int length) {
        return java.util.Arrays.copyOf(src, length, Force.cast(src.getClass()));
    }

    /**
     * Returns a copy the given {@code boolean[]} array, truncating or padding
     * with {@code false} (if necessary) so the copy has the given length.
     *
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return {@code boolean[]} array copy.
     */
    public static boolean[] copy(final boolean[] src, final int length) {
        return length > 0 ? java.util.Arrays.copyOf(src, length) : emptyBool;
    }

    /**
     * Returns a copy the given {@code byte[]} array, truncating or padding
     * with {@code false} (if necessary) so the copy has the given length.
     *
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return {@code byte[]} array copy.
     */
    public static byte[] copy(final byte[] src, final int length) {
        return length > 0 ? java.util.Arrays.copyOf(src, length) : emptyI8;
    }

    /**
     * Returns a copy the given {@code short[]} array, truncating or padding
     * with {@code false} (if necessary) so the copy has the given length.
     *
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return {@code short[]} array copy.
     */
    public static short[] copy(final short[] src, final int length) {
        return length > 0 ? java.util.Arrays.copyOf(src, length) : emptyI16;
    }

    /**
     * Returns a copy the given {@code long[]} array, truncating or padding
     * with {@code false} (if necessary) so the copy has the given length.
     *
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return {@code long[]} array copy.
     */
    public static char[] copy(final char[] src, final int length) {
        return length > 0 ? java.util.Arrays.copyOf(src, length) : emptyC16;
    }

    /**
     * Returns a copy the given {@code int[]} array, truncating or padding
     * with {@code false} (if necessary) so the copy has the given length.
     *
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return {@code int[]} array copy.
     */
    public static int[] copy(final int[] src, final int length) {
        return length > 0 ? java.util.Arrays.copyOf(src, length) : emptyI32;
    }

    /**
     * Returns a copy the given {@code long[]} array, truncating or padding
     * with {@code false} (if necessary) so the copy has the given length.
     *
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return {@code long[]} array copy.
     */
    public static long[] copy(final long[] src, final int length) {
        return length > 0 ? java.util.Arrays.copyOf(src, length) : emptyI64;
    }

    /**
     * Returns a copy the given {@code float[]} array, truncating or padding
     * with {@code false} (if necessary) so the copy has the given length.
     *
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return {@code float[]} array copy.
     */
    public static float[] copy(final float[] src, final int length) {
        return length > 0 ? java.util.Arrays.copyOf(src, length) : emptyF32;
    }

    /**
     * Returns a copy the given {@code double[]} array, truncating or padding
     * with {@code false} (if necessary) so the copy has the given length.
     *
     * @param src    source array to be copied.
     * @param length of the copy to be returned.
     * @return {@code double[]} array copy.
     */
    public static double[] copy(final double[] src, final int length) {
        return length > 0 ? java.util.Arrays.copyOf(src, length) : emptyF64;
    }

    // ----------------------------------------------------------

    /**
     * TODO
     */
    public static <A, R> R[] copy(final A[] src, final Class<? extends R[]> type, final Range range) {
        return copy(src, type, range.lo(), range.hi());
    }

    /**
     * TODO
     */
    public static <A> A[] copy(final A[] src, final Range range) {
        return copy(src, Force.cast(src.getClass()), range);
    }

    /**
     * TODO
     */
    public static boolean[] copy(final boolean[] src, final Range range) {
        return copy(src, range.lo(), range.hi());
    }

    /**
     * TODO
     */
    public static byte[] copy(final byte[] src, final Range range) {
        return copy(src, range.lo(), range.hi());
    }

    /**
     * TODO
     */
    public static short[] copy(final short[] src, final Range range) {
        return copy(src, range.lo(), range.hi());
    }

    /**
     * TODO
     */
    public static char[] copy(final char[] src, final Range range) {
        return copy(src, range.lo(), range.hi());
    }

    /**
     * TODO
     */
    public static int[] copy(final int[] src, final Range range) {
        return copy(src, range.lo(), range.hi());
    }

    /**
     * TODO
     */
    public static long[] copy(final long[] src, final Range range) {
        return copy(src, range.lo(), range.hi());
    }

    /**
     * TODO
     */
    public static float[] copy(final float[] src, final Range range) {
        return copy(src, range.lo(), range.hi());
    }

    /**
     * TODO
     */
    public static double[] copy(final double[] src, final Range range) {
        return copy(src, range.lo(), range.hi());
    }

    // ----------------------------------------------------------

    /**
     * TODO
     */
    public static <A, R> R[] copy(final A[] src, final Class<? extends R[]> type, final long lo, final long hi) {
        return java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi), type);
    }

    /**
     * TODO
     */
    public static <A> A[] copy(final A[] src, final long lo, final long hi) {
        return copy(src, Force.cast(src.getClass()), lo, hi);
    }

    /**
     * TODO
     */
    public static boolean[] copy(final boolean[] src, final long lo, final long hi) {
        return hi > lo ? java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi)) : emptyBool;
    }

    /**
     * TODO
     */
    public static byte[] copy(final byte[] src, final long lo, final long hi) {
        return hi > lo ? java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi)) : emptyI8;
    }

    /**
     * TODO
     */
    public static short[] copy(final short[] src, final long lo, final long hi) {
        return hi > lo ? java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi)) : emptyI16;
    }

    /**
     * TODO
     */
    public static char[] copy(final char[] src, final long lo, final long hi) {
        return hi > lo ? java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi)) : emptyC16;
    }

    /**
     * TODO
     */
    public static int[] copy(final int[] src, final long lo, final long hi) {
        return hi > lo ? java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi)) : emptyI32;
    }

    /**
     * TODO
     */
    public static long[] copy(final long[] src, final long lo, final long hi) {
        return hi > lo ? java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi)) : emptyI64;
    }

    /**
     * TODO
     */
    public static float[] copy(final float[] src, final long lo, final long hi) {
        return hi > lo ? java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi)) : emptyF32;
    }

    /**
     * TODO
     */
    public static double[] copy(final double[] src, final long lo, final long hi) {
        return hi > lo ? java.util.Arrays.copyOfRange(src, Narrow.I32(lo), Narrow.I32(hi)) : emptyF64;
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code A[]} produced from the given
     * source array by prepending the given value of type {@code A}.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @param <A> type of array values in source/result array.
     * @return new array with the prepended value.
     */
    public static <A> A[] prepend(final A[] src, final A val) {
        final int m = src.length;
        final var out = Array.<A> alloc(src, m + 1);
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    /**
     * Returns a new {@code boolean[]} array produced from the given
     * source array by prepending the given {@code boolean} value.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @return new {@code boolean[]} array with the prepended value.
     */
    public static boolean[] prepend(final boolean[] src, final boolean val) {
        final int m = src.length;
        final var out = new boolean[m + 1];
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    /**
     * Returns a new {@code byte[]} array produced from the given
     * source array by prepending the given {@code byte} value.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @return new {@code byte[]} array with the prepended value.
     */
    public static byte[] prepend(final byte[] src, final byte val) {
        final int m = src.length;
        final var out = new byte[m + 1];
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    /**
     * Returns a new {@code short[]} array produced from the given
     * source array by prepending the given {@code short} value.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @return new {@code short[]} array with the prepended value.
     */
    public static short[] prepend(final short[] src, final short val) {
        final int m = src.length;
        final var out = new short[m + 1];
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    /**
     * Returns a new {@code char[]} array produced from the given
     * source array by prepending the given {@code char} value.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @return new {@code char[]} array with the prepended value.
     */
    public static char[] prepend(final char[] src, final char val) {
        final int m = src.length;
        final var out = new char[m + 1];
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    /**
     * Returns a new {@code int[]} array produced from the given
     * source array by prepending the given {@code int} value.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @return new {@code int[]} array with the prepended value.
     */
    public static int[] prepend(final int[] src, final int val) {
        final int m = src.length;
        final var out = new int[m + 1];
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    /**
     * Returns a new {@code long[]} array produced from the given
     * source array by prepending the given {@code long} value.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @return new {@code long[]} array with the prepended value.
     */
    public static long[] prepend(final long[] src, final long val) {
        final int m = src.length;
        final var out = new long[m + 1];
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    /**
     * Returns a new {@code float[]} array produced from the given
     * source array by prepending the given {@code float} value.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @return new {@code float[]} array with the prepended value.
     */
    public static float[] prepend(final float[] src, final float val) {
        final int m = src.length;
        final var out = new float[m + 1];
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    /**
     * Returns a new {@code double[]} array produced from the given
     * source array by prepending the given {@code double} value.
     *
     * @param src source array to be expanded.
     * @param val value to be prepended to the result array.
     * @return new {@code double[]} array with the prepended value.
     */
    public static double[] prepend(final double[] src, final double val) {
        final int m = src.length;
        final var out = new double[m + 1];
        System.arraycopy(src, 0, out, 1, m);
        out[0] = val;
        return out;
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code A[]} produced from the given source array by
     * prepending all values of type {@code A} contained in the second array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @param <A> type of array values in source/result array.
     * @return new array with the prepended values.
     */
    @SafeVarargs
    public static <A> A[] prepend(final A[] src, final A... ary) {
        final int m, n;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary.clone();
        final var out = Array.<A> alloc(ary, m + n);
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    /**
     * Returns a new array {@code boolean[]} produced from the given source
     * array by prepending all {@code boolean} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @return new {@code boolean[]} array with the prepended values.
     */
    public static boolean[] prepend(final boolean[] src, final boolean... ary) {
        final int n, m;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary;
        final var out = new boolean[n + m];
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    /**
     * Returns a new array {@code byte[]} produced from the given source
     * array by prepending all {@code byte} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @return new {@code byte[]} array with the prepended values.
     */
    public static byte[] prepend(final byte[] src, final byte... ary) {
        final int n, m;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary;
        final var out = new byte[n + m];
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    /**
     * Returns a new array {@code short[]} produced from the given source
     * array by prepending all {@code short} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @return new {@code short[]} array with the prepended values.
     */
    public static short[] prepend(final short[] src, final short... ary) {
        final int n, m;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary;
        final var out = new short[n + m];
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    /**
     * Returns a new array {@code char[]} produced from the given source
     * array by prepending all {@code char} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @return new {@code char[]} array with the prepended values.
     */
    public static char[] prepend(final char[] src, final char... ary) {
        final int n, m;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary;
        final var out = new char[n + m];
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    /**
     * Returns a new array {@code int[]} produced from the given source
     * array by prepending all {@code int} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @return new {@code int[]} array with the prepended values.
     */
    public static int[] prepend(final int[] src, final int... ary) {
        final int n, m;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary;
        final var out = new int[n + m];
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    /**
     * Returns a new array {@code long[]} produced from the given source
     * array by prepending all {@code long} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @return new {@code long[]} array with the prepended values.
     */
    public static long[] prepend(final long[] src, final long... ary) {
        final int n, m;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary;
        final var out = new long[n + m];
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    /**
     * Returns a new array {@code float[]} produced from the given source
     * array by prepending all {@code float} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @return new {@code float[]} array with the prepended values.
     */
    public static float[] prepend(final float[] src, final float... ary) {
        final int n, m;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary;
        final var out = new float[n + m];
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    /**
     * Returns a new array {@code double[]} produced from the given source
     * array by prepending all {@code double} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be prepended to the result array.
     * @return new {@code double[]} array with the prepended values.
     */
    public static double[] prepend(final double[] src, final double... ary) {
        final int n, m;
        if ((m = ary.length) == 0) return src;
        if ((n = src.length) == 0) return ary;
        final var out = new double[n + m];
        System.arraycopy(ary, 0, out, 0, m);
        System.arraycopy(src, 0, out, m, n);
        return out;
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code A[]} produced from the given source array
     * by prepending all values of type {@code A} contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @param <A> type of array values in source/result array.
     * @return new array with the prepended values.
     */
    public static <A> A[] prepend(final A[] src, final Iterable<? extends A> itr) {
        return prepend(src, from(Force.cast(src.getClass()), itr));
    }

    /**
     * Returns a new array {@code boolean[]} produced from the given source
     * array by prepending all {@code boolean} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @return new {@code boolean[]} array with the prepended values.
     */
    public static boolean[] prepend(final boolean[] src, final Iterable<Boolean> itr) {
        return prepend(src, fromBool(itr));
    }

    /**
     * Returns a new array {@code byte[]} produced from the given source
     * array by prepending all {@code byte} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @return new {@code byte[]} array with the prepended values.
     */
    public static byte[] prepend(final byte[] src, final Iterable<Byte> itr) {
        return prepend(src, fromI8(itr));
    }

    /**
     * Returns a new array {@code short[]} produced from the given source
     * array by prepending all {@code short} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @return new {@code short[]} array with the prepended values.
     */
    public static short[] prepend(final short[] src, final Iterable<Short> itr) {
        return prepend(src, fromI16(itr));
    }

    /**
     * Returns a new array {@code char[]} produced from the given source
     * array by prepending all {@code char} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @return new {@code char[]} array with the prepended values.
     */
    public static char[] prepend(final char[] src, final Iterable<Character> itr) {
        return prepend(src, fromC16(itr));
    }

    /**
     * Returns a new array {@code int[]} produced from the given source
     * array by prepending all {@code int} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @return new {@code int[]} array with the prepended values.
     */
    public static int[] prepend(final int[] src, final Iterable<Integer> itr) {
        return prepend(src, fromI32(itr));
    }

    /**
     * Returns a new array {@code long[]} produced from the given source
     * array by prepending all {@code long} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @return new {@code long[]} array with the prepended values.
     */
    public static long[] prepend(final long[] src, final Iterable<Long> itr) {
        return prepend(src, fromI64(itr));
    }

    /**
     * Returns a new array {@code float[]} produced from the given source
     * array by prepending all {@code float} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @return new {@code float[]} array with the prepended values.
     */
    public static float[] prepend(final float[] src, final Iterable<Float> itr) {
        return prepend(src, fromF32(itr));
    }

    /**
     * Returns a new array {@code double[]} produced from the given source
     * array by prepending all {@code double} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be prepended to the result array.
     * @return new {@code double[]} array with the prepended values.
     */
    public static double[] prepend(final double[] src, final Iterable<Double> itr) {
        return prepend(src, fromF64(itr));
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code A[]} produced from the given
     * source array by appending the given value of type {@code A}.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @param <A> type of array values in source/result array.
     * @return new array with the appended value.
     */
    public static <A> A[] append(final A[] src, final A val) {
        final int m = src.length;
        final var out = Array.<A> alloc(src, src.length + 1);
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    /**
     * Returns a new array {@code boolean[]} produced from the given
     * source array by appending the given {@code boolean} value.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @return new {@code boolean[]} array with the appended value.
     */
    public static boolean[] append(final boolean[] src, final boolean val) {
        final int m = src.length;
        final var out = new boolean[m + 1];
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    /**
     * Returns a new array {@code byte[]} produced from the given
     * source array by appending the given {@code byte} value.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @return new {@code byte[]} array with the appended value.
     */
    public static byte[] append(final byte[] src, final byte val) {
        final int m = src.length;
        final var out = new byte[m + 1];
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    /**
     * Returns a new array {@code short[]} produced from the given
     * source array by appending the given {@code short} value.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @return new {@code short[]} array with the appended value.
     */
    public static short[] append(final short[] src, final short val) {
        final int m = src.length;
        final var out = new short[m + 1];
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    /**
     * Returns a new array {@code char[]} produced from the given
     * source array by appending the given {@code char} value.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @return new {@code char[]} array with the appended value.
     */
    public static char[] append(final char[] src, final char val) {
        final int m = src.length;
        final var out = new char[m + 1];
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    /**
     * Returns a new array {@code int[]} produced from the given
     * source array by appending the given {@code int} value.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @return new {@code int[]} array with the appended value.
     */
    public static int[] append(final int[] src, final int val) {
        final int m = src.length;
        final var out = new int[m + 1];
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    /**
     * Returns a new array {@code long[]} produced from the given
     * source array by appending the given {@code long} value.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @return new {@code long[]} array with the appended value.
     */
    public static long[] append(final long[] src, final long val) {
        final int m = src.length;
        final var out = new long[m + 1];
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    /**
     * Returns a new array {@code float[]} produced from the given
     * source array by appending the given {@code float} value.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @return new {@code float[]} array with the appended value.
     */
    public static float[] append(final float[] src, final float val) {
        final int m = src.length;
        final var out = new float[m + 1];
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    /**
     * Returns a new array {@code double[]} produced from the given
     * source array by appending the given {@code double} value.
     *
     * @param src source array to be expanded.
     * @param val value to be appended to the result array.
     * @return new {@code double[]} array with the appended value.
     */
    public static double[] append(final double[] src, final double val) {
        final int m = src.length;
        final var out = new double[m + 1];
        System.arraycopy(src, 0, out, 0, m);
        out[m] = val;
        return out;
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code A[]} produced from the given source array by
     * appending all values of type {@code A} contained in the second array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @param <A> type of array values in source/result array.
     * @return new array with the appended values.
     */
    @SafeVarargs
    public static <A> A[] append(final A[] src, final A... ary) {
        if (ary == empty) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = Array.<A> alloc(src, (n = src.length) + m);
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    /**
     * Returns a new array {@code boolean[]} produced from the given source
     * array by appending all {@code boolean} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @return new {@code boolean[]} array with the appended values.
     */
    public static boolean[] append(final boolean[] src, final boolean... ary) {
        if (ary == emptyBool) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = new boolean[(n = src.length) + m];
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    /**
     * Returns a new array {@code byte[]} produced from the given source
     * array by appending all {@code byte} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @return new {@code byte[]} array with the appended values.
     */
    public static byte[] append(final byte[] src, final byte... ary) {
        if (ary == emptyI8) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = new byte[(n = src.length) + m];
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    /**
     * Returns a new array {@code short[]} produced from the given source
     * array by appending all {@code short} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @return new {@code short[]} array with the appended values.
     */
    public static short[] append(final short[] src, final short... ary) {
        if (ary == emptyI16) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = new short[(n = src.length) + m];
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    /**
     * Returns a new array {@code char[]} produced from the given source
     * array by appending all {@code char} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @return new {@code char[]} array with the appended values.
     */
    public static char[] append(final char[] src, final char... ary) {
        if (ary == emptyC16) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = new char[(n = src.length) + m];
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    /**
     * Returns a new array {@code int[]} produced from the given source
     * array by appending all {@code int} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @return new {@code int[]} array with the appended values.
     */
    public static int[] append(final int[] src, final int... ary) {
        if (ary == emptyI32) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = new int[(n = src.length) + m];
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    /**
     * Returns a new array {@code long[]} produced from the given source
     * array by appending all {@code long} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @return new {@code long[]} array with the appended values.
     */
    public static long[] append(final long[] src, final long... ary) {
        if (ary == emptyI64) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = new long[(n = src.length) + m];
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    /**
     * Returns a new array {@code float[]} produced from the given source
     * array by appending all {@code float} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @return new {@code float[]} array with the appended values.
     */
    public static float[] append(final float[] src, final float... ary) {
        if (ary == emptyF32) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = new float[(n = src.length) + m];
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    /**
     * Returns a new array {@code double[]} produced from the given source
     * array by appending all {@code double} values contained in the second
     * array.
     *
     * @param src source array to be expanded.
     * @param ary array of values to be appended to the result array.
     * @return new {@code double[]} array with the appended values.
     */
    public static double[] append(final double[] src, final double... ary) {
        if (ary == emptyF64) return src;
        final int n, m;
        if ((m = ary.length) == 0) return src;
        final var out = new double[(n = src.length) + m];
        System.arraycopy(src, 0, out, 0, n);
        System.arraycopy(ary, 0, out, n, m);
        return out;
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code A[]} produced from the given source array by
     * appending all values of type {@code A} contained in the given iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @param <A> type of array values in source/result array.
     * @return new array with the appended values.
     */
    public static <A> A[] append(final A[] src, final Iterable<? extends A> itr) {
        return append(src, from(Force.cast(src.getClass()), itr));
    }

    /**
     * Returns a new array {@code boolean[]} produced from the given source
     * array by appending all {@code boolean} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @return new {@code boolean[]} array with the appended values.
     */
    public static boolean[] append(final boolean[] src, final Iterable<Boolean> itr) {
        return append(src, fromBool(itr));
    }

    /**
     * Returns a new array {@code byte[]} produced from the given source
     * array by appending all {@code byte} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @return new {@code byte[]} array with the appended values.
     */
    public static byte[] append(final byte[] src, final Iterable<Byte> itr) {
        return append(src, fromI8(itr));
    }

    /**
     * Returns a new array {@code short[]} produced from the given source
     * array by appending all {@code short} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @return new {@code short[]} array with the appended values.
     */
    public static short[] append(final short[] src, final Iterable<Short> itr) {
        return append(src, fromI16(itr));
    }

    /**
     * Returns a new array {@code char[]} produced from the given source
     * array by appending all {@code char} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @return new {@code char[]} array with the appended values.
     */
    public static char[] append(final char[] src, final Iterable<Character> itr) {
        return append(src, fromC16(itr));
    }

    /**
     * Returns a new array {@code int[]} produced from the given source
     * array by appending all {@code int} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @return new {@code int[]} array with the appended values.
     */
    public static int[] append(final int[] src, final Iterable<Integer> itr) {
        return append(src, fromI32(itr));
    }

    /**
     * Returns a new array {@code long[]} produced from the given source
     * array by appending all {@code long} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @return new {@code long[]} array with the appended values.
     */
    public static long[] append(final long[] src, final Iterable<Long> itr) {
        return append(src, fromI64(itr));
    }

    /**
     * Returns a new array {@code float[]} produced from the given source
     * array by appending all {@code float} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @return new {@code float[]} array with the appended values.
     */
    public static float[] append(final float[] src, final Iterable<Float> itr) {
        return append(src, fromF32(itr));
    }

    /**
     * Returns a new array {@code double[]} produced from the given source
     * array by appending all {@code double} values contained in the given
     * iterable.
     *
     * @param src source array to be expanded.
     * @param itr iterable of values to be appended to the result array.
     * @return new {@code double[]} array with the appended values.
     */
    public static double[] append(final double[] src, final Iterable<Double> itr) {
        return append(src, fromF64(itr));
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code A[]} array by inserting the given value at the
     * specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code A[]} array.
     */
    public static <A> A[] insertAt(final A[] src, final long pos, final A val) {
        Assert.position(pos, src);
        final int m, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((m = src.length) == p) return append(src, val);
        final var out = Array.<A> alloc(src, m + 1);
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, m - p);
        out[p] = val;
        return out;
    }

    /**
     * Produces a new {@code boolean[]} array by inserting the given value
     * at the specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] insertAt(final boolean[] src, final long pos, final boolean val) {
        Assert.position(pos, src);
        final int n, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((n = src.length) == p) return append(src, val);
        final var out = new boolean[n + 1];
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, n - p);
        out[p] = val;
        return out;
    }

    /**
     * Produces a new {@code byte[]} array by inserting the given value
     * at the specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code byte[]} array.
     */
    public static byte[] insertAt(final byte[] src, final long pos, final byte val) {
        Assert.position(pos, src);
        final int n, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((n = src.length) == p) return append(src, val);
        final var out = new byte[n + 1];
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, n - p);
        out[p] = val;
        return out;
    }

    /**
     * Produces a new {@code short[]} array by inserting the given value
     * at the specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code short[]} array.
     */
    public static short[] insertAt(final short[] src, final long pos, final short val) {
        Assert.position(pos, src);
        final int n, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((n = src.length) == p) return append(src, val);
        final var out = new short[n + 1];
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, n - p);
        out[p] = val;
        return out;
    }

    /**
     * Produces a new {@code char[]} array by inserting the given value
     * at the specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code char[]} array.
     */
    public static char[] insertAt(final char[] src, final long pos, final char val) {
        Assert.position(pos, src);
        final int n, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((n = src.length) == p) return append(src, val);
        final var out = new char[n + 1];
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, n - p);
        out[p] = val;
        return out;
    }

    /**
     * Produces a new {@code int[]} array by inserting the given value
     * at the specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code int[]} array.
     */
    public static int[] insertAt(final int[] src, final long pos, final int val) {
        Assert.position(pos, src);
        final int n, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((n = src.length) == p) return append(src, val);
        final var out = new int[n + 1];
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, n - p);
        out[p] = val;
        return out;
    }

    /**
     * Produces a new {@code long[]} array by inserting the given value
     * at the specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code long[]} array.
     */
    public static long[] insertAt(final long[] src, final long pos, final long val) {
        Assert.position(pos, src);
        final int n, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((n = src.length) == p) return append(src, val);
        final var out = new long[n + 1];
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, n - p);
        out[p] = val;
        return out;
    }

    /**
     * Produces a new {@code float[]} array by inserting the given value
     * at the specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code float[]} array.
     */
    public static float[] insertAt(final float[] src, final long pos, final float val) {
        Assert.position(pos, src);
        final int n, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((n = src.length) == p) return append(src, val);
        final var out = new float[n + 1];
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, n - p);
        out[p] = val;
        return out;
    }

    /**
     * Produces a new {@code double[]} array by inserting the given value
     * at the specified position to the given source array.
     *
     * @param pos position of the insert location.
     * @param val value to be inserted.
     * @return new {@code double[]} array.
     */
    public static double[] insertAt(final double[] src, final long pos, final double val) {
        Assert.position(pos, src);
        final int n, p;
        if ((p = (int) pos) == 0) return prepend(src, val);
        if ((n = src.length) == p) return append(src, val);
        final var out = new double[n + 1];
        System.arraycopy(src, 0, out, 0, p);
        System.arraycopy(src, p, out, p + 1, n - p);
        out[p] = val;
        return out;
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code A[]} array by inserting all values in the given
     * array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code A[]} array.
     */
    @SafeVarargs
    public static <A> A[] insertAt(final A[] src, final long pos, final A... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = Array.<A> alloc(src, (n = src.length) + m);
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    /**
     * Produces a new {@code boolean[]} array by inserting all values in the
     * given array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] insertAt(final boolean[] src, final long pos, final boolean... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = new boolean[(n = src.length) + m];
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    /**
     * Produces a new {@code byte[]} array by inserting all values in the
     * given array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code byte[]} array.
     */
    public static byte[] insertAt(final byte[] src, final long pos, final byte... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = new byte[(n = src.length) + m];
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    /**
     * Produces a new {@code short[]} array by inserting all values in the
     * given array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code short[]} array.
     */
    public static short[] insertAt(final short[] src, final long pos, final short... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = new short[(n = src.length) + m];
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    /**
     * Produces a new {@code char[]} array by inserting all values in the
     * given array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code char[]} array.
     */
    public static char[] insertAt(final char[] src, final long pos, final char... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = new char[(n = src.length) + m];
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    /**
     * Produces a new {@code int[]} array by inserting all values in the
     * given array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code int[]} array.
     */
    public static int[] insertAt(final int[] src, final long pos, final int... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = new int[(n = src.length) + m];
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    /**
     * Produces a new {@code long[]} array by inserting all values in the
     * given array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code long[]} array.
     */
    public static long[] insertAt(final long[] src, final long pos, final long... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = new long[(n = src.length) + m];
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    /**
     * Produces a new {@code float[]} array by inserting all values in the
     * given array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code float[]} array.
     */
    public static float[] insertAt(final float[] src, final long pos, final float... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = new float[(n = src.length) + m];
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    /**
     * Produces a new {@code double[]} array by inserting all values in the
     * given array at the specified position into the given source array.
     *
     * @param pos position of the insert location.
     * @param ary array of values to be inserted.
     * @return new {@code double[]} array.
     */
    public static double[] insertAt(final double[] src, final long pos, final double... ary) {
        Assert.position(pos, src);
        final int n, m, p;
        if ((m = ary.length) == 0) return src;
        final var out = new double[(n = src.length) + m];
        System.arraycopy(ary, 0, out, (p = (int) pos), m);
        if (p > 0) System.arraycopy(src, 0, out, 0, p);
        if (p < n) System.arraycopy(src, p, out, p + m, n - p);
        return out;
    }

    // ----------------------------------------------------------

    public static <A> A[] insertAt(final A[] src, final long pos, final Iterable<? extends A> itr) {
        return insertAt(src, pos, Array.<A> from(Force.cast(src.getClass()), itr));
    }

    public static boolean[] insertAt(final boolean[] src, final long pos, final Iterable<Boolean> itr) {
        return insertAt(src, pos, fromBool(itr));
    }

    public static byte[] insertAt(final byte[] src, final long pos, final Iterable<Byte> itr) {
        return insertAt(src, pos, fromI8(itr));
    }

    public static short[] insertAt(final short[] src, final long pos, final Iterable<Short> itr) {
        return insertAt(src, pos, fromI16(itr));
    }

    public static char[] insertAt(final char[] src, final long pos, final Iterable<Character> itr) {
        return insertAt(src, pos, fromC16(itr));
    }

    public static int[] insertAt(final int[] src, final long pos, final Iterable<Integer> itr) {
        return insertAt(src, pos, fromI32(itr));
    }

    public static long[] insertAt(final long[] src, final long pos, final Iterable<Long> itr) {
        return insertAt(src, pos, fromI64(itr));
    }

    public static float[] insertAt(final float[] src, final long pos, final Iterable<Float> itr) {
        return insertAt(src, pos, fromF32(itr));
    }

    public static double[] insertAt(final double[] src, final long pos, final Iterable<Double> itr) {
        return insertAt(src, pos, fromF64(itr));
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]} array produced from the given source array
     * by removing the first value occurrence that is equal to the given
     * value. If no value occurrence can be determined, the source array
     * is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @param <A> component-type of the source array.
     * @return a {@code A[]} array.
     */
    public static <A> A[] remove(final A[] src, final A val) {
        return remove(src, range(src), val);
    }

    /**
     * Returns a new {@code boolean[]} array produced from the given source
     * array by removing the first {@code boolean} value occurrence that is
     * equal to the given value. If no value occurrence can be determined,
     * the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code boolean[]} array.
     */
    public static boolean[] remove(final boolean[] src, final boolean val) {
        return remove(src, range(src), val);
    }

    /**
     * Returns a new {@code byte[]} array produced from the given source
     * array by removing the first {@code byte} value occurrence that is
     * equal to the given value. If no value occurrence can be determined,
     * the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code byte[]} array.
     */
    public static byte[] remove(final byte[] src, final byte val) {
        return remove(src, range(src), val);
    }

    /**
     * Returns a new {@code short[]} array produced from the given source
     * array by removing the first {@code short} value occurrence that is
     * equal to the given value. If no value occurrence can be determined,
     * the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code short[]} array.
     */
    public static short[] remove(final short[] src, final short val) {
        return remove(src, range(src), val);
    }

    /**
     * Returns a new {@code char[]} array produced from the given source
     * array by removing the first {@code char} value occurrence that is
     * equal to the given value. If no value occurrence can be determined,
     * the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code char[]} array.
     */
    public static char[] remove(final char[] src, final char val) {
        return remove(src, range(src), val);
    }

    /**
     * Returns a new {@code int[]} array produced from the given source
     * array by removing the first {@code int} value occurrence that is
     * equal to the given value. If no value occurrence can be determined,
     * the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code int[]} array.
     */
    public static int[] remove(final int[] src, final int val) {
        return remove(src, range(src), val);
    }

    /**
     * Returns a new {@code long[]} array produced from the given source
     * array by removing the first {@code long} value occurrence that is
     * equal to the given value. If no value occurrence can be determined,
     * the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code long[]} array.
     */
    public static long[] remove(final long[] src, final long val) {
        return remove(src, range(src), val);
    }

    /**
     * Returns a new {@code float[]} array produced from the given source
     * array by removing the first {@code float} value occurrence that is
     * equal to the given value. If no value occurrence can be determined,
     * the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code float[]} array.
     */
    public static float[] remove(final float[] src, final float val) {
        return remove(src, range(src), val);
    }

    /**
     * Returns a new {@code double[]} array produced from the given source
     * array by removing the first {@code double} value occurrence that is
     * equal to the given value. If no value occurrence can be determined,
     * the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code double[]} array.
     */
    public static double[] remove(final double[] src, final double val) {
        return remove(src, range(src), val);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]} array produced from the given source array
     * by removing the first value occurrence that is equal to the given
     * value within specified index-range. If no value occurrence can be
     * determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @param <A>   component-type of the source array.
     * @return a {@code A[]} array.
     */
    public static <A> A[] remove(final A[] src, final Range range, final A val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    /**
     * Returns a new {@code boolean[]} array produced from the given source
     * array by removing the first {@code boolean} value occurrence that is
     * equal to the given value within specified index-range. If no value
     * occurrence can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code boolean[]} array.
     */
    public static boolean[] remove(final boolean[] src, final Range range, final boolean val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    /**
     * Returns a new {@code byte[]} array produced from the given source
     * array by removing the first {@code byte} value occurrence that is
     * equal to the given value within specified index-range. If no value
     * occurrence can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code byte[]} array.
     */
    public static byte[] remove(final byte[] src, final Range range, final byte val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    /**
     * Returns a new {@code short[]} array produced from the given source
     * array by removing the first {@code short} value occurrence that is
     * equal to the given value within specified index-range. If no value
     * occurrence can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code short[]} array.
     */
    public static short[] remove(final short[] src, final Range range, final short val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    /**
     * Returns a new {@code char[]} array produced from the given source
     * array by removing the first {@code char} value occurrence that is
     * equal to the given value within specified index-range. If no value
     * occurrence can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code char[]} array.
     */
    public static char[] remove(final char[] src, final Range range, char val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    /**
     * Returns a new {@code int[]} array produced from the given source
     * array by removing the first {@code int} value occurrence that is
     * equal to the given value within specified index-range. If no value
     * occurrence can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code int[]} array.
     */
    public static int[] remove(final int[] src, final Range range, int val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    /**
     * Returns a new {@code long[]} array produced from the given source
     * array by removing the first {@code long} value occurrence that is
     * equal to the given value within specified index-range. If no value
     * occurrence can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code long[]} array.
     */
    public static long[] remove(final long[] src, final Range range, long val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    /**
     * Returns a new {@code float[]} array produced from the given source
     * array by removing the first {@code float} value occurrence that is
     * equal to the given value within specified index-range. If no value
     * occurrence can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code float[]} array.
     */
    public static float[] remove(final float[] src, final Range range, float val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    /**
     * Returns a new {@code double[]} array produced from the given source
     * array by removing the first {@code double} value occurrence that is
     * equal to the given value within specified index-range. If no value
     * occurrence can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code double[]} array.
     */
    public static double[] remove(final double[] src, final Range range, double val) {
        precondition(src, range);
        final var i = indexOf(src, range, val);
        if (i == -1) return src;
        return removeAt(src, Range.lo(range) + i);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]} array produced from the given source array
     * by removing all value occurrences that are equal to the given value.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @param <A> component-type of the source array.
     * @return a {@code A[]} array.
     */
    public static <A> A[] removeAll(final A[] src, A val) {
        return filter(src, range(src), x -> x != val);
    }

    /**
     * Returns a new {@code boolean[]} array produced from the given source
     * array by removing all {@code boolean} value occurrences that are equal.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code boolean[]} array.
     */
    public static boolean[] removeAll(final boolean[] src, boolean val) {
        return filter(src, range(src), x -> x != val);
    }

    /**
     * Returns a new {@code byte[]} array produced from the given source
     * array by removing all {@code byte} value occurrences that are equal.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code byte[]} array.
     */
    public static byte[] removeAll(final byte[] src, byte val) {
        return filter(src, range(src), x -> x != val);
    }

    /**
     * Returns a new {@code short[]} array produced from the given source
     * array by removing all {@code short} value occurrences that are equal.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code short[]} array.
     */
    public static short[] removeAll(final short[] src, short val) {
        return filter(src, range(src), x -> x != val);
    }

    /**
     * Returns a new {@code char[]} array produced from the given source
     * array by removing all {@code char} value occurrences that are equal.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code char[]} array.
     */
    public static char[] removeAll(final char[] src, char val) {
        return filter(src, range(src), x -> x != val);
    }

    /**
     * Returns a new {@code int[]} array produced from the given source
     * array by removing all {@code int} value occurrences that are equal.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code int[]} array.
     */
    public static int[] removeAll(final int[] src, int val) {
        return filter(src, range(src), x -> x != val);
    }

    /**
     * Returns a new {@code long[]} array produced from the given source
     * array by removing all {@code long} value occurrences that are equal.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code long[]} array.
     */
    public static long[] removeAll(final long[] src, long val) {
        return filter(src, range(src), x -> x != val);
    }

    /**
     * Returns a new {@code float[]} array produced from the given source
     * array by removing all {@code float} value occurrences that are equal.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code float[]} array.
     */
    public static float[] removeAll(final float[] src, float val) {
        return filter(src, range(src), x -> x != val);
    }

    /**
     * Returns a new {@code double[]} array produced from the given source
     * array by removing all {@code double} value occurrences that are equal.
     * If no value occurrence can be determined, the source array is returned.
     *
     * @param src source array to be queried.
     * @param val whose first occurrence is to be removed.
     * @return a {@code double[]} array.
     */
    public static double[] removeAll(final double[] src, double val) {
        return filter(src, range(src), x -> x != val);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]} array produced from the given source array
     * by removing all value occurrences that are equal to the given value
     * within specified index-range. If no value occurrence can be determined,
     * the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @param <A>   component-type of the source array.
     * @return a {@code A[]} array.
     */
    public static <A> A[] removeAll(final A[] src, final Range range, final A val) {
        return filter(src, range, x -> !java.util.Objects.equals(x, val));
    }

    /**
     * Returns a new {@code boolean[]} array produced from the given source
     * array by removing all {@code boolean} value occurrences that are equal
     * to the given value within specified index-range. If no value occurrence
     * can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code boolean[]} array.
     */
    public static boolean[] removeAll(final boolean[] src, final Range range, final boolean val) {
        return filter(src, range, x -> x != val);
    }

    /**
     * Returns a new {@code byte[]} array produced from the given source
     * array by removing all {@code byte} value occurrences that are equal
     * to the given value within specified index-range. If no value occurrence
     * can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code byte[]} array.
     */
    public static byte[] removeAll(final byte[] src, final Range range, final byte val) {
        return filter(src, range, x -> x != val);
    }

    /**
     * Returns a new {@code short[]} array produced from the given source
     * array by removing all {@code short} value occurrences that are equal
     * to the given value within specified index-range. If no value occurrence
     * can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code short[]} array.
     */
    public static short[] removeAll(final short[] src, final Range range, final short val) {
        return filter(src, range, x -> x != val);
    }

    /**
     * Returns a new {@code char[]} array produced from the given source
     * array by removing all {@code char} value occurrences that are equal
     * to the given value within specified index-range. If no value occurrence
     * can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code char[]} array.
     */
    public static char[] removeAll(final char[] src, final Range range, final char val) {
        return filter(src, range, x -> x != val);
    }

    /**
     * Returns a new {@code int[]} array produced from the given source
     * array by removing all {@code int} value occurrences that are equal
     * to the given value within specified index-range. If no value occurrence
     * can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code int[]} array.
     */
    public static int[] removeAll(final int[] src, final Range range, final int val) {
        return filter(src, range, x -> x != val);
    }

    /**
     * Returns a new {@code long[]} array produced from the given source
     * array by removing all {@code long} value occurrences that are equal
     * to the given value within specified index-range. If no value occurrence
     * can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code long[]} array.
     */
    public static long[] removeAll(final long[] src, final Range range, final long val) {
        return filter(src, range, x -> x != val);
    }

    /**
     * Returns a new {@code float[]} array produced from the given source
     * array by removing all {@code float} value occurrences that are equal
     * to the given value within specified index-range. If no value occurrence
     * can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code float[]} array.
     */
    public static float[] removeAll(final float[] src, final Range range, final float val) {
        return filter(src, range, x -> x != val);
    }

    /**
     * Returns a new {@code double[]} array produced from the given source
     * array by removing all {@code double} value occurrences that are equal
     * to the given value within specified index-range. If no value occurrence
     * can be determined, the source array is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param val   whose first occurrence is to be removed.
     * @return a {@code double[]} array.
     */
    public static double[] removeAll(final double[] src, final Range range, final double val) {
        return filter(src, range, x -> x != val);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]}  array produced from the given source
     * array by removing the first value. If the source array is empty,
     * the empty array is returned.
     *
     * @param src source array to be transformed.
     * @param <A> component-type of the source array.
     * @return new array.
     */
    public static <A> A[] removeFirst(final A[] src) {
        return (src.length == 0) ? copy(src, 0) : copy(src, 1, src.length);
    }

    /**
     * Returns a new {@code boolean[]} array produced from the given
     * source array by removing the first {@code boolean} value. If
     * the source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code boolean[]} array.
     */
    public static boolean[] removeFirst(final boolean[] src) {
        return (src.length == 0) ? emptyBool : copy(src, 1, src.length);
    }

    /**
     * Returns a new {@code byte[]} array produced from the given
     * source array by removing the first {@code byte} value. If
     * the source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code byte[]} array.
     */
    public static byte[] removeFirst(final byte[] src) {
        return (src.length == 0) ? emptyI8 : copy(src, 1, src.length);
    }

    /**
     * Returns a new {@code short[]} array produced from the given
     * source array by removing the first {@code short} value. If
     * the source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code short[]} array.
     */
    public static short[] removeFirst(final short[] src) {
        return (src.length == 0) ? emptyI16 : copy(src, 1, src.length);
    }

    /**
     * Returns a new {@code char[]} array produced from the given
     * source array by removing the first {@code char} value. If
     * the source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code char[]} array.
     */
    public static char[] removeFirst(final char[] src) {
        return (src.length == 0) ? emptyC16 : copy(src, 1, src.length);
    }

    /**
     * Returns a new {@code int[]} array produced from the given
     * source array by removing the first {@code int} value. If
     * the source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code int[]} array.
     */
    public static int[] removeFirst(final int[] src) {
        return (src.length == 0) ? emptyI32 : copy(src, 1, src.length);
    }

    /**
     * Returns a new {@code long[]} array produced from the given
     * source array by removing the first {@code long} value. If
     * the source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code long[]} array.
     */
    public static long[] removeFirst(final long[] src) {
        return (src.length == 0) ? emptyI64 : copy(src, 1, src.length);
    }

    /**
     * Returns a new {@code float[]} array produced from the given
     * source array by removing the first {@code float} value. If
     * the source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code float[]} array.
     */
    public static float[] removeFirst(final float[] src) {
        return (src.length == 0) ? emptyF32 : copy(src, 1, src.length);
    }

    /**
     * Returns a new {@code double[]} array produced from the given
     * source array by removing the first {@code double} value. If
     * the source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code double[]} array.
     */
    public static double[] removeFirst(final double[] src) {
        return (src.length == 0) ? emptyF64 : copy(src, 1, src.length);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]} array produced from the given source
     * array by removing the last value. If the source array is empty,
     * the empty array is returned.
     *
     * @param src source array to be transformed.
     * @param <A> component-type of the source array.
     * @return new array.
     */
    public static <A> A[] removeLast(final A[] src) {
        return (src.length == 0) ? copy(src, 0) : copy(src, src.length - 1);
    }

    /**
     * Returns a new {@code boolean[]} array produced from the given
     * source array by removing the last {@code boolean} value. If the
     * source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code boolean[]} array.
     */
    public static boolean[] removeLast(final boolean[] src) {
        return (src.length == 0) ? emptyBool : copy(src, src.length - 1);
    }

    /**
     * Returns a new {@code byte[]} array produced from the given
     * source array by removing the last {@code byte} value. If the
     * source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code byte[]} array.
     */
    public static byte[] removeLast(final byte[] src) {
        return (src.length == 0) ? emptyI8 : copy(src, src.length - 1);
    }

    /**
     * Returns a new {@code short[]} array produced from the given
     * source array by removing the last {@code short} value. If the
     * source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code short[]} array.
     */
    public static short[] removeLast(final short[] src) {
        return (src.length == 0) ? emptyI16 : copy(src, src.length - 1);
    }

    /**
     * Returns a new {@code char[]} array produced from the given
     * source array by removing the last {@code char} value. If the
     * source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code char[]} array.
     */
    public static char[] removeLast(final char[] src) {
        return (src.length == 0) ? emptyC16 : copy(src, src.length - 1);
    }

    /**
     * Returns a new {@code int[]} array produced from the given
     * source array by removing the last {@code int} value. If the
     * source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code int[]} array.
     */
    public static int[] removeLast(final int[] src) {
        return (src.length == 0) ? emptyI32 : copy(src, src.length - 1);
    }

    /**
     * Returns a new {@code long[]} array produced from the given
     * source array by removing the last {@code long} value. If the
     * source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code long[]} array.
     */
    public static long[] removeLast(final long[] src) {
        return (src.length == 0) ? emptyI64 : copy(src, src.length - 1);
    }

    /**
     * Returns a new {@code float[]} array produced from the given
     * source array by removing the last {@code float} value. If the
     * source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code float[]} array.
     */
    public static float[] removeLast(final float[] src) {
        return (src.length == 0) ? emptyF32 : copy(src, src.length - 1);
    }

    /**
     * Returns a new {@code double[]} array produced from the given
     * source array by removing the last {@code double} value. If the
     * source array is empty, the empty array is returned.
     *
     * @param src source array to be transformed.
     * @return a {@code double[]} array.
     */
    public static double[] removeLast(final double[] src) {
        return (src.length == 0) ? emptyF64 : copy(src, src.length - 1);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]} array from the given source array by
     * removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @param <A> component-type of the source array.
     * @return new {@code A[]} array.
     */
    public static <A> A[] removeAt(final A[] src, final long idx) {
        Assert.index(idx, src);
        final int lo, ix;
        if ((ix = (int) idx) == 0)
            return removeFirst(src);
        if (ix == (lo = src.length - 1))
            return removeLast(src);
        final var out = Array.<A> alloc(src, lo);
        System.arraycopy(src, 0, out, 0, ix);
        System.arraycopy(src, ix + 1, out, ix, lo - ix);
        return out;
    }

    /**
     * Returns a new {@code boolean[]} array from the given source array
     * by removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] removeAt(final boolean[] src, final long idx) {
        Assert.index(idx, src);
        final int n, x;
        if ((x = (int) idx) == 0)
            return removeFirst(src);
        if (x == (n = src.length - 1))
            return removeLast(src);
        final var out = new boolean[n];
        System.arraycopy(src, 0, out, 0, x);
        System.arraycopy(src, x + 1, out, x, n - x);
        return out;
    }

    /**
     * Returns a new {@code byte[]} array from the given source array
     * by removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @return new {@code byte[]} array.
     */
    public static byte[] removeAt(final byte[] src, final long idx) {
        Assert.index(idx, src);
        final int n, x;
        if ((x = (int) idx) == 0)
            return removeFirst(src);
        if (x == (n = src.length - 1))
            return removeLast(src);
        final var out = new byte[n];
        System.arraycopy(src, 0, out, 0, x);
        System.arraycopy(src, x + 1, out, x, n - x);
        return out;
    }

    /**
     * Returns a new {@code short[]} array from the given source array
     * by removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @return new {@code short[]} array.
     */
    public static short[] removeAt(final short[] src, final long idx) {
        Assert.index(idx, src);
        final int n, x;
        if ((x = (int) idx) == 0)
            return removeFirst(src);
        if (x == (n = src.length - 1))
            return removeLast(src);
        final var out = new short[n];
        System.arraycopy(src, 0, out, 0, x);
        System.arraycopy(src, x + 1, out, x, n - x);
        return out;
    }

    /**
     * Returns a new {@code char[]} array from the given source array
     * by removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @return new {@code char[]} array.
     */
    public static char[] removeAt(final char[] src, final long idx) {
        Assert.index(idx, src);
        final int n, x;
        if ((x = (int) idx) == 0)
            return removeFirst(src);
        if (x == (n = src.length - 1))
            return removeLast(src);
        final var out = new char[n];
        System.arraycopy(src, 0, out, 0, x);
        System.arraycopy(src, x + 1, out, x, n - x);
        return out;
    }

    /**
     * Returns a new {@code int[]} array from the given source array
     * by removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @return new {@code int[]} array.
     */
    public static int[] removeAt(final int[] src, final long idx) {
        Assert.index(idx, src);
        final int n, x;
        if ((x = (int) idx) == 0)
            return removeFirst(src);
        if (x == (n = src.length - 1))
            return removeLast(src);
        final var out = new int[n];
        System.arraycopy(src, 0, out, 0, x);
        System.arraycopy(src, x + 1, out, x, n - x);
        return out;
    }

    /**
     * Returns a new {@code long[]} array from the given source array
     * by removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @return new {@code long[]} array.
     */
    public static long[] removeAt(final long[] src, final long idx) {
        Assert.index(idx, src);
        final int n, x;
        if ((x = (int) idx) == 0)
            return removeFirst(src);
        if (x == (n = src.length - 1))
            return removeLast(src);
        final var out = new long[n];
        System.arraycopy(src, 0, out, 0, x);
        System.arraycopy(src, x + 1, out, x, n - x);
        return out;
    }

    /**
     * Returns a new {@code float[]} array from the given source array
     * by removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @return new {@code float[]} array.
     */
    public static float[] removeAt(final float[] src, final long idx) {
        Assert.index(idx, src);
        final int n, x;
        if ((x = (int) idx) == 0)
            return removeFirst(src);
        if (x == (n = src.length - 1))
            return removeLast(src);
        final var out = new float[n];
        System.arraycopy(src, 0, out, 0, x);
        System.arraycopy(src, x + 1, out, x, n - x);
        return out;
    }

    /**
     * Returns a new {@code double[]} array from the given source array
     * by removing the value located at the given index. If the given index
     * is outside the array a {@link IndexOutOfBoundsException} is thrown.
     *
     * @param src source array to be transformed.
     * @param idx index of the value to be removed.
     * @return new {@code double[]} array.
     */
    public static double[] removeAt(final double[] src, final long idx) {
        Assert.index(idx, src);
        final int n, x;
        if ((x = (int) idx) == 0)
            return removeFirst(src);
        if (x == (n = src.length - 1))
            return removeLast(src);
        final var out = new double[n];
        System.arraycopy(src, 0, out, 0, x);
        System.arraycopy(src, x + 1, out, x, n - x);
        return out;
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @param <A>   component-type of the source array.
     * @return new {@code A[]} array.
     */
    public static <A> A[] removeRange(final A[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return empty(src);
        final var out = Array.<A> alloc(src, n = src.length - m);
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    /**
     * Returns a new {@code boolean[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] removeRange(final boolean[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return emptyBool;
        final var out = new boolean[n = src.length - m];
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    /**
     * Returns a new {@code byte[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @return new {@code byte[]} array.
     */
    public static byte[] removeRange(final byte[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return emptyI8;
        final var out = new byte[n = src.length - m];
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    /**
     * Returns a new {@code short[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @return new {@code short[]} array.
     */
    public static short[] removeRange(final short[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return emptyI16;
        final var out = new short[n = src.length - m];
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    /**
     * Returns a new {@code char[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @return new {@code char[]} array.
     */
    public static char[] removeRange(final char[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return emptyC16;
        final var out = new char[n = src.length - m];
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    /**
     * Returns a new {@code int[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @return new {@code int[]} array.
     */
    public static int[] removeRange(final int[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return emptyI32;
        final var out = new int[n = src.length - m];
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    /**
     * Returns a new {@code long[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @return new {@code long[]} array.
     */
    public static long[] removeRange(final long[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return emptyI64;
        final var out = new long[n = src.length - m];
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    /**
     * Returns a new {@code float[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @return new {@code float[]} array.
     */
    public static float[] removeRange(final float[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return emptyF32;
        final var out = new float[n = src.length - m];
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    /**
     * Returns a new {@code double[]} array from the given source array by
     * discarding all values within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be transformed.
     * @param range lower/upper bounds of the index-range.
     * @return new {@code double[]} array.
     */
    public static double[] removeRange(final double[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int m = hi - lo, n;
        if (m == 1) return removeAt(src, lo);
        if (m == src.length) return emptyF64;
        final var out = new double[n = src.length - m];
        System.arraycopy(src, 0, out, 0, lo);
        System.arraycopy(src, hi, out, lo, n - lo);
        return out;
    }

    // ----------------------------------------------------------

    public static <A> A[] filter(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return filter(src, range(src), predicate);
    }

    public static byte[] filter(final byte[] src, final I8.Predicate predicate) {
        return filter(src, range(src), predicate);
    }

    public static short[] filter(final short[] src, final I16.Predicate predicate) {
        return filter(src, range(src), predicate);
    }

    public static char[] filter(final char[] src, final C16.Predicate predicate) {
        return filter(src, range(src), predicate);
    }

    public static int[] filter(final int[] src, final I32.Predicate predicate) {
        return filter(src, range(src), predicate);
    }

    public static long[] filter(final long[] src, final I64.Predicate predicate) {
        return filter(src, range(src), predicate);
    }

    public static float[] filter(final float[] src, final F32.Predicate predicate) {
        return filter(src, range(src), predicate);
    }

    public static double[] filter(final double[] src, final F64.Predicate predicate) {
        return filter(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new {@code A[]} array from the given source array by removing
     * all values that do not satisfy the given predicate within the specified
     * index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @param <A>       component-type of the source array.
     * @return a {@code A[]} array.
     */
    public static <A> A[] filter(final A[] src, final Range range,
                                 final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = Array.<A> alloc(src, len);
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    /**
     * Returns a new {@code boolean[]} array from the given source array by
     * removing all values that do not satisfy the given predicate within the
     * specified index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @return a {@code boolean[]} array.
     */
    public static boolean[] filter(final boolean[] src, final Range range,
                                   final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = new boolean[len];
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    /**
     * Returns a new {@code byte[]} array from the given source array by
     * removing all values that do not satisfy the given predicate within the
     * specified index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @return a {@code byte[]} array.
     */
    public static byte[] filter(final byte[] src, final Range range,
                                final I8.Predicate predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = new byte[len];
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    /**
     * Returns a new {@code short[]} array from the given source array by
     * removing all values that do not satisfy the given predicate within the
     * specified index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @return a {@code short[]} array.
     */
    public static short[] filter(final short[] src, final Range range,
                                 final I16.Predicate predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = new short[len];
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    /**
     * Returns a new {@code char[]} array from the given source array by
     * removing all values that do not satisfy the given predicate within the
     * specified index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @return a {@code char[]} array.
     */
    public static char[] filter(final char[] src, final Range range,
                                final C16.Predicate predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = new char[len];
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    /**
     * Returns a new {@code int[]} array from the given source array by
     * removing all values that do not satisfy the given predicate within the
     * specified index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @return a {@code int[]} array.
     */
    public static int[] filter(final int[] src, final Range range,
                               final I32.Predicate predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = new int[len];
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    /**
     * Returns a new {@code long[]} array from the given source array by
     * removing all values that do not satisfy the given predicate within the
     * specified index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @return a {@code long[]} array.
     */
    public static long[] filter(final long[] src, final Range range,
                                final I64.Predicate predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = new long[len];
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    /**
     * Returns a new {@code float[]} array from the given source array by
     * removing all values that do not satisfy the given predicate within the
     * specified index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @return a {@code float[]} array.
     */
    public static float[] filter(final float[] src, final Range range,
                                 final F32.Predicate predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = new float[len];
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    /**
     * Returns a new {@code double[]} array from the given source array by
     * removing all values that do not satisfy the given predicate within the
     * specified index-range
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src       source array to be transformed.
     * @param range     lower/upper bounds of the index-range.
     * @param predicate to be satisfied by the values to be included.
     * @return a {@code double[]} array.
     */
    public static double[] filter(final double[] src, final Range range,
                                  final F64.Predicate predicate) {
        precondition(src, range, predicate);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        int n = hi - lo, m = 0;
        final long[] tag = Bit.Vec.make(n);
        for (int i = lo; i < hi; ++i) {
            if (predicate.eval(src[i])) {
                Bit.Vec.set(tag, i - lo);
                ++m;
            }
        }
        if (m == n) return src;
        final int len = lo + m + (src.length - hi);
        final var out = new double[len];
        if (m == 0) {
            System.arraycopy(src, 0, out, 0, lo);
            System.arraycopy(src, hi, out, lo, src.length - hi);
            return out;
        }
        int i = 0, j, u = lo;
        System.arraycopy(src, 0, out, 0, u);
        while (u < len) {
            i = Bit.Vec.nextSetBit(tag, i);
            if (i == -1) {
                System.arraycopy(src, hi, out, u, src.length - hi);
                break;
            }
            j = Bit.Vec.nextClearBit(tag, i);
            if (j == -1) j = len;
            final int cnt;
            if ((cnt = j - i) == 1) {
                out[u++] = src[lo + i];
            } else {
                System.arraycopy(src, lo + i, out, u, cnt);
                u += cnt;
            }
            i = j;
        }
        return out;
    }

    // ----------------------------------------------------------

    public static <A, R> R[] map(final A[] src, final Fn1<? super A, ? extends R> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <A> A[] map(final boolean[] src, final Bool.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <A> A[] map(final byte[] src, final I8.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <A> A[] map(final short[] src, final I16.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <A> A[] map(final char[] src, final C16.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <A> A[] map(final int[] src, final I32.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <A> A[] map(final long[] src, final I64.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <A> A[] map(final float[] src, final F32.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <A> A[] map(final double[] src, final F64.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range(src), mapper);
    }

    // ----------------------------------------------------------

    public static <A, R> R[] map(final A[] src, final Class<? extends R[]> type, final Fn1<? super A, ? extends R> mapper) {
        return map(src, type, range(src), mapper);
    }

    public static <A> A[] map(final boolean[] src, final Class<? extends A[]> type, final Bool.To<? extends A> mapper) {
        return map(src, type, range(src), mapper);
    }

    public static <A> A[] map(final byte[] src, final Class<? extends A[]> type, final I8.To<? extends A> mapper) {
        return map(src, type, range(src), mapper);
    }

    public static <A> A[] map(final short[] src, final Class<? extends A[]> type, final I16.To<? extends A> mapper) {
        return map(src, type, range(src), mapper);
    }

    public static <A> A[] map(final char[] src, final Class<? extends A[]> type, final C16.To<? extends A> mapper) {
        return map(src, type, range(src), mapper);
    }

    public static <A> A[] map(final int[] src, final Class<? extends A[]> type, final I32.To<? extends A> mapper) {
        return map(src, type, range(src), mapper);
    }

    public static <A> A[] map(final long[] src, final Class<? extends A[]> type, final I64.To<? extends A> mapper) {
        return map(src, type, range(src), mapper);
    }

    public static <A> A[] map(final float[] src, final Class<? extends A[]> type, final F32.To<? extends A> mapper) {
        return map(src, type, range(src), mapper);
    }

    public static <A> A[] map(final double[] src, final Class<? extends A[]> type, final F64.To<? extends A> mapper) {
        return map(src, type, range(src), mapper);
    }

    // ----------------------------------------------------------

    public static <A, R> R[] map(final A[] src, final Range range, final Fn1<? super A, ? extends R> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    public static <A> A[] map(final boolean[] src, final Range range, final Bool.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    public static <A> A[] map(final byte[] src, final Range range, final I8.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    public static <A> A[] map(final short[] src, final Range range, final I16.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    public static <A> A[] map(final char[] src, final Range range, final C16.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    public static <A> A[] map(final int[] src, final Range range, final I32.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    public static <A> A[] map(final long[] src, final Range range, final I64.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    public static <A> A[] map(final float[] src, final Range range, final F32.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    public static <A> A[] map(final double[] src, final Range range, final F64.To<? extends A> mapper) {
        return map(src, Force.cast(Object[].class), range, mapper);
    }

    // ----------------------------------------------------------

    public static <R, A> A[] map(final R[] src, final Class<? extends A[]> type,
                                 final Range range, final Fn1<? super R, ? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <A> A[] map(final boolean[] src, final Class<? extends A[]> type,
                              final Range range, final Bool.To<? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <A> A[] map(final byte[] src, final Class<? extends A[]> type,
                              final Range range, final I8.To<? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <A> A[] map(final short[] src, final Class<? extends A[]> type,
                              final Range range, final I16.To<? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <A> A[] map(final char[] src, final Class<? extends A[]> type,
                              final Range range, final C16.To<? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <A> A[] map(final int[] src, final Class<? extends A[]> type,
                              final Range range, final I32.To<? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <A> A[] map(final long[] src, final Class<? extends A[]> type,
                              final Range range, final I64.To<? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <A> A[] map(final float[] src, final Class<? extends A[]> type,
                              final Range range, final F32.To<? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <A> A[] map(final double[] src, final Class<? extends A[]> type,
                              final Range range, final F64.To<? extends A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            final var out = Array.alloc(type, hi - lo);
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each value of type {@code A}
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static <A> boolean[] mapBool(final A[] src, final Fn1.ToBool<? super A> mapper) {
        return mapBool(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each {@code boolean} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final boolean[] src, final Bool.ToBool mapper) {
        return mapBool(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each {@code byte} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final byte[] src, final I8.ToBool mapper) {
        return mapBool(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each {@code short} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final short[] src, final I16.ToBool mapper) {
        return mapBool(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each {@code char} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final char[] src, final C16.ToBool mapper) {
        return mapBool(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each {@code int} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final int[] src, final I32.ToBool mapper) {
        return mapBool(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each {@code long} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final long[] src, final I64.ToBool mapper) {
        return mapBool(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each {@code float} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final float[] src, final F32.ToBool mapper) {
        return mapBool(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of
     * applying the given mapping function to each {@code double} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final double[] src, final F64.ToBool mapper) {
        return mapBool(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each value of type {@code A} in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static <A> boolean[] mapBool(final A[] src, final Range range, final Fn1.ToBool<? super A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each {@code boolean} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final boolean[] src, final Range range, final Bool.ToBool mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each  {@code byte} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final byte[] src, final Range range, final I8.ToBool mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each {@code byte} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final short[] src, final Range range, final I16.ToBool mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each {@code char} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final char[] src, final Range range, final C16.ToBool mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each {@code int} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final int[] src, final Range range, final I32.ToBool mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each {@code long} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final long[] src, final Range range, final I64.ToBool mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each {@code float} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final float[] src, final Range range, final F32.ToBool mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code boolean[]} consisting of the results of applying
     * the given mapping function to each {@code double} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code boolean[]} array.
     */
    public static boolean[] mapBool(final double[] src, final Range range, final F64.ToBool mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            final var out = new boolean[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each value of type {@code A}
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static <A> byte[] mapI8(final A[] src, final Fn1.ToI8<? super A> mapper) {
        return mapI8(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each {@code boolean} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final boolean[] src, final Bool.ToI8 mapper) {
        return mapI8(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each {@code byte} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final byte[] src, final I8.ToI8 mapper) {
        return mapI8(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each {@code short} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final short[] src, final I16.ToI8 mapper) {
        return mapI8(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each {@code char} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final char[] src, final C16.ToI8 mapper) {
        return mapI8(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each {@code int} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final int[] src, final I32.ToI8 mapper) {
        return mapI8(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each {@code long} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final long[] src, final I64.ToI8 mapper) {
        return mapI8(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each {@code float} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final float[] src, final F32.ToI8 mapper) {
        return mapI8(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of
     * applying the given mapping function to each {@code double} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final double[] src, final F64.ToI8 mapper) {
        return mapI8(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each value of type {@code A} in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static <A> byte[] mapI8(final A[] src, final Range range, final Fn1.ToI8<? super A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each {@code boolean} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final boolean[] src, final Range range, final Bool.ToI8 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each {@code byte} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final byte[] src, final Range range, final I8.ToI8 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each {@code short} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final short[] src, final Range range, final I16.ToI8 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each {@code char} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final char[] src, final Range range, final C16.ToI8 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each {@code int} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final int[] src, final Range range, final I32.ToI8 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each {@code long} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final long[] src, final Range range, final I64.ToI8 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each {@code float} value in the given source
     * array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final float[] src, final Range range, final F32.ToI8 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code byte[]} consisting of the results of applying
     * the given mapping function to each {@code double} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code byte[]} array.
     */
    public static byte[] mapI8(final double[] src, final Range range, final F64.ToI8 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            final var out = new byte[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each value of type {@code A}
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static <A> short[] mapI16(final A[] src, final Fn1.ToI16<? super A> mapper) {
        return mapI16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each {@code boolean} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final boolean[] src, final Bool.ToI16 mapper) {
        return mapI16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each {@code byte} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final byte[] src, final I8.ToI16 mapper) {
        return mapI16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each {@code short} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final short[] src, final I16.ToI16 mapper) {
        return mapI16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each {@code char} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final char[] src, final C16.ToI16 mapper) {
        return mapI16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each {@code int} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final int[] src, final I32.ToI16 mapper) {
        return mapI16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each {@code long} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final long[] src, final I64.ToI16 mapper) {
        return mapI16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each {@code float} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final float[] src, final F32.ToI16 mapper) {
        return mapI16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of
     * applying the given mapping function to each {@code double} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final double[] src, final F64.ToI16 mapper) {
        return mapI16(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each value of type {@code A} in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static <A> short[] mapI16(final A[] src, final Range range, final Fn1.ToI16<? super A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each {@code boolean} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final boolean[] src, final Range range, final Bool.ToI16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each {@code byte} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final byte[] src, final Range range, final I8.ToI16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each {@code short} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final short[] src, final Range range, final I16.ToI16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each {@code char} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final char[] src, final Range range, final C16.ToI16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each {@code int} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final int[] src, final Range range, final I32.ToI16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each {@code long} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final long[] src, final Range range, final I64.ToI16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each {@code float} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final float[] src, final Range range, final F32.ToI16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code short[]} consisting of the results of applying
     * the given mapping function to each {@code double} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code short[]} array.
     */
    public static short[] mapI16(final double[] src, final Range range, final F64.ToI16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            final var out = new short[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each value of type {@code A}
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static <A> char[] mapC16(final A[] src, final Fn1.ToC16<? super A> mapper) {
        return mapC16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each {@code boolean} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final boolean[] src, final Bool.ToC16 mapper) {
        return mapC16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each {@code byte} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final byte[] src, final I8.ToC16 mapper) {
        return mapC16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each {@code short} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final short[] src, final I16.ToC16 mapper) {
        return mapC16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each {@code char} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final char[] src, final C16.ToC16 mapper) {
        return mapC16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each {@code int} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final int[] src, final I32.ToC16 mapper) {
        return mapC16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each {@code long} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final long[] src, final I64.ToC16 mapper) {
        return mapC16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each {@code float} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final float[] src, final F32.ToC16 mapper) {
        return mapC16(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of
     * applying the given mapping function to each {@code double} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final double[] src, final F64.ToC16 mapper) {
        return mapC16(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each value of type {@code A} in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static <A> char[] mapC16(final A[] src, final Range range, final Fn1.ToC16<? super A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each {@code boolean} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final boolean[] src, final Range range, final Bool.ToC16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each {@code byte} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final byte[] src, final Range range, final I8.ToC16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each {@code short} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final short[] src, final Range range, final I16.ToC16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each {@code char} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final char[] src, final Range range, final C16.ToC16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each {@code int} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final int[] src, final Range range, final I32.ToC16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each {@code long} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final long[] src, final Range range, final I64.ToC16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each {@code float} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final float[] src, final Range range, final F32.ToC16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code char[]} consisting of the results of applying
     * the given mapping function to each {@code double} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code char[]} array.
     */
    public static char[] mapC16(final double[] src, final Range range, final F64.ToC16 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            final var out = new char[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each value of type {@code A}
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static <A> int[] mapI32(final A[] src, final Fn1.ToI32<? super A> mapper) {
        return mapI32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each {@code boolean} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final boolean[] src, final Bool.ToI32 mapper) {
        return mapI32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each {@code byte} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final byte[] src, final I8.ToI32 mapper) {
        return mapI32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each {@code short} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final short[] src, final I16.ToI32 mapper) {
        return mapI32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each {@code char} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final char[] src, final C16.ToI32 mapper) {
        return mapI32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each {@code int} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final int[] src, final I32.ToI32 mapper) {
        return mapI32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each {@code long} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final long[] src, final I64.ToI32 mapper) {
        return mapI32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each {@code float} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final float[] src, final F32.ToI32 mapper) {
        return mapI32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of
     * applying the given mapping function to each {@code double} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final double[] src, final F64.ToI32 mapper) {
        return mapI32(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each value of type {@code A} in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static <A> int[] mapI32(final A[] src, final Range range, final Fn1.ToI32<? super A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each {@code boolean} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final boolean[] src, final Range range, final Bool.ToI32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each {@code byte} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final byte[] src, final Range range, final I8.ToI32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each {@code short} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final short[] src, final Range range, final I16.ToI32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each {@code char} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final char[] src, final Range range, final C16.ToI32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each {@code int} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final int[] src, final Range range, final I32.ToI32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each {@code long} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final long[] src, final Range range, final I64.ToI32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each {@code float} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final float[] src, final Range range, final F32.ToI32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code int[]} consisting of the results of applying
     * the given mapping function to each {@code double} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code int[]} array.
     */
    public static int[] mapI32(final double[] src, final Range range, final F64.ToI32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            final var out = new int[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each value of type {@code A}
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static <A> long[] mapI64(final A[] src, final Fn1.ToI64<? super A> mapper) {
        return mapI64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each {@code boolean} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final boolean[] src, final Bool.ToI64 mapper) {
        return mapI64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each {@code byte} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final byte[] src, final I8.ToI64 mapper) {
        return mapI64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each {@code short} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final short[] src, final I16.ToI64 mapper) {
        return mapI64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each {@code char} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final char[] src, final C16.ToI64 mapper) {
        return mapI64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each {@code int} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final int[] src, final I32.ToI64 mapper) {
        return mapI64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each {@code long} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final long[] src, final I64.ToI64 mapper) {
        return mapI64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each {@code float} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final float[] src, final F32.ToI64 mapper) {
        return mapI64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of
     * applying the given mapping function to each {@code double} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final double[] src, final F64.ToI64 mapper) {
        return mapI64(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each value of type {@code A} in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static <A> long[] mapI64(final A[] src, final Range range, final Fn1.ToI64<? super A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each {@code boolean} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final boolean[] src, final Range range, final Bool.ToI64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each {@code byte} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final byte[] src, final Range range, final I8.ToI64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each {@code short} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final short[] src, final Range range, final I16.ToI64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each {@code char} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final char[] src, final Range range, final C16.ToI64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each {@code int} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final int[] src, final Range range, final I32.ToI64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each {@code long} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final long[] src, final Range range, final I64.ToI64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each {@code float} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final float[] src, final Range range, final F32.ToI64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code long[]} consisting of the results of applying
     * the given mapping function to each {@code double} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code long[]} array.
     */
    public static long[] mapI64(final double[] src, final Range range, final F64.ToI64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            final var out = new long[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each value of type {@code A}
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static <A> float[] mapF32(final A[] src, final Fn1.ToF32<? super A> mapper) {
        return mapF32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each {@code boolean} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final boolean[] src, final Bool.ToF32 mapper) {
        return mapF32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each {@code byte} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final byte[] src, final I8.ToF32 mapper) {
        return mapF32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each {@code short} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final short[] src, final I16.ToF32 mapper) {
        return mapF32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each {@code char} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final char[] src, final C16.ToF32 mapper) {
        return mapF32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each {@code int} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final int[] src, final I32.ToF32 mapper) {
        return mapF32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each {@code long} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final long[] src, final I64.ToF32 mapper) {
        return mapF32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each {@code float} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final float[] src, final F32.ToF32 mapper) {
        return mapF32(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of
     * applying the given mapping function to each {@code double} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final double[] src, final F64.ToF32 mapper) {
        return mapF32(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each value of type {@code A} in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static <A> float[] mapF32(final A[] src, final Range range, final Fn1.ToF32<? super A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each {@code boolean} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final boolean[] src, final Range range, final Bool.ToF32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each {@code byte} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final byte[] src, final Range range, final I8.ToF32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each {@code short} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final short[] src, final Range range, final I16.ToF32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each {@code char} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final char[] src, final Range range, final C16.ToF32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each {@code int} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final int[] src, final Range range, final I32.ToF32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each {@code long} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final long[] src, final Range range, final I64.ToF32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each {@code float} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final float[] src, final Range range, final F32.ToF32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code float[]} consisting of the results of applying
     * the given mapping function to each {@code double} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code float[]} array.
     */
    public static float[] mapF32(final double[] src, final Range range, final F64.ToF32 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            final var out = new float[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each value of type {@code A}
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static <A> double[] mapF64(final A[] src, final Fn1.ToF64<? super A> mapper) {
        return mapF64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each {@code boolean} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final boolean[] src, final Bool.ToF64 mapper) {
        return mapF64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each {@code byte} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final byte[] src, final I8.ToF64 mapper) {
        return mapF64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each {@code short} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final short[] src, final I16.ToF64 mapper) {
        return mapF64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each {@code char} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final char[] src, final C16.ToF64 mapper) {
        return mapF64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each {@code int} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final int[] src, final I32.ToF64 mapper) {
        return mapF64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each {@code long} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final long[] src, final I64.ToF64 mapper) {
        return mapF64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each {@code float} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final float[] src, final F32.ToF64 mapper) {
        return mapF64(src, range(src), mapper);
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of
     * applying the given mapping function to each {@code double} value
     * in the given source array.
     *
     * @param src    source array to be mapped.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final double[] src, final F64.ToF64 mapper) {
        return mapF64(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each value of type {@code A} in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static <A> double[] mapF64(final A[] src, final Range range, final Fn1.ToF64<? super A> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each {@code boolean} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final boolean[] src, final Range range, final Bool.ToF64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each {@code byte} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final byte[] src, final Range range, final I8.ToF64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each {@code short} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final short[] src, final Range range, final I16.ToF64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each {@code char} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final char[] src, final Range range, final C16.ToF64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each {@code int} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final int[] src, final Range range, final I32.ToF64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each {@code long} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final long[] src, final Range range, final I64.ToF64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each {@code float} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final float[] src, final Range range, final F32.ToF64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns a new array {@code double[]} consisting of the results of applying
     * the given mapping function to each {@code double} value in the given
     * source array within the specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to source values.
     * @return new {@code double[]} array.
     */
    public static double[] mapF64(final double[] src, final Range range, final F64.ToF64 mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            final var out = new double[hi - lo];
            for (int i = lo, j = 0; i < hi; ++i) {
                out[j++] = mapper.onApply(src[i]);
            }
            return out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A, R> R[] flatMap(final A[] src, final Fn1<? super A, ? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <R> R[] flatMap(final boolean[] src, final Bool.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <R> R[] flatMap(final byte[] src, final I8.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <R> R[] flatMap(final short[] src, final I16.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <R> R[] flatMap(final char[] src, final C16.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <R> R[] flatMap(final int[] src, final I32.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <R> R[] flatMap(final long[] src, final I64.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <R> R[] flatMap(final float[] src, final F32.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    public static <R> R[] flatMap(final double[] src, final F64.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range(src), mapper);
    }

    // ----------------------------------------------------------

    public static <A, R> R[] flatMap(final A[] src, final Class<? extends R[]> type,
                                     final Fn1<? super A, ? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    public static <R> R[] flatMap(final boolean[] src, final Class<? extends R[]> type,
                                  final Bool.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    public static <R> R[] flatMap(final byte[] src, final Class<? extends R[]> type,
                                  final I8.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    public static <R> R[] flatMap(final short[] src, final Class<? extends R[]> type,
                                  final I16.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    public static <R> R[] flatMap(final char[] src, final Class<? extends R[]> type,
                                  final C16.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    public static <R> R[] flatMap(final int[] src, final Class<? extends R[]> type,
                                  final I32.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    public static <R> R[] flatMap(final long[] src, final Class<? extends R[]> type,
                                  final I64.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    public static <R> R[] flatMap(final float[] src, final Class<? extends R[]> type,
                                  final F32.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    public static <R> R[] flatMap(final double[] src, final Class<? extends R[]> type,
                                  final F64.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, type, range(src), mapper);
    }

    // ----------------------------------------------------------

    public static <A, R> R[] flatMap(final A[] src, final Range range,
                                     final Fn1<? super A, ? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    public static <R> R[] flatMap(final boolean[] src, final Range range,
                                  final Bool.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    public static <R> R[] flatMap(final byte[] src, final Range range,
                                  final I8.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    public static <R> R[] flatMap(final short[] src, final Range range,
                                  final I16.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    public static <R> R[] flatMap(final char[] src, final Range range,
                                  final C16.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    public static <R> R[] flatMap(final int[] src, final Range range,
                                  final I32.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    public static <R> R[] flatMap(final long[] src, final Range range,
                                  final I64.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    public static <R> R[] flatMap(final float[] src, final Range range,
                                  final F32.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    public static <R> R[] flatMap(final double[] src, final Range range,
                                  final F64.To<? extends Iterable<? extends R>> mapper) {
        return flatMap(src, Force.cast(Object[].class), range, mapper);
    }

    // ----------------------------------------------------------

    public static <A, R> R[] flatMap(final A[] src, final Class<? extends R[]> type, final Range range,
                                     final Fn1<? super A, ? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <R> R[] flatMap(final boolean[] src, final Class<? extends R[]> type, final Range range,
                                  final Bool.To<? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <R> R[] flatMap(final byte[] src, final Class<? extends R[]> type, final Range range,
                                  final I8.To<? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <R> R[] flatMap(final short[] src, final Class<? extends R[]> type, final Range range,
                                  final I16.To<? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <R> R[] flatMap(final char[] src, final Class<? extends R[]> type, final Range range,
                                  final C16.To<? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <R> R[] flatMap(final int[] src, final Class<? extends R[]> type, final Range range,
                                  final I32.To<? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <R> R[] flatMap(final long[] src, final Class<? extends R[]> type, final Range range,
                                  final I64.To<? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <R> R[] flatMap(final float[] src, final Class<? extends R[]> type, final Range range,
                                  final F32.To<? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static <R> R[] flatMap(final double[] src, final Class<? extends R[]> type, final Range range,
                                  final F64.To<? extends Iterable<? extends R>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return empty(type);
            int j = 0;
            var out = Array.alloc(type, INITIAL_CAPACITY);
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    R val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A> boolean[] flatMapBool(final A[] src, final Fn1<? super A, ? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    public static boolean[] flatMapBool(final boolean[] src, final Bool.To<? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    public static boolean[] flatMapBool(final byte[] src, final I8.To<? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    public static boolean[] flatMapBool(final short[] src, final I16.To<? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    public static boolean[] flatMapBool(final char[] src, final C16.To<? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    public static boolean[] flatMapBool(final int[] src, final I32.To<? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    public static boolean[] flatMapBool(final long[] src, final I64.To<? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    public static boolean[] flatMapBool(final float[] src, final F32.To<? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    public static boolean[] flatMapBool(final double[] src, final F64.To<? extends Iterable<Boolean>> mapper) {
        return flatMapBool(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static <A> boolean[] flatMapBool(final A[] src, final Range range,
                                            final Fn1<? super A, ? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static boolean[] flatMapBool(final boolean[] src, final Range range,
                                        final Bool.To<? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static boolean[] flatMapBool(final byte[] src, final Range range,
                                        final I8.To<? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static boolean[] flatMapBool(final short[] src, final Range range,
                                        final I16.To<? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static boolean[] flatMapBool(final char[] src, final Range range,
                                        final C16.To<? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static boolean[] flatMapBool(final int[] src, final Range range,
                                        final I32.To<? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static boolean[] flatMapBool(final long[] src, final Range range,
                                        final I64.To<? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static boolean[] flatMapBool(final float[] src, final Range range,
                                        final F32.To<? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static boolean[] flatMapBool(final double[] src, final Range range,
                                        final F64.To<? extends Iterable<Boolean>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyBool;
            int j = 0;
            var out = new boolean[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Boolean val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A> byte[] flatMapI8(final A[] src, final Fn1<? super A, ? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    public static byte[] flatMapI8(final boolean[] src, final Bool.To<? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    public static byte[] flatMapI8(final byte[] src, final I8.To<? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    public static byte[] flatMapI8(final short[] src, final I16.To<? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    public static byte[] flatMapI8(final char[] src, final C16.To<? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    public static byte[] flatMapI8(final int[] src, final I32.To<? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    public static byte[] flatMapI8(final long[] src, final I64.To<? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    public static byte[] flatMapI8(final float[] src, final F32.To<? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    public static byte[] flatMapI8(final double[] src, final F64.To<? extends Iterable<Byte>> mapper) {
        return flatMapI8(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static <A> byte[] flatMapI8(final A[] src, final Range range,
                                       final Fn1<? super A, ? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static byte[] flatMapI8(final boolean[] src, final Range range,
                                   final Bool.To<? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static byte[] flatMapI8(final byte[] src, final Range range,
                                   final I8.To<? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static byte[] flatMapI8(final short[] src, final Range range,
                                   final I16.To<? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static byte[] flatMapI8(final char[] src, final Range range,
                                   final C16.To<? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static byte[] flatMapI8(final int[] src, final Range range,
                                   final I32.To<? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static byte[] flatMapI8(final long[] src, final Range range,
                                   final I64.To<? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static byte[] flatMapI8(final float[] src, final Range range,
                                   final F32.To<? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code byte[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code byte[]} array of flattened values.
     */
    public static byte[] flatMapI8(final double[] src, final Range range,
                                   final F64.To<? extends Iterable<Byte>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI8;
            int j = 0;
            var out = new byte[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Byte val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A> short[] flatMapI16(final A[] src, final Fn1<? super A, ? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    public static short[] flatMapI16(final boolean[] src, final Bool.To<? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    public static short[] flatMapI16(final byte[] src, final I8.To<? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    public static short[] flatMapI16(final short[] src, final I16.To<? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    public static short[] flatMapI16(final char[] src, final C16.To<? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    public static short[] flatMapI16(final int[] src, final I32.To<? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    public static short[] flatMapI16(final long[] src, final I64.To<? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    public static short[] flatMapI16(final float[] src, final F32.To<? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    public static short[] flatMapI16(final double[] src, final F64.To<? extends Iterable<Short>> mapper) {
        return flatMapI16(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static <A> short[] flatMapI16(final A[] src, final Range range,
                                         final Fn1<? super A, ? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static short[] flatMapI16(final boolean[] src, final Range range,
                                     final Bool.To<? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static short[] flatMapI16(final byte[] src, final Range range,
                                     final I8.To<? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static short[] flatMapI16(final short[] src, final Range range,
                                     final I16.To<? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static short[] flatMapI16(final char[] src, final Range range,
                                     final C16.To<? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static short[] flatMapI16(final int[] src, final Range range,
                                     final I32.To<? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static short[] flatMapI16(final long[] src, final Range range,
                                     final I64.To<? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static short[] flatMapI16(final float[] src, final Range range,
                                     final F32.To<? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code short[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code short[]} array of flattened values.
     */
    public static short[] flatMapI16(final double[] src, final Range range,
                                     final F64.To<? extends Iterable<Short>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI16;
            int j = 0;
            var out = new short[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Short val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A> char[] flatMapC16(final A[] src, final Fn1<? super A, ? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    public static char[] flatMapC16(final boolean[] src, final Bool.To<? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    public static char[] flatMapC16(final byte[] src, final I8.To<? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    public static char[] flatMapC16(final short[] src, final I16.To<? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    public static char[] flatMapC16(final char[] src, final C16.To<? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    public static char[] flatMapC16(final int[] src, final I32.To<? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    public static char[] flatMapC16(final long[] src, final I64.To<? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    public static char[] flatMapC16(final float[] src, final F32.To<? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    public static char[] flatMapC16(final double[] src, final F64.To<? extends Iterable<Character>> mapper) {
        return flatMapC16(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static <A> char[] flatMapC16(final A[] src, final Range range,
                                        final Fn1<? super A, ? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static char[] flatMapC16(final boolean[] src, final Range range,
                                    final Bool.To<? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static char[] flatMapC16(final byte[] src, final Range range,
                                    final I8.To<? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static char[] flatMapC16(final short[] src, final Range range,
                                    final I16.To<? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static char[] flatMapC16(final char[] src, final Range range,
                                    final C16.To<? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static char[] flatMapC16(final int[] src, final Range range,
                                    final I32.To<? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static char[] flatMapC16(final long[] src, final Range range,
                                    final I64.To<? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static char[] flatMapC16(final float[] src, final Range range,
                                    final F32.To<? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code char[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code char[]} array of flattened values.
     */
    public static char[] flatMapC16(final double[] src, final Range range,
                                    final F64.To<? extends Iterable<Character>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyC16;
            int j = 0;
            var out = new char[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Character val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A> int[] flatMapI32(final A[] src, final Fn1<? super A, ? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    public static int[] flatMapI32(final boolean[] src, final Bool.To<? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    public static int[] flatMapI32(final byte[] src, final I8.To<? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    public static int[] flatMapI32(final short[] src, final I16.To<? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    public static int[] flatMapI32(final char[] src, final C16.To<? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    public static int[] flatMapI32(final int[] src, final I32.To<? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    public static int[] flatMapI32(final long[] src, final I64.To<? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    public static int[] flatMapI32(final float[] src, final F32.To<? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    public static int[] flatMapI32(final double[] src, final F64.To<? extends Iterable<Integer>> mapper) {
        return flatMapI32(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static <A> int[] flatMapI32(final A[] src, final Range range,
                                       final Fn1<? super A, ? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static int[] flatMapI32(final boolean[] src, final Range range,
                                   final Bool.To<? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static int[] flatMapI32(final byte[] src, final Range range,
                                   final I8.To<? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static int[] flatMapI32(final short[] src, final Range range,
                                   final I16.To<? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static int[] flatMapI32(final char[] src, final Range range,
                                   final C16.To<? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static int[] flatMapI32(final int[] src, final Range range,
                                   final I32.To<? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static int[] flatMapI32(final long[] src, final Range range,
                                   final I64.To<? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static int[] flatMapI32(final float[] src, final Range range,
                                   final F32.To<? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code int[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code int[]} array of flattened values.
     */
    public static int[] flatMapI32(final double[] src, final Range range,
                                   final F64.To<? extends Iterable<Integer>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI32;
            int j = 0;
            var out = new int[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Integer val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static <A> long[] flatMapI64(final A[] src, final Fn1<? super A, ? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final boolean[] src, final Bool.To<? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final byte[] src, final I8.To<? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final short[] src, final I16.To<? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final char[] src, final C16.To<? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final int[] src, final I32.To<? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final long[] src, final I64.To<? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final float[] src, final F32.To<? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final double[] src, final F64.To<? extends Iterable<Long>> mapper) {
        return flatMapI64(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static <A> long[] flatMapI64(final A[] src, final Range range,
                                        final Fn1<? super A, ? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final boolean[] src, final Range range,
                                    final Bool.To<? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final byte[] src, final Range range,
                                    final I8.To<? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final short[] src, final Range range,
                                    final I16.To<? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final char[] src, final Range range,
                                    final C16.To<? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final int[] src, final Range range,
                                    final I32.To<? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final long[] src, final Range range,
                                    final I64.To<? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final float[] src, final Range range,
                                    final F32.To<? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code long[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code long[]} array of flattened values.
     */
    public static long[] flatMapI64(final double[] src, final Range range,
                                    final F64.To<? extends Iterable<Long>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyI64;
            int j = 0;
            var out = new long[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Long val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static <A> float[] flatMapF32(final A[] src, final Fn1<? super A, ? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final boolean[] src, final Bool.To<? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final byte[] src, final I8.To<? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final short[] src, final I16.To<? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final char[] src, final C16.To<? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final int[] src, final I32.To<? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final long[] src, final I64.To<? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final float[] src, final F32.To<? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each
     * source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation
     * for each value in the wrapped array, and then flattening produced
     * results into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final double[] src, final F64.To<? extends Iterable<Float>> mapper) {
        return flatMapF32(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static <A> float[] flatMapF32(final A[] src, final Range range,
                                         final Fn1<? super A, ? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code boolean[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code boolean[]} array of flattened values.
     */
    public static float[] flatMapF32(final boolean[] src, final Range range,
                                     final Bool.To<? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code float[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final byte[] src, final Range range,
                                     final I8.To<? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code flat[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final short[] src, final Range range,
                                     final I16.To<? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code flat[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final char[] src, final Range range,
                                     final C16.To<? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code flat[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final int[] src, final Range range,
                                     final I32.To<? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code flat[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final long[] src, final Range range,
                                     final I64.To<? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code flat[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final float[] src, final Range range,
                                     final F32.To<? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code flat[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code float[]} array of flattened values.
     */
    public static float[] flatMapF32(final double[] src, final Range range,
                                     final F64.To<? extends Iterable<Float>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF32;
            int j = 0;
            var out = new float[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Float val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array with the contents of
     * a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static <A> double[] flatMapF64(final A[] src, final Fn1<? super A, ? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array with the contents of
     * a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final boolean[] src, final Bool.To<? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array with the contents of
     * a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final byte[] src, final I8.To<? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array with the contents of
     * a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final short[] src, final I16.To<? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array with the contents of
     * a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final char[] src, final C16.To<? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array with the contents of
     * a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final int[] src, final I32.To<? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array with the contents of
     * a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final long[] src, final I64.To<? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array with the contents of
     * a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final float[] src, final F32.To<? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array with the contents
     * of a mapped iterable by applying the given mapping function to each source
     * value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     *
     * @param src    source array to be flat-mapped.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final double[] src, final F64.To<? extends Iterable<Double>> mapper) {
        return flatMapF64(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code A} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static <A> double[] flatMapF64(final A[] src, final Range range,
                                          final Fn1<? super A, ? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code boolean} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final boolean[] src, final Range range,
                                      final Bool.To<? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code byte} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final byte[] src, final Range range,
                                      final I8.To<? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code short} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final short[] src, final Range range,
                                      final I16.To<? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code char} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final char[] src, final Range range,
                                      final C16.To<? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code int} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final int[] src, final Range range,
                                      final I32.To<? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code long} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final long[] src, final Range range,
                                      final I64.To<? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code float} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final float[] src, final Range range,
                                      final F32.To<? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Produces a new {@code double[]} array consisting of values obtained by
     * replacing each {@code double} value in the source array within the specified
     * index range with the contents of a mapped iterable by applying the given
     * mapping function to each source value.
     * <p>
     * The operation has the effect of applying a one-to-many transmutation for
     * each value in the wrapped array, and then flattening produced results
     * into a new wrapped array.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be flat-mapped.
     * @param range  lower/upper bounds of the index-range.
     * @param mapper function to be applied to each selected value.
     * @return new {@code double[]} array of flattened values.
     */
    public static double[] flatMapF64(final double[] src, final Range range,
                                      final F64.To<? extends Iterable<Double>> mapper) {
        precondition(src, range, mapper);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.lo(range);
            if (src.length == 0 || lo >= hi)
                return emptyF64;
            int j = 0;
            var out = new double[INITIAL_CAPACITY];
            for (int i = lo; i < hi; ++i) {
                final var itr = mapper.onApply(src[i++]);
                if (itr != null) {
                    Double val;
                    for (final var it = itr.iterator(); it.hasNext(); ) {
                        if ((val = it.next()) != null) {
                            if (j <= out.length) {
                                out = Capacity.ensure(out, j + 1);
                            }
                            out[j++] = val;
                        }
                    }
                }
            }
            return (out.length != j) ? Array.copy(out, j) : out;
        } catch (final Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * TODO
     */
    public static <A> A[] mapUpdate(final A[] src, final Fn1<A, A> mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    /**
     * TODO
     */
    public static boolean[] mapUpdate(final boolean[] src, final Bool.ToBool mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    /**
     * TODO
     */
    public static byte[] mapUpdate(final byte[] src, final I8.ToI8 mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    /**
     * TODO
     */
    public static short[] mapUpdate(final short[] src, final I16.ToI16 mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    /**
     * TODO
     */
    public static char[] mapUpdate(final char[] src, final C16.ToC16 mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    /**
     * TODO
     */
    public static int[] mapUpdate(final int[] src, final I32.ToI32 mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    /**
     * TODO
     */
    public static long[] mapUpdate(final long[] src, final I64.ToI64 mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    /**
     * TODO
     */
    public static float[] mapUpdate(final float[] src, final F32.ToF32 mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    /**
     * TODO
     */
    public static double[] mapUpdate(final double[] src, final F64.ToF64 mapper) {
        return mapUpdate(src, range(src), mapper);
    }

    // ----------------------------------------------------------

    /**
     * TODO
     */
    public static <A> A[] mapUpdate(final A[] src, final Range range, final Fn1<A, A> mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    /**
     * TODO
     */
    public static boolean[] mapUpdate(final boolean[] src, final Range range, final Bool.ToBool mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    /**
     * TODO
     */
    public static byte[] mapUpdate(final byte[] src, final Range range, final I8.ToI8 mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    /**
     * TODO
     */
    public static short[] mapUpdate(final short[] src, final Range range, final I16.ToI16 mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    /**
     * TODO
     */
    public static char[] mapUpdate(final char[] src, final Range range, final C16.ToC16 mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    /**
     * TODO
     */
    public static int[] mapUpdate(final int[] src, final Range range, final I32.ToI32 mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    /**
     * TODO
     */
    public static long[] mapUpdate(final long[] src, final Range range, final I64.ToI64 mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    /**
     * TODO
     */
    public static float[] mapUpdate(final float[] src, final Range range, final F32.ToF32 mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    /**
     * TODO
     */
    public static double[] mapUpdate(final double[] src, final Range range, final F64.ToF64 mapper) {
        precondition(src, range, mapper);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (lo >= hi) return src;
        for (int i = lo; i < hi; ++i) {
            src[i] = mapper.apply(src[i]);
        }
        return src;
    }

    // ----------------------------------------------------------

    public static <A> A[] reverseUpdate(final A[] src) {
        return reverseUpdate(src, range(src));
    }

    public static boolean[] reverseUpdate(final boolean[] src) {
        return reverseUpdate(src, range(src));
    }

    public static byte[] reverseUpdate(final byte[] src) {
        return reverseUpdate(src, range(src));
    }

    public static short[] reverseUpdate(final short[] src) {
        return reverseUpdate(src, range(src));
    }

    public static char[] reverseUpdate(final char[] src) {
        return reverseUpdate(src, range(src));
    }

    public static int[] reverseUpdate(final int[] src) {
        return reverseUpdate(src, range(src));
    }

    public static long[] reverseUpdate(final long[] src) {
        return reverseUpdate(src, range(src));
    }

    public static float[] reverseUpdate(final float[] src) {
        return reverseUpdate(src, range(src));
    }

    public static double[] reverseUpdate(final double[] src) {
        return reverseUpdate(src, range(src));
    }

    // ----------------------------------------------------------

    /**
     * Returns the given {@link A[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @param <A>   component-type of the source array.
     * @return reversed {@link A[]} array.
     */
    public static <A> A[] reverseUpdate(final A[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    /**
     * Returns the given {@link boolean[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @return reversed {@link boolean[]} array.
     */
    public static boolean[] reverseUpdate(final boolean[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    /**
     * Returns the given {@link byte[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @return reversed {@link byte[]} array.
     */
    public static byte[] reverseUpdate(final byte[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    /**
     * Returns the given {@link short[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @return reversed {@link short[]} array.
     */
    public static short[] reverseUpdate(final short[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    /**
     * Returns the given {@link char[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @return reversed {@link char[]} array.
     */
    public static char[] reverseUpdate(final char[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    /**
     * Returns the given {@link int[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @return reversed {@link int[]} array.
     */
    public static int[] reverseUpdate(final int[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    /**
     * Returns the given {@link long[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @return reversed {@link long[]} array.
     */
    public static long[] reverseUpdate(final long[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    /**
     * Returns the given {@link float[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @return reversed {@link float[]} array.
     */
    public static float[] reverseUpdate(final float[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    /**
     * Returns the given {@link double[]} source array with reversed value
     * arrangement within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be updated.
     * @param range lower/upper bounds of the index-range.
     * @return reversed {@link double[]} array.
     */
    public static double[] reverseUpdate(final double[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        final int n = hi - lo;
        if (n <= 1) return src;
        final int m = lo + (n / 2);
        for (int i = lo; i < m; ++i) {
            final var tmp = src[i];
            src[i] = src[hi - 1 - i];
            src[hi - 1 - i] = tmp;
        }
        return src;
    }

    // ----------------------------------------------------------

    /**
     * TODO
     */
    public static <A> A[] sortUpdate(final A[] src, final java.util.Comparator<? super A> comparator) {
        return sortUpdate(src, range(src), Sorting.ASCENDING, comparator);
    }

    /**
     * TODO
     */
    public static <A extends Comparable<A>> A[] sortUpdate(final A[] src) {
        return sortUpdate(src, range(src), Sorting.ASCENDING);
    }

    /**
     * TODO
     */
    public static byte[] sortUpdate(final byte[] src) {
        return sortUpdate(src, range(src), Sorting.ASCENDING);
    }

    /**
     * TODO
     */
    public static short[] sortUpdate(final short[] src) {
        return sortUpdate(src, range(src), Sorting.ASCENDING);
    }

    /**
     * TODO
     */
    public static char[] sortUpdate(final char[] src) {
        return sortUpdate(src, range(src), Sorting.ASCENDING);
    }

    /**
     * TODO
     */
    public static int[] sortUpdate(final int[] src) {
        return sortUpdate(src, range(src), Sorting.ASCENDING);
    }

    /**
     * TODO
     */
    public static long[] sortUpdate(final long[] src) {
        return sortUpdate(src, range(src), Sorting.ASCENDING);
    }

    /**
     * TODO
     */
    public static float[] sortUpdate(final float[] src) {
        return sortUpdate(src, range(src), Sorting.ASCENDING);
    }

    /**
     * TODO
     */
    public static double[] sortUpdate(final double[] src) {
        return sortUpdate(src, range(src), Sorting.ASCENDING);
    }

    // ----------------------------------------------------------

    /**
     * TODO
     */
    public static <A> A[] sortUpdate(final A[] src, final Sorting mode, final java.util.Comparator<? super A> comparator) {
        return sortUpdate(src, range(src), mode, comparator);
    }

    /**
     * TODO
     */
    public static <A extends Comparable<A>> A[] sortUpdate(final A[] src, final Sorting mode) {
        return sortUpdate(src, range(src), mode);
    }

    /**
     * TODO
     */
    public static byte[] sortUpdate(final byte[] src, final Sorting mode) {
        return sortUpdate(src, range(src), mode);
    }

    /**
     * TODO
     */
    public static short[] sortUpdate(final short[] src, final Sorting mode) {
        return sortUpdate(src, range(src), mode);
    }

    /**
     * TODO
     */
    public static char[] sortUpdate(final char[] src, final Sorting mode) {
        return sortUpdate(src, range(src), mode);
    }

    /**
     * TODO
     */
    public static int[] sortUpdate(final int[] src, final Sorting mode) {
        return sortUpdate(src, range(src), mode);
    }

    /**
     * TODO
     */
    public static long[] sortUpdate(final long[] src, final Sorting mode) {
        return sortUpdate(src, range(src), mode);
    }

    /**
     * TODO
     */
    public static float[] sortUpdate(final float[] src, final Sorting mode) {
        return sortUpdate(src, range(src), mode);
    }

    /**
     * TODO
     */
    public static double[] sortUpdate(final double[] src, final Sorting mode) {
        return sortUpdate(src, range(src), mode);
    }

    // ----------------------------------------------------------

    /**
     * Returns the given source {@code A[]} array with sorted values
     * according to the given comparator within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src        source array to be sorted.
     * @param range      lower/upper bounds of the index-range.
     * @param comparator required to compare values.
     * @param <A>        component-type of the source array.
     * @return sorted {@code A[]} array.
     */
    public static <A> A[] sortUpdate(final A[] src, final Range range, final java.util.Comparator<? super A> comparator) {
        return sortUpdate(src, range, Sorting.ASCENDING, comparator);
    }

    /**
     * Returns the given source {@code A[]} array with sorted values in
     * ascending order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param <A>   component-type of the source array.
     * @return sorted {@code A[]} array.
     */
    public static <A extends Comparable<A>> A[] sortUpdate(final A[] src, final Range range) {
        return sortUpdate(src, range, Sorting.ASCENDING);
    }

    /**
     * Returns the given source {@code byte[]} array with sorted values in
     * ascending order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @return sorted {@code byte[]} array.
     */
    public static byte[] sortUpdate(final byte[] src, final Range range) {
        return sortUpdate(src, range, Sorting.ASCENDING);
    }

    /**
     * Returns the given source {@code short[]} array with sorted values in
     * ascending order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @return sorted {@code short[]} array.
     */
    public static short[] sortUpdate(final short[] src, final Range range) {
        return sortUpdate(src, range, Sorting.ASCENDING);
    }

    /**
     * Returns the given source {@code char[]} array with sorted values in
     * ascending order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @return sorted {@code char[]} array.
     */
    public static char[] sortUpdate(final char[] src, final Range range) {
        return sortUpdate(src, range, Sorting.ASCENDING);
    }

    /**
     * Returns the given source {@code int[]} array with sorted values in
     * ascending order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @return sorted {@code int[]} array.
     */
    public static int[] sortUpdate(final int[] src, final Range range) {
        return sortUpdate(src, range, Sorting.ASCENDING);
    }

    /**
     * Returns the given source {@code long[]} array with sorted values in
     * ascending order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @return sorted {@code long[]} array.
     */
    public static long[] sortUpdate(final long[] src, final Range range) {
        return sortUpdate(src, range, Sorting.ASCENDING);
    }

    /**
     * Returns the given source {@code float[]} array with sorted values in
     * ascending order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @return sorted {@code float[]} array.
     */
    public static float[] sortUpdate(final float[] src, final Range range) {
        return sortUpdate(src, range, Sorting.ASCENDING);
    }

    /**
     * Returns the given source {@code double[]} array with sorted values in
     * ascending order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @return sorted {@code double[]} array.
     */
    public static double[] sortUpdate(final double[] src, final Range range) {
        return sortUpdate(src, range, Sorting.ASCENDING);
    }

    // ----------------------------------------------------------

    /**
     * Returns the given source {@code A[]} array with sorted values within the
     * specified index-range according to the given comparator and sort order.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src        source array to be sorted.
     * @param range      lower/upper bounds of the index-range.
     * @param mode       of the sorting to be applied.
     * @param comparator required to compare values.
     * @param <A>        component-type of the source array.
     * @return sorted {@code A[]} array.
     */
    public static <A> A[] sortUpdate(final A[] src, final Range range, final Sorting mode,
                                     final java.util.Comparator<? super A> comparator) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi, comparator);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    /**
     * Returns the given source {@code A[]} array with sorted values according
     * to the given sort order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param mode  of the sorting to be applied.
     * @param <A>   component-type of the source array.
     * @return sorted {@link A[]} array.
     */
    public static <A extends Comparable<A>> A[] sortUpdate(final A[] src, final Range range, final Sorting mode) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    /**
     * Returns the given source {@code byte[]} array with sorted values
     * according to the given sort order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param mode  of the sorting to be applied.
     * @return sorted {@code byte[]} array.
     */
    public static byte[] sortUpdate(final byte[] src, final Range range, final Sorting mode) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    /**
     * Returns the given source {@code short[]} array with sorted values
     * according to the given sort order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param mode  of the sorting to be applied.
     * @return sorted {@code short[]} array.
     */
    public static short[] sortUpdate(final short[] src, final Range range, final Sorting mode) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    /**
     * Returns the given source {@code char[]} array with sorted values
     * according to the given sort order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param mode  of the sorting to be applied.
     * @return sorted {@code char[]} array.
     */
    public static char[] sortUpdate(final char[] src, final Range range, final Sorting mode) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    /**
     * Returns the given source {@code int[]} array with sorted values
     * according to the given sort order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param mode  of the sorting to be applied.
     * @return sorted {@code int[]} array.
     */
    public static int[] sortUpdate(final int[] src, final Range range, final Sorting mode) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    /**
     * Returns the given source {@code long[]} array with sorted values
     * according to the given sort order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param mode  of the sorting to be applied.
     * @return sorted {@code long[]} array.
     */
    public static long[] sortUpdate(final long[] src, final Range range, final Sorting mode) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    /**
     * Returns the given source {@code float[]} array with sorted values
     * according to the given sort order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param mode  of the sorting to be applied.
     * @return sorted {@code float[]} array.
     */
    public static float[] sortUpdate(final float[] src, final Range range, final Sorting mode) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    /**
     * Returns the given source {@code double[]} array with sorted values
     * according to the given sort order within the specified index-range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be sorted.
     * @param range lower/upper bounds of the index-range.
     * @param mode  of the sorting to be applied.
     * @return sorted {@code double[]} array.
     */
    public static double[] sortUpdate(final double[] src, final Range range, final Sorting mode) {
        precondition(src, range, mode);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (hi - lo <= 1) return src;
        java.util.Arrays.sort(src, lo, hi);
        if (mode == Sorting.DESCENDING) {
            reverseUpdate(src, range);
        }
        return src;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of folding the values of the given {@link A[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <A>      component-type of source array.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <A, Z> Z foldLeft(final A[] src, final Z initial, final Fn2<Z, ? super A, Z> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link boolean[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final boolean[] src, final Z initial, final Fn1<Z, Bool.To<Z>> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link byte[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final byte[] src, final Z initial, final Fn1<Z, I8.To<Z>> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link short[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final short[] src, final Z initial, final Fn1<Z, I16.To<Z>> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link char[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final char[] src, final Z initial, final Fn1<Z, C16.To<Z>> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link int[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final int[] src, final Z initial, final Fn1<Z, I32.To<Z>> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link long[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final long[] src, final Z initial, final Fn1<Z, I64.To<Z>> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link float[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final float[] src, final Z initial, final Fn1<Z, F32.To<Z>> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link double[]}
     * array using the given binary combiner function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be left folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final double[] src, final Z initial, final Fn1<Z, F64.To<Z>> combiner) {
        return foldLeft(src, range(src), initial, combiner);
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of folding the values of the given {@link A[]} array
     * within the specified index-range using the given binary combiner function
     * from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <A>      component-type of source array.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <A, Z> Z foldLeft(final A[] src, final Range range, final Z initial,
                                    final Fn2<Z, ? super A, Z> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state, src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link boolean[]}
     * array within the specified index-range using the given binary combiner
     * function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final boolean[] src, final Range range, final Z initial,
                                 final Fn1<Z, Bool.To<Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state).onApply(src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link byte[]}
     * array within the specified index-range using the given binary combiner
     * function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final byte[] src, final Range range, final Z initial,
                                 final Fn1<Z, I8.To<Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state).onApply(src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link short[]}
     * array within the specified index-range using the given binary combiner
     * function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final short[] src, final Range range, final Z initial,
                                 final Fn1<Z, I16.To<Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state).onApply(src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link char[]}
     * array within the specified index-range using the given binary combiner
     * function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final char[] src, final Range range, final Z initial,
                                 final Fn1<Z, C16.To<Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state).onApply(src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link int[]}
     * array within the specified index-range using the given binary combiner
     * function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final int[] src, final Range range, final Z initial,
                                 final Fn1<Z, I32.To<Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state).onApply(src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link long[]}
     * array within the specified index-range using the given binary combiner
     * function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final long[] src, final Range range, final Z initial,
                                 final Fn1<Z, I64.To<Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state).onApply(src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link float[]}
     * array within the specified index-range using the given binary combiner
     * function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final float[] src, final Range range, final Z initial,
                                 final Fn1<Z, F32.To<Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state).onApply(src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link double[]}
     * array within the specified index-range using the given binary combiner
     * function from left to right.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from left to right,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldLeft(final double[] src, final Range range, final Z initial,
                                 final Fn1<Z, F64.To<Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = lo; i < hi; ++i) {
                state = combiner.onApply(state).onApply(src[i]);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of folding the values of the given {@link A[]} array
     * using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left.
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <A>      component-type of source array.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <A, Z> Z foldRight(final A[] src, final Z initial, final Fn2<? super A, Z, Z> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link boolean[]}
     * array using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final boolean[] src, final Z initial, final Bool.To<Fn1<Z, Z>> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link byte[]}
     * array using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final byte[] src, final Z initial, final I8.To<Fn1<Z, Z>> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link short[]}
     * array using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final short[] src, final Z initial, final I16.To<Fn1<Z, Z>> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link char[]}
     * array using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final char[] src, final Z initial, final C16.To<Fn1<Z, Z>> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link int[]}
     * array using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final int[] src, final Z initial, final I32.To<Fn1<Z, Z>> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link long[]}
     * array using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final long[] src, final Z initial, final I64.To<Fn1<Z, Z>> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link float[]}
     * array using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final float[] src, final Z initial, final F32.To<Fn1<Z, Z>> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    /**
     * Returns the result of folding the values of the given {@link double[]}
     * array using the given binary combiner function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     *
     * @param src      source array to be right folded.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final double[] src, final Z initial, final F64.To<Fn1<Z, Z>> combiner) {
        return foldRight(src, range(src), initial, combiner);
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of folding the values of the given {@link A[]} array
     * within the specified index-range using the given binary combiner function
     * from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left.
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <A>      component-type of source array.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <A, Z> Z foldRight(final A[] src, final Range range, final Z initial,
                                     final Fn2<? super A, Z, Z> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i], state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link boolean[]}
     * array within the specified index-range using the given binary combiner
     * function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final boolean[] src, final Range range, final Z initial,
                                  final Bool.To<Fn1<Z, Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link byte[]}
     * array within the specified index-range using the given binary combiner
     * function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final byte[] src, final Range range, final Z initial,
                                  final I8.To<Fn1<Z, Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link short[]}
     * array within the specified index-range using the given binary combiner
     * function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final short[] src, final Range range, final Z initial,
                                  final I16.To<Fn1<Z, Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link char[]}
     * array within the specified index-range using the given binary combiner
     * function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final char[] src, final Range range, final Z initial,
                                  final C16.To<Fn1<Z, Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link int[]}
     * array within the specified index-range using the given binary combiner
     * function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final int[] src, final Range range, final Z initial,
                                  final I32.To<Fn1<Z, Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link long[]}
     * array within the specified index-range using the given binary combiner
     * function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final long[] src, final Range range, final Z initial,
                                  final I64.To<Fn1<Z, Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link float[]}
     * array within the specified index-range using the given binary combiner
     * function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final float[] src, final Range range, final Z initial,
                                  final F32.To<Fn1<Z, Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    /**
     * Returns the result of folding the values of the given {@link double[]}
     * array within the specified index-range using the given binary combiner
     * function from right to left.
     * <p>
     * The folding procedure applies the given combiner function to the current
     * accumulator value and each value of the input array from right to left,
     * beginning with the {@code initial} accumulator. In case the given source
     * array is empty then the {@code initial} accumulator is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src      source array to be left folded.
     * @param range    lower/upper bounds of the index-range.
     * @param initial  initial value to start fold operation.
     * @param combiner binary function to combine values.
     * @param <Z>      type of accumulator value.
     * @return accumulated value of type {@code Z}.
     */
    public static <Z> Z foldRight(final double[] src, final Range range, final Z initial,
                                  final F64.To<Fn1<Z, Z>> combiner) {
        precondition(src, range, combiner);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            Z state = initial;
            for (int i = hi - 1; i >= lo; --i) {
                state = combiner.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A> A reduce(final A[] src, final A identity,
                               final Fn2<A, A, A> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    public static boolean reduce(final boolean[] src, final boolean identity,
                                 final Bool.To<Bool.ToBool> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    public static byte reduce(final byte[] src, final byte identity,
                              final I8.To<I8.ToI8> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    public static short reduce(final short[] src, final short identity,
                               final I16.To<I16.ToI16> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    public static char reduce(final char[] src, final char identity,
                              final C16.To<C16.ToC16> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    public static int reduce(final int[] src, final int identity,
                             final I32.To<I32.ToI32> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    public static long reduce(final long[] src, final long identity,
                              final I64.To<I64.ToI64> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    public static float reduce(final float[] src, final float identity,
                               final F32.To<F32.ToF32> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    public static double reduce(final double[] src, final double identity,
                                final F64.To<F64.ToF64> accumulator) {
        return reduce(src, range(src), identity, accumulator);
    }

    // ----------------------------------------------------------

    public static <A> A reduce(final A[] src, final Range range, final A identity,
                               final Fn2<A, A, A> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i], state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static boolean reduce(final boolean[] src, final Range range, final boolean identity,
                                 final Bool.To<Bool.ToBool> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static byte reduce(final byte[] src, final Range range, final byte identity,
                              final I8.To<I8.ToI8> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static short reduce(final short[] src, final Range range, final short identity,
                               final I16.To<I16.ToI16> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static char reduce(final char[] src, final Range range, final char identity,
                              final C16.To<C16.ToC16> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int reduce(final int[] src, final Range range, final int identity,
                             final I32.To<I32.ToI32> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static long reduce(final long[] src, final Range range, final long identity,
                              final I64.To<I64.ToI64> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static float reduce(final float[] src, final Range range, final float identity,
                               final F32.To<F32.ToF32> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static double reduce(final double[] src, final Range range, final double identity,
                                final F64.To<F64.ToF64> accumulator) {
        precondition(src, range, accumulator);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            var state = identity;
            for (int i = lo; i < hi; ++i) {
                state = accumulator.onApply(src[i]).onApply(state);
            }
            return state;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A> long count(final A[] src, final Fn1.Predicate<? super A> predicate) {
        return count(src, range(src), predicate);
    }

    public static int count(final boolean[] src, final Bool.Predicate predicate) {
        return count(src, range(src), predicate);
    }

    public static int count(final byte[] src, final I8.Predicate predicate) {
        return count(src, range(src), predicate);
    }

    public static int count(final short[] src, final I16.Predicate predicate) {
        return count(src, range(src), predicate);
    }

    public static int count(final char[] src, final C16.Predicate predicate) {
        return count(src, range(src), predicate);
    }

    public static int count(final int[] src, final I32.Predicate predicate) {
        return count(src, range(src), predicate);
    }

    public static int count(final long[] src, final I64.Predicate predicate) {
        return count(src, range(src), predicate);
    }

    public static int count(final float[] src, final F32.Predicate predicate) {
        return count(src, range(src), predicate);
    }

    public static int count(final double[] src, final F64.Predicate predicate) {
        return count(src, range(src), predicate);
    }

    // ----------------------------------------------------------

    public static <A> int count(final A[] src, final Range range,
                                final Fn1.Predicate<? super A> predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int count(final boolean[] src, final Range range,
                            final Bool.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int count(final byte[] src, final Range range,
                            final I8.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int count(final short[] src, final Range range,
                            final I16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int count(final char[] src, final Range range,
                            final C16.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int count(final int[] src, final Range range,
                            final I32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int count(final long[] src, final Range range,
                            final I64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int count(final float[] src, final Range range,
                            final F32.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    public static int count(final double[] src, final Range range,
                            final F64.Predicate predicate) {
        precondition(src, range, predicate);
        try {
            final int lo = Range.lo(range);
            final int hi = Range.hi(range);
            int count = 0;
            for (int i = lo; i < hi; ++i) {
                if (predicate.onEval(src[i])) {
                    ++count;
                }
            }
            return count;
        } catch (Throwable ex) {
            return Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    public static <A> Option<A> min(final A[] src, final java.util.Comparator<? super A> comparator) {
        return min(src, range(src), comparator);
    }

    public static <A extends Comparable<A>> Option<A> min(final A[] src) {
        return min(src, range(src));
    }

    public static I8.Option min(final byte[] src) {
        return min(src, range(src));
    }

    public static I16.Option min(final short[] src) {
        return min(src, range(src));
    }

    public static C16.Option min(final char[] src) {
        return min(src, range(src));
    }

    public static I32.Option min(final int[] src) {
        return min(src, range(src));
    }

    public static I64.Option min(final long[] src) {
        return min(src, range(src));
    }

    public static F32.Option min(final float[] src) {
        return min(src, range(src));
    }

    public static F64.Option min(final double[] src) {
        return min(src, range(src));
    }

    // ----------------------------------------------------------

    public static <A> Option<A> min(final A[] src, final Range range,
                                    final java.util.Comparator<? super A> comparator) {
        precondition(src, range, comparator);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return Option.none();
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (comparator.compare(min, src[i]) > 0) {
                min = src[i];
            }
        }
        return Option.some(min);
    }

    public static <A extends Comparable<A>> Option<A> min(final A[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return Option.none();
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (min.compareTo(src[i]) > 0) {
                min = src[i];
            }
        }
        return Option.some(min);
    }

    public static I8.Option min(final byte[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return I8.none;
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (src[i] < min) {
                min = src[i];
            }
        }
        return I8.some(min);
    }

    public static I16.Option min(final short[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return I16.none;
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (src[i] < min) {
                min = src[i];
            }
        }
        return I16.some(min);
    }

    public static C16.Option min(final char[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return C16.none;
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (src[i] < min) {
                min = src[i];
            }
        }
        return C16.some(min);
    }

    public static I32.Option min(final int[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return I32.none;
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (src[i] < min) {
                min = src[i];
            }
        }
        return I32.some(min);
    }

    public static I64.Option min(final long[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return I64.none;
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (src[i] < min) {
                min = src[i];
            }
        }
        return I64.some(min);
    }

    public static F32.Option min(final float[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return F32.none;
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (src[i] < min) {
                min = src[i];
            }
        }
        return F32.some(min);
    }

    public static F64.Option min(final double[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return F64.none;
        var min = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (src[i] < min) {
                min = src[i];
            }
        }
        return F64.some(min);
    }

    // ----------------------------------------------------------

    /**
     * Returns the optional maximum value of type {@code A} contained in the 
     * given source array, according to the given comparator. If the given 
     * array or index range is empty, the empty option is returned.
     *
     * @param src   source array to be queried.
     * @param <A>   type of {@link Comparable} array value.
     * @return optional maximum of type {@code A}.
     */
    public static <A> Option<A> max(final A[] src, final java.util.Comparator<? super A> comparator) {
        return max(src, range(src), comparator);
    }

    /**
     * Returns the optional maximum value of type {@code A} contained in the
     * given source array. If the given array or index range is empty, the
     * empty option is returned.
     *
     * @param src   source array to be queried.
     * @param <A>   type of {@link Comparable} array value.
     * @return optional maximum of type {@code A}.
     */
    public static <A extends Comparable<A>> Option<A> max(final A[] src) {
        return max(src, range(src));
    }

    /**
     * Returns the optional maximum {@code byte} value contained in the given 
     * source array. If the given array or index range is empty, the empty 
     * option is returned.
     *
     * @param src source array to be queried.
     * @return optional {@code byte} maximum.
     */
    public static I8.Option max(final byte[] src) {
        return max(src, range(src));
    }

    /**
     * Returns the optional maximum {@code short} value contained in the given
     * source array. If the given array or index range is empty, the empty
     * option is returned.
     *
     * @param src source array to be queried.
     * @return optional {@code short} maximum.
     */
    public static I16.Option max(final short[] src) {
        return max(src, range(src));
    }

    /**
     * Returns the optional maximum {@code char} value contained in the given
     * source array. If the given array or index range is empty, the empty
     * option is returned.
     *
     * @param src source array to be queried.
     * @return optional {@code char} maximum.
     */
    public static C16.Option max(final char[] src) {
        return max(src, range(src));
    }

    /**
     * Returns the optional maximum {@code int} value contained in the given
     * source array. If the given array or index range is empty, the empty
     * option is returned.
     *
     * @param src source array to be queried.
     * @return optional {@code int} maximum.
     */
    public static I32.Option max(final int[] src) {
        return max(src, range(src));
    }

    /**
     * Returns the optional maximum {@code long} value contained in the given
     * source array. If the given array or index range is empty, the empty
     * option is returned.
     *
     * @param src source array to be queried.
     * @return optional {@code long} maximum.
     */
    public static I64.Option max(final long[] src) {
        return max(src, range(src));
    }

    /**
     * Returns the optional maximum {@code float} value contained in the given
     * source array. If the given array or index range is empty, the empty
     * option is returned.
     *
     * @param src source array to be queried.
     * @return optional {@code float} maximum.
     */
    public static F32.Option max(final float[] src) {
        return max(src, range(src));
    }

    /**
     * Returns the optional maximum {@code double} value contained in the given
     * source array. If the given array or index range is empty, the empty
     * option is returned.
     *
     * @param src source array to be queried.
     * @return optional {@code double} maximum.
     */
    public static F64.Option max(final double[] src) {
        return max(src, range(src));
    }

    // ----------------------------------------------------------

    /**
     * Returns the optional maximum value of type {@code A} contained in 
     * the given source array, according to the given comparator, within 
     * the specified index range. If the given array or index range is 
     * empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param <A>   type of {@link Comparable} array value.
     * @return optional maximum of type {@code A}.
     */
    public static <A> Option<A> max(final A[] src, final Range range, final java.util.Comparator<? super A> comparator) {
        precondition(src, range, comparator);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return Option.none();
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (comparator.compare(max, src[i]) < 0) {
                max = src[i];
            }
        }
        return Option.some(max);
    }

    /**
     * Returns the optional maximum value of type {@code A} contained in the
     * given source array within the specified index range. If the given
     * array or index range is empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @param <A>   type of {@link Comparable} array value.
     * @return optional maximum of type {@code A}.
     */
    public static <A extends Comparable<A>> Option<A> max(final A[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return Option.none();
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (max.compareTo(src[i]) < 0) {
                max = src[i];
            }
        }
        return Option.some(max);
    }

    /**
     * Returns the optional maximum {@code byte} value contained in the
     * given source array within the specified index range. If the given
     * array or index range is empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @return optional {@code byte} maximum.
     */
    public static I8.Option max(final byte[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return I8.none;
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (max < src[i]) {
                max = src[i];
            }
        }
        return I8.some(max);
    }

    /**
     * Returns the optional maximum {@code short} value contained in the
     * given source array within the specified index range. If the given
     * array or index range is empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @return optional {@code short} maximum.
     */
    public static I16.Option max(final short[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return I16.none;
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (max < src[i]) {
                max = src[i];
            }
        }
        return I16.some(max);
    }

    /**
     * Returns the optional maximum {@code char} value contained in the
     * given source array within the specified index range. If the given
     * array or index range is empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @return optional {@code char} maximum.
     */
    public static C16.Option max(final char[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return C16.none;
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (max < src[i]) {
                max = src[i];
            }
        }
        return C16.some(max);
    }

    /**
     * Returns the optional maximum {@code int} value contained in the
     * given source array within the specified index range. If the given
     * array or index range is empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @return optional {@code int} maximum.
     */
    public static I32.Option max(final int[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return I32.none;
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (max < src[i]) {
                max = src[i];
            }
        }
        return I32.some(max);
    }

    /**
     * Returns the optional maximum {@code long} value contained in the
     * given source array within the specified index range. If the given
     * array or index range is empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @return optional {@code long} maximum.
     */
    public static I64.Option max(final long[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return I64.none;
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (max < src[i]) {
                max = src[i];
            }
        }
        return I64.some(max);
    }

    /**
     * Returns the optional maximum {@code float} value contained in the
     * given source array within the specified index range. If the given
     * array or index range is empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @return optional {@code float} maximum.
     */
    public static F32.Option max(final float[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return F32.none;
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (max < src[i]) {
                max = src[i];
            }
        }
        return F32.some(max);
    }

    /**
     * Returns the optional maximum {@code double} value contained in the
     * given source array within the specified index range. If the given
     * array or index range is empty, the empty option is returned.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src   source array to be queried.
     * @param range lower/upper bounds of the index-range.
     * @return optional {@code double} maximum.
     */
    public static F64.Option max(final double[] src, final Range range) {
        precondition(src, range);
        final int lo = Range.lo(range);
        final int hi = Range.hi(range);
        if (src.length == 0 || lo >= hi)
            return F64.none;
        var max = src[lo];
        for (int i = lo + 1; i < hi; ++i) {
            if (max < src[i]) {
                max = src[i];
            }
        }
        return F64.some(max);
    }

    // ----------------------------------------------------------

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final A[] src, final Fn1.Consumer<? super A> action) {
        forEach(src, range(src), action);
    }

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final boolean[] src, final Bool.Consumer action) {
        forEach(src, range(src), action);
    }

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final byte[] src, final I8.Consumer action) {
        forEach(src, range(src), action);
    }

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final short[] src, final I16.Consumer action) {
        forEach(src, range(src), action);
    }

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final char[] src, final C16.Consumer action) {
        forEach(src, range(src), action);
    }

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final int[] src, final I32.Consumer action) {
        forEach(src, range(src), action);
    }

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final long[] src, final I64.Consumer action) {
        forEach(src, range(src), action);
    }

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final float[] src, final F32.Consumer action) {
        forEach(src, range(src), action);
    }

    /**
     * Performs the given action on each value in the given source array.
     *
     * @param src    source array to be iterated.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final double[] src, final F64.Consumer action) {
        forEach(src, range(src), action);
    }

    // ----------------------------------------------------------

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final A[] src, final Range range, final Fn1.Consumer<? super A> action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final boolean[] src, final Range range, final Bool.Consumer action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final byte[] src, final Range range, final I8.Consumer action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final short[] src, final Range range, final I16.Consumer action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final char[] src, final Range range, final C16.Consumer action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final int[] src, final Range range, final I32.Consumer action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final long[] src, final Range range, final I64.Consumer action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final float[] src, final Range range, final F32.Consumer action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    /**
     * Performs the given action on each value in the given source array
     * within specified index range.
     * <p>
     * A valid index range is defined by a lower bound {@code lo} (inclusive)
     * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
     * be within the array index bounds, {@code 0 <= lo < hi <= src.length}.
     * The index range is considered empty if {@code lo >= hi}.
     *
     * @param src    source array to be iterated.
     * @param range  lower/upper bounds of the index-range.
     * @param action to be performed on array values.
     * @param <A>    type of source array.
     */
    public static <A> void forEach(final double[] src, final Range range, final F64.Consumer action) {
        precondition(src, range, action);
        try {
            final var lo = Range.lo(range);
            final var hi = Range.hi(range);
            for (int i = lo; i < hi; ++i) {
                action.onAccept(src[i]);
            }
        } catch (Throwable ex) {
            Throw.sneaky(ex);
        }
    }

    // ----------------------------------------------------------

    /**
     * Returns a string representation of the contents of the given array.
     *
     * @param src the array to be represented as string.
     * @return string representation.
     */
    public static String toString(final Object src) {
        if (src != null && src.getClass().isArray()) {
            if (src.getClass() == boolean[].class)
                return toString((boolean[]) src);
            else if (src.getClass() == byte[].class)
                return toString((byte[]) src);
            else if (src.getClass() == char[].class)
                return toString((char[]) src);
            else if (src.getClass() == double[].class)
                return toString((double[]) src);
            else if (src.getClass() == float[].class)
                return toString((float[]) src);
            else if (src.getClass() == int[].class)
                return toString((int[]) src);
            else if (src.getClass() == long[].class)
                return toString((long[]) src);
            else if (src.getClass() == short[].class)
                return toString((short[]) src);
            else {
                return Array.foldLeft((Object[]) src, new java.util.StringJoiner(", ", "[", "]"),
                        (joiner, value) -> (value != null && value.getClass().isArray())
                                ? joiner.add(Array.toString(value))
                                : joiner.add(String.valueOf(value))).toString();
            }
        } else {
            return String.valueOf(src);
        }
    }

    private static String toString(final boolean... ary) {
        if (ary == null) return "null";
        else if (0 == ary.length)
            return "[]";
        else {
            final var joiner = new java.util.StringJoiner(", ", "[", "]");
            for (int i = 0; i < ary.length; ++i) {
                joiner.add(String.valueOf(ary[i]));
            }
            return joiner.toString();
        }
    }

    private static String toString(final byte... ary) {
        if (ary == null) return "null";
        else if (0 == ary.length)
            return "[]";
        else {
            final var joiner = new java.util.StringJoiner(", ", "[", "]");
            for (int i = 0; i < ary.length; ++i) {
                joiner.add(String.valueOf(ary[i]));
            }
            return joiner.toString();
        }
    }

    private static String toString(final short... ary) {
        if (ary == null) return "null";
        else if (0 == ary.length)
            return "[]";
        else {
            final var joiner = new java.util.StringJoiner(", ", "[", "]");
            for (int i = 0; i < ary.length; ++i) {
                joiner.add(String.valueOf(ary[i]));
            }
            return joiner.toString();
        }
    }

    private static String toString(final char... ary) {
        if (ary == null) return "null";
        else if (0 == ary.length)
            return "[]";
        else {
            final var joiner = new java.util.StringJoiner(", ", "[", "]");
            for (int i = 0; i < ary.length; ++i) {
                joiner.add(String.valueOf(ary[i]));
            }
            return joiner.toString();
        }
    }

    private static String toString(final int... ary) {
        if (ary == null) return "null";
        else if (0 == ary.length)
            return "[]";
        else {
            final var joiner = new java.util.StringJoiner(", ", "[", "]");
            for (int i = 0; i < ary.length; ++i) {
                joiner.add(String.valueOf(ary[i]));
            }
            return joiner.toString();
        }
    }

    private static String toString(final long... ary) {
        if (ary == null) return "null";
        else if (0 == ary.length)
            return "[]";
        else {
            final var joiner = new java.util.StringJoiner(", ", "[", "]");
            for (int i = 0; i < ary.length; ++i) {
                joiner.add(String.valueOf(ary[i]));
            }
            return joiner.toString();
        }
    }

    private static String toString(final float... ary) {
        if (ary == null) return "null";
        else if (0 == ary.length)
            return "[]";
        else {
            final var joiner = new java.util.StringJoiner(", ", "[", "]");
            for (int i = 0; i < ary.length; ++i) {
                joiner.add(String.valueOf(ary[i]));
            }
            return joiner.toString();
        }
    }

    private static String toString(final double... ary) {
        if (ary == null) return "null";
        else if (0 == ary.length)
            return "[]";
        else {
            final var joiner = new java.util.StringJoiner(", ", "[", "]");
            for (int i = 0; i < ary.length; ++i) {
                joiner.add(String.valueOf(ary[i]));
            }
            return joiner.toString();
        }
    }

    // ----------------------------------------------------------

    public static <A> A[] from(final Class<? extends A[]> type,
                               final Iterable<? extends A> iterable) {
        final var it = iterable.iterator();
        if (!it.hasNext()) return empty(type);
        var out = Array.alloc(type, INITIAL_CAPACITY);
        out[0] = it.next();
        int index = 1;
        while (it.hasNext()) {
            out[index++] = it.next();
            if (index == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, index);
    }

    public static boolean[] fromBool(final Iterable<Boolean> itr) {
        final var it = itr.iterator();
        if (!it.hasNext()) return emptyBool;
        var out = new boolean[INITIAL_CAPACITY];
        out[0] = it.next();
        int i = 1;
        while (it.hasNext()) {
            out[i++] = it.next();
            if (i == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, i);
    }

    public static byte[] fromI8(final Iterable<Byte> itr) {
        final var it = itr.iterator();
        if (!it.hasNext()) return emptyI8;
        var out = new byte[INITIAL_CAPACITY];
        out[0] = it.next();
        int i = 1;
        while (it.hasNext()) {
            out[i++] = it.next();
            if (i == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, i);
    }

    public static short[] fromI16(final Iterable<Short> itr) {
        final var it = itr.iterator();
        if (!it.hasNext()) return emptyI16;
        var out = new short[INITIAL_CAPACITY];
        out[0] = it.next();
        int i = 1;
        while (it.hasNext()) {
            out[i++] = it.next();
            if (i == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, i);
    }

    public static char[] fromC16(final Iterable<Character> itr) {
        final var it = itr.iterator();
        if (!it.hasNext()) return emptyC16;
        var out = new char[INITIAL_CAPACITY];
        out[0] = it.next();
        int i = 1;
        while (it.hasNext()) {
            out[i++] = it.next();
            if (i == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, i);
    }

    public static int[] fromI32(final Iterable<Integer> itr) {
        final var it = itr.iterator();
        if (!it.hasNext()) return emptyI32;
        var out = new int[INITIAL_CAPACITY];
        out[0] = it.next();
        int i = 1;
        while (it.hasNext()) {
            out[i++] = it.next();
            if (i == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, i);
    }

    public static long[] fromI64(final Iterable<Long> itr) {
        final var it = itr.iterator();
        if (!it.hasNext()) return emptyI64;
        var out = new long[INITIAL_CAPACITY];
        out[0] = it.next();
        int i = 1;
        while (it.hasNext()) {
            out[i++] = it.next();
            if (i == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, i);
    }

    public static float[] fromF32(final Iterable<Float> itr) {
        final var it = itr.iterator();
        if (!it.hasNext()) return emptyF32;
        var out = new float[INITIAL_CAPACITY];
        out[0] = it.next();
        int i = 1;
        while (it.hasNext()) {
            out[i++] = it.next();
            if (i == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, i);
    }

    public static double[] fromF64(final Iterable<Double> itr) {
        final var it = itr.iterator();
        if (!it.hasNext()) return emptyF64;
        var out = new double[INITIAL_CAPACITY];
        out[0] = it.next();
        int i = 1;
        while (it.hasNext()) {
            out[i++] = it.next();
            if (i == out.length) {
                out = Capacity.grow(out);
            }
        }
        return copy(out, i);
    }

    // ----------------------------------------------------------

    /**
     * Creates a new array with the given component type and length.
     *
     * @param type   specified by this class object.
     * @param length of the 1st array dimension.
     * @param <A>    type of array component.
     * @return new array of type {@link A[]}.
     */
    @SuppressWarnings({ "unchecked", "RedundantCast" })
    public static <A> A[] alloc(final Class<? extends A[]> type, final long length) {
        return ((Object) type == (Object) Object[].class)
                ? (A[]) new Object[Narrow.I32(length)]
                : (A[]) java.lang.reflect.Array.newInstance(type.getComponentType(), Narrow.I32(length));
    }

    /**
     * Creates a new 2-dimensional array with the given component type and dimensions.
     *
     * @param type    specified by this class object.
     * @param length1 length of the 1st array dimension.
     * @param length2 length of the 2nd array dimension.
     * @param <A>     type of array component.
     * @return new array of type {@link A[][]}.
     */
    @SuppressWarnings("unchecked")
    public static <A> A[][] alloc(final Class<?> type, final long length1, final long length2) {
        return (A[][]) java.lang.reflect.Array.newInstance(type, Narrow.I32(length1), Narrow.I32(length2));
    }

    /**
     * Creates a new 3-dimensional array with the given component type and dimensions.
     *
     * @param type    specified by this class object.
     * @param length1 length of the 1st array dimension.
     * @param length2 length of the 2nd array dimension.
     * @param length3 length of the 3rd array dimension.
     * @param <A>     type of array component.
     * @return new {@link A[][][]} array.
     */
    @SuppressWarnings("unchecked")
    public static <A> A[][][] alloc(final Class<?> type, final long length1, final long length2, final long length3) {
        return (A[][][]) java.lang.reflect.Array.newInstance(type, Narrow.I32(length1), Narrow.I32(length2), Narrow.I32(length3));
    }

    public static <A> A[] alloc(final Object[] prototype, final long length) {
        return alloc(Force.<Class<? extends A[]>> cast(prototype.getClass()), length);
    }

    public static <A> A[] alloc(final Object[] src) {
        return alloc(src, src.length);
    }

    public static <A> A[] alloc(final long length) {
        return Force.cast(new Object[Narrow.I32(length)]);
    }

    public static boolean[] allocBool(final long length) {
        return length <= 0L ? Array.emptyBool : new boolean[Narrow.I32(length)];
    }

    public static byte[] allocI8(final long length) {
        return length <= 0L ? Array.emptyI8 : new byte[Narrow.I32(length)];
    }

    public static short[] allocI16(final long length) {
        return length <= 0L ? Array.emptyI16 : new short[Narrow.I32(length)];
    }

    public static char[] allocC16(final long length) {
        return length <= 0L ? Array.emptyC16 : new char[Narrow.I32(length)];
    }

    public static int[] allocI32(final long length) {
        return length <= 0L ? Array.emptyI32 : new int[Narrow.I32(length)];
    }

    public static long[] allocI64(final long length) {
        return length <= 0L ? Array.emptyI64 : new long[Narrow.I32(length)];
    }

    public static float[] allocF32(final long length) {
        return length <= 0L ? Array.emptyF32 : new float[Narrow.I32(length)];
    }

    public static double[] allocF64(final long length) {
        return length <= 0L ? Array.emptyF64 : new double[Narrow.I32(length)];
    }

    // ----------------------------------------------------------
    //  ARRAY.CAPACITY
    // ----------------------------------------------------------

    /**
     * Array capacity management.
     */
    public interface Capacity {

        // ----------------------------------------------------------
        //  ARRAY.CAPACITY.ADAPTER
        // ----------------------------------------------------------

        /**
         * Defines the capacity adapter which is used to compute a
         * new capacity from a current and a required capacity.
         */
        interface Adapter extends Capacity {

            /**
             * Returns an adapted capacity from a current and a required capacity.
             *
             * @param current  capacity value.
             * @param required capacity value.
             * @return new capacity value.
             */
            long newCapacity(long current, long required);

            /**
             * Returns an adapted capacity from a current and a required capacity.
             *
             * @param current  capacity value.
             * @param required capacity value.
             * @return new capacity value.
             */
            int newArrayCapacity(int current, int required);
        }

        // ----------------------------------------------------------
        //  ARRAY.CAPACITY.EXTENDER
        // ----------------------------------------------------------

        /**
         * Capacity expander defines strategies for capacity expansion.
         */
        enum Expander implements Adapter {

            /**
             * Used in Java Collection Framework.
             */
            JAVA {
                @Override
                public final long newCapacity(final long current, final long required) {
                    var capacity = current + (current >> 1) + 1L;
                    if (capacity < required) {
                        capacity = Long.highestOneBit(required - 1L) << 1;
                    }
                    return capacity;
                }

                @Override
                public final int newArrayCapacity(final int current, final int required) {
                    var capacity = current + (current >> 1) + 1;
                    if (capacity < required) {
                        capacity = Integer.highestOneBit(required - 1) << 1;
                    }
                    if (capacity < 0) {
                        capacity = Array.MAX_CAPACITY;
                    }
                    return capacity;
                }
            },

            /**
             * Over-allocates proportional to current capacity, making room for
             * additional growth. Over-allocation is mild, but is enough to give
             * linear-time amortized behavior over a long sequence of appends.
             * (0, 4, 8, 16, 25, 35, 46, 58, 72, 88, 164, ...)
             */
            PYTHON {
                @Override
                public final long newCapacity(final long current, final long required) {
                    var capacity = (required >> 3) + (required < 9L ? 3L : 6L);
                    if (capacity < required) {
                        capacity = Long.highestOneBit(required - 1L) << 1;
                    }
                    return capacity;
                }

                @Override
                public final int newArrayCapacity(final int current, final int required) {
                    var capacity = (required >> 3) + (required < 9 ? 3 : 6);
                    if (capacity < required) {
                        capacity = Integer.highestOneBit(required - 1) << 1;
                    }
                    if (capacity < 0) {
                        capacity = Array.MAX_CAPACITY;
                    }
                    return capacity;
                }
            }
        }

        // ----------------------------------------------------------
        //  ARRAY.CAPACITY.ADJUSTER
        // ----------------------------------------------------------

        /**
         * Capacity expander defines strategies for capacity expansion and contraction.
         */
        enum Adjuster implements Adapter {
            UNDEF {
                @Override
                public long newCapacity(long current, long required) {
                    throw TODO.notImplemented();
                }

                @Override
                public int newArrayCapacity(int current, int required) {
                    throw TODO.notImplemented();
                }
            }
        }

        // ----------------------------------------------------------

        /// ARRAY.ENSURE.

        /**
         * Ensures the {@code required} capacity of the given source array.
         * If the length of the source array is greater than or equal to the
         * required capacity, this operation has no effect. If the required
         * capacity is greater than the length of the given source array, a
         * new array with the given runtime component type is allocated. The
         * length of the allocated array is determined by required capacity
         * and the given {@link Expander} strategy. The contents of
         * the specified source array are transferred to the new array. If
         * the given source array is null or empty, a new array is allocated.
         *
         * @param src      source array whose capacity is to be ensured.
         * @param type     class object specifying the target component-type.
         * @param required capacity to be ensured by the source array.
         * @param exp      expander do determine length of allocations.
         * @param <A>      component-type of the source array.
         * @return original source or a resized array.
         */
        static <A> A[] ensure(final A[] src, final Class<? extends A[]> type, final int required, final Expander exp) {
            if (null == src || src.length == 0) return Array.alloc(type, exp.newArrayCapacity(0, required));
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        /**
         * @see #ensure(Object[], Class, int, Expander)
         * In case the given source array is null, then {@link Object}
         * is taken as runtime component type for fresh allocations.
         */
        static <A> A[] ensure(final A[] src, final int required, final Expander exp) {
            return ensure(src, Force.cast(src.getClass()), required, exp);
        }

        /**
         * Ensures the {@code required} capacity of the given source array.
         * If the length of the source array is greater than or equal to the
         * required capacity, this operation has no effect. If the required
         * capacity is greater than the length of the given source array, a
         * new array of the same type is allocated.
         *
         * The length of the allocated array is determined by required capacity
         * and the given {@link Expander} strategy. The contents of the
         * specified source array are transferred to the new array. If the given
         * source array is null or empty, a new array is allocated.
         *
         * @param src      source array whose capacity is to be ensured.
         * @param required capacity to be ensured for the source array.
         * @param exp      expander do determine length of allocations.
         * @return original source or a resized array.
         */
        static boolean[] ensure(final boolean[] src, final int required, final Expander exp) {
            if (src == null || src.length == 0) return new boolean[required];
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        /**
         * @see #ensure(boolean[], int, Expander)
         */
        static byte[] ensure(final byte[] src, final int required, final Expander exp) {
            if (null == src || src.length == 0) return new byte[required];
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        /**
         * @see #ensure(boolean[], int, Expander)
         */
        static short[] ensure(final short[] src, final int required, final Expander exp) {
            if (null == src || src.length == 0) return new short[required];
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        /**
         * @see #ensure(boolean[], int, Expander)
         */
        static char[] ensure(final char[] src, final int required, final Expander exp) {
            if (null == src || src.length == 0) return new char[required];
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        /**
         * @see #ensure(boolean[], int, Expander)
         */
        static int[] ensure(final int[] src, final int required, final Expander exp) {
            if (null == src || src.length == 0) return new int[required];
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        /**
         * @see #ensure(boolean[], int, Expander)
         */
        static long[] ensure(final long[] src, final int required, final Expander exp) {
            if (null == src || src.length == 0) return new long[required];
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        /**
         * @see #ensure(boolean[], int, Expander)
         */
        static float[] ensure(final float[] src, final int required, final Expander exp) {
            if (null == src || src.length == 0) return new float[required];
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        /**
         * @see #ensure(boolean[], int, Expander)
         */
        static double[] ensure(final double[] src, final int required, final Expander exp) {
            if (null == src || src.length == 0) return new double[required];
            return (required >= src.length) ? java.util.Arrays.copyOf(src, exp.newArrayCapacity(src.length, required)) : src;
        }

        // ----------------------------------------------------------

        /**
         * @see #ensure(Object[], Class, int, Expander)
         */
        static <A> A[] ensure(final A[] src, final Class<? extends A[]> type, final int required) {
            return ensure(src, type, required, Expander.PYTHON);
        }

        /**
         * @see #ensure(Object[], Class, int, Expander)
         */
        static <A> A[] ensure(final A[] src, final int required) {
            return ensure(src, Force.cast(src.getClass()), required);
        }

        /**
         * @see #ensure(boolean[], int, Expander)
         */
        static boolean[] ensure(final boolean[] src, final int required) {
            return ensure(src, required, Expander.PYTHON);
        }

        /**
         * @see #ensure(byte[], int, Expander)
         */
        static byte[] ensure(final byte[] src, final int required) {
            return ensure(src, required, Expander.PYTHON);
        }

        /**
         * @see #ensure(short[], int, Expander)
         */
        static short[] ensure(final short[] src, final int required) {
            return ensure(src, required, Expander.PYTHON);
        }

        /**
         * @see #ensure(char[], int, Expander)
         */
        static char[] ensure(final char[] src, final int required) {
            return ensure(src, required, Expander.PYTHON);
        }

        /**
         * @see #ensure(int[], int, Expander)
         */
        static int[] ensure(final int[] src, final int required) {
            return ensure(src, required, Expander.PYTHON);
        }

        /**
         * @see #ensure(long[], int, Expander)
         */
        static long[] ensure(final long[] src, final int required) {
            return ensure(src, required, Expander.PYTHON);
        }

        /**
         * @see #ensure(float[], int, Expander)
         */
        static float[] ensure(final float[] src, final int required) {
            return ensure(src, required, Expander.PYTHON);
        }

        /**
         * @see #ensure(double[], int, Expander)
         */
        static double[] ensure(final double[] src, final int required) {
            return ensure(src, required, Expander.PYTHON);
        }

        // ----------------------------------------------------------

        /**
         * @see #ensure(Object[], int)
         */
        static <A> A[] grow(final A[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        /**
         * @see #ensure(boolean[], int)
         */
        static boolean[] grow(final boolean[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        /**
         * @see #ensure(byte[], int)
         */
        static byte[] grow(final byte[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        /**
         * @see #ensure(short[], int)
         */
        static short[] grow(final short[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        /**
         * @see #ensure(char[], int)
         */
        static char[] grow(final char[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        /**
         * @see #ensure(int[], int)
         */
        static int[] grow(final int[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        /**
         * @see #ensure(long[], int)
         */
        static long[] grow(final long[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        /**
         * @see #ensure(float[], int)
         */
        static float[] grow(final float[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        /**
         * @see #ensure(double[], int)
         */
        static double[] grow(final double[] src) {
            return Capacity.ensure(src, src.length + 1, Expander.PYTHON);
        }

        // ----------------------------------------------------------

        /**
         * Returns the component-type of the given source array. If the array is
         * null then {@link Object} is returned as component type.
         *
         * @param src array whose component-type is to be returned.
         * @return component-type of source array.
         */
        private static Class<?> componentType(final Object[] src) {
            return src != null ? src.getClass().getComponentType() : Object.class;
        }
    }

    // ----------------------------------------------------------

    private static void precondition(final Object[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final Object[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final boolean[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final boolean[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final byte[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final byte[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final short[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final short[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final char[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final char[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final int[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final int[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final long[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final long[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final float[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final float[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final double[] a, final Range b) {
        if (null == a) throw Exceptions.nullPointer("src = null");
        if (null == b) throw Exceptions.nullPointer("range = null");
        precondition(b, a.length);
    }

    private static void precondition(final double[] a, final Range b, final Object c) {
        if (null == c) throw Exceptions.nullPointer("3rd argument = null");
        precondition(a, b);
    }

    private static void precondition(final Range range, final long length) {
        final long lo, hi;
        if ((lo = range.lo()) < 0 || lo > (hi = range.hi()) || hi > length) {
            throw Exceptions.outOfBounds("range[%d:%d) is out of bounds for array.length[%d]", lo, range.hi(), length);
        }
    }

    // ----------------------------------------------------------

    private static Range range(final Object[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }

    private static Range range(final boolean[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }

    private static Range range(final byte[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }

    private static Range range(final short[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }

    private static Range range(final char[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }

    private static Range range(final int[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }

    private static Range range(final long[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }

    private static Range range(final float[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }

    private static Range range(final double[] src) {
        final class Length extends Range.AbstractBase {
            @Override public long lo() { return 0L; }

            @Override public long hi() { return src.length; }
        }
        return new Length();
    }
}
