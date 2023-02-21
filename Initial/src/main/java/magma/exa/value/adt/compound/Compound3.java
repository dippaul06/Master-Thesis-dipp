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
import magma.exa.adt.typelevel.nat.$3;
import magma.exa.value.adt.slot.Slot3;

/**
 * Compounded value structure consisting of 3 different typed slots.
 *
 * @param <N> type-level arity.
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 * @param <C> type of the 3rd slot.
 */
public @Mixin interface Compound3<N extends $3, A, B, C>
        extends Compound2<N, A, B>, Slot3<N, C> {

    // ----------------------------------------------------------
    //  COMPOUND-3.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Compound3}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <C> type of the 3rd slot.
     * @param <R> type of assignment.
     */
    @Mixin interface Assignable<A, B, C, R extends Compound3<?, A, B, C>>
            extends Compound2.Assignable<A, B, R>, Slot3.Assignable<C, R> {
    }
}
