import java.util.Stack;

class TextEditor {
    private StringBuffer currentText;
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public TextEditor(String initialText) {
        this.currentText = new StringBuffer(initialText);
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void insertText(int offset, String text) {
        Command command = new InsertCommand(currentText, offset, text);
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void deleteText(int offset, int length) {
        Command command = new DeleteCommand(currentText, offset, length);
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void replaceText(int offset, int length, String newText) {
        Command command = new ReplaceCommand(currentText, offset, length, newText);
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.unexecute();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    public String getText() {
        return currentText.toString();
    }
}
