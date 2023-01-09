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

import magma.exa.adt.typelevel.shape.Arity;
import magma.exa.adt.typelevel.nat.$2;
import magma.exa.base.contract.Assert;
import magma.exa.control.function.Fn1;

/**
 * Generalized coproduct of 2 heterogeneous typed choices.
 *
 * @param <A> type of the 1st case.
 * @param <B> type of the 2nd case.
 */
public interface Coproduct2<A, B> extends Arity<$2>,
        Coproduct<$2, Coproduct2<A, B>> {

    /**
     * Case analysis for {@link Coproduct2}.
     *
     * @param <A> type of the 1st case.
     * @param <B> type of the 2nd case.
     * @param <R> type of result for all cases.
     */
    interface Cases<A, B, R> {
        R _1(A value);
        R _2(B value);
    }

    /**
     * Returns the arity of this coproduct.
     */
    @Override
    default $2 arity() {
        return $2.nat;
    }

    /**
     * Returns the result of applying the function
     * corresponding to the matching case.
     *
     * @param case1 function {@code A -> R}.
     * @param case2 function {@code B -> R}.
     * @param <R>   type of result of all case functions.
     * @return result of the matching case function.
     */
    <R> R match(Fn1<? super A, ? extends R> case1,
                Fn1<? super B, ? extends R> case2);

    /**
     * Returns the result of the given case analysis.
     *
     * @param cases to be matched.
     * @param <R>   type of result of all analyzed cases.
     * @return result of the matching case.
     */
    default <R> R match(final Cases<A, B, R> cases) {
        return match(cases::_1, cases::_2);
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 1st function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B>
    Fn1<A, R> _1(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2)
    {
        Assert.notNull(cse2);
        return cse1::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 2nd function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B>
    Fn1<B, R> _2(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2)
    {
        Assert.notNull(cse1);
        return cse2::onApply;
    }
}
