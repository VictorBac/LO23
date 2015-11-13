package fr.utc.lo23.client.network.main;

import java.io.Serializable;

public class TestSerialize implements Serializable {

    private static final long serialVersionUID = 5950169519310163575L;
    private int id;
    private String name;

    public TestSerialize(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int hashCode() {
        return id;
    }

    public String toString() {
        return "Id = " + getId() + " ; Name = " + getName();
    }
}
