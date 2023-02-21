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

package magma.exa.control.adt.traversal;

import magma.exa.adt.mixin.Mixin;
import magma.exa.adt.typelevel.signature.$Fn;
import magma.exa.control.function.Fn1;
import magma.exa.data.index.*;

/**
 * THEORY: INTERNAL VS. EXTERNAL ITERATORS.
 *
 * In the discussion of the iterator pattern in the book "Gang Of Four"
 * Patterns we read (page 260): Who controls the iteration?
 *
 * A fundamental question is to decide, which party controls the iteration,
 * "the iterator" or "the client", which uses the iterator.
 *
 * If a client controls the iteration, the iterator is called an "external
 * iterator" (C++ and Java), and if the iterator controls the iteration, the
 * iterator is an "internal iterator", which is predominant in languages that
 * are considered functional (in fact, external iterators imply an explicit
 * management of the iteration, which requires mutable state, which cannot
 * be expressed in a pure applicative abstraction).
 *
 * Clients using an external iterator:
 *
 * (A) must advance the traversal, and
 * (B) explicitly request the next value from the underlying data source.
 *
 * In contrast, "internal iterators" are passed an "operation" (lambda expression)
 * for execution, and the iterator applies this operation to every value in the
 * traversed data source.
 *
 * External iterators are considered more flexible than internal iterators.
 * This is due to the fact that the client has control over the iteration and
 * thus ~ code locations ~ are exposed to the outside (i.e. the using client)
 * for injecting further logic into the iteration.
 *
 * The "external iterator protocol" in Java is based on two methods,
 *
 * (1) {@link java.util.Iterator#hasNext()} 	<=> (A)
 * (2) {@link java.util.Iterator#next()} 		<=> (B)
 *
 * The access to the next value may involve (but does not force) the call of
 * both methods. Consequently, the correct coding of an iterator requires a
 * certain amount of defensive and double coding to ensure correct interaction
 * with the client. Think about this for example:
 *
 * What if the client does not call hasNext() before next() ?
 * What if the client calls hasNext() twice ?
 *
 * Another implication of the external two-method protocol is keeping track of
 * the current iteration state, such as looking ahead of a value (and tracking
 * whether you have already looked ahead). Taken together, these requirements
 * add up to a considerable amount of overhead in terms of complexity and
 * performance for >> access per value <<.
 *
 * The internal iteration protocol encapsulates the iteration logic within
 * the iterator and does not impose the load on the client to maintain the
 * iteration state.
 *
 * ----------------------------------------------------------
 * REFERENCES
 * - http://gafter.blogspot.com/2007/07/internal-versus-external-iterators.html
 * - Rust Iterators https://doc.rust-lang.org/std/iter/index.html
 * - Java Spliterator {@link java.util.Spliterator}.
 * - https://www.oodesign.com/iterator-pattern.html
 * - https://en.wikipedia.org/wiki/Iteratee
 * - https://developer.apple.com/documentation/swift/iteratorprotocol
 * - https://www.cs.ox.ac.uk/jeremy.gibbons/publications/iterator.pdf
 * - https://cr.openjdk.java.net/~jrose/values/iterator-vs-cursor.html
 * - https://en.wikipedia.org/wiki/Iterator
 * - https://en.wikipedia.org/wiki/Generator_(computer_programming)
 * - https://softwareengineering.stackexchange.com/questions/102912/iterators-versus-cursors-in-java
 * - https://www.cs.ox.ac.uk/jeremy.gibbons/publications/dgp.pdf
 * - https://blog.plover.com/prog/haskell/traversable.html
 * ----------------------------------------------------------
 *
 * A cursor abstracts traversal over an underlying data source.
 * The cursor is designed as 'internal iterator' that accepts
 * higher order cursor operations.
 *
 * @param <OP> type of cursor operation.
 */
public interface ICursor<OP extends $Fn> {

    // ----------------------------------------------------------
    //  CURSOR.RESETTABLE
    // ----------------------------------------------------------

    /**
     * Mixin type to expose a reset behaviour.
     */
    @Mixin interface Resettable {

        /**
         * Resets the internal state of the cursor to the initial state.
         * Returns true to indicate that the reset was successful.
         */
        default boolean reset() {
            return false;
        }
    }

    // ----------------------------------------------------------
    //  CURSOR.STATE
    // ----------------------------------------------------------

    /**
     * Describe iteration state machine.
     */
    interface State {
        int S_INIT      = 0x00000000;
        int S_ACTIVE    = 0x00000001;
        int S_EXITED    = 0x00000002;
        int S_COMPLETED = 0x00000004;
    }

    static boolean exited(final long status) {
        return (status & State.S_EXITED) != 0;
    }

