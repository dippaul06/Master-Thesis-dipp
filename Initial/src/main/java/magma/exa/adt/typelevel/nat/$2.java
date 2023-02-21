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
 * Type-level representation of the natural number 2.
 */
public class $2 extends $1 {
    /* package private */ $2() { }

    /** Instance of natural number 2. */
    public static final $2 nat = new $2();

    /** Returns {@code $1} as preceding natural. */
    @Override public $1 pred() { return $1.nat; }

    /** Returns {@code $3} as succeeding natural. */
    @Override public $3 succ() { return $3.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 2; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$2"; }
}
