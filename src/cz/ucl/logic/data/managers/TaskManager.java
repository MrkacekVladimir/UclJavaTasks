package cz.ucl.logic.data.managers;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.data.dao.TaskDAO;
import cz.ucl.logic.data.managers.definition.ITaskManager;
import cz.ucl.logic.data.mappers.MapperFactory;

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

    // TODO
}
