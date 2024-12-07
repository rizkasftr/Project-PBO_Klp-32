import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer {
    private TimerListener timerListener;
    private JLabel timerLabel;
    private int seconds;
    private javax.swing.Timer timer;

    public Timer(int interval) {
        this.seconds = 60; // Waktu awal 60 detik
        this.timerLabel = new JLabel("Waktu: " + seconds + " detik");
        this.timer = new javax.swing.Timer(1000, new ActionListener() { // Timer berjalan setiap 1 detik
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds--;
                timerLabel.setText("Waktu: " + seconds + " detik");
                if (seconds <= 0) {
                    stopTimer(); // Hentikan timer ketika waktu habis
                    if (timerListener != null) {
                        timerListener.onTimeUp(); // Panggil listener jika waktu habis
                    }
                }
            }
        });
    }

    public JLabel getLabel() {
        return timerLabel;
    }

    public void start() {
        if (timer != null) {
            timer.start();
        }
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void resetTimer() {
        stopTimer();
        seconds = 60; // Reset waktu ke 60 detik
        timerLabel.setText("Waktu: " + seconds + " detik");
    }

    public void setTimerListener(TimerListener listener) {
        this.timerListener = listener;
    }

    public interface TimerListener {
        void onTimeUp();
    }
}
