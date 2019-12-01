package cz.ucl.logic.data.mappers.definition;

import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.data.dao.TagDAO;

import java.util.List;

public interface ITagMapper {
    ITag mapFromDAOShallow(TagDAO dao);
    TagDAO mapToDAOShallow(ITag entity);
    ITag mapFromDAODeep(TagDAO dao);
    TagDAO mapToDAODeep(ITag entity);

    List<ITag> mapFromDAOsShallow(List<TagDAO> daos);
    List<TagDAO> mapToDAOsShallow(List<ITag> entities);
}
