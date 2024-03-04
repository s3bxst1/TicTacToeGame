import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame extends JFrame {
    private TTTBoard board;
    private JButton[][] buttons;

    public TicTacToeGame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        board = new TTTBoard();
        buttons = new JButton[3][3];

        initializeUI();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(100, 100));
                button.addActionListener(new ButtonClickListener(row, col));
                mainPanel.add(button);
                buttons[row][col] = button;
            }
        }

        add(mainPanel);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board.isTileEmpty(row, col)) {
                char currentPlayer = board.getCurrentPlayer();
                board.markTile(row, col);
                buttons[row][col].setText(String.valueOf(currentPlayer));

                if (board.checkWin()) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    resetGame();
                } else if (board.isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a cat's game!");
                    resetGame();
                }
            }
        }
    }

    private void resetGame() {
        board.resetBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGame::new);
    }
}

class TTTBoard {
    private char[][] board;
    private char currentPlayer;

    public TTTBoard() {
        board = new char[3][3];
        currentPlayer = 'X';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isTileEmpty(int row, int col) {
        return board[row][col] == '\0';
    }

    public void markTile(int row, int col) {
        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public boolean checkWin() {

        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != '\0') {
                return true;
            }
        }


        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col] != '\0') {
                return true;
            }
        }


        return (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '\0') ||
                (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '\0');
    }

    public boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = '\0';
            }
        }
        currentPlayer = 'X';
    }
}
