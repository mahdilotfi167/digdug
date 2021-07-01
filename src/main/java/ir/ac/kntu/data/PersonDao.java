package ir.ac.kntu.data;

import java.util.ArrayList;
import java.util.List;

public interface PersonDao {
    public void add(Person person);
    public List<Person> all();
    public void update(Person person);
}
