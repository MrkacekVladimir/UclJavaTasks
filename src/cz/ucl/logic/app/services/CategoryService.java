package cz.ucl.logic.app.services;

import cz.ucl.logic.app.entities.Category;
import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.logic.app.services.definition.ICategoryService;
import cz.ucl.logic.app.services.definition.IUserService;
import cz.ucl.logic.data.managers.definition.ICategoryManager;

import java.time.LocalDateTime;

public class CategoryService implements ICategoryService {
    private IUserService userService;
    private ICategoryManager manager;

    public CategoryService(IUserService userService, ICategoryManager manager) {
        this.userService = userService;
        this.manager = manager;
    }

    @Override
    public ICategory[] getAllCategories() {
        return manager.getAllCategoriesForUser(userService.getUserLoggedIn());
    }

    @Override
    public ICategory getCategoryById(int id) {
        return manager.getCategoryByIdForUser(id, userService.getUserLoggedIn());
    }

    @Override
    public void createCategory(String title) {
        manager.createCategory(new Category(userService.getUserLoggedIn(), title));
    }

    @Override
    public void createCategory(String title, Color color) {
        manager.createCategory(new Category(userService.getUserLoggedIn(), title, color));
    }

    @Override
    public void updateCategory(int id, String title, Color color) {
        ICategory foundCategory = manager.getCategoryByIdForUser(id, userService.getUserLoggedIn());

        ICategory updatedCategory = new Category(userService.getUserLoggedIn(), id, title, color, foundCategory.getCreatedAt(), LocalDateTime.now());

        // Warning! We need to copy all nested attributes of the category as well!
        for (ITask task : foundCategory.getTasks()) {
            updatedCategory.addTask(task);
        }

        manager.updateCategory(updatedCategory);
    }

    @Override
    public void destroyCategory(int id) {
        manager.deleteCategoryByIdForUser(id, userService.getUserLoggedIn());
    }
}
