/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Master;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

/**
 *
 * @author Paul Decul
 */
public class Barang extends javax.swing.JInternalFrame {

    /**
     * Creates new form barang
     */
    private Vector<Vector<String>> data;
    private Vector<String> header;
    private Vector<Vector<String>> data1;
    private Vector<String> header1;
    public Barang() {
        JInternalFrame frame= new JInternalFrame();
        setRootPaneCheckingEnabled(false);
        javax.swing.plaf.InternalFrameUI ifu=frame.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);                
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        try {
            data = new Koneksi().getData("obat");

            header = new Vector<String>();
            header.add("KODE OBAT");
            header.add("NAMA OBAT");
            header.add("SATUAN");
            header.add("STOK");
            header.add("EXPIRE");
            header.add("HARGA JUAL");
            header.add("HARGA BELI");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initComponents();
    }
private void prosesData(String proses){
    String value;
    int row= tblBarang.getSelectedRow();
    switch (proses) {
            case "insert":
                ResultSet objCek = new Koneksi().ExecuteQuery("select kd_obat from t_obat where kd_obat ='"+tObat.getText()+"'");
                try{
                    if (objCek.next()){
                        JOptionPane.showMessageDialog(this,"Kode obat dengan kode "+tObat.getText()+
                        " Sudah ada"
                        ,"Message",JOptionPane.INFORMATION_MESSAGE);
                        tObat.requestFocus();
                    }else{
//                        SimpleDateFormat frm= new SimpleDateFormat("yyyy-MM-dd");
//                        String a=frm.format(jdExpire.getDate());
//                        String date1= a;
//                        DateTimeFormatter frmt1= DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);
//                        LocalDate dt1=LocalDate.parse(date1,frmt1);
                        value="'"+tObat.getText()+"','"+tNama.getText()+"','"+cbSatuan.getSelectedItem()+"','"+jComboBox2.getSelectedItem()+"'";
                        System.out.println(value);
                        new Koneksi().ExecuteSQl(proses, "t_obat", "kd_obat,nama_obat,satuan,kategori ", value);
                        JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                      
                    }
                }catch(Exception e){
                    
                }
                 break;    
                case "update":
                        
                value = "nama_obat = '"+ tNama.getText() +"',";                
                value += "satuan = '"+ cbSatuan.getSelectedItem().toString()+"'";
                value += "kategori = '"+ jComboBox2.getSelectedItem().toString()+"'";
//                value += "stok = "+ tStok.getText() +",";
//                value += "tgl_expire = '"+ dt1 +"'";
                
                new Koneksi().ExecuteSQl(proses, "t_obat", value, "kd_obat = '"+tObat.getText()+"'");
                JOptionPane.showMessageDialog(this, "Data berhasil di update");
                break;
                case "delete":
                int i = tblBarang.getSelectedRow();
                String data = tblBarang.getValueAt(i, 0).toString();

                new Koneksi().ExecuteSQl(proses, "t_obat", "", "kd_obat ='"+data+"'");
                JOptionPane.showMessageDialog(this, "Data berhasil di hapus");
                break;
    }
}
private void showTab(){
    try {
            data = new Koneksi().getData("obat");

            header = new Vector<String>();
            header.add("KODE OBAT");
            header.add("NAMA OBAT");
            header.add("SATUAN");
            header.add("STOK");
            header.add("EXPIRE");
            header.add("HARGA JUAL");
            header.add("HARGA BELI");
            tblBarang.setModel(new javax.swing.table.DefaultTableModel(data, header));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}
private void showTab1(){
    try {
            data1 = new Koneksi().getData("expire");

            header1 = new Vector<String>();
            header1.add("KODE OBAT");
            header1.add("NAMA OBAT");
            header1.add("SATUAN");
            header1.add("STOK");
            header1.add("EXPIRE");
            jTable4.setModel(new javax.swing.table.DefaultTableModel(data1, header1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}
private void Enable(boolean st){
    tObat.setEnabled(st);
    tNama.setEnabled(st);
//    tStok.setEnabled(st);
    cbSatuan.setEnabled(st);
    jComboBox2.setEnabled(st);
}
private void clear(){
    tObat.setText("");
    tNama.setText("");
//    tStok.setText("");
    cbSatuan.setSelectedItem("Botol");
   
    
}
private void search(){
    DefaultTableModel header= new DefaultTableModel();
            header.addColumn("KODE OBAT");
            header.addColumn("NAMA OBAT");
            header.addColumn("SATUAN");
            header.addColumn("STOK");
            header.addColumn("EXPIRE");
            header.addColumn("HARGA JUAL");
            header.addColumn("HARGA BELI");
            
        try{
          ResultSet objCek = new Koneksi().ExecuteQuery("select kd_obat,nama_obat,satuan,stok,tgl_expire,harga,harga_beli from t_obat "
                  + "where nama_obat = '"+jTextField1.getText()+"'"); 
          
              while(objCek.next()){
                header.addRow(new Object[]{
                objCek.getString(1), 
                objCek.getString(2), 
                objCek.getString(3), 
                objCek.getString(4), 
                objCek.getString(5),
                objCek.getString(6),
                objCek.getString(7)

            });
          }
          tblBarang.setModel(header);
//          }else{
//              JOptionPane.showMessageDialog(this, "Data dengan nim "+txtNimCari+" tidak ada");
//          }
          
        }catch(SQLException ex){
            
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

        jDialog1 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBarang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tObat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tNama = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbSatuan = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();

        jDialog1.setMinimumSize(new java.awt.Dimension(427, 330));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            data1,header1
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        jScrollPane5.setViewportView(jTable4);

        jButton5.setText("Keluar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton5))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setTitle("Master Barang");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "BARANG", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Obat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        tblBarang.setModel(new javax.swing.table.DefaultTableModel(
            data,header
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        tblBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBarangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblBarang);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel1.setText("Kategori");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel2.setText("Cari Nama Obat");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Botol", "Strip", "Lembar", "Tablet", "Pcs" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 2, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("* Tekan Enter");

        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton4.setText("Data Obat Expire");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel7.setText("Jenis");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Umum", "Resep" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, 146, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton4)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Obat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel3.setText("Kode Obat");

        tObat.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel5.setText("Nama Obat");

        tNama.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel6.setText("Satuan");

        cbSatuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Botol", "Strip", "Lembar", "Tablet", "Pcs" }));
        cbSatuan.setEnabled(false);

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton2.setText("Ubah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel4.setText("Kategori");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Umum", "Resep" }));
        jComboBox2.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tNama)
                            .addComponent(cbSatuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tObat)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 72, Short.MAX_VALUE)))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(jButton1.getText().equals("Tambah")){
            Enable(true);
            jButton1.setText("Simpan");
            jButton3.setText("Batal");
            jButton2.setEnabled(false);
        }else{
            if(tObat.getText().trim().isEmpty()||tNama.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Mohon lengkapi semua field");
            }else if(jButton1.getText().equals("Simpan") && jButton2.getText().equals("Ubah")){
                prosesData("insert");
                jButton1.setText("Tambah");
                jButton3.setText("Hapus");
                showTab();
                clear();
                Enable(false);
                jButton2.setEnabled(true);
            }else if(jButton2.getText().equals("Batal")){
                prosesData("update");
                showTab();
                clear();
                Enable(false);
                jButton2.setText("Ubah");
                jButton3.setEnabled(true);
                jButton1.setText("Tambah");
            }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jButton2.getText().equals("Ubah")){
           if(tblBarang.getSelectedRow()<0){
               JOptionPane.showMessageDialog(this, "Anda belum memilih data untuk diubah");
           }else{
               jButton2.setText("Batal");
           jButton1.setText("Simpan");
           jButton3.setEnabled(false);
           Enable(true);
           tObat.setEnabled(false);
        int row=tblBarang.getSelectedRow();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
//       String date=tblBarang.getValueAt(row, 4).toString();
       tObat.setText(tblBarang.getValueAt(row, 0).toString());
       tNama.setText(tblBarang.getValueAt(row, 1).toString());
       cbSatuan.setSelectedItem(tblBarang.getValueAt(row, 2).toString());
//       tStok.setText(tblBarang.getValueAt(row, 3).toString());
           }
            
        }else{
            clear();
            Enable(false);
            jButton1.setText("Tambah");
            jButton2.setText("Ubah");
            jButton3.setEnabled(true);
        }
        
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(jButton3.getText().equals("Batal")){
            clear();
            Enable(false);
            jButton3.setText("Hapus");
            jButton1.setText("Tambah");
            jButton2.setEnabled(true);
        }else{
            if(tblBarang.getSelectedRow()<0){
               JOptionPane.showMessageDialog(this, "Pilih data yang akan di hapus");
            }else{
                prosesData("delete");
                showTab();
            }  
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBarangMouseClicked
      
    }//GEN-LAST:event_tblBarangMouseClicked

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
         if(evt.getKeyCode()== KeyEvent.VK_ENTER){
             if(jTextField1.getText().trim().isEmpty()){
                 JOptionPane.showMessageDialog(this, "Field cari belum di isi");
             }else{
                 search();
             }
         }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if(jComboBox1.getSelectedItem().equals("-")){
            DefaultTableModel header= new DefaultTableModel();
            header.addColumn("KODE OBAT");
            header.addColumn("NAMA OBAT");
            header.addColumn("SATUAN");
            header.addColumn("STOK");
            header.addColumn("EXPIRE");
            header.addColumn("HARGA JUAL");
            header.addColumn("HARGA BELI");
            
        try{
          ResultSet objCek = new Koneksi().ExecuteQuery("select kd_obat,nama_obat,satuan,stok,tgl_expire,harga,harga_beli from t_obat"); 
          
              while(objCek.next()){
                header.addRow(new Object[]{
                objCek.getString(1), 
                objCek.getString(2), 
                objCek.getString(3), 
                objCek.getString(4), 
                objCek.getString(5),
                objCek.getString(6),
                objCek.getString(7)

            });
          }
          tblBarang.setModel(header);
//          }else{
//              JOptionPane.showMessageDialog(this, "Data dengan nim "+txtNimCari+" tidak ada");
//          }
          
        }catch(SQLException ex){
            
        }
        }else{
            DefaultTableModel header= new DefaultTableModel();
            header.addColumn("KODE OBAT");
            header.addColumn("NAMA OBAT");
            header.addColumn("SATUAN");
            header.addColumn("STOK");
            header.addColumn("EXPIRE");
            header.addColumn("HARGA JUAL");
            header.addColumn("HARGA BELI");
            
        try{
          ResultSet objCek = new Koneksi().ExecuteQuery("select kd_obat,nama_obat,satuan,stok,tgl_expire,harga,harga_beli from t_obat "
                  + "where satuan = '"+jComboBox1.getSelectedItem().toString()+"'"); 
          
              while(objCek.next()){
                header.addRow(new Object[]{
                objCek.getString(1), 
                objCek.getString(2), 
                objCek.getString(3), 
                objCek.getString(4), 
                objCek.getString(5),
                objCek.getString(6),
                objCek.getString(7)

            });
          }
          tblBarang.setModel(header);
//          }else{
//              JOptionPane.showMessageDialog(this, "Data dengan nim "+txtNimCari+" tidak ada");
//          }
          
        }catch(SQLException ex){
            
        }
        }
        
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jDialog1.setLocationRelativeTo(this);
        showTab1();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
    if(jComboBox3.getSelectedItem().equals("-")){
            DefaultTableModel header= new DefaultTableModel();
            header.addColumn("KODE OBAT");
            header.addColumn("NAMA OBAT");
            header.addColumn("SATUAN");
            header.addColumn("STOK");
            header.addColumn("EXPIRE");
            header.addColumn("HARGA JUAL");
            header.addColumn("HARGA BELI");
            
        try{
          ResultSet objCek = new Koneksi().ExecuteQuery("select kd_obat,nama_obat,satuan,stok,tgl_expire,harga,harga_beli from t_obat"); 
          
              while(objCek.next()){
                header.addRow(new Object[]{
                objCek.getString(1), 
                objCek.getString(2), 
                objCek.getString(3), 
                objCek.getString(4), 
                objCek.getString(5),
                objCek.getString(6),
                objCek.getString(7)

            });
          }
          tblBarang.setModel(header);
//          }else{
//              JOptionPane.showMessageDialog(this, "Data dengan nim "+txtNimCari+" tidak ada");
//          }
          
        }catch(SQLException ex){
            
        }
        }else{
            DefaultTableModel header= new DefaultTableModel();
            header.addColumn("KODE OBAT");
            header.addColumn("NAMA OBAT");
            header.addColumn("SATUAN");
            header.addColumn("STOK");
            header.addColumn("EXPIRE");
            header.addColumn("HARGA JUAL");
            header.addColumn("HARGA BELI");
            
        try{
          ResultSet objCek = new Koneksi().ExecuteQuery("select kd_obat,nama_obat,satuan,stok,tgl_expire,harga,harga_beli from t_obat "
                  + "where kategori = '"+jComboBox3.getSelectedItem().toString()+"'"); 
          
              while(objCek.next()){
                header.addRow(new Object[]{
                objCek.getString(1), 
                objCek.getString(2), 
                objCek.getString(3), 
                objCek.getString(4), 
                objCek.getString(5),
                objCek.getString(6),
                objCek.getString(7)

            });
          }
          tblBarang.setModel(header);
//          }else{
//              JOptionPane.showMessageDialog(this, "Data dengan nim "+txtNimCari+" tidak ada");
//          }
          
        }catch(SQLException ex){
            
        }
        }
        
                 
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbSatuan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField tNama;
    private javax.swing.JTextField tObat;
    private javax.swing.JTable tblBarang;
    // End of variables declaration//GEN-END:variables
}
