package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

import java.time.LocalDateTime;
import java.util.List;

public class Task implements ITask {

    //region Attributes
    private int id;
    private IUser user;
    private String title;
    private String note;
    private boolean isDone;
    private ICategory category;
    private List<ITag> tags;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //endregion

    //region Getters

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public ICategory getCategory() {
        return category;
    }

    @Override
    public LocalDateTime getDueDate() {
        return dueDate;
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
    public ITag[] getTags() {
        return new ITag[0];
    }

    @Override
    public ITag getTag(int i) {
        return null;
    }

    //endregion

    @Override
    public void saveTag(int i, ITag tag) {

    }

    @Override
    public void addTag(ITag tag) {

    }

    @Override
    public int tagsCount() {
        return 0;
    }

    @Override
    public void complete() {

    }

    @Override
    public void reopen() {

    }
}
