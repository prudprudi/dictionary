package com.prudprudi4.dictionary;

import javax.swing.*;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedListModel<T> extends AbstractListModel<T> implements Iterable<T> {
    private SortedSet<T> set = new TreeSet<>();

    public int getSize() {
        return set.size();
    }

    public boolean addElement(T t) {
        boolean isAdd = set.add(t);
        if(isAdd) {
            fireContentsChanged(this, 0, getSize());
        }
        return isAdd;
    }

    public Iterator<T> iterator() {
        return set.iterator();
    }

    public void clear() {
        set.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean removeElementAt(T t) {
        boolean isRemove = set.remove(t);
        if(isRemove) {
            fireContentsChanged(this, 0, getSize());
        }
        return isRemove;
    }

    public T getElementAt(int index) {
        return (T)set.toArray()[index];
    }

    public int getIndexByElement(T t) {
        return (set.contains(t)) ? set.headSet(t).size() : -1;
    }
}
