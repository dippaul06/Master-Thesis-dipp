package datastructures.concurrent;


/*
 * Written by Cliff Click and released to the public domain, as explained at
 * http://creativecommons.org/licenses/publicdomain
 */

import java.util.Map;

/**
 * A simple implementation of {@link Map.Entry}.
 * Does not implement 'java.util.Map.Entry', that is done by users of the class.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Cliff Click
 * @since 1.5
 */
@SuppressWarnings("all")
abstract class AbstractEntry<K, V> implements Map.Entry<K, V> {
    /**
     * Strongly typed key
     */
    protected final K _key;
    /**
     * Strongly typed value
     */
    protected V _val;

    public AbstractEntry(final K key, final V val) {
        _key = key;
        _val = val;
    }

    public AbstractEntry(final Map.Entry<K, V> e) {
        _key = e.getKey();
        _val = e.getValue();
    }

    private static boolean eq(final Object o1, final Object o2) {
        return (o1 == null ? o2 == null : o1.equals(o2));
    }

    /**
     * Return "key=val" string
     */
    public String toString() { return _key + "=" + _val; }

    /**
     * Return key
     */
    public K getKey() { return _key; }

    /**
     * Return val
     */
    public V getValue() { return _val; }

    /**
     * Equal if the underlying key & value are equal
     */
    public boolean equals(final Object o) {
        if (!(o instanceof Map.Entry)) return false;
        final Map.Entry e = (Map.Entry) o;
        return eq(_key, e.getKey()) && eq(_val, e.getValue());
    }

    /**
     * Compute <code>"key.hashCode() ^ val.hashCode()"</code>
     */
    public int hashCode() {
        return
                ((_key == null) ? 0 : _key.hashCode()) ^
                        ((_val == null) ? 0 : _val.hashCode());
    }
}

