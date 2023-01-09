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

package magma.exa.value.adt.coproduct;

import magma.exa.adt.typelevel.nat.$1;
import magma.exa.adt.typelevel.shape.Arity;
import magma.exa.control.function.Fn0;
import magma.exa.control.function.Fn1;

/**
 * Specialized Coproduct to represent an optional case.
 *
 * @param <A> type of the optional case.
 */
public interface Coproduct1<A> extends Arity<$1>,
        Coproduct<$1, Coproduct1<A>> {

    /**
     * Case analysis for {@link Coproduct1}.
     *
     * @param <A> type of the 'some' case.
     * @param <R> type of result of both cases.
     */
    interface Cases<A, R> {
        R some(A value);
        R none();
    }

    /**
     * Returns the arity of this coproduct.
     */
    @Override
    default $1 arity() {
        return $1.nat;
    }

    /**
     * Returns the result of applying the function
     * corresponding to the matching case.
     *
     * @param some case function {@code A  -> R}.
     * @param none case function {@code () -> R}.
     * @param <R>   type of result of both case functions.
     * @return result of the matching case function.
     */
    <R> R match(Fn1<? super A, ? extends R> some,
                Fn0<? extends R> none);

    /**
     * Returns the result of the given case analysis.
     *
     * @param cases to be matched.
     * @param <R>   type of result of all analyzed cases.
     * @return result of the matching case.
     */
    default <R> R match(final Cases<A, R> cases) {
        return match(cases::some, cases::none);
    }
}
