package in.vbuy.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utilities {
 
   public StringBuilder InputStreamToString(InputStream is) throws IOException {
      String line = "";
      StringBuilder total = new StringBuilder();
      
      // Wrap a BufferedReader around the InputStream
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));

      // Read response until the end
      while ((line = rd.readLine()) != null) { 
        total.append(line);
      }
      
      // Return full string
      return total;
  }
}