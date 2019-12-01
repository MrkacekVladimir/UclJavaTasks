package cz.ucl.logic.data.mappers;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.data.dao.TaskDAO;
import cz.ucl.logic.data.mappers.definition.ITaskMapper;

import java.util.ArrayList;
import java.util.List;

public class TaskMapper implements ITaskMapper {
    MapperFactory factory;

    public TaskMapper(MapperFactory factory) {
        this.factory = factory;
    }

    // TODO
}