package cz.ucl.logic.data.mappers;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.data.dao.TaskDAO;
import cz.ucl.logic.data.mappers.definition.ITaskMapper;

import java.util.List;

public class TaskMapper implements ITaskMapper {
    private MapperFactory factory;

    public TaskMapper(MapperFactory factory){
        this.factory = factory;
    }

    @Override
    public ITask mapFromDao(TaskDAO dao) {
        return null;
    }

    @Override
    public List<ITask> mapFromDaoList(List<TaskDAO> daoList) {
        return null;
    }

    @Override
    public TaskDAO mapToDao(ITask entity) {
        return null;
    }

    @Override
    public List<TaskDAO> mapToDaoList(List<ITask> entityList) {
        return null;
    }
}
