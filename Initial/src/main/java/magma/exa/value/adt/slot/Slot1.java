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

package magma.exa.value.adt.slot;

import magma.exa.adt.mixin.Mixin;
import magma.exa.adt.typelevel.nat.$1;

/**
 * Declares a parametric slot at position 1 via an index-named accessor.
 * Slots are elementary shapes used to form compounded value structures.
 *
 * @param <N> type-level arity.
 * @param <A> type of slot value.
 */
public @Mixin interface Slot1<N extends $1, A> {

    /**
     * Returns the slot value.
     */
    A _1();

    // ----------------------------------------------------------
    //  SLOT-1.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Slot1} by declaring
     * an index-named assignment operation.
     *
     * @param <A> type of  value to be assigned.
     * @param <R> type of result to be returned.
     */
    @Mixin interface Assignable<A, R extends Slot1<?, A>> {

        /**
         * Returns the result of assigning the given value to this slot.
         *
         * @param value to be assigned of type {@link A}.
         * @return result of type {@link R}.
         */
        R _1(A value);
    }
}
