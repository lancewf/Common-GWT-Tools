package com.finfrock.client;

import java.util.ArrayList;
import java.util.List;

public class Row
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private List<Column> columns = new ArrayList<Column>();
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public List<Column> getColumns()
   {
      return columns;
   }
   
   public void addColumn(Column column)
   {
      columns.add(column);
   }
}
