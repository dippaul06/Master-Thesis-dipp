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
import magma.exa.adt.typelevel.nat.$5;

/**
 * Declares a parametric slot at position 5 via an index-named accessor.
 * Slots are elementary shapes used to form compounded value structures.
 *
 * @param <N> type-level arity.
 * @param <E> type of slot value.
 */
public @Mixin interface Slot5<N extends $5, E> {

    /**
     * Returns the value of the 5th slot.
     */
    E _5();

    // ----------------------------------------------------------
    //  SLOT-5.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Slot5} by declaring
     * an index-named assignment operation.
     *
     * @param <E> type of  value to be assigned.
     * @param <R> type of result to be returned.
     */
    @Mixin interface Assignable<E, R extends Slot5<?, E>> {

        /**
         * Returns the result of assigning the given value to this slot.
         *
         * @param value to be assigned of type {@link E}.
         * @return result of type {@link R}.
         */
        R _5(E value);
    }
}
