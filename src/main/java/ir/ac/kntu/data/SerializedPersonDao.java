package ir.ac.kntu.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SerializedPersonDao implements PersonDao {
    private String foldername;

    public SerializedPersonDao(String foldername) {
        this.foldername = foldername;
    }

    @Override
    public void add(Person person) {
        File file = new File(foldername + "/" + person.getName() + ".person");
        file.getParentFile().mkdirs();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> all() {
        File folder = new File(foldername);
        if (folder == null || folder.listFiles() == null || folder.listFiles().length == 0) {
            return new ArrayList<>();
        }
        return List.of(folder.listFiles()).stream().map(file -> {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                return (Person) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public void update(Person person) {
        File file = new File(foldername + "/" + person.getName() + ".person");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
