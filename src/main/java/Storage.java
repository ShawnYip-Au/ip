import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Storage {

    private Path filePath;

    public Storage(String filePath) {
        String home = System.getProperty("user.home"); // Get home directory
        String[] s = filePath.split("/");

        // Create directories
        this.filePath = Paths.get(home);
        for (int i = 0; i < s.length - 1; i++) {
            this.filePath = Paths.get(String.valueOf(this.filePath), s[i]);

            try {
                Files.createDirectories(this.filePath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }

        }

        // Create file
        this.filePath = Paths.get(String.valueOf(this.filePath), s[s.length-1]);
        try {
            Files.createFile(this.filePath); // Create empty file if it does not exist
        }
        catch (FileAlreadyExistsException ignored) {
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }

    public LinkedList<Task> loadFile() {

        LinkedList<Task> savedInputs = new LinkedList<>();

        try {
            Boolean isTaskDone;
            String taskDetails, taskDate;
            String[] savedTasks = Files.readString(filePath).split("\n");

            if (savedTasks[0].isBlank()) {
                return savedInputs; // blank file
            }

            for (String task : savedTasks) {
                switch (task.charAt(0)) {
                    case 'T':
                        isTaskDone = this.getIsTaskDone(task);
                        taskDetails = this.getTaskDetails(task);
                        savedInputs.add(new ToDo(isTaskDone, taskDetails));
                        break;
                    case 'D':
                        isTaskDone = this.getIsTaskDone(task);
                        taskDetails = this.getTaskDetails(task);
                        taskDate = this.getTaskDate(task);
                        savedInputs.add(new Deadline(isTaskDone, taskDetails, taskDate));
                        break;
                    case 'E':
                        isTaskDone = this.getIsTaskDone(task);
                        taskDetails = this.getTaskDetails(task);
                        taskDate = this.getTaskDate(task);
                        savedInputs.add(new Event(isTaskDone, taskDetails, taskDate));
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage()); // file creation unsuccessful
        }
        return savedInputs;
    }

    private Boolean getIsTaskDone(String s) {
        String line = s.substring(s.indexOf("|") + 1);
        return line.substring(0, line.indexOf("|")).equals("X");
    }

    private String getTaskDetails(String s) {
        String line = s.substring(s.indexOf("|") + 1);
        line = line.substring(line.indexOf("|") + 1);

        if (!line.contains("|")) {
            return line;
        }

        return line.substring(0, line.indexOf("|"));
    }

    private String getTaskDate(String s) {
        String line = s.substring(s.indexOf("|") + 1);
        line = line.substring(line.indexOf("|") + 1);
        line = line.substring(line.indexOf("|") + 1);
        return line;
    }

    public void overwriteFile(List<Task> storedInputs) {
        StringBuilder s = new StringBuilder();
        for (Task storedInput : storedInputs) {
            s.append(storedInput.writeToFile());
            s.append("\n");
        }

        try {
            Files.writeString(filePath, s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
