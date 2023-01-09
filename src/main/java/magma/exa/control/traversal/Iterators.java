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

package magma.exa.control.traversal;

import magma.exa.base.Force;
import magma.exa.base.contract.Assert;
import magma.exa.control.exception.Exceptions;

/**
 * Iterator companion.
 */
public enum Iterators {
    ;
    /**
     * Returns a concatenated iterator that walks the given array of
     * {@code iterator}s. Each passed array-cell is purged (set to null)
     * to prevent leaking memory.
     *
     * @param its array of iterators.
     * @param <A> type of value.
     * @return composite iterator.
     */
    @SafeVarargs public static <A>
    java.util.Iterator<A> concat(final java.util.Iterator<? extends A>... its) {
        Assert.noNulls(its);
        final int length = its.length;
        if (0 == length) return Iterators.empty();
        final class ConcatIterator implements java.util.Iterator<A> {
            private java.util.Iterator<? extends A> it;
            private int ix;
            { it = its[ix = 0]; }
            @Override
            public boolean hasNext() {
                while (!it.hasNext()) {
                    if (ix < length) {
                        it = its[++ix];
                        // prevent garbage from leaking....
                        its[ix] = null;
                    } else return false;
                }
                return true;
            }

            @Override
            public A next() {
                return it.next();
            }

            @Override
            public void forEachRemaining(final java.util.function.Consumer<? super A> action) {
                it.forEachRemaining(action);
                for (int i = this.ix; i < length; ++i) {
                    final var it = its[i];
                    // prevent garbage from leaking....
                    its[i] = null;
                    it.forEachRemaining(action);
                }
            }
        }
        return new ConcatIterator();
    }

    /**
     * Determines whether the two given iterators contain equal values
     * in the same order.
     *
     * @param ita iterable to be compared with {@code itb}.
     * @param itb iterable to be compared with {@code ita}.
     * @return true iff equal values in the same order.
     */
    public static boolean equals(final java.util.Iterator<?> ita,
                                 final java.util.Iterator<?> itb) {
        while (ita.hasNext()) {
            if (!itb.hasNext()) return false;
            if (!java.util.Objects.equals(ita.next(), itb.next())) {
                return false;
            }
        }
        return !itb.hasNext();
    }

    /**
     * Returns the string representation of the given iterator.
     */
    public static String toString(final java.util.Iterator<?> it) {
        if (!it.hasNext()) return "[]";
        final var sb = new StringBuilder();
        sb.append('[');
        while (true) {
            sb.append(it.next());
            if (!it.hasNext()) break;
            sb.append(", ");
        }
        return sb.append(']').toString();
    }

    /**
     * Returns the empty iterator of type {@code A}.
     *
     * @param <A> type of value.
     * @return empty iterator.
     */
    public static <A> java.util.Iterator<A> empty() {
        enum Empty implements java.util.Iterator<Object> {
            iterator;

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Object next() {
                throw Exceptions.noSuchElement();
            }
        }
        return Force.cast(Empty.iterator);
    }
}
