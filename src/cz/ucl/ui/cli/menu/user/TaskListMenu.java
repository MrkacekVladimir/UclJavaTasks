package cz.ucl.ui.cli.menu.user;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.ui.cli.menu.Menu;
import cz.ucl.ui.cli.menu.MenuOption;
import cz.ucl.ui.cli.views.TaskView;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.MenuType;

public class TaskListMenu extends Menu {
    private ITask[] tasks;

    public TaskListMenu(IMenu parentMenu, ITask[] tasks, String identifier, String title) {
        super(parentMenu, identifier, title);

        this.tasks = tasks;
    }

    @Override
    protected void build() {
        setDescription("Všechny úkoly");

        String formattedTasks = this.ui.getTaskView().formatTaskList(this.tasks);
        this.ui.drawOutput(formattedTasks);

        IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

        addOption(new MenuOption(nextOptionNumber(), backMenu));
    }

    @Override
    public MenuType getType() {
        return MenuType.USER;
    }
}
