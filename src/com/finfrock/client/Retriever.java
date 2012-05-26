package com.finfrock.client;

public abstract class Retriever<T> extends SenderAndReceiver 
  implements Loadable
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private T object;
   
   private Returnable<Loadable> returnable;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public Retriever(String address)
   {
       super(address);
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public T getObject()
   {
      return object;
   }

   // --------------------------------------------------------------------------
   // Loadable Members
   // --------------------------------------------------------------------------
   
   @Override
   public void startLoad(final Returnable<Loadable> returnable)
   {
     this.returnable = returnable;
     
     send();
   }

   // --------------------------------------------------------------------------
   // Overridden Members
   // --------------------------------------------------------------------------
   
   @Override
   protected String getData()
   {
     return getRequestData();
   }
   
   @Override
   protected void reponse(String reponse)
   {
     object = parseText(reponse);
   
     returnable.returned(Retriever.this);
   }
   
   // --------------------------------------------------------------------------
   // Abstract Members
   // --------------------------------------------------------------------------
  
   protected abstract T parseText(String rawText);

   protected String getRequestData()
   {
      return "";
   }
}
