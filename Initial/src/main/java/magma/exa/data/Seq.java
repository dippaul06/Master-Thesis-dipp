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

package magma.exa.data;

import java.util.Comparator;

import magma.exa.base.text.Text;
import magma.exa.control.function.Fn1;
import magma.exa.control.function.Fn2;
import magma.exa.control.function.Predicates;
import magma.exa.control.traversal.Traversable;
import magma.exa.control.traversal.Traverser;
import magma.exa.data.adt.operation.Operation;
import magma.exa.data.adt.ISeq;
import magma.exa.value.scalar.I32;

/**
 *
 * @param <A>
 */
public interface Seq<A> extends ISeq<A>, Traversable<A>,

        Flow.Producer<A, ISeq<?>>,

        /// OPERATION.QUERY

        Operation.Query.Bulk.Membership.Of<A, ISeq<?>>,

        Operation.Query.Quantify.Of<A, ISeq<?>>,

        Operation.Query.FindValue.Of<A, ISeq<?>>,

        Operation.Query.FindIndex.Of<A, ISeq<?>>,

        /// OPERATION.ACCESS

        Operation.Access.Single.Of<A, ISeq<?>>,

        Operation.Access.First.Of<A, ISeq<?>>,

        Operation.Access.Last.Of<A, ISeq<?>>,

        Operation.Access.At.Of<A, ISeq<?>>,

        /// OPERATION.FOLD

        Operation.Fold.FoldLeft.Of<A, ISeq<?>>,

        Operation.Fold.FoldRight.Of<A, ISeq<?>>,

        Operation.Fold.Reduce.Of<A, ISeq<?>>,

        Operation.Fold.Collect.Of<A, ISeq<?>>,

        /// OPERATION.AGGREGATE

        Operation.Aggregate.Count.Of<A, ISeq<?>>,

        Operation.Aggregate.Min.Of<A, ISeq<?>>,

        Operation.Aggregate.Max.Of<A, ISeq<?>>,

        /// OPERATION.CONVERT

        Operation.Convert.ToArray.Of<A, ISeq<?>>,

        Operation.Convert.ToCollection.Of<A, ISeq<?>>,

        /// ----------------------------------------------------------

        /// OPERATION.TRANSFORM

        Operation.Transform.Peek.Of<A, ISeq<?>>,

        Operation.Transform.Filter.Of<A, ISeq<?>>,

        Operation.Transform.Distinct.Of<A, ISeq<?>>,

        Operation.Transform.Sort.Of<A, ISeq<?>>,

        Operation.Transform.Reverse.Of<A, ISeq<?>>,

        /// OPERATION.TRANSMUTE

        Operation.Transmute.Map.Of<A, ISeq<?>>,

        Operation.Transmute.FilterMap.Of<A, ISeq<?>>,

        Operation.Transmute.FlatMap.Of<A, ISeq<?>>,

        /// OPERATION.PARTITION

        Operation.Partition.Take.Of<A, ISeq<?>>,

        Operation.Partition.Drop.Of<A, ISeq<?>>
{
    // ----------------------------------------------------------

    /**
     * Determines whether the given value is contained.
     *
     * @param val value whose presence is to be determined.
     * @return true iff the given value is present.
     */
    @Override
    boolean contains(Object val);

    /**
     * Determines whether all values in the given array are contained.
     *
     * @param ary array of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    @Override
    boolean contains(Object... ary);

    /**
     * Determines whether all values in the given iterable are contained.
     *
     * @param itr iterable of values whose presence is to be determined.
     * @return true iff all values are present.
     */
    @Override
    boolean contains(Iterable<?> itr);

    // ----------------------------------------------------------

    /**
     * Determines whether any value satisfies the given predicate.
     * <p>
     * This corresponds to evaluating the existential quantification
     * of the given predicate over the values {@code '∃(x) ∈ Seq<A> P(x)'}.
     * The predicate is not evaluated for all values if it is not required
     * to determine the result. If no values are present, false is returned
     * and the predicate is not evaluated.
     *
     * @param predicate to be evaluated on contained values.
     * @return true iff any value matches.
     */
    @Override
    boolean anyMatch(Fn1.Predicate<? super A> predicate);

    /**
     * Determines whether all values satisfy the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the predicate over the values {@code '∀(x) ∈ Seq<A> P(x)'}. The
     * predicate is not evaluated for all values if it is not required to
     * determine the result. If no values are present, the quantification
     * is said to be 'vacuously satisfied', and always true is returned,
     * independent of P(x).
     *
     * @param predicate to be evaluated on contained values.
     * @return true iff all values match or no values are present.
     */
    @Override
    boolean allMatch(Fn1.Predicate<? super A> predicate);

    /**
     * Determines whether no values satisfy the given predicate.
     * <p>
     * This corresponds to the evaluation of the universal quantification
     * of the negated predicate over {@code '∀(x) ∈ Seq<A> ¬P(x)'} this
     * sequence. The predicate is not evaluated for all values if it is
     * not required to determine the result. If no values are present,
     * the quantification is said to be 'vacuously satisfied', and always
     * true is returned, independent of P(x).
     *
     * @param predicate to be evaluated on contained values.
     * @return true iff no value matches or no values are present.
     */
    @Override
    boolean noneMatch(Fn1.Predicate<? super A> predicate);

    // ----------------------------------------------------------

    /**
     * Returns the optional first value of type {@code A} which
     * satisfies the given predicate. If no value can be determined,
     * the empty option is returned.
     *
     * @param predicate to be evaluated on contained values.
     * @return optional first value of type {@code A}.
     */
    @Override
    Option<A> find(Fn1.Predicate<? super A> predicate);

    /**
     * Returns the optional last value of type {@code A} which
     * satisfies the given predicate. If no value can be determined,
     * the empty option is returned.
     *
     * @param predicate to be evaluated on contained values.
     * @return optional last value of type {@code A}.
     */
    @Override
    Option<A> findLast(Fn1.Predicate<? super A> predicate);

    // ----------------------------------------------------------

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
    @Override
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
    @Override
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
    @Override
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
    @Override
    long lastIndexWhere(Fn1.Predicate<? super A> predicate);

    // ----------------------------------------------------------

    /**
     * Returns the optional value singleton of type {@code A} if
     * it is the only existing value, otherwise the empty option
     * is returned.
     */
    @Override
    Option<A> single();

    // ----------------------------------------------------------

    /**
     * Returns the optional first value of type {@code A}
     * if present, or the empty option if empty.
     */
    @Override
    Option<A> first();

    // ----------------------------------------------------------

    /**
     * Returns the optional last value of type {@code A}
     * if present, or the empty option if empty.
     */
    @Override
    Option<A> last();

    // ----------------------------------------------------------

    /**
     * Returns the value of type {@code A} at the given index.
     *
     * @param idx whose corresponding value is to be returned.
     * @return value of type {@code A}.
     */
    @Override
    A at(long idx);

    // ----------------------------------------------------------

    /**
     * Performs a left fold on the contained values of type {@code A}
     * using the given combining function. If no values are present,
     * the initial value accumulator is returned.
     * <p>
     * A left fold operation starts with the given initial value and
     * applies the given combining function to the current accumulator
     * state and each contained value, from left to right, to compute
     * the accumulated result.
     *
     * @param initial  accumulator value.
     * @param combiner to integrate values into the accumulator.
     * @return accumulated result of type {@code Z}.
     */
    @Override
    <Z> Z foldLeft(Z initial, Fn2<Z, ? super A, Z> combiner);

    // ----------------------------------------------------------

    /**
     * Performs a right fold on the contained values of type {@code A}
     * using the given combining function. If no values are present,
     * the initial value accumulator is returned.
     * <p>
     * A right fold operation starts with the given initial value and
     * applies the given combining function to the current accumulator
     * state and each contained value, from right to left, to compute
     * the accumulated result.
     *
     * @param initial  accumulator value.
     * @param combiner to integrate values into the accumulator.
     * @return accumulated result of type {@code Z}.
     */
    @Override
    <Z> Z foldRight(Z initial, Fn2<? super A, Z, Z> combiner);

    // ----------------------------------------------------------

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
    @Override
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
    @Override
    Option<A> reduce(Fn2<A, A, A> accumulator);

    // ----------------------------------------------------------

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
    @Override
    <C> C collect(I32.To<? extends C> factory, Fn2.Consumer<C, ? super A> accumulator);

    // ----------------------------------------------------------

    /**
     * Returns the number of contained values that are equal to the given
     * value, according to {@link java.util.Objects#equals(Object, Object)}
     *
     * @param val must be equal to be counted.
     * @return number of equal values.
     */
    @Override
    default long count(final Object val) {
        return count(Predicates.equal(val));
    }

    /**
     * Returns the number of contained values that satisfy the given predicate.
     *
     * @param predicate that determines the values to be counted.
     * @return number of matches.
     */
    @Override
    long count(Fn1.Predicate<? super A> predicate);

    // ----------------------------------------------------------

    /**
     * Returns the optional minimum value of type {@code A} according
     * to the given comparator. If no values are present, the empty
     * option is returned.
     *
     * @param comparator required to compare values.
     * @return optional minimum value of type {@code A}.
     */
    @Override
    Option<A> min(Comparator<? super A> comparator);

    // ----------------------------------------------------------

    /**
     * Returns the optional maximum value of type {@code A} according
     * to the given comparator. If no values are present, the empty
     * option is returned.
     *
     * @param comparator required to compare values.
     * @return optional minimum value of type {@code A}.
     */
    @Override
    Option<A> max(Comparator<? super A> comparator);

    // ----------------------------------------------------------

    /**
     * Returns a sequence of values of type {@code A} consisting of the
     * values, additionally performing the provided action
     * on each value as values are consumed from the resulting sequence.
     *
     * @param action to be performed on each value.
     * @return sequence of type {@code A}.
     */
    @Override
    Seq<A> peek(Fn1.Consumer<? super A> action);

    // ----------------------------------------------------------

    /**
     * Returns a sequence consisting of values that do
     * satisfy the given predicate.
     *
     * @param predicate to be applied to each value to determine inclusion.
     * @return sequence of type {@code A}.
     */
    @Override
    Seq<A> filter(Fn1.Predicate<? super A> predicate);

    /**
     * Returns a sequence consisting of values that do NOT
     * satisfy the given predicate.
     *
     * @param predicate to be applied to each value to determine exclusion.
     * @return sequence of type {@code A}.
     */
    @Override
    Seq<A> filterNot(Fn1.Predicate<? super A> predicate);

    /**
     * Returns a sequence consisting of non-null values.
     *
     * @return sequence of type {@code A}.
     */
    @Override
    Seq<A> filterNull();

    // ----------------------------------------------------------

    /**
     * Returns a sequence consisting of the values of type {@code A} of
     * this sequence, sorted according to the given {@link Comparator}.
     *
     * @param comparator to be used to compare values of type {@code A}.
     * @return sequence of type {@code A}.
     */
    @Override
    Seq<A> sort(Comparator<? super A> comparator);

    // ----------------------------------------------------------

    /**
     * Returns a sequence of values of type {@code A} consisting
     * of the values in reverse order.
     */
    @Override
    Seq<A> reverse();

    // ----------------------------------------------------------

    /**
     * Returns a sequence of values of type {@code B} consisting of the
     * results obtained by applying the given mapper function to each
     * value of type {@code A}.
     *
     * @param mapper function to be applied to each value.
     * @return sequence of type {@code B}.
     */
    @Override
    <B> Seq<B> map(Fn1<? super A, ? extends B> mapper);

    // ----------------------------------------------------------

    /**
     * Returns a sequence of type {@code B} consisting of assignment
     * compatible values. Assignment compatibility is determined via
     * the given class object.
     *
     * @param type to which values must be assignable.
     * @return sequence of type {@code B}.
     */
    @Override
    <B> Seq<B> filter(Class<? extends B> type);

    // ----------------------------------------------------------

    /**
     * Returns values of type {@code B} produced by applying the given
     * mapping function to each value of type {@code A} and replacing
     * it with the contents of the mapped iterable.
     * <p>
     * Flat-map has the effect of applying a one-to-many transformation
     * to the contained values and then flattening the produced results.
     *
     * @param mapper function to be applied on each value.
     * @return sequence of type {@code B}.
     */
    @Override
    <B> Seq<B> flatMap(Fn1<? super A, ? extends Iterable<? extends B>> mapper);

    // ----------------------------------------------------------

    /**
     * Returns the longest prefix of values of type {@code A} that
     * satisfy the given predicate.
     *
     * @param predicate to determine the longest prefix.
     * @return sequence of type {@code A}.
     */
    @Override
    Seq<A> takeWhile(Fn1.Predicate<? super A> predicate);

    // ----------------------------------------------------------

    /**
     * Returns all remaining values of type {@code A} after dropping
     * the longest prefix satisfying the given predicate.
     *
     * @param predicate to determine the longest prefix.
     * @return sequence of type {@code A}.
     */
    @Override
    Seq<A> dropWhile(Fn1.Predicate<? super A> predicate);

    // ----------------------------------------------------------

    /**
     * Returns duplex traverser over this sequence.
     */
    @Override
    Traverser.Duplex.Indexed<A> traverser();

    // ----------------------------------------------------------

    /// COMPARISON AND HASHING.

    /**
     * Compares the specified object with this sequence for ordered
     * structural equality. Returns true if the given object is also
     * a sequence with the same size and values in the same order.
     *
     * @param o object to be compared for ordered structural equality.
     * @return true if the ordered structural equality is satisfied.
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this sequence.
     */
    int hashCode();

    // ----------------------------------------------------------

    /// STRING REPRESENTATION.

    /**
     * Returns a string representation of this sequence.
     */
    String toString();

    /**
     * Formatted used to display contents.
     */
    enum Formatter implements Text.Formatter<Seq<?>> {
        instance;
        @Override
        public Text onApply(final Seq<?> seq) throws Throwable {
            if (seq.isEmpty()) return Text.of("[]");
            final Text txt = Text.of();
            seq.traverser().forNext(x -> {
                txt.append(x == this ? "($self)" : x);
                txt.append(',').append(' ');
            });
            return txt;
        }
    }
}
