package com.finfrock.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

public abstract class SenderAndReceiver 
{
  // --------------------------------------------------------------------------
  // Private Data
  // --------------------------------------------------------------------------

  private String address;
  private Request request;
  
  // --------------------------------------------------------------------------
  // Constructor
  // --------------------------------------------------------------------------

  public SenderAndReceiver(String address)
  {
     this.address = address;
  }
  
  // --------------------------------------------------------------------------
  // Public Members
  // --------------------------------------------------------------------------

  public void send()
  {
     String url = URL.encode(address);
     
     RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

     builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
     
     String requestData = getData();
     
     try
     {
       request = builder.sendRequest(requestData, new RequestCallback()
        {
           public void onError(Request request, Throwable exception)
           {
               //Window.alert("onError: " + exception.getMessage());
           }

           public void onResponseReceived(Request request, Response response)
           {
             //Window.alert(response.getText());
             reponse(response.getText());
           }
        });
     }
     catch (RequestException e)
     {
         Window.alert(e.getMessage());
     } 
  }
  
  public boolean loaded()
  {
     return request.isPending();
  }
  
  // --------------------------------------------------------------------------
  // Abstract Members
  // --------------------------------------------------------------------------
  
  protected abstract void reponse(String reponse);
  
  protected abstract String getData();
}
