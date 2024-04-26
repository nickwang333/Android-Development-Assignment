package edu.northeastern.numad24sp_zhuofengwang;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import androidx.appcompat.app.AlertDialog;


public class PrimeActivity extends AppCompatActivity {

    private Handler textHandler = new Handler();
    private static TextView currPrime;
    private PrimeSearcherThread thread1;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int checked = 0;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Store the state of the check box
        CheckBox checkBox = findViewById(R.id.pacifierCheckBox);
        boolean isChecked = checkBox.isChecked();
        outState.putBoolean("checkbox_state", isChecked);

        if(thread1 != null) {
            Log.v("th1", "not empty");
            lock.readLock().lock();
            try {
                outState.putInt("currentSearchNumber", thread1.curNum);
                outState.putInt("latestPrime", thread1.lastPrime);
                outState.putInt("checkedBox", checked);
                if(thread1.isStop) {
                    outState.putInt("isStop", 1);
                }
                else{
                    outState.putInt("isStop", 0);
                }
            } finally {
                lock.readLock().unlock();
                thread1.isStop = true;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);
        thread1 = new PrimeSearcherThread();
        currPrime = findViewById(R.id.currentPrimeText);

        if (savedInstanceState != null) {
            boolean isChecked = savedInstanceState.getBoolean("checkbox_state");
            CheckBox checkBox = findViewById(R.id.pacifierCheckBox);
            checkBox.setChecked(isChecked);

            // Restore the states
            lock.writeLock().lock();
            try {
                thread1.curNum = savedInstanceState.getInt("currentSearchNumber");
                thread1.lastPrime = savedInstanceState.getInt("latestPrime");
            }
            finally {
                if(savedInstanceState.getInt("isStop") == 0) {
                    thread1.isStop = false;
                    new Thread(thread1).start();
                }
                lock.writeLock().unlock();
            }
        }


        findViewById(R.id.findPrimesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread1.curNum = 3;
                thread1.lastPrime = 3;
                thread1.isStop = false;
                new Thread(thread1).start();
            }
        });

        findViewById(R.id.primeTerminateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread1.isStop = true;
                Log.v(String.valueOf(thread1.curNum),String.valueOf(thread1.lastPrime));
                textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        currPrime.setText("Latest Prime found: " + thread1.lastPrime + "\nCurrent number being checking: " + thread1.curNum );
                    }
                });
            }
        });

        findViewById(R.id.pacifierCheckBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = 1 - checked;
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (!thread1.isStop) {
                    new AlertDialog.Builder(PrimeActivity.this)
                            .setMessage("Are you sure you want to stop the search and exit?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                thread1.isStop = true;
                                finish();
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    setEnabled(false);
                    PrimeActivity.this.onBackPressed();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

    }



    public class PrimeSearcherThread implements Runnable{
        public int lastPrime = 3;
        public int curNum = 3;
        private final int showFreq = 2001;
        public boolean isStop = true;

        @Override
        public void run() {
            while(!isStop){
                curNum += 2;
                if (isPrime(curNum)) {
                    lastPrime = curNum;
                }
                if(curNum % showFreq == 2){


                    textHandler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            currPrime.setText("Latest Prime found: " + curNum + "\nCurrent number being checking: " + curNum );
                        }
                    });
                }
            }
        }

        private boolean isPrime(int prime){
            int tmp = prime;
            for(int i=3; i<tmp; i+=2){
                if(tmp % i == 0){
                    return false;
                }
            }
            return true;
        }
    }
}
