
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sajeewa
 */
public class Additems extends javax.swing.JFrame {

    /**
     * Creates new form Additems
     */
    Connection con = DBconnect.connect();
    PreparedStatement prt;
    Resultset rs = null;

    public Additems() {
        initComponents();
        con = DBconnect.connect();
        table_load();
    }

    public void addItems() {
        String name = itemName.getText();
        String genummila = pobtained.getText();
        String wikunummila = sellprice.getText();
        String qunti = quntity.getText();
        if (name.equals("") && genummila.equals("") && wikunummila.equals("") && qunti.equals("")) {
            JOptionPane.showMessageDialog(null, "faill");
        } else {
            try {
                String sql = "INSERT INTO `items`( `name`, `p_obtained`, `p_selling`, `quntity`) VALUES ('" + name + "','" + genummila + "','" + wikunummila + "','" + qunti + "')";
                prt = con.prepareStatement(sql);
                prt.execute();
                JOptionPane.showMessageDialog(null, "Add succesfully");
                itemName.setText("");
                pobtained.setText("");
                sellprice.setText("");
                quntity.setText("");
                table_load();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void table_load() {
        try {
            String sql = "SELECT * FROM `items` WHERE 1";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            itemTable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void p_table_select() {
        int pdata = itemTable.getSelectedRow();

        String id = itemTable.getValueAt(pdata, 0).toString();;
        String names = itemTable.getValueAt(pdata, 1).toString();
        String obprice = itemTable.getValueAt(pdata, 2).toString();
        String selprice = itemTable.getValueAt(pdata, 3).toString();
        String quntityy = itemTable.getValueAt(pdata, 4).toString();

        itemName.setText(names);
        pobtained.setText(obprice);
        sellprice.setText(selprice);
        quntity.setText(quntityy);
    }

    public void delete_data() {
        int check = JOptionPane.showConfirmDialog(null, "confirm hand over");
        int pdata = itemTable.getSelectedRow();
        String id = itemTable.getValueAt(pdata, 0).toString();;
        try {
            if (check == 0) {
                String sql = "DELETE FROM items WHERE id='" + id + "'";
                prt = con.prepareStatement(sql);
                prt.execute();
                table_load();
                itemName.setText("");
                pobtained.setText("");
                sellprice.setText("");
                quntity.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void items_serch() {
        String searchdata = search.getText();

        try {
            String sql = "SELECT * FROM items WHERE name LIKE '%" + searchdata + "%' or p_obtained LIKE '%" + searchdata + "%' or name LIKE '%" + searchdata + "%' ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            itemTable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void update() {
        String name = itemName.getText();
        String genummila = pobtained.getText();
        String wikunummila = sellprice.getText();
        String qunti = quntity.getText();
        int pdata = itemTable.getSelectedRow();
        String id = itemTable.getValueAt(pdata, 0).toString();;

        try {
            String sql = "UPDATE items SET name='" + name + "', p_obtained='" + genummila + "', p_selling='" + wikunummila + "', quntity='" + qunti + "' WHERE id='" + id + "'";
            prt = con.prepareStatement(sql);
            prt.execute();
            JOptionPane.showMessageDialog(null, "updated");
            table_load();
            itemName.setText("");
            pobtained.setText("");
            sellprice.setText("");
            quntity.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        quntity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        itemName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pobtained = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sellprice = new javax.swing.JTextField();
        delete = new javax.swing.JButton();
        save = new javax.swing.JButton();
        update = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        serchb = new javax.swing.JButton();
        search = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel1.setText("ITEMS");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("quntity");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 120, -1));

        quntity.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(quntity, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 220, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Items Name");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, -1));

        itemName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        itemName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNameActionPerformed(evt);
            }
        });
        jPanel1.add(itemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 220, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Price obtained");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, -1));

        pobtained.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(pobtained, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 220, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("selling price");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 120, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("selling price");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 120, -1));

        sellprice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(sellprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 220, -1));

        delete.setBackground(new java.awt.Color(0, 204, 0));
        delete.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        delete.setText("delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, -1, -1));

        save.setBackground(new java.awt.Color(0, 204, 0));
        save.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        save.setText("save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel1.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 190, 70, -1));

        update.setBackground(new java.awt.Color(0, 204, 0));
        update.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        update.setText("update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel1.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        clear.setBackground(new java.awt.Color(0, 204, 0));
        clear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        clear.setText("clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        jPanel1.add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 80, -1));

        itemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        itemTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemTableMouseClicked(evt);
            }
        });
        itemTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(itemTable);

        serchb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        serchb.setText("Search");
        serchb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchbActionPerformed(evt);
            }
        });

        search.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jButton4.setBackground(new java.awt.Color(0, 204, 0));
        jButton4.setText("Sell");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(165, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(259, 259, 259)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addComponent(serchb)
                        .addGap(18, 18, 18)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(serchb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemNameActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        addItems();
    }//GEN-LAST:event_saveActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        itemName.setText("");
        pobtained.setText("");
        sellprice.setText("");
        quntity.setText("");
    }//GEN-LAST:event_clearActionPerformed

    private void itemTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemTableMouseClicked
        p_table_select();
    }//GEN-LAST:event_itemTableMouseClicked

    private void itemTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemTableKeyReleased
        p_table_select();
    }//GEN-LAST:event_itemTableKeyReleased

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        delete_data();
    }//GEN-LAST:event_deleteActionPerformed

    private void serchbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchbActionPerformed
        items_serch();
    }//GEN-LAST:event_serchbActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        update();
    }//GEN-LAST:event_updateActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new sell().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Additems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Additems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Additems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Additems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Additems().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clear;
    private javax.swing.JButton delete;
    private javax.swing.JTextField itemName;
    private javax.swing.JTable itemTable;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField pobtained;
    private javax.swing.JTextField quntity;
    private javax.swing.JButton save;
    private javax.swing.JTextField search;
    private javax.swing.JTextField sellprice;
    private javax.swing.JButton serchb;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
