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

import magma.exa.base.Narrow;
import magma.exa.value.Lazy;
import magma.exa.value.scalar.I32;

import java.lang.annotation.*;

/**
 * Annotation that is to be used on interface type definitions,
 * if this type is intended to be 'mixed' into the definition of
 * an 'primary type'.
 * <p>
 * Mixin interface types are referred to as » Mixin «, because they
 * encapsulate a self-contained fragment of protocol
 * that can be orthogonally mixed with other fragments to compose
 * protocols  in a unified, reusable manner.
 * <p>
 * Mixin types are then be implemented (or adopted) by a primary
 * type to provide an actual implementation of those requirements.
 * Mixin types shapes are described as being » included « rather
 * than 'inherited'.
 * <p>
 * Mixins support definition of primary types via mixin composition
 * in a uniform and consistent manner, reducing complexity through
 * reuse of structural and behavioural blueprints that generalize
 * across a wide variety of types.
 * <p>
 * For instance, {@link Comparable} interface of the JDK, which
 * allows a class type to declare that instances are ordered with
 * respect to other mutually comparable instances.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Mixin {

    // ----------------------------------------------------------
    //  MIXIN.SELF
    // ----------------------------------------------------------

    /**
     * Mixin type that provides a parametric type approximation to
     * the 'self'-type, i.e. a constrained 'this' reference to a
     * self-referential generic type.
     * <p>
     * Self expresses F-bounded polymorphism, which parameterizes a
     * type over its own subtypes, which is a weaker constraint than
     * what the user usually wants, which is a way to say 'my type',
     * which cannot be expressed exactly by subtyping.
     *
     * @param <T> F-bound parameter.
     */
    @Mixin interface Self<T extends Self<T>> {

        /**
         * Returns 'this' narrowed to type {@code T}.
         */
        default T self() {
            return Narrow.cast(this);
        }
    }

    /// PROPERTY MIXINS

    // ----------------------------------------------------------
    //  MIXIN.IDENTITY
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a {@link #id()} property, which is
     * used to impose an externalized identity which is different
     * to the intrinsic {@link Object} identity of the primary type.
     */
    @Mixin interface Identity {

        /**
         * Returns a value of type {@code long}.
         */
        long id();
    }

    // ----------------------------------------------------------
    //  MIXIN.NAME
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a {@link #name()} property.
     */
    @Mixin interface Name {

        /**
         * Returns the name.
         */
        String name();
    }

    // ----------------------------------------------------------
    //  MIXIN.TYPE
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a {@link #type()} property.
     *
     * @param <T> type of {@code type}.
     */
    @Mixin interface Type<T> {

        /**
         * Returns the 'type' of type {@code T}.
         */
        T type();
    }

    // ----------------------------------------------------------
    //  MIXIN.KEY
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a {@link #key()} property.
     *
     * @param <K> type of key.
     */
    @Mixin interface Key<K> {

        /**
         * Returns the key of type {@code K}.
         */
        K key();
    }

    // ----------------------------------------------------------
    //  MIXIN.VALUE
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a {@link #value()} property.
     *
     * @param <A> type of value.
     */
    @Mixin interface Value<A> {

        /**
         * Returns the value of type {@code A}.
         */
        A value();
    }

    // ----------------------------------------------------------
    //  MIXIN.LENGTH
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a {@link #length()} property.
     */
    @Mixin interface Length {

        /**
         * Returns the length of type {@code int}.
         */
        int length();
    }

    // ----------------------------------------------------------
    //  MIXIN.COUNT
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a {@link #count()} property.
     */
    @Mixin interface Count {

        /**
         * Returns the count of type {@code long}.
         */
        long count();
    }

    // ----------------------------------------------------------
    //  MIXIN.CAPACITY
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a {@link #capacity()} property.
     */
    @Mixin interface Capacity {

        /**
         * Returns the capacity of type {@code long}.
         */
        long capacity();
    }

    // ----------------------------------------------------------
    //  MIXIN.ORDINAL
    // ----------------------------------------------------------

    /**
     * Mixin type to expose an assigned » ordinal «.
     */
    @Mixin interface Ordinal {

        /**
         * Returns the ordinal.
         */
        int ordinal();

        @Mixin interface _1 extends Ordinal {
            default int ordinal() { return 1; }
        }

        @Mixin interface _2 extends Ordinal {
            default int ordinal() { return 2; }
        }

        @Mixin interface _3 extends Ordinal {
            default int ordinal() { return 3; }
        }

        @Mixin interface _4 extends Ordinal {
            default int ordinal() { return 4; }
        }

        @Mixin interface _5 extends Ordinal {
            default int ordinal() { return 5; }
        }

        @Mixin interface _6 extends Ordinal {
            default int ordinal() { return 6; }
        }

        @Mixin interface _7 extends Ordinal {
            default int ordinal() { return 7; }
        }

        @Mixin interface _8 extends Ordinal {
            default int ordinal() { return 8; }
        }

        // ----------------------------------------------------------
        //  ORDINAL.INDICATOR
        // ----------------------------------------------------------

        /**
         * Ordinal Indicator. A group of characters following a digit
         * and indicating an ordinal number, e.g. '1st', '2nd', '3rd'.
         */
        enum Indicator implements I32.To<String> {
            st, nd, rd, th;

            /**
             * Returns the ordinal indicator string for the given ordinal number.
             *
             * @param ordinal number.
             * @return indicator string.
             */
            @Override
            public String onApply(final int ordinal) {
                return String.format("%d%s", ordinal, name());
            }

            /**
             * Returns the corresponding ordinal indicator.
             *
             * @param ordinal whose indicator is to be returned.
             * @return ordinal indicator string.
             */
            public static String toString(final int ordinal) {
                final int j = ordinal % 10, k = ordinal % 100;
                if (j == 1 && k != 11) return st.apply(ordinal);
                if (j == 2 && k != 12) return nd.apply(ordinal);
                if (j == 3 && k != 13) return rd.apply(ordinal);
                return th.apply(ordinal);
            }
        }
    }

    /// STRUCTURAL-RELATION MIXINS

    // ----------------------------------------------------------
    //  MIXIN.OWNER
    // ----------------------------------------------------------

    /**
     * Mixin type that declares a 'owner' relationship.
     *
     * @param <T> type of owner.
     */
    @Mixin interface Owner<T> {

        /**
         * Returns an owner.
         */
        T owner();
    }

    // ----------------------------------------------------------
    //  MIXIN.COMPOSITE
    // ----------------------------------------------------------

    /**
     * Mixin type that declares
     *
     * @param <C> type of component.
     */
    @Mixin interface Composite<C extends Component<?>> {

        /**
         * Returns a sequence of components.
         */
        Iterable<C> components();
    }

    // ----------------------------------------------------------
    //  MIXIN.COMPONENT
    // ----------------------------------------------------------

    /**
     * Mixin type that
     *
     * @param <C> type of owner.
     */
    @Mixin interface Component<C extends Composite<?>> extends Owner<C> {

        /**
         * Returns the component owner.
         */
        C owner();
    }

    // ----------------------------------------------------------
    //  MIXIN.IS-EMPTY
    // ----------------------------------------------------------

    /**
     * Mixin type that declares (existential) queries about membership.
     */
    @Mixin interface IsEmpty { // Domain

        /**
         * Determines whether nothing is contained.
         */
        boolean isEmpty();

        /**
         * Determines whether something is contained.
         */
        default boolean isPresent() {
            return isEmpty();
        }
    }

    /// BEHAVIORAL MIXINS

    // ----------------------------------------------------------
    //  MIXIN.INITIALIZABLE
    // ----------------------------------------------------------

    /**
     * Mixin type that introduces an explicit initialization-state.
     * E.g. acquire unmanaged resources before use.
     */
    @Mixin interface Initializable {

        /**
         * Handler intended for prepare-logic.
         */
        default void initialize() {
            // ...to be overriden.
        }
    }

    // ----------------------------------------------------------
    //  MIXIN.RESETTABLE
    // ----------------------------------------------------------

    /**
     * Mixin type that introduces an explicit reset-state.
     * E.g. reset a data structure traversal to repeat a pass.
     */
    @Mixin interface Resettable {

        /**
         * Operation that requires resetting to the initial state,
         * True is returned if the reset was successful.
         */
        default boolean reset() {
            return false;
        }
    }

    // ----------------------------------------------------------
    //  MIXIN.DISPOSABLE
    // ----------------------------------------------------------

    /**
     * Mixin type that introduces an explicit disposal-state.
     * E.g. release unmanaged resources after use.
     */
    @Mixin interface Disposable {

        /**
         * Handler intended for cleanup-logic.
         */
        default void dispose() {
            // ...to be overriden.
        }
    }

    // ----------------------------------------------------------
    //  MIXIN.DISPOSABLE
    // ----------------------------------------------------------

    /**
     * Mixin type that declares an operation to produce deep copies of itself.
     *
     * @param <T> F-bound parameter.
     */
    @Mixin interface Copyable<T extends Copyable<T>> extends Self<T> {

        /**
         * Returns a deep copy of itself.
         */
        T copy();

        /**
         * Returns a function that produces a copy of the
         * given {@code self}-instance when its required.
         *
         * @param self instance to produce a deep from.
         * @param <T>  type of self.
         * @return function that produces a deep copy when required.
         */
        static <T extends Copyable<T>> Lazy<T> apply(final T self) {
            return self::copy;
        }
    }

    // ----------------------------------------------------------

    // Stateful
    // Stateless
    // Resource
    // Artifact
    // Active
    // Passiv
}