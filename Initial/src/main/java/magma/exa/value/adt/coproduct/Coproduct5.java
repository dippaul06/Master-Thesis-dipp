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
import magma.exa.adt.typelevel.nat.$5;
import magma.exa.base.contract.Assert;
import magma.exa.control.function.Fn1;

/**
 * Generalized coproduct of 5 heterogeneous typed choices.
 *
 * @param <A> type of the 1st case.
 * @param <B> type of the 2nd case.
 * @param <C> type of the 3rd case.
 * @param <D> type of the 4th case.
 * @param <E> type of the 5th case.
 */
public interface Coproduct5<A, B, C, D, E> extends Arity<$5>,
        Coproduct<$5, Coproduct5<A, B, C, D, E>> {

    /**
     * Case analysis for {@link Coproduct5}.
     *
     * @param <A> type of the 1st case.
     * @param <B> type of the 2nd case.
     * @param <C> type of the 3rd case.
     * @param <D> type of the 4th case.
     * @param <E> type of the 5th case.
     * @param <R> type of result for all cases.
     */
    interface Cases<A, B, C, D, E, R> extends Coproduct4.Cases<A, B, C, D, R> {
        R _5(E value);
    }

    /**
     * Returns the arity of this coproduct.
     */
    @Override
    default $5 arity() {
        return $5.nat;
    }

    /**
     * Returns the result of applying the function
     * corresponding to the matching case.
     *
     * @param case1 function {@code A -> R}.
     * @param case2 function {@code B -> R}.
     * @param case3 function {@code C -> R}.
     * @param case4 function {@code D -> R}.
     * @param case5 function {@code E -> R}.
     * @param <R>   type of result of all case functions.
     * @return result of the matching case function.
     */
    <R> R match(Fn1<? super A, ? extends R> case1,
                Fn1<? super B, ? extends R> case2,
                Fn1<? super C, ? extends R> case3,
                Fn1<? super D, ? extends R> case4,
                Fn1<? super E, ? extends R> case5);

    /**
     * Returns the result of the given case analysis.
     *
     * @param cases to be matched.
     * @param <R>   type of result of all analyzed cases.
     * @return result of the matching case.
     */
    default <R> R match(final Cases<A, B, C, D, E, R> cases) {
        return match(
                cases::_1,
                cases::_2,
                cases::_3,
                cases::_4,
                cases::_5
        );
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 1st function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E>
    Fn1<A, R> _1(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5)
    {
        Assert.notNull(cse2, cse3, cse4, cse5);
        return cse1::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 2nd function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E>
    Fn1<B, R> _2(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5)
    {
        Assert.notNull(cse1, cse3, cse4, cse5);
        return cse2::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 3rd function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E>
    Fn1<C, R> _3(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5)
    {
        Assert.notNull(cse1, cse2, cse4, cse5);
        return cse3::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 4th function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E>
    Fn1<D, R> _4(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5)
    {
        Assert.notNull(cse1, cse2, cse3, cse5);
        return cse4::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 5th function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E>
    Fn1<E, R> _5(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5)
    {
        Assert.notNull(cse1, cse2, cse3, cse4);
        return cse5::onApply;
    }
}
