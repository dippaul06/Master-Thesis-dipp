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

package magma.exa.control.function;

import magma.exa.base.contract.Assert;
import magma.exa.data.Array;
import magma.exa.value.scalar.*;

import java.util.Objects;

/**
 * Predicate combinators.
 */
public enum Predicates {
    ;
    public static <A> Fn1.Predicate<A> isNull()  { return Objects::isNull; }

    public static <A> Fn1.Predicate<A> nonNull() { return Objects::nonNull; }

    // ----------------------------------------------------------

    public static <A> Fn1.Predicate<A> equal(final A val) { return x -> Objects.equals(x, val); }

    public static <A> Fn1.Predicate<A> notEqual(final A val) { return not(equal(val)); }

    // ----------------------------------------------------------

    @SafeVarargs public static <A> Fn1.Predicate<A> any(final A... ary) {
        return (ary.length != 0) ? x -> Array.anyMatch(ary.clone(), Predicates.equal(x)) : Fn1.Predicate.False();
    }

    @SafeVarargs public static <A> Fn1.Predicate<A> all(final A... ary) {
        return (ary.length != 0) ? x -> Array.allMatch(ary.clone(), Predicates.equal(x)) : Fn1.Predicate.True();
    }

    @SafeVarargs public static <A> Fn1.Predicate<A> none(final A... ary) {
        return (ary.length != 0) ? x -> Array.noneMatch(ary.clone(), Predicates.equal(x)) : Fn1.Predicate.True();
    }

    // ----------------------------------------------------------

    /// PREDICATE NEGATION

    /** Produces a negation of the given {@code boolean} predicate. */
    public static Bool.Predicate not(final Bool.Predicate pd) { return x -> !pd.onEval(x); }

    /** Produces a negation of the given {@code byte} predicate. */
    public static I8.Predicate  not(final I8.Predicate  pd) { return x -> !pd.onEval(x); }

    /** Produces a negation of the given {@code short} predicate. */
    public static I16.Predicate not(final I16.Predicate pd) { return x -> !pd.onEval(x); }

    /** Produces a negation of the given {@code char} predicate. */
    public static C16.Predicate not(final C16.Predicate pd) { return x -> !pd.onEval(x); }

    /** Produces a negation of the given {@code int} predicate. */
    public static I32.Predicate not(final I32.Predicate pd) { return x -> !pd.onEval(x); }

    /** Produces a negation of the given {@code long} predicate. */
    public static I64.Predicate not(final I64.Predicate pd) { return x -> !pd.onEval(x); }

    /** Produces a negation of the given {@code float} predicate. */
    public static F32.Predicate not(final F32.Predicate pd) { return x -> !pd.onEval(x); }

    /** Produces a negation of the given {@code double} predicate. */
    public static F64.Predicate not(final F64.Predicate pd) { return x -> !pd.onEval(x); }

    // ----------------------------------------------------------

    /** Produces a negation of the given predicate of arity 1. */
    public static <A> 
    Fn1.Predicate<A> not(final Fn1.Predicate<? super A> pa) {
        return a -> !pa.onEval(a);
    }

    /** Produces a negation of the given predicate of arity 2. */
    public static <A, B> 
    Fn2.Predicate<A, B> not(final Fn2.Predicate<? super A, ? super B> pa) {
        return (a, b) -> !pa.onEval(a, b);
    }

    /** Produces a negation of the given predicate of arity 3. */
    public static <A, B, C> 
    Fn3.Predicate<A, B, C> not(final Fn3.Predicate<? super A, ? super B, ? super C> pa) {
        return (a, b, c) -> !pa.onEval(a, b, c);
    }

    /** Produces a negation of the given predicate of arity 4. */
    public static <A, B, C, D> 
    Fn4.Predicate<A, B, C, D> not(final Fn4.Predicate<? super A, ? super B, ? super C, ? super D> pa) {
        return (a, b, c, d) -> !pa.onEval(a, b, c, d);
    }

    /** Produces a negation of the given predicate of arity 5. */
    public static <A, B, C, D, E> 
    Fn5.Predicate<A, B, C, D, E> not(final Fn5.Predicate<? super A, ? super B, ? super C, ? super D, ? super E> pa) {
        return (a, b, c, d, e) -> !pa.onEval(a, b, c, d, e);
    }

    /** Produces a negation of the given predicate of arity 6. */
    public static <A, B, C, D, E, F> 
    Fn6.Predicate<A, B, C, D, E, F> not(final Fn6.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> pa) {
        return (a, b, c, d, e, f) -> !pa.onEval(a, b, c, d, e, f);
    }

    /** Produces a negation of the given predicate of arity 7. */
    public static <A, B, C, D, E, F, G> 
    Fn7.Predicate<A, B, C, D, E, F, G> not(final Fn7.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> pa) {
        return (a, b, c, d, e, f, g) -> !pa.onEval(a, b, c, d, e, f, g);
    }

