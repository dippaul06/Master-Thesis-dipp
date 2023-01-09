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
 * Type-level representation of the natural number 0.
 */
public class $0 implements $N {
    /* package private */ $0() { }

    /** Instance of natural number 0. */
    public static final $0 nat = new $0();

    /** Returns {@code $0} as preceding natural. */
    @Override public $0 pred() { return $0.nat; }

    /** Returns {@code $1} as succeeding natural. */
    @Override public $1 succ() { return $1.nat; }

    /** Returns the runtime value encoding as {@code int}. */
    @Override public int ordinal() { return 0; }

    /** Returns the string representation of this natural number.*/
    @Override public String toString()  { return "$0"; }
}
