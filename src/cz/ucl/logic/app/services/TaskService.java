package cz.ucl.logic.app.services;

import cz.ucl.logic.app.entities.Task;
import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.services.definition.ITaskService;
import cz.ucl.logic.app.services.definition.IUserService;
import cz.ucl.logic.app.services.definition.TasksOrder;
import cz.ucl.logic.data.managers.definition.ITaskManager;

public class TaskService implements ITaskService {
    private IUserService userService;
    private ITaskManager manager;

    public TaskService(IUserService userService, ITaskManager manager) {
        this.userService = userService;
        this.manager = manager;
    }

    public ITask[] getAllTasks() {
        return manager.getAllTasksForUser(userService.getUserLoggedIn());
    }

    @Override
    public ITask[] getAllTasks(TasksOrder order) {

        return getAllTasks();
    }

    @Override
    public ITask[] searchTasksForKeyword(String keyword) {

        return this.getAllTasks();
    }

    @Override
    public ITask[] getAllTasksByCategory(ICategory category) {

        return this.getAllTasks();
    }

    @Override
    public ITask[] getAllTasksByTag(ITag tag) {

        return this.getAllTasks();
    }

    @Override
    public ITask[] getAllTasksByTags(ITag[] tag) {

        return this.getAllTasks();
    }

    @Override
    public ITask[] getAllTasksByTags(ITag[] tag, ICategory category) {

        return this.getAllTasks();
    }

    @Override
    public ITask getTaskById(int id) {
        return manager.getTaskByIdForUser(id, userService.getUserLoggedIn());
    }

    @Override
    public void createTask(String title) {
        manager.createTask(new Task(userService.getUserLoggedIn(), title));
    }

    @Override
    public void createTask(String title, String note) {
        manager.createTask(new Task(userService.getUserLoggedIn(), title, note));
    }

    @Override
    public void createTask(String title, String note, ICategory category) {
        manager.createTask(new Task(userService.getUserLoggedIn(), title, note, category));
    }

    @Override
    public void updateTask(int id, String title, Color color) {
        manager.updateTask(new Task(id, userService.getUserLoggedIn(), title));
    }

    @Override
    public void destroyTask(int id) {
        manager.deleteTaskByIdForUser(id, userService.getUserLoggedIn());
    }
}
