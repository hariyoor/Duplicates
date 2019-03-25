import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	
	public static void main(String[] args) {
		CharacterData characterlist = new CharacterData();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter command: ");
		
		String response = scanner.nextLine();
		
		while(!response.contentEquals("exit")) {
			
			
			String[] responseComponents = response.split(" ", 2);
			responseComponents[1] = responseComponents[1].replaceAll(" ", "");
			
			if(responseComponents[0].contentEquals("ADD")) {
				char[] charactersToAdd = responseComponents[1].toCharArray();
				for(char c : charactersToAdd) {
					characterlist.add(Character.toUpperCase(c));
				}
			} else if (responseComponents[0].contentEquals("RESET")) {
				char[] charactersToAdd = responseComponents[1].toCharArray();
				characterlist.reset(charactersToAdd);
			} else if (responseComponents[0].contentEquals("DELETE")) {
				char[] charactersToDelete = responseComponents[1].toCharArray();
				for(char c : charactersToDelete) {
					characterlist.delete(Character.toUpperCase(c));
				}
			} else if (responseComponents[0].contentEquals("REPORT")) {
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
