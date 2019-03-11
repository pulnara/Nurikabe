package pl.edu.agh.iisg.to.nurikabe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
public class Score {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();

    public Score() {}

    Score(String name, String time) {
        setTime(time);
        setName(name);
    }

    public Score(int id, String name, String time) {
        setId(id);
        setTime(time);
        setName(name);
    }

    @Id
    @GeneratedValue
    public int getId() {
        return this.id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getTime() {
        return time.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setName(String name) {
        this.name.setValue(name);
    }
}
