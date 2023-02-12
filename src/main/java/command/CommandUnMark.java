package command;

import duke.DukeException;
import duke.TaskList;
import duke.Ui;

/**
 * Command to unmark task.
 */
public class CommandUnMark extends Command {

    private final TaskList taskList;
    private final String index;

    /**
     * Constructor for CommandUnMark
     *
     * @param taskList List of all tasks.
     * @param index Index of task to be unmark, starting from 1.
     */
    public CommandUnMark(TaskList taskList, String index) {
        this.taskList = taskList;
        this.index = index;
    }

    @Override
    public String execute() throws DukeException {
        return Ui.getUnMarkMessageWithAttitude(this.taskList.unMarkTask(this.index));
    }
}
