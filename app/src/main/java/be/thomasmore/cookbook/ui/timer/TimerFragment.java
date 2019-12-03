package be.thomasmore.cookbook.ui.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Locale;

import be.thomasmore.cookbook.R;

public class TimerFragment extends Fragment {

    private TimerViewModel timerViewModel;
    private static final long startTime = 60000;

    private TextView countDown;
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonReset;

    private CountDownTimer timer;

    private boolean active;

    private long timeLeft = startTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timerViewModel =
                ViewModelProviders.of(this).get(TimerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        //final TextView textView = root.findViewById(R.id.text_share);
        //timerViewModel.getText().observe(this, new Observer<String>() {
        //    @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
        //    }
        //});
        countDown = (TextView) getView().findViewById(R.id.text_view_timer);
        buttonStart = (Button) getView().findViewById(R.id.button_start);
        buttonStop = (Button) getView().findViewById(R.id.button_stop);
        buttonReset = (Button) getView().findViewById(R.id.button_reset);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        return root;
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();


    }

    private void stopTimer() {

    }

    private void resetTimer() {

    }

    private void updateCountDownText() {
        int minutes = (int) timeLeft / 1000 / 60;
        int seconds = (int) timeLeft / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        countDown.setText(timeLeftFormatted);
    }
}