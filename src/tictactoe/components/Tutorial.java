package tictactoe.components;

import external.AudioReader;

import tictactoe.Colors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static tictactoe.TicTacToe.DECIDED_SIZE;
import static tictactoe.TicTacToe.APP_ICON;
import static tictactoe.TicTacToe.asInt;
import static tictactoe.TicTacToe.getBestFontSize;

/**
 * Extremely rudimentary tutorial; probably will update in the future.
 * @see MenuBar
 */
public class Tutorial extends JFrame {
    public Tutorial() {
        JPanel init = new JPanel(new BorderLayout());

        init.setOpaque(true);
        init.setBorder(new LineBorder(new Color(0xA6DBD9), asInt(DECIDED_SIZE * 0.05)));

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(true);
        content.setBackground(Colors.query("BUBBLE_BACKGROUND"));
        int size = asInt(DECIDED_SIZE * 0.0125);
        content.setBorder(new EmptyBorder(size, size, size, size));

        JLabel header = new JLabel();
        header.setText("Tic Tac Toe");
        header.setFocusable(true);
        header.setFont(new Font("Arial", Font.BOLD, getBestFontSize()));

        JTextArea ta = new JTextArea(
                """
                Welcome to my version of Tic Tac Toe! Included is a complete description of how to play.
                
                \u00A71. Start the Game
                There is a yellow bubble towards the top of the screen that consists of two selectors and one action button. Let's delve in to each in more detail.
                
                \u00A72. Selector Buttons
                Each selector has two arrows and a piece of text straddled in between. The text represents a player selection! There are four types of players: Human, Easy Bot, Medium Bot, and Hard Bot. You can cycle through each of these by pressing the previously mentioned arrows
                        
                \u00A73. Action Button
                The action button has two states: 'start' (Green Arrow) and 'reset' (Return Arrow). The start button starts a round with the two players supplied on each adjacent wheel. The reset button clears the board and lets you pick new players.
                
                \u00A74. Players and their Logic
                Human: Read user input; Easy Bot: Random Placements; Medium Bot: Will win if it has two in a row, or block you if you have two in a row; Hard Bot: Good Luck...
                      
                Made by Mateo Rodriguez - 10/30/2021
                        """,
                5, 50
        );
        ta.setFont(new Font("Arial", Font.PLAIN, asInt(getBestFontSize() * 0.4)));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        ta.setFocusable(true);

        JScrollPane sbrText = new JScrollPane(ta);
        sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        content.add(header, BorderLayout.NORTH);
        content.add(ta, BorderLayout.CENTER);
        init.add(content, BorderLayout.CENTER);

        this.setTitle("Tic Tac Toe Tutorial");
        this.add(init);
        this.setSize(asInt(DECIDED_SIZE * 0.95), asInt(DECIDED_SIZE * 1.15));
        this.setPreferredSize(new Dimension(asInt(DECIDED_SIZE * 0.95), asInt(DECIDED_SIZE * 1.15)));
        this.setMaximumSize(new Dimension(asInt(DECIDED_SIZE * 0.95), asInt(DECIDED_SIZE * 1.15)));
        this.setMinimumSize(new Dimension(asInt(DECIDED_SIZE * 0.95), asInt(DECIDED_SIZE * 1.15)));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                Tutorial.this.dispose();
                AudioReader.playSound("gn_button_click.wav");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                Tutorial.this.dispose();
                AudioReader.playSound("gn_button_click.wav");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                Tutorial.this.dispose();
                AudioReader.playSound("gn_button_click.wav");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                Tutorial.this.dispose();
                AudioReader.playSound("gn_button_click.wav");
            }
        });
        this.setIconImage(APP_ICON.getImage());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }

}
