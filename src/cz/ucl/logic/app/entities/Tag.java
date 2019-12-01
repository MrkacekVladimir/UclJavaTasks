package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

import java.time.LocalDateTime;
import java.util.List;

public class Tag implements ITag {

    //region Attributes
    private int id;
    private String title;
    private Color color;
    private IUser user;
    private List<Tag> tags;
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
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public IUser getUser() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return null;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return null;
    }

    @Override
    public ITask[] getTasks() {
        return new ITask[0];
    }

    @Override
    public ITask getTask(int i) {
        return null;
    }

    @Override
    public void saveTask(int i, ITask task) {

    }

    @Override
    public void addTask(ITask task) {

    }

    @Override
    public int tasksCount() {
        return 0;
    }
}
