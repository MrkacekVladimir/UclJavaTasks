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
        return String.format("#%i - %n - %c", task.getId(), task.getTitle(), task.getCategory().getTitle());
    }
}
