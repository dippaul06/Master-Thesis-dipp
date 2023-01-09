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

import magma.exa.adt.typelevel.shape.Rank;
import magma.exa.adt.typelevel.nat.$2;
import magma.exa.control.function.Fn1;

/**
 * Indexing function for 2-dimensional data (e.g. matrix)
 * to locate an R-Value or L-Value components.
 *
 * @param <A> type of value.
 */
public interface Ix2D<A> extends Rank<$2>, Ix.Of2<$2, A> {

    /**
     * Fixes the rank to 2.
     */
    @Override
    default $2 rank() {
        return $2.nat;
    }

    /**
     * Partially applies this function by passing the first index-component.
     */
    @Override
    default Ix1D<A> index(final long i1) {
        return i2 -> index(i1, i2);
    }

    /**
     * Lifts the given value into an indexing-function of rank 2.
     *
     * @param val value to be lifted.
     * @param <A> type of value.
     * @return lifted value.
     */
    static <A> Ix2D<A> lift(final A val) {
        return (i1, i2) -> val;
    }

    /**
     * Lifts result of the given function into an indexing-function of rank 2.
     *
     * @param fn  function whose result is to be lifted.
     * @param <A> type of function parameter.
     * @param <R> type of function result.
     * @return function with lifted result.
     */
    static <A, R> Fn1<A, Ix2D<R>> liftFn(final Fn1<? super A, ? extends R> fn) {
        return val -> (i1, i2) -> fn.apply(val);
    }
}
