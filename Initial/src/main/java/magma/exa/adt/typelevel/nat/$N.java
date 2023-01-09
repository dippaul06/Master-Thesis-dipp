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

package magma.exa.adt.typelevel.nat;

import magma.exa.adt.mixin.Mixin;
import magma.exa.base.Hash;
import magma.exa.base.contract.Assert;
import magma.exa.control.function.Fn1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Church-encoded natural numbers (with additional subtype relation for N < 8).
 */
public interface $N extends Mixin.Ordinal {

    /**
     * Returns the preceding natural.
     */
    $N pred();

    /**
     * Returns the succeeding natural.
     */
    default $N succ() {
        return Natural.numbers.computeIfAbsent(this, Natural::new);
    }

    /**
     * Returns the corresponding runtime value encoding as {@code int}.
     */
    int ordinal();

    /**
     * The first argument on the left is the case to use when an object
     * represents zero. The second argument on the right is a function
     * that ultimately produces the value associated with a {@link #succ()}.
     * The implicit contract here is that the Natural Number {@link $N}
     * passed as input to {@link #succ()} is the predecessor of the
     * "current value". This may seem counterintuitive. The key insight
     * is that a succ value has no value of its own; it is defined solely
     * by how many predecessors it has.
     *
     * @param zero case function iff {@link $0}.
     * @param succ case function iff successor of {@link $0}.
     * @param <R>  result type for both cases.
     * @return result of zero or successor function.
     */
    default <R> R match(final Fn1<? super $0, ? extends R> zero,
                        final Fn1<? super $N, ? extends R> succ) {
        Assert.notNull(zero, succ);
        return this == $0.nat ? zero.apply($0.nat) : succ.apply(this);
    }

    // ----------------------------------------------------------

    /**
     * Natural number representation for > 8.
     */
    final class Natural implements $N {
        private final $N pred;

        Natural(final $N pred) {
            this.pred = pred;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public $N pred() {
            return pred;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public $N succ() {
            return numbers.computeIfAbsent(this, Natural::new);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int ordinal() {
            return pred.ordinal() + 1;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof $N nat)) return false;
            return ordinal() == nat.ordinal();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return Hash.code(ordinal());
        }

        static final Map<$N, $N> numbers;
        static { numbers = new ConcurrentHashMap<>(); }
    }

    // ----------------------------------------------------------

    /// TYPE-LEVEL ADDITION.

    static $0 add($0 x, $0 y) { return $0.nat; }

    static $1 add($0 x, $1 y) { return $1.nat; }

    static $2 add($0 x, $2 y) { return $2.nat; }

    static $3 add($0 x, $3 y) { return $3.nat; }

    static $4 add($0 x, $4 y) { return $4.nat; }

    static $5 add($0 x, $5 y) { return $5.nat; }

    static $6 add($0 x, $6 y) { return $6.nat; }

    static $7 add($0 x, $7 y) { return $7.nat; }

    static $8 add($0 x, $8 y) { return $8.nat; }

    static $1 add($1 x, $0 y) { return $1.nat; }

    static $2 add($1 x, $1 y) { return $2.nat; }

    static $3 add($1 x, $2 y) { return $3.nat; }

    static $4 add($1 x, $3 y) { return $4.nat; }

    static $5 add($1 x, $4 y) { return $5.nat; }

    static $6 add($1 x, $5 y) { return $6.nat; }

    static $7 add($1 x, $6 y) { return $7.nat; }

    static $8 add($1 x, $7 y) { return $8.nat; }

    static $2 add($2 x, $0 y) { return $2.nat; }

    static $3 add($2 x, $1 y) { return $3.nat; }

    static $4 add($2 x, $2 y) { return $4.nat; }

    static $5 add($2 x, $3 y) { return $5.nat; }

    static $6 add($2 x, $4 y) { return $6.nat; }

    static $7 add($2 x, $5 y) { return $7.nat; }

    static $8 add($2 x, $6 y) { return $8.nat; }

    static $3 add($3 x, $0 y) { return $3.nat; }

    static $4 add($3 x, $1 y) { return $4.nat; }

    static $5 add($3 x, $2 y) { return $5.nat; }

    static $6 add($3 x, $3 y) { return $6.nat; }

    static $7 add($3 x, $4 y) { return $7.nat; }

    static $8 add($3 x, $5 y) { return $8.nat; }

    static $4 add($4 x, $0 y) { return $4.nat; }

    static $5 add($4 x, $1 y) { return $5.nat; }

    static $6 add($4 x, $2 y) { return $6.nat; }

    static $7 add($4 x, $3 y) { return $7.nat; }

