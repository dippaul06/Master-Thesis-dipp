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

import java.util.Objects;

/**
 * Collection of hash function utilities.
 */
public enum Hash {
    ;
    /**
     * Returns a hash code for the given {@code int} value.
     * <p>
     * The algorithm provides a good statistical distribution. Each input bit
     * influences each output bit with a probability of about 50%. There are
     * no collisions (each input leads to a different output).
     * @see <a href="https://bit.ly/3CqxrTs"></a>
     *
     * @param value value to be hashed.
     * @return hashed value.
     */
    public static int code(final int value) {
        int x = value;
        x = ((x >>> 16) ^ x) * 0x119DE1F3;
        x = ((x >>> 16) ^ x) * 0x119DE1F3;
        x = ((x >>> 16) ^ x);
        return x;
    }

    /**
     * Returns the result of computing the hash of the given {@code int} value
     * and applying the given mask to it (to get the remainder). The mask must
     * be a power of 2 - 1.
     *
     * @param value to be hashed.
     * @param mask to be applied.
     * @return hash of the value.
     */
    public static int code(final int value, final int mask) {
        return Hash.code(value) & mask;
    }

    /**
     * Returns the result of computing the hash of the given {@code int} value
     * and applying the given mask to it (to get the remainder). The mask must
     * be a power of 2 - 1.
     *
     * @param value to be hashed.
     * @param mask to be applied.
     * @return hash of the value.
     */
    public static int evenCode(final int value, final int mask) {
        final int hash = Hash.code(value);
        return ((hash << 1) - (hash << 8)) & mask;
    }

    // ----------------------------------------------------------

    /**
     * Returns a hash code for the given {@code long} value.
     * <p>
     * The algorithm provides a good statistical distribution. Each input bit
     * influences each output bit with a probability of about 50%. There are
     * no collisions (each input leads to a different output).
     * @see <a href="https://bit.ly/3CqxrTs"></a>
     *
     * @param value value to be hashed.
     * @return hashed value.
     */
    public static int code(final long value) {
        long x = value;
        x = (x ^ (x >>> 30)) * 0xBF58476D1CE4E5B9L;
        x = (x ^ (x >>> 27)) * 0x94D049BB133111EBL;
        x = (x ^ (x >>> 31));
        return (int) x ^ (int) (x >>> 32);
    }

    /**
     * Returns the result of computing the hash of the given {@code long} value
     * and applying the given mask to it (to get the remainder). The mask must
     * be a power of 2 - 1.
     *
     * @param value to be hashed.
     * @param mask  to be applied.
     * @return hashed value.
     */
    public static int code(final long value, final int mask) {
        return Hash.code(value) & mask;
    }

    /**
     * Returns the result of computing the hash of the given {@code long} value
     * and applying the given mask to it (to get the remainder). The mask must
     * be a power of 2 - 1.
     *
     * @param value to be hashed.
     * @param mask  to be applied.
     * @return hashed value (even).
     */
    public static int evenCode(final long value, final int mask) {
        final int hash = Hash.code(value);
        return ((hash << 1) - (hash << 8)) & mask;
    }

    // ----------------------------------------------------------

    /**
     * Returns the hash code for the given object. If the argument is null
     * then 0 is returned, according to {@link Objects#hashCode(Object)}.
     *
     * @param obj to be hashed.
     * @return hashed value.
     */
    public static int code(final Object obj) {
        return null == obj ? 0 : obj.hashCode();
    }

    /**
     * Returns the result of computing the hash of the given object and
     * applying the given mask to it (to get the remainder).
     * The mask must be a power of 2 - 1.
     *
     * @param obj    object to be hashed.
     * @param mask to be applied.
     * @return hashed value.
     */
    public static int code(final Object obj, final int mask) {
        return Hash.code(obj) & mask;
    }

    // ----------------------------------------------------------

    /**
     * Returns a combined hash code from the given 2 objects.
     *
     * @param a 1st object to be hashed.
     * @param b 2nd object to be hashed.
     * @return hashed value.
     */
    public static int code(final Object a, final Object b)
    {
        return Hash.combine(
                Hash.code(a),
                Hash.code(b)
        );
    }

