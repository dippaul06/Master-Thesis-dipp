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
 * Type-level representation of the natural number 1.
 */
public class $1 extends $0 {
    /* package private */ $1() { }

    /** Instance of natural number 1. */
    public static final $1 nat = new $1();

    /** Returns {@code $0} as preceding natural. */
    @Override public $0 pred() { return $0.nat; }

    /** Returns {@code $2} as succeeding natural. */
    @Override public $2 succ() { return $2.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 1; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$1"; }
}
