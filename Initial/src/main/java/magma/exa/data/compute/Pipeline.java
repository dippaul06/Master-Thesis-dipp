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

package magma.exa.data.compute;


import magma.exa.adt.mixin.Mixin;
import magma.exa.data.compute.pipe.Pipe;

/**
 * A pipeline specialization for reference values of type {@code A}
 * that allows type-safe composition with other heterogeneous pipeline
 * stages of arbitrary depth via a linear recursive type signature.
 *
 * @param <A>    type of produced values.
 * @param <TAIL> linear recursive pipeline type.
 * @param <SELF> self type of this stage.
 */
public interface Pipeline<A,
        TAIL extends HPipeline<?>,
        SELF extends Pipeline<A, TAIL, SELF>
        > extends Mixin.Self<SELF>, HPipeline.Stage<Pipe<A>, TAIL>
{

    // ----------------------------------------------------------
    //  PIPELINE.ABSTRACT-BASE
    // ----------------------------------------------------------

    /**
     * Abstract base for reference-values specialized pipeline.
     *
     * @param <A>    type of produced values.
     * @param <TAIL> linear recursive pipeline type.
     * @param <SELF> self type of this pipeline.
     */
    abstract class AbstractBase<A,
            TAIL extends HPipeline<?>,
            SELF extends Pipeline<A, TAIL, SELF>
            > extends HPipeline.Stage.AbstractBase<Pipe<A>, TAIL, SELF>
            implements Pipeline<A, TAIL, SELF> {

        /**
         * First stage Constructor.
         */
        protected AbstractBase() {
        }

        /**
         * Constructs the abstract base for a {@code RefPipeline}.
         *
         * @param head operator.
         * @param tail pipeline.
         */
        protected AbstractBase(final HOperator<?, Pipe<A>> head, final TAIL tail) {
            super(head, tail);
        }
    }


    // ----------------------------------------------------------
    //  REF-PIPELINE.STAGE
    // ----------------------------------------------------------

    /**
     * A pipeline stage that outputs reference-values.
     *
     * @param <A>    type of produced values.
     * @param <TAIL> linear recursive pipeline type.
     * @param <SELF> self type of this stage.
     */
    abstract class Stage<A,
            TAIL extends HPipeline<?>,
            SELF extends Pipeline<A, TAIL, SELF>
            > extends AbstractBase<A, TAIL, SELF> {

        /**
         * First stage Constructor.
         */
        protected Stage() { }

        /**
         * Constructs a stage for a {@code RefPipeline}.
         *
         * @param tail pipeline.
         * @param head operator.
         */
        protected Stage(final TAIL tail, final HOperator<?, Pipe<A>> head) {
            super(head, tail);
        }
    }
}
