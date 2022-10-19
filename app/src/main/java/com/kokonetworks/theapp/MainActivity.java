package com.kokonetworks.theapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;


public class MainActivity extends AppCompatActivity {

    private Field field;
    private TextView tvLevel;
    private TextView tvScore;
    private TextView tvTopScore;
    private Button btnStart;
    private Button btnShowTop;
    private TreeSet<Integer> topScore = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field = findViewById(R.id.field);
        tvLevel = findViewById(R.id.tvLevel);
        tvTopScore = findViewById(R.id.tvTopScore);
        btnStart = findViewById(R.id.btnStart);
        btnShowTop = findViewById(R.id.btnShowTop);
        tvScore = findViewById(R.id.tvScore);

        setEventListeners();
    }

    void setEventListeners() {
        btnStart.setOnClickListener(view -> {
            btnStart.setVisibility(View.GONE);
            tvScore.setVisibility(View.GONE);
            field.startGame();
        });
        btnShowTop.setOnClickListener(view -> {
            NavigableSet<Integer> temp = topScore.descendingSet();
            Iterator<Integer> i = temp.iterator();
            StringBuilder scores = new StringBuilder();
            while (i.hasNext()) {
                scores.append(i.next()).append("\n");
            }
            tvTopScore.setText(scores.toString());
        });
        field.setListener(listener);
    }

    private final Field.Listener listener = new Field.Listener() {

        @Override
        public void onUpdateScore(int score) {
            tvScore.setVisibility(View.VISIBLE);
            tvScore.setText(String.format(getString(R.string.your_score), score));
        }

        @Override
        public void onGameEnded(int score) {
            btnStart.setVisibility(View.VISIBLE);
            tvScore.setVisibility(View.VISIBLE);
            tvScore.setText(String.format(getString(R.string.your_score), score));
            topScore.add(score);
        }

        @Override
        public void onLevelChange(int level) {
            tvLevel.setText(String.format(getString(R.string.level), level));
        }
    };
}