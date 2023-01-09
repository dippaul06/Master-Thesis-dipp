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

package magma.exa.data.index;

/**
 *
 */
public enum Span {
    ;
    /**
     * Returns lower bound of the given span (inclusive).
     */
    public static int lo(final long span) {
        return (int) (span >> 32);
    }

    /**
     * Returns upper bound of the given span (exclusive).
     */
    public static int hi(final long span) {
        return (int) (span);
    }

    // ----------------------------------------------------------

    /// RANGE QUERIES.

    /**
     * Determines whether the given span is empty.
     * <p>
     * A span is considered empty if the upper bound is less than or
     * equal to the lower bound, i.e. formally {@code lo() >= hi()}.
     *
     * @param span to be queried.
     * @return true iff empty.
     */
    public static boolean isEmpty(final long span) {
        return ((int) span) <= (int) (span >> 32);
    }

    /**
     * Determines whether the given span is present.
     * <p>
     * A span is considered empty if the upper bound is less than or
     * equal to the lower bound, i.e. formally {@code lo() >= hi()}.
     *
     * @param span to be queried.
     * @return true iff present.
     */
    public static boolean isPresent(final long span) {
        return (int) (span >> 32) < ((int) span);
    }

    /**
     * Returns the length of the given span.
     *
     * @param span to be queried.
     * @return true iff present.
     */
    public static long length(final long span) {
        return Math.max(0, ((int) span) - (int) (span >> 32));
    }

