package eu32k.vJoy.common.workset;

public class Port {

   private String name;
   private int dataType;

   public Port(String name, int dataType) {
      this.name = name;
      this.dataType = dataType;
   }

   public int getDataType() {
      return dataType;
   }

   public void setDataType(int dataType) {
      this.dataType = dataType;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
