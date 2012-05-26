package com.finfrock.client;


public class FacebookUser
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private long facebookUid;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public FacebookUser(long facebookUid)
   {
      this.facebookUid = facebookUid;
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   public long getId()
   {
      return facebookUid;
   }
   
   public String getName()
   {
      return "";
   }
}
