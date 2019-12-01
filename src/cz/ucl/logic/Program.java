package cz.ucl.logic;

import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.app.services.CategoryService;
import cz.ucl.logic.app.services.TagService;
import cz.ucl.logic.app.services.TaskService;
import cz.ucl.logic.app.services.UserService;
import cz.ucl.logic.app.services.definition.*;
import cz.ucl.logic.data.managers.ManagerFactory;
import cz.ucl.logic.data.mappers.MapperFactory;
import cz.ucl.logic.exceptions.AlreadyLoggedInException;
import cz.ucl.logic.exceptions.EmailAddressAlreadyUsedException;
import cz.ucl.logic.exceptions.InvalidCredentialsException;
import cz.ucl.logic.exceptions.NotLoggedInException;

/**
 * This class HAS to honor the Facade design pattern!
 * <p>
 * No direct functionality should be present!
 * All functionality should be delegated to service classes
 * <p>
 * All xxxService attributes have to be private!
 */
public class Program implements IAppLogic {
    private MapperFactory mapperFactory;
    private ManagerFactory managerFactory;

    private ICategoryService categoryService;
    private ITagService tagService;
    private ITaskService taskService;
    private IUserService userService;

    public Program() {
        mapperFactory = new MapperFactory();
        managerFactory = new ManagerFactory(mapperFactory);

        userService = new UserService(managerFactory.getUserManager());
        categoryService = new CategoryService(userService, managerFactory.getCategoryManager());
        tagService = new TagService(userService, managerFactory.getTagManager());
        taskService = new TaskService(userService, managerFactory.getTaskManager());
    }

    @Override
    public void generateMockData() {
        try {
            this.userService.registerUser("test", "test", "test");
        } catch (Exception e) {

        }
    }

    //region ICategoryService

    @Override
    public ICategory[] getAllCategories() {
        return this.categoryService.getAllCategories();
    }

    @Override
    public ICategory getCategoryById(int id) {
        return this.categoryService.getCategoryById(id);
    }

    @Override
    public void createCategory(String title) {
        this.categoryService.createCategory(title);
    }

    @Override
    public void createCategory(String title, Color color) {
        this.categoryService.createCategory(title, color);
    }

    @Override
    public void updateCategory(int id, String title, Color color) {
        this.categoryService.updateCategory(id, title, color);
    }

    @Override
    public void destroyCategory(int id) {
        this.categoryService.destroyCategory(id);
    }

    //endregion

    //region ITagService

    @Override
    public ITag[] getAllTags() {
        return this.tagService.getAllTags();
    }

    @Override
    public ITag getTagById(int id) {
        return this.tagService.getTagById(id);
    }

    @Override
    public void createTag(String title) {
        this.tagService.createTag(title);
    }

    @Override
    public void createTag(String title, Color color) {
        this.tagService.createTag(title, color);
    }

    @Override
    public void updateTag(int id, String title, Color color) {
        this.tagService.updateTag(id, title, color);
    }

    @Override
    public void destroyTag(int id) {
        this.tagService.destroyTag(id);
    }

    //endregion

    //region ITaskService

    @Override
    public ITask[] getAllTasks() {
        return this.taskService.getAllTasks();
    }

    @Override
    public ITask[] getAllTasks(TasksOrder order) {
        return this.taskService.getAllTasks(order);
    }

    @Override
    public ITask[] searchTasksForKeyword(String keyword) {
        return this.taskService.searchTasksForKeyword(keyword);
    }

    @Override
    public ITask[] getAllTasksByCategory(ICategory category) {
        return this.taskService.getAllTasksByCategory(category);
    }

    @Override
    public ITask[] getAllTasksByTag(ITag tag) {
        return this.taskService.getAllTasksByTag(tag);
    }

    @Override
    public ITask[] getAllTasksByTags(ITag[] tag) {
        return this.taskService.getAllTasksByTags(tag);
    }

    @Override
    public ITask[] getAllTasksByTags(ITag[] tag, ICategory category) {
        return this.taskService.getAllTasksByTags(tag, category);
    }

    @Override
    public ITask getTaskById(int id) {
        return this.taskService.getTaskById(id);
    }

    @Override
    public void createTask(String title) {
        this.taskService.createTask(title);
    }

    @Override
    public void createTask(String title, String note) {
        this.taskService.createTask(title, note);
    }

    @Override
    public void createTask(String title, String note, ICategory category) {
        this.taskService.createTask(title, note, category);
    }

    @Override
    public void updateTask(int id, String title, Color color) {
        this.taskService.updateTask(id, title, color);
    }

    @Override
    public void destroyTask(int id) {
        this.taskService.destroyTask(id);
    }

    //endregion

    //region IUserService

    @Override
    public void loginUser(String email, String password) throws AlreadyLoggedInException, InvalidCredentialsException {
        this.userService.loginUser(email, password);
    }

    @Override
    public void logoutUser() throws NotLoggedInException {
        this.userService.logoutUser();
    }

    @Override
    public void registerUser(String email, String username, String password) throws EmailAddressAlreadyUsedException {
        this.userService.registerUser(email, username, password);
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.userService.isUserLoggedIn();
    }

    @Override
    public IUser getUserLoggedIn() {
        return this.userService.getUserLoggedIn();
    }

    @Override
    public void destroyUserLoggedIn() throws NotLoggedInException {
        this.userService.destroyUserLoggedIn();
    }

    //endregion
}
