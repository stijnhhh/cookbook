package be.thomasmore.cookbook.ui.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    private long startTime = 0;

    private TextView countDownText;
    private Button buttonStart;
    private Button buttonReset;
    private ImageView minUp;
    private ImageView minDown;
    private ImageView secUp;
    private ImageView secDown;

    private CountDownTimer timer;

    private boolean active;

    private long timeLeft = startTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timerViewModel =
                ViewModelProviders.of(this).get(TimerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timer, container, false);

        countDownText = root.findViewById(R.id.text_view_timer);
        buttonStart = root.findViewById(R.id.button_start);
        buttonReset = root.findViewById(R.id.button_reset);
        secDown = root.findViewById(R.id.seconds_down);
        minDown = root.findViewById(R.id.minutes_down);
        secUp = root.findViewById(R.id.seconds_up);
        minUp = root.findViewById(R.id.minutes_up);

        active = false;
        buttonReset.setVisibility(View.INVISIBLE);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (active == true) {
                    stopTimer();
                } else
                {
                    startTimer();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        secUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSecond();
            }
        });

        secDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractSecond();
            }
        });

        minUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMinute();
            }
        });

        minDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractMinute();
            }
        });

        updateCountDownText();

        return root;
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();
                buttonStart.setText("pause");
                active = true;
                buttonReset.setVisibility(View.INVISIBLE);

                minUp.setVisibility(View.INVISIBLE);
                secUp.setVisibility(View.INVISIBLE);
                minDown.setVisibility(View.INVISIBLE);
                secDown.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFinish() {

            }
        }.start();

        buttonReset.setVisibility(View.INVISIBLE);
    }

    private void stopTimer() {
        timer.cancel();

        buttonStart.setText("start");
        active = false;
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        timeLeft = startTime;
        updateCountDownText();
        buttonReset.setVisibility(View.INVISIBLE);

        minUp.setVisibility(View.VISIBLE);
        secUp.setVisibility(View.VISIBLE);
        minDown.setVisibility(View.VISIBLE);
        secDown.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) timeLeft / 1000 / 60;
        int seconds = (int) timeLeft / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        countDownText.setText(timeLeftFormatted);
    }

    private void addSecond() {
        startTime = startTime + 1000;
        timeLeft = startTime;

        updateCountDownText();
    }

    private void addMinute() {
        startTime = startTime + 60000;
        timeLeft = startTime;

        updateCountDownText();
    }

    private void subtractSecond() {
        int seconds = (int) timeLeft / 1000 % 60;

        if (seconds != 0)
        {
            startTime = startTime - 1000;
            timeLeft = startTime;
        }

        updateCountDownText();
    }

    private void subtractMinute() {
        int minutes = (int) timeLeft / 1000 / 60;

        if (minutes != 0)
        {
            startTime = startTime - 60000;
            timeLeft = startTime;
        }

        updateCountDownText();
    }
}