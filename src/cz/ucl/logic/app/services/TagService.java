package cz.ucl.logic.app.services;

import cz.ucl.logic.app.entities.Tag;
import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.services.definition.ITagService;
import cz.ucl.logic.app.services.definition.IUserService;
import cz.ucl.logic.data.managers.definition.ITagManager;


public class TagService implements ITagService {
    private IUserService userService;
    private ITagManager manager;

    public TagService(IUserService userService, ITagManager manager) {
        this.userService = userService;
        this.manager = manager;
    }

    @Override
    public ITag[] getAllTags() {
        return manager.getAllTagsForUser(userService.getUserLoggedIn());
    }

    @Override
    public ITag getTagById(int id) {
        return manager.getTagByIdForUser(id, userService.getUserLoggedIn());
    }

    @Override
    public void createTag(String title) {
        manager.createTag(new Tag(userService.getUserLoggedIn(), title));
    }

    @Override
    public void createTag(String title, Color color) {
        manager.createTag(new Tag(userService.getUserLoggedIn(), title, color));
    }

    @Override
    public void updateTag(int id, String title, Color color) {
        // TODO
    }

    @Override
    public void destroyTag(int id) {
        // TODO
    }
}
