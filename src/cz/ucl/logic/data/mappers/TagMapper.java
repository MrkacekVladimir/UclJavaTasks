package cz.ucl.logic.data.mappers;

import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.data.dao.TagDAO;
import cz.ucl.logic.data.mappers.definition.ITagMapper;

import java.util.List;

public class TagMapper implements ITagMapper {
    private MapperFactory factory;

    public TagMapper(MapperFactory factory){
        this.factory = factory;
    }

    @Override
    public ITag mapFromDao(TagDAO dao) {
        return null;
    }

    @Override
    public List<ITag> mapFromDaoList(List<TagDAO> daoList) {
        return null;
    }

    @Override
    public TagDAO mapToDao(ITag entity) {
        return null;
    }

    @Override
    public List<TagDAO> mapToDaoList(List<ITag> entityList) {
        return null;
    }
}
