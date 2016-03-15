package org.unesco.jisis.wizards.dbcreate;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.event.ListDataListener;
import org.openide.util.NbBundle;

public final class DbNameVisualPanel extends JPanel {
    
    /**
     * Creates new form DbNameVisualPanel
     */
    public DbNameVisualPanel() {
        initComponents();
       
    }
    
   @Override
    public String getName() {
        return NbBundle.getMessage(DbNameVisualPanel.class, "MSG_DbNameStep");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      lblDbHome = new javax.swing.JLabel();
      lblDbName = new javax.swing.JLabel();
      txtDbName = new javax.swing.JTextField();
      cmbDbHome = new javax.swing.JComboBox();
      jSeparator1 = new javax.swing.JSeparator();

      lblDbHome.setFont(new java.awt.Font("Tahoma", 1, 12));
      org.openide.awt.Mnemonics.setLocalizedText(lblDbHome, org.openide.util.NbBundle.getMessage(DbNameVisualPanel.class, "LBL_SelDBHOME")); // NOI18N

      lblDbName.setFont(new java.awt.Font("Tahoma", 1, 12));
      org.openide.awt.Mnemonics.setLocalizedText(lblDbName, org.openide.util.NbBundle.getMessage(DbNameVisualPanel.class, "LBL_SelDBNAME")); // NOI18N

      txtDbName.setText("NewDatabase1");

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(cmbDbHome, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(lblDbHome)))
               .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addGroup(layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(txtDbName, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(lblDbName))))
            .addContainerGap(32, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(lblDbHome)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cmbDbHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(28, 28, 28)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(lblDbName)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtDbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(177, Short.MAX_VALUE))
      );
   }// </editor-fold>//GEN-END:initComponents
    
    
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JComboBox cmbDbHome;
   private javax.swing.JSeparator jSeparator1;
   private javax.swing.JLabel lblDbHome;
   private javax.swing.JLabel lblDbName;
   private javax.swing.JTextField txtDbName;
   // End of variables declaration//GEN-END:variables
    
    public void fillDbHomesList(String[] dbHomes) {
        cmbDbHome.setModel(new DefaultComboBoxModel(dbHomes));
        //jList1.setModel(new DbHomesModel(dbHomes));
    }

    public void addListDataListener(ListDataListener l) {
    }

    public void removeListDataListener(ListDataListener l) {
    }
    
    public String getDbHome() {
        Object selObject = cmbDbHome.getSelectedItem();
        return (selObject == null ? "" : selObject.toString());
    }
    
    public String getDbName() {
        return txtDbName.getText();
    }
    
}
