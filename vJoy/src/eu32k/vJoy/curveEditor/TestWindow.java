package eu32k.vJoy.curveEditor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class TestWindow extends JFrame {

   public TestWindow() {
      super("");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      add(new JScrollPane(new Curve(600, 20)));
      pack();
      setSize(1200, 550);
      setLocationRelativeTo(null);
      setVisible(true);
   }

   public static void main(String[] args) {
      new TestWindow();
   }
}
