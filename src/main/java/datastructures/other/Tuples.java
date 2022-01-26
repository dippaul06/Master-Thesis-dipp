package datastructures.other;

import com.google.common.collect.ComparisonChain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

// ---------------------------------------------------
// TUPLES.
// ---------------------------------------------------
public enum Tuples {
    ;

    // ---------------------------------------------------
    // TUPLE.
    // ---------------------------------------------------
    public static abstract class Tuple implements Serializable, Iterable<Object>, Comparable<Tuple> {
        // ---------------------------------------------------
        public abstract <T> T field(int pos);
        public abstract <T> void field(int pos, T value);
        public abstract int length();
        public abstract Iterator<Object> iterator();
        public abstract int compareTo(Tuple o);

    }

    // ---------------------------------------------------
    public static <T1> Tuple1<T1> of(T1 _1) { return new Tuple1<T1>(_1); }

    public static <T1, T2> Tuple2<T1, T2> of(T1 _1, T2 _2) { return new Tuple2<>(_1, _2); }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 _1, T2 _2, T3 _3) { return new Tuple3<>(_1, _2, _3); }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of(T1 _1, T2 _2, T3 _3, T4 _4) { return new Tuple4<>(_1, _2, _3, _4); }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) { return new Tuple5<>(_1, _2, _3, _4, _5); }

    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) { return new Tuple6<>(_1, _2, _3, _4, _5, _6); }

    public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> of(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) { return new Tuple7<>(_1, _2, _3, _4, _5, _6, _7); }


    public static<V, T extends Tuple> V _1(T t) { return t.field(0); }

    public static<V, T extends Tuple> V _2(T t) { return t.field(1); }

    public static<V, T extends Tuple> V _3(T t) { return t.field(2); }

    public static<V, T extends Tuple> V _4(T t) { return t.field(3); }

    public static<V, T extends Tuple> V _5(T t) { return t.field(4); }

    public static<V, T extends Tuple> V _6(T t) { return t.field(5); }

    public static<V, T extends Tuple> V _7(T t) { return t.field(6); }


    public static <T1, T2> Tuple2<T1, T2> of(Map.Entry<T1, T2> e) { return of(e.getKey(), e.getValue()); }

    // ---------------------------------------------------
    // TUPLE 1.
    // ---------------------------------------------------
    @SuppressWarnings("unchecked")
    public static class Tuple1<T1> extends Tuple {
        public T1 _1;
        // ---------------------------------------------------
        Tuple1() { this((T1)null); }
        Tuple1(T1 _1) { this._1 = _1; }
        Tuple1(Tuple1<T1> t) { this(t._1); }
        // ---------------------------------------------------
        public int length() { return 1; }
        public Iterator<Object> iterator() { return Arrays.asList(new Object[]{_1}).iterator(); }
        public <T> T field(int pos) {
            switch(pos) {
                case 0: return (T) this._1;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public <T> void field(int pos, T value) {
            switch(pos) {
                case 0: this._1 = (T1) value; break;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        // ---------------------------------------------------
        public int compareTo(Tuple t) {
            if (this == t) return 0;
            Tuple1<T1> o = (Tuple1<T1>)t;
            ComparisonChain cc = ComparisonChain.start();
            if (_1 instanceof Comparable) cc.compare((Comparable<?>) _1, (Comparable<?>) o._1);
            return cc.result();
        }
        public int hashCode() { return 31 + ((_1 == null) ? 0 : _1.hashCode()); }
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Tuple1<T1> other = (Tuple1<T1>) obj;
            if (_1 == null) { if (other._1 != null) return false; }
            else if (!_1.equals(other._1))
                return false;
            return true;
        }
        public String toString() { return "(" + _1 + ")"; }
    }

    // ---------------------------------------------------
    // TUPLE 2.
    // ---------------------------------------------------
    @SuppressWarnings("unchecked")
    public static class Tuple2<T1, T2> extends Tuple {
        public T1 _1;
        public T2 _2;
        // ---------------------------------------------------
        Tuple2() { this(null, null); }
        Tuple2(Tuple2<T1, T2> t) { this(t._1, t._2); }
        Tuple2(T1 _1, T2 _2) {
            this._1 = _1;
            this._2 = _2;
        }
        // ---------------------------------------------------
        public int length() { return 2; }
        public Iterator<Object> iterator() { return Arrays.asList(new Object[]{_1, _2}).iterator(); }
        public <T> T field(int pos) {
            switch(pos) {
                case 0: return (T) this._1;
                case 1: return (T) this._2;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public <T> void field(int pos, T value) {
            switch(pos) {
                case 0: this._1 = (T1) value; break;
                case 1: this._2 = (T2) value; break;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        // ---------------------------------------------------
        public int compareTo(Tuple t) {
            if (this == t) return 0;
            Tuple2<T1, T2> o = (Tuple2<T1, T2>)t;
            ComparisonChain cc = ComparisonChain.start();
            if (_1 instanceof Comparable) cc.compare((Comparable<?>) _1, (Comparable<?>) o._1);
            if (_2 instanceof Comparable) cc.compare((Comparable<?>) _2, (Comparable<?>) o._2);
            return cc.result();
        }
        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = prime * result + ((_1 == null) ? 0 : _1.hashCode());
            result = prime * result + ((_2 == null) ? 0 : _2.hashCode());
            return result;
        }
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Tuple2<T1, T2> other = (Tuple2<T1, T2>) obj;
            if (_1 == null) { if (other._1 != null)  return false; }
            else if (!_1.equals(other._1)) return false;
            if (_2 == null) { if (other._2 != null) return false; }
            else if (!_2.equals(other._2)) return false;
            return true;
        }
        public String toString() { return "(" + _1 + "," + _2 + ")"; }
    }

    // ---------------------------------------------------
    // TUPLE 3.
    // ---------------------------------------------------
    @SuppressWarnings("unchecked")
    public static class Tuple3<T1, T2, T3> extends Tuple {
        public T1 _1;
        public T2 _2;
        public T3 _3;
        // ---------------------------------------------------
        Tuple3() { this(null, null, null); }
        Tuple3(Tuple3<T1, T2, T3> t) { this(t._1, t._2, t._3); }
        Tuple3(T1 _1, T2 _2, T3 _3) {
            this._1 = _1;
            this._2 = _2;
            this._3 = _3;
        }
        // ---------------------------------------------------
        public int length() { return 3; }
        public Iterator<Object> iterator() { return Arrays.asList(new Object[]{_1, _2, _3}).iterator(); }
        public <T> T field(int pos) {
            switch(pos) {
                case 0: return (T) this._1;
                case 1: return (T) this._2;
                case 2: return (T) this._3;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public <T> void field(int pos, T value) {
            switch(pos) {
                case 0:this._1 = (T1) value;break;
                case 1:this._2 = (T2) value;break;
                case 2:this._3 = (T3) value;break;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        // ---------------------------------------------------
        public int compareTo(Tuple t) {
            if (this == t) return 0;
            Tuple3<T1, T2, T3> o = (Tuple3<T1, T2, T3>)t;
            ComparisonChain cc = ComparisonChain.start();
            if (_1 instanceof Comparable) cc.compare((Comparable<?>) _1, (Comparable<?>) o._1);
            if (_2 instanceof Comparable) cc.compare((Comparable<?>) _2, (Comparable<?>) o._2);
            if (_3 instanceof Comparable) cc.compare((Comparable<?>) _3, (Comparable<?>) o._3);
            return cc.result();
        }
        public int hashCode() {
            int p = 31;
            int r = 1;
            r = p * r + ((_1 == null) ? 0 : _1.hashCode());
            r = p * r + ((_2 == null) ? 0 : _2.hashCode());
            r = p * r + ((_3 == null) ? 0 : _3.hashCode());
            return r;
        }
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Tuple3<T1, T2, T3> other = (Tuple3<T1, T2, T3>) obj;
            if (_1 == null) { if (other._1 != null) return false; }
            else if (!_1.equals(other._1)) return false;
            if (_2 == null) { if (other._2 != null) return false; }
            else if (!_2.equals(other._2)) return false;
            if (_3 == null) { if (other._3 != null) return false; }
            else if (!_3.equals(other._3)) return false;
            return true;
        }
        public String toString() { return "(" + _1 + "," + _2 + "," + _3 + ")"; }
    }

    // ---------------------------------------------------
    // TUPLE 4.
    // ---------------------------------------------------
    @SuppressWarnings("unchecked")
    public static final class Tuple4<T1, T2, T3, T4> extends Tuple {
        public T1 _1;
        public T2 _2;
        public T3 _3;
        public T4 _4;
        // ---------------------------------------------------
        Tuple4() { this(null, null, null, null); }
        Tuple4(Tuple4<T1, T2, T3, T4> t) { this(t._1, t._2, t._3, t._4); }
        Tuple4(T1 _1, T2 _2, T3 _3, T4 _4) {
            this._1 = _1;
            this._2 = _2;
            this._3 = _3;
            this._4 = _4;
        }
        // ---------------------------------------------------
        public int length() { return 4; }
        public Iterator<Object> iterator() { return Arrays.asList(new Object[]{_1, _2, _3, _4}).iterator(); }
        public <T> T field(int pos) {
            switch(pos) {
                case 0: return (T) this._1;
                case 1: return (T) this._2;
                case 2: return (T) this._3;
                case 3: return (T) this._4;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public <T> void field(int pos, T value) {
            switch(pos) {
                case 0: this._1 = (T1) value; break;
                case 1: this._2 = (T2) value; break;
                case 2: this._3 = (T3) value; break;
                case 3: this._4 = (T4) value; break;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public int compareTo(Tuple t) {
            if (this == t) return 0;
            Tuple4<T1, T2, T3, T4> o = (Tuple4<T1, T2, T3, T4>)t;
            ComparisonChain cc = ComparisonChain.start();
            if (_1 instanceof Comparable) cc.compare((Comparable<?>) _1, (Comparable<?>) o._1);
            if (_2 instanceof Comparable) cc.compare((Comparable<?>) _2, (Comparable<?>) o._2);
            if (_3 instanceof Comparable) cc.compare((Comparable<?>) _3, (Comparable<?>) o._3);
            if (_4 instanceof Comparable) cc.compare((Comparable<?>) _4, (Comparable<?>) o._4);
            return cc.result();
        }
        public int hashCode() {
            int p = 31, r = 1;
            r = p * r + ((_1 == null) ? 0 : _1.hashCode());
            r = p * r + ((_2 == null) ? 0 : _2.hashCode());
            r = p * r + ((_3 == null) ? 0 : _3.hashCode());
            r = p * r + ((_4 == null) ? 0 : _4.hashCode());
            return r;
        }
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Tuple4<T1, T2, T3, T4> other = (Tuple4<T1, T2, T3, T4>) obj;
            if (_1 == null) { if (other._1 != null) return false; }
            else if (!_1.equals(other._1)) return false;
            if (_2 == null) { if (other._2 != null) return false; }
            else if (!_2.equals(other._2)) return false;
            if (_3 == null) { if (other._3 != null) return false; }
            else if (!_3.equals(other._3)) return false;
            if (_4 == null) { if (other._4 != null) return false; }
            else if (!_4.equals(other._4)) return false;
            return true;
        }
        public String toString() { return "(" + _1 + "," + _2 + "," + _3 + "," + _4 + ")"; }
    }

    // ---------------------------------------------------
    // TUPLE 5.
    // ---------------------------------------------------
    @SuppressWarnings("unchecked")
    public static final class Tuple5<T1, T2, T3, T4, T5> extends Tuple {
        public T1 _1;
        public T2 _2;
        public T3 _3;
        public T4 _4;
        public T5 _5;
        // ---------------------------------------------------
        Tuple5() { this(null, null, null, null, null); }
        Tuple5(Tuple5<T1, T2, T3, T4, T5> t) { this(t._1, t._2, t._3, t._4, t._5); }
        Tuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
            this._1 = _1;
            this._2 = _2;
            this._3 = _3;
            this._4 = _4;
            this._5 = _5;
        }
        // ---------------------------------------------------
        public int length() { return 5; }
        public Iterator<Object> iterator() { return Arrays.asList(new Object[]{_1, _2, _3, _4, _5}).iterator(); }
        public <T> T field(int pos) {
            switch(pos) {
                case 0: return (T) this._1;
                case 1: return (T) this._2;
                case 2: return (T) this._3;
                case 3: return (T) this._4;
                case 4: return (T) this._5;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public <T> void field(int pos, T value) {
            switch(pos) {
                case 0: this._1 = (T1) value; break;
                case 1: this._2 = (T2) value; break;
                case 2: this._3 = (T3) value; break;
                case 3: this._4 = (T4) value; break;
                case 4: this._5 = (T5) value; break;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public int compareTo(Tuple t) {
            if (this == t) return 0;
            Tuple5<T1, T2, T3, T4, T5> o = (Tuple5<T1, T2, T3, T4, T5>)t;
            ComparisonChain cc = ComparisonChain.start();
            if (_1 instanceof Comparable) cc.compare((Comparable<?>) _1, (Comparable<?>) o._1);
            if (_2 instanceof Comparable) cc.compare((Comparable<?>) _2, (Comparable<?>) o._2);
            if (_3 instanceof Comparable) cc.compare((Comparable<?>) _3, (Comparable<?>) o._3);
            if (_4 instanceof Comparable) cc.compare((Comparable<?>) _4, (Comparable<?>) o._4);
            if (_5 instanceof Comparable) cc.compare((Comparable<?>) _5, (Comparable<?>) o._5);
            return cc.result();
        }
        public int hashCode() {
            int p = 31, r = 1;
            r = p * r + ((_1 == null) ? 0 : _1.hashCode());
            r = p * r + ((_2 == null) ? 0 : _2.hashCode());
            r = p * r + ((_3 == null) ? 0 : _3.hashCode());
            r = p * r + ((_4 == null) ? 0 : _4.hashCode());
            r = p * r + ((_5 == null) ? 0 : _5.hashCode());
            return r;
        }
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Tuple5<T1, T2, T3, T4, T5> other = (Tuple5<T1, T2, T3, T4, T5>) obj;
            if (_1 == null) { if (other._1 != null) return false; }
            else if (!_1.equals(other._1)) return false;
            if (_2 == null) { if (other._2 != null) return false; }
            else if (!_2.equals(other._2)) return false;
            if (_3 == null) { if (other._3 != null) return false; }
            else if (!_3.equals(other._3)) return false;
            if (_4 == null) { if (other._4 != null) return false; }
            else if (!_4.equals(other._4)) return false;
            if (_5 == null) { if (other._5 != null) return false; }
            else if (!_5.equals(other._5)) return false;
            return true;
        }
        public String toString() { return "(" + _1 + "," + _2 + "," + _3 + "," + _4 + "," + _5 + ")"; }
    }

    // ---------------------------------------------------
    // TUPLE 6.
    // ---------------------------------------------------
    @SuppressWarnings("unchecked")
    public static final class Tuple6<T1, T2, T3, T4, T5, T6> extends Tuple {
        public T1 _1;
        public T2 _2;
        public T3 _3;
        public T4 _4;
        public T5 _5;
        public T6 _6;
        // ---------------------------------------------------
        Tuple6() { this(null, null, null, null, null, null); }
        Tuple6(Tuple6<T1, T2, T3, T4, T5, T6> t) { this(t._1, t._2, t._3, t._4, t._5, t._6); }
        Tuple6(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
            this._1 = _1;
            this._2 = _2;
            this._3 = _3;
            this._4 = _4;
            this._5 = _5;
            this._6 = _6;
        }
        // ---------------------------------------------------
        public int length() { return 6; }
        public Iterator<Object> iterator() { return Arrays.asList(new Object[]{_1, _2, _3, _4, _5, _6}).iterator(); }
        public <T> T field(int pos) {
            switch(pos) {
                case 0: return (T) this._1;
                case 1: return (T) this._2;
                case 2: return (T) this._3;
                case 3: return (T) this._4;
                case 4: return (T) this._5;
                case 5: return (T) this._6;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public <T> void field(int pos, T value) {
            switch(pos) {
                case 0: this._1 = (T1) value; break;
                case 1: this._2 = (T2) value; break;
                case 2: this._3 = (T3) value; break;
                case 3: this._4 = (T4) value; break;
                case 4: this._5 = (T5) value; break;
                case 5: this._6 = (T6) value; break;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public int compareTo(Tuple t) {
            if (this == t) return 0;
            Tuple6<T1, T2, T3, T4, T5, T6> o = (Tuple6<T1, T2, T3, T4, T5, T6>)t;
            ComparisonChain cc = ComparisonChain.start();
            if (_1 instanceof Comparable) cc.compare((Comparable<?>) _1, (Comparable<?>) o._1);
            if (_2 instanceof Comparable) cc.compare((Comparable<?>) _2, (Comparable<?>) o._2);
            if (_3 instanceof Comparable) cc.compare((Comparable<?>) _3, (Comparable<?>) o._3);
            if (_4 instanceof Comparable) cc.compare((Comparable<?>) _4, (Comparable<?>) o._4);
            if (_5 instanceof Comparable) cc.compare((Comparable<?>) _5, (Comparable<?>) o._5);
            if (_6 instanceof Comparable) cc.compare((Comparable<?>) _6, (Comparable<?>) o._6);
            return cc.result();
        }
        public int hashCode() {
            int p = 31, r = 1;
            r = p * r + ((_1 == null) ? 0 : _1.hashCode());
            r = p * r + ((_2 == null) ? 0 : _2.hashCode());
            r = p * r + ((_3 == null) ? 0 : _3.hashCode());
            r = p * r + ((_4 == null) ? 0 : _4.hashCode());
            r = p * r + ((_5 == null) ? 0 : _5.hashCode());
            r = p * r + ((_6 == null) ? 0 : _6.hashCode());
            return r;
        }
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Tuple6<T1, T2, T3, T4, T5, T6> other = (Tuple6<T1, T2, T3, T4, T5, T6>) obj;
            if (_1 == null) { if (other._1 != null) return false; }
            else if (!_1.equals(other._1)) return false;
            if (_2 == null) { if (other._2 != null) return false; }
            else if (!_2.equals(other._2)) return false;
            if (_3 == null) { if (other._3 != null) return false; }
            else if (!_3.equals(other._3)) return false;
            if (_4 == null) { if (other._4 != null) return false; }
            else if (!_4.equals(other._4)) return false;
            if (_5 == null) { if (other._5 != null) return false; }
            else if (!_5.equals(other._5)) return false;
            if (_6 == null) { if (other._6 != null) return false; }
            else if (!_6.equals(other._6)) return false;
            return true;
        }
        public String toString() { return "(" + _1 + "," + _2 + "," + _3 + "," + _4 + "," + _5 + "," + _6 + ")"; }
    }

    // ---------------------------------------------------
    // TUPLE 7.
    // ---------------------------------------------------
    @SuppressWarnings("unchecked")
    public static final class Tuple7<T1, T2, T3, T4, T5, T6, T7> extends Tuple {
        public T1 _1;
        public T2 _2;
        public T3 _3;
        public T4 _4;
        public T5 _5;
        public T6 _6;
        public T7 _7;
        // ---------------------------------------------------
        Tuple7() { this(null, null, null, null, null, null, null); }
        Tuple7(Tuple7<T1, T2, T3, T4, T5, T6, T7> t) { this(t._1, t._2, t._3, t._4, t._5, t._6, t._7); }
        Tuple7(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
            this._1 = _1;
            this._2 = _2;
            this._3 = _3;
            this._4 = _4;
            this._5 = _5;
            this._6 = _6;
            this._7 = _7;
        }
        // ---------------------------------------------------
        public int length() { return 7; }
        public Iterator<Object> iterator() { return Arrays.asList(new Object[]{_1, _2, _3, _4, _5, _6, _7}).iterator(); }
        public <T> T field(int pos) {
            switch(pos) {
                case 0: return (T) this._1;
                case 1: return (T) this._2;
                case 2: return (T) this._3;
                case 3: return (T) this._4;
                case 4: return (T) this._5;
                case 5: return (T) this._6;
                case 6: return (T) this._7;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public <T> void field(int pos, T value) {
            switch(pos) {
                case 0: this._1 = (T1) value; break;
                case 1: this._2 = (T2) value; break;
                case 2: this._3 = (T3) value; break;
                case 3: this._4 = (T4) value; break;
                case 4: this._5 = (T5) value; break;
                case 5: this._6 = (T6) value; break;
                case 6: this._7 = (T7) value; break;
                default: throw new IndexOutOfBoundsException(String.valueOf(pos));
            }
        }
        public int compareTo(Tuple t) {
            if (this == t) return 0;
            Tuple7<T1, T2, T3, T4, T5, T6, T7> o = (Tuple7<T1, T2, T3, T4, T5, T6, T7>)t;
            ComparisonChain cc = ComparisonChain.start();
            if (_1 instanceof Comparable) cc.compare((Comparable<?>) _1, (Comparable<?>) o._1);
            if (_2 instanceof Comparable) cc.compare((Comparable<?>) _2, (Comparable<?>) o._2);
            if (_3 instanceof Comparable) cc.compare((Comparable<?>) _3, (Comparable<?>) o._3);
            if (_4 instanceof Comparable) cc.compare((Comparable<?>) _4, (Comparable<?>) o._4);
            if (_5 instanceof Comparable) cc.compare((Comparable<?>) _5, (Comparable<?>) o._5);
            if (_6 instanceof Comparable) cc.compare((Comparable<?>) _6, (Comparable<?>) o._6);
            if (_7 instanceof Comparable) cc.compare((Comparable<?>) _7, (Comparable<?>) o._7);
            return cc.result();
        }
        public int hashCode() {
            int p = 31, r = 1;
            r = p * r + ((_1 == null) ? 0 : _1.hashCode());
            r = p * r + ((_2 == null) ? 0 : _2.hashCode());
            r = p * r + ((_3 == null) ? 0 : _3.hashCode());
            r = p * r + ((_4 == null) ? 0 : _4.hashCode());
            r = p * r + ((_5 == null) ? 0 : _5.hashCode());
            r = p * r + ((_6 == null) ? 0 : _6.hashCode());
            r = p * r + ((_7 == null) ? 0 : _7.hashCode());

            return r;
        }
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Tuple7<T1, T2, T3, T4, T5, T6, T7> other = (Tuple7<T1, T2, T3, T4, T5, T6, T7>) obj;
            if (_1 == null) { if (other._1 != null) return false; }
            else if (!_1.equals(other._1)) return false;
            if (_2 == null) { if (other._2 != null) return false; }
            else if (!_2.equals(other._2)) return false;
            if (_3 == null) { if (other._3 != null) return false; }
            else if (!_3.equals(other._3)) return false;
            if (_4 == null) { if (other._4 != null) return false; }
            else if (!_4.equals(other._4)) return false;
            if (_5 == null) { if (other._5 != null) return false; }
            else if (!_5.equals(other._5)) return false;
            if (_6 == null) { if (other._6 != null) return false; }
            else if (!_6.equals(other._6)) return false;
            if (_7 == null) { if (other._7 != null) return false; }
            else if (!_7.equals(other._7)) return false;
            return true;
        }
        public String toString() { return "(" + _1 + "," + _2 + "," + _3 + "," + _4 + "," + _5 + "," + _6 + "," + _7 + ")"; }
    }
}

