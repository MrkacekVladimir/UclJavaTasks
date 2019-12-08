package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tag implements ITag {

    //region Attributes
    private int id;
    private String title;
    private Color color;
    private IUser user;
    private List<ITask> tasks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //endregion


    public Tag(IUser user, int id, String title, Color color, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.id = id;
        this.title = title;
        this.color = color;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.tasks = new ArrayList<>();
    }

    public Tag(int id, IUser user, String title) {
        this(user, id, title, Color.BLACK, LocalDateTime.now(), LocalDateTime.now());
    }

    public Tag(int id, IUser user, String title, Color color) {
        this(user, id, title, color, LocalDateTime.now(), LocalDateTime.now());
    }

    public Tag(IUser user, String title) {
        this(user, 0, title, Color.BLACK, LocalDateTime.now(), LocalDateTime.now());
    }

    public Tag(IUser user, String title, Color color) {
        this(user, 0, title, color, LocalDateTime.now(), LocalDateTime.now());
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public IUser getUser() {
        return this.user;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public ITask[] getTasks() {
        ITask[] taskArray = new ITask[this.tasks.size()];

        return this.tasks.toArray(taskArray);
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
