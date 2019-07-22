package br.com.digitoglobal.projeto.util.optionalList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class OptionalList<T extends OptionalModelable> extends ArrayList<T> {

    public OptionalList() {}

    public OptionalList(Collection<T> collection) {
        super(collection);
    }

    public void selectAll() {
        this.parallelStream().forEach(item -> item.select());
    }

    public void unSelectAll() {
        this.parallelStream().forEach(item -> item.unSelect());
    }

    public void removeAllTemporarilyAdded() {
        Iterator<T> temporarilyAddedItens = this.parallelStream().filter(item -> item.isTemporarilyAdded()).iterator();
        while (temporarilyAddedItens.hasNext()) {
            temporarilyAddedItens.remove();
        }
    }

    public void removeAllTemporarilyRemoved() {
        Iterator<T> temporarilyAddedItens = this.parallelStream().filter(item -> item.isTemporarilyRemoved()).iterator();
        while (temporarilyAddedItens.hasNext()) {
            temporarilyAddedItens.remove();
        }
    }

    public OptionalList<T> getAllNotTemporarilyAdded() {
        return this.parallelStream().filter(item -> !item.isTemporarilyAdded()).collect(Collector.of((Supplier<OptionalList<T>>) OptionalList::new, OptionalList::add,
                                                                                (left, right) -> { left.addAll(right); return left; }));
    }

    public OptionalList<T> getAllNotTemporarilyRemoved() {
        return this.parallelStream().filter(item -> !item.isTemporarilyRemoved()).collect(Collector.of((Supplier<OptionalList<T>>) OptionalList::new, OptionalList::add,
                                                                                    (left, right) -> { left.addAll(right); return left; }));
    }

    public void resetAllOptionalItens() {
        this.parallelStream().forEach(i -> i.reset());
    }

    public boolean addTemporarily(T t) {
        t.setTemporarilyAdded(true);
        return this.add(t);
    }

    public boolean addAllTemporarily(List<T> itens) {
        if (itens == null) return false;
        itens.parallelStream().forEach(i -> addTemporarily(i));
        return true;
    }

    public OptionalList<T> getAllTemporarilyAdded() {
        return this.parallelStream().filter(i -> i.isTemporarilyAdded()).collect(Collector.of((Supplier<OptionalList<T>>) OptionalList::new, OptionalList::add,
                                                                                    (left, right) -> { left.addAll(right); return left; }));
    }

    public OptionalList<T> getAllTemporarilyRemoved() {
        return this.parallelStream().filter(i -> i.isTemporarilyRemoved()).collect(Collector.of((Supplier<OptionalList<T>>) OptionalList::new, OptionalList::add,
                                                                            (left, right) -> { left.addAll(right); return left; }));
    }

    public void setAll(OptionalModel optionalModel) {
        this.parallelStream().forEach(i -> i.setOptionalModel(optionalModel));
    }

    public void setAllTemporarilyAdded() {
        this.parallelStream().forEach(i -> i.addedTemporarily());
    }

    public void setAllTemporarilyAdded(boolean value) {
        this.parallelStream().forEach(i -> i.setTemporarilyAdded(value));
    }

    public void setAllTemporarilyRemoved() {
        this.parallelStream().forEach(i -> i.removeTemporarily());
    }

    public void setAllTemporarilyRemoved(boolean value) {
        this.parallelStream().forEach(i -> i.setTemporarilyRemoved(value));
    }

    public boolean removeTemporarily(T t) {
        if (t == null) return false;
        int index = this.indexOf(t);
        if (index != -1) {
            this.get(index).removeTemporarily();
            return true;
        } return false;
    }

}
