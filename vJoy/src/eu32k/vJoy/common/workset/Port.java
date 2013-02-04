package eu32k.vJoy.common.workset;

import java.io.Serializable;

public class Port implements Serializable {
   private static final long serialVersionUID = 6274602646420185844L;

   private String name;
   private DataType dataType;

   public Port(String name, DataType dataType) {
      this.name = name;
      this.dataType = dataType;
   }

   public DataType getDataType() {
      return dataType;
   }

   public void setDataType(DataType dataType) {
      this.dataType = dataType;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
