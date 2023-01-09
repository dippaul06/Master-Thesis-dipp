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

import magma.exa.base.Hash;
import magma.exa.base.contract.Assert;

/**
 * Range is a constant-space representation of a half-open interval
 * bounded inclusively below and exclusively above. A range yields a
 * continuous {@code long} value sequence. A range require that the
 * upper bound may not be less than the lower bound, else the range
 * is considered as empty, {@code lo >= hi}. More formally, the re-
 * presented sequence for the range [lo:hi] contains all indices 'ix'
 * such that:
 *            [lo:hi] <=> [lo..hi) = {x | lo <= x < hi}
 *
 * Ranges support iteration over the represented indices and provide
 * a rich set of static operations. All operations on a range return
 * a new range rather than modifying the existing one.
 *
 * http://esri.github.io/geometry-api-java/doc/RelationalOperators.html
 */
public interface Range {
    long _$__LO = Long.MIN_VALUE;
    long _$__HI = Long.MAX_VALUE;
    long MIN_LO = Long.MIN_VALUE + 1;
    long MAX_HI = Long.MAX_VALUE - 1;
    
    // ----------------------------------------------------------

    /**
     * The empty range.
     */
    Range empty = Const.empty;

    /**
     * The range that contain all other ranges.
     */
    Range all = Const.all;

    // ----------------------------------------------------------

    /**
     * Returns the lower bound of this range (inclusive).
     */
    long lo();

    /**
     * Returns the upper bound of this range (exclusive).
     */
    long hi();

    // ----------------------------------------------------------

    /// RANGE QUERIES.

    /**
     * Returns lower bound of the given range (inclusive).
     */
    static int lo(final Range range) {
        return (int) range.lo();
    }

    /**
     * Returns upper bound of the given range (exclusive).
     */
    static int hi(final Range range) {
        return (int) range.hi();
    }

    /**
     * Determines whether the given range is empty.
     * <p>
     * A range is considered empty if the upper bound is less than or
     * equal to the lower bound, i.e. formally {@code lo() >= hi()}.
     *
     * @param range to be queried.
     * @return true iff empty.
     */
    static boolean isEmpty(final Range range) {
        return range.hi() <= range.lo();
    }

    /**
     * Determines whether the given range is present.
     * <p>
     * A range is considered empty if the upper bound is less than or
     * equal to the lower bound, i.e. formally {@code lo() >= hi()}.
     *
     * @param range to be queried.
     * @return true iff present.
     */
    static boolean isPresent(final Range range) {
        return range.lo() < range.hi();
    }

    /**
     * Returns the length of the given range.
     *
     * @param range to be queried.
     * @return true iff present.
     */
    static long length(final Range range) {
        return Math.max(range.hi() - range.lo(), 0L);
    }

