package duke;

import command.Command;
import task.TaskList;
import storage.Storage;

/**
 * A class containing Duke logical sequence.
 */
public class Duke {

    private final Storage storage;

    private final TaskList taskList;

    /**
     * Constructor for Duke.
     *
     * @param filePath Path of file that stores the TaskList data.
     */
    public Duke(String filePath) {
        this.storage = new Storage(filePath);
        taskList = new TaskList(storage.loadFile());
    }

    /**
     * Execute user input and get the appropriate confirmation message.
     *
     * @param userInput Input of user.
     * @return Confirmation message.
     */
    public String getResponse(String userInput) {
        Command c = interpretInput(userInput);
        return executeCommand(c);
    }

    private Command interpretInput(String userInput) {
        Parser parser = new Parser(this.taskList);
        return parser.decode(userInput);
    }

    private String executeCommand(Command c) {
        try {
            return c.execute();
        } catch (DukeException e) {
            return e.toString();
        }
    }

}
