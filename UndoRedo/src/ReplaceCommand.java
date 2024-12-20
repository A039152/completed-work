class ReplaceCommand implements Command {
    private StringBuffer text;
    private int offset;
    private String oldText;
    private String newText;

    public ReplaceCommand(StringBuffer text, int offset, int length, String newText) {
        this.text = text;
        this.offset = offset;
        this.oldText = text.substring(offset, offset + length);
        this.newText = newText;
    }

    @Override
    public void execute() {
        text.replace(offset, offset + oldText.length(), newText);
    }

    @Override
    public void unexecute() {
        text.replace(offset, offset + newText.length(), oldText);
    }
}