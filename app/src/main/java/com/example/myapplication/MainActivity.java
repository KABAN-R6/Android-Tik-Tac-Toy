package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LinearLayout board;
    private ArrayList<Button> squares = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            View.OnClickListener listener = (view)->{Button btn = (Button) view;
                if(!btn.getText().toString().equals("")) return;
                if(GameInfo.isTurn) {
                    btn.setText(GameInfo.firstSymbol);
                    int [] comb = calcWinnPositions(GameInfo.firstSymbol);
                    if(comb != null)
                    {
                        Toast.makeText(
                                getApplicationContext(),
                                "winner "+GameInfo.firstSymbol,
                                Toast.LENGTH_LONG).show();
                        winers(comb);
                    }

                }
                else {
                    btn.setText(GameInfo.secondSymbol);
                    int [] comb = calcWinnPositions(GameInfo.secondSymbol);
                    if(comb != null)
                    {
                        Toast.makeText(
                                getApplicationContext(),
                                "winner is "+GameInfo.secondSymbol,
                                Toast.LENGTH_LONG).show();
                        winers(comb);

                    }

                }
                GameInfo.isTurn = !GameInfo.isTurn;

            };
            board = findViewById(R.id.board);
            generateBoard(3,3,board);
            setListenerToSquares(listener);
            initClearBordBtn();
        }

        private void initClearBordBtn(){
            Button clearBtn = findViewById(R.id.clear_board_value);
            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Новая игра",
                            Toast.LENGTH_LONG).show();
                    for(Button square: squares) {
                        square.setText("");
                        square.setBackgroundTintList(
                                ContextCompat.getColorStateList(
                                        getApplicationContext(),
                                        R.color.white));
                    }
                }
            });
        }

        public void generateBoard(int rowCount, int columnCount, LinearLayout board){
            for(int row = 0; row < rowCount;row++){
                LinearLayout rowContainer = generateRow(columnCount);
                board.addView(rowContainer);
            }
        }
        private void setListenerToSquares(View.OnClickListener listener){
            for(int i = 0; i < squares.size();i++)
                squares.get(i).setOnClickListener(listener);
        }
        private LinearLayout generateRow(int squaresCount){
            LinearLayout rowContainer = new LinearLayout(getApplicationContext());
            rowContainer.setOrientation(LinearLayout.HORIZONTAL);
            rowContainer.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            for(int square = 0; square < squaresCount;square++){
                Button button = new Button(getApplicationContext());
                button.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                                getApplicationContext(),
                                R.color.white));
                button.setWidth(convertToPixel(50));
                button.setHeight(convertToPixel(90));
                rowContainer.addView(button);
                squares.add(button);
            }
            return rowContainer;
        }
        public int convertToPixel(int digit){
            float density = getApplicationContext()
                    .getResources().getDisplayMetrics().density;
            return (int)(digit * density + 0.5);
        }
        public int [] calcWinnPositions(String symbol){
            for(int i = 0; i < GameInfo.winCombination.length;i++){
                boolean findComb = true;
                for(int j = 0; j < GameInfo.winCombination[0].length;j++){
                    int index = GameInfo.winCombination[i][j];//0, 1, 2
                    if (!squares.get(index).getText().toString().equals(symbol)) {
                        findComb = false;
                        break;
                    }
                }
                if(findComb) return GameInfo.winCombination[i];
            }
            return null;

        }
        public void winers(int[]winCombo)
        {
            for (int i = 0 ; i<winCombo.length;i++)
            {
                squares.get(winCombo[i]).setBackgroundTintList(ContextCompat.getColorStateList(
                        getApplicationContext(),
                        R.color.green));
            }
        }
}