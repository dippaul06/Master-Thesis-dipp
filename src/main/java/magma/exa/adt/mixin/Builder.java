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

package magma.exa.adt.mixin;

/**
 * Mixin type that designates a builder type which encapsulates
 * multi-step preparation and instantiation of a type {@code A}.
 *
 * @param <A> type to be instantiated.
 */
public @Mixin interface Builder<A> {

    /**
     * Returns the constructed instance of type {@code A}.
     */
    A build();

    // ----------------------------------------------------------

    /**
     * Refined mixin type that supports cascading operations along
     * subtype hierarchies of Builders via F-bounded quantification
     * of type {@code T}.
     *
     * @param <A> type of value to be constructed.
     * @param <T> F-bound parameter.
     */
    @Mixin interface Self<A, T extends Self<A, T>>
            extends Builder<A>, Mixin.Self<T> {

        /*
         * Performs the given action to inspect current builder state.
         * Primary intention is use for debugging purposes.
         *
         * @param action to be performed on current builder state.
         * @return 'this' of type {@code T}.
         */
        /*default T inspect(final Fn1.Consumer<? super T> action) {
            final var self = self();
            action.accept(self);
            return self;
        }*/
    }
}
