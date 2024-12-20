import java.util.Stack;

// Command Interface
interface Command {
    void execute();
    void unexecute();
}