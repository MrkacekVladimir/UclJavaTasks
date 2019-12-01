package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Category(IUser user, int id, String title, Color color, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.id = id;
        this.title = title;
        this.color = color;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.tasks = new ArrayList<>();
    }

    public Category(IUser user, String title) {
        this(user, 0, title, Color.BLACK, LocalDateTime.now(),LocalDateTime.now());
    }

    public Category(IUser user, String title, Color color) {
        this(user, 0, title, color, LocalDateTime.now(),LocalDateTime.now());
    }
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
