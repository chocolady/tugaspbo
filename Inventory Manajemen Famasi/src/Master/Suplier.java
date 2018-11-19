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
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

/**
 *
 * @author Paul Decul
 */
public class Suplier extends javax.swing.JInternalFrame {

    /**
     * Creates new form Suplier
     */
    private Vector<Vector<String>> data;
    private Vector<String> header;
    public Suplier() {
        JInternalFrame frame= new JInternalFrame();
        setRootPaneCheckingEnabled(false);
        javax.swing.plaf.InternalFrameUI ifu=frame.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);                
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        try {
            data = new Koneksi().getData("suplier");

            header = new Vector<String>();
            header.add("KODE SUPPLIER");
            header.add("NAMA");
            header.add("ALAMAT");
            header.add("TLP");
            header.add("HP");
            header.add("EMAIL");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initComponents();
    }
private void prosesData(String proses){
    String value;
    int row= tblSupplier.getSelectedRow();
    switch (proses) {
            case "insert":
                ResultSet objCek = new Koneksi().ExecuteQuery("select kd_suplier from t_suplier where kd_suplier ='"+tSuplier.getText()+"'");
                try{
                    if (objCek.next()){
                        JOptionPane.showMessageDialog(this,"Kode suplier dengan kode "+tSuplier.getText()+
                        " Sudah ada"
                        ,"Message",JOptionPane.INFORMATION_MESSAGE);
                        tSuplier.requestFocus();
                    }else{
                      
                        value="'"+tSuplier.getText()+"','"+tNama.getText()+"','"+tAlamat.getText()+"',"
                                + "'"+tNo.getText()+"','"+tHp.getText()+"','"+tEmail.getText()+"'";
                        System.out.println(value);
                        new Koneksi().ExecuteSQl(proses, "t_suplier", "kd_suplier,nama,alamat,no_tlp,no_hp,email ", value);
                        JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                      
                    }
                }catch(Exception e){
                    
                }
                 break;    
                case "update":
                value = "nama = '"+ tNama.getText() +"',";                
                value += "alamat = '"+ tAlamat.getText()+"',";
                value += "no_tlp = "+ tNo.getText() +",";
                value += "no_hp = '"+ tHp.getText() +"',";
                value += "email = '"+ tEmail.getText() +"'";
                
                new Koneksi().ExecuteSQl(proses, "t_suplier", value, "kd_suplier = '"+tSuplier.getText()+"'");
                JOptionPane.showMessageDialog(this, "Data berhasil di update");
                break;
                case "delete":
                int i = tblSupplier.getSelectedRow();
                String data = tblSupplier.getValueAt(i, 0).toString();

                new Koneksi().ExecuteSQl(proses, "t_suplier", "", "kd_suplier ='"+data+"'");
                JOptionPane.showMessageDialog(this, "Data berhasil di hapus");
                break;
    }
}
private void showTab(){
    try {
            data = new Koneksi().getData("suplier");

            header = new Vector<String>();
            header.add("KODE SUPPLIER");
            header.add("NAMA");
            header.add("ALAMAT");
            header.add("TLP");
            header.add("HP");
            header.add("EMAIL");
            tblSupplier.setModel(new javax.swing.table.DefaultTableModel(data, header));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}
private void Enable(boolean st){
    tSuplier.setEnabled(st);
    tNama.setEnabled(st);
    tAlamat.setEnabled(st);
    tNo.setEnabled(st);
    tHp.setEnabled(st);
    tEmail.setEnabled(st);
}
private void clear(){
    tSuplier.setText("");
    tNama.setText("");
    tAlamat.setText("");
    tNo.setText("");
    tHp.setText("");
    tEmail.setText("");
    
}
private void search(){
    DefaultTableModel header= new DefaultTableModel();
            header.addColumn("KODE SUPPLIER");
            header.addColumn("NAMA");
            header.addColumn("ALAMAT");
            header.addColumn("TLP");
            header.addColumn("HP");
            header.addColumn("EMAIL");
            
        try{
          ResultSet objCek = new Koneksi().ExecuteQuery("select * from t_suplier "
                  + "where nama = '"+tCari.getText()+"' or kd_suplier='"+tCari.getText()+"'"); 
          
              while(objCek.next()){
                header.addRow(new Object[]{
                objCek.getString(1), 
                objCek.getString(2), 
                objCek.getString(3), 
                objCek.getString(4), 
                objCek.getString(5),
                objCek.getString(6)

            });
          }
          tblSupplier.setModel(header);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSupplier = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tSuplier = new javax.swing.JTextField();
        tNama = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tAlamat = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        tNo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tHp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tEmail = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setTitle("Master Suplier");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "SUPPLIER", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Supplier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel1.setText("Cari Nama Supplier/ Kode");

        tCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tCariKeyPressed(evt);
            }
        });

        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
            data,header
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        tblSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSupplierMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSupplier);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
            .addComponent(jScrollPane4)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Supplier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel2.setText("Kode Supplier");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel3.setText("Nama Supplier");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel4.setText("Alamat");

        tSuplier.setEnabled(false);

        tNama.setEnabled(false);

        tAlamat.setColumns(20);
        tAlamat.setRows(5);
        tAlamat.setEnabled(false);
        jScrollPane2.setViewportView(tAlamat);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel5.setText("No Tlp");

        tNo.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel6.setText("No Hp");

        tHp.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel7.setText("Email");

        tEmail.setEnabled(false);

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tEmail)
                    .addComponent(tHp)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(tNo)
                    .addComponent(tNama)
                    .addComponent(tSuplier))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tHp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 65, Short.MAX_VALUE))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                if(jButton2.getText().equals("Ubah")){
           if(tblSupplier.getSelectedRow()<0){
               JOptionPane.showMessageDialog(this, "Anda belum memilih data untuk diubah");
           }else{
               jButton2.setText("Batal");
           jButton1.setText("Simpan");
           jButton3.setEnabled(false);
           Enable(true);
           tSuplier.setEnabled(false);
        int row=tblSupplier.getSelectedRow();
       
       tSuplier.setText(tblSupplier.getValueAt(row, 0).toString());
       tNama.setText(tblSupplier.getValueAt(row, 1).toString());
       tAlamat.setText(tblSupplier.getValueAt(row, 2).toString());
       tNo.setText(tblSupplier.getValueAt(row, 3).toString());
       tHp.setText(tblSupplier.getValueAt(row, 4).toString());
       tEmail.setText(tblSupplier.getValueAt(row, 5).toString());
           }
            
        }else{
            clear();
            Enable(false);
            jButton1.setText("Tambah");
            jButton2.setText("Ubah");
            jButton3.setEnabled(true);
        }
        
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplierMouseClicked

    }//GEN-LAST:event_tblSupplierMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      if(jButton1.getText().equals("Tambah")){
            Enable(true);
            tSuplier.requestFocus();
            jButton1.setText("Simpan");
            jButton3.setText("Batal");
            jButton2.setEnabled(false);
        }else{
            if(tSuplier.getText().trim().isEmpty()||tNama.getText().trim().isEmpty()
                ||tAlamat.getText().trim().isEmpty()||tNo.getText().trim().isEmpty()
                ||tHp.getText().trim().isEmpty()||tEmail.getText().trim().isEmpty()){
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       if(jButton3.getText().equals("Batal")){
            clear();
            Enable(false);
            jButton3.setText("Hapus");
            jButton1.setText("Tambah");
            jButton2.setEnabled(true);
        }else{
            if(tblSupplier.getSelectedRow()<0){
               JOptionPane.showMessageDialog(this, "Pilih daa yang akan di hapus");
            }else{
                prosesData("delete");
                showTab();
            }  
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tCariKeyPressed
         if(evt.getKeyCode()== KeyEvent.VK_ENTER){
             if(tCari.getText().trim().isEmpty()){
                 DefaultTableModel header= new DefaultTableModel();
            header.addColumn("KODE SUPPLIER");
            header.addColumn("NAMA");
            header.addColumn("ALAMAT");
            header.addColumn("TLP");
            header.addColumn("HP");
            header.addColumn("EMAIL");
            
        try{
          ResultSet objCek = new Koneksi().ExecuteQuery("select * from t_suplier "); 
          
              while(objCek.next()){
                header.addRow(new Object[]{
                objCek.getString(1), 
                objCek.getString(2), 
                objCek.getString(3), 
                objCek.getString(4), 
                objCek.getString(5),
                objCek.getString(6)

            });
          }
          tblSupplier.setModel(header);
//          }else{
//              JOptionPane.showMessageDialog(this, "Data dengan nim "+txtNimCari+" tidak ada");
//          }
          
        }catch(SQLException ex){
            
        }
             }else{
                 search();
             }
         }
    }//GEN-LAST:event_tCariKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea tAlamat;
    private javax.swing.JTextField tCari;
    private javax.swing.JTextField tEmail;
    private javax.swing.JTextField tHp;
    private javax.swing.JTextField tNama;
    private javax.swing.JTextField tNo;
    private javax.swing.JTextField tSuplier;
    private javax.swing.JTable tblSupplier;
    // End of variables declaration//GEN-END:variables
}
