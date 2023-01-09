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

import java.util.Objects;
import java.util.Optional;

/**
 * Mixin type that declares the shape of a {@link Enum} constant.
 */
public @Mixin interface Constant<S extends Enum<S> & Constant<S>>
        extends Mixin.Self<S>, Mixin.Owner<Class<S>>, Mixin.Name
{
	/**
	 * Returns the ordinal of this constant.
	 */
	default int ordinal() {
		return self().ordinal();
	}

	/**
	 * Returns the name of this constant.
	 */
	default String name() {
		return self().name();
	}

	/**
	 * Returns the declaring enumeration.
	 */
	default Class<S> owner() {
		return self().getDeclaringClass();
	}

    /**
     * Returns the number of constants defined on the enum subclass.
     */
    default int length() {
        return values(self()).length;
    }

    /**
     * Obtain all constants defined on the enum subclass.
     */
	default S[] constants() {
	    return Constant.values(self());
    }

    /**
     * Returns the enum constant with  all constants defined on the enum subclass.
     */
    default S constant(final int ordinal) {
        return Constant.values(self())[ordinal];
    }

    /**
     * Returns the enum constant of this enum subclass with the given name. The
     * name must match exactly an identifier used to declare an enum constant in
     * this type. (Extraneous whitespace characters are not permitted.)
     *
     * Note that for a particular enum type {@code T}, the implicitly declared
     * {@code public static T valueOf(String)} method on that enum may be used
     * instead of this method to map from a name to the corresponding enum constant.
     * All the constants of an enum type can be obtained by calling the implicit
     * {@code public static T[] values()} method of that type.
     *
     * @param name of the constant to be returned.
     * @return some optional constant or the empty option.
     */
    default Optional<S> constant(final String name) {
        Objects.requireNonNull(name);
        final var constants = constants();
        for (final var constant : constants) {
            if (constant.name().equals(name)) {
                return Optional.of(constant);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the singleton enum constant of this enum subclass if only this
     * exists. If no or more constants exist, null is returned.
     */
    default S singleton() {
        final S[] cst; return ((cst = constants()).length == 1) ? cst[0] : null;
    }

    // ----------------------------------------------------------

    private static <C extends Enum<C> & Constant<C>> C[] values(final C enumeration) {
        return enumeration.getDeclaringClass().getEnumConstants();
	}
}
