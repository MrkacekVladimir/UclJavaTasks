package cz.ucl.ui.cli.menu;

import cz.ucl.logic.IAppLogic;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.ui.cli.forms.FormField;
import cz.ucl.ui.cli.menu.system.BackMenu;
import cz.ucl.ui.cli.menu.system.FillFormMenu;
import cz.ucl.ui.cli.menu.system.LogoutMenu;
import cz.ucl.ui.cli.menu.system.QuitMenu;
import cz.ucl.ui.cli.menu.user.MainMenu;
import cz.ucl.ui.cli.menu.user.TaskListMenu;
import cz.ucl.ui.definition.IUserInterface;
import cz.ucl.ui.definition.forms.FormFieldType;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.IMenuFactory;
import cz.ucl.ui.definition.menu.MenuType;
import cz.ucl.ui.definition.views.ICategoryView;
import cz.ucl.ui.definition.views.ITagView;
import cz.ucl.ui.definition.views.ITaskView;

public class MenuFactory implements IMenuFactory {
    @Override
    public IMenu createMainMenu(IUserInterface ui) {
        return new MainMenu(ui, "Hlavní nabídka");
    }

    @Override
    public IMenu createQuitMenu(IMenu parentMenu) {
        return new QuitMenu(parentMenu, "Ukončit aplikaci");
    }

    @Override
    public IMenu createBackMenu(IMenu parentMenu) {
        return new BackMenu(parentMenu, "Zpět");
    }

    @Override
    public IMenu createFillFormMenu(IMenu parentMenu) {
        return new FillFormMenu(parentMenu, "Vyplnit formulář");
    }

