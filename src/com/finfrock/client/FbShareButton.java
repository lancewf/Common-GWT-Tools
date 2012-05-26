package com.finfrock.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class FbShareButton extends Widget
{
   public FbShareButton(String url)
   {
      super.setElement(DOM.createElement("fb:share-button"));

      getElement().setAttribute("class", "url");
      getElement().setAttribute("href", url);
      getElement().setAttribute("type", "button");
   }
}
