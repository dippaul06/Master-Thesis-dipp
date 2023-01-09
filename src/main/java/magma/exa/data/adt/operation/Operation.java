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

package magma.exa.data.adt.operation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import magma.exa.adt.mixin.Mixin;
import magma.exa.base.Force;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn2;
import magma.exa.control.function.Predicates;
import magma.exa.control.traversal.Traversable;
import magma.exa.data.Array;
import magma.exa.data.Option;
import magma.exa.data.misc.Sorting;
import magma.exa.value.scalar.Bool;
import magma.exa.value.scalar.C16;
import magma.exa.value.scalar.F32;
import magma.exa.value.scalar.F64;
import magma.exa.value.scalar.I16;
import magma.exa.value.scalar.I32;
import magma.exa.value.scalar.I64;
import magma.exa.value.scalar.I8;

/**
 * Abstract Data Type
 * _______________________
 * ___    |__  __ \__  __/
 * __  /| |_  / / /_  /
 * _  ___ |  /_/ /_  /
 * /_/  |_/_____/ /_/ Operations.
 *
 * {@link Query}
 *      {@link Query.Membership}
 *      {@link Query.Quantify}
 *      {@link Query.FindValue}
 *      {@link Query.FindIndex}
 *      {@link Query.Bulk}
 *          {@link Query.Bulk.Membership}
 *
 * {@link Access}
 *      {@link Access.Value}
 *      {@link Access.Single}
 *      {@link Access.First}
 *      {@link Access.Last}
 *      {@link Access.At}
 *
 * {@link Plus}
 *      {@link Plus.Insert}
 *      {@link Plus.Prepend}
 *      {@link Plus.Append}
 *      {@link Plus.InsertAt}
 *      {@link Plus.Bulk}
 *          {@link Plus.Bulk.Insert}
 *          {@link Plus.Bulk.Prepend}
 *          {@link Plus.Bulk.Append}
 *          {@link Plus.Bulk.InsertAt}
 *
 * {@link Minus}
 *      {@link Minus.Remove}
 *      {@link Minus.RemoveAll}
 *      {@link Minus.RemoveFirst}
 *      {@link Minus.RemoveLast}
 *      {@link Minus.RemoveAt}
 *      {@link Minus.RemoveRange}
 *
 * {@link Transform}
 *      {@link Transform.Peek}
 *      {@link Transform.Filter}
 *      {@link Transform.Distinct}
 *      {@link Transform.Sort}
 *      {@link Transform.Reverse}
 *
 * {@link Transmute}
 *      {@link Transmute.PolyMap}
 *      {@link Transmute.FilterMap}
 *      {@link Transmute.PolyFlatMap}
 *
 * {@link Fold}
 *      {@link Fold.FoldLeft}
 *      {@link Fold.FoldRight}
 *      {@link Fold.Reduce}
 *      {@link Fold.Collect}
 *
 * {@link Aggregate}
 *      {@link Aggregate.Count}
 *      {@link Aggregate.Min}
 *      {@link Aggregate.Max}
 *
 * {@link Convert}
 *      {@link Convert.ToArray}
 *      {@link Convert.ToCollection}
 *
 * @param <A> type of value.
 * @param <U> unification parameter.
 */
public interface Operation<A, U extends Operation<?, U>> {

    // ----------------------------------------------------------
    //  OPERATION.INSPECTION
    // ----------------------------------------------------------

