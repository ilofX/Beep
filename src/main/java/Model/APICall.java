/*
 * Copyright 2018 Stella Filippo, Cecconato Filippo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Model;

import View.MainFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/**
 *
 * @author Stella Filippo
 * @author Cecconato Filippo
 * @version 0.1
 */

public class APICall extends Thread{

    private final MainFrame mainFrame;
    private final String Domain;
    private final URL url;
    
    public APICall(String Domain, MainFrame mainFrame) throws MalformedURLException {
        super("Lookup for "+Domain);
        this.Domain = Domain;
        this.mainFrame = mainFrame;
        this.url = new URL("https://freegeoip.net/json/"+this.Domain);
    }

    @Override
    public void run() {
        try {
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            
            if(conn.getResponseCode() != 200){
                throw new RuntimeException("Request Failed : HTTPS Error Code "+conn.getResponseCode());
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                
            while(!in.ready()){
                this.sleep(200);
            }
            
            JSONObject result = new JSONObject(in.readLine());
            
            conn.disconnect();
            
            mainFrame.setValuesFromLookup(result);
        } catch (IOException ex) {
            Logger.getLogger(APICall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(APICall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
