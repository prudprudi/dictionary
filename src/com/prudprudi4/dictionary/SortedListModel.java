package com.prudprudi4.dictionary;

import javax.swing.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedListModel extends AbstractListModel<String> implements Iterable<String> {
    private SortedSet<String> set = new TreeSet<>();

    public int getSize() {
        return set.size();
    }

    public boolean addElement(String string) {
        boolean isAdd = set.add(string);
        if(isAdd) {
            fireContentsChanged(this, 0, getSize());
        }
        return isAdd;
    }

    public Iterator<String> iterator() {
        return set.iterator();
    }

    public void clear() {
        set.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean removeElementAt(String string) {
        boolean isRemove = set.remove(string);
        if(isRemove) {
            fireContentsChanged(this, 0, getSize());
        }
        return isRemove;
    }

    public boolean removeElementAtIndex(int index) {
        String string = getElementAt(index);
        return removeElementAt(string);
    }

    public int getIndexBySubstring(String string) {
        Iterator<String> it = set.iterator();
        while(it.hasNext()) {
            String s = it.next();
            if (s.startsWith(string)) {
                return getIndexByElement(s);
            }
        }
        return -1;
    }

    public String getElementAt(int index) {
        return (String)set.toArray()[index];
    }

    public int getIndexByElement(String string) {
        return (set.contains(string)) ? set.headSet(string).size() : -1;
    }
}
