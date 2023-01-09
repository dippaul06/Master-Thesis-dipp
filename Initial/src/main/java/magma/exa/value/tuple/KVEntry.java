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

package magma.exa.value.tuple;

import java.util.Map;

import magma.exa.adt.mixin.Mixin;
import magma.exa.control.exception.Exceptions;
import magma.exa.value.adt.product.Product2;

/**
 *
 * @param <K>
 * @param <V>
 */
public interface KVEntry<K, V> extends Map.Entry<K, V>,

        Product2<K, V>,

        Mixin.Key<K>, Mixin.Value<V>
{

    @Override K key();

    @Override V value();

    @Override default K getKey()       { return key();   }

    @Override default V getValue()     { return value(); }

    @Override default K _1()           { return key();   }

    @Override default V _2()           { return value(); }

    @Override default V setValue(V __) { throw Exceptions.illegalState(); }


    static <K, V> KVEntry<K, V> of(final K key, final V value) {

        final class KVTuple implements KVEntry<K, V> {

            private KVTuple() { }

            @Override public K key()   { return key;   }

            @Override public V value() { return value; }

            @Override public String toString() {
                return String.format("[%s => %s]", key, value);
            }
        }

        return new KVTuple();
    }
}
