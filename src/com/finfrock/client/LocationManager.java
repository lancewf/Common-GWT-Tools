package com.finfrock.client;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.user.client.Timer;

public class LocationManager
{
   // --------------------------------------------------------------------------
   // Private Data
   // --------------------------------------------------------------------------
   
   private double latitude;
   private double longitude;
   private boolean locationRetrieved = false;
   
   // --------------------------------------------------------------------------
   // Constructor
   // --------------------------------------------------------------------------
   
   public LocationManager()
   {
      latitude = 0;
      longitude = 0;
      
      if (Geolocation.isSupported())
      {
         findLocation();
         
         Timer t = new Timer()
         {
            public void run()
            {
               findLocation();
            }
         };

         // Schedule the timer to run every 15 minutes.
         t.scheduleRepeating(900000);
      }
   }
   
   // --------------------------------------------------------------------------
   // Public Members
   // --------------------------------------------------------------------------
   
   public boolean isLocationRetrieved()
   {
      return locationRetrieved;
   }
   
   public boolean isSupported()
   {
      return Geolocation.isSupported();
   }
   
   public double getLatitude()
   {
      return latitude;
   }
   
   public double getLongitude()
   {
      return longitude;
   }
   
   public double findDistanceFrom(double toLatitude, double toLongitude)
   {
      double distance = 
         getDistance(latitude, longitude, toLatitude, toLongitude);
      
      return distance;
   }
   
   // --------------------------------------------------------------------------
   // Private Members
   // --------------------------------------------------------------------------
   
   private void findLocation()
   {
      Geolocation geo = Geolocation.getGeolocation();
      if (geo == null)
      {
         return;
      }
      
      geo.getCurrentPosition(new PositionCallback()
      {
         public void onFailure(PositionError error)
         {
            String message = "";
            switch (error.getCode())
            {
               case PositionError.UNKNOWN_ERROR:
                  message = "Unknown Error";
                  break;
               case PositionError.PERMISSION_DENIED:
                  message = "Permission Denied";
                  break;
               case PositionError.POSITION_UNAVAILABLE:
                  message = "Position Unavailable";
                  break;
               case PositionError.TIMEOUT:
                  message = "Time-out";
                  break;
               default:
                  message = "Unknown error code.";
            }
         }

         public void onSuccess(Position position)
         {
            Coordinates c = position.getCoords();
            
            latitude = c.getLatitude();
            longitude = c.getLongitude();
            
            locationRetrieved = true;
         }
      });
   }
   
   private double getDistance(double lat1, double lon1, 
                              double lat2, double lon2)
   {
      double R = 6371; // Radius of the earth in km
      double dLat = toRad(lat2-lat1);  // Javascript functions in radians
      double dLon = toRad(lon2-lon1); 
      double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
              Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * 
              Math.sin(dLon/2) * Math.sin(dLon/2); 
      double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
      double d = R * c; // Distance in km
      
      return d;
   }
   
   private double toRad(double deg)
   {
      double rad = deg * Math.PI/180;
      
      return rad;
   }
}
