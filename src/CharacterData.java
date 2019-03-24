import java.util.ArrayList;
import java.util.TreeMap;

public class CharacterData {
	private TreeMap<Character, Integer> characters;
	private ArrayList<ArrayList<Character>> histories;
	
	public CharacterData() {
		characters = new TreeMap<Character, Integer>();
		histories = new ArrayList<ArrayList<Character>>();
	}
	
	public void add(char input) {
		if(characters.keySet().contains(input)) {
			characters.put(input, characters.get(input) + 1);
		} else {
			characters.put(input, 1);
		}
		maintainHistory(input, 0);
	}
	
	public void delete(char input) {
		if(characters.keySet().contains(input)) {
			if(characters.get(input)==1) {
				characters.remove(input);
			} else {
				characters.put(input, characters.get(input) - 1);
			}
		}
		maintainHistory(input, 0);
	}
	
	public void reset(char[] input) {
		characters = new TreeMap<Character, Integer>();
		histories = new ArrayList<ArrayList<Character>>();
		for(char c : input) {
			this.add(c);
		}
	}
	
	public void currentCharacters() {
		System.out.println("Current characters read: ");
		for(Character c: characters.keySet()) {
			System.out.println(c + ": " + characters.get(c));
		}
	}
	
	public void report(int index) {
		for(Character c : histories.get(index)) {
			System.out.print(c);
		}
		System.out.println();
	}
	
	private void maintainHistory(char input, int currIndex) {
		ArrayList<Character> currHistory;
		try {
			currHistory = histories.get(currIndex);
			Character currValue = currHistory.get(currHistory.size()-1);
			if(input == currValue.charValue()) {
				return;
			} else if(input < currValue.charValue()) {
				currHistory.add(input);
				maintainHistory(currValue, currIndex + 1);
			} else {
				maintainHistory(input, currIndex + 1);
			}
		} catch (IndexOutOfBoundsException e) {
			currHistory = new ArrayList<Character>();
			currHistory.add(input);
			histories.add(currIndex, currHistory);
		}
	}
}
