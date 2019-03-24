import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	
	public static void main(String[] args) {
		CharacterData characterlist = new CharacterData();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter command: ");
		
		String response = scanner.nextLine();
		
		while(!response.contentEquals("exit")) {
			
			
			String[] responseComponents = response.split(" ");
			
			if(responseComponents[0].contentEquals("add")) {
				char[] charactersToAdd = responseComponents[1].toCharArray();
				for(char c : charactersToAdd) {
					characterlist.add(c);
				}
			} else if (responseComponents[0].contentEquals("reset")) {
				char[] charactersToAdd = responseComponents[1].toCharArray();
				characterlist.reset(charactersToAdd);
			} else if (responseComponents[0].contentEquals("delete")) {
				char[] charactersToDelete = responseComponents[1].toCharArray();
				for(char c : charactersToDelete) {
					characterlist.delete(c);
				}
			} else if (responseComponents[0].contentEquals("report")) {
				int index = Integer.parseInt(responseComponents[1]);
				characterlist.report(index-1);
			}
			characterlist.currentCharacters();
			System.out.println();
			System.out.println("Enter command: ");
			
			response = scanner.nextLine();
		}
		System.out.println("Exited Program");
	}
}
