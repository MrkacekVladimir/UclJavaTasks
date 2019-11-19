package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

import java.time.LocalDateTime;

public class Category implements ICategory {
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
