class DeleteCommand implements Command {
    private StringBuffer text;
    private int offset;
    private String deletedText;

    public DeleteCommand(StringBuffer text, int offset, int length) {
        this.text = text;
        this.offset = offset;
        this.deletedText = text.substring(offset, offset + length);
    }

    @Override
    public void execute() {
        text.delete(offset, offset + deletedText.length());
    }

    @Override
    public void unexecute() {
        text.insert(offset, deletedText);
    }
}