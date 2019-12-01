package cz.ucl.logic.app.entities;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;

import java.time.LocalDateTime;
import java.util.List;


public class User implements IUser {

    //region Attributes
    private int id;
    private String email;
    private String username;
    private String password;
    private List<ICategory> categories;
    private List<ITask> tasks;
    private List<ITag> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //endregion

    public User(int id, String email, String username, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
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
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
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
