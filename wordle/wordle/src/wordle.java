import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class wordle {

    static List<String> words = new ArrayList<>();
    static String answer; // the word to guess
    static String[][] board = new String[6][5]; // 6x5 wordle board
    static int currentRow = 0;

    // main method for testing!
    // [x] indicates green square
    // (x) indicates yellow square
    // [ ] indicates gray square
    public static void main(String[] args) throws FileNotFoundException {

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = "-";
            }
        }

        loadWords();
        answer = getRandomWord();
        System.out.println("ready to play the daily wordle?");

        Scanner scanner = new Scanner(System.in);
        boolean hasWon = false;

        while (currentRow < 6 && !hasWon) {
            printBoard();
            System.out.print("enter your guess #" + (currentRow + 1) + ": ");
            String guess = scanner.nextLine().toLowerCase();
            if (guess.length() == 5) {
                updateBoard(guess);
                if (guess.equals(answer)) {
                    hasWon = true;
                }
                currentRow++;
            } else {
                System.out.println("please enter only 5 letter words!");
            }
        }

        scanner.close();
        printBoard();
        if (hasWon) {
            System.out.println("congratulations! you guessed the word: " + answer);
        } else {
            System.out.println("sorry, you've used all your guesses. the word was: " + answer);
        }
    }

    // load all 5 letter words from a text file
    public static void loadWords() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("5-letter-words.txt"));
        while (fileScanner.hasNextLine()) {
            words.add(fileScanner.nextLine().toLowerCase());
        }
        fileScanner.close();
    }

    // pick a random word for the game
    public static String getRandomWord() {
        int randomIndex = (int)(Math.random() * words.size()); // always generates an index within bounds
        return words.get(randomIndex);
    }

    public static void updateBoard(String guess) {
        boolean[] answerLettersUsed = new boolean[5]; // to track which letters in the answer have been used
        boolean[] isGreen = new boolean[5]; // to track which letters in the guess are green

        for (int i = 0; i < 5; i++) {
            board[currentRow][i] = " " + guess.charAt(i) + " ";
        }

        // marking green squares
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                board[currentRow][i] = "[" + guess.charAt(i) +  "]";
                answerLettersUsed[i] = true;
                isGreen[i] = true;
            } else {
                board[currentRow][i] = "[ ]";
            }
        }

        // marking yellow/gray squares
        for (int i = 0; i < 5; i++) {
            boolean alreadyGreen = (guess.charAt(i) == answer.charAt(i));
            boolean foundElsewhere = false;
            if (!alreadyGreen) {
                int j = 0;
                while (j < 5 && !foundElsewhere) {
                    if (!answerLettersUsed[j] && guess.charAt(i) == answer.charAt(j)) {
                        board[currentRow][i] = "(" + guess.charAt(i) + ")";
                        answerLettersUsed[j] = true;
                        foundElsewhere = true;
                    }
                    j++;
                }
            }
            // if (!foundElsewhere) {
            //     board[currentRow][i] = "[ ]";
            // }
        }
    }

    // print the current state of the board
    public static void printBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
