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
import magma.exa.control.exception.TODO;

/**
 * Iterable companion.
 */
public enum Iterables {
    ;
    /**
     * Combines multiple iterables into a single iterable. The returned composite
     * iterable produces a composite iterators that yield all values of each
     * corresponding iterable in the specified declaration order.
     *
     * @param itrs array of iterators.
     * @param <A> type of iterable values.
     * @return composite iterable.
     */
    @SafeVarargs
    public static <A> Iterable<A> concat(final Iterable<? extends A>... itrs) {
        /*Assert.notNull(itrs);
        final class ConcatIterable implements Iterable<A> {
            private final Iterable<? extends A>[] src;
            { src = Array.filterNull(itrs.clone()); }
            @Override
            public Iterator<A> iterator() {
                final var its = Array.map(src, Iterable::iterator);
                return Iterators.concat(Array.filterNull(its));
            }
            @Override
            public void forEach(final java.util.function.Consumer<? super A> action) {
                iterator().forEachRemaining(java.util.Objects.requireNonNull(action));
            }
        }
        return new ConcatIterable();*/
        throw TODO.notImplemented();
    }

    /**
     * Determines whether the two given Iterables contain equal values
     * in the same order.
     *
     * @param ia iterable to be compared with {@code ib}.
     * @param ib iterable to be compared with {@code ia}.
     * @return true iff equal values in the same order.
     */
    public static boolean equals(final Iterable<?> ia, final Iterable<?> ib) {
        return Iterators.equals(ia.iterator(), ib.iterator());
    }

    /**
     * Returns the string representation of the given iterable.
     */
    public static String toString(final Iterable<?> itr) {
        return Iterators.toString(itr.iterator());
    }

    /**
     * Returns the empty Iterator of type {@code A}.
     *
     * @param <A> type of value.
     * @return empty Iterator.
     */
    public static <A> Iterable<A> empty() {
        enum Empty implements Iterable<Object> {
            iterable;

            /**
             * {@inheritDoc}
             */
            @Override
            public java.util.Iterator<Object> iterator() {
                return Iterators.empty();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void forEach(final java.util.function.Consumer<? super Object> action) {
                java.util.Objects.requireNonNull(action);
            }
        }
        return Force.cast(Empty.iterable);
    }
}
