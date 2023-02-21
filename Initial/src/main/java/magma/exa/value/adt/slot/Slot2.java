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
import magma.exa.adt.typelevel.nat.$2;

/**
 * Declares a parametric slot at position 2 via an index-named accessor.
 * Slots are elementary shapes used to form compounded value structures.
 *
 * @param <N> type-level arity.
 * @param <B> type of slot value.
 */
public @Mixin interface Slot2<N extends $2, B> {

    /**
     * Returns the value of the 2nd slot.
     */
    B _2();

    // ----------------------------------------------------------
    //  SLOT-2.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Slot2} by declaring
     * an index-named assignment operation.
     *
     * @param <B> type of  value to be assigned.
     * @param <R> type of result to be returned.
     */
    @Mixin interface Assignable<B, R extends Slot2<?, B>> {

        /**
         * Returns the result of assigning the given value to this slot.
         *
         * @param value to be assigned of type {@link B}.
         * @return result of type {@link R}.
         */
        R _2(B value);
    }
}
