package cz.ucl.logic.data.managers;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.data.dao.TaskDAO;
import cz.ucl.logic.data.managers.definition.ITaskManager;
import cz.ucl.logic.data.mappers.MapperFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager implements ITaskManager {
    /** Keys in the map will be emails of user who owns the task */
    private Map<String, List<TaskDAO>> taskDatabase;
    private MapperFactory mappers;
    private ManagerFactory managers;

    public TaskManager(ManagerFactory managers, MapperFactory mappers) {
        this.taskDatabase = new HashMap<>();
        this.mappers = mappers;
        this.managers = managers;
    }

    @Override
    public ITask[] getAllTasksForUser(IUser user) {
        List<TaskDAO> userTasks = getDAOsForUserLoggedIn(user);

        ITask[] loadedTasks = new ITask[userTasks.size()];

        for (int i = 0; i < userTasks.size(); i++) {
            TaskDAO dao = userTasks.get(i);

            loadedTasks[i] = mappers.getTaskMapper().mapFromDAODeep(dao);
        }

        return loadedTasks;
    }

    @Override
    public ITask getTaskByIdForUser(int taskId, IUser user) {
        List<TaskDAO> userTasks = getDAOsForUserLoggedIn(user);

        for (TaskDAO categoryDao : userTasks) {
            if (categoryDao.getId() == taskId) {
                return mappers.getTaskMapper().mapFromDAODeep(categoryDao);
            }
        }

        return null;
    }

    @Override
    public void createTask(ITask task) {
        List<TaskDAO> userTasks = getDAOsForUserLoggedIn(task.getUser());

        TaskDAO dao = mappers.getTaskMapper().mapToDAODeep(task);

        userTasks.add(dao);
    }

    @Override
    public void updateTask(ITask task) {
        List<TaskDAO> userTasks = getDAOsForUserLoggedIn(task.getUser());

        int daoIndex = -1;
        for(int i = 0; i < userTasks.size(); i++) {
            if (userTasks.get(i).getId() == task.getId()) {
                daoIndex = i;
                break;
            }
        }

        TaskDAO newDao = mappers.getTaskMapper().mapToDAODeep(task);

        userTasks.set(daoIndex, newDao);
    }

    @Override
    public void deleteTaskByIdForUser(int taskId, IUser user) {
        List<TaskDAO> userTasks = getDAOsForUserLoggedIn(user);

        int foundCategoryIndex = -1;
        for (TaskDAO dao : userTasks) {
            if (dao.getId() == taskId) {
                foundCategoryIndex = userTasks.indexOf(dao);
            }
        }

        userTasks.remove(foundCategoryIndex);
    }

    private List<TaskDAO> getDAOsForUserLoggedIn(IUser user) {
        List<TaskDAO> userTasks = taskDatabase.get(user.getEmail());

        // We have to create a new empty ArrayList in the map in case it does not exists yet
        if (userTasks == null) {
            userTasks = new ArrayList<>();

            taskDatabase.put(user.getEmail(), userTasks);
        }

        return userTasks;
    }
}