    static $8 add($4 x, $4 y) { return $8.nat; }

    static $5 add($5 x, $0 y) { return $5.nat; }

    static $6 add($5 x, $1 y) { return $6.nat; }

    static $7 add($5 x, $2 y) { return $7.nat; }

    static $8 add($5 x, $3 y) { return $8.nat; }

    static $6 add($6 x, $0 y) { return $6.nat; }

    static $7 add($6 x, $1 y) { return $7.nat; }

    static $8 add($6 x, $2 y) { return $8.nat; }

    static $7 add($7 x, $0 y) { return $7.nat; }

    static $8 add($7 x, $1 y) { return $8.nat; }

    static $8 add($8 x, $0 y) { return $8.nat; }

    // ----------------------------------------------------------

    /// TYPE-LEVEL SUBTRACTION.

    static $0 sub($0 x, $0 y) { return $0.nat; }

    static $1 sub($1 x, $0 y) { return $1.nat; }

    static $0 sub($1 x, $1 y) { return $0.nat; }

    static $2 sub($2 x, $0 y) { return $2.nat; }

    static $1 sub($2 x, $1 y) { return $1.nat; }

    static $0 sub($2 x, $2 y) { return $0.nat; }

    static $3 sub($3 x, $0 y) { return $3.nat; }

    static $2 sub($3 x, $1 y) { return $2.nat; }

    static $1 sub($3 x, $2 y) { return $1.nat; }

    static $0 sub($3 x, $3 y) { return $0.nat; }

    static $4 sub($4 x, $0 y) { return $4.nat; }

    static $3 sub($4 x, $1 y) { return $3.nat; }

    static $2 sub($4 x, $2 y) { return $2.nat; }

    static $1 sub($4 x, $3 y) { return $1.nat; }

    static $0 sub($4 x, $4 y) { return $0.nat; }

    static $5 sub($5 x, $0 y) { return $5.nat; }

    static $4 sub($5 x, $1 y) { return $4.nat; }

    static $3 sub($5 x, $2 y) { return $3.nat; }

    static $2 sub($5 x, $3 y) { return $2.nat; }

    static $1 sub($5 x, $4 y) { return $1.nat; }

    static $0 sub($5 x, $5 y) { return $0.nat; }

    static $6 sub($6 x, $0 y) { return $6.nat; }

    static $5 sub($6 x, $1 y) { return $5.nat; }

    static $4 sub($6 x, $2 y) { return $4.nat; }

    static $3 sub($6 x, $3 y) { return $3.nat; }

    static $2 sub($6 x, $4 y) { return $2.nat; }

    static $1 sub($6 x, $5 y) { return $1.nat; }

    static $0 sub($6 x, $6 y) { return $0.nat; }

    static $7 sub($7 x, $0 y) { return $7.nat; }

    static $6 sub($7 x, $1 y) { return $6.nat; }

    static $5 sub($7 x, $2 y) { return $5.nat; }

    static $4 sub($7 x, $3 y) { return $4.nat; }

    static $3 sub($7 x, $4 y) { return $3.nat; }

    static $2 sub($7 x, $5 y) { return $2.nat; }

    static $1 sub($7 x, $6 y) { return $1.nat; }

    static $0 sub($7 x, $7 y) { return $0.nat; }

    static $8 sub($8 x, $0 y) { return $8.nat; }

    static $7 sub($8 x, $1 y) { return $7.nat; }

    static $6 sub($8 x, $2 y) { return $6.nat; }

    static $5 sub($8 x, $3 y) { return $5.nat; }

    static $4 sub($8 x, $4 y) { return $4.nat; }

    static $3 sub($8 x, $5 y) { return $3.nat; }

    static $2 sub($8 x, $6 y) { return $2.nat; }

    static $1 sub($8 x, $7 y) { return $1.nat; }

    static $0 sub($8 x, $8 y) { return $0.nat; }

    // ----------------------------------------------------------

    /// TYPE-LEVEL MULTIPLICATION.

    static $0 mul($0 x, $0 y) { return $0.nat; }

    static $0 mul($0 x, $1 y) { return $0.nat; }

    static $0 mul($0 x, $2 y) { return $0.nat; }

    static $0 mul($0 x, $3 y) { return $0.nat; }

    static $0 mul($0 x, $4 y) { return $0.nat; }

    static $0 mul($0 x, $5 y) { return $0.nat; }

    static $0 mul($0 x, $6 y) { return $0.nat; }

    static $0 mul($0 x, $7 y) { return $0.nat; }

    static $0 mul($0 x, $8 y) { return $0.nat; }