    /**
     * Returns the last valid value within the given span.
     *
     * @param span to be queried.
     * @return last valid value.
     */
    public static long last(final long span) {
        return (int) span - 1;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the given span contains the given value,
     * i.e. formally {@code lo() <= x0 < hi()}.
     *
     * @param span to be queried.
     * @param x1 value to be queried for inclusion.
     * @return true iff value is contained.
     */
    public static boolean contains(final long span, final long x1) {
        return (lo(span) <= x1) && (x1 < hi(span));
    }

    /**
     * Determines whether the given span contains all given values,
     * i.e. formally ∀i {@code lo <= x_i < hi}.
     *
     * @param span to be queried.
     * @param x1 value to be queried for containment.
     * @param x2 value to be queried for containment.
     * @return true iff all values are contained.
     */
    public static boolean contains(final long span, final long x1, final long x2) {
        final long lo, hi;
        return (lo = lo(span)) <= x1
                && lo <= x2 
                && x1 < (hi = hi(span)) 
                && x2 < hi;
    }

    /**
     * Determines whether the given span contains all given values,
     * i.e. formally ∀i {@code lo <= x_i < hi}.
     *
     * @param span to be queried.
     * @param xi array of values to be queried for containment
     * @return true iff all values are contained.
     */
    public static boolean contains(final long span, final long... xi) {
        final int len;
        if (isEmpty(span) || (len = xi.length) == 0)
            return false;
        final long lo = lo(span);
        final long hi = hi(span);
        for (int i = 0; i < len; ++i) {
            final long x = xi[i];
            if ((lo > x) || (x >= hi)) {
                return false;
            }
        }
        return true;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the given span contains the other range.
     *
     * @param span to be queried.
     * @param a range to be queried for containment.
     * @return true if given range is contained.
     */
    public static boolean contains(final long span, final Range a) {
        final long l0, h0, l1, h1;
        return (l0 = lo(span)) <= (l1 = a.lo())
                && l1 <= (h0 = hi(span))
                && l0 <= (h1 = a.hi())
                && h1 <= h0;
    }

    /**
     * Determines whether the given span contains all other ranges.
     *
     * @param span to be queried.
     * @param a range to be queried for containment.
     * @param b range to be queried for containment.
     * @return true if all ranges are contained.
     */
    public static boolean contains(final long span, final Range a, final Range b) {
        final long l0, h0, l1, h1, l2, h2;
        return (l0 = lo(span)) <= (l1 = a.lo()) && l1 <= (h0 = hi(span))
                && l0 <= (h1 = a.hi()) && h1 <= h0 
                && l0 <= (l2 = b.lo()) && l2 <= h0
                && l0 <= (h2 = b.hi()) && h2 <= h0;
    }

    /**
     * Determines whether the given span contains all other ranges.
     *
     * @param span to be queried.
     * @param ri array of ranges to be queried for containment.
     * @return true iff all ranges are contained.
     */
    public static boolean contains(final long span, final Range... ri) {
        if (isEmpty(span)) return false;
        final int len = ri.length;
        for (int i = 0; i < len; ++i) {
            if (!contains(span, ri[i])) {
                return false;
            }
        }
        return true;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether there exists a (possibly empty) span which
     * is enclosed by the given spans. Note that the given spans have
     * a well-defined 'union' and 'intersection' (as a single, possibly
     * empty span) iff this query determines connectedness.
     * <p>
     * The conjoined-relation is both reflexive and symmetric, but
     * does not form an equivalence relation as it is not transitive.
     *
     * @param a span queried to be connected with {@code b}.
     * @param b span queried to be connected with {@code a}.
     * @return true iff an enclosed span exists.
     */
    public static boolean connects(final long a, final long b) {
        return lo(a) <= hi(b) && lo(b) <= hi(a);
    }

    /**
     * Determines whether there exists no span which is enclosed by
     * the given spans. Note there exists no well-defined 'union'
     * and 'intersection' iff this query determines non-connectedness.
     *
     * @param a span queried to be disconnected with {@code b}.
     * @param b span queried to be disconnected with {@code a}.
     * @return true iff no enclosed span exists.
     */
    public static boolean disconnects(final long a, final long b) {
        return lo(a) > hi(b) || lo(b) > hi(a);
    }

    /**
     * Determines whether the given spans are adjacent.
     *
     * @param a span queried to be adjacent to {@code b}.
     * @param b span queried to be adjacent to {@code a}.
     * @return true if spans are adjacent.
     */
    public static boolean adjacent(final long a, final long b) {
        return lo(a) == hi(b) || hi(a) == lo(b);
    }

    // --------------------------------------------------

    /// UNION / INTERSECTION

    /**
     * Returns the maximal span enclosed by the given spans, if such
     * a span exists. The intersection exists if and only if the two
     * spans are connected, see {@link #connects(long, long)}.
     * <p>
     * The intersection operation is commutative, associative and
     * idempotent, and its identity element is {@link Range#all}).
     *
     * @param a 1st connected span.
     * @param b 2nd connected span.
     * @return intersection of given spans.
     */
    public static long intersect(final long a, final long b) {
        final long as, ae, bs, be;
        return ((bs = lo(b)) > (ae = hi(a)) || (as = lo(a)) > (be = hi(b)))
                ? 0L : of(Math.max(as, bs), Math.min(ae, be));
    }

    /**
     * Returns the minimal span enclosed by the given spans, if such
     * a span exists. The intersection exists if and only if the two
     * spans are connected, see {@link #connects(long, long)}.
     * <p>
     * Like intersection, this operation is commutative, associative
     * and idempotent. Unlike it, it is always well-defined for any
     * two input spans.
     *
     * @param a 1st connected span.
     * @param b 2nd connected span.
     * @return union of given spans.
     */
    public static long union(final long a, final long b) {
        final long as, ae, bs, be;
        return ((bs = lo(b)) > (ae = hi(a)) || (as = lo(a)) > (be = hi(b)))
                ? 0L : of(Math.min(as, bs), Math.max(ae, be));
    }

    // ----------------------------------------------------------

    /**
     * Constructs a span from the given range.
     *
     * @param range to be converted.
     * @return span encoded value.
     */
    public static long of(final Range range) {
        return range.lo() << 32 | range.hi() & 0xFFFF_FFFFL;
    }

    /**
     * Constructs a span from the given lower- and upper bound.
     *
     * @param lo lower-bound (inclusive).
     * @param hi upper-bound (exclusive).
     * @return span encoded value.
     */
    public static long of(final int lo, final int hi) {
        return ((long) lo) << 32 | hi & 0xFFFF_FFFFL;
    }

    /**
     * Constructs a span from the given lower- and upper bound.
     *
     * @param lo lower-bound (inclusive).
     * @param hi upper-bound (exclusive).
     * @return span encoded value.
     */
    public static long of(final long lo, final long hi) {
        return lo << 32 | hi & 0xFFFF_FFFFL;
    }
}
