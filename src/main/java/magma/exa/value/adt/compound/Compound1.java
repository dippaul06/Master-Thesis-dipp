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
import magma.exa.adt.typelevel.shape.Arity;
import magma.exa.adt.typelevel.nat.$1;
import magma.exa.value.adt.slot.Slot1;

/**
 * Compounded value structure consisting of a single typed slot.
 *
 * @param <N> type-level arity.
 * @param <A> type of the 1st slot.
 */
public @Mixin interface Compound1<N extends $1, A> extends Arity<N>, Slot1<N, A> {

    // ----------------------------------------------------------
    //  COMPOUND-1.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Compound1}.
     *
     * @param <A> type of the 1st slot.
     * @param <R> type of assignment.
     */
    @Mixin interface Assignable<A, R extends Compound1<?, A>>
            extends Slot1.Assignable<A, R> {
    }
}
