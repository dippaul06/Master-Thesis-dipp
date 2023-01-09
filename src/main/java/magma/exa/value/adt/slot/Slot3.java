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
import magma.exa.adt.typelevel.nat.$3;

/**
 * Declares a parametric slot at position 3 via an index-named accessor.
 * Slots are elementary shapes used to form compounded value structures.
 *
 * @param <N> type-level arity.
 * @param <C> type of slot value.
 */
public @Mixin interface Slot3<N extends $3, C> {

    /**
     * Returns the value of the 3rd slot.
     */
    C _3();

    // ----------------------------------------------------------
    //  SLOT-3.ASSIGNABLE
    // ----------------------------------------------------------

    /**
     * Assignable form of {@link Slot3} by declaring
     * an index-named assignment operation.
     *
     * @param <C> type of  value to be assigned.
     * @param <R> type of result to be returned.
     */
    @Mixin interface Assignable<C, R extends Slot3<?, C>> {

        /**
         * Returns the result of assigning the given value to this slot.
         *
         * @param value to be assigned of type {@link C}.
         * @return result of type {@link R}.
         */
        R _3(C value);
    }
}
