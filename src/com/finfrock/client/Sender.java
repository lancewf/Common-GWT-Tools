package com.finfrock.client;

import java.util.ArrayList;
import java.util.List;

public abstract class Sender extends SenderAndReceiver
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private List<Returnable<Boolean>> reponseReturnables = 
     new ArrayList<Returnable<Boolean>>();
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public Sender(String address)
   {
     super(address);
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public void addReturnableListener(Returnable<Boolean> reponseReturnable)
   {
     reponseReturnables.add(reponseReturnable);
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------

   @Override
   protected void reponse(String reponse)
   {
      Boolean reponseSuccess = Boolean.FALSE;
     
      if(reponse != null && reponse.equals("Success"))
      {
        reponseSuccess = Boolean.TRUE;
      }
      else
      {
        reponseSuccess = Boolean.FALSE;
      }
      
      for(Returnable<Boolean> reponseReturnable : reponseReturnables)
      {
        reponseReturnable.returned(reponseSuccess);
      }
   }
}
