package cz.ucl.logic.data.mappers.definition;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.data.dao.TaskDAO;

import java.util.List;

public interface ITaskMapper {
    ITask mapFromDao(TaskDAO dao);

    ITask mapFromDao(TaskDAO dao, boolean preventDeepMap);

    List<ITask> mapFromDaoList(List<TaskDAO> daoList);

    List<ITask> mapFromDaoList(List<TaskDAO> daoList, boolean preventDeepMap);

    TaskDAO mapToDao(ITask entity);

    TaskDAO mapToDao(ITask entity, boolean preventDeepMap);

    List<TaskDAO> mapToDaoList(List<ITask> entityList);

    List<TaskDAO> mapToDaoList(List<ITask> entityList, boolean preventDeepMap);
}