    static boolean completed(final long status) {
        return (status & State.S_COMPLETED) != 0;
    }

    // ----------------------------------------------------------
    //  CURSOR.CONTROL
    // ----------------------------------------------------------

    /**
     * Transitions to control loop state (machine).
     * (Exposed to client operation)
     */
    interface Control {
        //  break | continue | skip | move | seek | locate | retry | suspend | resume
        void exit();
    }

    // ----------------------------------------------------------
    //  CURSOR.TRY
    // ----------------------------------------------------------

    /**
     * Single-step iteration protocol.
     *
     * - Single-step operation.
     * - Traversal is performed by passing one value at a time.
     * - Requires dedicated activation for each step.
     *
     * @param <OP> type of cursor operation.
     */
    interface Try<OP extends $Fn> extends ICursor<OP> {

        // ----------------------------------------------------------
        //  CURSOR.TRY.NEXT
        // ----------------------------------------------------------

        /**
         * Single-step iteration in forward direction.
         *
         * @param <OP> type of cursor operation.
         */
        @Mixin interface Next<OP extends $Fn> extends Try<OP> {

            /**
             * Traverses values individually in forward direction. In case of
             * a remaining value, the given operation is applied to it and
             * true is returned to request further invocation. If no remaining
             * value exists, false is returned without applying the operation.
             * <p>
             * Exceptions thrown in the operation are relayed to the caller.
             *
             * @param operation to be applied to each traversed value.
             * @return false if there are no values upon entry, otherwise true.
             */
            boolean tryNext(OP operation);

            // ----------------------------------------------------------
            //  CURSOR.TRY.NEXT.INDEXED
            // ----------------------------------------------------------

            /**
             * Refines the {@link Next} operation by additionally passing
             * an index of type {@link Ix} that wraps the cursor operation.
             *
             * @param <OP> type of cursor operation.
             * @param <IX> type of operation index.
             */
            @Mixin interface Indexed<OP extends $Fn, IX extends Ix<OP>> extends Next<OP> {

                /**
                 * Traverses indexed values individually in the forward direction.
                 *
                 * @param operation to be applied to each traversed value.
                 * @return false if there are no values upon entry, otherwise true.
                 * @see Next
                 */
                boolean tryNextIndexed(IX operation);
            }
        }

        // ----------------------------------------------------------
        //  CURSOR.TRY.PREV
        // ----------------------------------------------------------

        /**
         * Single-step iteration in reverse direction.
         *
         * @param <OP> type of cursor operation.
         */
        @Mixin interface Prev<OP extends $Fn> extends Try<OP> {

            /**
             * Traverses values individually in reverse direction. In case
             * of a remaining value, the given operation is applied to it and
             * true is returned to request further invocation. If no remaining
             * value exists, false is returned without applying the operation.
             * <p>
             * Exceptions thrown in the operation are relayed to the caller.
             *
             * @param operation to be applied to each traversed value.
             * @return false if there are no values upon entry, otherwise true.
             */
            boolean tryPrev(OP operation);

            // ----------------------------------------------------------
            //  CURSOR.TRY.PREV.INDEXED
            // ----------------------------------------------------------

            /**
             * Refine the {@link Prev} operation by additionally passing
             * an index of type {@link Ix} that wraps the cursor operation.
             *
             * @param <OP> type of cursor operation.
             * @param <IX> type of operation index.
             */
            @Mixin interface Indexed<OP extends $Fn, IX extends Ix<OP>> extends Prev<OP> {

                /**
                 * Traverses indexed values individually in reverse direction.
                 *
                 * @param operation to be applied to each traversed value.
                 * @return false if there are no values upon entry, otherwise true.
                 * @see Prev
                 */
                boolean tryPrevIndexed(IX operation);
            }
        }
    }

    // ----------------------------------------------------------
    //  CURSOR.FOR
    // ----------------------------------------------------------

    /**
     * Bulk iteration protocol.
     *
     * @param <OP> type of cursor operation.
     */
    interface For<OP extends $Fn> extends ICursor<OP> {

        // ----------------------------------------------------------
        //  CURSOR.FOR.NEXT
        // ----------------------------------------------------------

        /**
         * Bulk iteration in forward direction.
         *
         * @param <OP> type of cursor operation.
         */
        @Mixin interface Next<OP extends $Fn> extends For<OP> {

            /**
             * Traverses all values that remain in forward direction and
             * applies the given operation to each value until all values
             * are processed or the given operation throws an exception.
             *
             * @param operation to be applied to each traversed value.
             */
            void forNext(OP operation);

