package com.ngtkn.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    boolean playerTurn = true;
    boolean gameOver = false;
    // gameState used to track moves on board 0: player1, 1: player2, and 2: empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    // array hold the possible winning positions
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6},
            {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Place a game piece on the board
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int boardPosition = Integer.parseInt(counter.getTag().toString());

        if (gameState[boardPosition] == 2 && gameOver == false) {
            if (playerTurn) {
                counter.setImageResource(R.drawable.x);
                gameState[boardPosition] = 0;

            } else {
                counter.setImageResource(R.drawable.o);
                gameState[boardPosition] = 1;
            }
            counter.setTranslationY(-1500);
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            checkGameStatus();

            playerTurn = !playerTurn;
        }
    }

    // Checks if a winner has been determined
    public void checkGameStatus(){
        for (int[] winningPosition: winningPositions){
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2){
                String winner = (playerTurn) ? "Player 1" : "Player 2";
                gameOver = true;

                showResult(winner + " has won!");
                return;
            }
        }

        // Checks if game is tied,
        for(int i = 0; i < gameState.length; i++){
            if (gameState[i] == 2){
                return;
            }
        }
        showResult("Tie game.");
    }

    // Displays the result of the game
    public void showResult(String message) {
        Button playButton = findViewById(R.id.playButton);
        TextView resultTextView = findViewById(R.id.resultTextView);

        resultTextView.setText(message);
        resultTextView.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.VISIBLE);
    }

    // Reset and start a new game when button is pressed
    public void playAgain(View view){
        Button playButton = findViewById(R.id.playButton);
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        playerTurn = true;
        gameOver = false;

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ImageView child = (ImageView) gridLayout.getChildAt(i);
            child.setImageDrawable(null);
        }

        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }
    }
}
