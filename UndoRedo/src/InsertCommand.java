class InsertCommand implements Command {
    private StringBuffer text;
    private int offset;
    private String insertedText;

    public InsertCommand(StringBuffer text, int offset, String insertedText) {
        this.text = text;
        this.offset = offset;
        this.insertedText = insertedText;
    }

    @Override
    public void execute() {
        text.insert(offset, insertedText);
    }

    @Override
    public void unexecute() {
        text.delete(offset, offset + insertedText.length());
    }
}
