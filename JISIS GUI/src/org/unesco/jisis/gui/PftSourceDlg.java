/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PftSourceDlg.java
 *
 * Created on Feb 11, 2009, 6:56:13 PM
 */
package org.unesco.jisis.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author jc_dauphin
 *
 * Note: JTextPane uses the default font and color (defined by the current Swing look-and-feel), rather than
 * the font and color passed to the setFont(…) and setForeground(…) methods. The explanation for this lies in
 * JTextPane‘s flexibility. Since each character can have a different font and color, the notion of setting
 * the font and color for all of the text does not make as much sense as it does in the case of the
 * less-flexible TextArea.
 *
 * The trick is to set the character attributes for the entire text content. If the text pane is empty, the
 * character attributes will be used for any text entered later.
 */
public class PftSourceDlg extends javax.swing.JDialog {

    protected PftEditor editor = null;
    protected boolean succeeded_ = false;
    protected UndoManager undoManager = new UndoManager();

    /**
     * Creates new form PftSourceDlg
     *
     * @param parent
     * @param source
     */
    public PftSourceDlg(java.awt.Frame parent, String source) {
        super(parent, "PFT Source", true);
        initComponents();
        PftDocumentEx doc = new PftDocumentEx();
        editor = (PftEditor) srcTextPane;

        doc.addDocumentListener(editor);
        HashSet<String> keywords = new HashSet<String>();
        keywords.add("mfn");
        keywords.add("mpl");
        keywords.add("mhl");
        keywords.add("mdl");
        keywords.add("and");
        keywords.add("or");
        keywords.add("not");
        keywords.add("val");
        keywords.add("rsum");
        keywords.add("rmin");
        keywords.add("rmax");
        keywords.add("ravr");
        keywords.add("l");
        keywords.add("lr");
        keywords.add("npst");
        keywords.add("nocc");
        keywords.add("occ");
        keywords.add("size");
        keywords.add("type");
        keywords.add("tag");
        keywords.add("f");
        keywords.add("ref");
        keywords.add("s");
        keywords.add("ss");
        keywords.add("date");
        keywords.add("db");
        keywords.add("p");
        keywords.add("a");
        keywords.add("if");
        keywords.add("then");
        keywords.add("else");
        keywords.add("fi");
        keywords.add("while");
        keywords.add("link");

        doc.setKeywords(keywords);
        editor.setDocument(doc);

        undoManager.setLimit(1000);

        Action undoAction = new UndoAction(undoManager);
        Action redoAction = new RedoAction(undoManager);

        // Assign the actions to keys
        editor.registerKeyboardAction(undoAction, KeyStroke.getKeyStroke(
            KeyEvent.VK_Z, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
        editor.registerKeyboardAction(redoAction, KeyStroke.getKeyStroke(
            KeyEvent.VK_Y, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);

        editor.getDocument().addUndoableEditListener(undoManager);

        editor.setText(source);

        undoManager.discardAllEdits();

    }

    public boolean succeeded() {
        return succeeded_;
    }

    public String getSource() {
        return editor.getText();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      panel = new javax.swing.JPanel();
      btnSave = new javax.swing.JButton();
      btnCancel = new javax.swing.JButton();
      editorScrollPane = new javax.swing.JScrollPane();
      srcTextPane = new PftEditor(){
         public boolean getScrollableTracksViewportWidth() {
            return (getSize().width < getParent().getSize().width);
         }
         public void setSize(Dimension d) {
            if (d.width < getParent().getSize().width) {
               d.width = getParent().getSize().width;
            }
            super.setSize(d);
         }
      };
      ;

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("null");

      panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10));

      btnSave.setText(org.openide.util.NbBundle.getMessage(PftSourceDlg.class, "PftSourceDlg.btnSave.text")); // NOI18N
      btnSave.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSaveActionPerformed(evt);
         }
      });

      btnCancel.setText(org.openide.util.NbBundle.getMessage(PftSourceDlg.class, "PftSourceDlg.btnCancel.text")); // NOI18N
      btnCancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnCancelActionPerformed(evt);
         }
      });

      editorScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
      editorScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      editorScrollPane.setViewportView(srcTextPane);

      javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
      panel.setLayout(panelLayout);
      panelLayout.setHorizontalGroup(
         panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(panelLayout.createSequentialGroup()
            .addGap(99, 99, 99)
            .addComponent(btnSave)
            .addGap(60, 60, 60)
            .addComponent(btnCancel)
            .addContainerGap(288, Short.MAX_VALUE))
         .addComponent(editorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
      );
      panelLayout.setVerticalGroup(
         panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
            .addComponent(editorScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(btnSave)
               .addComponent(btnCancel))
            .addGap(11, 11, 11))
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        succeeded_ = true;
        dispose();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        succeeded_ = false;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                PftSourceDlg dialog = new PftSourceDlg(new javax.swing.JFrame(), "if v1 then");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnCancel;
   private javax.swing.JButton btnSave;
   private javax.swing.JScrollPane editorScrollPane;
   private javax.swing.JPanel panel;
   private javax.swing.JTextPane srcTextPane;
   // End of variables declaration//GEN-END:variables

// The Undo action
    public class UndoAction extends AbstractAction {
        private final UndoManager manager;

        public UndoAction(UndoManager manager) {
            this.manager = manager;
        }

        public void actionPerformed(ActionEvent evt) {
            try {
                manager.undo();
            } catch (CannotUndoException e) {
                Toolkit.getDefaultToolkit().beep();
            }
        }

    }

    // The Redo action
    public class RedoAction extends AbstractAction {

        private final UndoManager manager;

        public RedoAction(UndoManager manager) {
            this.manager = manager;
        }

        public void actionPerformed(ActionEvent evt) {
            try {
                manager.redo();
            } catch (CannotRedoException e) {
                Toolkit.getDefaultToolkit().beep();
            }
        }

    }
}