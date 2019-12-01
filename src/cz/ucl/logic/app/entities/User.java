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

    public User(String email, String username, String securePassword) {
        this.email = email;
        this.username = username;
        this.password = securePassword;
    }

    @Override
    public int getId() {
        return this.id;
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
        return this.categories.toArray(new ICategory[0]);
    }

    @Override
    public ICategory getCategory(int i) {
        return this.categories.get(i);
    }

    @Override
    public void saveCategory(int i, ICategory category) {
        this.categories.set(i, category);
    }

    @Override
    public void addCategory(ICategory category) {
        this.categories.add(category);
    }

    @Override
    public int categoriesCount() {
        return this.categories.size();
    }

    @Override
    public ITag[] getTags() {
        return this.tags.toArray(new ITag[0]);
    }

    @Override
    public ITag getTag(int i) {
        return this.tags.get(i);
    }

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
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public ITask[] getTasks() {
        return this.tasks.toArray(new ITask[0]);
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
