package com.finfrock.client;

import java.util.ArrayList;
import java.util.List;

import com.finfrock.client.DataChangeListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.ApiFactory;
import com.gwittit.client.facebook.ConnectState;
import com.gwittit.client.facebook.FacebookApi;
import com.gwittit.client.facebook.FacebookConnect;
import com.gwittit.client.facebook.LoginCallback;
import com.gwittit.client.facebook.xfbml.FbLoginButton;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;

public class FacebookConnectionStatus
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------

   private List<DataChangeListener> dataChangeListeners = new ArrayList<DataChangeListener>();

   private boolean isConnected = false;

   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------

   public FacebookConnectionStatus()
   {
      checkStatus();
      
      defineLoginCallbackFunction(new LoginCallback() 
      {
        @Override
        public void onLogin() 
        {
          userLoggedIn();
        }
      });
   }

   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------

   /**
    * Define a javascript function wich is called by facebook when a user logs
    * in.
    * 
    * @param callback
    */
   public static native void defineLoginCallbackFunction(LoginCallback callback) 
   /*-{
       $wnd.facebookConnectLogin = function() {
           @com.gwittit.client.facebook.FacebookConnect::onLoginProxy(Lcom/gwittit/client/facebook/LoginCallback;)(callback);
       };
   }-*/;
   
   public boolean isloggedin()
   {
      return isConnected;
   }
   
   public void logoutAndRedirect()
   {
      FacebookConnect.logoutAndRedirect(GWT.getHostPageBaseURL());
   }

   public void addLoggedinListener(DataChangeListener dataChangeListener)
   {
      dataChangeListeners.add(dataChangeListener);
   }

   public void removeLoggedinListener(DataChangeListener dataChangeListener)
   {
      dataChangeListeners.remove(dataChangeListener);
   }
   
   public void logoutOfFacebook()
   {
       FacebookConnect.logoutAndRedirect(GWT.getHostPageBaseURL());
   }

   public void connectToFacebook()
   {
      if (!isloggedin())
      {
         FacebookConnect.requireSession(new AsyncCallback<Boolean>()
         {
            public void onFailure(Throwable caught)
            {
              Window.alert("Connect to Facebook Error : " + caught.getMessage());
            }

            public void onSuccess(Boolean result)
            {
              userLoggedIn();
            }
         });
      }
   }
   
   /*
    * Do actuall call to facebook.
    */
   public static native boolean publishToUserWall(String title, String tileHref, 
           String caption, String imageSource, String imageHref, String userPromptMessage, 
           String actionLinkText, String actionLinkHref) 
   /*-{ 
        var attachment = {'name' : title,
                          'caption' : caption,
                          'href' : tileHref, // a link on the name 
                          'media': [{'type':'image',
                                   'src':imageSource,
                                   'href':imageHref}] // link on image.
                          };  
                          
        var actionLinks = [{ "text": actionLinkText, "href": actionLinkHref}]               
                             
        var success = $wnd.FB.Connect.streamPublish('', attachment, actionLinks, 
          null, userPromptMessage);
        
        return success;
   }-*/;
 
   public FacebookApi getApiClient()
   {
       return ApiFactory.getInstance();
   }
   
   public long getFacebookUserId()
   {
      return getApiClient().getLoggedInUser();
   }

   public Widget createProfilePicture() 
   {
     FbProfilePic fbProfilePic = new FbProfilePic();

     Xfbml.parse(fbProfilePic);

     return fbProfilePic;
   }
   
   public Widget createLoginButton()
   {
     FbLoginButton button = new FbLoginButton();
     
     button.setAutoLogoutLink(true);
     
     Xfbml.parse(button);
     
     return button;
   }
   
   public Widget createShareButton(String url)
   {
      FbShareButton shareButton = new FbShareButton(url);
      
      return shareButton;
   }
   
   public Widget createProfilePicture(long uid) 
   {
     FbProfilePic fbProfilePic = new FbProfilePic(uid);

     Xfbml.parse(fbProfilePic);

     return fbProfilePic;
   }
   
   public Widget createProfilePicture(FacebookUser facebookUser) 
   {
     FbProfilePic fbProfilePic = new FbProfilePic(facebookUser.getId());

     Xfbml.parse(fbProfilePic);

     return fbProfilePic;
   }
   
   /**
    * This button currently does not show up. 
    * @return
    */
   public Widget createBookmarkButton()
   {
      FbBookmarkButton bookmarkButton = new FbBookmarkButton();

      Xfbml.parse(bookmarkButton);

      return bookmarkButton;
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private void userLoggedIn()
   {
      isConnected = true;
      notifyOfLoggin();
   }
   
   private void userLoggedOut()
   {
      isConnected = false;
   }
   
   private void notifyOfLoggin()
   {
      for (DataChangeListener dataChangeListener : dataChangeListeners)
      {
         dataChangeListener.onDataChange();
      }
   }

   private void checkStatus()
   {
      FacebookConnect.waitUntilStatusReady(new AsyncCallback<ConnectState>()
      {
         @Override
         public void onFailure(Throwable caught)
         {
            Window.alert("CheckStatus Error: " + caught.getMessage());
         }

         @Override
         public void onSuccess(ConnectState result)
         {
            if (result == ConnectState.connected)
            {
               userLoggedIn();
            }
            else
            {
               userLoggedOut();
            }
         }
      });
   }
}
