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

import magma.exa.adt.mixin.Mixin;
import magma.exa.adt.typelevel.nat.$N;
import magma.exa.base.contract.Assert;

// https://github.com/apple/swift/blob/main/stdlib/public/core/Stride.swift


/**
 * A const.-space representation of a finite, consecutive integral index set.
 * The sequence of valid index values that the axis represent is defined
 * as half-open interval bounded inclusively below and exclusively above.
 *
 * More formally, the represented index sequence for the range (lo, hi)
 * contains all indices {@code ix} such that:
 *
 * 		[lo, hi) := [lo, hi[ := {ix: long | lo <= ix < hi}
 *
 * The axis is considered as empty, if the upper index-bound is less than
 * the lower index-bound, i.e. {@code lo() >= hi()}.
 *
 * @param <D> type-level numeral that specifies the dimension.
 */
public interface Axis<D extends $N> extends Dim<D>, Range,
        Mixin.Count, Mixin.IsEmpty {

	/**
	 * Index wildcard.
	 */
	long $ = Long.MIN_VALUE;

    /**
     * Returns the type-level numeral that specifies the dimension.
     */
    D dim();

    /**
     * Returns the lower index-bound, inclusive.
     */
    long lo();

    /**
     * Returns the upper index-bound, exclusive.
     */
    long hi();

    /**
     * Returns the number of defined indices.
     */
    default long count() { return Math.max(0L, hi() - lo()); }

    /**
     * Determines whether this axis has no indices.
     */
    default boolean isEmpty() { return hi() - lo() <= 0L; }

    /**
     * Returns the last valid upper index.
     */
    default long lastIndex() { return hi() - lo() > 0L ? hi() - 1L : Axis.$; }

    /*
     * Returns linear distance between adjacent indices.
     */
    //long stride();

    /*
     * Returns the 0-centered displacement value.
     */
    //long origin();

	/**
	 * Determines whether the given index is valid.
	 *
	 * @param index to be validated.
	 * @return true iff valid.
	 */
	static boolean isWildcard(final long index) {
		return index != Axis.$;
	}

	/**
	 * Returns string representation of the given axis.
	 */
	static String toString(final Axis<?> axis) {
		return Range.toString(axis);
	}

    // ----------------------------------------------------------

    static <D extends $N> Axis<D> of(final D dim, final Range range) {
        return of(dim, range.lo(), range.hi());
    }

    static <D extends $N> Axis<D> of(final D dim, final long lo, final long hi) {
        Assert.notNull(dim);
        final class BaseAxis implements Axis<D> {
            @Override public D      dim()       { return dim; }
            @Override public long   count()     { return Math.max(hi - lo, 0L); }
            @Override public long   lo()        { return lo; }
            @Override public long   hi()        { return hi; }
            @Override public long   lastIndex() { return hi - 1; }
            @Override public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Axis<?> axis))
                    return false;
                return dim == axis.dim() &&
                        lo == axis.lo() &&
                        hi == axis.hi();
            }
            @Override public int hashCode() {
                int hash = dim.hashCode();
                hash = 31 * hash + (int) (lo ^ (lo >>> 32));
                return 31 * hash + (int) (hi ^ (hi >>> 32));
            }
        }
        return new BaseAxis();
    }

    // ----------------------------------------------------------
    //  AXIS.SLICE
    // ----------------------------------------------------------

    /*
     * A slice represents a regular subset of a corresponding axis and
     * is defined as a triple (start, stop, step). The 1st component
     * determines the start-index which is either a valid index of the
     * corresponding axis or has the {link Index#NaX} value, which has
     * the effect that the lower bound of the axis is selected. The 2nd
     * component determines the stop-index which is also either a valid
     * index of the corresponding axis or has the {link Index#NaX} value,
     * which has the effect that the upper bound of the axis is selected.
     * The 3rd component determines the step width, which must be != 0.
     * A slice given with ’i : j : k’ selects the m elements with index
     * values i, i + k, …, i + (m − 1) * k where m = q + (r != 0) where
     * q and r are the quotient and remainder obtained by dividing j − i
     * by k: j − i = qk + r, so that i + (m − 1) * k < j.
     *
     * @param <D> type-level numeral that specifies the dimension.
     */
    //interface Slice<D extends $N> extends Dim<D>, Counted, Owned<Axis<D>>, Range {
    //
    //    /**
    //     * Returns the axis on which the slice is defined.
    //     */
    //    Axis<D> owner();
    //
    //    /**
    //     * Returns the number of slice indices.
    //     */
    //    long count();
    //
    //    /**
    //     * Reports the lower slice bound (inclusive).
    //     */
    //    long start();
    //
    //    /**
    //     * Reports the upper slice bound (exclusive).
    //     */
    //    long stop();
    //
    //    /**
    //     * Reports the step width (!= 0).
    //     */
    //    long step();
    //
    //    // ----------------------------------------------------------
    //    //  AXIS.SLICE.BASE
    //    // ----------------------------------------------------------
    //
    //    /**
    //     *
    //     * @param <D> type-level numeral that specifies the dimension.
    //     */
    //    class Base<D extends $N> implements Axis.Slice<D> {
    //        private final Axis<D> owner;
    //        private final long i, j, k, m;
    //
    //        Base(final Axis<D> owner, final long i, final long j, final long k) {
    //            this.owner = java.util.Objects.requireNonNull(owner);
    //            this.k = k;
    //            this.i = k > 0L ? i : Math.min(i, owner().hi() - 1L);
    //            this.j =  stop(i, j, k);
    //            this.m = count(i, j, k);
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public D dim() {
    //            return owner.dim();
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public Axis<D> owner() {
    //            return owner;
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public final long lo() {
    //            return Math.min(i, j);
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public final long hi() {
    //            return Math.max(i, j) + 1L;
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public long start() {
    //            return i;
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public long stop() {
    //            return j;
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public long step() {
    //            return k;
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public long at(final long n) {
    //            Assert.validPosition(n, this);
    //            return i + n * k;
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public long count() {
    //            return m;
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public I64.Traverser.Linear traverser() {
    //            return new Axis.Slice.Base.Traverser<>(this);
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public long[] toArray() {
    //            final long[] ary = new long[Narrow.I32(m)];
    //            long ix = i;
    //            if (k > 0L) for (int l = 0; ix <= j; ix += k, ++l) ary[l] = ix;
    //            else        for (int l = 0; ix >= j; ix += k, ++l) ary[l] = ix;
    //            return ary;
    //        }
    //
    //        // ----------------------------------------------------------
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public boolean equals(Object o) {
    //            if (this == o) return true;
    //            if (!(o instanceof Base<?> base))
    //                return false;
    //            return owner.equals(base.owner) &&
    //                    i == base.i &&
    //                    j == base.j &&
    //                    k == base.k &&
    //                    m == base.m;
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public int hashCode() {
    //            int hash = owner.hashCode();
    //            hash = 31 * hash + (int) (i ^ (i >>> 32));
    //            hash = 31 * hash + (int) (j ^ (j >>> 32));
    //            hash = 31 * hash + (int) (k ^ (k >>> 32));
    //            return 31 * hash + (int) (m ^ (m >>> 32));
    //        }
    //
    //        /**
    //         * {@inheritDoc}
    //         */
    //        @Override
    //        public String toString() {
    //            return String.format("[%d:%d:%d]", i, j, k);
    //        }
    //
    //        // ----------------------------------------------------------
    //        //  AXIS.SLICE.BASE.TRAVERSABLE
    //        // ----------------------------------------------------------
    //
    //        public static final class Traverser<D extends $N> implements I64.Traverser.Linear {
    //            private final Axis.Slice<D> slice;
    //
    //            public Traverser(Slice<D> slice) {
    //                this.slice = slice;
    //            }
    //
    //            @Override
    //            public boolean tryNext(I64.Consumer action) {
    //                return false;
    //            }
    //
    //            @Override
    //            public boolean tryPrev(I64.Consumer action) {
    //                return false;
    //            }
    //        }
    //
    //        // ----------------------------------------------------------
    //
    //        private static long count(final long i, final long j, final long k) {
    //            final long q = (j - i) / k;
    //            final long r = (j - i) % k;
    //            return q + (r != 0L ? 1L : 0L);
    //        }
    //
    //        private static long stop(final long i, final long j, final long k) {
    //            return i + ((count(i, j, k) - 1) * k);
    //        }
    //    }
    //}
}
