package com.finfrock.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class FbBookmarkButton extends Widget
{
   public FbBookmarkButton()
   {
      super.setElement(DOM.createElement("fb:bookmark"));
   }
}
