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

package magma.exa.value;

import magma.exa.control.function.Fn0;

/**
 * A lazy value.
 *
 *
 * @param <A> type of value.
 */
public interface Lazy<A> extends Fn0<A> {

    /**
     * Constructs a value whose content is a calculation deferred
     * until the first access; this is done with memoization to be
     * strict on subsequent accesses.
     *
     * @param thunk deferred computation until first access.
     * @param <A> type of value.
     * @return lazy value accessor.
     */
    static <A> Lazy<A> of(final Fn0<? extends A> thunk) {
        java.util.Objects.requireNonNull(thunk);
        final class Accessor implements Lazy<A> {
            private volatile boolean initialized = false;
            private A value;
            @Override
            public A onApply() throws Throwable {
                if (!initialized) {
                    synchronized (this) {
                        if (!initialized) {
                            value = thunk.apply();
                            initialized = true;
                        }
                    }
                }
                return value;
            }
        }
        return new Accessor();
    }
}
