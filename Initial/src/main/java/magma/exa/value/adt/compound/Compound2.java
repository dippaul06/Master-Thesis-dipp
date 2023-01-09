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
import magma.exa.adt.typelevel.nat.$2;
import magma.exa.value.adt.slot.Slot2;

/**
 * Compounded value structure consisting of 2 different typed slots.
 *
 * @param <N> type-level arity.
 * @param <A> type of the 1st slot.
 * @param <B> type of the 2nd slot.
 */
public @Mixin interface Compound2<N extends $2, A, B>
        extends Compound1<N, A>, Slot2<N, B> {

    // ----------------------------------------------------------
    //  COMPOUND-2.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Compound2}.
     *
     * @param <A> type of the 1st slot.
     * @param <B> type of the 2nd slot.
     * @param <R> type of assignment.
     */
    @Mixin interface Assignable<A, B, R extends Compound2<?, A, B>>
            extends Compound1.Assignable<A, R>, Slot2.Assignable<B, R> {
    }
}