    @Override
    public IMenu createLoginFormMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "login", "Přihlásit se") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("email", "E-Mail", FormFieldType.TEXTUAL));
                addFormField(new FormField("password", "Heslo", FormFieldType.SECURE));
            }

            @Override
            protected void build() {
                setDescription("Pro přihlášení je třeba zadat uživatelské jméno a heslo.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createRegistrationFormMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "register", "Registrovat se") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("email", "E-Mail", FormFieldType.TEXTUAL));
                addFormField(new FormField("username", "Uživatelské jméno", FormFieldType.TEXTUAL));
                addFormField(new FormField("password", "Heslo", FormFieldType.SECURE));
            }

            @Override
            protected void build() {
                setDescription("Pro registraci je třeba zadat e-mail, uživatelské jméno a heslo.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createLogoutMenu(IMenu parentMenu) {
        return new LogoutMenu(parentMenu, "Odhlásit se");
    }

    //region Settings

    @Override
    public IMenu createSettingsMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "settings", "Nastavení") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete upravovat svoje štítky a kategorie."
                );

                IAppLogic logic = ui.getLogic();

                IMenu tagMenu = ui.getMenuFactory().createTagsMenu(this);
                IMenu categoryMenu = ui.getMenuFactory().createCategoriesMenu(this);
                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), tagMenu));
                addOption(new MenuOption(nextOptionNumber(), categoryMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    //endregion


    //region Tasks
    @Override
    public IMenu createTasksMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "tasks", "Administrace úkolů") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete pracovat svoje úkoly."
                );

                IAppLogic logic = ui.getLogic();
                IMenuFactory menuFactory = ui.getMenuFactory();

                IMenu tasksListMenu = menuFactory.createTaskListMenu(this, logic.getAllTasks());
                IMenu createTaskMenu = menuFactory.createNewTaskMenu(this);
                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), tasksListMenu));
                addOption(new MenuOption(nextOptionNumber(), createTaskMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createTaskListMenu(IMenu parentMenu, ITask[] tasks) {
        return new TaskListMenu(parentMenu, tasks, "taskList");
    }

    @Override
    public IMenu createTaskDetailMenu(IMenu parentMenu, ITask task) {
        return new Menu(parentMenu, "taskDetail", String.format("#%d - %s", task.getId(), task.getTitle())) {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void build() {
                setDescription(String.format("Upravujete úkol #%d - '%s'", task.getId(), task.getTitle()));

                IAppLogic logic = ui.getLogic();

                ITaskView view = ui.getTaskView();
                setDescription(view.formatTask(task));

                IMenu updateMenu = ui.getMenuFactory().createUpdateTaskMenu(this, task);
                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), updateMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }
        };
    }

    @Override
    public IMenu createNewTaskMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "newTask", "Nový úkol") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se přidat nový úkol.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createUpdateTaskMenu(IMenu parentMenu, ITask task) {
        return new FormMenu(parentMenu, "updateTask", String.format("Úprava úkolu - #%d", task.getId())) {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se přidat nový úkol.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    //endregion


    //region Categories

    @Override
    public IMenu createCategoriesMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "categories", "Administrace kategorií") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete pracovat se svými kategoriemi."
                );

                IAppLogic logic = ui.getLogic();
                IMenuFactory menuFactory = ui.getMenuFactory();

                IMenu categoryListMenu = menuFactory.createCategoryListMenu(this, logic.getAllCategories());
                IMenu createCategoryMenu = menuFactory.createNewCategoryMenu(this);
                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), categoryListMenu));
                addOption(new MenuOption(nextOptionNumber(), createCategoryMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createCategoryListMenu(IMenu parentMenu, ICategory[] categories) {
        return new Menu(parentMenu, "categoryList", "Seznam kategorií") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete upravovat váš seznam kategorií."
                );

                ICategoryView view = ui.getCategoryView();
                IMenuFactory menuFactory = ui.getMenuFactory();

                for (ICategory category : categories) {
                    IMenu detailMenu = menuFactory.createCategoryDetailMenu(this, category);
                    addOption(new MenuOption(category.getId(), detailMenu));
                }

                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createCategoryDetailMenu(IMenu parentMenu, ICategory category) {
        return new Menu(parentMenu, "categoryDetail", String.format("#%d - %s", category.getId(), category.getTitle())) {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void build() {
                setDescription("");

                IAppLogic logic = ui.getLogic();

                ICategoryView view = ui.getCategoryView();
                setDescription(view.formatCategory(category));

                IMenu updateMenu = ui.getMenuFactory().createUpdateCategoryMenu(this, category);
                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), updateMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }
        };
    }

    @Override
    public IMenu createNewCategoryMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "newCategory", "Nová kategorie") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se přidat novou kategorii.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createUpdateCategoryMenu(IMenu parentMenu, ICategory category) {
        return new FormMenu(parentMenu, "updateCategory", "Upravit kategorii") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("id", "Identifier", FormFieldType.HIDDEN, false, Integer.toString(category.getId())));
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se upravit kategorii.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    //endregion

    //region Tags

    @Override
    public IMenu createTagsMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "tags", "Administrace štítků") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete pracovat se svými štítky."
                );

                IAppLogic logic = ui.getLogic();
                IMenuFactory menuFactory = ui.getMenuFactory();

                IMenu tagListMenu = menuFactory.createTagListMenu(this, logic.getAllTags());
                IMenu createTagMenu = menuFactory.createNewTagMenu(this);
                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), tagListMenu));
                addOption(new MenuOption(nextOptionNumber(), createTagMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createTagListMenu(IMenu parentMenu, ITag[] tags) {
        return new Menu(parentMenu, "tagList", "Seznam štítků") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete upravovat váš seznam štítků."
                );

                setDescription("");

                ITagView view = ui.getTagView();
                IMenuFactory menuFactory = ui.getMenuFactory();

                for (ITag tag : tags) {
                    IMenu detailMenu = menuFactory.createTagDetailMenu(this, tag);
                    addOption(new MenuOption(tag.getId(), detailMenu));
                }

                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createTagDetailMenu(IMenu parentMenu, ITag tag) {
        return new Menu(parentMenu, "tagDetail", String.format("#%d - %s", tag.getId(), tag.getTitle())) {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void build() {
                setDescription(String.format("Upravujete štítek #%d - '%s'", tag.getId(), tag.getTitle()));

                IAppLogic logic = ui.getLogic();

                ITagView view = ui.getTagView();
                setDescription(view.formatTag(tag));

                IMenu updateMenu = ui.getMenuFactory().createUpdateTagMenu(this, tag);
                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), updateMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }
        };
    }

    @Override
    public IMenu createNewTagMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "newTag", "Nový štítek") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se přidat nový štítek.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createUpdateTagMenu(IMenu parentMenu, ITag tag) {
        return new FormMenu(parentMenu, "updateTag", "Upravit") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se upravit štítek.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    //endregion


    //region User

    @Override
    public IMenu createUserMenu(IMenu parentMenu) {
        return null;
    }

    //endregion


}