            // ----------------------------------------------------------
            //  CURSOR.FOR.NEXT.INDEXED
            // ----------------------------------------------------------

            /**
             * Refine the {@link Next} operation by additionally passing
             * an index of type {@link Ix} that wraps the cursor operation.
             *
             * @param <OP> type of cursor operation.
             * @param <IX> type of operation index.
             */
            @Mixin interface Indexed<OP extends $Fn, IX extends Ix<OP>> extends Next<OP> {

                /**
                 * Traverses indexed values that remain in forward direction, in bulk.
                 *
                 * @param operation to be applied to each traversed value.
                 * @see Next
                 */
                void forNextIndexed(IX operation);
            }
        }

        // ----------------------------------------------------------
        //  CURSOR.FOR.PREV
        // ----------------------------------------------------------

        /**
         * Bulk iteration in reverse direction.
         *
         * @param <OP> type of cursor operation.
         */
        @Mixin interface Prev<OP extends $Fn> extends For<OP> {

            /**
             * Traverses all values that remain in reverse direction and
             * applies the given operation to each value until all values
             * are processed or the given operation throws an exception.
             *
             * @param operation to be applied to each traversed value.
             */
            void forPrev(OP operation);

            // ----------------------------------------------------------
            //  CURSOR.FOR.PREV.INDEXED
            // ----------------------------------------------------------

            /**
             * Refine the {@link Prev} operation by additionally passing
             * an index of type {@link Ix} that wraps the cursor operation.
             *
             * @param <OP> type of cursor operation.
             * @param <IX> type of operation index.
             */
            @Mixin interface Indexed<OP extends $Fn, IX extends Ix<OP>> extends Prev<OP> {

                /**
                 * Traverses indexed values that remain in reverse direction, in bulk.
                 *
                 * @param operation to be applied to each traversed value.
                 * @see Prev
                 */
                void forPrevIndexed(IX operation);
            }
        }
    }

    // ----------------------------------------------------------
    //  CURSOR.WHILE
    // ----------------------------------------------------------

    /**
     * Until operator provides iteration control. The cursor operation
     * is passed by construction as a lambda expression that accesses
     * the outer scope with the control continuation.
     *
     * @param <OC> type of operation control.
     * @param <OP> type of cursor operation.
     */
    @FunctionalInterface
    interface Until<OC extends Control, OP extends $Fn> extends Fn1<OC, OP> {

    }

    /**
     * Controlled-Bulk iteration protocol.
     *
     * @param <OC> type of operation control.
     * @param <OP> type of cursor operation.
     */
    interface While<OC extends Control, OP extends $Fn> extends ICursor<OP> {

        // ----------------------------------------------------------
        //  CURSOR.WHILE.NEXT
        // ----------------------------------------------------------

        /**
         * Controlled bulk iteration in forward direction.
         *
         * @param <OC> type of operation control.
         * @param <OP> type of cursor operation.
         */
        @Mixin interface Next<OC extends Control, OP extends $Fn> extends While<OC, OP> {

            /**
             * Traverses all values remaining in the forward direction and
             * applies the given operation to each value until (I) all values
             * are processed, (II) the iteration is terminated via the passed
             * driver control, or (III) the operation throws an exception.
             * <p>
             * This operation returns a completion status to the client indicating
             * whether the source is exhausted or the iteration was terminated
             * via the passed driver control.
             *
             * @param driver passing control to cursor operation.
             * @return completion status.
             */
            long whileNext(Until<OC, OP> driver);

            // ----------------------------------------------------------
            //  CURSOR.WHILE.NEXT.INDEXED
            // ----------------------------------------------------------

            /**
             * Refine the {@link Next} operation by additionally passing
             * an index of type {@link Ix} that wraps the cursor operation.
             *
             * @param <OC> type of operation control.
             * @param <OP> type of cursor operation.
             * @param <IX> type of operation index.
             */
            @Mixin interface Indexed<OC extends Control, OP extends $Fn, IX extends Ix<OP>>
                    extends Next<OC, OP> {

                /**
                 * Traverses indexed values that remain in forward direction.
                 *
                 * @param driver passing control to indexed cursor operation.
                 * @return operation status.
                 * @see Next
                 */
                long whileNextIndexed(Until<OC, IX> driver);
            }
        }

        // ----------------------------------------------------------
        //  CURSOR.WHILE.PREV
        // ----------------------------------------------------------

        /**
         * Controlled bulk iteration in reverse direction.
         *
         * @param <OC> type of operation control.
         * @param <OP> type of cursor operation.
         */
        @Mixin interface Prev<OC extends Control, OP extends $Fn> extends While<OC, OP> {

