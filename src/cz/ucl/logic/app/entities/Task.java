package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

import java.util.Date;

public class Task implements ITask {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getNote() {
        return null;
    }

    @Override
    public IUser getUser() {
        return null;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public ICategory getCategory() {
        return null;
    }

    @Override
    public Date getCreatedAt() {
        return null;
    }

    @Override
    public Date getUpdatedAt() {
        return null;
    }

    @Override
    public ITag[] getTags() {
        return new ITag[0];
    }

    @Override
    public ITag getTag(int i) {
        return null;
    }

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
