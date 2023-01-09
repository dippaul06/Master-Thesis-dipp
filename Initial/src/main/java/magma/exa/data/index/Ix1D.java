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
import magma.exa.adt.typelevel.nat.$1;
import magma.exa.control.function.Fn1;

/**
 * Indexing function for 1-dimensional data (e.g. vector)
 * to locate an R-Value or L-Value components.
 *
 *     [0]   [1]   [2]   [3]   [4]   [5]   [6]
 *      v     v     v     v     v     v     v
 *   | [A] | [B] | [C] | [D] | [E] | [F] | [G] |
 *   ^     ^     ^     ^     ^     ^     ^     ^
 *  [0]   [1]   [2]   [3]   [4]   [5]   [6]   [7]
 *
 * @param <A> type of value.
 */
public interface Ix1D<A> extends Rank<$1>, Ix.Of1<$1, A> {

    /**
     * Fixes the rank to 1.
     */
    @Override
    default $1 rank() {
        return $1.nat;
    }

    /**
     * Lifts the given value into an indexing-function of rank 1.
     *
     * @param val value to be lifted.
     * @param <A> type of value.
     * @return lifted value.
     */
    static <A> Ix1D<A> lift(final A val) {
        return i1 -> val;
    }

    /**
     * Lifts result of the given function into an indexing-function of rank 1.
     *
     * @param fn  function whose result is to be lifted.
     * @param <A> type of function parameter.
     * @param <R> type of function result.
     * @return function with lifted result.
     */
    static <A, R> Fn1<A, Ix1D<R>> liftFn(final Fn1<? super A, ? extends R> fn) {
        return val -> i1 -> fn.apply(val);
    }
}
