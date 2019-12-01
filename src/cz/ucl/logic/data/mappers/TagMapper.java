package cz.ucl.logic.data.mappers;

import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.data.dao.TagDAO;
import cz.ucl.logic.data.mappers.definition.ITagMapper;

import java.util.ArrayList;
import java.util.List;

public class TagMapper implements ITagMapper {
    MapperFactory factory;

    public TagMapper(MapperFactory factory) {
        this.factory = factory;
    }

    // TODO
}
