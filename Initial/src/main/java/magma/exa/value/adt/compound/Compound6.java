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

package magma.exa.value.adt.compound;

import magma.exa.adt.mixin.Mixin;
import magma.exa.adt.typelevel.nat.$6;
import magma.exa.value.adt.slot.Slot6;

/**
 * Compounded value structure consisting of 6 different typed slots.
 *
 * @param <N> type-level arity.
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 * @param <C> type of the 3rd slot.
 * @param <D> type of the 4th slot.
 * @param <E> type of the 5th slot.
 * @param <F> type of the 6th slot.
 */
public @Mixin interface Compound6<N extends $6, A, B, C, D, E, F>
        extends Compound5<N, A, B, C, D, E>, Slot6<N, F> {

    // ----------------------------------------------------------
    //  COMPOUND-6.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Compound6}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <C> type of the 3rd slot.
     * @param <D> type of the 4th slot.
     * @param <E> type of the 5th slot.
     * @param <F> type of the 6th slot.
     * @param <R> type of assignment.
     */
    @Mixin interface Assignable<A, B, C, D, E, F, R extends Compound6<?, A, B, C, D, E, F>>
            extends Compound5.Assignable<A, B, C, D, E, R>, Slot6.Assignable<F, R> {
    }
}
