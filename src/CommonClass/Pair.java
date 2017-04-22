package CommonClass;

import java.io.Serializable;

/**
 * CommonClass
 * Created by blaisewang on 2016/7/7.
 */

public class Pair<A extends Comparable<A>, B> implements Comparable<Pair<A, B>>, Serializable {
    private static final long serialVersionUID = -8914647164831651005L;

    public A first;
    public B second;

    public Pair(A a, B b) {
        first = a;
        second = b;
    }

    public String toString() {
        if (first == null || second == null)
            return "(null, null)";
        return "(" + first.toString() + ", " + second.toString() + ")";
    }

    @Override
    public int compareTo(Pair<A, B> o) {
        return first.compareTo(o.first);
    }
}