    /**
     * Returns a combined hash code from the given 3 objects.
     *
     * @param a 1st object to be hashed.
     * @param b 2nd object to be hashed.
     * @param c 3rd object to be hashed.
     * @return hashed value.
     */
    public static int code(final Object a, final Object b, final Object c)
    {
        return Hash.combine(
                Hash.code(a),
                Hash.code(b),
                Hash.code(c)
        );
    }

    /**
     * Returns a combined hash code from the given 4 objects.
     *
     * @param a 1st object to be hashed.
     * @param b 2nd object to be hashed.
     * @param c 3rd object to be hashed.
     * @param d 4th object to be hashed.
     * @return hashed value.
     */
    public static int code(final Object a, final Object b,
                           final Object c, final Object d)
    {
        return Hash.combine(
                Hash.code(a),
                Hash.code(b),
                Hash.code(c),
                Hash.code(d)
        );
    }

    /**
     * Returns a combined hash code from the given 5 objects.
     *
     * @param a 1st object to be hashed.
     * @param b 2nd object to be hashed.
     * @param c 3rd object to be hashed.
     * @param d 4th object to be hashed.
     * @param e 5th object to be hashed.
     * @return hashed value.
     */
    public static int code(final Object a, final Object b,
                           final Object c, final Object d,
                           final Object e)
    {
        return Hash.combine(
                Hash.code(a),
                Hash.code(b),
                Hash.code(c),
                Hash.code(d),
                Hash.code(e)
        );
    }

    /**
     * Returns a combined hash code from the given 6 objects.
     *
     * @param a 1st object to be hashed.
     * @param b 2nd object to be hashed.
     * @param c 3rd object to be hashed.
     * @param d 4th object to be hashed.
     * @param e 5th object to be hashed.
     * @param f 6th object to be hashed.
     * @return hashed value.
     */
    public static int code(final Object a, final Object b,
                           final Object c, final Object d,
                           final Object e, final Object f)
    {
        return Hash.combine(
                Hash.code(a),
                Hash.code(b),
                Hash.code(c),
                Hash.code(d),
                Hash.code(e),
                Hash.code(f)
        );
    }

    /**
     * Returns a combined hash code from the given 7 objects.
     *
     * @param a 1st object to be hashed.
     * @param b 2nd object to be hashed.
     * @param c 3rd object to be hashed.
     * @param d 4th object to be hashed.
     * @param e 5th object to be hashed.
     * @param f 6th object to be hashed.
     * @param g 7th object to be hashed.
     * @return hashed value.
     */
    public static int code(final Object a, final Object b,
                           final Object c, final Object d,
                           final Object e, final Object f,
                           final Object g)
    {
        return Hash.combine(
                Hash.code(a),
                Hash.code(b),
                Hash.code(c),
                Hash.code(d),
                Hash.code(e),
                Hash.code(f),
                Hash.code(g)
        );
    }

    /**
     * Returns a combined hash code from the given 8 objects.
     *
     * @param a 1st object to be hashed.
     * @param b 2nd object to be hashed.
     * @param c 3rd object to be hashed.
     * @param d 4th object to be hashed.
     * @param e 5th object to be hashed.
     * @param f 6th object to be hashed.
     * @param g 7th object to be hashed.
     * @param h 8th object to be hashed.
     * @return hashed value.
     */
    public static int code(final Object a, final Object b,
                           final Object c, final Object d,
                           final Object e, final Object f,
                           final Object g, final Object h)
    {
        return Hash.combine(
                Hash.code(a),
                Hash.code(b),
                Hash.code(c),
                Hash.code(d),
                Hash.code(e),
                Hash.code(f),
                Hash.code(g),
                Hash.code(h)
        );
    }

    // ----------------------------------------------------------

    /// HASH COMBINER.

    /**
     * Returns a combined hash code from the given 2 hash codes.
     *
     * @param a 1st hash code.
     * @param b 2nd hash code.
     * @return combined hashed value.
     */
    public static int combine(final int a, final int b) {
        return ((a << 5) + a) ^ b;
    }

