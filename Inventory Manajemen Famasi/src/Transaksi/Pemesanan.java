/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaksi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

/**
 *
 * @author DreamTrue
 */
public class Pemesanan extends javax.swing.JInternalFrame {

    /**
     * Creates new form Pemesanan
     */
    Date date= new Date();
    private Vector<Vector<String>> data;
    private Vector<String> header;
    private Vector<Vector<String>> data1;
    private Vector<String> header1;
    private DefaultTableModel model;
    private DefaultTableModel model1;
    public Pemesanan() {
        JInternalFrame frame= new JInternalFrame();
        setRootPaneCheckingEnabled(false);
        javax.swing.plaf.InternalFrameUI ifu=frame.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);                
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        try {
            data = new Koneksi().getData("pemesanan");

            header = new Vector<String>();
            header.add("KODE PEMESANAN");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initComponents();
        generateKode();
        getTanggal();
        setJTable();
        setJTable1();
    }
    private void generateKode(){
        SimpleDateFormat thn=new SimpleDateFormat("yy");
        SimpleDateFormat bln=new SimpleDateFormat("MM");
        String bulan=bln.format(date);
        String tahun=thn.format(date);
        String prefix = "PM"+tahun+bulan;
        try{
            ResultSet objCek = new Koneksi().ExecuteQuery("Select kd_pesan from t_pemesanan order by kd_pesan desc limit 1");
            String nomor="";
            int sequen;
            if(objCek.next()){
                nomor=objCek.getString("kd_pesan");
                sequen = Integer.valueOf(nomor.substring(6, 9))+1;
            }else{
                sequen = 1;
            }
            
            if(sequen<10){
                nomor = prefix + "00" + sequen;
            }else if(sequen<100){
                nomor = prefix + "0" + sequen;
            }else if(sequen<1000){
                nomor = prefix + sequen;
            }else{
                nomor = prefix + "error";
            }
            tPemesanan.setText(nomor);
        }
        catch (SQLException sqle) {
            System.out.println("Proses Query Gagal = " + sqle);
            System.exit(0);
        }
    }
    private void getData(){
        DefaultTableModel header= new DefaultTableModel();
            header.addColumn("KODE OBAT");
            header.addColumn("NAMA OBAT");
            header.addColumn("SATUAN");
            header.addColumn("STOK"); 
            
        try{
          ResultSet objCek = new Koneksi().ExecuteQuery("select kd_obat,nama_obat,satuan,stok from t_obat "); 
          
              while(objCek.next()){
                header.addRow(new Object[]{
                objCek.getString(1), 
                objCek.getString(2), 
                objCek.getString(3), 
                objCek.getString(4)
              
            });
          }
          tbBarang.setModel(header);
//          }else{
//              JOptionPane.showMessageDialog(this, "Data dengan nim "+txtNimCari+" tidak ada");
//          }
          
        }catch(SQLException ex){
            
        }
    }
    private void prosesData(String proses){
    String value;
    switch (proses) {
            case "insert":
                ResultSet objCek = new Koneksi().ExecuteQuery("select kd_pesan from t_pemesanan where kd_pesan ='"+tPemesanan.getText()+"'");
                try{
                    if (objCek.next()){
                        JOptionPane.showMessageDialog(this,"Kode pemesanan dengan kode "+tPemesanan.getText()+
                        " Sudah ada"
                        ,"Message",JOptionPane.INFORMATION_MESSAGE);
                        tPemesanan.requestFocus();
                    }else{
                        String date=tgl.getText();
                        DateTimeFormatter frmt1= DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);
                        LocalDate dt1=LocalDate.parse(date,frmt1);
                          for (int i = 0; i < model.getRowCount(); i++) {
                        value="'"+tPemesanan.getText()+"','"+tbPemesanan1.getValueAt(i, 0).toString()+"','"+tbPemesanan1.getValueAt(i, 1).toString()+"','"+tbPemesanan1.getValueAt(i, 2).toString()+"',"
                                + ""+tbPemesanan1.getValueAt(i, 3).toString()+",'"+dt1+"','"+tbPemesanan1.getValueAt(i, 4)+"'";
                        System.out.println(value);
                        new Koneksi().ExecuteSQl(proses, "t_pemesanan", "kd_pesan,kode,nama_obat,satuan,jumlah,tgl_pemesanan,kd_suplier ", value);
                          }
                        JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                    }
                }catch(Exception e){
                    
                }
                 break;    
                case "update":
                value = "nama_obat = '"+ tNama.getText() +"',";                
                value += "satuan = '"+ cbSatuan.getSelectedItem().toString()+"',";
                value += "jumlah = "+ tJumlah.getText() +"";
                int row=tbPemesanan.getSelectedRow();
                new Koneksi().ExecuteSQl(proses, "t_pemesanan", value, "kd_pesan = '"+tbPemesanan.getValueAt(row, 0)+"'");
                JOptionPane.showMessageDialog(this, "Data berhasil di update");
                break;
                case "delete":
                int i = tbPemesanan.getSelectedRow();
                String data = tbPemesanan.getValueAt(i, 0).toString();

                new Koneksi().ExecuteSQl(proses, "t_pemesanan", "", "kd_pesan ='"+data+"'");
                JOptionPane.showMessageDialog(this, "Data berhasil di hapus");
                break;
    }
}
    private void showTab(){
     try {
            data = new Koneksi().getData("pemesanan");

            header = new Vector<String>();
            header.add("KODE PEMESANAN");           
            tbPemesanan2.setModel(new javax.swing.table.DefaultTableModel(data, header));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
}
    private void clear(){
        tKode.setText(null);
        tNama.setText(null);
        tJumlah.setText(null);
        cbSatuan.setSelectedItem(1);
        tKode.setText(null);
    }
    private void getTanggal(){
        Date date=new Date();
        SimpleDateFormat frm= new SimpleDateFormat("yyyy-MM-dd");
        String dt= frm.format(date);
        tgl.setText(dt);
    }
     private void getDataSuplier(){
        try {
            data1 = new Koneksi().getData("suplier");

            header1 = new Vector<String>();
            header1.add("KODE SUPLIER");
            header1.add("NAMA");
            header1.add("ALAMAT");
            header1.add("TLP");
            header1.add("HP");
            header1.add("EMAIL");
            tblSuplier.setModel(new javax.swing.table.DefaultTableModel(data1, header1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     private void tempTable(){
        String sup=tSuplier.getText();
        String kode=tKode.getText();
        String nama =tNama.getText();
        String satuan=cbSatuan.getSelectedItem().toString();
        String jumlah=tJumlah.getText();
       
        int row = model.getRowCount();
        if(row == 0){
            Object Data[]={kode,nama,satuan,jumlah,sup};
            model.addRow(Data);   
        }else{
            for (int i = 0; i < row; i++) {
                if(tKode.getText().equals(model.getValueAt(i, 0))){
                     JOptionPane.showMessageDialog(this, "Data obat sudah masuk data pemesanan sementara");
                }else{
                    Object Data[]={kode,nama,satuan,jumlah,sup};
                     model.addRow(Data); 
                }     
            }  
        }   
    }
    private void setJTable() {
        String [] JudulKolom={"KODE","NAMA OBAT","JENIS","JUMLAH","SUPLIER"};
        model = new DefaultTableModel(null, JudulKolom){
                      boolean[] canEdit = new boolean [] { false, false, false, false, false};
                      @Override
                      public boolean isCellEditable(int rowIndex, int columnIndex) {
                       return canEdit [columnIndex];
                      }
                  };
        tbPemesanan1.setModel(model);
        tbPemesanan1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbPemesanan1.getColumnModel().getColumn(0).setPreferredWidth(150);
        tbPemesanan1.getColumnModel().getColumn(1).setPreferredWidth(150);
        tbPemesanan1.getColumnModel().getColumn(2).setPreferredWidth(150);
        tbPemesanan1.getColumnModel().getColumn(3).setPreferredWidth(150);
        tbPemesanan1.getColumnModel().getColumn(4).setPreferredWidth(150);
    }
    private void hapusDataTable(){
        int row = model.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }
     private void hapusDataTable1() {
        int row = model1.getRowCount();
        for (int i = 0; i < row; i++) {
            model1.removeRow(0);
        }
     }
      private void setJTable1() {
        String[] JudulKolom = {"KODE OBAT", "NAMA OBAT", "SATUAN", "JUMLAH", "TANGGAL PESAN"};
        model1 = new DefaultTableModel(null, JudulKolom) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tbPemesanan.setModel(model1);
        tbPemesanan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbPemesanan.getColumnModel().getColumn(0).setPreferredWidth(110);
        tbPemesanan.getColumnModel().getColumn(1).setPreferredWidth(110);
        tbPemesanan.getColumnModel().getColumn(2).setPreferredWidth(110);
        tbPemesanan.getColumnModel().getColumn(3).setPreferredWidth(110);
        tbPemesanan.getColumnModel().getColumn(4).setPreferredWidth(110);
        
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tbBarang = new javax.swing.JTable();
        jDialog2 = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSuplier = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPemesanan = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbPemesanan2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tPemesanan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tgl = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tSuplier = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        tKode = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tNama = new javax.swing.JTextField();
        cbSatuan = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tJumlah = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbPemesanan1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jDialog1.setMinimumSize(new java.awt.Dimension(566, 397));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DAFTAR OBAT", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N
        jPanel4.setMaximumSize(new java.awt.Dimension(565, 397));
        jPanel4.setMinimumSize(new java.awt.Dimension(565, 397));

        tbBarang.setModel(new javax.swing.table.DefaultTableModel(
            data1,header1
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        tbBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbBarang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
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

        jDialog2.setMaximumSize(new java.awt.Dimension(514, 343));
        jDialog2.setMinimumSize(new java.awt.Dimension(514, 343));

        tblSuplier.setModel(new javax.swing.table.DefaultTableModel(
            data1,header1
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        tblSuplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSuplierMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSuplier);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "PEMESANAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Pemesanan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        tbPemesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemesananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPemesanan);

        tbPemesanan2.setModel(new javax.swing.table.DefaultTableModel(
            data,header
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        tbPemesanan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemesanan2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbPemesanan2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Pemesanan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel4.setText("No Pemesanan");

        tPemesanan.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel5.setText("Tanggal Pemesanan");

        tgl.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel7.setText("Supplier");

        tSuplier.setEnabled(false);

        jButton5.setText("...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(tSuplier)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tPemesanan)
                            .addComponent(tgl))
                        .addGap(10, 10, 10))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tPemesanan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(174, 174, 174))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Pemesanan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        jButton4.setText("...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tKode.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel6.setText("Kode Obat");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel1.setText("Nama Obat");

        tNama.setEnabled(false);

        cbSatuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Botol", "Lembar", "Strip", "Tablet", "Pcs" }));
        cbSatuan.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel2.setText("Satuan");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel3.setText("Jumlah");

        jButton6.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton6.setText("Tambah");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton7.setText("Ubah");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tJumlah)
                            .addComponent(cbSatuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tNama)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(tKode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(tKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Pemesanan Sementara", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 11))); // NOI18N

        tbPemesanan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemesanan1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbPemesanan1);

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jDialog1.setLocationRelativeTo(this);
        getData();
        jDialog1.setVisible(true);  
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tbBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBarangMouseClicked
        int row=tbBarang.getSelectedRow();
        tKode.setText(tbBarang.getValueAt(row, 0).toString());
        tNama.setText(tbBarang.getValueAt(row, 1).toString());
        cbSatuan.setSelectedItem(tbBarang.getValueAt(row, 2).toString());
        jDialog1.setVisible(false);
    }//GEN-LAST:event_tbBarangMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(model.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "Tidak ada data yang bisa d simpan");
        }else{
            prosesData("insert");
            hapusDataTable();
            clear();
            generateKode();
            showTab();
            jButton5.setEnabled(true);
            tSuplier.setText(null);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbPemesananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemesananMouseClicked
        
    }//GEN-LAST:event_tbPemesananMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int row=tbPemesanan1.getSelectedRow();
        if(row<0){
            JOptionPane.showMessageDialog(this, "Pilih data untuk di hapus");
        }else{
            model.removeRow(tbPemesanan1.getSelectedRow());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tbPemesanan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemesanan1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPemesanan1MouseClicked

    private void tbPemesanan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemesanan2MouseClicked
        hapusDataTable1();
        int row = tbPemesanan2.getSelectedRow();
        try {
            ResultSet objCek = new Koneksi().ExecuteQuery("SELECT `kode`, `nama_obat`, `satuan`, `jumlah`, `tgl_pemesanan` FROM `t_pemesanan` WHERE kd_pesan"
                    + "='" + tbPemesanan2.getValueAt(row, 0) + "' and jumlah>0");
            
            while (objCek.next()) {
                model1.addRow(new Object[]{
                    objCek.getString(1),
                    objCek.getString(2),
                    objCek.getString(3),
                    objCek.getString(4),
                    objCek.getString(5)
                 
                
                });
            }
            
            tbPemesanan.setModel(model1);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_tbPemesanan2MouseClicked

    private void tblSuplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSuplierMouseClicked
        int row=tblSuplier.getSelectedRow();
        tSuplier.setText(tblSuplier.getValueAt(row, 1).toString());
        jDialog2.setVisible(false);
        jButton5.setEnabled(false);
    }//GEN-LAST:event_tblSuplierMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jDialog2.setLocationRelativeTo(this);
        getDataSuplier();
        jDialog2.setVisible(true);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(jButton6.getText().equals("Simpan")){
            String kode=tKode.getText();
            String nama =tNama.getText();
            String satuan=cbSatuan.getSelectedItem().toString();
            String jumlah=tJumlah.getText();
            int row = model.getRowCount();
            model.removeRow(tbPemesanan1.getSelectedRow());
            Object Data[] = {kode, nama, satuan, jumlah};
            model.addRow(Data);
            jButton6.setText("Tambah");
            clear();
            jButton7.setText("Ubah");
        }else{
            if(tKode.getText().trim().isEmpty()||tJumlah.getText().trim().isEmpty()||tSuplier.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Mohon isi semua field");
             }else{
            tempTable();
            clear();
            }
        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        int row=tbPemesanan1.getSelectedRow();
        
         if(jButton7.getText().equals("Batal")){
                jButton6.setText("Tambah");
                jButton7.setText("Ubah");
                clear();
            }else{
             if(row<0){
            JOptionPane.showMessageDialog(this, "Pilih data yang akan di ubah");
             }else{
            tKode.setText(tbPemesanan1.getValueAt(row, 0).toString());
            tNama.setText(tbPemesanan1.getValueAt(row, 1).toString());
            cbSatuan.setSelectedItem(tbPemesanan1.getValueAt(row, 2).toString());
            tJumlah.setText(tbPemesanan1.getValueAt(row, 3).toString());
            jButton6.setText("Simpan");
            jButton7.setText("Batal");
           
        }
         }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jButton5.setEnabled(true);
        tSuplier.setText(null);
        clear();
        hapusDataTable();
        hapusDataTable1();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbSatuan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField tJumlah;
    private javax.swing.JTextField tKode;
    private javax.swing.JTextField tNama;
    private javax.swing.JTextField tPemesanan;
    private javax.swing.JTextField tSuplier;
    private javax.swing.JTable tbBarang;
    private javax.swing.JTable tbPemesanan;
    private javax.swing.JTable tbPemesanan1;
    private javax.swing.JTable tbPemesanan2;
    private javax.swing.JTable tblSuplier;
    private javax.swing.JTextField tgl;
    // End of variables declaration//GEN-END:variables
}
