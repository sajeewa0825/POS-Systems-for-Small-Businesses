
import com.mysql.cj.protocol.Resultset;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public final class sell extends javax.swing.JFrame {

    /**
     * Creates new form sell
     */
    Connection con = DBconnect.connect();
    PreparedStatement prt;
    Resultset rs = null;

    public sell() {
        initComponents();
        con = DBconnect.connect();
        table_load();
        temptable_load();
    }

    public void table_load() {
        try {
            String sql = "SELECT  `id`, `name`, `p_selling`, `p_obtained`, `quntity` FROM `items` WHERE 1";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            itemTables.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void table_loadbill() {
        try {
            String sql = "SELECT `name`, `quntity`, `price`, `total` FROM `temoder` WHERE 1";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            billtable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void I_table_select() {
        int pdata = itemTables.getSelectedRow();

        String id = itemTables.getValueAt(pdata, 0).toString();;
        String names = itemTables.getValueAt(pdata, 1).toString();
        String selprice = itemTables.getValueAt(pdata, 2).toString();
        String ORIGINALPRICE = itemTables.getValueAt(pdata, 3).toString();

        add.setText(names);
        oneprice.setText(selprice);
        this.id.setText(id);
        this.originalp.setText(ORIGINALPRICE);
    }

    public void items_serch() {
        String searchdata = iSearch.getText();

        try {
            String sql = "SELECT  `id`, `name`, `p_selling`, `p_obtained`, `quntity` FROM items WHERE name LIKE '%" + searchdata + "%' or p_selling LIKE '%" + searchdata + "%' or id LIKE '%" + searchdata + "%' ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            itemTables.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setbill() {
        String name = add.getText();
        String priceone = oneprice.getText();
        String qunti = addqunt.getText();
        String totallabel = fulltotal.getText();
        String orip = originalp.getText();

        int pdata = itemTables.getSelectedRow();
        String countity = itemTables.getValueAt(pdata, 4).toString();;
        int quntity1 = Integer.parseInt(countity);
        

        float fullytotal = Float.parseFloat(totallabel);

        float price1;
        int qunt;
        float total;
        price1 = Float.parseFloat(priceone);
        qunt = Integer.parseInt(qunti);
        total = qunt * price1;

        float discount;
        String getdis = dis.getText();
        float getdiss = Float.parseFloat(getdis);
        float dis1 = getdiss / 100;
        discount = total * dis1;
        total = total - discount;

        float profit;
        float nonprofit;
        float originalpric = Float.parseFloat(orip);
        nonprofit = qunt * originalpric;
        profit = total - nonprofit;

        int quntity = quntity1 - qunt;
        fullytotal = fullytotal + total;
        String s = Float.toString(fullytotal);

        if (name.equals("") && priceone.equals("") && qunti.equals("")) {
            JOptionPane.showMessageDialog(null, "not select");
        } else {
            if (quntity1 > 0 && quntity1 >= qunt) {
                try {
                    String sql = "UPDATE items SET quntity='" + quntity + "' WHERE name='" + name + "'";
                    prt = con.prepareStatement(sql);
                    prt.execute();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                try {
                    String sql = "INSERT INTO `temoder`(`name`, `quntity`, `price`, `total`,`profit`,`discount`) VALUES ('" + name + "','" + qunt + "','" + price1 + "','" + total + "','" + profit + "','" + getdis + "')";
                    prt = con.prepareStatement(sql);
                    prt.execute();
                    fulltotal.setText(s);
                    add.setText("");
                    oneprice.setText("");
                    addqunt.setText("1");
                    dis.setText("0");
                    originalp.setText("");
                    id.setText("");
                    table_loadbill();
                    table_load();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "not store");
            }
        }
    }

    @SuppressWarnings("empty-statement")
    public void tempBillremove() {
        int check = JOptionPane.showConfirmDialog(null, "confirm hand over");

        int pdata = billtable.getSelectedRow();
        String name = billtable.getValueAt(pdata, 0).toString();
        String total = billtable.getValueAt(pdata, 3).toString();
        float fullytotal = Float.parseFloat(total);
        String quntity2 = null;

        try {
            prt = con.prepareStatement("SELECT  quntity FROM items WHERE name='" + name + "'");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                quntity2 = result.getString(1);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        String qunti = billtable.getValueAt(pdata, 1).toString();
        int quntity1 = Integer.parseInt(qunti);

        int quntity3 = Integer.parseInt(quntity2);

        int qunt3 = quntity1 + quntity3;

        String totallabel = fulltotal.getText();
        float fullytotal1 = Float.parseFloat(totallabel);

        try {
            String sql = "UPDATE items SET quntity='" + qunt3 + "' WHERE name='" + name + "'";
            prt = con.prepareStatement(sql);
            prt.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        try {
            if (check == 0) {
                String sql = "DELETE FROM temoder WHERE name='" + name + "'";
                prt = con.prepareStatement(sql);
                prt.execute();
                float fully = fullytotal1 - fullytotal;
                String s = Float.toString(fully);
                fulltotal.setText(s);
                table_load();
                temselect.setText("");
                temptable_load();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void temp_select() {
        int pdata = billtable.getSelectedRow();
        String names = billtable.getValueAt(pdata, 0).toString();

        temselect.setText(names);
    }

    public void billcal() {
        String totallabel = fulltotal.getText();
        float fullytotal1 = Float.parseFloat(totallabel);

        String ramount = Ramount.getText();
        float paydamout = Float.parseFloat(ramount);

        float total = paydamout - fullytotal1;
        String s = Float.toString(total);
        Rpay.setText(s);
    }

    public void temptable_load() {
        try {
            String sql = "SELECT * FROM `temoder` WHERE 1";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            billtable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void printbill() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");
        String date = s.format(d);
        String customer = cname.getText();
        String totall = fulltotal.getText();
        String rech = Ramount.getText();
        String retc = Rpay.getText();
        String ref = this.ref.getText();

        jTextArea1.append("\n\t...Madhara (pvt) Ltd...\n  ");
        jTextArea1.append("                    Siyambalangamuwa, Diddeniya, "
                + "\n	       Melsiripura\n ");
        jTextArea1.append("\t   Tel: 0754261015 \n ");
        jTextArea1.append("...................................................................................................");
        jTextArea1.append("\n date: " + date + " ");
        jTextArea1.append("\n customer name: " + customer + " ");
        jTextArea1.append("\n Ref : " + ref + "  \n  ");
        jTextArea1.append("...................................................................................................\n");
        jTextArea1.append("\t \n Itmes	         quantity \t Price \t Total \n");
        String name = null, qun, price, total;
        try {
            prt = con.prepareStatement("SELECT * FROM `temoder` WHERE 1");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                name = result.getString(1);
                qun = result.getString(2);
                price = result.getString(3);
                total = result.getString(4);
                jTextArea1.append("\t \n" + name + "         " + qun + "\t " + price + "\t " + total + "");
            }
            jTextArea1.append("\n...................................................................................................");
            jTextArea1.append("\n Total Amount \t\t\t" + totall + "");
            jTextArea1.append("\n\t\t                           ........................");
            jTextArea1.append("\n...................................................................................................");
            jTextArea1.append("\n Received cash: " + rech + " ");
            jTextArea1.append("\n Return cash     : " + retc + "  \n  ");
            jTextArea1.append("\n...................................................................................................");
            jTextArea1.append("\nobjection of goods condition will be acceptable with "
                    + "\nthe warrenty sticks within 7 days  it was a pleasure"
                    + "\nto serve you"
                    + "\n\t...thank you come agin...");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void storebuydata() {
        String c_name = cname.getText();
        String ref = this.ref.getText();
        String totalprice = fulltotal.getText();
        String rec_cash = Ramount.getText();
        String retu_cash = Rpay.getText();

        try {
            prt = con.prepareStatement("SELECT * FROM `temoder` WHERE 1");
            ResultSet result = prt.executeQuery();
            while (result.next()) {
                String name = result.getString(1);
                String qu = result.getString(2);
                String price = result.getString(3);
                String total = result.getString(4);
                String profit = result.getString(5);
                String discount = result.getString(6);
                try {
                    String sql = "INSERT INTO `sell`(`name`, `quntity`, `price`, `total`,`profit`, `discount`, `customer_name`, `Ref`, `Total_price`, `Received_cash`, `Return_cash`) VALUES ('" + name + "','" + qu + "','" + price + "','" + total + "','" + profit + "','" + discount + "','" + c_name + "','" + ref + "','" + totalprice + "','" + rec_cash + "','" + retu_cash + "')";
                    prt = con.prepareStatement(sql);
                    prt.execute();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void print() {
        PrinterJob job = PrinterJob.getPrinterJob();

        boolean doprint = job.printDialog();
        if (doprint) {
            try {
                jTextArea1.print();
            } catch (Exception e) {
            }

        } else {
            JOptionPane.showMessageDialog(null, "print fail");
        }
        try {
            String sql = "DELETE FROM temoder";
            prt = con.prepareStatement(sql);
            prt.execute();
            allclean();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void allclean() {
        try {
            String sql = "DELETE FROM temoder";
            prt = con.prepareStatement(sql);
            prt.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        table_loadbill();
        jTextArea1.setText("");
        fulltotal.setText("0");
        Ramount.setText("");
        Rpay.setText("");
        cname.setText("");
        ref.setText("");
    }

    public void customer_serch() {
        String searchdata = csearch.getText();

        try {
            String sql = "SELECT * FROM customer WHERE C_name LIKE '%" + searchdata + "%' or phone LIKE '%" + searchdata + "%' or c_id LIKE '%" + searchdata + "%' ";
            prt = con.prepareStatement(sql);
            rs = (Resultset) prt.executeQuery();
            ctable.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void c_table_select() {
        int pdata = ctable.getSelectedRow();
        String names = ctable.getValueAt(pdata, 1).toString();
        cname.setText(names);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        billtable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        itemTables = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        temselect = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        add = new javax.swing.JLabel();
        print = new javax.swing.JButton();
        fulltotal = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        Isearch = new javax.swing.JButton();
        iSearch = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Ramount = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        Rpay = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        addqunt = new javax.swing.JTextField();
        oneprice = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        dis = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        originalp = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        ref = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ctable = new javax.swing.JTable();
        csearch = new javax.swing.JTextField();
        Isearch1 = new javax.swing.JButton();
        oneprice1 = new javax.swing.JLabel();
        add1 = new javax.swing.JLabel();
        id1 = new javax.swing.JLabel();
        print1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabel1.setText("SELL madhara");

        billtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, ""},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        billtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billtableMouseClicked(evt);
            }
        });
        billtable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                billtableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(billtable);
        if (billtable.getColumnModel().getColumnCount() > 0) {
            billtable.getColumnModel().getColumn(3).setResizable(false);
            billtable.getColumnModel().getColumn(3).setHeaderValue("Title 4");
        }

        itemTables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        itemTables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemTablesMouseClicked(evt);
            }
        });
        itemTables.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemTablesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(itemTables);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Bill table");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("store table");

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Remove");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        temselect.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        temselect.setText("select item");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Received cash");

        add.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        add.setText("Add");

        print.setBackground(new java.awt.Color(0, 204, 0));
        print.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        print.setText("print");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        fulltotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        fulltotal.setText("0");

        jButton3.setBackground(new java.awt.Color(0, 204, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton3.setText("ADD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        Isearch.setBackground(new java.awt.Color(204, 255, 204));
        Isearch.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Isearch.setText("Search");
        Isearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IsearchActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Total Price");

        Ramount.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Return cash");

        Rpay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jButton5.setBackground(new java.awt.Color(0, 204, 0));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton5.setText("BUY");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        addqunt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        addqunt.setText("1");

        oneprice.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        oneprice.setText("id");

        id.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        id.setText("id");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jButton2.setBackground(new java.awt.Color(0, 204, 0));
        jButton2.setText("Add Items");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("discount");

        dis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dis.setText("0");
        dis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Original P");

        originalp.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        originalp.setForeground(new java.awt.Color(255, 0, 0));
        originalp.setText("Original P");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Ref");

        ref.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ref.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Customer name");

        cname.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("quntity");

        ctable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        ctable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ctableMouseClicked(evt);
            }
        });
        ctable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ctableKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(ctable);

        Isearch1.setBackground(new java.awt.Color(204, 255, 204));
        Isearch1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Isearch1.setText("Search");
        Isearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Isearch1ActionPerformed(evt);
            }
        });

        oneprice1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        oneprice1.setText("Selling P");

        add1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        add1.setText("Item");

        id1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        id1.setText("id");

        print1.setBackground(new java.awt.Color(0, 204, 0));
        print1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        print1.setText("data store and clear");
        print1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                print1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 204, 0));
        jButton4.setText("Report");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 204, 0));
        jButton6.setText("Add customer");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(698, 698, 698)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(394, 394, 394)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(iSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(Isearch, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(62, 62, 62)
                                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(add1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(oneprice1)
                                        .addGap(33, 33, 33)
                                        .addComponent(oneprice, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(22, 22, 22)
                                        .addComponent(originalp))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(33, 33, 33)
                                        .addComponent(dis, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(46, 46, 46)
                                        .addComponent(addqunt, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(109, 109, 109)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(temselect)
                                .addGap(32, 32, 32)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel9)
                                .addGap(72, 72, 72)
                                .addComponent(fulltotal))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel6)
                                .addGap(61, 61, 61)
                                .addComponent(Ramount, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel10)
                                .addGap(80, 80, 80)
                                .addComponent(Rpay, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel11)
                                .addGap(159, 159, 159)
                                .addComponent(ref, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel12)
                                .addGap(47, 47, 47)
                                .addComponent(cname, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(csearch, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(Isearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(print1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1))
                    .addComponent(jButton6)
                    .addComponent(jButton4)
                    .addComponent(jButton2))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(iSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Isearch))
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id1)
                            .addComponent(id))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(add1)
                            .addComponent(add))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(oneprice1)
                            .addComponent(oneprice))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(originalp))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(dis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(addqunt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(7, 7, 7)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(temselect)
                            .addComponent(jButton1))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(fulltotal))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(Ramount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(Rpay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(ref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(cname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(csearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Isearch1))
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(jButton5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(print)
                        .addGap(31, 31, 31)
                        .addComponent(print1)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tempBillremove();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void itemTablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemTablesMouseClicked
        I_table_select();
    }//GEN-LAST:event_itemTablesMouseClicked

    private void itemTablesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemTablesKeyReleased
        I_table_select();
    }//GEN-LAST:event_itemTablesKeyReleased

    private void IsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IsearchActionPerformed
        items_serch();
    }//GEN-LAST:event_IsearchActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        setbill();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void billtableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billtableKeyReleased
        temp_select();
    }//GEN-LAST:event_billtableKeyReleased

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked

    private void billtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_billtableMouseClicked
        temp_select();
    }//GEN-LAST:event_billtableMouseClicked

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        print();
    }//GEN-LAST:event_printActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        billcal();
        printbill();
        storebuydata();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Additems().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ctableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ctableMouseClicked
        c_table_select();
    }//GEN-LAST:event_ctableMouseClicked

    private void ctableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ctableKeyReleased
        c_table_select();
    }//GEN-LAST:event_ctableKeyReleased

    private void Isearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Isearch1ActionPerformed
        customer_serch();
    }//GEN-LAST:event_Isearch1ActionPerformed

    private void disActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_disActionPerformed

    private void print1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_print1ActionPerformed
        allclean();
    }//GEN-LAST:event_print1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new Report().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new customer().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void refActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refActionPerformed

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
            java.util.logging.Logger.getLogger(sell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sell.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sell().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Isearch;
    private javax.swing.JButton Isearch1;
    private javax.swing.JTextField Ramount;
    private javax.swing.JTextField Rpay;
    private javax.swing.JLabel add;
    private javax.swing.JLabel add1;
    private javax.swing.JTextField addqunt;
    private javax.swing.JTable billtable;
    private javax.swing.JTextField cname;
    private javax.swing.JTextField csearch;
    private javax.swing.JTable ctable;
    private javax.swing.JTextField dis;
    private javax.swing.JLabel fulltotal;
    private javax.swing.JTextField iSearch;
    private javax.swing.JLabel id;
    private javax.swing.JLabel id1;
    private javax.swing.JTable itemTables;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel oneprice;
    private javax.swing.JLabel oneprice1;
    private javax.swing.JLabel originalp;
    private javax.swing.JButton print;
    private javax.swing.JButton print1;
    private javax.swing.JTextField ref;
    private javax.swing.JLabel temselect;
    // End of variables declaration//GEN-END:variables
}
