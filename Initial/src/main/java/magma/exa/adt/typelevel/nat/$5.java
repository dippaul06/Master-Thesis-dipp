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

/**
 * Type-level representation of the natural number 5.
 */
public class $5 extends $4 {
    /* package private */ $5() { }

    /** Instance of natural number 5. */
    public static final $5 nat = new $5();

    /** Returns {@code $4} as preceding natural. */
    @Override public $4 pred() { return $4.nat; }

    /** Returns {@code $6} as succeeding natural. */
    @Override public $6 succ() { return $6.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 5; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$5"; }
}