    static $0 mul($1 x, $0 y) { return $0.nat; }

    static $1 mul($1 x, $1 y) { return $1.nat; }

    static $2 mul($1 x, $2 y) { return $2.nat; }

    static $3 mul($1 x, $3 y) { return $3.nat; }

    static $4 mul($1 x, $4 y) { return $4.nat; }

    static $5 mul($1 x, $5 y) { return $5.nat; }

    static $6 mul($1 x, $6 y) { return $6.nat; }

    static $7 mul($1 x, $7 y) { return $7.nat; }

    static $8 mul($1 x, $8 y) { return $8.nat; }

    static $0 mul($2 x, $0 y) { return $0.nat; }

    static $2 mul($2 x, $1 y) { return $2.nat; }

    static $4 mul($2 x, $2 y) { return $4.nat; }

    static $6 mul($2 x, $3 y) { return $6.nat; }

    static $8 mul($2 x, $4 y) { return $8.nat; }

    static $0 mul($3 x, $0 y) { return $0.nat; }

    static $3 mul($3 x, $1 y) { return $3.nat; }

    static $6 mul($3 x, $2 y) { return $6.nat; }

    static $0 mul($4 x, $0 y) { return $0.nat; }

    static $4 mul($4 x, $1 y) { return $4.nat; }

    static $8 mul($4 x, $2 y) { return $8.nat; }

    static $0 mul($5 x, $0 y) { return $0.nat; }

    static $5 mul($5 x, $1 y) { return $5.nat; }

    static $0 mul($6 x, $0 y) { return $0.nat; }

    static $6 mul($6 x, $1 y) { return $6.nat; }

    static $0 mul($7 x, $0 y) { return $0.nat; }

    static $7 mul($7 x, $1 y) { return $7.nat; }

    static $0 mul($8 x, $0 y) { return $0.nat; }

    static $8 mul($8 x, $1 y) { return $8.nat; }

    // ----------------------------------------------------------

    /// TYPE-LEVEL DIVISION.

    static $0 div($0 x, $1 y) { return $0.nat; }

    static $0 div($0 x, $2 y) { return $0.nat; }

    static $0 div($0 x, $3 y) { return $0.nat; }

    static $0 div($0 x, $4 y) { return $0.nat; }

    static $0 div($0 x, $5 y) { return $0.nat; }

    static $0 div($0 x, $6 y) { return $0.nat; }

    static $0 div($0 x, $7 y) { return $0.nat; }

    static $0 div($0 x, $8 y) { return $0.nat; }

    static $1 div($1 x, $1 y) { return $1.nat; }

    static $0 div($1 x, $2 y) { return $0.nat; }

    static $0 div($1 x, $3 y) { return $0.nat; }

    static $0 div($1 x, $4 y) { return $0.nat; }

    static $0 div($1 x, $5 y) { return $0.nat; }

    static $0 div($1 x, $6 y) { return $0.nat; }

    static $0 div($1 x, $7 y) { return $0.nat; }

    static $0 div($1 x, $8 y) { return $0.nat; }

    static $2 div($2 x, $1 y) { return $2.nat; }

    static $1 div($2 x, $2 y) { return $1.nat; }

    static $0 div($2 x, $3 y) { return $0.nat; }

    static $0 div($2 x, $4 y) { return $0.nat; }

    static $0 div($2 x, $5 y) { return $0.nat; }

    static $0 div($2 x, $6 y) { return $0.nat; }

    static $0 div($2 x, $7 y) { return $0.nat; }

    static $0 div($2 x, $8 y) { return $0.nat; }

    static $3 div($3 x, $1 y) { return $3.nat; }

    static $1 div($3 x, $2 y) { return $1.nat; }

    static $1 div($3 x, $3 y) { return $1.nat; }

    static $0 div($3 x, $4 y) { return $0.nat; }

    static $0 div($3 x, $5 y) { return $0.nat; }

    static $0 div($3 x, $6 y) { return $0.nat; }

    static $0 div($3 x, $7 y) { return $0.nat; }

    static $0 div($3 x, $8 y) { return $0.nat; }

    static $4 div($4 x, $1 y) { return $4.nat; }

    static $2 div($4 x, $2 y) { return $2.nat; }

    static $1 div($4 x, $3 y) { return $1.nat; }

    static $1 div($4 x, $4 y) { return $1.nat; }

    static $0 div($4 x, $5 y) { return $0.nat; }

    static $0 div($4 x, $6 y) { return $0.nat; }

    static $0 div($4 x, $7 y) { return $0.nat; }

    static $0 div($4 x, $8 y) { return $0.nat; }

