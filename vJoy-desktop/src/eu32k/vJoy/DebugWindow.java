package eu32k.vJoy;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class DebugWindow extends JFrame {

   private JTextArea area;

   public DebugWindow() {
      // super("VJoy Debug");
      // setLayout(new BorderLayout());
      // area = new JTextArea();
      // area.setEditable(false);
      // area.setFont(new Font("Courier New", 0, 10));
      // add(new JScrollPane(area), BorderLayout.CENTER);
      // setSize(800, 600);
      // setLocationRelativeTo(null);
      // setVisible(true);
      //
      // new Thread(new Runnable() {
      //
      // @Override
      // public void run() {
      // while (true) {
      // try {
      // byte[] data = Serializer.serialize(ArchitectStage.workset);
      // String str = new String(data);
      // area.setText(str);
      // System.out.println(str);
      // Workset wtf = (Workset) Serializer.deserialize(data);
      // } catch (IOException e1) {
      // // TODO Auto-generated catch block
      // e1.printStackTrace();
      // } catch (ClassNotFoundException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      //
      // try {
      // Thread.sleep(1000);
      // } catch (InterruptedException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      // }
      // }
      // }).start();
   }
}
