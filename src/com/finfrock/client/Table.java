package com.finfrock.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public abstract class Table extends FlexTable implements ClickHandler
{
   private boolean isInialized = false;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public Table()
   {
      addStyleName("table");
   }
   
   // --------------------------------------------------------------------------
   // ClickHandler Members
   // --------------------------------------------------------------------------
   
   @Override
   public void onClick(ClickEvent event)
   {
      Cell cellClicked = getCellForEvent(event);

      if (cellClicked != null)
      {
         if (cellClicked.getRowIndex() == 0)
         {
            columnHeaderClicked(cellClicked.getCellIndex());                 
         }
      }
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public void updateTable()
   {
      if(!isInialized)
      {
         Row headerRow = createHeaderRow();
         
         addRow(headerRow, 0);
         
         isInialized = true;
      }
      
      clearTable();
      
      for (Row row : getRows())
      {
         int newRow = getRowCount();
         
         addRow(row, newRow);
      }
   }
   
   protected void columnHeaderClicked(int column)
   {
      //
      // Not implemented
      //
   }
   
   // --------------------------------------------------------------------------
   // Abstract Members
   // --------------------------------------------------------------------------
   
   protected abstract List<Row> getRows();

   protected abstract Row getHeaderRow();
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private void addRow(Row row, int newRowIndex)
   {
      for(Column column : row.getColumns())
      {
         if(column.geValueType() == Column.TEXT_COLUMN_TYPE)
         {
            setText(newRowIndex, column.getIndex(), (String)column.getValue());
         }
         else if(column.geValueType() == Column.WIDGET_COLUMN_TYPE)
         {
            setWidget(newRowIndex, column.getIndex(), (Widget)column.getValue());
         }

         if(column.hasStyleName())
         {
            getCellFormatter().addStyleName(newRowIndex, 
               column.getIndex(), column.getStyleName());
         }
      }
      
      if(isEvenRow(newRowIndex))
      {
         getRowFormatter().addStyleName(newRowIndex, "alternate");
      }
   }
   
   /**
    * clear all rows but the first row the header row.
    */
   private void clearTable()
   {
      while (getRowCount() > 1)
      {
         removeRow(1);
      }
   }
   
   private boolean isEvenRow(int rowIndex)
   {
      if ((rowIndex % 2) == 0)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   private Row createHeaderRow()
   {
      getRowFormatter().addStyleName(0, "tableHeader");
      addClickHandler(this);
      
      Row headerRow = getHeaderRow();
      
      return headerRow;
   }
}
