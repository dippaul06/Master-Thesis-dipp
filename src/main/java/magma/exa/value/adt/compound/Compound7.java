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
import magma.exa.adt.typelevel.nat.$7;
import magma.exa.value.adt.slot.Slot7;

/**
 * Compounded value structure consisting of 7 different typed slots.
 *
 * @param <N> type-level arity.
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 * @param <C> type of the 3rd slot.
 * @param <D> type of the 4th slot.
 * @param <E> type of the 5th slot.
 * @param <F> type of the 6th slot.
 * @param <G> type of the 7th slot.
 */
public @Mixin interface Compound7<N extends $7, A, B, C, D, E, F, G>
        extends Compound6<N, A, B, C, D, E, F>, Slot7<N, G> {

    // ----------------------------------------------------------
    //  COMPOUND-7.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Compound7}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <C> type of the 3rd slot.
     * @param <D> type of the 4th slot.
     * @param <E> type of the 5th slot.
     * @param <F> type of the 6th slot.
     * @param <G> type of the 7th slot.
     * @param <R> type of assignment.
     */
    @Mixin interface Assignable<A, B, C, D, E, F, G, R extends Compound7<?, A, B, C, D, E, F, G>>
            extends Compound6.Assignable<A, B, C, D, E, F, R>, Slot7.Assignable<G, R> {
    }
}
