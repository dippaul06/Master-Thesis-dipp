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
import magma.exa.adt.typelevel.nat.$8;
import magma.exa.value.adt.slot.Slot8;

/**
 * Compounded value structure consisting of 8 different typed slots.
 *
 * @param <N> type-level arity.
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 * @param <C> type of the 3rd slot.
 * @param <D> type of the 4th slot.
 * @param <E> type of the 5th slot.
 * @param <F> type of the 6th slot.
 * @param <G> type of the 7th slot.
 * @param <H> type of the 8th slot.
 */
public @Mixin interface Compound8<N extends $8, A, B, C, D, E, F, G, H>
        extends Compound7<N, A, B, C, D, E, F, G>, Slot8<N, H> {

    // ----------------------------------------------------------
    //  COMPOUND-8.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Compound8}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <C> type of the 3rd slot.
     * @param <D> type of the 4th slot.
     * @param <E> type of the 5th slot.
     * @param <F> type of the 6th slot.
     * @param <G> type of the 7th slot.
     * @param <H> type of the 8th slot.
     * @param <R> type of assignment.
     */
    @Mixin interface Assignable<A, B, C, D, E, F, G, H, R extends Compound8<?, A, B, C, D, E, F, G, H>>
            extends Compound7.Assignable<A, B, C, D, E, F, G, R>, Slot8.Assignable<H, R> {
    }
}