    /**
     * Returns the last valid value within the given range.
     *
     * @param range to be queried.
     * @return last valid value.
     */
    static long last(final Range range) {
        return range.hi() - 1L;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the given range contains the given value,
     * i.e. formally {@code lo() <= x0 < hi()}.
     *
     * @param range to be queried.
     * @param x1 value to be queried for inclusion.
     * @return true iff value is contained.
     */
    static boolean contains(final Range range, final long x1) {
        return (range.lo() <= x1) && (x1 < range.hi());
    }

    /**
     * Determines whether the given range contains all given values,
     * i.e. formally ∀i {@code lo <= x_i < hi}.
     *
     * @param range to be queried.
     * @param x1 value to be queried for containment.
     * @param x2 value to be queried for containment.
     * @return true iff all values are contained.
     */
    static boolean contains(final Range range, final long x1, final long x2) {
        final long lo, hi;
        return (lo = range.lo()) <= x1 && lo <= x2 &&
                x1 < (hi = range.hi()) && x2 < hi;
    }

    /**
     * Determines whether the given range contains all given values,
     * i.e. formally ∀i {@code lo <= x_i < hi}.
     *
     * @param range to be queried.
     * @param xi array of values to be queried for containment
     * @return true iff all values are contained.
     */
    static boolean contains(final Range range, final long... xi) {
        final int len;
        if (isEmpty(range) || (len = xi.length) == 0)
            return false;
        final long lo = range.lo();
        final long hi = range.hi();
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
     * Determines whether the given source range contains the other range.
     *
     * @param range to be queried.
     * @param a range to be queried for containment.
     * @return true if given range is contained.
     */
    static boolean contains(final Range range, final Range a) {
        final long l0, h0, l1, h1;
        return (l0 = range.lo()) <= (l1 = a.lo()) && l1 <= (h0 = range.hi()) &&
                l0 <= (h1 = a.hi()) && h1 <= h0;
    }

    /**
     * Determines whether the given source range contains all other ranges.
     *
     * @param range to be queried.
     * @param a range to be queried for containment.
     * @param b range to be queried for containment.
     * @return true if all ranges are contained.
     */
    static boolean contains(final Range range, final Range a, final Range b) {
        final long l0, h0, l1, h1, l2, h2;
        return (l0 = range.lo()) <= (l1 = a.lo()) && l1 <= (h0 = range.hi()) &&
                l0 <= (h1 = a.hi()) && h1 <= h0 &&
                l0 <= (l2 = b.lo()) && l2 <= h0 &&
                l0 <= (h2 = b.hi()) && h2 <= h0;
    }

    /**
     * Determines whether the given source range contains all other ranges.
     *
     * @param range to be queried.
     * @param ri array of ranges to be queried for containment.
     * @return true iff all ranges are contained.
     */
    static boolean contains(final Range range, final Range... ri) {
        if (isEmpty(range)) return false;
        final int len = ri.length;
        for (int i = 0; i < len; ++i) {
            if (!contains(range, ri[i])) {
                return false;
            }
        }
        return true;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether there exists a (possibly empty) range which
     * is enclosed by the given ranges. Note that the given ranges have
     * a well-defined 'union' and 'intersection' (as a single, possibly
     * empty range) iff this query determines connectedness.
     * <p>
     * The conjoined-relation is both reflexive and symmetric, but
     * does not form an equivalence relation as it is not transitive.
     *
     * @param a range queried to be connected with {@code b}.
     * @param b range queried to be connected with {@code a}.
     * @return true iff an enclosed range exists.
     */
    static boolean connects(final Range a, final Range b) {
        return a.lo() <= b.hi() && b.lo() <= a.hi();
    }

    /**
     * Determines whether there exists no range which is enclosed by
     * the given ranges. Note there exists no well-defined 'union'
     * and 'intersection' iff this query determines non-connectedness.
     *
     * @param a range queried to be disconnected with {@code b}.
     * @param b range queried to be disconnected with {@code a}.
     * @return true iff no enclosed range exists.
     */
    static boolean disconnects(final Range a, final Range b) {
        return a.lo() > b.hi() || b.lo() > a.hi();
    }

    /**
     * Determines whether the given ranges are adjacent.
     *
     * @param a range queried to be adjacent to {@code b}.
     * @param b range queried to be adjacent to {@code a}.
     * @return true if ranges are adjacent.
     */
    static boolean adjacent(final Range a, final Range b) {
        return a.lo() == b.hi() || a.hi() == b.lo();
    }

    // --------------------------------------------------

    /// UNION / INTERSECTION

    /**
     * Returns the maximal range enclosed by the given ranges, if such
     * a range exists. The intersection exists if and only if the two
     * ranges are connected, see {@link #connects(Range, Range)}.
     * <p>
     * The intersection operation is commutative, associative and
     * idempotent, and its identity element is {@link Range#all}).
     *
     * @param a 1st connected range.
     * @param b 2nd connected range.
     * @return intersection of given ranges.
     */
    static Range intersect(final Range a, final Range b) {
        final long as, ae, bs, be;
        return ((bs = b.lo()) > (ae = a.hi()) || (as = a.lo()) > (be = b.hi()))
                ? empty : of(Math.max(as, bs), Math.min(ae, be));
    }

    /**
     * Returns the minimal range enclosed by the given ranges, if such
     * a range exists. The intersection exists if and only if the two
     * ranges are connected, see {@link #connects(Range, Range)}.
     * <p>
     * Like intersection, this operation is commutative, associative and
     * idempotent. Unlike it, it is always well-defined for any two input
     * ranges.
     *
     * @param a 1st connected range.
     * @param b 2nd connected range.
     * @return union of given ranges.
     */
    static Range union(final Range a, final Range b) {
        final long as, ae, bs, be;
        return ((bs = b.lo()) > (ae = a.hi()) || (as = a.lo()) > (be = b.hi()))
                ? empty : of(Math.min(as, bs), Math.max(ae, be));
    }

    // --------------------------------------------------

    /// SPAN / GAP

    /**
     * Returns the minimum range that contains the given ranges.
     * Unlike 'union' the given ranges must not be connected.
     *
     * @param a 1st range to be contained.
     * @param b 2nd range to be contained.
     * @return minimum span range.
     */
    static Range span(final Range a, final Range b) {
        return isEmpty(a) ? b : isEmpty(b) ? a : Range.of(Math.min(a.lo(), b.lo()), Math.max(a.hi(), b.hi()));
    }

    /**
     * Returns the minimum range that contains all ranges in the given array.
     * Unlike 'union' the given ranges must not be connected.
     *
     * @param rns array of ranges.
     * @return minimum span range.
     */
    static Range span(final Range... rns) {
        final int len = rns.length;
        long lo = MAX_HI, hi = MIN_LO;
        for (int i = 0; i < len; ++i) {
            final Range rn = rns[i];
            lo = Math.min(lo, rn.lo());
            hi = Math.max(hi, rn.hi());
        }
        return of(lo, hi);
    }

    // ----------------------------------------------------------

    /**
     * Returns an array containing the sub-ranges produced by splitting
     * the given range into {@code count} equisized sub-ranges..
     *
     * @param range to be partitioned.
     * @param count of range splits.
     * @return array containing equisized splits.
     */
    static Range[] partition(final Range range, final int count) {
        final long len = length(range);
        Assert.lessThan(count, len);
        final long m = Math.min(count, len);
        final long p = len / m;
        final Range[] rns = new Range[(int) m];
        for (int i = 0; i < m; ++i) {
            final long lo;
            rns[i] = Range.of(
                    lo = i * p,
                    lo + p + ((i < m - 1) ? 0 : len % m)
            );
        }
        return rns;
    }

    // ----------------------------------------------------------

    /**
     * Determines whether the given ranges are equal to each other.
     * Consequently, both ranges are considered equal if they are null.
     *
     * @param a range to be compared with {@code r2}.
     * @param b range to be compared with {@code r1}.
     * @return true iff ranges are equal to each other.
     */
    static boolean equals(final Range a, final Range b) {
        return a == b || a != null && b != null &&
                a.lo() == b.lo() && a.hi() == b.hi();
    }

    /**
     * Returns the hash code of the given non-null range, otherwise 0.
     */
    static int hashCode(final Range range) {
        return range != null ? Hash.combine(Hash.code(range.lo()), Hash.code(range.hi())) : 0;
    }

    /**
     * Returns the string representation of the given range.
     *
     * @param range whose string is to be returned.
     * @return string representation.
     */
    static String toString(final Range range) {
        return range != null ? String.format("[%d..%d]", range.lo(), range.hi()) : "null";
    }

    // ----------------------------------------------------------

    // TODO: COMPARATOR.

    // ----------------------------------------------------------


    static Range of(final CharSequence csq) {
        return Range.of(csq.length());
    }

    static Range of(final long length) {
        if (length <= 0L) return empty;
        final class Rng implements Range {
            @Override public long    lo()             { return 0L; }
            @Override public long    hi()             { return length; }
            @Override public String  toString()       { return Range.toString(this); }
            @Override public int     hashCode()       { return Range.hashCode(this); }
            @Override public boolean equals(final Object o) {
                return this == o || (o instanceof Range rn) &&
                         0L == rn.lo() && length == rn.hi();
            }
        }
        return new Rng();
    }

    /**
     * Constructs a range with the given lower and upper bounds.
     *
     * @param lo lower bound of the range (inclusive).
     * @param hi upper bound of the range (exclusive).
     * @return new range.
     */
    static Range of(final long lo, final long hi) {
        if (hi <= lo) return empty;
        final class Rng implements Range {
            @Override public long    lo()             { return lo; }
            @Override public long    hi()             { return hi; }
            @Override public String  toString()       { return Range.toString(this); }
            @Override public int     hashCode()       { return Range.hashCode(this); }
            @Override public boolean equals(final Object o) {
                return this == o || (o instanceof Range rn) &&
                        lo == rn.lo() && hi == rn.hi();
            }
        }
        return new Rng();
    }

    /**
     * Constructs a range that contains only the given value.
     *
     * @param val value to be contained.
     * @return new range.
     */
    static Range singleton(final long val) {
        final class Rng implements Range {
            @Override public long    lo()             { return val; }
            @Override public long    hi()             { return val + 1; }
            @Override public String  toString()       { return Range.toString(this); }
            @Override public int     hashCode()       { return Range.hashCode(this); }
            @Override public boolean equals(final Object o) {
                return this == o || (o instanceof Range rn) &&
                        val == rn.lo() && val + 1 == rn.hi();
            }
        }
        return new Rng();
    }

    /**
     * The empty range type.
     */
    enum Const implements Range {
        empty {
            @Override public long lo() { return 0L; }
            @Override public long hi() { return 0L; }
        },
        all {
            @Override public long lo() { return MIN_LO; }
            @Override public long hi() { return MAX_HI; }
        }
        ;
        @Override public String toString() {
            return Range.toString(this);
        }
    }

    /**
     * Abstract base class implementation.
     */
    abstract class AbstractBase implements Range {
        protected AbstractBase() { }
        @Override public final String  toString() { return Range.toString(this); }
        @Override public final int     hashCode() { return Range.hashCode(this); }
        @Override public final boolean equals(final Object o) {
            return this == o || (o instanceof Range rn) &&
                    lo() == rn.lo() && hi() == rn.hi();
        }
    }
}
