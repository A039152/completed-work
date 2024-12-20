public class UndoRedoTest {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor("Every good boy does fine.");

        editor.insertText(13, "d");
        System.out.println(editor.getText());

        editor.deleteText(5, 6);
        System.out.println(editor.getText());

        editor.replaceText(15, 4, "well");
        System.out.println(editor.getText());

        editor.undo();
        System.out.println(editor.getText());

        editor.undo();
        System.out.println(editor.getText());

        editor.redo();
        System.out.println(editor.getText());

        System.out.println("\nAdditional Tests");


        editor.replaceText(9, 4, "great");
        System.out.println(editor.getText());

        editor.undo();
        System.out.println(editor.getText());

        editor.insertText(0, "Wow! ");
        System.out.println(editor.getText());

        editor.undo();
        System.out.println(editor.getText());

        editor.redo();
        System.out.println(editor.getText());

        editor.deleteText(4, 9);
        System.out.println(editor.getText());

        editor.undo();
        System.out.println(editor.getText());

        editor.redo();
        System.out.println(editor.getText());
    }
}