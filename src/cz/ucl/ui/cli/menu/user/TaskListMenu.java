package cz.ucl.ui.cli.menu.user;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.ui.cli.menu.Menu;
import cz.ucl.ui.cli.menu.MenuOption;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.IMenuFactory;
import cz.ucl.ui.definition.menu.MenuType;
import cz.ucl.ui.definition.views.ITaskView;

public class TaskListMenu extends Menu {
    public TaskListMenu(IMenu parentMenu, String identifier) {
        super(parentMenu, identifier, "Seznam úkolů");
    }

    @Override
    protected void build() {
        setDescription("Všechny úkoly");

        ITaskView view = ui.getTaskView();
        IMenuFactory menuFactory = ui.getMenuFactory();

        for (ITask task : this.logic.getAllTasks()) {
            IMenu detailMenu = menuFactory.createTaskDetailMenu(this, task.getId());
            addOption(new MenuOption(nextOptionNumber(), detailMenu));
        }

        IMenu backMenu = menuFactory.createBackMenu(this);

        addOption(new MenuOption(nextOptionNumber(), backMenu));
    }

    @Override
    public MenuType getType() {
        return MenuType.USER;
    }
}
