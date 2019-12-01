package cz.ucl.logic.data.mappers;

import cz.ucl.logic.app.entities.Task;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.data.dao.CategoryDAO;
import cz.ucl.logic.data.dao.TagDAO;
import cz.ucl.logic.data.dao.TaskDAO;
import cz.ucl.logic.data.dao.UserDAO;
import cz.ucl.logic.data.mappers.definition.ITaskMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskMapper implements ITaskMapper {
    private MapperFactory factory;

    public TaskMapper(MapperFactory factory) {
        this.factory = factory;
    }

    @Override
    public ITask mapFromDAOShallow(TaskDAO dao) {
        IUser userEntity = factory.getUserMapper().mapFromDAOShallow(dao.getUser());
        ICategory categoryEntity = factory.getCategoryMapper().mapFromDAOShallow(dao.getCategory());

        ITask taskEntity = new Task(userEntity, dao.getId(), dao.getTitle(), dao.getNote(), dao.getIsDone(), categoryEntity, dao.getDueDate(), dao.getCreatedAt(), dao.getUpdatedAt());

        return taskEntity;
    }

    @Override
    public TaskDAO mapToDAOShallow(ITask entity) {
        UserDAO userDao = factory.getUserMapper().mapToDAOShallow(entity.getUser());
        CategoryDAO categoryDAO = factory.getCategoryMapper().mapToDAOShallow(entity.getCategory());

        TaskDAO taskDao = new TaskDAO();

        taskDao.setUser(userDao);
        taskDao.setCategory(categoryDAO);
        taskDao.setId(entity.getId());
        taskDao.setTitle(entity.getTitle());
        taskDao.setNote(entity.getNote());
        taskDao.setIsDone(entity.isDone());
        taskDao.setCreatedAt(entity.getCreatedAt());
        taskDao.setUpdatedAt(entity.getUpdatedAt());
        taskDao.setDueDate(entity.getDueDate());

        return taskDao;
    }

    @Override
    public ITask mapFromDAODeep(TaskDAO dao) {
        ITask taskEntity = mapFromDAOShallow(dao);

        List<ITag> tags = factory.getTagMapper().mapFromDAOsShallow(dao.getTags());

        for (ITag tagEntity : tags) {
            taskEntity.addTag(tagEntity);
        }

        return taskEntity;
    }

    @Override
    public TaskDAO mapToDAODeep(ITask entity) {
        TaskDAO taskDao = mapToDAOShallow(entity);

        List<ITag> tagEntities = Arrays.asList(entity.getTags());
        List<TagDAO> tagDaos = factory.getTagMapper().mapToDAOsShallow(tagEntities);

        List<TagDAO> tagsListRef = taskDao.getTags();
        for (TagDAO tagDao : tagDaos) {
            tagsListRef.add(tagDao);
        }

        return taskDao;
    }

    @Override
    public List<ITask> mapFromDAOsShallow(List<TaskDAO> daos) {
        List<ITask> taskEntities = new ArrayList<>();

        for (TaskDAO dao : daos) {
            taskEntities.add(mapFromDAOShallow(dao));
        }

        return taskEntities;
    }

    @Override
    public List<TaskDAO> mapToDAOsShallow(List<ITask> entities) {
        List<TaskDAO> tagDaos = new ArrayList<>();

        for (ITask entity : entities) {
            tagDaos.add(mapToDAOShallow(entity));
        }

        return tagDaos;
    }
}