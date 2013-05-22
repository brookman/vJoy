package eu32k.vJoy.curveEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.TreeMap;

import eu32k.vJoy.curveEditor.DataPoint.InterPolationType;

public class Curve extends MousePanel {
   private static final long serialVersionUID = -5357691733950868730L;

   private int resolutionX;
   private int resolutionY;

   // private DataPoint currentPoint = null;
   private Integer currentPoint = null;

   // private ExtendedTreeSet<DataPoint> points = new ExtendedTreeSet<DataPoint>();
   private TreeMap<Integer, DataPoint> points = new TreeMap<Integer, DataPoint>();

   public Curve(int resolutionX, int resolutionY) {
      this.resolutionX = resolutionX;
      this.resolutionY = resolutionY;
   }

   @Override
   protected void pressed(int x, int y, int button) {
      synchronized (points) {
         int xPos = (int) Math.round(toInternalX(x));
         int yPos = (int) Math.round(toInternalY(y));
         DataPoint point = points.get(xPos);
         if (point == null) {
            point = new DataPoint(yPos);
            points.put(xPos, point);
         }
         currentPoint = xPos;
         point.y = yPos;

         if (button == MouseEvent.BUTTON3) {
            int ordinal = point.connectionType.ordinal();
            point.connectionType = InterPolationType.values()[(ordinal + 1) % InterPolationType.values().length];
         }
      }
      repaint();
   }

   @Override
   protected void released(int x, int y, int button) {
      synchronized (points) {
         if (currentPoint != null) {
            if (currentPoint < 0.0 || currentPoint > Curve.this.resolutionX || points.get(currentPoint).y < 0.0 || points.get(currentPoint).y > Curve.this.resolutionY) {
               points.remove(currentPoint);
               currentPoint = null;
            }
         }
      }
      repaint();
   }

   @Override
   protected void dragged(int x, int y, int button) {
      synchronized (points) {

         int newX = (int) Math.round(toInternalX(x));

         Integer higher = points.higherKey(currentPoint);
         if (higher != null) {
            if (newX >= higher) {
               newX = currentPoint;
            }
         }

         Integer lower = points.lowerKey(currentPoint);
         if (lower != null) {
            if (newX <= lower) {
               newX = currentPoint;
            }
         }

         DataPoint point = points.get(currentPoint);
         point.y = (int) Math.round(toInternalY(y));

         points.remove(currentPoint);
         points.put(newX, point);
         currentPoint = newX;
      }
      repaint();
   }

   private int toScreenX(double x) {
      return (int) Math.round(x / resolutionX * getWidth());
   }

   private int toScreenY(double y) {
      return getHeight() - (int) Math.round(y / resolutionY * getHeight());
   }

   private double toInternalX(int x) {
      return (double) x / (double) getWidth() * resolutionX;
   }

   private double toInternalY(int y) {
      return resolutionY - (double) y / (double) getHeight() * resolutionY;
   }

   @Override
   public Dimension getPreferredSize() {
      int height = 500;
      int width = (int) Math.round(height / (double) resolutionY * resolutionX);
      return new Dimension(width, height);
   }

   @Override
   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;

      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      g2.setColor(Color.WHITE);
      g2.clearRect(0, 0, getWidth(), getHeight());

      g2.setColor(Color.LIGHT_GRAY);
      g2.setStroke(new BasicStroke(1));
      for (int i = 0; i < resolutionX; i++) {
         int x = toScreenX(i);
         g2.drawLine(x, 0, x, getHeight());
      }
      for (int i = 0; i < resolutionY; i++) {
         int y = toScreenY(i);
         g2.drawLine(0, y, getWidth(), y);
      }

      g2.setStroke(new BasicStroke(1.3f));

      synchronized (points) {
         g2.setColor(Color.BLUE);
         Integer lastKey = null;
         for (Integer key : points.keySet()) {
            DataPoint current = points.get(key);
            g2.setColor(Color.BLUE);
            if (lastKey == null) {
               g2.drawLine(toScreenX(0.0), toScreenY(0.0), toScreenX(key), toScreenY(current.y));
            } else {
               // g2.drawLine(toScreenX(lastKey), toScreenY(points.get(lastKey).y), toScreenX(key), toScreenY(current.y));
               drawInterpolated(g2, lastKey, key, points.get(lastKey), current);
            }
            lastKey = key;
         }

         for (Integer key : points.keySet()) {
            DataPoint current = points.get(key);
            g2.setColor(Color.BLUE);
            if (key == currentPoint) {
               g2.setColor(Color.RED);
            }
            g2.fillOval(toScreenX(key) - 4, toScreenY(current.y) - 4, 8, 8);
         }
      }
   }

   private void drawInterpolated(Graphics2D g2, Integer x1, Integer x2, DataPoint p1, DataPoint p2) {
      int diff = x2 - x1;

      double lastX = x1;
      double lastY = p1.y;

      int totalPoints = diff * 4;
      double size = diff / (double) totalPoints;

      for (int i = 0; i <= totalPoints; i++) {
         double currentX = x1 + i * size;
         double currentY = p1.connectionType.getInterpolation().apply(p1.y, p2.y, (float) i / (float) totalPoints);

         g2.drawLine(toScreenX(lastX), toScreenY(lastY), toScreenX(currentX), toScreenY(currentY));
         lastX = currentX;
         lastY = currentY;
      }
   }
}
