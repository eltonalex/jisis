/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unesco.jisis.wizards.marc;

import java.awt.Component;
import java.awt.Rectangle;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import org.openide.util.NbBundle;
import org.unesco.jisis.corelib.client.ConnectionInfo;
import org.unesco.jisis.corelib.client.ConnectionPool;
import org.unesco.jisis.corelib.common.Global;
import org.unesco.jisis.corelib.common.IDatabase;
import org.unesco.jisis.gui.DirectoryChooser;
import org.unesco.jisis.jisisutils.proxy.ClientDatabaseProxy;
import org.unesco.jisis.jisisutils.proxy.MarkedRecords;
import org.unesco.jisis.jisisutils.proxy.SearchResult;

/**
 *
 * @author jcd
 */
public class ExpDCVisualPanel extends javax.swing.JPanel {

    private ClientDatabaseProxy db_;
   /**
    * Creates new form ExpDCVisualPanel
    */
   public ExpDCVisualPanel() {
       ConnectionInfo connectionInfo = ConnectionPool.getDefaultConnectionInfo();
       
      IDatabase db = connectionInfo.getDefaultDatabase();

      if (db instanceof ClientDatabaseProxy) {
         db_ = (ClientDatabaseProxy) db;
      } else {
         throw new RuntimeException("ExpMODSVisualPanel: Cannot cast DB to ClientDatabaseProxy");
      }
      initComponents();
      String[] fstNames = db_.getFstNames();
      String [] cmbModel;
      if (fstNames == null) {
          cmbModel = new String[] {"<none>"};
      } else {
          cmbModel = new String[fstNames.length+1];
          cmbModel[0] = "<none>";
          System.arraycopy(fstNames, 0, cmbModel, 1, fstNames.length);
      }
       cmbReformattingFST.setModel(new DefaultComboBoxModel(cmbModel));
      String lastDir = Global.getClientWorkPath();
      Global.prefs_.put("IMPEXP_OUTPUT_DIR", lastDir);
      txtOutputDir.setText(lastDir);
     
       cmbSearch.setEnabled(false);
       rdbAllMfn.setEnabled(true);
       rdbMfns.setEnabled(true);
       rdbMarked.setEnabled(true);
       prepareSearchHistory();
       prepareMarkedRecordsHistory();
       prepareHitSortHistory();
       rdbOAI.setSelected(true);
       rdbRDF.setSelected(false);
       rdbSRW.setSelected(false);
   }
    private void prepareSearchHistory() {
         List<SearchResult> searchResults = db_.getSearchResults();
      String[] searches = {"No Search"};
      if (searchResults != null && searchResults.size() > 0) {

         int n = searchResults.size();
         searches = new String[n];
         for (int i = 0; i < n; i++) {
            searches[i] = searchResults.get(i).toString();
         }
      } else {
         // Disable Search radio button and combo box
         cmbSearch.setEnabled(false);
         rdbSearchResult.setEnabled(false);
      }
        cmbSearch.setModel(new DefaultComboBoxModel(searches));

        /**
         * Make Combo text display short, and tool tip for full text
         */
        cmbSearch.setPrototypeDisplayValue("Short");
        cmbSearch.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
                if (index == -1) {
                    cmbSearch.setToolTipText(value.toString());
                    return this;
                }

                setToolTipText(value.toString());
                Rectangle textRect
                        = new Rectangle(cmbSearch.getSize().width,
                                getPreferredSize().height);
                String shortText = SwingUtilities.layoutCompoundLabel(this,
                        getFontMetrics(getFont()),
                        value.toString(), null,
                        getVerticalAlignment(), getHorizontalAlignment(),
                        getHorizontalTextPosition(), getVerticalTextPosition(),
                        textRect, new Rectangle(), textRect,
                        getIconTextGap());
                setText(shortText);
                return this;
            }
        });
    }

    private void prepareHitSortHistory() {

        String dbHitSortFilePath = Global.getClientWorkPath() + File.separator
                + db_.getDbName()
                + Global.HIT_SORT_FILE_EXT;
        File dbHitSortFile_ = new File(dbHitSortFilePath);

        String dbHitSortHxfFilePath = Global.getClientWorkPath() + File.separator
                + db_.getDbName()
                + Global.HIT_SORT_HXF_FILE_EXT;
        File dbHitSortHxfFile_ = new File(dbHitSortHxfFilePath);

        String[] hitSortNames = new String[1];
        if (!dbHitSortFile_.exists()) {
            hitSortNames[0] = "No HitSorts";
            // Disable Hit File radio button and combo box
            cmbHitSortFile.setEnabled(false);
            rdbHitSort.setEnabled(false);

        } else {
            hitSortNames[0] = dbHitSortFilePath;
        }

        cmbHitSortFile.setModel(new DefaultComboBoxModel(hitSortNames));

    }

    private void prepareMarkedRecordsHistory() {
        List<MarkedRecords> markedRecords = db_.getMarkedRecordsList();
        String[] markedSets = {"No Marked Sets"};
        if (markedRecords != null && !markedRecords.isEmpty()) {

            int n = markedRecords.size();
            markedSets = new String[n];
            for (int i = 0; i < n; i++) {
                markedSets[i] = markedRecords.get(i).toString();
            }
        } else {
            // Disable Search radio button and combo box
            cmbMarked.setEnabled(false);
            rdbMarked.setEnabled(false);
        }
        cmbMarked.setModel(new DefaultComboBoxModel(markedSets));
        
         cmbMarked.setModel(new DefaultComboBoxModel(markedSets));
        cmbMarked.setPrototypeDisplayValue("Short");
        cmbMarked.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
                if (index == -1) {
                    cmbMarked.setToolTipText(value.toString());
                    return this;
                }

                setToolTipText(value.toString());
                Rectangle textRect
                        = new Rectangle(cmbMarked.getSize().width,
                                getPreferredSize().height);
                String shortText = SwingUtilities.layoutCompoundLabel(this,
                        getFontMetrics(getFont()),
                        value.toString(), null,
                        getVerticalAlignment(), getHorizontalAlignment(),
                        getHorizontalTextPosition(), getVerticalTextPosition(),
                        textRect, new Rectangle(), textRect,
                        getIconTextGap());
                setText(shortText);
                return this;
            }
        });

    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ExpDCVisualPanel.class, "MSG_ExpDCVisualPanel");
    }


   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        expFileName = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cmbReformattingFST = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        NumberFormat nf = NumberFormat.getIntegerInstance();
        txtRenumberFromMFN = new javax.swing.JFormattedTextField(nf);
        jLabel7 = new javax.swing.JLabel();
        txtOutputTagMFN = new javax.swing.JFormattedTextField(nf);
        cmbEncoding = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        rdbMfnRange = new javax.swing.JRadioButton();
        rdbAllMfn = new javax.swing.JRadioButton();
        rdbMarked = new javax.swing.JRadioButton();
        rdbMfns = new javax.swing.JRadioButton();
        txtMfns = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        rdbSearchResult = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        cmbSearch = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        cmbHitSortFile = new javax.swing.JComboBox();
        txtOutputDir = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbMarked = new javax.swing.JComboBox();
        rdbOAI = new javax.swing.JRadioButton();
        rdbRDF = new javax.swing.JRadioButton();
        rdbSRW = new javax.swing.JRadioButton();
        rdbHitSort = new javax.swing.JRadioButton();

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 470));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel2.text")); // NOI18N

        btnBrowse.setText(NbBundle.getMessage(ExpMarcXmlVisualPanel.class, "ExpMarcXmlVisualPanel.btnBrowse.text"));
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel5.text")); // NOI18N

        cmbReformattingFST.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbReformattingFST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbReformattingFSTActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel6.text")); // NOI18N

        txtRenumberFromMFN.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel7.text")); // NOI18N

        txtOutputTagMFN.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        cmbEncoding.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "US-ASCII", "CP850 (or IBM850)", "ISO-8859-1", "UTF-8", "UTF-16", "UTF-16BE", "UTF-16LE", "CP1256 (Arabic Windows-1256)", "MARC-8", "ISO-5426 (Used by UNIMARC)", "ISO-6937 (Used by UNIMARC)" }));
        cmbEncoding.setSelectedIndex(3);
        cmbEncoding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEncodingActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel8.text")); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel13.text")); // NOI18N

        buttonGroup1.add(rdbMfnRange);
        rdbMfnRange.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdbMfnRange.setSelected(true);
        rdbMfnRange.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbMfnRange.text")); // NOI18N

        buttonGroup2.add(rdbAllMfn);
        rdbAllMfn.setSelected(true);
        rdbAllMfn.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbAllMfn.text")); // NOI18N
        rdbAllMfn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllMfnActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdbMarked);
        rdbMarked.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbMarked.text")); // NOI18N

        buttonGroup2.add(rdbMfns);
        rdbMfns.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbMfns.text")); // NOI18N
        rdbMfns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbMfnsActionPerformed(evt);
            }
        });

        txtMfns.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.txtMfns.text")); // NOI18N
        txtMfns.setEnabled(false);
        txtMfns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMfnsActionPerformed(evt);
            }
        });

        jLabel14.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel14.text")); // NOI18N

        buttonGroup1.add(rdbSearchResult);
        rdbSearchResult.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdbSearchResult.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbSearchResult.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel15.text")); // NOI18N

        cmbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbHitSortFile.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtOutputDir.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.txtOutputDir.text")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.jLabel1.text")); // NOI18N

        cmbMarked.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        rdbOAI.setSelected(true);
        rdbOAI.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbOAI.text")); // NOI18N
        rdbOAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbOAIActionPerformed(evt);
            }
        });

        rdbRDF.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbRDF.text")); // NOI18N
        rdbRDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbRDFActionPerformed(evt);
            }
        });

        rdbSRW.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbSRW.text")); // NOI18N
        rdbSRW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbSRWActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbHitSort);
        rdbHitSort.setText(org.openide.util.NbBundle.getMessage(ExpDCVisualPanel.class, "ExpDCVisualPanel.rdbHitSort.text")); // NOI18N
        rdbHitSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbHitSortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtOutputDir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowse)
                        .addGap(129, 129, 129))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(expFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(rdbOAI)
                        .addGap(18, 18, 18)
                        .addComponent(rdbRDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdbSRW)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtRenumberFromMFN)
                    .addComponent(txtOutputTagMFN)
                    .addComponent(cmbReformattingFST, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbEncoding, 0, 209, Short.MAX_VALUE)
                .addGap(224, 224, 224))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbMfnRange)
                            .addComponent(jLabel13)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(119, 119, 119)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(rdbAllMfn)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdbMfns))
                                            .addComponent(rdbMarked)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdbSearchResult)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel15))
                                    .addComponent(rdbHitSort))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMfns)
                                    .addComponent(cmbMarked, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbSearch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel14)
                                        .addGap(54, 147, Short.MAX_VALUE))
                                    .addComponent(cmbHitSortFile, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(expFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbOAI)
                    .addComponent(rdbRDF)
                    .addComponent(rdbSRW))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtOutputDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBrowse))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdbMfnRange)
                            .addComponent(rdbAllMfn)
                            .addComponent(rdbMfns)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMfns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbMarked)
                    .addComponent(cmbMarked, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbSearchResult)
                    .addComponent(jLabel15)
                    .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbHitSortFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbHitSort))
                .addGap(26, 26, 26)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbReformattingFST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cmbEncoding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRenumberFromMFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(txtOutputTagMFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

   private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed

      selectDirectory();
      //expFileName.setText(selectFile("mrcxml", "MARC XML Files"));
   }//GEN-LAST:event_btnBrowseActionPerformed

   private void cmbReformattingFSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbReformattingFSTActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_cmbReformattingFSTActionPerformed

   private void cmbEncodingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEncodingActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_cmbEncodingActionPerformed

   private void rdbAllMfnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllMfnActionPerformed
      txtMfns.setEnabled(false);
   }//GEN-LAST:event_rdbAllMfnActionPerformed

   private void rdbMfnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbMfnsActionPerformed
      txtMfns.setEnabled(true);
   }//GEN-LAST:event_rdbMfnsActionPerformed

   private void txtMfnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMfnsActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_txtMfnsActionPerformed

   private void rdbRDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbRDFActionPerformed
      rdbOAI.setSelected(false);
      rdbRDF.setSelected(true);
      rdbSRW.setSelected(false);
   }//GEN-LAST:event_rdbRDFActionPerformed

   private void rdbSRWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbSRWActionPerformed
      rdbOAI.setSelected(false);
      rdbRDF.setSelected(false);
      rdbSRW.setSelected(true);
   }//GEN-LAST:event_rdbSRWActionPerformed

   private void rdbOAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbOAIActionPerformed
      rdbOAI.setSelected(true);
      rdbRDF.setSelected(false);
      rdbSRW.setSelected(false);
   }//GEN-LAST:event_rdbOAIActionPerformed

    private void rdbHitSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbHitSortActionPerformed
        cmbHitSortFile.setEnabled(true);
        cmbSearch.setEnabled(false);
        cmbMarked.setEnabled(false);

        rdbAllMfn.setEnabled(false);
        rdbMfns.setEnabled(false);
        txtMfns.setEnabled(false);
        rdbMarked.setEnabled(false);
    }//GEN-LAST:event_rdbHitSortActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cmbEncoding;
    private javax.swing.JComboBox cmbHitSortFile;
    private javax.swing.JComboBox cmbMarked;
    private javax.swing.JComboBox cmbReformattingFST;
    private javax.swing.JComboBox cmbSearch;
    private javax.swing.JTextField expFileName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JRadioButton rdbAllMfn;
    private javax.swing.JRadioButton rdbHitSort;
    private javax.swing.JRadioButton rdbMarked;
    private javax.swing.JRadioButton rdbMfnRange;
    private javax.swing.JRadioButton rdbMfns;
    private javax.swing.JRadioButton rdbOAI;
    private javax.swing.JRadioButton rdbRDF;
    private javax.swing.JRadioButton rdbSRW;
    private javax.swing.JRadioButton rdbSearchResult;
    private javax.swing.JTextField txtMfns;
    private javax.swing.JTextField txtOutputDir;
    private javax.swing.JFormattedTextField txtOutputTagMFN;
    private javax.swing.JFormattedTextField txtRenumberFromMFN;
    // End of variables declaration//GEN-END:variables

   public String getSelectedFile() {
      String fileName = expFileName.getText();
      if (!fileName.toLowerCase().endsWith(".dc")) {
         fileName += ".dc";
      }
      String fullFileName = Global.prefs_.get("IMPEXP_OUTPUT_DIR", "") + File.separator
              + fileName;

      return fullFileName;
   }
  private String selectDirectory() {
      //prefs = Preferences.userNodeForPackage(this.getClass());
      String lastDir = Global.getClientWorkPath();
      DirectoryChooser dc = new DirectoryChooser(new File(lastDir));
      dc.showOpenDialog(this);
      File file;
      if ((file = dc.getSelectedFile()) != null) {
         Global.prefs_.put("IMPEXP_OUTPUT_DIR", file.getAbsolutePath());
         txtOutputDir.setText(file.getAbsolutePath());
         return file.getAbsolutePath();
      }
      return "";
   }

   public String getReformattingFST() {
      int index = cmbReformattingFST.getSelectedIndex();
      return (String) ((index == -1) ? "<none>" : cmbReformattingFST.getSelectedItem());
   }

   public String getEncoding() {
      int index = cmbEncoding.getSelectedIndex();
      return (String) ((index == -1) ? "" : cmbEncoding.getSelectedItem());
   }

   public String geHitSortFile() {
      int index = cmbHitSortFile.getSelectedIndex();
      return (String) ((index == -1) ? "" : cmbHitSortFile.getSelectedItem());
   }

   public int getRenumberFromMFN() {
      Number num = (Number) txtRenumberFromMFN.getValue();
      return (num == null) ? -1 : num.intValue();
   }

   public int getOutputTagMFN() {
      Number num = (Number) txtOutputTagMFN.getValue();
      return (num == null) ? -1 : num.intValue();
   }

  
    public int getMfnsRangeOption() {
        if (rdbMfnRange.isSelected() && rdbAllMfn.isSelected()) {
            return Global.MFNS_OPTION_ALL;
        } else if (rdbMfnRange.isSelected() && rdbMfns.isSelected()) {
            return Global.MFNS_OPTION_RANGE;
        } else if (rdbMfnRange.isSelected() && rdbMarked.isSelected()) {
            return Global.MFNS_OPTION_MARKED;
        } else if (rdbSearchResult.isSelected()) {
            return Global.MFNS_OPTION_SEARCH;
        } else if (rdbHitSort.isSelected()) {
            return Global.MFNS_OPTION_HITSORT;
        } else {
            return Global.MFNS_OPTION_ALL;
        }
    }
    public int getDCOption() {
      if (rdbOAI.isSelected()) {
         return Global.DUBLIN_CORE_OAI;
      } else if (rdbRDF.isSelected()) {
         return Global.DUBLIN_CORE_RDF;
      } else {
         return Global.DUBLIN_CORE_SRW;
      }
   }

   public String getMfnRanges() {
      String s = txtMfns.getText();
      return s;
   }

   public boolean isSearchResult() {
      return rdbSearchResult.isSelected();
   }

   public int getSearchHistoryIndex() {
      int index = cmbSearch.getSelectedIndex();
      return index;
   }

   public int getMarkedRecordsIndex() {
      int index = cmbMarked.getSelectedIndex();
      return index;
   }
    
}
