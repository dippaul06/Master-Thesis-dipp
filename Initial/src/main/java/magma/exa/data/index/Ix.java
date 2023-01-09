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

package magma.exa.data.index;

import magma.exa.adt.typelevel.nat.$1;
import magma.exa.adt.typelevel.nat.$2;
import magma.exa.adt.typelevel.nat.$3;
import magma.exa.adt.typelevel.signature.$Fn;

/**
 * Common supertype of indexing functions.
 *
 * @param <A> type of value.
 */
public interface Ix<A> extends $Fn {

    // ----------------------------------------------------------

    /**
     * Index-function protocol of rank 1.
     *
     * @param <D> type-level rank.
     * @param <A> type of value.
     */
    interface Of1<D extends $1, A> extends Ix<A> {

        /**
         * Index application by passing 1 index-component.
         *
         * @param i1 1st index-component.
         * @return value of type {@code A}.
         */
        A index(long i1);
    }

    // ----------------------------------------------------------

    /**
     * Index-function protocol of rank 2.
     *
     * @param <D> type-level rank.
     * @param <A> type of value.
     */
    interface Of2<D extends $2, A> extends Of1<D, Of1<?, A>> {

        /**
         * Index application by passing 2 index-components.
         *
         * @param i1 1st index-component.
         * @param i2 2nd index-component.
         * @return value of type {@code A}.
         */
        A index(long i1, long i2);

        /**
         * Partial application by passing the first index-component.
         *
         * @param i1 1st index-component.
         * @return index of rank 1.
         */
        @Override Of1<?, A> index(long i1);
    }

    // ----------------------------------------------------------

    /**
     * Index-function protocol of rank 3.
     *
     * @param <D> type-level rank.
     * @param <A> type of value.
     */
    interface Of3<D extends $3, A> extends Of2<D, Of1<?, A>> {

        /**
         * Index application by passing 3 index-components.
         *
         * @param i1 1st index-component.
         * @param i2 2nd index-component.
         * @param i3 3rd index-component.
         * @return value of type {@code A}.
         */
        A index(long i1, long i2, long i3);

        /**
         * Partial application by passing the first 2 index-components.
         *
         * @param i1 1st index-component.
         * @param i2 2nd index-component.
         * @return index of rank 1.
         */
        @Override Of1<?, A> index(long i1, long i2);

        /**
         * Partial application by passing the first index-component.
         *
         * @param i1 1st index-component.
         * @return index of rank 2.
         */
        @Override Of2<?, A> index(long i1);
    }
}
