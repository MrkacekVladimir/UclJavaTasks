package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

import java.time.LocalDateTime;
import java.util.List;

public class Category implements ICategory {

    //region Attributes
    private int id;
    private IUser user;
    private String title;
    private Color color;
    private List<ITask> tasks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //endregion

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public ITask[] getTasks() {
        return tasks.toArray(new ITask[0]);
    }

    @Override
    public ITask getTask(int i) {
        return this.tasks.get(i);
    }

    @Override
    public void saveTask(int i, ITask task) {
        this.tasks.set(i, task);
    }

    @Override
    public void addTask(ITask task) {
        this.tasks.add(task);
    }

    @Override
    public int tasksCount() {
        return this.tasks.size();
    }
}
