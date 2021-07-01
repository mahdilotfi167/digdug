package ir.ac.kntu.data;

import java.io.Serializable;

public class Person implements Serializable, Comparable<Person> {
    public final static long serialVersionUID = 10;
    private String name;
    private int hiScore;

    public Person(String name, int hiScore) {
        this.name = name;
        this.hiScore = hiScore;
    }

    public int getHiScore() {
        return hiScore;
    }

    public String getName() {
        return name;
    }

    public void setHiScore(int hiScore) {
        this.hiScore = hiScore;
    }

    @Override
    public int compareTo(Person o) {
        return this.hiScore-o.hiScore;
    }
}