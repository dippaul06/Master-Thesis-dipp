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

package magma.exa.base;

/**
 * Widening reference conversion treats expressions of a reference type
 * {@code S} as expressions of a different reference type {@code T},
 * where S » IS A SUBTYPE « of T.
 * <p>
 * Required when dealing with parametric types that are invariant in
 * their parameters and a cast is required for compatibility reasons.
 *
 * @param <S>   subtype of {@code T}.
 * @param <T> supertype if {@code S},
 */
public final class Widen<S extends T, T> {

    /**
     * Returns the operand widened to type {@code T}.
     *
     * @param s operand to be windend.
     * @return operand.
     */
    public T apply(final S s) {
        return s;
    }

    /**
     * Return the given value of type {@code S} widened to type {@code T}.
     *
     * @param s value of type {@code S},
     * @param <S>   subtype of {@code T}.
     * @param <T> supertype if {@code S},
     * @return value widened to type {@code T}.
     */
    @SuppressWarnings("unchecked")
    public static <S extends T, T> T cast(final S s) {
        return ((Widen<S, T>) operator).apply(s);
    }

    /**
     * Returns widen-cast operator as first-class.
     *
     * @param <S> supertype of {@code T}.
     * @param <T>   subtype if {@code S}.
     * @return cast operator.
     */
    @SuppressWarnings("unchecked")
    public static <S extends T, T> Widen<S, T> cast() {
        return (Widen<S, T>) operator;
    }

    /** Singleton. */
    private Widen() { }
    static { operator = new Widen<>(); }
    private static final Widen<?, ?> operator;
}
