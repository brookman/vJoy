package eu32k.vJoy.common.workset;

import java.util.ArrayList;
import java.util.List;

public abstract class Type {

   private String name;
   private int dataType;
   private List<Port> ports = new ArrayList<Port>();

   public Type(String name, int dataType) {
      this.name = name;
      this.dataType = dataType;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Instance instanciate() {
      return instanciate(0, 0);
   }

   public Instance instanciate(float x, float y) {
      return new Instance(this, x, y);
   }

   public int getDataType() {
      return dataType;
   }

   public void setDataType(int dataType) {
      this.dataType = dataType;
   }

   public List<Port> getPorts() {
      return ports;
   }

   public BooleanPort addPort(BooleanPort port) {
      ports.add(port);
      return port;
   }

   public NumberPort addPort(NumberPort port) {
      ports.add(port);
      return port;
   }

   public ImagePort addPort(ImagePort port) {
      ports.add(port);
      return port;
   }

   @Override
   public String toString() {
      return name;
   }
}
