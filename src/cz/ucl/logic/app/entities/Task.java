package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;
import jdk.vm.ci.meta.Local;

import java.time.LocalDate;
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

    public Task(IUser user, int id,  String title, String note, boolean isDone, ICategory category, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.id = id;
        this.title = title;
        this.note = note;
        this.isDone = isDone;
        this.category = category;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Task(IUser user, String title) {
        this(user, 0, title, "", false, null, LocalDateTime.now(),LocalDateTime.now(), LocalDateTime.now() );
    }

    public Task(IUser user, String title, String note) {
        this(user, 0, title, note, false, null, LocalDateTime.now(),LocalDateTime.now(), LocalDateTime.now() );
    }

    public Task(IUser user, String title, String note, ICategory category) {
        this(user, 0, title, "", false, category, LocalDateTime.now(),LocalDateTime.now(), LocalDateTime.now() );
    }


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
        return this.tags.toArray(new ITag[0]);
    }

    @Override
    public ITag getTag(int i) {
        return this.tags.get(i);
    }

    //endregion

    @Override
    public void saveTag(int i, ITag tag) {
        this.tags.set(i, tag);
    }

    @Override
    public void addTag(ITag tag) {
        this.tags.add(tag);
    }

    @Override
    public int tagsCount() {
        return this.tags.size();
    }

    @Override
    public void complete() {
        this.isDone = true;
    }

    @Override
    public void reopen() {
    }
}
