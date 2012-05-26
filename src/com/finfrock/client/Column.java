package com.finfrock.client;

public interface Column
{
   public static final String TEXT_COLUMN_TYPE = "Text column type";
   public static final String WIDGET_COLUMN_TYPE = "Widget column type";
   
   String geValueType();

   boolean hasStyleName();

   int getIndex();
   
   Object getValue();

   String getStyleName();

}
