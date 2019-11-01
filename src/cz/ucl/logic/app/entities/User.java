package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

public class User implements IUser {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public ICategory[] getCategories() {
        return new ICategory[0];
    }

    @Override
    public ICategory getCategory(int i) {
        return null;
    }

    @Override
    public void saveCategory(int i, ICategory category) {

    }

    @Override
    public void addCategory(ICategory category) {

    }

    @Override
    public int categoriesCount() {
        return 0;
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
