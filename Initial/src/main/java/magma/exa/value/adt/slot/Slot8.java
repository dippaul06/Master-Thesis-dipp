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
import magma.exa.adt.typelevel.nat.$8;

/**
 * Declares a parametric slot at position 8 via an index-named accessor.
 * Slots are elementary shapes used to form compounded value structures.
 *
 * @param <N> type-level arity.
 * @param <H> type of slot value.
 */
public @Mixin interface Slot8<N extends $8, H> {

    /**
     * Returns the value of the 8th slot.
     */
    H _8();

    // ----------------------------------------------------------
    //  SLOT-8.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Slot8} by declaring
     * an index-named assignment operation.
     *
     * @param <H> type of  value to be assigned.
     * @param <R> type of result to be returned.
     */
    @Mixin interface Assignable<H, R extends Slot8<?, H>> {

        /**
         * Returns the result of assigning the given value to this slot.
         *
         * @param value to be assigned of type {@link H}.
         * @return result of type {@link R}.
         */
        R _8(H value);
    }
}