    /**
     * Returns a combined hash code from the given 3 hash codes.
     *
     * @param a 1st hash code.
     * @param b 2nd hash code.
     * @param c 3rd hash code.
     * @return combined hashed value.
     */
    public static int combine(final int a, final int b, final int c) {
        final int z = (((a << 5) + a) ^ b);
        return ((z << 5) + z) ^ c;
    }

    /**
     * Returns a combined hash code from the given 4 hash codes.
     *
     * @param a 1st hash code.
     * @param b 2nd hash code.
     * @param c 3rd hash code.
     * @param d 4th hash code.
     * @return combined hashed value.
     */
    public static int combine(final int a, final int b,
                              final int c, final int d)
    {
        final int z = ((a << 5) + a) ^ b;
        final int y = ((c << 5) + c) ^ d;
        return ((z << 5) + z) ^ y;
    }

    /**
     * Returns a combined hash code from the given 5 hash codes.
     *
     * @param a 1st hash code.
     * @param b 2nd hash code.
     * @param c 3rd hash code.
     * @param d 4th hash code.
     * @param e 5th hash code.
     * @return combined hashed value.
     */
    public static int combine(final int a, final int b,
                              final int c, final int d,
                              final int e)
    {
        final int z = ((a << 5) + a) ^ b;
        final int y = ((c << 5) + c) ^ d;
        final int x = ((z << 5) + z) ^ y;
        return ((x << 5) + x) ^ e;
    }

    /**
     * Returns a combined hash code from the given 6 hash codes.
     *
     * @param a 1st hash code.
     * @param b 2nd hash code.
     * @param c 3rd hash code.
     * @param d 4th hash code.
     * @param e 5th hash code.
     * @param f 6th hash code.
     * @return combined hashed value.
     */
    public static int combine(final int a, final int b,
                              final int c, final int d,
                              final int e, final int f)
    {
        final int z = ((a << 5) + a) ^ b;
        final int y = ((c << 5) + c) ^ d;
        final int x = ((z << 5) + z) ^ y;
        final int w = ((x << 5) + x) ^ e;
        final int v = ((e << 5) + e) ^ f;
        return ((w << 5) + w) ^ v;
    }

    /**
     * Returns a combined hash code from the given 7 hash codes.
     *
     * @param a 1st hash code.
     * @param b 2nd hash code.
     * @param c 3rd hash code.
     * @param d 4th hash code.
     * @param e 5th hash code.
     * @param f 6th hash code.
     * @param g 7th hash code.
     * @return combined hashed value.
     */
    public static int combine(final int a, final int b,
                              final int c, final int d,
                              final int e, final int f,
                              final int g)
    {
        final int z = ((a << 5) + a) ^ b;
        final int y = ((c << 5) + c) ^ d;
        final int x = ((z << 5) + z) ^ y;
        final int w = ((x << 5) + x) ^ e;
        final int v = ((e << 5) + e) ^ f;
        final int u = ((v << 5) + v) ^ g;
        return ((w << 5) + w) ^ u;
    }

    /**
     * Returns a combined hash code from the given 8 hash codes.
     *
     * @param a 1st hash code.
     * @param b 2nd hash code.
     * @param c 3rd hash code.
     * @param d 4th hash code.
     * @param e 5th hash code.
     * @param f 6th hash code.
     * @param g 7th hash code.
     * @param h 8th hash code.
     * @return combined hashed value.
     */
    public static int combine(final int a, final int b,
                              final int c, final int d,
                              final int e, final int f,
                              final int g, final int h)
    {
        final int z = ((a << 5) + a) ^ b;
        final int y = ((c << 5) + c) ^ d;
        final int x = ((z << 5) + z) ^ y;
        final int w = ((e << 5) + e) ^ f;
        final int v = ((g << 5) + g) ^ h;
        final int u = ((w << 5) + w) ^ v;
        return ((x << 5) + x) ^ u;
    }
}
