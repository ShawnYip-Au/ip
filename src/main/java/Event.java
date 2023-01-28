import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private LocalDate startTime;
    private LocalDate endTime;

    public Event(String s, String startTime, String endTime) {
        super(s);
        this.startTime = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.endTime = LocalDate.parse(endTime, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Event(Boolean isTaskDone, String taskDetails, String taskDate) {
        super(taskDetails);
        if (isTaskDone) {
            this.markDone();
        }
        String from = taskDate.substring(0, taskDate.indexOf("|"));
        String to = taskDate.substring(taskDate.indexOf("|") + 1);

        System.out.println(from);
        System.out.println(to);


        this.startTime = LocalDate.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.endTime = LocalDate.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String writeToFile() {
        if (!taskDone) {
            return "E| |"
                    + this.taskName
                    + "|"
                    + this.startTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "|"
                    + this.endTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "E|X|"
                + this.taskName
                + "|"
                + this.startTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "|"
                + this.endTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        if (!taskDone) {
            return "[E][ ] " + this.taskName
                + " (from: " + this.startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                + " to: " + this.endTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ")";
        }
        return "[E][X] " + this.taskName
                + " (from: " + this.startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                + " to: " + this.endTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ")";
    }

}
