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
import magma.exa.adt.typelevel.nat.$5;
import magma.exa.value.adt.slot.Slot5;

/**
 * Compounded value structure consisting of 5 different typed slots.
 *
 * @param <N> type-level arity.
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 * @param <C> type of the 3rd slot.
 * @param <D> type of the 4th slot.
 * @param <E> type of the 5th slot.
 */
public @Mixin interface Compound5<N extends $5, A, B, C, D, E>
        extends Compound4<N, A, B, C, D>, Slot5<N, E> {

    // ----------------------------------------------------------
    //  COMPOUND-5.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Compound5}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <C> type of the 3rd slot.
     * @param <D> type of the 4th slot.
     * @param <E> type of the 5th slot.
     * @param <R> type of assignment.
     */
    @Mixin interface Assignable<A, B, C, D, E, R extends Compound5<?, A, B, C, D, E>>
            extends Compound4.Assignable<A, B, C, D, R>, Slot5.Assignable<E, R> {
    }
}
