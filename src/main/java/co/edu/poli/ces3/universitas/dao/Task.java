package co.edu.poli.ces3.universitas.dao;

public class Task {
    private int id;
    private String nameTask;

    public Task(int id, String nameTask) {
        this.id = id;
        this.nameTask = nameTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }
}
