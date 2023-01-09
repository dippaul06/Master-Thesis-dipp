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
import magma.exa.adt.typelevel.nat.$3;
import magma.exa.control.function.Fn1;

/**
 * Indexing function for 3-dimensional data (e.g. tensor)
 * to locate an R-Value or L-Value components.
 *
 * @param <A> type of value.
 */
public interface Ix3D<A> extends Rank<$3>, Ix.Of3<$3, A> {

    /**
     * Fixes the rank to 3.
     */
    @Override
    default $3 rank() {
        return $3.nat;
    }

    /**
     * Partially applies this function by passing the first 2 index-components.
     */
    @Override
    default Ix1D<A> index(final long i1, final long i2) {
        return i3 -> index(i1, i2, i3);
    }

    /**
     * Partially applies this function by passing the first index-component.
     */
    @Override
    default Ix2D<A> index(final long i1) {
        return (i2, i3) -> index(i1, i2, i3);
    }

    /**
     * Lifts the given value into an indexing-function of rank 3.
     *
     * @param val value to be lifted.
     * @param <A> type of value.
     * @return lifted value.
     */
    static <A> Ix3D<A> lift(final A val) {
        return (i1, i2, i3) -> val;
    }

    /**
     * Lifts result of the given function into an indexing-function of rank 3.
     *
     * @param fn  function whose result is to be lifted.
     * @param <A> type of function parameter.
     * @param <R> type of function result.
     * @return function with lifted result.
     */
    static <A, R> Fn1<A, Ix3D<R>> liftFn(final Fn1<? super A, ? extends R> fn) {
        return val -> (i1, i2, i3) -> fn.apply(val);
    }
}
