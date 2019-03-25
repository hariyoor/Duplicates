/**
 * Class CharacterData:
 * 
 * Contains two data structures that keep track of the input:
 * 
 * 		- characters: A TreeMap that contains a record of all the letters entered by the user mapped to the amount of times those letters have shown up.
 * 		- histories: An ArrayList of ArrayLists of Characters. This keeps track of what characters occupy each index of characters after each letter has been added/deleted.
 */

import java.util.ArrayList;
import java.util.TreeMap;

public class CharacterData {
	/*
	 * The data structures
	 */
	private TreeMap<Character, Integer> characters;
	private ArrayList<ArrayList<Character>> histories;

	/**
	 * Default constructor, which simply initializes both data structures. 
	 */
	public CharacterData() {
		characters = new TreeMap<Character, Integer>();
		histories = new ArrayList<ArrayList<Character>>();
	}

	/**
	 * Adds an input character to both characters and histories
	 * @param input
	 */
	public void add(char input) {
		/*
		 * If characters already has the input character, then the only action needed is to increment the counter for that character.
		 * Else, a new entry must be created in characters, and the history must be updated
		 */
		if(characters.keySet().contains(input)) {
			characters.put(input, characters.get(input) + 1);
		} else {
			characters.put(input, 1);
			maintainHistory(input, 0);
		}

	}

	/**
	 * Deletes an input character from both characters and histories
	 * @param input
	 */
	public void delete(char input) {
		/*
		 * If characters does not have the input character, then the call to this method should be ignored.
		 * 
		 * If characters does have the input character and the counter is only 1, then the entry must be deleted and the history must be updated.
		 * If the counter is greater than one, than the only action needed is to decrement the counter.
		 */
		if(characters.keySet().contains(input)) {
			if(characters.get(input)==1) {
				characters.remove(input);
				maintainHistoryDelete(input, 0);
			} else {
				characters.put(input, characters.get(input) - 1);
			}
		}
	}

	/**
	 * Resets both data structures and adds each character from input.
	 * @param input
	 */
	public void reset(char[] input) {
		characters = new TreeMap<Character, Integer>();
		histories = new ArrayList<ArrayList<Character>>();
		for(char c : input) {
			this.add(Character.toUpperCase(c));
		}
	}

	/**
	 * Utility method that is used to print out the contents of characters. 
	 */
	public void currentCharacters() {
		System.out.println("Current characters read: ");
		for(Character c: characters.keySet()) {
			System.out.println(c + ": " + characters.get(c));
		}
	}

	public void currentHistories() {
		System.out.println("Current histories: ");
		for(int i = 0; i < histories.size(); i++) {
			System.out.print("Index " + (i+1) + ": ");
			for(int j = 0; j < histories.get(i).size(); j++) {
				System.out.print(histories.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints out the history of the specified index.
	 * @param index
	 */
	public void report(int index) {
		for(Character c : histories.get(index)) {
			//To account for deleted entries and to not lose the histories of indexes that may end up empty,
			// 
			if(c != '-') {
				System.out.print(c);
			}
		}
		System.out.println();
	}

	/**
	 * Used after each new element is added to traverse histories and update histories of each index.
	 * 
	 * @param input The new character to be added
	 * @param currIndex The index at which the traversal is at
	 */
	private void maintainHistory(char input, int currIndex) {
		ArrayList<Character> currHistory; // The current history.
		try {
			currHistory = histories.get(currIndex);
			Character currValue = currHistory.get(currHistory.size()-1); // The current latest value in the current history

			/*
			 * If the input value is equal to the current latest value, then the input is already accounted for in the history. 
			 * 	Therefore, no further action is needed.
			 * If the input value is less than the current latest value, or we reach a point at which the history is empty due to deleted data,
			 *  then we are at the point at which we need to insert the
			 * 	input value and move everything else over. To do this, we repeat the process from the next index with the current
			 * 	latest value. 
			 * If the input value is greater than the current latest value, then we need to continue the search for the position at
			 * 	which we need to insert the input. Therefore, we execute this method at the next index.
			 */
			if(input == currValue.charValue()) {
				return;
			} else if(input < currValue.charValue() || currValue == '-') {
				currHistory.add(input);
				if(currValue == '-') {
					currHistory.remove(currValue);
					return;
				}
				maintainHistory(currValue, currIndex + 1);
			} else {
				maintainHistory(input, currIndex + 1);
			}
		} catch (IndexOutOfBoundsException e) {
			/*
			 * If the traversal has reached the end, then an IndexOutOfBoundsException will be thrown once the next call has 
			 * 	occurred, as histories will not have an entry at that point. As such, we need to create a new ArrayList containing
			 *  just the input character and add it to histories.
			 */

			currHistory = new ArrayList<Character>();
			currHistory.add(input);
			histories.add(currIndex, currHistory);
		}
	}

	/**
	 * Used to traverse histories and maintain the history after a character has been deleted altogether.
	 * @param input The input character
	 * @param currIndex The current index of the traversal
	 */
	private void maintainHistoryDelete(char input, int currIndex) {
		ArrayList<Character> currHistory = histories.get(currIndex); // The history of the current index.
		Character currCharacter = currHistory.get(currHistory.size() - 1); // The latest character in the current index.
		if(currCharacter < input) {
			// If the current character is less than the input value, then we continue on to the next index.
			maintainHistoryDelete(input, currIndex + 1);
		}	else {
			// In order to avoid losing the histories of positions that become empty, we will put a '-' wherever there is 
			// an empty spot.
			if(currIndex == (histories.size()-1)) {
				currHistory.add('-');
				return;
			} else {
				ArrayList<Character> nextHistory = histories.get(currIndex + 1);
				Character nextCharacter = nextHistory.get(nextHistory.size()-1);
				if(nextCharacter == '-') {
					currHistory.add('-');
					return;
				} else {
					currHistory.add(nextCharacter);
					maintainHistoryDelete(nextCharacter, currIndex+1);
				}
			}
		}
	}
}
