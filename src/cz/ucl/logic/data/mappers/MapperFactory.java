package cz.ucl.logic.data.mappers;

import cz.ucl.logic.data.mappers.definition.ICategoryMapper;
import cz.ucl.logic.data.mappers.definition.ITagMapper;
import cz.ucl.logic.data.mappers.definition.ITaskMapper;
import cz.ucl.logic.data.mappers.definition.IUserMapper;

public class MapperFactory {
    private ICategoryMapper categoryMapper;
    private ITagMapper tagMapper;
    private ITaskMapper taskMapper;
    private IUserMapper userMapper;

    public MapperFactory(){
        this.categoryMapper = new CategoryMapper(this);
        this.tagMapper = new TagMapper(this);
        this.taskMapper = new TaskMapper(this);
        this.userMapper = new UserMapper(this);
    }

    public ICategoryMapper getCategoryMapper() {
        return categoryMapper;
    }

    public ITagMapper getTagMapper() {
        return tagMapper;
    }

    public ITaskMapper getTaskMapper() {
        return taskMapper;
    }

    public IUserMapper getUserMapper() {
        return userMapper;
    }
}