    static $5 div($5 x, $1 y) { return $5.nat; }

    static $2 div($5 x, $2 y) { return $2.nat; }

    static $1 div($5 x, $3 y) { return $1.nat; }

    static $1 div($5 x, $4 y) { return $1.nat; }

    static $1 div($5 x, $5 y) { return $1.nat; }

    static $0 div($5 x, $6 y) { return $0.nat; }

    static $0 div($5 x, $7 y) { return $0.nat; }

    static $0 div($5 x, $8 y) { return $0.nat; }

    static $6 div($6 x, $1 y) { return $6.nat; }

    static $3 div($6 x, $2 y) { return $3.nat; }

    static $2 div($6 x, $3 y) { return $2.nat; }

    static $1 div($6 x, $4 y) { return $1.nat; }

    static $1 div($6 x, $5 y) { return $1.nat; }

    static $1 div($6 x, $6 y) { return $1.nat; }

    static $0 div($6 x, $7 y) { return $0.nat; }

    static $0 div($6 x, $8 y) { return $0.nat; }

    static $7 div($7 x, $1 y) { return $7.nat; }

    static $3 div($7 x, $2 y) { return $3.nat; }

    static $2 div($7 x, $3 y) { return $2.nat; }

    static $1 div($7 x, $4 y) { return $1.nat; }

    static $1 div($7 x, $5 y) { return $1.nat; }

    static $1 div($7 x, $6 y) { return $1.nat; }

    static $1 div($7 x, $7 y) { return $1.nat; }

    static $0 div($7 x, $8 y) { return $0.nat; }

    static $8 div($8 x, $1 y) { return $8.nat; }

    static $4 div($8 x, $2 y) { return $4.nat; }

    static $2 div($8 x, $3 y) { return $2.nat; }

    static $2 div($8 x, $4 y) { return $2.nat; }

    static $1 div($8 x, $5 y) { return $1.nat; }

    static $1 div($8 x, $6 y) { return $1.nat; }

    static $1 div($8 x, $7 y) { return $1.nat; }

    static $1 div($8 x, $8 y) { return $1.nat; }

    // ----------------------------------------------------------

    /// TYPE-LEVEL REMAINDER.

    static $0 rem($0 x, $1 y) { return $0.nat; }

    static $0 rem($0 x, $2 y) { return $0.nat; }

    static $0 rem($0 x, $3 y) { return $0.nat; }

    static $0 rem($0 x, $4 y) { return $0.nat; }

    static $0 rem($0 x, $5 y) { return $0.nat; }

    static $0 rem($0 x, $6 y) { return $0.nat; }

    static $0 rem($0 x, $7 y) { return $0.nat; }

    static $0 rem($0 x, $8 y) { return $0.nat; }

    static $0 rem($1 x, $1 y) { return $0.nat; }

    static $1 rem($1 x, $2 y) { return $1.nat; }

    static $1 rem($1 x, $3 y) { return $1.nat; }

    static $1 rem($1 x, $4 y) { return $1.nat; }

    static $1 rem($1 x, $5 y) { return $1.nat; }

    static $1 rem($1 x, $6 y) { return $1.nat; }

    static $1 rem($1 x, $7 y) { return $1.nat; }

    static $1 rem($1 x, $8 y) { return $1.nat; }

    static $0 rem($2 x, $1 y) { return $0.nat; }

    static $0 rem($2 x, $2 y) { return $0.nat; }

    static $2 rem($2 x, $3 y) { return $2.nat; }

    static $2 rem($2 x, $4 y) { return $2.nat; }

    static $2 rem($2 x, $5 y) { return $2.nat; }

    static $2 rem($2 x, $6 y) { return $2.nat; }

    static $2 rem($2 x, $7 y) { return $2.nat; }

    static $2 rem($2 x, $8 y) { return $2.nat; }

    static $0 rem($3 x, $1 y) { return $0.nat; }

    static $1 rem($3 x, $2 y) { return $1.nat; }

    static $0 rem($3 x, $3 y) { return $0.nat; }

    static $3 rem($3 x, $4 y) { return $3.nat; }

    static $3 rem($3 x, $5 y) { return $3.nat; }

    static $3 rem($3 x, $6 y) { return $3.nat; }

    static $3 rem($3 x, $7 y) { return $3.nat; }

    static $3 rem($3 x, $8 y) { return $3.nat; }

    static $0 rem($4 x, $1 y) { return $0.nat; }

    static $0 rem($4 x, $2 y) { return $0.nat; }

    static $1 rem($4 x, $3 y) { return $1.nat; }

    static $0 rem($4 x, $4 y) { return $0.nat; }

