package com.finfrock.client;

public interface Loadable
{
   void startLoad(final Returnable<Loadable> returnMethed);
   
   boolean loaded();
}
