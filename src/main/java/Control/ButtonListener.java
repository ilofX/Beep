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
package Control;

import Model.APICall;
import View.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**
 *
 * @author Stella Filippo
 * @author Cecconato Filippo
 * @version 0.1
 */
public class ButtonListener implements ActionListener {

    private final MainFrame mainFrame;
    private final Executor Executor;

    public ButtonListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.Executor = Executors.newSingleThreadExecutor();
        this.mainFrame.setButtonListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.mainFrame.getjButton_Lookup()){
            try {
                this.Executor.execute(new APICall(this.mainFrame.getjTextField_Address().getText(), this.mainFrame));
            } catch (MalformedURLException ex) {
                JOptionPane.showMessageDialog(this.mainFrame, "The address: "+this.mainFrame.getjTextField_Address().getText()+ "is malformed", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
}
