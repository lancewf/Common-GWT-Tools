package com.finfrock.client;

import com.finfrock.client.Column;

public class CommonColumn implements Column
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private String valueType;
   private int index;
   private String styleName;
   private Object value;
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public void setValueType(String valueType)
   {
      this.valueType = valueType;
   }
   
   public void setIndex(int index)
   {
      this.index = index;
   }
   
   public void setStyleName(String styleName)
   {
      this.styleName = styleName;
   }
   
   public void setValue(Object value)
   {
      this.value = value;
   }
   
   // --------------------------------------------------------------------------
   // CommonColumn Members
   // --------------------------------------------------------------------------

   @Override
   public String geValueType()
   {
      return valueType;
   }

   @Override
   public int getIndex()
   {
      return index;
   }

   @Override
   public String getStyleName()
   {
      return styleName;
   }

   @Override
   public Object getValue()
   {
      return value;
   }

   @Override
   public boolean hasStyleName()
   {
      if(styleName != null)
      {
         return true;
      }
      else
      {
         return false;
      }
   }

}
