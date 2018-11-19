/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaksi;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

/**
 *
 * @author Paul Decul
 */
public class Pembelian extends javax.swing.JInternalFrame {

    /**
     * Creates new form Pembelian
     */
    private Vector<Vector<String>> data;
    private Vector<String> header;
    private Vector<Vector<String>> data1;
    private Vector<String> header1;
    private DefaultTableModel model1;
    private DefaultTableModel model;
    public Pembelian() {
        JInternalFrame frame= new JInternalFrame();
        setRootPaneCheckingEnabled(false);
        javax.swing.plaf.InternalFrameUI ifu=frame.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);                
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
         try {
            data1 = new Koneksi().getData("penerimaan");

            header1 = new Vector<String>();
            header1.add("KODE PEMESANAN");           
//            jTable4.setModel(new javax.swing.table.DefaultTableModel(data1, header1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initComponents();
        getTanggal();
        setJTable1();
        setJTable();
        jLabel9.setVisible(false);
    }
    private void getTanggal(){
        Date date=new Date();
        SimpleDateFormat frm= new SimpleDateFormat("yyyy-MM-dd");
        String dt= frm.format(date);
        tgl.setText(dt);
    }
   private void showTab(){
     try {
            data = new Koneksi().getData("pemesanan");

            header = new Vector<String>();
            header.add("KODE PEMESANAN");           
            jTable2.setModel(new javax.swing.table.DefaultTableModel(data, header));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}
    private void showTab1(){
     try {
            data1 = new Koneksi().getData("penerimaan");

            header1 = new Vector<String>();
            header1.add("KODE PEMESANAN");           
            jTable4.setModel(new javax.swing.table.DefaultTableModel(data1, header1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}
   private void hapusDataTable1() {
        int row = model1.getRowCount();
        for (int i = 0; i < row; i++) {
            model1.removeRow(0);
        }
     }
   private void hapusDataTable() {
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
     }
   private void setJTable1() {
        String[] JudulKolom = {"KODE OBAT", "NAMA OBAT", "SATUAN", "JUMLAH", "SUPLIER", "TGL PESAN"};
        model1 = new DefaultTableModel(null, JudulKolom) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        jTable3.setModel(model1);
        jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(110);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(110);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(110);
        jTable3.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable3.getColumnModel().getColumn(5).setPreferredWidth(110);
        
    }
   private void setJTable() {
        String[] JudulKolom = {"TGL TERIMA","KODE OBAT" , "NAMA OBAT", "SATUAN", "JUMLAH", "HARGA", "NAMA SUPLIER"};
        model = new DefaultTableModel(null, JudulKolom) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        jTable1.setModel(model);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(110);
    }
   private void prosesData(String proses){
    String value;
    String values;
    String values1;
    int nilai,nilai1;
    switch (proses) {
            case "insert":
                ResultSet cek=new Koneksi().ExecuteQuery("select stok from t_obat where kd_obat ='"+tKode.getText()+"'");
                ResultSet cek1=new Koneksi().ExecuteQuery("select jumlah from t_pemesanan where kd_pesan ='"+tPemesanan.getText()+"' and kode='"+tKode.getText()+"'");
            try {    
                if(cek.next()){
                nilai=Integer.parseInt(tJumlah.getText())+cek.getInt(1);
                }else{
                nilai=0;
                }
                if(cek1.next()){
                if(cek1.getInt(1) <= Integer.parseInt(tJumlah.getText())){
                    nilai1=0;
                }else{
                    nilai1=cek1.getInt(1)-Integer.parseInt(tJumlah.getText());
                }
                }else{
                nilai1=0;
                }
                        SimpleDateFormat frm= new SimpleDateFormat("yyyy-MM-dd");
                        String a=frm.format(jdExpire.getDate());
                        String date1= a;
                        DateTimeFormatter frmt1= DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);
                        LocalDate dt1=LocalDate.parse(date1,frmt1);
                        value="'"+tPemesanan.getText()+"','"+tgl.getText()+"','"+tKode.getText()+"',"+tJumlah.getText()+","
                                + ""+tHarga.getText()+",'"+jTextField2.getText()+"'";
                        values="stok = "+ nilai+",";
                        values +="harga ="+ tHarga.getText()+",";
                        values +="tgl_expire ='"+ dt1+"',";
                        values +="harga_beli ="+ tBeli.getText()+"";
                        values1="jumlah= "+ nilai1+"";
                        System.out.println(value);
                        new Koneksi().ExecuteSQl(proses, "t_pembelian", "kd_pesan,tgl_penerimaan,kd_obat,jumlah_terima,harga_terima,nama_suplier ", value);
                        new Koneksi().ExecuteSQl("update", "t_obat", values, "kd_obat = '"+tKode.getText()+"'");
                        new Koneksi().ExecuteSQl("update", "t_pemesanan", values1, "kd_pesan = '"+tPemesanan.getText()+"' and kode='"+tKode.getText()+"'");
                        JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                        }catch (SQLException ex) {
                        Logger.getLogger(Pembelian.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 break;    
//                case "update":
//                value = "nama_obat = '"+ tNama.getText() +"',";                
//                value += "satuan = '"+ cbSatuan.getSelectedItem().toString()+"',";
//                value += "jumlah = "+ tJumlah.getText() +"";
//                int row=tbPemesanan.getSelectedRow();
//                new Koneksi().ExecuteSQl(proses, "t_pemesanan", value, "kd_pesan = '"+tbPemesanan.getValueAt(row, 0)+"'");
//                JOptionPane.showMessageDialog(this, "Data berhasil di update");
//                break;
                case "delete":
                int i = jTable1.getSelectedRow();
                int row=jTable4.getSelectedRow();
                String data = jTable1.getValueAt(i, 1).toString();
                String data1 = jTable4.getValueAt(row, 0).toString();
                new Koneksi().ExecuteSQl(proses, "t_pembelian", "", "kd_pesan ='"+data1+"' and kd_obat='"+data+"'");
                showTab1();
                JOptionPane.showMessageDialog(this, "Data berhasil di hapus");
                break;
    }
}
   private void clear(){
       tPemesanan.setText(null);
       tKode.setText(null);
       tNama.setText(null);
       tJumlah.setText(null);
       tHarga.setText(null);
       jLabel3.setText("0");
       jdExpire.setDate(null);
       tBeli.setText(null);
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tgl = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        tNama = new javax.swing.JTextField();
        cbSatuan = new javax.swing.JComboBox();
        tJumlah = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tHarga = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tPemesanan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tKode = new javax.swing.JTextField();
        jdExpire = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tBeli = new javax.swing.JTextField();

        jDialog1.setMinimumSize(new java.awt.Dimension(567, 410));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            data,header
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton1.setText("Batal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton5.setText("Pilih");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton5)))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "PENERIMAAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Penerimaan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel1.setText("Tanggal Penerimaan");

        tgl.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel2.setText("Nama Supplier");

        jTextField2.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Penerimaan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jScrollPane1.setViewportView(jTable1);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            data1,header1
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total Harga", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Penerimaan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton2.setText("Simpan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton4.setText("Hapus");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tNama.setEnabled(false);

        cbSatuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Botol", "Lembar", "Strip", "Tablet", "Pcs" }));
        cbSatuan.setEnabled(false);

        tJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tJumlahKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel5.setText("Jumlah");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel6.setText("Satuan");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel7.setText("Nama Obat");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel8.setText("Harga Jual");

        tHarga.setEditable(false);
        tHarga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tHargaMousePressed(evt);
            }
        });
        tHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tHargaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tHargaKeyReleased(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton3.setText("Lihat Detail Pesanan");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 2, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 102, 0));
        jLabel9.setText("* kolom harga/jumlah masih kosong");

        jLabel10.setText("No Pemesanan");

        tPemesanan.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel11.setText("Kode Obat");

        tKode.setEnabled(false);

        jdExpire.setDateFormatString("yyyy-MM-dd");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel12.setText("Tgl Expire");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel4.setText("Harga Beli");

        tBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tBeliKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tPemesanan, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                    .addComponent(tKode)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jdExpire, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(tJumlah)
                                    .addComponent(cbSatuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tNama)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tBeli)
                                    .addComponent(tHarga))))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jdExpire, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        hapusDataTable1();
        int row = jTable2.getSelectedRow();
        try {
            ResultSet objCek = new Koneksi().ExecuteQuery("SELECT `kode`, `nama_obat`, `satuan`, `jumlah`, `kd_suplier`, `tgl_pemesanan` FROM `t_pemesanan` WHERE kd_pesan"
                    + "='" + jTable2.getValueAt(row, 0) + "' and jumlah>0");
            
            while (objCek.next()) {
                model1.addRow(new Object[]{
                    objCek.getString(1),
                    objCek.getString(2),
                    objCek.getString(3),
                    objCek.getString(4),
                    objCek.getString(5),
                    objCek.getString(6)
                 
                
                });
            }
            
            jTable3.setModel(model1);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       int row=jTable3.getSelectedRow();
       int r=jTable2.getSelectedRow();
       if(row<0){
           JOptionPane.showMessageDialog(this, "anda belum memilih data pemesanan");
       }else{
           tPemesanan.setText(jTable2.getValueAt(r, 0).toString());
           tKode.setText(jTable3.getValueAt(row, 0).toString());
           tNama.setText(jTable3.getValueAt(row, 1).toString());
           cbSatuan.setSelectedItem(jTable3.getValueAt(row, 2).toString());
           tJumlah.setText(jTable3.getValueAt(row, 3).toString());
           jTextField2.setText(jTable3.getValueAt(row, 4).toString());
           jDialog1.setVisible(false);
       }
      
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jDialog1.setLocationRelativeTo(this);
        showTab();
        hapusDataTable1();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tHargaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tHargaMousePressed
        
    }//GEN-LAST:event_tHargaMousePressed

    private void tHargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tHargaKeyPressed
       if(evt.getKeyCode()== KeyEvent.VK_ENTER){
             int total;
           if(tJumlah.getText().trim().isEmpty()||tHarga.getText().trim().isEmpty()){
               jLabel9.setVisible(true);
           }else{
               total=Integer.parseInt(tJumlah.getText())*Integer.parseInt(tHarga.getText());
               jLabel9.setVisible(false);
               jLabel3.setText(String.valueOf(total));
           }
       }
    }//GEN-LAST:event_tHargaKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(tJumlah.getText().trim().isEmpty()||tHarga.getText().trim().isEmpty()||tKode.getText().trim().isEmpty()||
                jdExpire.getDate()==null||tBeli.getText().trim().isEmpty()){
//            jLabel9.setVisible(true);
            JOptionPane.showMessageDialog(this, "Mohon lengkapi semua field");
        }else{
            prosesData("insert");
            showTab1();
            clear();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        hapusDataTable();
        int row = jTable4.getSelectedRow();
        try {
            ResultSet objCek = new Koneksi().ExecuteQuery("SELECT `tgl_penerimaan`,t_pembelian.kd_obat, `nama_obat`, `satuan`, `jumlah_terima`, `harga_terima`, `nama_suplier` FROM `t_pembelian` "
                    + "join t_obat on t_pembelian.kd_obat=t_obat.kd_obat WHERE kd_pesan"
                    + "='" + jTable4.getValueAt(row, 0) + "'");
            
            while (objCek.next()) {
                model.addRow(new Object[]{
                    objCek.getString(1),
                    objCek.getString(2),
                    objCek.getString(3),
                    objCek.getString(4),
                    objCek.getString(5),
                    objCek.getString(6),
                    objCek.getString(7)
                
                });
            }
            
            jTable1.setModel(model);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jTable4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
           if(jTable1.getSelectedRow()<0){
               JOptionPane.showMessageDialog(this, "Pilih data yang akan di hapus");
            }else{
                prosesData("delete");
            hapusDataTable();
            int row = jTable4.getSelectedRow();
            try {
                ResultSet objCek = new Koneksi().ExecuteQuery("SELECT `tgl_penerimaan`,t_pembelian.kd_obat, `nama_obat`, `satuan`, `jumlah_terima`, `harga_terima`, `nama_suplier` FROM `t_pembelian` "
                        + "join t_obat on t_pembelian.kd_obat=t_obat.kd_obat WHERE kd_pesan"
                        + "='" + jTable4.getValueAt(row, 0) + "'");

                while (objCek.next()) {
                    model.addRow(new Object[]{
                        objCek.getString(1),
                        objCek.getString(2),
                        objCek.getString(3),
                        objCek.getString(4),
                        objCek.getString(5),
                        objCek.getString(6),
                        objCek.getString(7)

                    });
                }

                jTable1.setModel(model);

            } catch (SQLException ex) {
//                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tHargaKeyReleased
        if(tHarga.getText().trim().isEmpty()){
            jLabel3.setText("0");
        }else{
           int total;
           if(tJumlah.getText().trim().isEmpty()||tHarga.getText().trim().isEmpty()){
               jLabel9.setVisible(true);
           }else{
               total=Integer.parseInt(tJumlah.getText())*Integer.parseInt(tHarga.getText());
               jLabel9.setVisible(false);
               jLabel3.setText(String.valueOf(total));
            }
        }
    }//GEN-LAST:event_tHargaKeyReleased

    private void tBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tBeliKeyReleased
        int harga,total;
        if(tBeli.getText().trim().isEmpty()){
            harga=0;
        }else{
            harga=(int) Integer.parseInt(tBeli.getText())+(Integer.parseInt(tBeli.getText())*25/100);
        }
        tHarga.setText(String.valueOf(harga));
        if(tHarga.getText().trim().isEmpty()){
            jLabel3.setText("0");
        }else{
           if(tJumlah.getText().trim().isEmpty()||tHarga.getText().trim().isEmpty()){
               jLabel9.setVisible(true);
           }else{
               total=Integer.parseInt(tJumlah.getText())*Integer.parseInt(tHarga.getText());
               jLabel9.setVisible(false);
               jLabel3.setText(String.valueOf(total));
            }
        }
    }//GEN-LAST:event_tBeliKeyReleased

    private void tJumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tJumlahKeyReleased
        if(tHarga.getText().trim().isEmpty()){
            jLabel3.setText("0");
        }else{
            int total;
           if(tJumlah.getText().trim().isEmpty()||tHarga.getText().trim().isEmpty()){
               jLabel9.setVisible(true);
           }else{
               total=Integer.parseInt(tJumlah.getText())*Integer.parseInt(tHarga.getText());
               jLabel9.setVisible(false);
               jLabel3.setText(String.valueOf(total));
            }
        }
    }//GEN-LAST:event_tJumlahKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbSatuan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JDialog jDialog1;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField2;
    private com.toedter.calendar.JDateChooser jdExpire;
    private javax.swing.JTextField tBeli;
    private javax.swing.JTextField tHarga;
    private javax.swing.JTextField tJumlah;
    private javax.swing.JTextField tKode;
    private javax.swing.JTextField tNama;
    private javax.swing.JTextField tPemesanan;
    private javax.swing.JTextField tgl;
    // End of variables declaration//GEN-END:variables
}
