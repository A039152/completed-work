package files;

import java.time.LocalDateTime;

public class TestProgram {
	
	public static void main(String[] args) {
		// Create files
		File file1 = new File("document.txt", 1200, LocalDateTime.of(2024, 11, 20, 14, 30));
		HiddenFile hiddenFile = new HiddenFile("secret.doc", 500, LocalDateTime.of(2024, 11, 18, 10, 0));

		// Create folders
		Folder folder = new Folder("MyFolder");
		folder.add(file1);
		folder.add(hiddenFile);

		// Create remote folder
		RemoteFolder remoteFolder = new RemoteFolder("RemoteFolder");
		remoteFolder.add(new File("remoteFile.txt", 300, LocalDateTime.of(2024, 11, 15, 9, 0)));

		// Add folders to the root
		Folder root = new Folder("Coding Assessment");
		root.add(folder);
		root.add(remoteFolder);

		// Output the HTML representation
		System.out.println(root.asHTML());
	}
}