            /**
             * Traverses all values remaining in the reverse direction and
             * applies the given operation to each value until (I) all values
             * are processed, (II) the iteration is terminated via the passed
             * driver control, or (III) the operation throws an exception.
             * <p>
             * This operation returns a completion status to the client indicating
             * whether the source is exhausted or the iteration was terminated
             * via the passed driver control.
             *
             * @param driver passing control to cursor operation.
             * @return completion status.
             */
            long whilePrev(Until<OC, OP> driver);

            // ----------------------------------------------------------
            //  CURSOR.WHILE.PREV.INDEXED
            // ----------------------------------------------------------

            /**
             * Refines the {@link Prev} operation by additionally passing
             * an index of type {@link Ix} that wraps the cursor operation.
             *
             * @param <OC> type of operation control.
             * @param <OP> type of cursor operation.
             * @param <IX> type of operation index.
             */
            @Mixin interface Indexed<OC extends Control, OP extends $Fn, IX extends Ix<OP>>
                    extends Prev<OC, OP> {

                /**
                 * Traverses indexed values that remain in reverse direction.
                 *
                 * @param driver passing control to indexed cursor operation.
                 * @return operation status.
                 * @see Prev
                 */
                long whilePrevIndexed(Until<OC, IX> driver);
            }
        }
    }

    // ----------------------------------------------------------
    //  CURSOR.SIMPLEX
    // ----------------------------------------------------------

    /**
     * A simplex cursor abstracts unidirectional traversal over an underlying
     * source. The cursor is designed as an internal iterator that accepts
     * higher order operations.
     *
     * @param <OC> type of operation control.
     * @param <OP> type of cursor operation.
     */
    interface Simplex<OC extends Control, OP extends $Fn>

            extends While.Next<OC, OP>,

            For.Next<OP>,

            Try.Next<OP>
    {
        // ----------------------------------------------------------
        //  CURSOR.SIMPLEX.INDEXED
        // ----------------------------------------------------------

        /**
         * Refines the {@link Simplex} by additionally
         * passing an index of type {@link Ix}.
         *
         * @param <OC> type of operation control.
         * @param <OP> type of cursor operation.
         * @param <IX> type of operation index.
         */
        interface Indexed<OC extends Control, OP extends $Fn, IX extends Ix<OP>>

                extends Simplex<OC, OP>,

                While.Next.Indexed<OC, OP, IX>,

                For.Next.Indexed<OP, IX>,

                Try.Next.Indexed<OP, IX> {
        }
    }

    // ----------------------------------------------------------
    //  CURSOR.DUPLEX
    // ----------------------------------------------------------

    /**
     * The duplex cursor refines the simplex cursor by adding the
     * ability to move in reverse direction.
     *
     * @param <OC> type of operation control.
     * @param <OP> type of cursor operation.
     */
    interface Duplex<OC extends Control, OP extends $Fn>

            extends Simplex<OC, OP>,

            While.Prev<OC, OP>,

            For.Prev<OP>,

            Try.Prev<OP>
    {
        // ----------------------------------------------------------
        //  CURSOR.DUPLEX.INDEXED
        // ----------------------------------------------------------

        /**
         * Refines the {@link Duplex} by additionally passing
         * an index of type {@link Ix}.
         *
         * @param <OC> type of operation control.
         * @param <OP> type of cursor operation.
         * @param <IX> type of operation index.
         */
        interface Indexed<OC extends Control, OP extends $Fn, IX extends Ix<OP>>

                extends Simplex.Indexed<OC, OP, IX>, Duplex<OC, OP>,

                While.Prev.Indexed<OC, OP, IX>,

                For.Prev.Indexed<OP, IX>,

                Try.Prev.Indexed<OP, IX> {
        }
    }

    // ----------------------------------------------------------

    static <OP extends $Fn> Ix1D<OP> lift1D(final OP op) { return i1 -> op; }
    static <OP extends $Fn> Ix2D<OP> lift2D(final OP op) { return (i1, i2) -> op; }
    static <OP extends $Fn> Ix3D<OP> lift3D(final OP op) { return (i1, i2, i3) -> op; }

    static <OC extends Control, OP extends $Fn>
    Until<OC, Ix1D<OP>> lift1D(final Until<OC, OP> until) {
        return control -> i1 -> until.apply(control);
    }

    static <OC extends Control, OP extends $Fn>
    Until<OC, Ix2D<OP>> lift2D(final Until<OC, OP> until) {
        return control -> (i1, i2) -> until.apply(control);
    }

    static <OC extends Control, OP extends $Fn>
    Until<OC, Ix3D<OP>> lift3D(final Until<OC, OP> until) {
        return control -> (i1, i2, i3) -> until.apply(control);
    }
}
