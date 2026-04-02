import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Floating_Timr extends JFrame {

    private JLabel timeLabel;
    private JLabel infoLabel;
    private Point initialClick;
    private SimpleDateFormat timeFormat;

    public Floating_Timr() {
        // Set up the undecorated Frame
        setUndecorated(true);
        // Make the background transparent (Red=0, Green=0, Blue=0, Alpha=0)
        setBackground(new Color(0, 0, 0, 0));
        // Keep it always on top
        setAlwaysOnTop(true);
        
        setLayout(new BorderLayout());

        // Initialize time format
        timeFormat = new SimpleDateFormat("HH:mm:ss");

        // Set up time label
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Consolas", Font.BOLD, 48));
        timeLabel.setForeground(new Color(0, 255, 204)); // Cyan color
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(timeLabel, BorderLayout.CENTER);

        // Set up instruction label
        infoLabel = new JLabel("Left-click drag: Move | Right-click: Close");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        infoLabel.setForeground(new Color(200, 200, 200));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(infoLabel, BorderLayout.SOUTH);

        // Update time initially and set up a timer to update every second
        updateTime();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        // Mouse listeners for dragging the window & right-click closing
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    System.exit(0); // Close app on right click
                } else {
                    initialClick = e.getPoint();
                }
            }
        };

        MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (initialClick != null && !SwingUtilities.isRightMouseButton(e)) {
                    // get location of Window
                    int thisX = getLocation().x;
                    int thisY = getLocation().y;

                    // Determine how much the mouse moved since the initial click
                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;

                    // Move window to this position
                    int X = thisX + xMoved;
                    int Y = thisY + yMoved;
                    setLocation(X, Y);
                }
            }
        };

        // Add listeners to frame and labels
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseMotionAdapter);
        timeLabel.addMouseListener(mouseAdapter);
        timeLabel.addMouseMotionListener(mouseMotionAdapter);
        infoLabel.addMouseListener(mouseAdapter);
        infoLabel.addMouseMotionListener(mouseMotionAdapter);

        // Pack the frame to its components
        pack();

        // Center on screen
        setLocationRelativeTo(null);
    }

    private void updateTime() {
        timeLabel.setText(timeFormat.format(new Date()));
    }

    public static void main(String[] args) {
        // Ensure GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Floating_Timr app = new Floating_Timr();
                app.setVisible(true);
            }
        });
    }
}
