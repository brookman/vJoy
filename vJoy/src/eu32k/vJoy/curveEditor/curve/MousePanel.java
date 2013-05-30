package eu32k.vJoy.curveEditor.curve;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class MousePanel extends JPanel {
   private static final long serialVersionUID = 2837690364498308567L;

   public MousePanel() {
      addMouseListener(new MouseListener() {

         @Override
         public void mouseReleased(MouseEvent e) {
            released(e.getX(), e.getY(), e.getButton());
         }

         @Override
         public void mousePressed(MouseEvent e) {
            pressed(e.getX(), e.getY(), e.getButton());
         }

         @Override
         public void mouseExited(MouseEvent e) {
            // NOP
         }

         @Override
         public void mouseEntered(MouseEvent e) {
            // NOP
         }

         @Override
         public void mouseClicked(MouseEvent e) {
            // NOP
         }
      });

      addMouseMotionListener(new MouseMotionListener() {

         @Override
         public void mouseMoved(MouseEvent e) {
            // NOP
         }

         @Override
         public void mouseDragged(MouseEvent e) {
            dragged(e.getX(), e.getY(), e.getButton());
         }
      });
   }

   protected void pressed(int x, int y, int button) {
      // NOP
   }

   protected void released(int x, int y, int button) {
      // NOP
   }

   protected void dragged(int x, int y, int button) {
      // NOP
   }

}
