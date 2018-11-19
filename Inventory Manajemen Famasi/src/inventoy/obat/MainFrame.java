/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoy.obat;

/**
 *
 * @author Paul Decul
 */
import Master.*;
import Transaksi.Pembelian;
import Transaksi.Pemesanan;
import Transaksi.Penjualan;
//import Transaksi.Permintaan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import koneksi.Koneksi;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class MainFrame extends javax.swing.JFrame {

    Date dnow = new Date();
    SimpleDateFormat fNowT = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat fNow = new SimpleDateFormat("EEEE, dd MMMM yyyy");
    int xJam,xMnt;
    String akses=null;
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        
        initComponents();
        time();
        style(false);style2(false);style3(false);
//        autoResize();
        GetJam();
//        lblJam.setFocusable(true);
    }

    public void time() {
        //tanggal
        tTgl.setText(fNowT.format(dnow));
    }
    private void style(boolean stl){
       tBarang.setVisible(stl);
       tSuplier.setVisible(stl);
    }
    private void style2(boolean stl){
       jButton1.setVisible(stl);
       tPembelian.setVisible(stl);
//       tPermintaan.setVisible(stl);
    }
    private void style3(boolean stl){
       tlStok.setVisible(stl);
       tlPembelian.setVisible(stl);
    }
    private void autoResize(){
        inventoy.obat.logoInventoyObat lap=new logoInventoyObat();
        CallPane.removeAll();
        CallPane.add(lap);
        lap.setVisible(true);
        try {
            lap.setMaximum(true);
        } catch (PropertyVetoException ex) {
           ex.printStackTrace();
        }
    } 
    private void GetJam(){        
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String jc_jam = "";
                String jc_menit = "";
                String jc_detik = "";
                
                Date JC = new Date();
                
                int nilai_jam = JC.getHours();
                int nilai_menit = JC.getMinutes();
                int nilai_detik = JC.getSeconds();
                
                if (nilai_jam <= 9) {                    
                    jc_jam = "0";
                }
                
                if (nilai_menit <= 9) {                    
                    jc_menit = "0";
                }
                
                if (nilai_detik <= 9) {                    
                    jc_detik = "0";
                }
                
                String jam = jc_jam + Integer.toString(nilai_jam);
                String menit = jc_menit + Integer.toString(nilai_menit);
                String detik = jc_detik + Integer.toString(nilai_detik);
                xJam=nilai_jam;
                xMnt=nilai_menit;
                lblJam.setText(jam + ":" + menit + ":" + detik);
                lblJam.setToolTipText( fNow.format(dnow));
            }
        };
        
        new javax.swing.Timer(1000, taskPerformer).start();
    }
    private void cetak(){
    String reportDest;
    String reportSource;
    SimpleDateFormat frm= new SimpleDateFormat("yyyy-MM-dd");
    String date1=frm.format(jDateChooser3.getDate());
    String date2=frm.format(jDateChooser4.getDate());
    DateTimeFormatter frmt1= DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);
    LocalDate dt11=LocalDate.parse(date1,frmt1);
    LocalDate dt1=LocalDate.parse(date2, frmt1);
    try{
//    BufferedImage image = ImageIO.read(getClass().getResource("/report/UNIKOM.png"));
       Map<String, Object> param = new HashMap<String, Object>();
       param.put("date1", jDateChooser3.getDate());
       param.put("date2", jDateChooser4.getDate());
//       param.put("logo", image);
       
       
           reportSource = System.getProperty("user.dir")+"/src/report/Laporan_Penjualan.jrxml";
           reportDest = System.getProperty("user.dir")+"/src/report/Laporan_Penjualan.jasper";
           
           JasperReport jasper=JasperCompileManager.compileReport(reportSource);
           JasperPrint print=JasperFillManager.fillReport(jasper, param,Koneksi.getConnection());
           JasperExportManager.exportReportToHtmlFile(print, reportDest);
           JasperViewer.viewReport(print, false);
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    private void cetak1(){
    String reportDest;
    String reportSource;
    SimpleDateFormat frm= new SimpleDateFormat("yyyy-MM-dd");
    String date1=frm.format(jDateChooser5.getDate());
    String date2=frm.format(jDateChooser6.getDate());
    DateTimeFormatter frmt1= DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);
    LocalDate dt11=LocalDate.parse(date1,frmt1);
    LocalDate dt1=LocalDate.parse(date2, frmt1);
    try{
//    BufferedImage image = ImageIO.read(getClass().getResource("/report/UNIKOM.png"));
       Map<String, Object> param = new HashMap<String, Object>();
       param.put("date1", jDateChooser5.getDate());
       param.put("date2", jDateChooser6.getDate());
//       param.put("logo", image);
       
       
           reportSource = System.getProperty("user.dir")+"/src/report/Penerimaan.jrxml";
           reportDest = System.getProperty("user.dir")+"/src/report/Penerimaan.jasper";
           
           JasperReport jasper=JasperCompileManager.compileReport(reportSource);
           JasperPrint print=JasperFillManager.fillReport(jasper, param,Koneksi.getConnection());
           JasperExportManager.exportReportToHtmlFile(print, reportDest);
           JasperViewer.viewReport(print, false);
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    private void cetak2(){
    String reportDest;
    String reportSource;
    try{
           reportSource = System.getProperty("user.dir")+"/src/report/Stok_obat.jrxml";
           reportDest = System.getProperty("user.dir")+"/src/report/Stok_obat.jasper";
           
           JasperReport jasper=JasperCompileManager.compileReport(reportSource);
           JasperPrint print=JasperFillManager.fillReport(jasper, null,Koneksi.getConnection());
           JasperExportManager.exportReportToHtmlFile(print, reportDest);
           JasperViewer.viewReport(print, false);
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    public String hak(String test){
        akses=test;
        return akses;
    }
//    public void seter(){
//        this.akses=akses;
//    } 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jDialog2 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jDialog3 = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        CallPane = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblJam = new javax.swing.JLabel();
        tTgl = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        Master = new javax.swing.JButton();
        tBarang = new javax.swing.JButton();
        tSuplier = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        Transaksi = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        tPembelian = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        Laporan = new javax.swing.JButton();
        tlStok = new javax.swing.JButton();
        tlPembelian = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();

        jDialog1.setMinimumSize(new java.awt.Dimension(380, 137));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("STOK OBAT");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel4.setText("Dari");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel5.setText("s/d");

        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton2.setText("Print");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator3)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2))
                .addGap(0, 65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog2.setMinimumSize(new java.awt.Dimension(380, 137));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("PENJUALAN");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel7.setText("Dari");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel8.setText("s/d");

        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton3.setText("Print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator4)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3))
                .addGap(0, 65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog3.setMinimumSize(new java.awt.Dimension(380, 137));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("PENERIMAAN");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel10.setText("Dari");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel11.setText("s/d");

        jButton5.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jButton5.setText("Print");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator5)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)
                        .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5))
                .addGap(0, 65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Inventory Manajemen Famasi");
        setBackground(new java.awt.Color(0, 0, 0));

        CallPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout CallPaneLayout = new javax.swing.GroupLayout(CallPane);
        CallPane.setLayout(CallPaneLayout);
        CallPaneLayout.setHorizontalGroup(
            CallPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 824, Short.MAX_VALUE)
        );
        CallPaneLayout.setVerticalGroup(
            CallPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblJam.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lblJam.setForeground(new java.awt.Color(255, 255, 255));
        lblJam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJam.setText("00:00:00");

        tTgl.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        tTgl.setForeground(new java.awt.Color(255, 255, 255));
        tTgl.setText("2015/07/12");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblJam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(tTgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblJam, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "MENU", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToolBar1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        jLabel2.setText("    ");
        jToolBar1.add(jLabel2);

        Master.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Master.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/image/box_32.png"))); // NOI18N
        Master.setText("+Master");
        Master.setFocusable(false);
        Master.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Master.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Master.setMaximumSize(new java.awt.Dimension(139, 27));
        Master.setMinimumSize(new java.awt.Dimension(139, 27));
        Master.setPreferredSize(new java.awt.Dimension(139, 27));
        Master.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MasterActionPerformed(evt);
            }
        });
        jToolBar1.add(Master);

        tBarang.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        tBarang.setText("    Barang");
        tBarang.setFocusable(false);
        tBarang.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        tBarang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tBarang.setMaximumSize(new java.awt.Dimension(139, 19));
        tBarang.setMinimumSize(new java.awt.Dimension(139, 19));
        tBarang.setPreferredSize(new java.awt.Dimension(139, 19));
        tBarang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tBarangActionPerformed(evt);
            }
        });
        jToolBar1.add(tBarang);

        tSuplier.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        tSuplier.setText("    Suplier");
        tSuplier.setFocusable(false);
        tSuplier.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        tSuplier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tSuplier.setMaximumSize(new java.awt.Dimension(139, 19));
        tSuplier.setMinimumSize(new java.awt.Dimension(139, 19));
        tSuplier.setPreferredSize(new java.awt.Dimension(139, 19));
        tSuplier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tSuplierActionPerformed(evt);
            }
        });
        jToolBar1.add(tSuplier);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jToolBar1.add(jSeparator1);

        Transaksi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Transaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/1369491928_cash_register.png"))); // NOI18N
        Transaksi.setText("+Transaksi");
        Transaksi.setFocusable(false);
        Transaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Transaksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Transaksi.setMaximumSize(new java.awt.Dimension(139, 27));
        Transaksi.setMinimumSize(new java.awt.Dimension(139, 27));
        Transaksi.setPreferredSize(new java.awt.Dimension(139, 27));
        Transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransaksiActionPerformed(evt);
            }
        });
        jToolBar1.add(Transaksi);

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jButton1.setText("    Pemesanan");
        jButton1.setFocusable(false);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton1.setMaximumSize(new java.awt.Dimension(139, 19));
        jButton1.setMinimumSize(new java.awt.Dimension(139, 19));
        jButton1.setPreferredSize(new java.awt.Dimension(139, 19));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        tPembelian.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        tPembelian.setText("    Penerimaan");
        tPembelian.setFocusable(false);
        tPembelian.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        tPembelian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tPembelian.setMaximumSize(new java.awt.Dimension(139, 19));
        tPembelian.setMinimumSize(new java.awt.Dimension(139, 19));
        tPembelian.setPreferredSize(new java.awt.Dimension(139, 19));
        tPembelian.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tPembelianActionPerformed(evt);
            }
        });
        jToolBar1.add(tPembelian);
        jToolBar1.add(jSeparator2);

        Laporan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Laporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/image/Edit-32.png"))); // NOI18N
        Laporan.setText("+Laporan");
        Laporan.setFocusable(false);
        Laporan.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        Laporan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Laporan.setMaximumSize(new java.awt.Dimension(139, 27));
        Laporan.setMinimumSize(new java.awt.Dimension(139, 27));
        Laporan.setPreferredSize(new java.awt.Dimension(139, 27));
        Laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LaporanActionPerformed(evt);
            }
        });
        jToolBar1.add(Laporan);

        tlStok.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        tlStok.setText("    Stok Barang");
        tlStok.setFocusable(false);
        tlStok.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        tlStok.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tlStok.setMaximumSize(new java.awt.Dimension(139, 19));
        tlStok.setMinimumSize(new java.awt.Dimension(139, 19));
        tlStok.setPreferredSize(new java.awt.Dimension(139, 19));
        tlStok.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tlStokActionPerformed(evt);
            }
        });
        jToolBar1.add(tlStok);

        tlPembelian.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        tlPembelian.setText("    Penerimaan");
        tlPembelian.setFocusable(false);
        tlPembelian.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        tlPembelian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tlPembelian.setMaximumSize(new java.awt.Dimension(139, 19));
        tlPembelian.setMinimumSize(new java.awt.Dimension(139, 19));
        tlPembelian.setPreferredSize(new java.awt.Dimension(139, 19));
        tlPembelian.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tlPembelianActionPerformed(evt);
            }
        });
        jToolBar1.add(tlPembelian);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel1.setText("Aplikasi Inventory Manajemen Farmasi");

        jToolBar2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jToolBar2.setRollover(true);

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icons/login.png"))); // NOI18N
        jButton4.setText("Logout");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton4.setFocusable(false);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.setMaximumSize(new java.awt.Dimension(130, 27));
        jButton4.setMinimumSize(new java.awt.Dimension(130, 27));
        jButton4.setOpaque(false);
        jButton4.setPreferredSize(new java.awt.Dimension(130, 27));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CallPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(CallPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
        if (JOptionPane.showConfirmDialog(null,"Apakah anda yakin ingin keluar ??",
            "",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        Login lg=new Login();
        lg.setLocationRelativeTo(this);
        lg.setVisible(true);
        this.dispose();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void LaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LaporanActionPerformed
        if(Laporan.getText().equals("+Laporan")){
            Laporan.setText("-Laporan");
             if(akses.equals("gudang")){
                style3(true);
            }else{
                style3(true);
                tlStok.setVisible(false);
                tlPembelian.setVisible(false);
            }
        }else{
            Laporan.setText("+Laporan");
            style3(false);
        }
    }//GEN-LAST:event_LaporanActionPerformed

    private void tPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tPembelianActionPerformed
        Pembelian d = new Pembelian();
        CallPane.removeAll();
        CallPane.add(d);
        d.setVisible(true);
        try {
            d.setMaximum(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_tPembelianActionPerformed

    private void TransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransaksiActionPerformed
        if(Transaksi.getText().equals("+Transaksi")){
            Transaksi.setText("-Transaksi");
            if(akses.equals("gudang")){
                style2(true);
            }else{
                style2(true);
                jButton1.setVisible(false);
                tPembelian.setVisible(false);
            }
            
        }else{
            Transaksi.setText("+Transaksi");
            style2(false);
        }
    }//GEN-LAST:event_TransaksiActionPerformed

    private void tSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tSuplierActionPerformed
        Suplier c = new Suplier();
        CallPane.removeAll();
        CallPane.add(c);
        c.setVisible(true);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_tSuplierActionPerformed

    private void tBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tBarangActionPerformed
        Barang b = new Barang();
        CallPane.removeAll();
        CallPane.add(b);
        b.setVisible(true);
        try {
            b.setMaximum(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_tBarangActionPerformed

    private void MasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MasterActionPerformed
        if(Master.getText().equals("+Master")){
            Master.setText("-Master");
            jSeparator1.setVisible(true);
            if(akses.equals("gudang")){
                style(true);
            }else{
                style(true);
                tSuplier.setVisible(false);
                tBarang.setVisible(false);
            }       
        }else{
            Master.setText("+Master");
            style(false);
        }
    }//GEN-LAST:event_MasterActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Pemesanan pm = new Pemesanan();
        CallPane.removeAll();
        CallPane.add(pm);
        pm.setVisible(true);
        try {
            pm.setMaximum(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tlStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tlStokActionPerformed
        cetak2();
    }//GEN-LAST:event_tlStokActionPerformed

    private void tlPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tlPembelianActionPerformed
        jDialog3.setLocationRelativeTo(this);
        jDialog3.setVisible(true);
    }//GEN-LAST:event_tlPembelianActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cetak();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        cetak1();
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CallPane;
    private javax.swing.JButton Laporan;
    private javax.swing.JButton Master;
    private javax.swing.JButton Transaksi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblJam;
    private javax.swing.JButton tBarang;
    private javax.swing.JButton tPembelian;
    private javax.swing.JButton tSuplier;
    private javax.swing.JLabel tTgl;
    private javax.swing.JButton tlPembelian;
    private javax.swing.JButton tlStok;
    // End of variables declaration//GEN-END:variables
}
