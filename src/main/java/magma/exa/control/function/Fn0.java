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

package magma.exa.control.function;

import magma.exa.adt.typelevel.nat.$0;
import magma.exa.control.adt.function.IFn0;
import magma.exa.value.Unit;

/**
 * Function that takes no argument and returns a result of type {@code R}.
 *
 * @param <R> type of function result.
 */
@FunctionalInterface
public interface Fn0<R> extends IFn0<$0, R> {

    /**
     * Returns the function arity.
     */
    @Override
    default $0 arity() {
        return $0.nat;
    }

    // ----------------------------------------------------------
    //  FN0.PREDICATE
    // ----------------------------------------------------------

    /**
     * A lazy evaluated predicate (boolean-valued function).
     */
    @FunctionalInterface
    interface Predicate extends IPredicate<$0>, Fn0<Boolean> {
    }

    // ----------------------------------------------------------
    //  FN1.CONSUMER
    // ----------------------------------------------------------

    /**
     * A lazy executed side effect operation.
     */
    @FunctionalInterface
    interface Consumer extends IConsumer<$0>, Fn0<Unit> {
    }
}
