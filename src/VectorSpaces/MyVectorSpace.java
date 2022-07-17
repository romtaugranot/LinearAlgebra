package VectorSpaces;

import ComplexMath.FieldScalars.Scalar;

import java.util.*;

public class MyVectorSpace implements VectorSpace{

    private final Set<Vector> base;

    public MyVectorSpace(){
        this.base = new HashSet<>();
    }

    @Override
    public boolean add(Vector vector) {
        if (contains(vector)) return false;
        return base.add(vector);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Vector> c) {
        return c.stream().allMatch(this::add);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        base.clear();
    }

    @Override
    public int size() {
        return base.size();
    }

    @Override
    public boolean isEmpty() {
        return base.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (base.contains(o)) return true;
        if (!(o instanceof Vector)) return false;
        Vector v = (Vector) o;
        for (Vector v2 : base){
            if (divide(v,v2)) return true;
        }
        return false;
    }

    /**
     * @pre: v1.getSize() == v2.getSize()
     * @param v1
     * @param v2
     * @return $ret == true implies for all 0 <= i,j < v1.getSize(),
     *      v1.getEntries().get(i).divide(v2.getEntries().get(i)).equals(v1.getEntries().get(j).divide(v2.getEntries().get(j))
     */
    private boolean divide(Vector v1, Vector v2) {
        List<Scalar> entries1 = v1.getEntries();
        List<Scalar> entries2 = v2.getEntries();
        Scalar s = null;
        int size = v1.getSize();
        int i = 0;
        do {
            if (entries1.get(i).equals(Scalar.getZero())){
                if (!entries2.get(i).equal(Scalar.getZero())) return false;
            }else {
                if (entries2.get(i).equals(Scalar.getZero())) return false;
                else{
                    if (i == 0){
                        s = entries1.get(i).div(entries2.get(i));
                    } else {
                        if (!entries1.get(i).div(entries2.get(i)).equal(s)) return false;
                    }
                }
            }
            i++;
        } while (i < size);
        return true;
    }

    @Override
    public Iterator<Vector> iterator() {
        return base.iterator();
    }

    @Override
    public Object[] toArray() {
        return base.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return base.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(MyVectorSpace.this::contains);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof MyVectorSpace other)) return false;
        return other.base.equals(this.base);
    }


    @Override
    public Set<Vector> getBase() {
        return Set.copyOf(base);
    }
}