    static $4 rem($4 x, $5 y) { return $4.nat; }

    static $4 rem($4 x, $6 y) { return $4.nat; }

    static $4 rem($4 x, $7 y) { return $4.nat; }

    static $4 rem($4 x, $8 y) { return $4.nat; }

    static $0 rem($5 x, $1 y) { return $0.nat; }

    static $1 rem($5 x, $2 y) { return $1.nat; }

    static $2 rem($5 x, $3 y) { return $2.nat; }

    static $1 rem($5 x, $4 y) { return $1.nat; }

    static $0 rem($5 x, $5 y) { return $0.nat; }

    static $5 rem($5 x, $6 y) { return $5.nat; }

    static $5 rem($5 x, $7 y) { return $5.nat; }

    static $5 rem($5 x, $8 y) { return $5.nat; }

    static $0 rem($6 x, $1 y) { return $0.nat; }

    static $0 rem($6 x, $2 y) { return $0.nat; }

    static $0 rem($6 x, $3 y) { return $0.nat; }

    static $2 rem($6 x, $4 y) { return $2.nat; }

    static $1 rem($6 x, $5 y) { return $1.nat; }

    static $0 rem($6 x, $6 y) { return $0.nat; }

    static $6 rem($6 x, $7 y) { return $6.nat; }

    static $6 rem($6 x, $8 y) { return $6.nat; }

    static $0 rem($7 x, $1 y) { return $0.nat; }

    static $1 rem($7 x, $2 y) { return $1.nat; }

    static $1 rem($7 x, $3 y) { return $1.nat; }

    static $3 rem($7 x, $4 y) { return $3.nat; }

    static $2 rem($7 x, $5 y) { return $2.nat; }

    static $1 rem($7 x, $6 y) { return $1.nat; }

    static $0 rem($7 x, $7 y) { return $0.nat; }

    static $7 rem($7 x, $8 y) { return $7.nat; }

    static $0 rem($8 x, $1 y) { return $0.nat; }

    static $0 rem($8 x, $2 y) { return $0.nat; }

    static $2 rem($8 x, $3 y) { return $2.nat; }

    static $0 rem($8 x, $4 y) { return $0.nat; }

    static $3 rem($8 x, $5 y) { return $3.nat; }

    static $2 rem($8 x, $6 y) { return $2.nat; }

    static $1 rem($8 x, $7 y) { return $1.nat; }

    static $0 rem($8 x, $8 y) { return $0.nat; }

    // ----------------------------------------------------------

    /*
     * Redneck-style Java source generator to emit type-level arithmetic operations.
     *
    static void main(String[] args) {
        enum IOp implements Fn2<Integer, Integer, Integer> {
            add("ADDITION",       Fn.function(Integer::sum)),
            sub("SUBTRACTION",    Fn.function((x, y) -> x - y)),
            mul("MULTIPLICATION", Fn.function((x, y) -> x * y)),
            div("DIVISION",       Fn.function((x, y) -> y == 0 ? Integer.MIN_VALUE : x / y)),
            rem("REMAINDER",      Fn.function((x, y) -> y == 0 ? Integer.MIN_VALUE : x % y))
            ;
            private static final String TYPE_TERM = "static $%d %s($%d x, $%d y) { return $%d.nat; }";
            private final String header;
            private final Fn2<Integer, Integer, Integer> op;
            IOp(String header, Fn2<Integer, Integer, Integer> op) {
                this.header = header;
                this.op = op;
            }
            @Override
            public Integer onApply(Integer x, Integer y) throws Throwable {
                return op.onApply(x, y);
            }
            static String emit(int rank) { return emit(rank, TYPE_TERM); }
            static String emit(final int maxRank, final String typeTerm) {
                final var srcCode = new StringBuilder();
                final var dedup = new HashSet<String>();
                for (var op : IOp.values()) {
                    srcCode.append("\n\t").append("// ----------------------------------------------------------").append("\n\n");
                    srcCode.append('\t').append(String.format("/// TYPE-LEVEL ARITHMETIC: %s.", op.header)).append("\n\n");
                    for (int x = 0; x < maxRank; ++x) {
                        for (int y = 0; y < maxRank; ++y) {
                            final var r = op.apply(x, y);
                            if (r >= 0 && r < maxRank) {
                                final var javaTerm = String.format(typeTerm, r, op.name(), x, y, r);
                                if (dedup.add(javaTerm)) {
                                    srcCode.append('\t').append(javaTerm).append('\n');
                                }
                            }
                        }
                    }
                }
                return srcCode.toString();
            }
        }
        System.out.println(IOp.emit(9));
    }*/
}