    /**
     * Inspect operations to query the underlying content provider.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Inspect<A, U extends Inspect<?, U>> extends Operation<A, U> {

        // state

        // shape

        // storage
    }

    // ----------------------------------------------------------
    //  OPERATION.QUERY
    // ----------------------------------------------------------

    /**
     * Query operations to make statements about contents.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Query<A, U extends Query<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.QUERY.CONTAINS
        // ----------------------------------------------------------

        /**
         * Declares the family of membership queries.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Membership<A, U extends Membership<?, U>> extends Query<A, U> {

            /**
             * Membership query for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Membership<?, U>> extends Membership<A, U> {

                /**
                 * Determines whether the given value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present.
                 */
                boolean contains(Object val);
            }

            // ----------------------------------------------------------

            /**
             * Membership query specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Membership<?, U>> extends Membership<Boolean, U> {

                /**
                 * Determines whether the given {@code boolean} value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present.
                 */
                boolean contains(boolean val);
            }

            // ----------------------------------------------------------

            /**
             * Membership query specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Membership<?, U>> extends Membership<Byte, U> {

                /**
                 * Determines whether the given {@code byte} value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present.
                 */
                boolean contains(byte val);
            }

            // ----------------------------------------------------------

            /**
             * Membership query specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Membership<?, U>> extends Membership<Short, U> {

                /**
                 * Determines whether the given {@code short} value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present.
                 */

                boolean contains(short val);
            }

            // ----------------------------------------------------------

            /**
             * Membership query specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Membership<?, U>> extends Membership<Character, U> {

                /**
                 * Determines whether the given {@code char} value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present.
                 */

                boolean contains(char val);
            }

            // ----------------------------------------------------------

            /**
             * Membership query specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Membership<?, U>> extends Membership<Integer, U> {

                /**
                 * Determines whether the given {@code int} value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present.
                 */

                boolean contains(int val);
            }

            // ----------------------------------------------------------

            /**
             * Membership query specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Membership<?, U>> extends Membership<Long, U> {

                /**
                 * Determines whether the given {@code long} value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present.
                 */
                boolean contains(long val);
            }

            // ----------------------------------------------------------

            /**
             * Membership query specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Membership<?, U>> extends Membership<Float, U> {

                /**
                 * Determines whether the given {@code float} value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present.
                 */
                boolean contains(float val);
            }

            // ----------------------------------------------------------

            /**
             * Membership query specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Membership<?, U>> extends Membership<Double, U> {

                /**
                 * Determines whether the given {@code double} value is contained.
                 *
                 * @param val value whose presence is to be determined.
                 * @return true iff the given value is present,
                 */
                boolean contains(double val);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.QUERY.QUANTIFY
        // ----------------------------------------------------------

        /**
         * Declares the family of quantifier query operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Quantify<A, U extends Quantify<?, U>> extends Query<A, U> {

            /**
             * Quantify queries specialized for reference values.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Quantify<?, U>> extends Quantify<A, U> {

                /**
                 * Determines whether any value of type {@code A} satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Seq<A> P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true iff any value matches.
                 */
                boolean anyMatch(Fn1.Predicate<? super A> predicate);

                /**
                 * Determines whether all values of type {@code A} satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Seq<A> P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true iff all values match or no values are present.
                 */
                boolean allMatch(Fn1.Predicate<? super A> predicate);

                /**
                 * Determines whether no values of type {@code A} satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Seq<A> ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true iff no value matches or no values are present.
                 */
                boolean noneMatch(Fn1.Predicate<? super A> predicate);
            }

            // ----------------------------------------------------------

            /**
             * Quantifier queries specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Quantify<?, U>> extends Quantify<Boolean, U> {
                /**
                 * Determines whether any {@code boolean} value satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Data.OfBool P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is true for any value.
                 */
                boolean anyMatch(Bool.Predicate predicate);

                /**
                 * Determines whether all {@code boolean} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Data.OfBool P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is satisfied for all values,
                 * or no value is present.
                 */
                boolean allMatch(Bool.Predicate predicate);

                /**
                 * Determines whether no {@code boolean} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Data.OfBool ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is never satisfied for all values,
                 * or no value is present.
                 */
                boolean noneMatch(Bool.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Quantifier queries specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Quantify<?, U>> extends Quantify<Byte, U> {

                /**
                 * Determines whether any {@code byte} value satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Data.OfI8 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is true for any value.
                 */
                boolean anyMatch(I8.Predicate predicate);

                /**
                 * Determines whether all {@code byte} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Data.OfI8 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is satisfied for all values,
                 * or no value is present.
                 */
                boolean allMatch(I8.Predicate predicate);

                /**
                 * Determines whether no {@code byte} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Data.OfI8 ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is never satisfied for all values,
                 * or no value is present.
                 */
                boolean noneMatch(I8.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Quantifier queries specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Quantify<?, U>> extends Quantify<Short, U> {

                /**
                 * Determines whether any {@code short} value satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Data.OfI16 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is true for any value.
                 */
                boolean anyMatch(I16.Predicate predicate);

                /**
                 * Determines whether all {@code short} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Data.OfI16 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is satisfied for all values,
                 * or no value is present.
                 */
                boolean allMatch(I16.Predicate predicate);

                /**
                 * Determines whether no {@code short} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Data.OfI16 ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is never satisfied for all values,
                 * or no value is present.
                 */
                boolean noneMatch(I16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Quantifier queries specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Quantify<?, U>> extends Quantify<Character, U> {

                /**
                 * Determines whether any {@code char} value satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Data.OfC16 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is true for any value.
                 */
                boolean anyMatch(C16.Predicate predicate);

                /**
                 * Determines whether all {@code char} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Data.OfC16 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is satisfied for all values,
                 * or no value is present.
                 */
                boolean allMatch(C16.Predicate predicate);

                /**
                 * Determines whether no {@code char} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Data.OfC16 ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is never satisfied for all values,
                 * or no value is present.
                 */
                boolean noneMatch(C16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Quantifier queries specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Quantify<?, U>> extends Quantify<Integer, U> {

                /**
                 * Determines whether any {@code int} value satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Data.OfI32 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is true for any value.
                 */
                boolean anyMatch(I32.Predicate predicate);

                /**
                 * Determines whether all {@code int} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Data.OfI32 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is satisfied for all values,
                 * or no value is present.
                 */
                boolean allMatch(I32.Predicate predicate);

                /**
                 * Determines whether no {@code int} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Data.OfI32 ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is never satisfied for all values,
                 * or no value is present.
                 */
                boolean noneMatch(I32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Quantifier queries specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Quantify<?, U>> extends Quantify<Long, U> {

                /**
                 * Determines whether any {@code long} value satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Data.OfI64 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is true for any value.
                 */
                boolean anyMatch(I64.Predicate predicate);

                /**
                 * Determines whether all {@code long} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Data.OfI64 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is satisfied for all values,
                 * or no value is present.
                 */
                boolean allMatch(I64.Predicate predicate);

                /**
                 * Determines whether no {@code long} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Data.OfI64 ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is never satisfied for all values,
                 * or no value is present.
                 */
                boolean noneMatch(I64.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Quantifier queries specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Quantify<?, U>> extends Quantify<Float, U> {

                /**
                 * Determines whether any {@code float} value satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Data.OfF32 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is true for any value.
                 */
                boolean anyMatch(F32.Predicate predicate);

                /**
                 * Determines whether all {@code float} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Data.OfF32 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is satisfied for all values,
                 * or no value is present.
                 */
                boolean allMatch(F32.Predicate predicate);

                /**
                 * Determines whether no {@code float} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Data.OfF32 ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is never satisfied for all values,
                 * or no value is present.
                 */
                boolean noneMatch(F32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Quantifier queries specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Quantify<?, U>> extends Quantify<Double, U> {

                /**
                 * Determines whether any {@code double} value satisfies the given predicate.
                 * <p>
                 * This corresponds to evaluating the existential quantification of
                 * the given predicate over the values {@code '∃(x) ∈ Data.OfF64 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, false is returned
                 * and the predicate is not evaluated.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is true for any value.
                 */
                boolean anyMatch(F64.Predicate predicate);

                /**
                 * Determines whether all {@code double} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification
                 * of the predicate over the values {@code '∀(x) ∈ Data.OfF64 P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is satisfied for all values,
                 * or no value is present.
                 */
                boolean allMatch(F64.Predicate predicate);

                /**
                 * Determines whether no {@code double} values satisfy the given predicate.
                 * <p>
                 * This corresponds to the evaluation of the universal quantification of
                 * the negated predicate over the array {@code '∀(x) ∈ Data.OfF64 ¬P(x)'}.
                 * The predicate is not evaluated for all values if it is not required
                 * to determine the result. If no values are present, the quantification
                 * is said to be 'vacuously satisfied', and always true is returned,
                 * independent of P(x).
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return true if the predicate is never satisfied for all values,
                 * or no value is present.
                 */
                boolean noneMatch(F64.Predicate predicate);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.QUERY.FIND-VALUE
        // ----------------------------------------------------------

        /**
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface FindValue<A, U extends FindValue<?, U>> extends Query<A, U> {

            /**
             * Value search queries specialized for values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            interface Of<A, U extends FindValue<?, U>> extends FindValue<A, U> {

                /**
                 * Returns the optional first value of type {@code A} which
                 * satisfies the given predicate. If no value can be determined,
                 * the empty option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first value of type {@code A}.
                 */
                Option<A> find(Fn1.Predicate<? super A> predicate);

                /**
                 * Returns the optional last value of type {@code A} which
                 * satisfies the given predicate. If no value can be determined,
                 * the empty option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last value of type {@code A}.
                 */
                Option<A> findLast(Fn1.Predicate<? super A> predicate);
            }

            // ----------------------------------------------------------

            /**
             * Value search queries specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            interface OfBool<U extends FindValue<?, U>> extends FindValue<Boolean, U> {

                /**
                 * Returns the optional first {@code boolean} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first {@code boolean} value.
                 */
                Bool.Option find(Bool.Predicate predicate);

                /**
                 * Returns the optional last {@code boolean} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last {@code boolean} value.
                 */
                Bool.Option findLast(Bool.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Value search queries specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI8<U extends FindValue<?, U>> extends FindValue<Byte, U> {

                /**
                 * Returns the optional first {@code byte} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first {@code byte} value.
                 */
                I8.Option find(I8.Predicate predicate);

                /**
                 * Returns the optional last {@code byte} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last {@code byte} value.
                 */
                I8.Option findLast(I8.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Value search queries specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI16<U extends FindValue<?, U>> extends FindValue<Short, U> {

                /**
                 * Returns the optional first {@code short} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first {@code short} value.
                 */
                I16.Option find(I16.Predicate predicate);

                /**
                 * Returns the optional last {@code short} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last {@code short} value.
                 */
                I16.Option findLast(I16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Value search queries specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            interface OfC16<U extends FindValue<?, U>> extends FindValue<Character, U> {

                /**
                 * Returns the optional first {@code char} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first {@code char} value.
                 */
                C16.Option find(C16.Predicate predicate);

                /**
                 * Returns the optional last {@code char} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last {@code char} value.
                 */
                C16.Option findLast(C16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Value search queries specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI32<U extends FindValue<?, U>> extends FindValue<Integer, U> {

                /**
                 * Returns the optional first {@code int} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first {@code int} value.
                 */
                I32.Option find(I32.Predicate predicate);

                /**
                 * Returns the optional last {@code int} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last {@code int} value.
                 */
                I32.Option findLast(I32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Value search queries specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI64<U extends FindValue<?, U>> extends FindValue<Long, U> {

                /**
                 * Returns the optional first {@code long} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first {@code long} value.
                 */
                I64.Option find(I64.Predicate predicate);

                /**
                 * Returns the optional last {@code long} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last {@code long} value.
                 */
                I64.Option findLast(I64.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Value search queries specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            interface OfF32<U extends FindValue<?, U>> extends FindValue<Float, U> {

                /**
                 * Returns the optional first {@code float} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first {@code float} value.
                 */
                F32.Option find(F32.Predicate predicate);

                /**
                 * Returns the optional last {@code float} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last {@code float} value.
                 */
                F32.Option findLast(F32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Value search queries specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            interface OfF64<U extends FindValue<?, U>> extends FindValue<Double, U> {

                /**
                 * Returns the optional first {@code double} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional first {@code double} value.
                 */
                F64.Option find(F64.Predicate predicate);

                /**
                 * Returns the optional last {@code double} value which satisfies
                 * the given predicate. If no value can be determined, the empty
                 * option is returned.
                 *
                 * @param predicate to be evaluated on contained values.
                 * @return optional last {@code double} value.
                 */
                F64.Option findLast(F64.Predicate predicate);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.QUERY.FIND-INDEX
        // ----------------------------------------------------------

        /**
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface FindIndex<A, U extends FindIndex<?, U>> extends Query<A, U> {

            /**
             * Index retrieval queries specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            interface Of<A, U extends FindIndex<?, U>> extends FindIndex<A, U> {

                /**
                 * Returns the index of the first value occurrence that is equal
                 * to the given value, according to the contract defined by
                 * {@link java.util.Objects#equals(Object, Object)}. If no
                 * occurrence can be determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(Object value);

                /**
                 * Returns the index of the first value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(Fn1.Predicate<? super A> predicate);

                /**
                 * Returns the index of the last value occurrence that is equal
                 * to the given value, according to the contract defined by
                 * {@link java.util.Objects#equals(Object, Object)}.
                 * If no occurrence can be determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(Object value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(Fn1.Predicate<? super A> predicate);
            }

            // ----------------------------------------------------------

            /**
             * Index retrieval queries specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            interface OfBool<U extends FindIndex<?, U>> extends FindIndex<Boolean, U> {

                /**
                 * Returns the index of the first {@code boolean} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(boolean value);

                /**
                 * Returns the index of the first {@code boolean} value occurrence
                 * that satisfies the given predicate. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(Bool.Predicate predicate);

                /**
                 * Returns the index of the last {@code boolean} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(boolean value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(Bool.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Index retrieval queries specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI8<U extends FindIndex<?, U>> extends FindIndex<Byte, U> {

                /**
                 * Returns the index of the first {@code byte} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(byte value);

                /**
                 * Returns the index of the first {@code byte} value occurrence
                 * that satisfies the given predicate. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(I8.Predicate predicate);

                /**
                 * Returns the index of the last {@code byte} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(byte value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(I8.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Index retrieval queries specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI16<U extends FindIndex<?, U>> extends FindIndex<Short, U> {

                /**
                 * Returns the index of the first {@code short} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(short value);

                /**
                 * Returns the index of the first {@code short} value occurrence
                 * that satisfies the given predicate. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(I16.Predicate predicate);

                /**
                 * Returns the index of the last {@code short} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(short value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(I16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Index retrieval queries specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            interface OfC16<U extends FindIndex<?, U>> extends FindIndex<Character, U> {

                /**
                 * Returns the index of the first {@code char} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(char value);

                /**
                 * Returns the index of the first {@code char} value occurrence
                 * that satisfies the given predicate. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(C16.Predicate predicate);

                /**
                 * Returns the index of the last {@code char} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(char value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(C16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Index retrieval queries specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI32<U extends FindIndex<?, U>> extends FindIndex<Integer, U> {

                /**
                 * Returns the index of the first {@code int} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(int value);

                /**
                 * Returns the index of the first {@code int} value occurrence
                 * that satisfies the given predicate. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(I32.Predicate predicate);

                /**
                 * Returns the index of the last {@code int} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(int value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(I32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Index retrieval queries specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI64<U extends FindIndex<?, U>> extends FindIndex<Long, U> {

                /**
                 * Returns the index of the first {@code long} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(long value);

                /**
                 * Returns the index of the first {@code long} value occurrence
                 * that satisfies the given predicate. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(I64.Predicate predicate);

                /**
                 * Returns the index of the last {@code long} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(long value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(I64.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Index retrieval queries specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            interface OfF32<U extends FindIndex<?, U>> extends FindIndex<Float, U> {

                /**
                 * Returns the index of the first {@code float} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(float value);

                /**
                 * Returns the index of the first {@code float} value occurrence
                 * that satisfies the given predicate. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(F32.Predicate predicate);

                /**
                 * Returns the index of the last {@code float} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(float value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(F32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Index retrieval queries specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            interface OfF64<U extends FindIndex<?, U>> extends FindIndex<Double, U> {

                /**
                 * Returns the index of the first {@code double} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the first equal value
                 *              occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexOf(double value);

                /**
                 * Returns the index of the first {@code double} value occurrence
                 * that satisfies the given predicate. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param predicate whose index of the first matching value
                 *                  occurrence is to be determined.
                 * @return index of the first value occurrence or -1.
                 */
                long indexWhere(F64.Predicate predicate);

                /**
                 * Returns the index of the last {@code double} value occurrence
                 * that is equal to the given value. If no occurrence can be
                 * determined, -1 is returned.
                 *
                 * @param value whose index of the last equal value
                 *              occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexOf(double value);

                /**
                 * Returns the index of the last value occurrence that satisfies
                 * the given predicate. If no occurrence can be determined,
                 * -1 is returned.
                 *
                 * @param predicate whose index of the last matching value
                 *                  occurrence is to be determined.
                 * @return index of the last value occurrence or -1.
                 */
                long lastIndexWhere(F64.Predicate predicate);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.QUERY.BULK
        // ----------------------------------------------------------

        /**
         * Declares the family of bulk addition operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Bulk<A, U extends Bulk<?, U>> extends Query<A, U> {

            // ----------------------------------------------------------
            //  OPERATION.QUERY.BULK.CONTAINS
            // ----------------------------------------------------------

            /**
             * Declares the family of bulk membership queries.
             *
             * @param <U> unification parameter.
             */
            interface Membership<A, U extends Membership<?, U>> extends Bulk<A, U>, Query.Membership<A, U> {

                /**
                 * Bulk membership specialized for reference values of type {@code A}.
                 *
                 * @param <A> type of value.
                 * @param <U> unification parameter.
                 */
                @Mixin interface Of<A, U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<A, U>,
                        Query.Membership.Of<A, U> {

                    /**
                     * Determines whether all values in the given array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all values are present.
                     */
                    boolean contains(Object... ary);

                    /**
                     * Determines whether all values in the given iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all values are present.
                     */
                    boolean contains(Iterable<?> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk membership specialized for {@code boolean} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfBool<U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<Boolean, U>,
                        Query.Membership.OfBool<U> {

                    /**
                     * Determines whether all {@code boolean} values in the given
                     * array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all {@code boolean} values are present.
                     */
                    boolean contains(boolean... ary);

                    /**
                     * Determines whether all {@code boolean} values in the given
                     * iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all {@code boolean} values are present.
                     */
                    boolean contains(Iterable<Boolean> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk membership specialized for {@code byte} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI8<U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<Byte, U>,
                        Query.Membership.OfI8<U> {

                    /**
                     * Determines whether all {@code byte} values in the given
                     * array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all {@code byte} values are present.
                     */
                    boolean contains(byte... ary);

                    /**
                     * Determines whether all {@code byte} values in the given
                     * iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all {@code byte} values are present.
                     */
                    boolean contains(Iterable<Byte> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk membership specialized for {@code short} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI16<U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<Short, U>,
                        Query.Membership.OfI16<U> {

                    /**
                     * Determines whether all {@code short} values in the given
                     * array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all {@code short} values are present.
                     */
                    boolean contains(short... ary);

                    /**
                     * Determines whether all {@code short} values in the given
                     * iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all {@code short} values are present.
                     */
                    boolean contains(Iterable<Short> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk membership specialized for {@code char} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfC16<U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<Character, U>,
                        Query.Membership.OfC16<U> {

                    /**
                     * Determines whether all {@code char} values in the given
                     * array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all {@code char} values are present.
                     */
                    boolean contains(char... ary);

                    /**
                     * Determines whether all {@code char} values in the given
                     * iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all {@code char} values are present.
                     */
                    boolean contains(Iterable<Character> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk membership specialized for {@code int} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI32<U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<Integer, U>,
                        Query.Membership.OfI32<U> {

                    /**
                     * Determines whether all {@code int} values in the given
                     * array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all {@code int} values are present.
                     */
                    boolean contains(int... ary);

                    /**
                     * Determines whether all {@code int} values in the given
                     * iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all {@code int} values are present.
                     */
                    boolean contains(Iterable<Integer> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk membership specialized for {@code long} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI64<U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<Long, U>,
                        Query.Membership.OfI64<U> {

                    /**
                     * Determines whether all {@code long} values in the given
                     * array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all {@code long} values are present.
                     */
                    boolean contains(long... ary);

                    /**
                     * Determines whether all {@code long} values in the given
                     * iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all {@code long} values are present.
                     */
                    boolean contains(Iterable<Long> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk membership specialized for {@code float} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF32<U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<Float, U>,
                        Query.Membership.OfF32<U> {

                    /**
                     * Determines whether all {@code float} values in the given
                     * array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all {@code float} values are present.
                     */
                    boolean contains(float... ary);

                    /**
                     * Determines whether all {@code float} values in the given
                     * iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all {@code float} values are present.
                     */
                    boolean contains(Iterable<Float> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk membership specialized for {@code double} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF64<U extends Bulk.Membership<?, U>>
                        extends Bulk.Membership<Double, U>,
                        Query.Membership.OfF64<U> {

                    /**
                     * Determines whether all {@code double} values in the given
                     * array are contained.
                     *
                     * @param ary array of values whose presence is to be determined.
                     * @return true iff all {@code double} values are present.
                     */
                    boolean contains(double... ary);

                    /**
                     * Determines whether all {@code double} values in the given
                     * iterable are contained.
                     *
                     * @param itr iterable of values whose presence is to be determined.
                     * @return true iff all {@code double} values are present.
                     */
                    boolean contains(Iterable<Double> itr);
                }
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.ACCESS
    // ----------------------------------------------------------

    /**
     * Access operations to get specified content.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Access<A, U extends Access<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.ACCESS.VALUE
        // ----------------------------------------------------------

        /**
         * Declares the operation family of value accessors.
         *
         * @param <U> unification parameter.
         */
        interface Value<A, U extends Value<?, U>> extends Access<A, U> {

            /**
             * Declares a specialized value accessor of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Value<?, U>> extends Value<A, U> {

                /**
                 * Returns a value of type {@code A}.
                 */
                A value();
            }

            // ----------------------------------------------------------

            /**
             * Declares a specialized {@code boolean} value accessor.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Value<?, U>> extends Value<Boolean, U> {

                /**
                 * Returns a {@code boolean} value.
                 */
                boolean value();
            }

            // ----------------------------------------------------------

            /**
             * Declares a specialized {@code byte} value accessor.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Value<?, U>> extends Value<Byte, U> {

                /**
                 * Returns a {@code byte} value.
                 */
                byte value();
            }

            // ----------------------------------------------------------

            /**
             * Declares a specialized {@code short} value accessor.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Value<?, U>> extends Value<Short, U> {

                /**
                 * Returns a {@code short} value.
                 */
                short value();
            }

            // ----------------------------------------------------------

            /**
             * Declares a specialized {@code char} value accessor.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Value<?, U>> extends Value<Character, U> {

                /**
                 * Returns a {@code char} value.
                 */
                char value();
            }

            // ----------------------------------------------------------

            /**
             * Declares a specialized {@code int} value accessor.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Value<?, U>> extends Value<Integer, U> {

                /**
                 * Returns a {@code int} value.
                 */
                int value();
            }

            // ----------------------------------------------------------

            /**
             * Declares a specialized {@code long} value accessor.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Value<?, U>> extends Value<Long, U> {

                /**
                 * Returns a {@code long} value.
                 */
                long value();
            }

            // ----------------------------------------------------------

            /**
             * Declares a specialized {@code float} value accessor.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Value<?, U>> extends Value<Float, U> {

                /**
                 * Returns a {@code float} value.
                 */
                float value();
            }

            // ----------------------------------------------------------

            /**
             * Declares a specialized {@code double} value accessor.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Value<?, U>> extends Value<Double, U> {

                /**
                 * Returns a {@code double} value.
                 */
                double value();
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.ACCESS.SINGLE
        // ----------------------------------------------------------

        /**
         * Declares the operation family of singleton accessors.
         * <p>
         * This operation is based on the semantics that only one
         * existing value is considered valid. For example, to
         * determine that a query returns a single result value.
         *
         * @param <U> unification parameter.
         */
        interface Single<A, U extends Single<?, U>> {

            /**
             * Singleton accessor specialized to reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Single<?, U>> extends Single<A, U> {

                /**
                 * Returns the optional value singleton of type {@code A} if
                 * it is the only existing value, otherwise the empty option
                 * is returned.
                 */
                Option<A> single();
            }

            // ----------------------------------------------------------

            /**
             * Singleton accessor specialized to {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Single<?, U>> extends Single<Boolean, U> {

                /**
                 * Returns the optional {@code boolean} value singleton if
                 * it is the only existing value, otherwise the empty option
                 * is returned.
                 */
                Bool.Option single();
            }

            // ----------------------------------------------------------

            /**
             * Singleton accessor specialized to {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Single<?, U>> extends Single<Byte, U> {

                /**
                 * Returns the optional {@code byte} value singleton if it
                 * is the only existing value, otherwise the empty option
                 * is returned.
                 */
                I8.Option single();
            }

            // ----------------------------------------------------------

            /**
             * Singleton accessor specialized to {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Single<?, U>> extends Single<Short, U> {

                /**
                 * Returns the optional {@code short} value singleton if it
                 * is the only existing value, otherwise the empty option
                 * is returned.
                 */
                I16.Option single();
            }

            // ----------------------------------------------------------

            /**
             * Singleton accessor specialized to {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Single<?, U>> extends Single<Character, U> {

                /**
                 * Returns the optional {@code char} value singleton if it
                 * is the only existing value, otherwise the empty option
                 * is returned.
                 */
                C16.Option single();
            }

            // ----------------------------------------------------------

            /**
             * Singleton accessor specialized to {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Single<?, U>> extends Single<Integer, U> {

                /**
                 * Returns the optional {@code int} value singleton if it
                 * is the only existing value, otherwise the empty option
                 * is returned.
                 */
                I32.Option single();
            }

            // ----------------------------------------------------------

            /**
             * Singleton accessor specialized to {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Single<?, U>> extends Single<Long, U> {

                /**
                 * Returns the optional {@code long} value singleton if it
                 * is the only existing value, otherwise the empty option
                 * is returned.
                 */
                I64.Option single();
            }

            // ----------------------------------------------------------

            /**
             * Singleton accessor specialized to {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Single<?, U>> extends Single<Float, U> {

                /**
                 * Returns the optional {@code float} value singleton if it
                 * is the only existing value, otherwise the empty option is
                 * returned.
                 */
                F32.Option single();
            }

            // ----------------------------------------------------------

            /**
             * Singleton accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Single<?, U>> extends Single<Double, U> {

                /**
                 * Returns the optional {@code double} value singleton if it
                 * is the only existing value, otherwise the empty option is
                 * returned.
                 */
                F64.Option single();
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.ACCESS.FIRST
        // ----------------------------------------------------------

        /**
         * Declares the operation family of first accessors.
         *
         * @param <U> unification parameter.
         */
        interface First<A, U extends First<?, U>> extends Access<A, U> {

            /**
             * First accessor specialized to reference value of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends First<?, U>> extends First<A, U> {

                /**
                 * Returns the optional first value of type {@code A}
                 * if present, or the empty option if empty.
                 */
                Option<A> first();
            }

            // ----------------------------------------------------------

            /**
             * First accessor specialized to {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends First<?, U>> extends First<Boolean, U> {

                /**
                 * Returns the optional first {@code boolean} value
                 * if present, or the empty option if empty.
                 */
                Bool.Option first();
            }

            // ----------------------------------------------------------

            /**
             * First accessor specialized to {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends First<?, U>> extends First<Byte, U> {

                /**
                 * Returns the optional first {@code byte} value
                 * if present, or the empty option if empty.
                 */
                I8.Option first();
            }

            // ----------------------------------------------------------

            /**
             * First accessor specialized to {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends First<?, U>> extends First<Short, U> {

                /**
                 * Returns the optional first {@code short} value
                 * if present, or the empty option if empty.
                 */
                I16.Option first();
            }

            // ----------------------------------------------------------

            /**
             * First accessor specialized to {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends First<?, U>> extends First<Character, U> {

                /**
                 * Returns the optional first {@code char} value
                 * if present, or the empty option if empty.
                 */
                C16.Option first();
            }

            // ----------------------------------------------------------

            /**
             * First accessor specialized to {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends First<?, U>> extends First<Integer, U> {

                /**
                 * Returns the optional first {@code int} value
                 * if present, or the empty option if empty.
                 */
                I32.Option first();
            }

            // ----------------------------------------------------------

            /**
             * First accessor specialized to {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends First<?, U>> extends First<Long, U> {

                /**
                 * Returns the optional first {@code long} value
                 * if present, or the empty option if empty.
                 */
                I64.Option first();
            }

            // ----------------------------------------------------------

            /**
             * First accessor specialized to {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends First<?, U>> extends First<Float, U> {

                /**
                 * Returns the optional first {@code float} value
                 * if present, or the empty option if empty.
                 */
                F32.Option first();
            }

            // ----------------------------------------------------------

            /**
             * First accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends First<?, U>> extends First<Double, U> {

                /**
                 * Returns the optional first {@code double} value
                 * if present, or the empty option if empty.
                 */
                F64.Option first();
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.ACCESS.LAST
        // ----------------------------------------------------------

        /**
         * Declares the operation family of last accessors.
         *
         * @param <U> unification parameter.
         */
        interface Last<A, U extends Last<?, U>> extends Access<A, U> {

            /**
             * Last accessor specialized to reference values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Last<?, U>> extends Last<A, U> {

                /**
                 * Returns the optional last value of type {@code A}
                 * if present, or the empty option if empty.
                 */
                Option<A> last();
            }

            // ----------------------------------------------------------

            /**
             * Last accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Last<?, U>> extends Last<Boolean, U> {

                /**
                 * Returns the optional last {@code boolean} value
                 * if present, or the empty option if empty.
                 */
                Bool.Option last();
            }

            // ----------------------------------------------------------

            /**
             * Last accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Last<?, U>> extends Last<Byte, U> {

                /**
                 * Returns the optional last {@code byte} value
                 * if present, or the empty option if empty.
                 */
                I8.Option last();
            }

            // ----------------------------------------------------------

            /**
             * Last accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Last<?, U>> extends Last<Short, U> {

                /**
                 * Returns the optional last {@code short} value
                 * if present, or the empty option if empty.
                 */
                I16.Option last();
            }

            // ----------------------------------------------------------

            /**
             * Last accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Last<?, U>> extends Last<Character, U> {

                /**
                 * Returns the optional last {@code char} value
                 * if present, or the empty option if empty.
                 */
                C16.Option last();
            }

            // ----------------------------------------------------------

            /**
             * Last accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Last<?, U>> extends Last<Integer, U> {

                /**
                 * Returns the optional last {@code int} value
                 * if present, or the empty option if empty.
                 */
                I32.Option last();
            }

            // ----------------------------------------------------------

            /**
             * Last accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Last<?, U>> extends Last<Long, U> {

                /**
                 * Returns the optional last {@code long} value
                 * if present, or the empty option if empty.
                 */
                I64.Option last();
            }

            // ----------------------------------------------------------

            /**
             * Last accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Last<?, U>> extends Last<Float, U> {

                /**
                 * Returns the optional last {@code float} value
                 * if present, or the empty option if empty.
                 */
                F32.Option last();
            }

            // ----------------------------------------------------------

            /**
             * Last accessor specialized to {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Last<?, U>> extends Last<Double, U> {

                /**
                 * Returns the optional last {@code double} value
                 * if present, or the empty option if empty.
                 */
                F64.Option last();
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.ACCESS.AT
        // ----------------------------------------------------------

        /**
         * Declares the family of indexed accessors.
         *
         * @param <U> unification parameter.
         */
        interface At<A, U extends At<?, U>> extends Access<A, U> {

            /**
             * Index accessor specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends At<?, U>> extends At<A, U> {

                /**
                 * Returns the value of type {@code A} at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return value of type {@code A}.
                 */
                A at(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Index accessor specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends At<?, U>> extends At<Boolean, U> {

                /**
                 * Returns the {@code boolean} value at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return {@code boolean} value.
                 */
                boolean at(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Index accessor specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends At<?, U>> extends At<Byte, U> {

                /**
                 * Returns the {@code byte} value at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return {@code byte} value.
                 */
                byte at(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Index accessor specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends At<?, U>> extends At<Short, U> {

                /**
                 * Returns the {@code short} value at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return {@code short} value.
                 */
                short at(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Index accessor specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends At<?, U>> extends At<Character, U> {

                /**
                 * Returns the {@code char} value at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return {@code char} value.
                 */
                char at(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Index accessor specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends At<?, U>> extends At<Integer, U> {

                /**
                 * Returns the {@code int} value at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return {@code int} value.
                 */
                int at(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Index accessor specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends At<?, U>> extends At<Long, U> {

                /**
                 * Returns the {@code long} value at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return {@code long} value.
                 */
                long at(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Index accessor specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends At<?, U>> extends At<Float, U> {

                /**
                 * Returns the {@code float} value at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return {@code float} value.
                 */
                float at(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Index accessor specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends At<?, U>> extends At<Double, U> {

                /**
                 * Returns the {@code double} value at the given index.
                 *
                 * @param idx whose corresponding value is to be returned.
                 * @return {@code double} value.
                 */
                double at(long idx);
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.PLUS
    // ----------------------------------------------------------

    /**
     * Addition operations to insert specified content.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Plus<A, U extends Plus<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.PLUS.INSERT
        // ----------------------------------------------------------

        /**
         * Declares the family of insert operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Insert<A, U extends Insert<?, U>> extends Plus<A, U> {

            /**
             * Insert specialized for reference values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Insert<?, U>> extends Insert<A, U> {

                /**
                 * Inserts the given value of type {@code A}.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(A val);
            }

            // ----------------------------------------------------------

            /**
             * Insert specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Insert<?, U>> extends Insert<Boolean, U> {

                /**
                 * Inserts the given {@code boolean} value.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(boolean val);
            }

            // ----------------------------------------------------------

            /**
             * Insert specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Insert<?, U>> extends Insert<Byte, U> {

                /**
                 * Inserts the given {@code byte} value.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(byte val);
            }

            // ----------------------------------------------------------

            /**
             * Insert specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Insert<?, U>> extends Insert<Short, U> {

                /**
                 * Inserts the given {@code short} value.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(short val);
            }

            // ----------------------------------------------------------

            /**
             * Insert specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Insert<?, U>> extends Insert<Character, U> {

                /**
                 * Inserts the given {@code char} value.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(char val);
            }

            // ----------------------------------------------------------

            /**
             * Insert specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Insert<?, U>> extends Insert<Integer, U> {

                /**
                 * Inserts the given {@code int} value.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(int val);
            }

            // ----------------------------------------------------------

            /**
             * Insert specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Insert<?, U>> extends Insert<Long, U> {

                /**
                 * Inserts the given {@code long} value.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(long val);
            }

            // ----------------------------------------------------------

            /**
             * Insert specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Insert<?, U>> extends Insert<Float, U> {

                /**
                 * Inserts the given {@code float} value.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(float val);
            }

            // ----------------------------------------------------------

            /**
             * Insert specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Insert<?, U>> extends Insert<Double, U> {

                /**
                 * Inserts the given {@code double} value.
                 *
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insert(double val);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.PLUS.PREPEND
        // ----------------------------------------------------------

        /**
         * Declares the family of prepend operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Prepend<A, U extends Prepend<?, U>> extends Plus<A, U> {

            /**
             * Prepend specialized for reference values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Prepend<?, U>> extends Prepend<A, U> {

                /**
                 * Prepends the given value of type {@code A}.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(A val);
            }

            // ----------------------------------------------------------

            /**
             * Prepend specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Prepend<?, U>> extends Prepend<Boolean, U> {

                /**
                 * Prepends the given {@code boolean} value.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(boolean val);
            }

            // ----------------------------------------------------------

            /**
             * Prepend specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Prepend<?, U>> extends Prepend<Byte, U> {

                /**
                 * Prepends the given {@code byte} value.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(byte val);
            }

            // ----------------------------------------------------------

            /**
             * Prepend specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Prepend<?, U>> extends Prepend<Short, U> {

                /**
                 * Prepends the given {@code short} value.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(short val);
            }

            // ----------------------------------------------------------

            /**
             * Prepend specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Prepend<?, U>> extends Prepend<Character, U> {

                /**
                 * Prepends the given {@code char} value.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(char val);
            }

            // ----------------------------------------------------------

            /**
             * Prepend specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Prepend<?, U>> extends Prepend<Integer, U> {

                /**
                 * Prepends the given {@code int} value.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(int val);
            }

            // ----------------------------------------------------------

            /**
             * Prepend specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Prepend<?, U>> extends Prepend<Long, U> {

                /**
                 * Prepends the given {@code long} value.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(long val);
            }

            // ----------------------------------------------------------

            /**
             * Prepend specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Prepend<?, U>> extends Prepend<Float, U> {

                /**
                 * Prepends the given {@code float} value.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(float val);
            }

            // ----------------------------------------------------------

            /**
             * Prepend specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Prepend<?, U>> extends Prepend<Double, U> {

                /**
                 * Prepends the given {@code double} value.
                 *
                 * @param val value to be prepended.
                 * @return instance of unified type.
                 */
                U prepend(double val);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.PLUS.APPEND
        // ----------------------------------------------------------

        /**
         * Declares the family of append operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Append<A, U extends Append<?, U>> extends Plus<A, U> {

            /**
             * Append specialized for reference values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Append<?, U>> extends Append<A, U> {

                /**
                 * Appends the given value of type {@code A}.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(A val);
            }

            // ----------------------------------------------------------

            /**
             * Append specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Append<?, U>> extends Append<Boolean, U> {

                /**
                 * Appends the given {@code boolean} value.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(boolean val);
            }

            // ----------------------------------------------------------

            /**
             * Append specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Append<?, U>> extends Append<Byte, U> {

                /**
                 * Appends the given {@code byte} value.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(byte val);
            }

            // ----------------------------------------------------------

            /**
             * Append specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Append<?, U>> extends Append<Short, U> {

                /**
                 * Appends the given {@code short} value.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(short val);
            }

            // ----------------------------------------------------------

            /**
             * Append specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Append<?, U>> extends Append<Character, U> {

                /**
                 * Appends the given {@code char} value.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(char val);
            }

            // ----------------------------------------------------------

            /**
             * Append specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Append<?, U>> extends Append<Integer, U> {

                /**
                 * Appends the given {@code int} value.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(int val);
            }

            // ----------------------------------------------------------

            /**
             * Append specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Append<?, U>> extends Append<Long, U> {

                /**
                 * Appends the given {@code long} value.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(long val);
            }

            // ----------------------------------------------------------

            /**
             * Append specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Append<?, U>> extends Append<Float, U> {

                /**
                 * Appends the given {@code float} value.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(float val);
            }

            // ----------------------------------------------------------

            /**
             * Append specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Append<?, U>> extends Append<Double, U> {

                /**
                 * Appends the given {@code double} value.
                 *
                 * @param val value to be appended.
                 * @return instance of unified type.
                 */
                U append(double val);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.PLUS.INSERT-AT
        // ----------------------------------------------------------

        /**
         * Declares the family of positional insert operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface InsertAt<A, U extends InsertAt<?, U>> extends Plus<A, U> {

            /**
             * Positional insert specialized for reference values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends InsertAt<?, U>> extends InsertAt<A, U> {

                /**
                 * Inserts the given value of type {@code A} at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, A val);
            }

            // ----------------------------------------------------------

            /**
             * Positional insert specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends InsertAt<?, U>> extends InsertAt<Boolean, U> {

                /**
                 * Inserts the given {@code boolean} value at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, boolean val);
            }

            // ----------------------------------------------------------

            /**
             * Positional insert specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends InsertAt<?, U>> extends InsertAt<Byte, U> {

                /**
                 * Inserts the given {@code byte} value at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, byte val);
            }

            // ----------------------------------------------------------

            /**
             * Positional insert specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends InsertAt<?, U>> extends InsertAt<Short, U> {

                /**
                 * Inserts the given {@code short} value at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, short val);
            }

            // ----------------------------------------------------------

            /**
             * Positional insert specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends InsertAt<?, U>> extends InsertAt<Character, U> {

                /**
                 * Inserts the given {@code char} value at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, char val);
            }

            // ----------------------------------------------------------

            /**
             * Positional insert specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends InsertAt<?, U>> extends InsertAt<Integer, U> {

                /**
                 * Inserts the given {@code int} value at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, int val);
            }

            // ----------------------------------------------------------

            /**
             * Positional insert specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends InsertAt<?, U>> extends InsertAt<Long, U> {

                /**
                 * Inserts the given {@code long} value at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, long val);
            }

            // ----------------------------------------------------------

            /**
             * Positional insert specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends InsertAt<?, U>> extends InsertAt<Float, U> {

                /**
                 * Inserts the given {@code float} value at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, float val);
            }

            // ----------------------------------------------------------

            /**
             * Positional insert specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends InsertAt<?, U>> extends InsertAt<Double, U> {

                /**
                 * Inserts the given {@code double} value at the given position.
                 * If the position is outside the index range (0 to N inclusive,
                 * where N is the count before this operation).
                 *
                 * @param pos position of the insert location.
                 * @param val value to be inserted.
                 * @return instance of unified type.
                 */
                U insertAt(long pos, double val);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.PLUS.BULK
        // ----------------------------------------------------------

        /**
         * Declares the family of bulk addition operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Bulk<A, U extends Bulk<?, U>> extends Plus<A, U> {

            // ----------------------------------------------------------
            //  OPERATION.PLUS.BULK.INSERT
            // ----------------------------------------------------------

            /**
             * Declares the family of bulk-insert operations.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            interface Insert<A, U extends Insert<?, U>>
                    extends Bulk<A, U>, Plus.Insert<A, U> {

                /**
                 * Bulk-insert specialized for {@code long} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface Of<A, U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<A, U>,
                        Plus.Insert.Of<A, U> {

                    /**
                     * Inserts the values of type {@code A} contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    @SuppressWarnings("unchecked")
                    U insert(A... ary);

                    /**
                     * Inserts the values of type {@code A} contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<? extends A> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-insert specialized for {@code boolean} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfBool<U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<Boolean, U>,
                        Plus.Insert.OfBool<U> {

                    /**
                     * Inserts the {@code boolean} values contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(boolean... ary);

                    /**
                     * Inserts the {@code boolean} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<Boolean> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-insert specialized for {@code byte} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI8<U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<Byte, U>,
                        Plus.Insert.OfI8<U> {

                    /**
                     * Inserts the {@code byte} values contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(byte... ary);

                    /**
                     * Inserts the {@code byte} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<Byte> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-insert specialized for {@code short} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI16<U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<Short, U>,
                        Plus.Insert.OfI16<U> {

                    /**
                     * Inserts the {@code short} values contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(short... ary);

                    /**
                     * Inserts the {@code short} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<Short> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-insert specialized for {@code char} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfC16<U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<Character, U>,
                        Plus.Insert.OfC16<U> {

                    /**
                     * Inserts the {@code char} values contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(char... ary);

                    /**
                     * Inserts the {@code char} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<Character> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-insert specialized for {@code int} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI32<U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<Integer, U>,
                        Plus.Insert.OfI32<U> {

                    /**
                     * Inserts the {@code int} values contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(int... ary);

                    /**
                     * Inserts the {@code int} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<Integer> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-insert specialized for {@code long} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI64<U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<Long, U>,
                        Plus.Insert.OfI64<U> {

                    /**
                     * Inserts the {@code long} values contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(long... ary);

                    /**
                     * Inserts the {@code long} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<Long> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-insert specialized for {@code float} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF32<U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<Float, U>,
                        Plus.Insert.OfF32<U> {

                    /**
                     * Inserts the {@code float} values contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(float... ary);

                    /**
                     * Inserts the {@code float} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<Float> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-insert specialized for {@code double} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF64<U extends Bulk.Insert<?, U>>
                        extends Bulk.Insert<Double, U>,
                        Plus.Insert.OfF64<U> {

                    /**
                     * Inserts the {@code double} values contained in the given array.
                     *
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(double... ary);

                    /**
                     * Inserts the {@code double} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insert(Iterable<Double> itr);
                }
            }

            // ----------------------------------------------------------
            //  OPERATION.PLUS.BULK.PREPEND
            // ----------------------------------------------------------

            /**
             * Declares the family of bulk-prepend operations.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            interface Prepend<A, U extends Prepend<?, U>>
                    extends Bulk<A, U>, Plus.Prepend<A, U> {

                /**
                 * Bulk-prepend specialized for {@code long} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface Of<A, U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<A, U>,
                        Plus.Prepend.Of<A, U> {

                    /**
                     * Prepends the values of type {@code A} contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    @SuppressWarnings("unchecked")
                    U prepend(A... ary);

                    /**
                     * Prepends the values of type {@code A} contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<? extends A> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-prepend specialized for {@code boolean} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfBool<U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<Boolean, U>,
                        Plus.Prepend.OfBool<U> {

                    /**
                     * Prepends the {@code boolean} values contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(boolean... ary);

                    /**
                     * Prepends the {@code boolean} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<Boolean> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-prepend specialized for {@code byte} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI8<U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<Byte, U>,
                        Plus.Prepend.OfI8<U> {

                    /**
                     * Prepends the {@code byte} values contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(byte... ary);

                    /**
                     * Prepends the {@code byte} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<Byte> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-prepend specialized for {@code short} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI16<U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<Short, U>,
                        Plus.Prepend.OfI16<U> {

                    /**
                     * Prepends the {@code short} values contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(short... ary);

                    /**
                     * Prepends the {@code short} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<Short> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-prepend specialized for {@code char} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfC16<U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<Character, U>,
                        Plus.Prepend.OfC16<U> {

                    /**
                     * Prepends the {@code char} values contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(char... ary);

                    /**
                     * Prepends the {@code char} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<Character> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-prepend specialized for {@code int} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI32<U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<Integer, U>,
                        Plus.Prepend.OfI32<U> {

                    /**
                     * Prepends the {@code int} values contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(int... ary);

                    /**
                     * Prepends the {@code int} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<Integer> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-prepend specialized for {@code long} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI64<U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<Long, U>,
                        Plus.Prepend.OfI64<U> {

                    /**
                     * Prepends the {@code long} values contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(long... ary);

                    /**
                     * Prepends the {@code long} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<Long> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-prepend specialized for {@code float} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF32<U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<Float, U>,
                        Plus.Prepend.OfF32<U> {

                    /**
                     * Prepends the {@code float} values contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(float... ary);

                    /**
                     * Prepends the {@code float} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<Float> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-prepend specialized for {@code double} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF64<U extends Bulk.Prepend<?, U>>
                        extends Bulk.Prepend<Double, U>,
                        Plus.Prepend.OfF64<U> {

                    /**
                     * Prepends the {@code double} values contained in the given array.
                     *
                     * @param ary array of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(double... ary);

                    /**
                     * Prepends the {@code double} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be prepended.
                     * @return instance of unified type.
                     */
                    U prepend(Iterable<Double> itr);
                }
            }

            // ----------------------------------------------------------
            //  OPERATION.PLUS.BULK.APPEND
            // ----------------------------------------------------------

            /**
             * Declares the family of bulk-append operations.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            interface Append<A, U extends Append<?, U>> extends Bulk<A, U>, Plus.Append<A, U> {

                /**
                 * Bulk-append specialized for reference values of type {@code A}.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface Of<A, U extends Bulk.Append<?, U>>
                        extends Bulk.Append<A, U>,
                        Plus.Append.Of<A, U> {

                    /**
                     * Appends the values of type {@code A} contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    @SuppressWarnings("unchecked")
                    U append(A... ary);

                    /**
                     * Appends the values of type {@code A} contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<? extends A> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-append specialized for {@code boolean} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfBool<U extends Bulk.Append<?, U>>
                        extends Bulk.Append<Boolean, U>,
                        Plus.Append.OfBool<U> {

                    /**
                     * Appends the {@code boolean} values contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(boolean... ary);

                    /**
                     * Appends the {@code boolean} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<Boolean> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-append specialized for {@code byte} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI8<U extends Bulk.Append<?, U>>
                        extends Bulk.Append<Byte, U>,
                        Plus.Append.OfI8<U> {

                    /**
                     * Appends the {@code byte} values contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(byte... ary);

                    /**
                     * Appends the {@code byte} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<Byte> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-append specialized for {@code short} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI16<U extends Bulk.Append<?, U>>
                        extends Bulk.Append<Short, U>,
                        Plus.Append.OfI16<U> {

                    /**
                     * Appends the {@code short} values contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(short... ary);

                    /**
                     * Appends the {@code short} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<Short> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-append specialized for {@code char} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfC16<U extends Bulk.Append<?, U>>
                        extends Bulk.Append<Character, U>,
                        Plus.Append.OfC16<U> {

                    /**
                     * Appends the {@code char} values contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(char... ary);

                    /**
                     * Appends the {@code char} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<Character> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-append specialized for {@code int} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI32<U extends Bulk.Append<?, U>>
                        extends Bulk.Append<Integer, U>,
                        Plus.Append.OfI32<U> {

                    /**
                     * Appends the {@code int} values contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(int... ary);

                    /**
                     * Appends the {@code int} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<Integer> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-append specialized for {@code long} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI64<U extends Bulk.Append<?, U>>
                        extends Bulk.Append<Long, U>,
                        Plus.Append.OfI64<U> {

                    /**
                     * Appends the {@code long} values contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(long... ary);

                    /**
                     * Appends the {@code long} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<Long> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-append specialized for {@code float} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF32<U extends Bulk.Append<?, U>>
                        extends Bulk.Append<Float, U>,
                        Plus.Append.OfF32<U> {

                    /**
                     * Appends the {@code float} values contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(float... ary);

                    /**
                     * Appends the {@code float} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<Float> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Bulk-append specialized for {@code double} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF64<U extends Bulk.Append<?, U>>
                        extends Bulk.Append<Double, U>,
                        Plus.Append.OfF64<U> {

                    /**
                     * Appends the {@code double} values contained in the given array.
                     *
                     * @param ary array of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(double... ary);

                    /**
                     * Appends the {@code double} values contained in the given iterable.
                     *
                     * @param itr iterable of values to be appended.
                     * @return instance of unified type.
                     */
                    U append(Iterable<Double> itr);
                }
            }

            // ----------------------------------------------------------
            //  OPERATION.PLUS.BULK.INSERT-AT
            // ----------------------------------------------------------

            /**
             * Declares the family of positional bulk-insert operations.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            interface InsertAt<A, U extends InsertAt<?, U>>
                    extends Bulk<A, U>, Plus.InsertAt<A, U> {

                /**
                 * Positional bulk-insert specialized for reference values of type {@code A}.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface Of<A, U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<A, U>,
                        Plus.InsertAt.Of<A, U> {

                    /**
                     * Inserts the values of type {@code A} contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    @SuppressWarnings("unchecked")
                    U insertAt(long pos, A... ary);

                    /**
                     * Inserts the values of type {@code A} contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<? extends A> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Positional bulk-insert specialized for {@code boolean} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfBool<U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<Boolean, U>,
                        Plus.InsertAt.OfBool<U> {

                    /**
                     * Inserts the {@code boolean} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, boolean... ary);

                    /**
                     * Inserts the {@code boolean} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<Boolean> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Positional bulk-insert specialized for {@code byte} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI8<U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<Byte, U>,
                        Plus.InsertAt.OfI8<U> {

                    /**
                     * Inserts the {@code byte} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, byte... ary);

                    /**
                     * Inserts the {@code byte} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<Byte> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Positional bulk-insert specialized for {@code short} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI16<U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<Short, U>,
                        Plus.InsertAt.OfI16<U> {

                    /**
                     * Inserts the {@code short} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, short... ary);

                    /**
                     * Inserts the {@code short} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<Short> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Positional bulk-insert specialized for {@code char} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfC16<U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<Character, U>,
                        Plus.InsertAt.OfC16<U> {

                    /**
                     * Inserts the {@code char} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, char... ary);

                    /**
                     * Inserts the {@code char} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<Character> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Positional bulk-insert specialized for {@code int} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI32<U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<Integer, U>,
                        Plus.InsertAt.OfI32<U> {

                    /**
                     * Inserts the {@code int} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, int... ary);

                    /**
                     * Inserts the {@code int} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<Integer> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Positional bulk-insert specialized for {@code long} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfI64<U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<Long, U>,
                        Plus.InsertAt.OfI64<U> {

                    /**
                     * Inserts the {@code long} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, long... ary);

                    /**
                     * Inserts the {@code long} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<Long> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Positional bulk-insert specialized for {@code float} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF32<U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<Float, U>,
                        Plus.InsertAt.OfF32<U> {

                    /**
                     * Inserts the {@code float} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, float... ary);

                    /**
                     * Inserts the {@code float} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<Float> itr);
                }

                // ----------------------------------------------------------

                /**
                 * Positional bulk-insert specialized for {@code double} values.
                 *
                 * @param <U> unification parameter.
                 */
                @Mixin interface OfF64<U extends Bulk.InsertAt<?, U>>
                        extends Bulk.InsertAt<Double, U>,
                        Plus.InsertAt.OfF64<U> {

                    /**
                     * Inserts the {@code double} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param ary array of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, double... ary);

                    /**
                     * Inserts the {@code double} values contained in the given
                     * array starting from the given position. If the position
                     * is outside the index range (0 to N inclusive, where N
                     * is the count before this operation).
                     *
                     * @param pos position of the insert location.
                     * @param itr iterable of values to be inserted.
                     * @return instance of unified type.
                     */
                    U insertAt(long pos, Iterable<Double> itr);
                }
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.MINUS
    // ----------------------------------------------------------

    /**
     * Minus operations to remove specified content.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Minus<A, U extends Minus<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.MINUS.REMOVE
        // ----------------------------------------------------------

        /**
         * Declares the family of remove operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Remove<A, U extends Remove<?, U>> extends Minus<A, U> {

            /**
             * Remove specialized for reference values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Remove<?, U>> extends Remove<A, U> {

                /**
                 * Removes the first occurrence of the given value of type {@code A}.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(A val);
            }

            // ----------------------------------------------------------

            /**
             * Remove specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Remove<?, U>> extends Remove<Boolean, U> {

                /**
                 * Removes the first occurrence of the given {@code boolean} value.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(boolean val);
            }

            // ----------------------------------------------------------

            /**
             * Remove specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Remove<?, U>> extends Remove<Byte, U> {

                /**
                 * Removes the first occurrence of the given {@code byte} value.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(byte val);
            }

            // ----------------------------------------------------------

            /**
             * Remove specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Remove<?, U>> extends Remove<Short, U> {

                /**
                 * Removes the first occurrence of the given {@code short} value.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(short val);
            }

            // ----------------------------------------------------------

            /**
             * Remove specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Remove<?, U>> extends Remove<Character, U> {

                /**
                 * Removes the first occurrence of the given {@code char} value.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(char val);
            }

            // ----------------------------------------------------------

            /**
             * Remove specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Remove<?, U>> extends Remove<Integer, U> {

                /**
                 * Removes the first occurrence of the given {@code int} value.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(int val);
            }

            // ----------------------------------------------------------

            /**
             * Remove specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Remove<?, U>> extends Remove<Long, U> {

                /**
                 * Removes the first occurrence of the given {@code long} value.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(long val);
            }

            // ----------------------------------------------------------

            /**
             * Remove specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Remove<?, U>> extends Remove<Float, U> {

                /**
                 * Removes the first occurrence of the given {@code float} value.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(float val);
            }

            // ----------------------------------------------------------

            /**
             * Remove specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Remove<?, U>> extends Remove<Double, U> {

                /**
                 * Removes the first occurrence of the given {@code double} value.
                 *
                 * @param val value whose first occurrence is to be removed.
                 * @return true if a value has been removed.
                 */
                boolean remove(double val);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.MINUS.REMOVE-ALL
        // ----------------------------------------------------------

        /**
         * Declares the family of remove-all operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface RemoveAll<A, U extends RemoveAll<?, U>> extends Minus<A, U> {

            /**
             * Remove-All specialized for values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends RemoveAll<?, U>> extends RemoveAll<A, U> {

                /**
                 * Removes all occurrences of the given value of type {@code A}.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(A val);
            }

            // ----------------------------------------------------------

            /**
             * Remove-All specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends RemoveAll<?, U>> extends RemoveAll<Boolean, U> {

                /**
                 * Removes all occurrences of the given {@code boolean} value.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(boolean val);
            }

            // ----------------------------------------------------------

            /**
             * Remove-All specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends RemoveAll<?, U>> extends RemoveAll<Byte, U> {

                /**
                 * Removes all occurrences of the given {@code byte} value.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(byte val);
            }

            // ----------------------------------------------------------

            /**
             * Remove-All specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends RemoveAll<?, U>> extends RemoveAll<Short, U> {

                /**
                 * Removes all occurrences of the given {@code short} value.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(short val);
            }

            // ----------------------------------------------------------

            /**
             * Remove-All specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends RemoveAll<?, U>> extends RemoveAll<Character, U> {

                /**
                 * Removes all occurrences of the given {@code char} value.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(char val);
            }

            // ----------------------------------------------------------

            /**
             * Remove-All specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends RemoveAll<?, U>> extends RemoveAll<Integer, U> {

                /**
                 * Removes all occurrences of the given {@code int} value.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(int val);
            }

            // ----------------------------------------------------------

            /**
             * Remove-All specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends RemoveAll<?, U>> extends RemoveAll<Long, U> {

                /**
                 * Removes all occurrences of the given {@code long} value.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(long val);
            }

            // ----------------------------------------------------------

            /**
             * Remove-All specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends RemoveAll<?, U>> extends RemoveAll<Float, U> {

                /**
                 * Removes all occurrences of the given {@code float} value.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(float val);
            }

            // ----------------------------------------------------------

            /**
             * Remove-All specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends RemoveAll<?, U>> extends RemoveAll<Double, U> {

                /**
                 * Removes all occurrences of the given {@code double} value.
                 *
                 * @param val value whose occurrences are to be removed.
                 * @return instance of unified type.
                 */
                U removeAll(double val);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.MINUS.REMOVE-FIRST
        // ----------------------------------------------------------

        /**
         * Declares the family of front removal operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface RemoveFirst<A, U extends RemoveFirst<?, U>> extends Minus<A, U> {

            /**
             * Remove-First specialized for reference values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends RemoveFirst<?, U>> extends RemoveFirst<A, U> {

                /**
                 * Removes and returns the optional first value of type {@code A}
                 * or the empty option if no value is present.
                 */
                Option<A> removeFirst();
            }

            // ----------------------------------------------------------

            /**
             * Remove-First specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends RemoveFirst<?, U>> extends RemoveFirst<Boolean, U> {

                /**
                 * Removes and returns the optional first {@code boolean} value
                 * or the empty option if no value is present.
                 */
                Bool.Option removeFirst();
            }

            // ----------------------------------------------------------

            /**
             * Remove-First specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends RemoveFirst<?, U>> extends RemoveFirst<Byte, U> {

                /**
                 * Removes and returns the optional first {@code byte} value
                 * or the empty option if no value is present.
                 */
                I8.Option removeFirst();
            }

            // ----------------------------------------------------------

            /**
             * Remove-First specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends RemoveFirst<?, U>> extends RemoveFirst<Short, U> {

                /**
                 * Removes and returns the optional first {@code short} value
                 * or the empty option if no value is present.
                 */
                I16.Option removeFirst();
            }

            // ----------------------------------------------------------

            /**
             * Remove-First specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends RemoveFirst<?, U>> extends RemoveFirst<Character, U> {

                /**
                 * Removes and returns the optional first {@code char} value
                 * or the empty option if no value is present.
                 */
                C16.Option removeFirst();
            }

            // ----------------------------------------------------------

            /**
             * Remove-First specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends RemoveFirst<?, U>> extends RemoveFirst<Integer, U> {

                /**
                 * Removes and returns the optional first {@code int} value
                 * or the empty option if no value is present.
                 */
                I32.Option removeFirst();
            }

            // ----------------------------------------------------------

            /**
             * Remove-First specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends RemoveFirst<?, U>> extends RemoveFirst<Long, U> {

                /**
                 * Removes and returns the optional first {@code long} value
                 * or the empty option if no value is present.
                 */
                I64.Option removeFirst();
            }

            // ----------------------------------------------------------

            /**
             * Remove-First specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends RemoveFirst<?, U>> extends RemoveFirst<Float, U> {

                /**
                 * Removes and returns the optional first {@code float} value
                 * or the empty option if no value is present.
                 */
                F32.Option removeFirst();
            }

            // ----------------------------------------------------------

            /**
             * Remove-First specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends RemoveFirst<?, U>> extends RemoveFirst<Double, U> {

                /**
                 * Removes and returns the optional first {@code double} value
                 * or the empty option if no value is present.
                 */
                F64.Option removeFirst();
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.MINUS.REMOVE-LAST
        // ----------------------------------------------------------

        /**
         * Declares the family of tail removal operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface RemoveLast<A, U extends RemoveLast<?, U>> extends Minus<A, U> {

            /**
             * Remove-Last specialized for reference values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends RemoveLast<?, U>> extends RemoveLast<A, U> {

                /**
                 * Removes and returns the optional last value of type {@code A}
                 * or the empty option if no value is present.
                 */
                Option<A> removeLast();
            }

            // ----------------------------------------------------------

            /**
             * Remove-Last specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends RemoveLast<?, U>> extends RemoveLast<Boolean, U> {

                /**
                 * Removes and returns the optional last {@code boolean} value
                 * or the empty option if no value is present.
                 */
                Bool.Option removeLast();
            }

            // ----------------------------------------------------------

            /**
             * Remove-Last specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends RemoveLast<?, U>> extends RemoveLast<Byte, U> {

                /**
                 * Removes and returns the optional last {@code byte} value
                 * or the empty option if no value is present.
                 */
                I8.Option removeLast();
            }

            // ----------------------------------------------------------

            /**
             * Remove-Last specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends RemoveLast<?, U>> extends RemoveLast<Short, U> {

                /**
                 * Removes and returns the optional last {@code short} value
                 * or the empty option if no value is present.
                 */
                I16.Option removeLast();
            }

            // ----------------------------------------------------------

            /**
             * Remove-Last specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends RemoveLast<?, U>> extends RemoveLast<Character, U> {

                /**
                 * Removes and returns the optional last {@code char} value
                 * or the empty option if no value is present.
                 */
                C16.Option removeLast();
            }

            // ----------------------------------------------------------

            /**
             * Remove-Last specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends RemoveLast<?, U>> extends RemoveLast<Integer, U> {

                /**
                 * Removes and returns the optional last {@code int} value
                 * or the empty option if no value is present.
                 */
                I32.Option removeLast();
            }

            // ----------------------------------------------------------

            /**
             * Remove-Last specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends RemoveLast<?, U>> extends RemoveLast<Long, U> {

                /**
                 * Removes and returns the optional last {@code long} value
                 * or the empty option if no value is present.
                 */
                I64.Option removeLast();
            }

            // ----------------------------------------------------------

            /**
             * Remove-Last specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends RemoveLast<?, U>> extends RemoveLast<Float, U> {

                /**
                 * Removes and returns the optional last {@code float} value
                 * or the empty option if no value is present.
                 */
                F32.Option removeLast();
            }

            // ----------------------------------------------------------

            /**
             * Remove-Last specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends RemoveLast<?, U>> extends RemoveLast<Double, U> {

                /**
                 * Removes and returns the optional last {@code double} value
                 * or the empty option if no value is present.
                 */
                F64.Option removeLast();
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.MINUS.REMOVE-AT
        // ----------------------------------------------------------

        /**
         * Declares the family of positional remove operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface RemoveAt<A, U extends RemoveAt<?, U>> extends Minus<A, U> {

            /**
             * Remove-At specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends RemoveAt<?, U>> extends RemoveAt<A, U> {

                /**
                 * Removes and returns the value of type {@code A} at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed value of type {@code A}.
                 */
                A removeAt(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Remove-At specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends RemoveAt<?, U>> extends RemoveAt<Boolean, U> {

                /**
                 * Removes and returns the {@code boolean} value at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed {@code boolean} value.
                 */
                boolean removeAt(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Remove-At specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends RemoveAt<?, U>> extends RemoveAt<Byte, U> {

                /**
                 * Removes and returns the {@code byte} value at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed {@code byte} value.
                 */
                byte removeAt(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Remove-At specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends RemoveAt<?, U>> extends RemoveAt<Short, U> {

                /**
                 * Removes and returns the {@code short} value at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed {@code short} value.
                 */
                short removeAt(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Remove-At specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends RemoveAt<?, U>> extends RemoveAt<Character, U> {

                /**
                 * Removes and returns the {@code char} value at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed {@code char} value.
                 */
                char removeAt(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Remove-At specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends RemoveAt<?, U>> extends RemoveAt<Integer, U> {

                /**
                 * Removes and returns the {@code int} value at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed {@code int} value.
                 */
                int removeAt(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Remove-At specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends RemoveAt<?, U>> extends RemoveAt<Long, U> {

                /**
                 * Removes and returns the {@code long} value at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed {@code long} value.
                 */
                long removeAt(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Remove-At specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends RemoveAt<?, U>> extends RemoveAt<Float, U> {

                /**
                 * Removes and returns the {@code float} value at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed {@code float} value.
                 */
                float removeAt(long idx);
            }

            // ----------------------------------------------------------

            /**
             * Remove-At specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends RemoveAt<?, U>> extends RemoveAt<Double, U> {

                /**
                 * Removes and returns the {@code double} value at the given index.
                 *
                 * @param idx index of the value to be removed.
                 * @return removed {@code double} value.
                 */
                double removeAt(long idx);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.MINUS.REMOVE-RANGE
        // ----------------------------------------------------------

        /**
         * Declares the family of range removal operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface RemoveRange<A, U extends RemoveRange<?, U>> extends Minus<A, U> {

            /**
             * Remove-Range specialized for values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends RemoveRange<?, U>> extends RemoveRange<A, U> {

                /**
                 * Removes all values of type {@code A} within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return container of type {@code A}.
                 */
                U removeRange(long lo, long hi);
            }

            // ----------------------------------------------------------

            /**
             * Remove-Range specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends RemoveRange<?, U>> extends RemoveRange<Boolean, U> {

                /**
                 * Removes all {@code boolean} values within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return {@code boolean} container.
                 */
                U removeRange(long lo, long hi);
            }

            // ----------------------------------------------------------

            /**
             * Remove-Range specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends RemoveRange<?, U>> extends RemoveRange<Byte, U> {

                /**
                 * Removes all {@code byte} values within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return {@code byte} container.
                 */
                U removeRange(long lo, long hi);
            }

            // ----------------------------------------------------------

            /**
             * Remove-Range specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends RemoveRange<?, U>> extends RemoveRange<Short, U> {

                /**
                 * Removes all {@code short} values within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return {@code short} container.
                 */
                U removeRange(long lo, long hi);
            }

            // ----------------------------------------------------------

            /**
             * Remove-Range specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends RemoveRange<?, U>> extends RemoveRange<Character, U> {

                /**
                 * Removes all {@code char} values within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return {@code char} container.
                 */
                U removeRange(long lo, long hi);
            }

            // ----------------------------------------------------------

            /**
             * Remove-Range specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends RemoveRange<?, U>> extends RemoveRange<Integer, U> {

                /**
                 * Removes all {@code int} values within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return {@code int} container.
                 */
                U removeRange(long lo, long hi);
            }

            // ----------------------------------------------------------

            /**
             * Remove-Range specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends RemoveRange<?, U>> extends RemoveRange<Long, U> {

                /**
                 * Removes all {@code long} values within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return {@code long} container.
                 */
                U removeRange(long lo, long hi);
            }

            // ----------------------------------------------------------

            /**
             * Remove-Range specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends RemoveRange<?, U>> extends RemoveRange<Float, U> {

                /**
                 * Removes all {@code float} values within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return {@code float} container.
                 */
                U removeRange(long lo, long hi);
            }

            // ----------------------------------------------------------

            /**
             * Remove-Range specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends RemoveRange<?, U>> extends RemoveRange<Double, U> {

                /**
                 * Removes all {@code double} values within the specified index range.
                 * <p>
                 * A valid index range is defined by a lower bound {@code lo} (inclusive)
                 * and an upper bound {@code hi} (exclusive). Upper and lower bounds must
                 * be within the container index bounds, {@code 0 <= lo < hi <= count}.
                 * The index range is considered empty if {@code lo >= hi}.
                 *
                 * @param lo lower index-bound (inclusive).
                 * @param hi upper index-bound (exclusive).
                 * @return {@code double} container.
                 */
                U removeRange(long lo, long hi);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.MINUS.CLEAR
        // ----------------------------------------------------------

        /**
         * Declares the family of (data structure wide) clear operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Clear<A, U extends Clear<?, U>> extends Minus<A, U> {

            /**
             * Clear specialized for values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Clear<?, U>> extends Clear <A, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }

            // ----------------------------------------------------------

            /**
             * Clear specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Clear<?, U>> extends Clear<Boolean, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }

            // ----------------------------------------------------------

            /**
             * Clear specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Clear<?, U>> extends Clear<Byte, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }

            // ----------------------------------------------------------

            /**
             * Clear specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Clear<?, U>> extends Clear<Short, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }

            // ----------------------------------------------------------

            /**
             * Clear specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Clear<?, U>> extends Clear<Character, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }

            // ----------------------------------------------------------

            /**
             * Clear specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Clear<?, U>> extends Clear<Integer, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }

            // ----------------------------------------------------------

            /**
             * Clear specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Clear<?, U>> extends Clear<Long, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }

            // ----------------------------------------------------------

            /**
             * Clear specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Clear<?, U>> extends Clear<Float, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }

            // ----------------------------------------------------------

            /**
             * Clear specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Clear<?, U>> extends Clear<Double, U> {

                /**
                 * Removes all contained values and returns itself.
                 */
                U clear();
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.TRANSFORM
    // ----------------------------------------------------------

    /**
     * Transformation operations to produce different content with same type.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Transform<A, U extends Transform<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.TRANSFORM.PEEK
        // ----------------------------------------------------------

        /**
         * Defines the family of peek transformations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Peek<A, U extends Peek<?, U>> extends Transform<A, U> {

            /**
             * Peek specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Peek<?, U>> extends Peek<A, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of the
                 * values, additionally performing the provided action
                 * on each value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(Fn1.Consumer<? super A> action);
            }

            // ----------------------------------------------------------

            /**
             * Peek specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Peek<?, U>> extends Peek<Boolean, U> {

                /**
                 * Returns a flow of {@code boolean} values consisting of the values
                 * , additionally performing the provided action on each
                 * value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(Bool.Consumer action);
            }

            // ----------------------------------------------------------

            /**
             * Peek specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Peek<?, U>> extends Peek<Byte, U> {

                /**
                 * Returns a flow of {@code byte} values consisting of the values
                 * , additionally performing the provided action on each
                 * value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(I8.Consumer action);
            }

            // ----------------------------------------------------------

            /**
             * Peek specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Peek<?, U>> extends Peek<Short, U> {

                /**
                 * Returns a flow of {@code short} values consisting of the values
                 * , additionally performing the provided action on each
                 * value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(I16.Consumer action);
            }

            // ----------------------------------------------------------

            /**
             * Peek specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Peek<?, U>> extends Peek<Character, U> {

                /**
                 * Returns a flow of {@code char} values consisting of the values
                 * , additionally performing the provided action on each
                 * value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(C16.Consumer action);
            }

            // ----------------------------------------------------------

            /**
             * Peek specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Peek<?, U>> extends Peek<Integer, U> {

                /**
                 * Returns a flow of {@code int} values consisting of the values
                 * , additionally performing the provided action on each
                 * value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(I32.Consumer action);
            }

            // ----------------------------------------------------------

            /**
             * Peek specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Peek<?, U>> extends Peek<Long, U> {

                /**
                 * Returns a flow of {@code long} values consisting of the values
                 * , additionally performing the provided action on each
                 * value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(I64.Consumer action);
            }

            // ----------------------------------------------------------

            /**
             * Peek specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Peek<?, U>> extends Peek<Float, U> {

                /**
                 * Returns a flow of {@code float} values consisting of the values
                 * , additionally performing the provided action on each
                 * value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(F32.Consumer action);
            }

            // ----------------------------------------------------------

            /**
             * Peek specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Peek<?, U>> extends Peek<Double, U> {

                /**
                 * Returns a flow of {@code double} values consisting of the values
                 * , additionally performing the provided action on each
                 * value as values are consumed from the resulting flow.
                 *
                 * @param action to be performed on each value.
                 * @return instance of unified type.
                 */
                U peek(F64.Consumer action);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSFORM.FILTER
        // ----------------------------------------------------------

        /**
         * Defines the family of filter operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Filter<A, U extends Filter<?, U>> extends Transform<A, U> {

            /**
             * Filter specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Filter<?, U>> extends Filter<A, U> {

                /**
                 * Returns a flow consisting of values that do
                 * satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(Fn1.Predicate<? super A> predicate);

                /**
                 * Returns a flow consisting of values that do NOT
                 * satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine exclusion.
                 * @return instance of unified type.
                 */
                U filterNot(Fn1.Predicate<? super A> predicate);

                /**
                 * Returns a flow consisting of non-null values.
                 *
                 * @return instance of unified type.
                 */
                U filterNull();
            }

            // ----------------------------------------------------------

            /**
             * Filter specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Filter<?, U>> extends Filter<Boolean, U> {

                /**
                 * Returns a flow consisting of {@code boolean} values of
                 * this flow that do satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(Bool.Predicate predicate);

                /**
                 * Returns a flow consisting of {@code boolean} values of
                 * this flow that do NOT satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filterNot(Bool.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Filter specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Filter<?, U>> extends Filter<Byte, U> {

                /**
                 * Returns a flow consisting of {@code byte} values of
                 * this flow that do satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(I8.Predicate predicate);

                /**
                 * Returns a flow consisting of {@code byte} values of
                 * this flow that do NOT satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filterNot(I8.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Filter specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Filter<?, U>> extends Filter<Short, U> {

                /**
                 * Returns a flow consisting of {@code short} values of
                 * this flow that do satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(I16.Predicate predicate);

                /**
                 * Returns a flow consisting of {@code short} values of
                 * this flow that do NOT satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filterNot(I16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Filter specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Filter<?, U>> extends Filter<Character, U> {

                /**
                 * Returns a flow consisting of {@code char} values of
                 * this flow that do satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(C16.Predicate predicate);

                /**
                 * Returns a flow consisting of {@code char} values of
                 * this flow that do NOT satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filterNot(C16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Filter specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Filter<?, U>> extends Filter<Integer, U> {

                /**
                 * Returns a flow consisting of {@code int} values of
                 * this flow that do satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(I32.Predicate predicate);

                /**
                 * Returns a flow consisting of {@code int} values of
                 * this flow that do NOT satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filterNot(I32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Filter specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Filter<?, U>> extends Filter<Long, U> {

                /**
                 * Returns a flow consisting of {@code long} values of
                 * this flow that do satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(I64.Predicate predicate);

                /**
                 * Returns a flow consisting of {@code long} values of
                 * this flow that do NOT satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filterNot(I64.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Filter specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Filter<?, U>> extends Filter<Float, U> {

                /**
                 * Returns a flow consisting of {@code float} values of
                 * this flow that do satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(F32.Predicate predicate);

                /**
                 * Returns a flow consisting of {@code float} values of
                 * this flow that do NOT satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filterNot(F32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Filter specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Filter<?, U>> extends Filter<Double, U> {

                /**
                 * Returns a flow consisting of {@code double} values of
                 * this flow that do satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filter(F64.Predicate predicate);

                /**
                 * Returns a flow consisting of {@code double} values of
                 * this flow that do NOT satisfy the given predicate.
                 *
                 * @param predicate to be applied to each value to determine inclusion.
                 * @return instance of unified type.
                 */
                U filterNot(F64.Predicate predicate);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSFORM.DISTINCT
        // ----------------------------------------------------------

        /**
         * Defines the family of distinct operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Distinct<A, U extends Distinct<?, U>> extends Transform<A, U> {

            /**
             * Distinct specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Distinct<?, U>> extends Distinct<A, U> {

                /**
                 * Returns a flow consisting only of distinct values of type {@code A}
                 * . Among equal values, only the first value will be
                 * present in the resulting flow, in preserved order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }

            // ----------------------------------------------------------

            /**
             * Distinct specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Distinct<?, U>> extends Distinct<Boolean, U> {

                /**
                 * Returns a flow consisting of different {@code boolean} values
                 * . In case of equal values, only the first value is
                 * present in the resulting flow, in unchanged order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }

            // ----------------------------------------------------------

            /**
             * Distinct specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Distinct<?, U>> extends Distinct<Byte, U> {

                /**
                 * Returns a flow consisting of different {@code byte} values
                 * . In case of equal values, only the first value
                 * is present in the resulting flow, in unchanged order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }

            // ----------------------------------------------------------

            /**
             * Distinct specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Distinct<?, U>> extends Distinct<Short, U> {

                /**
                 * Returns a flow consisting of different {@code short} values
                 * . In case of equal values, only the first value
                 * is present in the resulting flow, in unchanged order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }

            // ----------------------------------------------------------

            /**
             * Distinct specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Distinct<?, U>> extends Distinct<Character, U> {

                /**
                 * Returns a flow consisting of different {@code char} values
                 * . In case of equal values, only the first value
                 * is present in the resulting flow, in unchanged order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }

            // ----------------------------------------------------------

            /**
             * Distinct specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Distinct<?, U>> extends Distinct<Integer, U> {

                /**
                 * Returns a flow consisting of different {@code int} values
                 * . In case of equal values, only the first value
                 * is present in the resulting flow, in unchanged order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }

            // ----------------------------------------------------------

            /**
             * Distinct specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Distinct<?, U>> extends Distinct<Long, U> {

                /**
                 * Returns a flow consisting of different {@code long} values
                 * . In case of equal values, only the first value
                 * is present in the resulting flow, in unchanged order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }

            // ----------------------------------------------------------

            /**
             * Distinct specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Distinct<?, U>> extends Distinct<Float, U> {

                /**
                 * Returns a flow consisting of different {@code float} values
                 * . In case of equal values, only the first value
                 * is present in the resulting flow, in unchanged order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }

            // ----------------------------------------------------------

            /**
             * Distinct specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Distinct<?, U>> extends Distinct<Double, U> {

                /**
                 * Returns a flow consisting of different {@code double} values
                 * . In case of equal values, only the first value
                 * is present in the resulting flow, in unchanged order.
                 *
                 * @return instance of unified type.
                 */
                U distinct();
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSFORM.SORT
        // ----------------------------------------------------------

        /**
         * Defines the family of sort operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Sort<A, U extends Sort<?, U>> extends Transform<A, U> {

            /**
             * Sort specialized for values of type {@code A}.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Sort<?, U>> extends Sort<A, U> {

                /**
                 * Returns a flow consisting of the values of type {@code A} of this
                 * flow, sorted according to the given {@link Comparator}.
                 *
                 * @param comparator to be used to compare values of type {@code A}.
                 * @return instance of unified type.
                 */
                U sort(Comparator<? super A> comparator);
            }

            // ----------------------------------------------------------

            /**
             * Sort specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Sort<?, U>> extends Sort<Byte, U> {

                /**
                 * Returns a flow consisting of the {@code byte} values
                 * in sorted order. The values are compared for equality according to
                 * {@link Byte#compare(byte, byte)}.
                 */
                U sort(Sorting order);
            }

            // ----------------------------------------------------------

            /**
             * Sort specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Sort<?, U>> extends Sort<Short, U> {

                /**
                 * Returns a flow consisting of the {@code short} values
                 * in sorted order. The values are compared for equality according to
                 * {@link Short#compare(short, short)}.
                 */
                U sort(Sorting order);
            }

            // ----------------------------------------------------------

            /**
             * Sort specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Sort<?, U>> extends Sort<Character, U> {

                /**
                 * Returns a flow consisting of the {@code char} values
                 * in sorted order. The values are compared for equality according to
                 * {@link Character#compare(char, char)}.
                 */
                U sort(Sorting order);
            }

            // ----------------------------------------------------------

            /**
             * Sort specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Sort<?, U>> extends Sort<Integer, U> {

                /**
                 * Returns a flow consisting of the {@code int} values
                 * in sorted order. The values are compared for equality according to
                 * {@link Integer#compare(int, int)}.
                 */
                U sort(Sorting order);
            }

            // ----------------------------------------------------------

            /**
             * Sort specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Sort<?, U>> extends Sort<Long, U> {

                /**
                 * Returns a flow consisting of the {@code long} values
                 * in sorted order. The values are compared for equality according to
                 * {@link Long#compare(long, long)}.
                 */
                U sort(Sorting order);
            }

            // ----------------------------------------------------------

            /**
             * Sort specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Sort<?, U>> extends Sort<Float, U> {

                /**
                 * Returns a flow consisting of the {@code float} values
                 * in sorted order. The values are compared for equality according to
                 * {@link Float#compare(float, float)}.
                 */
                U sort(Sorting order);
            }

            // ----------------------------------------------------------

            /**
             * Sort specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Sort<?, U>> extends Sort<Double, U> {

                /**
                 * Returns a flow consisting of the {@code double} values
                 * in sorted order. The values are compared for equality according to
                 * {@link Double#compare(double, double)}.
                 */
                U sort(Sorting order);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSFORM.REVERSE
        // ----------------------------------------------------------

        /**
         * Defines the family of reverse transformation.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Reverse<A, U extends Reverse<?, U>> extends Transform<A, U> {

            /**
             * Reverse transformation specialized for values of {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Reverse<?, U>> extends Reverse<A, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }

            // ----------------------------------------------------------

            /**
             * Reverse transformation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Reverse<?, U>> extends Reverse<Boolean, U> {

                /**
                 * Returns a flow of {@code boolean} values consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }

            // ----------------------------------------------------------

            /**
             * Reverse transformation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Reverse<?, U>> extends Reverse<Byte, U> {

                /**
                 * Returns a flow of {@code byte} values consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }

            // ----------------------------------------------------------

            /**
             * Reverse transformation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Reverse<?, U>> extends Reverse<Short, U> {

                /**
                 * Returns a flow of {@code short} values consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }

            // ----------------------------------------------------------

            /**
             * Reverse transformation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Reverse<?, U>> extends Reverse<Character, U> {

                /**
                 * Returns a flow of {@code char} values consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }

            // ----------------------------------------------------------

            /**
             * Reverse transformation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Reverse<?, U>> extends Reverse<Integer, U> {

                /**
                 * Returns a flow of {@code int} values consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }

            // ----------------------------------------------------------

            /**
             * Reverse transformation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Reverse<?, U>> extends Reverse<Long, U> {

                /**
                 * Returns a flow of {@code long} values consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }

            // ----------------------------------------------------------

            /**
             * Reverse transformation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Reverse<?, U>> extends Reverse<Float, U> {

                /**
                 * Returns a flow of {@code float} values consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }

            // ----------------------------------------------------------

            /**
             * Reverse transformation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Reverse<?, U>> extends Reverse<Double, U> {

                /**
                 * Returns a flow of {@code double} values consisting
                 * of the values in reverse order.
                 */
                U reverse();
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.TRANSMUTE
    // ----------------------------------------------------------

    /**
     * Transmutation operations to produce content with a different type.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Transmute<A, U extends Transmute<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.TRANSMUTE.MAP
        // ----------------------------------------------------------

        /**
         * Map transmutation.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Map<A, U extends Map<?, U>> extends Transmute<A, U> {

            /**
             * Map operation specialized for values of {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Map<?, U>> extends Map<A, U> {

                /**
                 * Returns a flow of values of type {@code B} consisting of
                 * the results obtained by applying the given mapper function
                 * to each value of type {@code A}.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <B> Of<B, U> map(Fn1<? super A, ? extends B> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Map operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Map<?, U>> extends Map<Boolean, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code boolean} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> map(Bool.To<? extends A> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Map operation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Map<?, U>> extends Map<Byte, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code byte} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> map(I8.To<? extends A> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Map operation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Map<?, U>> extends Map<Short, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> map(I16.To<? extends A> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Map operation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Map<?, U>> extends Map<Character, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code char} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> map(C16.To<? extends A> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Map operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Map<?, U>> extends Map<Integer, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code int} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> map(I32.To<? extends A> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Map operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Map<?, U>> extends Map<Long, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code long} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> map(I64.To<? extends A> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Map operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Map<?, U>> extends Map<Float, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code float} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> map(F32.To<? extends A> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Map operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Map<?, U>> extends Map<Double, U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code double} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> map(F64.To<? extends A> mapper);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSMUTE.POLY-MAP
        // ----------------------------------------------------------

        /**
         * Poly map transmutation.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface PolyMap<A, U extends PolyMap<?, U>> extends Map<A, U> {

            /**
             * Poly map operation specialized for values of {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends PolyMap<?, U>> extends PolyMap<A, U>, Map.Of<A, U> {

                /**
                 * Returns a flow of values of type {@code B} consisting of
                 * the results obtained by applying the given mapper function
                 * to each value of type {@code A}.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <B> PolyMap.Of<B, U> map(Fn1<? super A, ? extends B> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each value of type {@code A}.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(Fn1.ToI32<? super A> mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each value of type {@code A}.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(Fn1.ToI64<? super A> mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each value of type {@code A}.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(Fn1.ToF32<? super A> mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each value of type {@code A}.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(Fn1.ToF64<? super A> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly map operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends PolyMap<?, U>> extends PolyMap<Boolean, U>, Map.OfBool<U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code boolean} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyMap.Of<A, U> map(Bool.To<? extends A> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code boolean} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(Bool.ToI32 mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code boolean} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(Bool.ToI64 mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code boolean} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(Bool.ToF32 mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code boolean} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(Bool.ToF64 mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly map operation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends PolyMap<?, U>> extends PolyMap<Byte, U>, Map.OfI8<U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code byte} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyMap.Of<A, U> map(I8.To<? extends A> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code byte} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(I8.ToI32 mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code byte} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(I8.ToI64 mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code byte} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(I8.ToF32 mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code byte} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(I8.ToF64 mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly map operation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends PolyMap<?, U>> extends PolyMap<Short, U>, Map.OfI16<U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyMap.Of<A, U> map(I16.To<? extends A> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(I16.ToI32 mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(I16.ToI64 mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(I16.ToF32 mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(I16.ToF64 mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly map operation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends PolyMap<?, U>> extends PolyMap<Character, U>, Map.OfC16<U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyMap.Of<A, U> map(C16.To<? extends A> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code char} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(C16.ToI32 mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code char} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(C16.ToI64 mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(C16.ToF32 mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code short} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(C16.ToF64 mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly map operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends PolyMap<?, U>> extends PolyMap<Integer, U>, Map.OfI32<U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code int} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyMap.Of<A, U> map(I32.To<? extends A> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code int} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(I32.ToI32 mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code int} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(I32.ToI64 mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code int} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(I32.ToF32 mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code int} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(I32.ToF64 mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly map operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends PolyMap<?, U>> extends PolyMap<Long, U>, Map.OfI64<U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code long} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyMap.Of<A, U> map(I64.To<? extends A> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code long} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(I64.ToI32 mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code long} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(I64.ToI64 mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code int} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(I64.ToF32 mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code long} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(I64.ToF64 mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly map operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends PolyMap<?, U>> extends PolyMap<Float, U>, Map.OfF32<U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code float} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyMap.Of<A, U> map(F32.To<? extends A> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code float} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(F32.ToI32 mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code long} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(F32.ToI64 mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code float} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(F32.ToF32 mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code float} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(F32.ToF64 mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly map operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends PolyMap<?, U>> extends PolyMap<Double, U>, Map.OfF64<U> {

                /**
                 * Returns a flow of values of type {@code A} consisting of
                 * the results obtained by applying the given mapper function
                 * to each {@code double} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyMap.Of<A, U> map(F64.To<? extends A> mapper);

                /**
                 * Returns a flow of {@code int} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code double} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI32<U> mapI32(F64.ToI32 mapper);

                /**
                 * Returns a flow of {@code long} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code double} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfI64<U> mapI64(F64.ToI64 mapper);

                /**
                 * Returns a flow of {@code float} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code double} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF32<U> mapF32(F64.ToF32 mapper);

                /**
                 * Returns a flow of {@code double} values consisting of the
                 * results obtained by applying the given mapper function
                 * to each {@code double} value.
                 *
                 * @param mapper function to be applied to each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyMap.OfF64<U> mapF64(F64.ToF64 mapper);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSMUTE.FILTER-MAP
        // ----------------------------------------------------------

        /**
         * TODO
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface FilterMap<A, U extends FilterMap<?, U>> extends Transform.Filter<A, U>, PolyMap<A, U> {

            /**
             * Filter-map operation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends FilterMap<?, U>> extends FilterMap<A, U>, Filter.Of<A, U>, PolyMap.Of<A, U> {

                /**
                 * Returns a flow of type {@code B} consisting of assignment
                 * compatible values. Assignment compatibility
                 * is determined via the given class object.
                 *
                 * @param type to which values must be assignable in order to be included.
                 * @return flow of values of type {@code B}.
                 */
                <B> FilterMap<B, U> filter(Class<? extends B> type);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSMUTE.FLAT-MAP
        // ----------------------------------------------------------

        /**
         * Flat-map operation transmutation.
         *
         * @param <U> unification parameter.
         */
        interface FlatMap<A, U extends FlatMap<?, U>> extends Transmute<A, U> {

            /**
             * Flat-map operation specialized for values of {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends FlatMap<?, U>> extends FlatMap<A, U> {

                /**
                 * Returns the values of type {@code B} produced by applying the given mapping
                 * function to each value of type {@code A} and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <B> Of<B, U> flatMap(Fn1<? super A, ? extends Iterable<? extends B>> mapper);
            }
            // ----------------------------------------------------------

            /**
             * Flat-map operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends FlatMap<?, U>> extends FlatMap<Boolean, U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code boolean} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> flatMap(Bool.To<? extends Iterable<? extends A>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Flat-map operation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends FlatMap<?, U>> extends FlatMap<Byte, U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code byte} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> flatMap(I8.To<? extends Iterable<? extends A>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Flat-map operation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends FlatMap<?, U>> extends FlatMap<Short, U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code short} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> flatMap(I16.To<? extends Iterable<? extends A>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Flat-map operation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends FlatMap<?, U>> extends FlatMap<Character, U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code char} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> flatMap(C16.To<? extends Iterable<? extends A>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Flat-map operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends FlatMap<?, U>> extends FlatMap<Integer, U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code int} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> flatMap(I32.To<? extends Iterable<? extends A>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Flat-map operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends FlatMap<?, U>> extends FlatMap<Long, U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code long} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> flatMap(I64.To<? extends Iterable<? extends A>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Flat-map operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends FlatMap<?, U>> extends FlatMap<Float, U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code float} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> flatMap(F32.To<? extends Iterable<? extends A>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Flat-map operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends FlatMap<?, U>> extends FlatMap<Double, U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code double} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> Of<A, U> flatMap(F64.To<? extends Iterable<? extends A>> mapper);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSMUTE.POLY-FLAT-MAP
        // ----------------------------------------------------------

        /**
         * Poly flat-map operation that refines {@link FlatMap}.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface PolyFlatMap<A, U extends PolyFlatMap<?, U>> extends FlatMap<A, U> {

            /**
             * Poly flat-map operation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            interface Of<A, U extends PolyFlatMap<?, U>> extends PolyFlatMap<A, U>, FlatMap.Of<A, U> {

                /**
                 * Returns the values of type {@code B} produced by applying the given mapping
                 * function to each value of type {@code A} and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <B> PolyFlatMap.Of<B, U> flatMap(Fn1<? super A, ? extends Iterable<? extends B>> mapper);

                /**
                 * Returns the {@code int} values produced by applying the given mapping
                 * function to each value of type {@code A} and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(Fn1<? super A, ? extends Iterable<Integer>> mapper);

                /**
                 * Returns the {@code long} values produced by applying the given mapping
                 * function to each value of type {@code A} and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(Fn1<? super A, ? extends Iterable<Long>> mapper);

                /**
                 * Returns the {@code float} values produced by applying the given mapping
                 * function to each value of type {@code A} and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(Fn1<? super A, ? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each value of type {@code A} and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(Fn1<? super A, ? extends Iterable<Double>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly flat-map operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            interface OfBool<U extends PolyFlatMap<?, U>> extends PolyFlatMap<Boolean, U>, FlatMap.OfBool<U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code boolean} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyFlatMap.Of<A, U> flatMap(Bool.To<? extends Iterable<? extends A>> mapper);

                /**
                 * Returns the {@code int} values produced by applying the given mapping
                 * function to each {@code boolean} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(Bool.To<? extends Iterable<Integer>> mapper);

                /**
                 * Returns the {@code long} values produced by applying the given mapping
                 * function to each {@code boolean} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(Bool.To<? extends Iterable<Long>> mapper);

                /**
                 * Returns the {@code float} values produced by applying the given mapping
                 * function to each {@code boolean} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(Bool.To<? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each {@code boolean} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(Bool.To<? extends Iterable<Double>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly flat-map transmutation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI8<U extends PolyFlatMap<?, U>> extends PolyFlatMap<Byte, U>, FlatMap.OfI8<U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code byte} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyFlatMap.Of<A, U> flatMap(I8.To<? extends Iterable<? extends A>> mapper);

                /**
                 * Returns the {@code int} values produced by applying the given mapping
                 * function to each {@code byte} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(I8.To<? extends Iterable<Integer>> mapper);

                /**
                 * Returns the {@code long} values produced by applying the given mapping
                 * function to each {@code byte} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(I8.To<? extends Iterable<Long>> mapper);

                /**
                 * Returns the {@code float} values produced by applying the given mapping
                 * function to each {@code byte} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(I8.To<? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each {@code byte} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(I8.To<? extends Iterable<Double>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly flat-map transmutation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI16<U extends PolyFlatMap<?, U>> extends PolyFlatMap<Short, U>, FlatMap.OfI16<U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code short} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyFlatMap.Of<A, U> flatMap(I16.To<? extends Iterable<? extends A>> mapper);

                /**
                 * Returns the {@code int} values produced by applying the given mapping
                 * function to each {@code short} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(I16.To<? extends Iterable<Integer>> mapper);

                /**
                 * Returns the {@code long} values produced by applying the given mapping
                 * function to each {@code short} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(I16.To<? extends Iterable<Long>> mapper);

                /**
                 * Returns the {@code float} values produced by applying the given mapping
                 * function to each {@code short} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(I16.To<? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each {@code short} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(I16.To<? extends Iterable<Double>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly flat-map transmutation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            interface OfC16<U extends PolyFlatMap<?, U>> extends PolyFlatMap<Character, U>, FlatMap.OfC16<U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code char} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyFlatMap.Of<A, U> flatMap(C16.To<? extends Iterable<? extends A>> mapper);

                /**
                 * Returns a flow consisting of the results of replacing each
                 * {@code char} value with the contents of a mapped Iterable
                 * produced by applying the given mapping function to each value.
                 * <p>
                 * The flatMap operation has the effect of applying a one-to-many
                 * transformation to the contained {@code char} values, and then
                 * flattening the resulting {@code int} values into a new flow.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(C16.To<? extends Iterable<Integer>> mapper);

                /**
                 * Returns a flow consisting of the results of replacing each
                 * {@code char} value with the contents of a mapped Iterable
                 * produced by applying the given mapping function to each value.
                 * <p>
                 * The flatMap operation has the effect of applying a one-to-many
                 * transformation to the contained {@code char} values, and then
                 * flattening the resulting {@code long} values into a new flow.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(C16.To<? extends Iterable<Long>> mapper);

                /**
                 * Returns a flow consisting of the results of replacing each
                 * {@code char} value with the contents of a mapped Iterable
                 * produced by applying the given mapping function to each value.
                 * <p>
                 * The flatMap operation has the effect of applying a one-to-many
                 * transformation to the contained {@code char} values, and then
                 * flattening the resulting {@code float} values into a new flow.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(C16.To<? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each {@code char} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(C16.To<? extends Iterable<Double>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly flat-map transmutation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI32<U extends PolyFlatMap<?, U>> extends PolyFlatMap<Integer, U>, FlatMap.OfI32<U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code int} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyFlatMap.Of<A, U> flatMap(I32.To<? extends Iterable<? extends A>> mapper);

                /**
                 * Returns the {@code int} values produced by applying the given mapping
                 * function to each {@code int} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(I32.To<? extends Iterable<Integer>> mapper);

                /**
                 * Returns the {@code long} values produced by applying the given mapping
                 * function to each {@code int} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(I32.To<? extends Iterable<Long>> mapper);

                /**
                 * Returns the {@code float} values produced by applying the given mapping
                 * function to each {@code int} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(I32.To<? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each {@code int} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(I32.To<? extends Iterable<Double>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly flat-map transmutation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            interface OfI64<U extends PolyFlatMap<?, U>> extends PolyFlatMap<Long, U>, FlatMap.OfI64<U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given
                 * mapping function to each {@code long} value and replacing it with
                 * the contents of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyFlatMap.Of<A, U> flatMap(I64.To<? extends Iterable<? extends A>> mapper);

                /**
                 * Returns the {@code int} values produced by applying the given mapping
                 * function to each {@code long} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(I64.To<? extends Iterable<Integer>> mapper);

                /**
                 * Returns the {@code long} values produced by applying the given mapping
                 * function to each {@code long} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(I64.To<? extends Iterable<Long>> mapper);

                /**
                 * Returns the {@code float} values produced by applying the given mapping
                 * function to each {@code long} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(I64.To<? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each {@code long} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(I64.To<? extends Iterable<Double>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly flat-map transmutation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            interface OfF32<U extends PolyFlatMap<?, U>> extends PolyFlatMap<Float, U>, FlatMap.OfF32<U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code float} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyFlatMap.Of<A, U> flatMap(F32.To<? extends Iterable<? extends A>> mapper);

                /**
                 * Returns the {@code int} values produced by applying the given mapping
                 * function to each {@code float} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(F32.To<? extends Iterable<Integer>> mapper);

                /**
                 * Returns the {@code long} values produced by applying the given mapping
                 * function to each {@code float} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(F32.To<? extends Iterable<Long>> mapper);

                /**
                 * Returns the {@code float} values produced by applying the given mapping
                 * function to each {@code float} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(F32.To<? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each {@code float} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(F32.To<? extends Iterable<Double>> mapper);
            }

            // ----------------------------------------------------------

            /**
             * Poly flat-map transmutation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            interface OfF64<U extends PolyFlatMap<?, U>> extends PolyFlatMap<Double, U>, FlatMap.OfF64<U> {

                /**
                 * Returns the values of type {@code A} produced by applying the given mapping
                 * function to each {@code double} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                <A> PolyFlatMap.Of<A, U> flatMap(F64.To<? extends Iterable<? extends A>> mapper);

                /**
                 * Returns the {@code int} values produced by applying the given mapping
                 * function to each {@code double} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI32<U> flatMapI32(F64.To<? extends Iterable<Integer>> mapper);

                /**
                 * Returns the {@code long} values produced by applying the given mapping
                 * function to each {@code double} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfI64<U> flatMapI64(F64.To<? extends Iterable<Long>> mapper);

                /**
                 * Returns the {@code float} values produced by applying the given mapping
                 * function to each {@code double} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF32<U> flatMapF32(F64.To<? extends Iterable<Float>> mapper);

                /**
                 * Returns the {@code double} values produced by applying the given mapping
                 * function to each {@code double} value and replacing it with the contents
                 * of the mapped iterable.
                 * <p>
                 * This operation has the effect of applying a one-to-many transformation
                 * to the contained values and then flattening the produced results.
                 *
                 * @param mapper function to be applied on each value.
                 * @return instance of subtype of {@code U}.
                 */
                PolyFlatMap.OfF64<U> flatMapF64(F64.To<? extends Iterable<Double>> mapper);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.TRANSMUTE.FLATTEN
        // ----------------------------------------------------------

        /**
         * TODO
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Flatten<A, U extends Flatten<?, U>> extends Transmute<A, U> {

            /**
             * Flatten operation specialized for values of {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Flatten<?, U>> extends Flatten<A, U> {
                // TODO
            }

            // ----------------------------------------------------------

            /**
             * Flatten operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Flatten<?, U>> extends Flatten<Boolean, U> {
                // TODO
            }

            // ----------------------------------------------------------

            /**
             * Flatten operation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Flatten<?, U>> extends Flatten<Byte, U> {
                // TODO
            }

            // ----------------------------------------------------------

            /**
             * Flatten operation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Flatten<?, U>> extends Flatten<Short, U> {
                // TODO
            }

            // ----------------------------------------------------------

            /**
             * Flatten operation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Flatten<?, U>> extends Flatten<Character, U> {
                // TODO
            }

            // ----------------------------------------------------------

            /**
             * Flatten operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Flatten<?, U>> extends Flatten<Integer, U> {
                // TODO
            }

            // ----------------------------------------------------------

            /**
             * Flatten operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Flatten<?, U>> extends Flatten<Long, U> {
                // TODO
            }

            // ----------------------------------------------------------

            /**
             * Flatten operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Flatten<?, U>> extends Flatten<Float, U> {
                // TODO
            }

            // ----------------------------------------------------------

            /**
             * Flatten operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Flatten<?, U>> extends Flatten<Double, U> {
                // TODO
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.PARTITION
    // ----------------------------------------------------------

    /**
     * Partition operations to divide into separated parts.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Partition<A, U extends Partition<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.PARTITION.TAKE
        // ----------------------------------------------------------

        /**
         * Defines the family of take operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Take<A, U extends Take<?, U>> extends Partition<A, U> {

            /**
             * Returns the prefix values with a max, length of {@code count} values.
             *
             * @param count number of values limited to.
             * @return instance of unification parameter.
             */
            U take(long count);

            // ----------------------------------------------------------

            /**
             * Take operation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Take<?, U>> extends Take<A, U> {

                /**
                 * Returns the longest prefix of values of type {@code A} that satisfy the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U takeWhile(Fn1.Predicate<? super A> predicate);
            }

            // ----------------------------------------------------------

            /**
             * Take operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Take<?, U>> extends Take<Integer, U> {

                /**
                 * Returns the longest prefix of {@code int} values that satisfy the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U takeWhile(I32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Take operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Take<?, U>> extends Take<Long, U> {

                /**
                 * Returns the longest prefix of {@code long} values that satisfy the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U takeWhile(I64.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Take operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Take<?, U>> extends Take<Float, U> {

                /**
                 * Returns the longest prefix of {@code float} values that satisfy the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U takeWhile(F32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Take operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Take<?, U>> extends Take<Double, U> {

                /**
                 * Returns the longest prefix of {@code double} values that satisfy the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U takeWhile(F64.Predicate predicate);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.PARTITION.DROP
        // ----------------------------------------------------------

        /**
         * Defines the family of drop operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Drop<A, U extends Drop<?, U>> extends Partition<A, U> {

            /**
             * Returns all values except the first {@code count} number.
             *
             * @param count number of values to be dropped.
             * @return instance of unification parameter.
             */
            U drop(long count);

            // ----------------------------------------------------------

            /**
             * Drop operation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Drop<?, U>> extends Drop<A, U> {

                /**
                 * Returns all remaining values of type {@code A} after dropping the longest prefix
                 * satisfying the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U dropWhile(Fn1.Predicate<? super A> predicate);
            }

            // ----------------------------------------------------------

            /**
             * Drop operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Drop<?, U>> extends Drop<Integer, U> {

                /**
                 * Returns all remaining {@code int} values after dropping the longest prefix
                 * satisfying the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U dropWhile(I32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Drop operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Drop<?, U>> extends Drop<Long, U> {

                /**
                 * Returns all remaining {@code long} values after dropping the longest prefix
                 * satisfying the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U dropWhile(I64.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Drop operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Drop<?, U>> extends Drop<Float, U> {

                /**
                 * Returns all remaining {@code float} values after dropping the longest prefix
                 * satisfying the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U dropWhile(F32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Drop operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Drop<?, U>> extends Drop<Double, U> {

                /**
                 * Returns all remaining {@code double} values after dropping the longest prefix
                 * satisfying the given predicate.
                 *
                 * @param predicate to determine the longest prefix of values.
                 * @return instance of unification parameter.
                 */
                U dropWhile(F64.Predicate predicate);
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.FOLD
    // ----------------------------------------------------------

    /**
     * Fold operations to produce a summary value.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Fold<A, U extends Fold<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.FOLD.FOLD-LEFT
        // ----------------------------------------------------------

        /**
         * Declares the family of fold-left operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface FoldLeft<A, U extends FoldLeft<?, U>> extends Fold<A, U> {

            /**
             * Fold-Left operation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends FoldLeft<?, U>> extends FoldLeft<A, U> {

                /**
                 * Performs a left fold on the contained values of type {@code A} using
                 * the given combining function. If no values are present, the initial
                 * value accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn2<Z, ? super A, Z> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-Left operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends FoldLeft<?, U>> extends FoldLeft<Boolean, U> {

                /**
                 * Performs a left fold on the contained {@code boolean} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn1<Z, Bool.To<Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-Left operation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends FoldLeft<?, U>> extends FoldLeft<Byte, U> {

                /**
                 * Performs a left fold on the contained {@code byte} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn1<Z, I8.To<Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-Left operation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends FoldLeft<?, U>> extends FoldLeft<Short, U> {

                /**
                 * Performs a left fold on the contained {@code short} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn1<Z, I16.To<Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-Left operation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends FoldLeft<?, U>> extends FoldLeft<Character, U> {

                /**
                 * Performs a left fold on the contained {@code char} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn1<Z, C16.To<Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-Left operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends FoldLeft<?, U>> extends FoldLeft<Integer, U> {

                /**
                 * Performs a left fold on the contained {@code int} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn1<Z, I32.To<Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-Left operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends FoldLeft<?, U>> extends FoldLeft<Long, U> {

                /**
                 * Performs a left fold on the contained {@code long} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn1<Z, I64.To<Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-Left operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends FoldLeft<?, U>> extends FoldLeft<Float, U> {

                /**
                 * Performs a left fold on the contained {@code float} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn1<Z, F32.To<Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-Left operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends FoldLeft<?, U>> extends FoldLeft<Double, U> {

                /**
                 * Performs a left fold on the contained {@code double} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A left fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from left to right, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldLeft(Z initial, Fn1<Z, F64.To<Z>> combiner);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.FOLD.FOLD-RIGHT
        // ----------------------------------------------------------

        /**
         * Declares the family of fold-right operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface FoldRight<A, U extends FoldRight<?, U>> extends Fold<A, U> {

            /**
             * Fold-right operation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends FoldRight<?, U>> extends FoldRight<A, U> {

                /**
                 * Performs a right fold on the contained values of type {@code A} using
                 * the given combining function. If no values are present, the initial
                 * value accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, Fn2<? super A, Z, Z> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-right operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends FoldRight<?, U>> extends FoldRight<Boolean, U> {

                /**
                 * Performs a right fold on the contained {@code boolean} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, Bool.To<Fn1<Z, Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-right operation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends FoldRight<?, U>> extends FoldRight<Byte, U> {

                /**
                 * Performs a right fold on the contained {@code byte} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, I8.To<Fn1<Z, Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-right operation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends FoldRight<?, U>> extends FoldRight<Short, U> {

                /**
                 * Performs a right fold on the contained {@code short} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, I16.To<Fn1<Z, Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-right operation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends FoldRight<?, U>> extends FoldRight<Character, U> {

                /**
                 * Performs a right fold on the contained {@code char} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, C16.To<Fn1<Z, Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-right operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends FoldRight<?, U>> extends FoldRight<Integer, U> {

                /**
                 * Performs a right fold on the contained {@code int} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, I32.To<Fn1<Z, Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-right operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends FoldRight<?, U>> extends FoldRight<Long, U> {

                /**
                 * Performs a right fold on the contained {@code long} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, I64.To<Fn1<Z, Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-right operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends FoldRight<?, U>> extends FoldRight<Float, U> {

                /**
                 * Performs a right fold on the contained {@code float} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, F32.To<Fn1<Z, Z>> combiner);
            }

            // ----------------------------------------------------------

            /**
             * Fold-right operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends FoldRight<?, U>> extends FoldRight<Double, U> {

                /**
                 * Performs a right fold on the contained {@code double} values using the
                 * given combining function. If no values are present, the initial value
                 * accumulator is returned.
                 * <p>
                 * A right fold operation starts with the given initial value and applies
                 * the given combining function to the current accumulator state and each
                 * contained value, from right to left, to compute the accumulated result.
                 *
                 * @param initial  accumulator value.
                 * @param combiner to integrate values into the accumulator.
                 * @return accumulated result of type {@code Z}.
                 */
                <Z> Z foldRight(Z initial, F64.To<Fn1<Z, Z>> combiner);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.FOLD.REDUCE
        // ----------------------------------------------------------

        /**
         * Declares the family of associative reduce operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Reduce<A, U extends Reduce<?, U>> extends Fold<A, U> {

            /**
             * Reduce operation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Reduce<?, U>> extends Reduce<A, U> {

                /**
                 * Returns the reduced value of type {@code A} obtained by applying
                 * the given accumulator function to the current accumulator value
                 * and each contained value, going from right to left, starting with
                 * the given identity value.
                 * <p>
                 * Parallel implementations require an associative accumulator function
                 * with an identity value that the following holds {@code ∀(x):
                 * accumulator.apply(identity, x).equals(x)}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return reduction result of type {@code A}.
                 */
                A reduce(A identity, Fn2<A, A, A> accumulator);

                /**
                 * Returns the reduced value of type {@code A} obtained by applying
                 * the given accumulator function to the current accumulator value
                 * and each contained value, going from right to left, starting with
                 * the first contained value.
                 * <p>
                 * Parallel implementations require an associative accumulator function
                 * with an identity value that the following holds {@code ∀(x):
                 * accumulator.apply(identity, x).equals(x)}.
                 *
                 * @param accumulator function for combining two values.
                 * @return reduction result of type {@code A}.
                 */
                Option<A> reduce(Fn2<A, A, A> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Reduce operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Reduce<?, U>> extends Reduce<Boolean, U> {

                /**
                 * Returns the reduced {@code boolean} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the given
                 * identity value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return {@code boolean} reduction result.
                 */
                boolean reduce(boolean identity, Bool.To<Bool.ToBool> accumulator);

                /**
                 * Returns the reduced {@code boolean} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the first
                 * contained value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param accumulator function for combining two values.
                 * @return optional {@code boolean} reduction result.
                 */
                Bool.Option reduce(Bool.To<Bool.ToBool> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Reduce operation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Reduce<?, U>> extends Reduce<Byte, U> {

                /**
                 * Returns the reduced {@code byte} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the given
                 * identity value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return {@code byte} reduction result.
                 */
                byte reduce(byte identity, I8.To<I8.ToI8> accumulator);

                /**
                 * Returns the reduced {@code byte} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the first
                 * contained value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param accumulator function for combining two values.
                 * @return optional {@code byte} reduction result.
                 */
                I8.Option reduce(I8.To<I8.ToI8> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Reduce operation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Reduce<?, U>> extends Reduce<Short, U> {

                /**
                 * Returns the reduced {@code short} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the given
                 * identity value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return {@code short} reduction result.
                 */
                short reduce(short identity, I16.To<I16.ToI16> accumulator);

                /**
                 * Returns the reduced {@code short} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the first
                 * contained value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param accumulator function for combining two values.
                 * @return optional {@code short} reduction result.
                 */
                I16.Option reduce(I16.To<I16.ToI16> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Reduce operation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Reduce<?, U>> extends Reduce<Character, U> {

                /**
                 * Returns the reduced {@code char} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the given
                 * identity value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return {@code char} reduction result.
                 */
                char reduce(char identity, C16.To<C16.ToC16> accumulator);

                /**
                 * Returns the reduced {@code char} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the first
                 * contained value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param accumulator function for combining two values.
                 * @return optional {@code char} reduction result.
                 */
                C16.Option reduce(C16.To<C16.ToC16> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Reduce operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Reduce<?, U>> extends Reduce<Integer, U> {

                /**
                 * Returns the reduced {@code int} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the given
                 * identity value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return {@code int} reduction result.
                 */
                int reduce(int identity, I32.To<I32.ToI32> accumulator);

                /**
                 * Returns the reduced {@code int} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the first
                 * contained value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param accumulator function for combining two values.
                 * @return optional {@code int} reduction result.
                 */
                I32.Option reduce(I32.To<I32.ToI32> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Reduce operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Reduce<?, U>> extends Reduce<Long, U> {

                /**
                 * Returns the reduced {@code long} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the given
                 * identity value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return {@code long} reduction result.
                 */
                long reduce(long identity, I64.To<I64.ToI64> accumulator);

                /**
                 * Returns the reduced {@code long} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the first
                 * contained value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param accumulator function for combining two values.
                 * @return optional {@code long} reduction result.
                 */
                I64.Option reduce(I64.To<I64.ToI64> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Reduce operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Reduce<?, U>> extends Reduce<Float, U> {

                /**
                 * Returns the reduced {@code float} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the given
                 * identity value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return {@code float} reduction result.
                 */
                float reduce(float identity, F32.To<F32.ToF32> accumulator);

                /**
                 * Returns the reduced {@code float} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the first
                 * contained value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param accumulator function for combining two values.
                 * @return optional {@code float} reduction result.
                 */
                F32.Option reduce(F32.To<F32.ToF32> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Reduce operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Reduce<?, U>> extends Reduce<Double, U> {

                /**
                 * Returns the reduced {@code double} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the given
                 * identity value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param identity    value of the passed {@code accumulator}.
                 * @param accumulator function for combining two values.
                 * @return {@code double} reduction result.
                 */
                double reduce(double identity, F64.To<F64.ToF64> accumulator);

                /**
                 * Returns the reduced {@code double} value obtained by applying the
                 * given accumulator function to the current accumulator value and each
                 * contained value, going from right to left, starting with the first
                 * contained value.
                 * <p>
                 * Parallel implementations require the given accumulator function
                 * to be associative and an identity value for that the following
                 * holds {@code ∀(x): accumulator.apply(identity, x) == x}.
                 *
                 * @param accumulator function for combining two values.
                 * @return optional {@code double} reduction result.
                 */
                F64.Option reduce(F64.To<F64.ToF64> accumulator);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.FOLD.COLLECT
        // ----------------------------------------------------------

        /**
         * Declares the family of collect operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Collect<A, U extends Collect<?, U>> extends Fold<A, U> {

            /**
             * Collect operation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Collect<?, U>> extends Collect<A, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained values of type {@code A}. The container is created using
                 * the given factory. The contained values are integrated using the
                 * given accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn2.Consumer<C, ? super A> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Collect operation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Collect<?, U>> extends Collect<Boolean, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained {@code boolean} values. The container is created using
                 * the given factory. The values are integrated using the given
                 * accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn1<C, Bool.Consumer> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Collect operation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Collect<?, U>> extends Collect<Byte, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained {@code byte} values. The container is created using
                 * the given factory. The values are integrated using the given
                 * accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn1<C, I8.Consumer> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Collect operation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Collect<?, U>> extends Collect<Short, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained {@code short} values. The container is created using
                 * the given factory. The values are integrated using the given
                 * accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn1<C, I16.Consumer> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Collect operation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Collect<?, U>> extends Collect<Character, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained {@code char} values. The container is created using
                 * the given factory. The values are integrated using the given
                 * accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn1<C, C16.Consumer> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Collect operation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Collect<?, U>> extends Collect<Integer, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained {@code int} values. The container is created using
                 * the given factory. The values are integrated using the given
                 * accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn1<C, I32.Consumer> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Collect operation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Collect<?, U>> extends Collect<Long, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained {@code long} values. The container is created using
                 * the given factory. The values are integrated using the given
                 * accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn1<C, I64.Consumer> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Collect operation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Collect<?, U>> extends Collect<Float, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained {@code float} values. The container is created using
                 * the given factory. The values are integrated using the given
                 * accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn1<C, F32.Consumer> accumulator);
            }

            // ----------------------------------------------------------

            /**
             * Collect operation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Collect<?, U>> extends Collect<Double, U> {

                /**
                 * Returns a result container of type {@code C} populated with the
                 * contained {@code double} values. The container is created using
                 * the given factory. The values are integrated using the given
                 * accumulator function by updating the state of the container
                 * (instead of replacing the result).
                 *
                 * @param factory     used to instantiate the result container.
                 * @param accumulator function to fold a value into the container.
                 * @param <C>         type of container.
                 * @return container of type {@code C}.
                 */
                <C> C collect(I32.To<? extends C> factory, Fn1<C, F64.Consumer> accumulator);
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.AGGREGATE
    // ----------------------------------------------------------

    /**
     * Aggregation operations to produce computed results.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Aggregate<A, U extends Aggregate<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.AGGREGATION.COUNT
        // ----------------------------------------------------------

        /**
         * Declares the family of count aggregation operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Count<A, U extends Count<?, U>> extends Aggregate<A, U> {

            /**
             * Count aggregation specialized for values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Count<?, U>> extends Count<A, U> {

                /**
                 * Returns the number of contained values that are equal to the given
                 * value, according to {@link java.util.Objects#equals(Object, Object)}
                 *
                 * @param val must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(final Object val) {
                    return count(Predicates.equal(val));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(Fn1.Predicate<? super A> predicate);
            }

            // ----------------------------------------------------------

            /**
             * Count aggregation specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends Count<?, U>> extends Count<Boolean, U> {

                /**
                 * Returns the number of contained values that are equal to the given value.
                 *
                 * @param value must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(boolean value) {
                    return count(Bool.equal(value));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(Bool.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Count aggregation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Count<?, U>> extends Count<Byte, U> {

                /**
                 * Returns the number of contained values that are equal to the given value.
                 *
                 * @param value must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(final byte value) {
                    return count(I8.equal(value));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(I8.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Count aggregation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Count<?, U>> extends Count<Short, U> {

                /**
                 * Returns the number of contained values that are equal to the given value.
                 *
                 * @param value must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(final short value) {
                    return count(I16.equal(value));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(I16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Count aggregation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Count<?, U>> extends Count<Character, U> {

                /**
                 * Returns the number of contained values that are equal to the given value.
                 *
                 * @param value must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(final char value) {
                    return count(C16.equal(value));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(C16.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Count aggregation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Count<?, U>> extends Count<Integer, U> {

                /**
                 * Returns the number of contained values that are equal to the given value.
                 *
                 * @param value must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(final int value) {
                    return count(I32.equal(value));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(I32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Count aggregation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Count<?, U>> extends Count<Long, U> {

                /**
                 * Returns the number of contained values that are equal to the given value.
                 *
                 * @param value must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(final long value) {
                    return count(I64.equal(value));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(I64.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Count aggregation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Count<?, U>> extends Count<Float, U> {

                /**
                 * Returns the number of contained values that are equal to the given value.
                 *
                 * @param value must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(final float value) {
                    return count(F32.equal(value));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(F32.Predicate predicate);
            }

            // ----------------------------------------------------------

            /**
             * Count aggregation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Count<?, U>> extends Count<Double, U> {

                /**
                 * Returns the number of contained values that are equal to the given value.
                 *
                 * @param value must be equal to be counted.
                 * @return number of equal values.
                 */
                default long count(final double value) {
                    return count(F64.equal(value));
                }

                /**
                 * Returns the number of contained values that satisfy the given predicate.
                 *
                 * @param predicate that determines the values to be counted.
                 * @return number of matches.
                 */
                long count(F64.Predicate predicate);
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.AGGREGATION.MIN
        // ----------------------------------------------------------

        /**
         * Declares the family of minimum aggregation operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Min<A, U extends Min<?, U>> extends Aggregate<A, U> {

            /**
             * Minimum aggregation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Min<?, U>> extends Min<A, U> {

                /**
                 * Returns the optional minimum value of type {@code A} according
                 * to the given comparator. If no values are present, the empty
                 * option is returned.
                 *
                 * @param comparator required to compare values.
                 * @return optional minimum value of type {@code A}.
                 */
                Option<A> min(Comparator<? super A> comparator);
            }

            // ----------------------------------------------------------

            /**
             * Minimum aggregation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Min<?, U>> extends Min<Byte, U> {

                /**
                 * Returns the optional {@code byte} minimum value
                 * or the empty option if no values are present.
                 */
                I8.Option min();
            }

            // ----------------------------------------------------------

            /**
             * Minimum aggregation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Min<?, U>> extends Min<Short, U> {

                /**
                 * Returns the optional {@code short} minimum value
                 * or the empty option if no values are present.
                 */
                I16.Option min();
            }

            // ----------------------------------------------------------

            /**
             * Minimum aggregation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Min<?, U>> extends Min<Character, U> {

                /**
                 * Returns the optional {@code char} minimum value
                 * or the empty option if no values are present.
                 */
                C16.Option min();
            }

            // ----------------------------------------------------------

            /**
             * Minimum aggregation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Min<?, U>> extends Min<Integer, U> {

                /**
                 * Returns the optional {@code int} minimum value
                 * or the empty option if no values are present.
                 */
                I32.Option min();
            }

            // ----------------------------------------------------------

            /**
             * Minimum aggregation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Min<?, U>> extends Min<Long, U> {

                /**
                 * Returns the optional {@code long} minimum value
                 * or the empty option if no values are present.
                 */
                I64.Option min();
            }

            // ----------------------------------------------------------

            /**
             * Minimum aggregation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Min<?, U>> extends Min<Float, U> {

                /**
                 * Returns the optional {@code float} minimum value
                 * or the empty option if no values are present.
                 */
                F32.Option min();
            }

            // ----------------------------------------------------------

            /**
             * Minimum aggregation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Min<?, U>> extends Min<Double, U> {

                /**
                 * Returns the optional {@code double} minimum value
                 * or the empty option if no values are present.
                 */
                F64.Option min();
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.AGGREGATION.MAX
        // ----------------------------------------------------------

        /**
         * Declares the family of maximum aggregation operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface Max<A, U extends Max<?, U>> extends Aggregate<A, U> {

            /**
             * Maximum aggregation specialized for reference values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends Max<?, U>> extends Max<A, U> {

                /**
                 * Returns the optional maximum value of type {@code A} according
                 * to the given comparator. If no values are present, the empty
                 * option is returned.
                 *
                 * @param comparator required to compare values.
                 * @return optional minimum value of type {@code A}.
                 */
                Option<A> max(Comparator<? super A> comparator);
            }

            // ----------------------------------------------------------

            /**
             * Maximum aggregation specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends Max<?, U>> extends Max<Byte, U> {

                /**
                 * Returns the optional {@code byte} maximum value
                 * or the empty option if no values are present.
                 */
                I8.Option max();
            }

            // ----------------------------------------------------------

            /**
             * Maximum aggregation specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends Max<?, U>> extends Max<Short, U> {

                /**
                 * Returns the optional {@code short} maximum value
                 * or the empty option if no values are present.
                 */
                I16.Option max();
            }

            // ----------------------------------------------------------

            /**
             * Maximum aggregation specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends Max<?, U>> extends Max<Character, U> {

                /**
                 * Returns the optional {@code char} maximum value
                 * or the empty option if no values are present.
                 */
                C16.Option max();
            }

            // ----------------------------------------------------------

            /**
             * Maximum aggregation specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends Max<?, U>> extends Max<Integer, U> {

                /**
                 * Returns the optional {@code int} maximum value
                 * or the empty option if no values are present.
                 */
                I32.Option max();
            }

            // ----------------------------------------------------------

            /**
             * Maximum aggregation specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends Max<?, U>> extends Max<Long, U> {

                /**
                 * Returns the optional {@code long} maximum value
                 * or the empty option if no values are present.
                 */
                I64.Option max();
            }

            // ----------------------------------------------------------

            /**
             * Maximum aggregation specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends Max<?, U>> extends Max<Float, U> {

                /**
                 * Returns the optional {@code float} maximum value
                 * or the empty option if no values are present.
                 */
                F32.Option max();
            }

            // ----------------------------------------------------------

            /**
             * Maximum aggregation specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends Max<?, U>> extends Max<Double, U> {

                /**
                 * Returns the optional {@code double} maximum value
                 * or the empty option if no values are present.
                 */
                F64.Option max();
            }
        }
    }

    // ----------------------------------------------------------
    //  OPERATION.CONVERT
    // ----------------------------------------------------------

    /**
     * Conversion operations to different representations.
     *
     * @param <A> type of value.
     * @param <U> unification parameter.
     */
    interface Convert<A, U extends Convert<?, U>> extends Operation<A, U> {

        // ----------------------------------------------------------
        //  OPERATION.CONVERSION.TO-ARRAY
        // ----------------------------------------------------------

        /**
         * Declares the family of array conversion operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface ToArray<A, U extends ToArray<?, U>> extends Convert<A, U> {

            /**
             * Array conversion specialized for values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends ToArray<?, U>> extends ToArray<A, U> {

                /**
                 * Returns an {@code Object[]} array containing all values.
                 */
                default Object[] toArray() {
                    return toArray(Object[]::new);
                }

                /**
                 * Returns an array containing all values in preserved order
                 * by using the given {@code factory} function to allocate
                 * the array, to be returned. The factory function accepts
                 * the required length and returns the desired array.
                 *
                 * @param <R> component-type of the resulting array.
                 * @param factory function which produces a new array.
                 * @return array containing all values.
                 */
                default <R> R[] toArray(final I32.To<R[]> factory) {
                    return toArray(factory.apply(Factory.count(this)));
                }

                /**
                 * Returns an array containing all values in preserved order,
                 * if any is defined; the runtime type of the returned array
                 * is that of the given array. If the list fits in the given
                 * array, it is returned therein, otherwise a new array with
                 * the same runtime type is allocated with required capacity.
                 *
                 * @param target array into which the values are to be stored.
                 * @return array containing all values.
                 */
                default <R> R[] toArray(final R[] target) {
                    java.util.Objects.requireNonNull(target);
                    final int count = Factory.count(this);
                    if (target.length > count) target[count] = null;
                    final Traversable<R> self = Force.cast(this);
                    final class Copy implements Fn1.Consumer<R> {
                        private final R[] out = target.length < count
                                ? Array.alloc(target, count) : target;
                        private int idx = 0;
                        { self.traverser().forNext(this); }
                        @Override
                        public void onAccept(final R o) {
                            out[idx++] = o;
                        }
                    }
                    return new Copy().out;
                }
            }

            // ----------------------------------------------------------

            /**
             * Array conversion specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends ToArray<?, U>> extends ToArray<Boolean, U> {

                /**
                 * Returns a {@code boolean[]} array containing all values
                 * in same order (if one exists).
                 */
                default boolean[] toArray() {
                    return Factory.toArray(Force.<Bool.Traversable>cast(this));
                }
            }

            // ----------------------------------------------------------

            /**
             * Array conversion specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends ToArray<?, U>> extends ToArray<Byte, U> {

                /**
                 * Returns a {@code byte[]} array containing all values
                 * in same order (if one exists).
                 */
                default byte[] toArray() {
                    return Factory.toArray(Force.<I8.Traversable>cast(this));
                }
            }

            // ----------------------------------------------------------

            /**
             * Array conversion specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends ToArray<?, U>> extends ToArray<Short, U> {

                /**
                 * Returns a {@code short[]} array containing all values
                 * in same order (if one exists).
                 */
                default short[] toArray() {
                    return Factory.toArray(Force.<I16.Traversable>cast(this));
                }
            }

            // ----------------------------------------------------------

            /**
             * Array conversion specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends ToArray<?, U>> extends ToArray<Character, U> {

                /**
                 * Returns a {@code char[]} array containing all values
                 * in same order (if one exists).
                 */
                default char[] toArray() {
                    return Factory.toArray(Force.<C16.Traversable>cast(this));
                }
            }

            // ----------------------------------------------------------

            /**
             * Array conversion specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends ToArray<?, U>> extends ToArray<Integer, U> {

                /**
                 * Returns a {@code int[]} array containing all values
                 * in same order (if one exists).
                 */
                default int[] toArray() {
                    return Factory.toArray(Force.<I32.Traversable>cast(this));
                }
            }

            // ----------------------------------------------------------

            /**
             * Array conversion specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends ToArray<?, U>> extends ToArray<Long, U> {

                /**
                 * Returns a {@code long[]} array containing all values
                 * in same order (if one exists).
                 */
                default long[] toArray() {
                    return Factory.toArray(Force.<I64.Traversable>cast(this));
                }
            }

            // ----------------------------------------------------------

            /**
             * Array conversion specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends ToArray<?, U>> extends ToArray<Float, U> {

                /**
                 * Returns a {@code float[]} array containing all values
                 * in same order (if one exists).
                 */
                default float[] toArray() {
                    return Factory.toArray(Force.<F32.Traversable>cast(this));
                }
            }

            // ----------------------------------------------------------

            /**
             * Array conversion specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends ToArray<?, U>> extends ToArray<Double, U> {

                /**
                 * Returns a {@code double[]} array containing all values
                 * in same order (if one exists).
                 */
                default double[] toArray() {
                    return Factory.toArray(Force.<F64.Traversable>cast(this));
                }
            }
        }

        // ----------------------------------------------------------
        //  OPERATION.CONVERSION.TO-COLLECTION
        // ----------------------------------------------------------

        /**
         * Declares the family of Java collection conversion operations.
         *
         * @param <A> type of value.
         * @param <U> unification parameter.
         */
        interface ToCollection<A, U extends ToCollection<?, U>> extends Convert<A, U> {

            /**
             * Java collection conversion specialized for values of type {@code A}.
             *
             * @param <A> type of value.
             * @param <U> unification parameter.
             */
            @Mixin interface Of<A, U extends ToCollection<?, U>> extends ToCollection<A, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<A> javaSet() {
                    return java(HashSet::new); }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<A> javaList()  {
                    return java(ArrayList::new); }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<A>> C java(final Factory<C> factory) {
                    return Factory.<A, C> toJava(Force.cast(this)).apply(factory::apply);
                }
            }

            // ----------------------------------------------------------

            /**
             * Java collection conversion specialized for {@code boolean} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfBool<U extends ToCollection<?, U>> extends ToCollection<Boolean, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<Boolean> javaSet() {
                    return java(HashSet::new); }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<Boolean> javaList()  {
                    return java(ArrayList::new); }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<Boolean>> C java(final Factory<C> factory) {
                    return Factory.<C> toJava(Force.cast(this)).apply(factory::apply);
                }
            }

            // ----------------------------------------------------------

            /**
             * Java collection conversion specialized for {@code byte} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI8<U extends ToCollection<?, U>>
                    extends ToCollection<Byte, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<Byte> javaSet() {
                    return java(HashSet::new); }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<Byte> javaList()  {
                    return java(ArrayList::new); }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<Byte>> C java(final Factory<C> factory) {
                    return Factory.<C> toJava(Force.cast(this)).apply(factory::apply);
                }
            }

            // ----------------------------------------------------------

            /**
             * Java collection conversion specialized for {@code short} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI16<U extends ToCollection<?, U>>
                    extends ToCollection<Short, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<Short> javaSet() {
                    return java(HashSet::new); }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<Short> javaList()  {
                    return java(ArrayList::new); }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<Short>> C java(final Factory<C> factory) {
                    return Factory.<C> toJava(Force.cast(this)).apply(factory::apply);
                }
            }

            // ----------------------------------------------------------

            /**
             * Java collection conversion specialized for {@code char} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfC16<U extends ToCollection<?, U>>
                    extends ToCollection<Character, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<Character> javaSet() {
                    return java(HashSet::new); }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<Character> javaList()  {
                    return java(ArrayList::new); }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<Character>> C java(final Factory<C> factory) {
                    return Factory.<C> toJava(Force.cast(this)).apply(factory::apply);
                }
            }

            // ----------------------------------------------------------

            /**
             * Java collection conversion specialized for {@code int} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI32<U extends ToCollection<?, U>> extends ToCollection<Integer, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<Integer> javaSet() {
                    return java(HashSet::new); }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<Integer> javaList() {
                    return java(ArrayList::new); }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<Integer>> C java(final Factory<C> factory) {
                    return Factory.<C> toJava(Force.cast(this)).apply(factory::apply);
                }
            }

            // ----------------------------------------------------------

            /**
             * Java collection conversion specialized for {@code long} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfI64<U extends ToCollection<?, U>>
                    extends ToCollection<Long, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<Long> javaSet() {
                    return java(HashSet::new); }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<Long> javaList()  {
                    return java(ArrayList::new); }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<Long>> C java(final Factory<C> factory) {
                    return Factory.<C> toJava(Force.cast(this)).apply(factory::apply);
                }
            }

            // ----------------------------------------------------------

            /**
             * Java collection conversion specialized for {@code float} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF32<U extends ToCollection<?, U>> extends ToCollection<Float, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<Float> javaSet() {
                    return java(HashSet::new);
                }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<Float> javaList()  {
                    return java(ArrayList::new);
                }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<Float>> C java(final Factory<C> factory) {
                    return Factory.<C> toJava(Force.cast(this))
                            .apply(factory::apply);
                }
            }

            // ----------------------------------------------------------

            /**
             * Java collection conversion specialized for {@code double} values.
             *
             * @param <U> unification parameter.
             */
            @Mixin interface OfF64<U extends ToCollection<?, U>> extends ToCollection<Double, U> {

                /**
                 * Returns a {@link java.util.Set} collection containing all values.
                 */
                default java.util.Set<Double> javaSet() {
                    return java(HashSet::new);
                }

                /**
                 * Returns a {@link java.util.List} collection containing all values.
                 */
                default java.util.List<Double> javaList()  {
                    return java(ArrayList::new);
                }

                /**
                 * Returns a {@link java.util.Collection} containing all values.
                 * The result collection instance is created by given factory.
                 *
                 * @param factory used to create the target collection.
                 * @param <C>     type of result collection.
                 * @return filled {@link java.util.List}.
                 */
                default <C extends java.util.Collection<Double>> C java(final Factory<C> factory) {
                    return Factory.<C> toJava(Force.cast(this)).apply(factory::apply);
                }
            }
        }
    }

    // ----------------------------------------------------------

    /**
     *
     */
    interface Factory<C> extends I32.To<C> {

        static int count(final Object traversable) {
            return (traversable instanceof Mixin.Count countable)
                    ? (int) countable.count() : 0;
        }

        static boolean[] toArray(final Bool.Traversable traversable) {
            final int[] idx = { 0 };
            final var result = new boolean[count(traversable)];
            traversable.traverser().forNext(x -> result[idx[0]++] = x);
            return result;
        }

        static byte[] toArray(final I8.Traversable traversable) {
            final int[] idx = { 0 };
            final var result = new byte[count(traversable)];
            traversable.traverser().forNext(x -> result[idx[0]++] = x);
            return result;
        }

        static short[] toArray(final I16.Traversable traversable) {
            final int[] idx = { 0 };
            final var result = new short[count(traversable)];
            traversable.traverser().forNext(x -> result[idx[0]++] = x);
            return result;
        }

        static char[] toArray(final C16.Traversable traversable) {
            final int[] idx = { 0 };
            final var result = new char[count(traversable)];
            traversable.traverser().forNext(x -> result[idx[0]++] = x);
            return result;
        }

        static int[] toArray(final I32.Traversable traversable) {
            final int[] idx = { 0 };
            final var result = new int[count(traversable)];
            traversable.traverser().forNext(x -> result[idx[0]++] = x);
            return result;
        }

        static long[] toArray(final I64.Traversable traversable) {
            final int[] idx = { 0 };
            final var result = new long[count(traversable)];
            traversable.traverser().forNext(x -> result[idx[0]++] = x);
            return result;
        }

        static float[] toArray(final F32.Traversable traversable) {
            final int[] idx = { 0 };
            final var result = new float[count(traversable)];
            traversable.traverser().forNext(x -> result[idx[0]++] = x);
            return result;
        }

        static double[] toArray(final F64.Traversable traversable) {
            final int[] idx = { 0 };
            final var result = new double[count(traversable)];
            traversable.traverser().forNext(x -> result[idx[0]++] = x);
            return result;
        }

        // ----------------------------------------------------------

        static <A, C extends java.util.Collection<A>>
        Fn1<Factory<C>, C> toJava(final Traversable<A> traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }

        static <C extends java.util.Collection<Boolean>>
        Fn1<Factory<C>, C> toJava(final Bool.Traversable traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }

        static <C extends java.util.Collection<Byte>>
        Fn1<Factory<C>, C> toJava(final I8.Traversable traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }

        static <C extends java.util.Collection<Short>>
        Fn1<Factory<C>, C> toJava(final I16.Traversable traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }

        static <C extends java.util.Collection<Character>>
        Fn1<Factory<C>, C> toJava(final C16.Traversable traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }

        static <C extends java.util.Collection<Integer>>
        Fn1<Factory<C>, C> toJava(final I32.Traversable traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }

        static <C extends java.util.Collection<Long>>
        Fn1<Factory<C>, C> toJava(final I64.Traversable traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }

        static <C extends java.util.Collection<Float>>
        Fn1<Factory<C>, C> toJava(final F32.Traversable traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }

        static <C extends java.util.Collection<Double>>
        Fn1<Factory<C>, C> toJava(final F64.Traversable traversable) {
            return factory -> {
                final var list = factory.onApply(count(traversable));
                traversable.traverser().forNext(list::add);
                return list;
            };
        }
    }
}