    /** Produces a negation of the given predicate of arity 8. */
    public static <A, B, C, D, E, F, G, H> 
    Fn8.Predicate<A, B, C, D, E, F, G, H> not(final Fn8.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> pa) {
        return (a, b, c, d, e, f, g, h) -> !pa.onEval(a, b, c, d, e, f, g, h);
    }

    // ----------------------------------------------------------

    /// PREDICATE CONJUNCTION (SHORT-CIRCUITING)

    /** Produces a conjunctive composition of the given {@code boolean} predicates. */
    public static Bool.Predicate and(final Bool.Predicate pa, final Bool.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) && pb.onEval(x);
    }

    /** Produces a conjunctive composition of the given {@code byte} predicates. */
    public static I8.Predicate and(final I8.Predicate pa, final I8.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) && pb.onEval(x);
    }

    /** Produces a conjunctive composition of the given {@code short} predicates. */
    public static I16.Predicate and(final I16.Predicate pa, final I16.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) && pb.onEval(x);
    }

    /** Produces a conjunctive composition of the given {@code char} predicates. */
    public static C16.Predicate and(final C16.Predicate pa, final C16.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) && pb.onEval(x);
    }

    /** Produces a conjunctive composition of the given {@code int} predicates. */
    public static I32.Predicate and(final I32.Predicate pa, final I32.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) && pb.onEval(x);
    }

    /** Produces a conjunctive composition of the given {@code long} predicates. */
    public static I64.Predicate and(final I64.Predicate pa, final I64.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) && pb.onEval(x);
    }

    /** Produces a conjunctive composition of the given {@code float} predicates. */
    public static F32.Predicate and(final F32.Predicate pa, final F32.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) && pb.onEval(x);
    }

    /** Produces a conjunctive composition of the given {@code double} predicates. */
    public static F64.Predicate and(final F64.Predicate pa, final F64.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) && pb.onEval(x);
    }

    // ----------------------------------------------------------

    /** Produces a conjunctive composition of the two given predicates of arity 1. */
    public static <A>
    Fn1.Predicate<A> and(final Fn1.Predicate<? super A> pa,
                         final Fn1.Predicate<? super A> pb) {
        return a -> pa.onEval(a) && pb.onEval(a);
    }

    /** Produces a conjunctive composition of the two given predicates of arity 2. */
    public static <A, B>
    Fn2.Predicate<A, B> and(final Fn2.Predicate<? super A, ? super B> pa,
                            final Fn2.Predicate<? super A, ? super B> pb) {
        return (a, b) -> pa.onEval(a, b) && pb.onEval(a, b);
    }

    /** Produces a conjunctive composition of the two given predicates of arity 3. */
    public static <A, B, C>
    Fn3.Predicate<A, B, C> and(final Fn3.Predicate<? super A, ? super B, ? super C> pa,
                               final Fn3.Predicate<? super A, ? super B, ? super C> pb) {
        return (a, b, c) -> pa.onEval(a, b, c) && pb.onEval(a, b, c);
    }

    /** Produces a conjunctive composition of the two given predicates of arity 4. */
    public static <A, B, C, D>
    Fn4.Predicate<A, B, C, D> and(final Fn4.Predicate<? super A, ? super B, ? super C, ? super D> pa,
                                  final Fn4.Predicate<? super A, ? super B, ? super C, ? super D> pb) {
        return (a, b, c, d) -> pa.onEval(a, b, c, d) && pb.onEval(a, b, c, d);
    }

    /** Produces a conjunctive composition of the two given predicates of arity 5. */
    public static <A, B, C, D, E>
    Fn5.Predicate<A, B, C, D, E> and(final Fn5.Predicate<? super A, ? super B, ? super C, ? super D, ? super E> pa,
                                     final Fn5.Predicate<? super A, ? super B, ? super C, ? super D, ? super E> pb) {
        return (a, b, c, d, e) -> pa.onEval(a, b, c, d, e) && pb.onEval(a, b, c, d, e);
    }

    /** Produces a conjunctive composition of the two given predicates of arity 6. */
    public static <A, B, C, D, E, F>
    Fn6.Predicate<A, B, C, D, E, F> and(final Fn6.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> pa,
                                        final Fn6.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> pb) {
        return (a, b, c, d, e, f) -> pa.onEval(a, b, c, d, e, f) && pb.onEval(a, b, c, d, e, f);
    }

    /** Produces a conjunctive composition of the two given predicates of arity 7. */
    public static <A, B, C, D, E, F, G>
    Fn7.Predicate<A, B, C, D, E, F, G> and(final Fn7.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> pa,
                                           final Fn7.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> pb) {
        return (a, b, c, d, e, f, g) -> pa.onEval(a, b, c, d, e, f, g) && pb.onEval(a, b, c, d, e, f, g);
    }

    /** Produces a conjunctive composition of the two given predicates of arity 8. */
    public static <A, B, C, D, E, F, G, H>
    Fn8.Predicate<A, B, C, D, E, F, G, H> and(final Fn8.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> pa,
                                              final Fn8.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> pb) {
        return (a, b, c, d, e, f, g, h) -> pa.onEval(a, b, c, d, e, f, g, h) && pb.onEval(a, b, c, d, e, f, g, h);
    }

    // ----------------------------------------------------------

    /// PREDICATE DISJUNCTION (SHORT-CIRCUITING)

    /** Produces a disjunctive composition of the given {@code boolean} predicates. */
    public static Bool.Predicate or(final Bool.Predicate pa, final Bool.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) || pb.onEval(x);
    }

    /** Produces a disjunctive composition of the given {@code byte} predicates. */
    public static I8.Predicate or(final I8.Predicate pa, final I8.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) || pb.onEval(x);
    }

    /** Produces a disjunctive composition of the given {@code short} predicates. */
    public static I16.Predicate or(final I16.Predicate pa, final I16.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) || pb.onEval(x);
    }

    /** Produces a disjunctive composition of the given {@code char} predicates. */
    public static C16.Predicate or(final C16.Predicate pa, final C16.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) || pb.onEval(x);
    }

    /** Produces a disjunctive composition of the given {@code int} predicates. */
    public static I32.Predicate or(final I32.Predicate pa, final I32.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) || pb.onEval(x);
    }
    
    /** Produces a disjunctive composition of the given {@code long} predicates. */
    public static I64.Predicate or(final I64.Predicate pa, final I64.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) || pb.onEval(x);
    }

    /** Produces a disjunctive composition of the given {@code float} predicates. */
    public static F32.Predicate or(final F32.Predicate pa, final F32.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) || pb.onEval(x);
    }

    /** Produces a disjunctive composition of the given {@code double} predicates. */
    public static F64.Predicate or(final F64.Predicate pa, final F64.Predicate pb) {
        Assert.notNull(pa, pb); return x -> pa.onEval(x) || pb.onEval(x);
    }

    // ----------------------------------------------------------

    /** Produces a disjunctive composition of the given predicates of arity 1. */
    public static <A>
    Fn1.Predicate<A> or(final Fn1.Predicate<? super A> pa,
                        final Fn1.Predicate<? super A> pb) {
        return a -> pa.onEval(a) || pb.onEval(a);
    }

    /** Produces a disjunctive composition of the given predicates of arity 2. */
    public static <A, B>
    Fn2.Predicate<A, B> or(final Fn2.Predicate<? super A, ? super B> pa,
                           final Fn2.Predicate<? super A, ? super B> pb) {
        return (a, b) -> pa.onEval(a, b) || pb.onEval(a, b);
    }

    /** Produces a disjunctive composition of the given predicates of arity 3. */
    public static <A, B, C>
    Fn3.Predicate<A, B, C> or(final Fn3.Predicate<? super A, ? super B, ? super C> pa,
                              final Fn3.Predicate<? super A, ? super B, ? super C> pb) {
        return (a, b, c) -> pa.onEval(a, b, c) || pb.onEval(a, b, c);
    }

    /** Produces a disjunctive composition of the given predicates of arity 4. */
    public static <A, B, C, D>
    Fn4.Predicate<A, B, C, D> or(final Fn4.Predicate<? super A, ? super B, ? super C, ? super D> pa,
                                 final Fn4.Predicate<? super A, ? super B, ? super C, ? super D> pb) {
        return (a, b, c, d) -> pa.onEval(a, b, c, d) || pb.onEval(a, b, c, d);
    }

    /** Produces a disjunctive composition of the given predicates of arity 5. */
    public static <A, B, C, D, E>
    Fn5.Predicate<A, B, C, D, E> or(final Fn5.Predicate<? super A, ? super B, ? super C, ? super D, ? super E> pa,
                                    final Fn5.Predicate<? super A, ? super B, ? super C, ? super D, ? super E> pb) {
        return (a, b, c, d, e) -> pa.onEval(a, b, c, d, e) || pb.onEval(a, b, c, d, e);
    }

    /** Produces a disjunctive composition of the given predicates of arity 6. */
    public static <A, B, C, D, E, F>
    Fn6.Predicate<A, B, C, D, E, F> or(final Fn6.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> pa,
                                       final Fn6.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> pb) {
        return (a, b, c, d, e, f) -> pa.onEval(a, b, c, d, e, f) || pb.onEval(a, b, c, d, e, f);
    }

    /** Produces a disjunctive composition of the given predicates of arity 7. */
    public static <A, B, C, D, E, F, G>
    Fn7.Predicate<A, B, C, D, E, F, G> or(final Fn7.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> pa,
                                          final Fn7.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> pb) {
        return (a, b, c, d, e, f, g) -> pa.onEval(a, b, c, d, e, f, g) || pb.onEval(a, b, c, d, e, f, g);
    }

    /** Produces a disjunctive composition of the given predicates of arity 8. */
    public static <A, B, C, D, E, F, G, H>
    Fn8.Predicate<A, B, C, D, E, F, G, H> or(final Fn8.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> pa,
                                             final Fn8.Predicate<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> pb) {
        return (a, b, c, d, e, f, g, h) -> pa.onEval(a, b, c, d, e, f, g, h) || pb.onEval(a, b, c, d, e, f, g, h);
    }
}
