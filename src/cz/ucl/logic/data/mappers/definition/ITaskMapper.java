package cz.ucl.logic.data.mappers.definition;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.data.dao.TaskDAO;

import java.util.List;

public interface ITaskMapper {
    ITask mapFromDAOShallow(TaskDAO dao);
    TaskDAO mapToDAOShallow(ITask entity);
    ITask mapFromDAODeep(TaskDAO dao);
    TaskDAO mapToDAODeep(ITask entity);

    List<ITask> mapFromDAOsShallow(List<TaskDAO> daos);
    List<TaskDAO> mapToDAOsShallow(List<ITask> entities);
}
