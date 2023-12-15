package main;
import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {

        JFrame context = new JFrame();
        context.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        context.setResizable(false);
        context.setTitle("Game");

        GamePanel gamepanel = new GamePanel();
        context.add(gamepanel);
        context.pack();

        context.setLocationRelativeTo(null);
        context.setVisible(true);

        gamepanel.startGameThread();

    }
}
