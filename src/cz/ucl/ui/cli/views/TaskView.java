package cz.ucl.ui.cli.views;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.ui.definition.views.ITaskView;

public class TaskView implements ITaskView {
    @Override
    public String formatTaskList(ITask[] taskList) {
        StringBuilder builder = new StringBuilder();

        for ( ITask task : taskList ){
            builder.append(this.formatTask(task));
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    @Override
    public String formatTask(ITask task) {
        String categoryTitle = "No category";
        if(task.getCategory() != null){
            categoryTitle = task.getCategory().getTitle();
        }

        return String.format("#%d - %s - %s", task.getId(), task.getTitle(), categoryTitle);
    }
}
