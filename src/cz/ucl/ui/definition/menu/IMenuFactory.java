package cz.ucl.ui.definition.menu;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.ui.definition.IUserInterface;

public interface IMenuFactory {
    IMenu createMainMenu(IUserInterface ui);
    IMenu createQuitMenu(IMenu parentMenu);
    IMenu createBackMenu(IMenu parentMenu);
    IMenu createFillFormMenu(IMenu parentMenu);

    IMenu createLoginFormMenu(IMenu parentMenu);
    IMenu createRegistrationFormMenu(IMenu parentMenu);
    IMenu createLogoutMenu(IMenu parentMenu);

    //region Settings
    IMenu createSettingsMenu(IMenu parentMenu);
    //endregion

    //region Tasks
    IMenu createTasksMenu(IMenu parentMenu);
    IMenu createTaskListMenu(IMenu parentMenu, ITask[] tasks);
    IMenu createTaskDetailMenu(IMenu parentMenu, ITask task);
    IMenu createNewTaskMenu(IMenu parentMenu);
    IMenu createUpdateTaskMenu(IMenu parentMenu, ITask task);
    //endregion

    //region Categories
    IMenu createCategoriesMenu(IMenu parentMenu);
    IMenu createCategoryListMenu(IMenu parentMenu, ICategory[] categories);
    IMenu createCategoryDetailMenu(IMenu parentMenu, ICategory category);
    IMenu createNewCategoryMenu(IMenu parentMenu);
    IMenu createUpdateCategoryMenu(IMenu parentMenu, ICategory category);
    //endregion

    //region Tags
    IMenu createTagsMenu(IMenu parentMenu);
    IMenu createTagListMenu(IMenu parentMenu, ITag[] tags);
    IMenu createTagDetailMenu(IMenu parentMenu, ITag tag);
    IMenu createNewTagMenu(IMenu parentMenu);
    IMenu createUpdateTagMenu(IMenu parentMenu, ITag tagd);
    //endregion

    //region User

    IMenu createUserMenu(IMenu parentMenu);

    //endregion
}
