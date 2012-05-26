package com.finfrock.client;

import com.finfrock.client.Loadable;
import com.finfrock.client.Returnable;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

/**
 * Warning if this class is changed in anyway test the code on the Android OS 1.5
 * cupcake. 
 * 
 * @author lancewf
 *
 * @param <T>
 */
public abstract class RetrieverAndroid15<T> implements Loadable
{
  // --------------------------------------------------------------------------
  // Private Data
  // --------------------------------------------------------------------------
 
  private Request request;
  
  private T object;
  
  private String address;
  
  // --------------------------------------------------------------------------
  // Constructor
  // --------------------------------------------------------------------------

  public RetrieverAndroid15(String address)
  {
     this.address = address;
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
  public boolean loaded()
  {
     return request.isPending();
  }
  
  @Override
  public void startLoad(final Returnable<Loadable> returnable)
  {
     String url = URL.encode(address);
     
     RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

     try
     {
        request = builder.sendRequest("", new RequestCallback()
        {
           public void onError(Request request, Throwable exception){}

           public void onResponseReceived(Request request, Response response)
           {
              String rawText = response.getText();
              
              object = parseText(rawText);
            
              returnable.returned(RetrieverAndroid15.this);
           }
        });
     }
     catch (RequestException e)
     {
        //Window.alert(e.getMessage());
     }  
  }
  
  // --------------------------------------------------------------------------
  // Abstract Members
  // --------------------------------------------------------------------------
  
  protected abstract T parseText(String rawText);
}
