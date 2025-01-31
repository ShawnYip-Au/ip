package command;

import duke.DukeException;
import duke.Ui;
import storage.Storage;
import task.Task;
import task.TaskList;

/**
 * Command to mark a task as done.
 */
public class CommandMark extends Command {

    private final TaskList taskList;
    private final String index;
    private final Storage storage;

    /**
     * Constructor for CommandMark.
     *
     * @param taskList List of all tasks.
     * @param index Index of task to mark, starting from 1.
     * @param storage Handles storage actions.
     */
    public CommandMark(TaskList taskList, String index, Storage storage) {
        this.taskList = taskList;
        this.index = index;
        this.storage = storage;
    }

    @Override
    public String execute() throws DukeException {
        Task markedTask = this.markTaskAt(this.index);
        this.updateFile();
        return this.getConfirmationMessageOf(markedTask);
    }

    private void updateFile() throws DukeException {
        this.storage.overwriteFile(this.taskList);
    }

    private Task markTaskAt(String index) throws DukeException {
        int i = this.getIndex(index);
        return this.taskList.getTaskAt(i).markIsDone();
    }

    private int getIndex(String index) throws DukeException {
        try {
            return Integer.parseInt(index);
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
    }

    private String getConfirmationMessageOf(Task markedTask) {
        return Ui.getMarkMessageWithAttitude(markedTask);
    }
}
