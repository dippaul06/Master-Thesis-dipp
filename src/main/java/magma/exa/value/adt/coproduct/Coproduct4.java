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
import magma.exa.adt.typelevel.nat.$4;
import magma.exa.base.contract.Assert;
import magma.exa.control.function.Fn1;

/**
 * Generalized coproduct of 4 heterogeneous typed choices.
 *
 * @param <A> type of the 1st case.
 * @param <B> type of the 2nd case.
 * @param <C> type of the 3rd case.
 * @param <D> type of the 4th case.
 */
public interface Coproduct4<A, B, C, D> extends Arity<$4>,
        Coproduct<$4, Coproduct4<A, B, C, D>> {

    /**
     * Case analysis for {@link Coproduct4}.
     *
     * @param <A> type of the 1st case.
     * @param <B> type of the 2nd case.
     * @param <C> type of the 3rd case.
     * @param <D> type of the 4th case.
     * @param <R> type of result for all cases.
     */
    interface Cases<A, B, C, D, R> extends Coproduct3.Cases<A, B, C, R> {
        R _4(D value);
    }

    /**
     * Returns the arity of this coproduct.
     */
    @Override
    default $4 arity() {
        return $4.nat;
    }

    /**
     * Returns the result of applying the function
     * corresponding to the matching case.
     *
     * @param case1 function {@code A -> R}.
     * @param case2 function {@code B -> R}.
     * @param case3 function {@code C -> R}.
     * @param case4 function {@code D -> R}.
     * @param <R>   type of result of all case functions.
     * @return result of the matching case function.
     */
    <R> R match(Fn1<? super A, ? extends R> case1,
                Fn1<? super B, ? extends R> case2,
                Fn1<? super C, ? extends R> case3,
                Fn1<? super D, ? extends R> case4);

    /**
     * Returns the result of the given case analysis.
     *
     * @param cases to be matched.
     * @param <R>   type of result of all analyzed cases.
     * @return result of the matching case.
     */
    default <R> R match(final Cases<A, B, C, D, R> cases) {
        return match(
                cases::_1,
                cases::_2,
                cases::_3,
                cases::_4
        );
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 1st function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D>
    Fn1<A, R> _1(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4)
    {
        Assert.notNull(cse2, cse3, cse4);
        return cse1::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 2nd function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D>
    Fn1<B, R> _2(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4)
    {
        Assert.notNull(cse1, cse3, cse4);
        return cse2::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 3rd function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D>
    Fn1<C, R> _3(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4)
    {
        Assert.notNull(cse1, cse2, cse4);
        return cse3::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 4th function as matching 
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D>
    Fn1<D, R> _4(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4)
    {
        Assert.notNull(cse1, cse2, cse3);
        return cse4::onApply;
    }
}
