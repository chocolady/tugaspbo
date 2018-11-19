/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoy.obat;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Paul Decul
 */
public class InventoryObat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        SwingUtilities.updateComponentTreeUI(new Login());
    } catch (Exception e){
    }
        Login fm=new Login();
        fm.setLocationRelativeTo(null);
        fm.setVisible(true);
    }
}
