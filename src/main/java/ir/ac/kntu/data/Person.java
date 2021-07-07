package ir.ac.kntu.data;

import java.io.Serializable;

public class Person implements Serializable, Comparable<Person> {
    public final static long serialVersionUID = 11;
    private String name;
    private int hiScore;
    private int totalGames;

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
        return o.hiScore - this.hiScore;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }
}