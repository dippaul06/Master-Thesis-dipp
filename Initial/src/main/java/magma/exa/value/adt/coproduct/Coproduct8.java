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
import magma.exa.adt.typelevel.nat.$8;
import magma.exa.base.contract.Assert;
import magma.exa.control.function.Fn1;

/**
 * Generalized coproduct of 8 heterogeneous typed choices.
 *
 * @param <A> type of the 1st case.
 * @param <B> type of the 2nd case.
 * @param <C> type of the 3rd case.
 * @param <D> type of the 4th case.
 * @param <E> type of the 5th case.
 * @param <F> type of the 6th case.
 * @param <G> type of the 7th case.
 * @param <H> type of the 8th case.
 */
public interface Coproduct8<A, B, C, D, E, F, G, H> extends Arity<$8>,
        Coproduct<$8, Coproduct8<A, B, C, D, E, F, G, H>> {

    /**
     * Case analysis for {@link Coproduct8}.
     *
     * @param <A> type of the 1st case.
     * @param <B> type of the 2nd case.
     * @param <C> type of the 3rd case.
     * @param <D> type of the 4th case.
     * @param <E> type of the 5th case.
     * @param <F> type of the 6th case.
     * @param <G> type of the 7th case.
     * @param <H> type of the 8th case.
     * @param <R> type of result for all cases.
     */
    interface Cases<A, B, C, D, E, F, G, H, R> extends Coproduct7.Cases<A, B, C, D, E, F, G, R> {
        R _8(H value);
    }

    /**
     * Returns the arity of this coproduct.
     */
    @Override
    default $8 arity() {
        return $8.nat;
    }

    /**
     * Returns the result of applying the function
     * corresponding to the matching case.
     *
     * @param cse1 function {@code A -> R}.
     * @param cse2 function {@code B -> R}.
     * @param cse3 function {@code C -> R}.
     * @param cse4 function {@code D -> R}.
     * @param cse5 function {@code E -> R}.
     * @param cse6 function {@code F -> R}.
     * @param cse7 function {@code G -> R}.
     * @param cse8 function {@code H -> R}.
     * @param <R>  type of result of all case functions.
     * @return result of the matching case function.
     */
    <R> R match(Fn1<? super A, ? extends R> cse1,
                Fn1<? super B, ? extends R> cse2,
                Fn1<? super C, ? extends R> cse3,
                Fn1<? super D, ? extends R> cse4,
                Fn1<? super E, ? extends R> cse5,
                Fn1<? super F, ? extends R> cse6,
                Fn1<? super G, ? extends R> cse7,
                Fn1<? super H, ? extends R> cse8);

    /**
     * Returns the result of the given case analysis.
     *
     * @param cases to be matched.
     * @param <R>   type of result of all analyzed cases.
     * @return result of the matching case.
     */
    default <R> R match(final Cases<A, B, C, D, E, F, G, H, R> cases) {
        return match(
                cases::_1,
                cases::_2,
                cases::_3,
                cases::_4,
                cases::_5,
                cases::_6,
                cases::_7,
                cases::_8
        );
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 1st function as matching
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E, F, G, H>
    Fn1<A, R> _1(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5,
                 final Fn1<? super F, ? extends R> cse6,
                 final Fn1<? super G, ? extends R> cse7,
                 final Fn1<? super H, ? extends R> cse8)
    {
        Assert.notNull(cse2, cse3, cse4, cse5, cse6, cse7, cse8);
        return cse1::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 2nd function as matching
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E, F, G, H>
    Fn1<B, R> _2(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5,
                 final Fn1<? super F, ? extends R> cse6,
                 final Fn1<? super G, ? extends R> cse7,
                 final Fn1<? super H, ? extends R> cse8)
    {
        Assert.notNull(cse1, cse3, cse4, cse5, cse6, cse7, cse8);
        return cse2::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 3rd function as matching
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E, F, G, H>
    Fn1<C, R> _3(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5,
                 final Fn1<? super F, ? extends R> cse6,
                 final Fn1<? super G, ? extends R> cse7,
                 final Fn1<? super H, ? extends R> cse8)
    {
        Assert.notNull(cse1, cse2, cse3, cse5, cse6, cse7, cse8);
        return cse3::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 4th function as matching
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E, F, G, H>
    Fn1<D, R> _4(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5,
                 final Fn1<? super F, ? extends R> cse6,
                 final Fn1<? super G, ? extends R> cse7,
                 final Fn1<? super H, ? extends R> cse8)
    {
        Assert.notNull(cse1, cse2, cse3, cse5, cse6, cse7, cse8);
        return cse4::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 5th function as matching
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E, F, G, H>
    Fn1<E, R> _5(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5,
                 final Fn1<? super F, ? extends R> cse6,
                 final Fn1<? super G, ? extends R> cse7,
                 final Fn1<? super H, ? extends R> cse8)
    {
        Assert.notNull(cse1, cse2, cse3, cse4, cse6, cse7, cse8);
        return cse5::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 6th function as matching
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E, F, G, H>
    Fn1<F, R> _6(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5,
                 final Fn1<? super F, ? extends R> cse6,
                 final Fn1<? super G, ? extends R> cse7,
                 final Fn1<? super H, ? extends R> cse8)
    {
        Assert.notNull(cse1, cse2, cse3, cse4, cse5, cse6, cse8);
        return cse6::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 7th function as matching
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E, F, G, H>
    Fn1<G, R> _7(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5,
                 final Fn1<? super F, ? extends R> cse6,
                 final Fn1<? super G, ? extends R> cse7,
                 final Fn1<? super H, ? extends R> cse8)
    {
        Assert.notNull(cse1, cse2, cse3, cse4, cse5, cse6, cse8);
        return cse7::onApply;
    }

    // ----------------------------------------------------------

    /**
     * Returns the result of the 8th function as matching
     * case and null-checks all other remaining functions.
     */
    static <R, A, B, C, D, E, F, G, H>
    Fn1<H, R> _8(final Fn1<? super A, ? extends R> cse1,
                 final Fn1<? super B, ? extends R> cse2,
                 final Fn1<? super C, ? extends R> cse3,
                 final Fn1<? super D, ? extends R> cse4,
                 final Fn1<? super E, ? extends R> cse5,
                 final Fn1<? super F, ? extends R> cse6,
                 final Fn1<? super G, ? extends R> cse7,
                 final Fn1<? super H, ? extends R> cse8)
    {
        Assert.notNull(cse1, cse2, cse3, cse4, cse5, cse6, cse7);
        return cse8::onApply;
    }
}
