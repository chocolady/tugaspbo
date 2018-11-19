/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DC
 */
public class Koneksi {
//    private static String DATABASE_URL = "jdbc:mysql://192.168.1.1:3306/absensi";
    private static String DATABASE_URL = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    
    private static Connection conn;
    
    private static String setConfig(){
        FileInputStream input= null;
        int data;
        String xData = "";
        try{
            input=new FileInputStream("DATABASE_URL.txt");
            }catch(IOException e){
            //System.out.print("file tidak ditemukan");
            //return;
        }
        try{
            while ((data=input.read())!=-1){
                //System.out.print((char) data);
                xData += (char) data;
            }
        }catch(IOException e){
             //System.out.print(e.getMessage());
             //return;
        }
    
        try{
            input.close();
        }catch (IOException e){
        
        }
        return xData;
    }
    
    public static Connection getConnection(){
        if(conn==null){        
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                //conn=DriverManager.getConnection(DATABASE_URL,"root","");
                //String xDb = new set
                conn=DriverManager.getConnection(setConfig(),"root","");
            } catch (SQLException ex) {
                Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }
    
    private void con(){
        try {
            //connection = DriverManager.getConnection(DATABASE_URL,"root","");
            connection = DriverManager.getConnection(setConfig(),"root","");
            statement = connection.createStatement();
        }catch (Exception ex){
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Exception"+ex.getMessage());
        }
    }
    
    public ResultSet ExecuteQuery(String mysql){
        try {
            con();
            resultSet = statement.executeQuery(mysql);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return resultSet;
    }
    
    public void ExecuteSQl(String jenis,String tabel,String kolom,String values){
        try {
            new Koneksi().con();
            switch (jenis) {
                case "insert":
                    statement.executeUpdate(jenis+" into "+ tabel +" ("+ kolom +") values ("+ values +")");
                    break;
                case "update":
                    statement.executeUpdate(jenis+" "+ tabel +" set "+ kolom +" where "+ values +"");
                    break;
                case "delete":
                    statement.executeUpdate(jenis+" from "+ tabel +" where "+ values +"");
                    break;
                default:
                    statement = null;
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();  
            //JOptionPane.showMessageDialog(null,"Exception"+ex.getMessage());
        }
    }
    
    public Vector getData(String tabel)throws Exception{
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        switch (tabel) {
            case "obat":
                data = getObat();
                break;
            case "suplier":
                data = getSuplier();
                break;
            case "pemesanan":
                data = getPemesanan();
                break;
            case "pembelian":
                data = getPembelian();
                break;
            case "kasir":
                data = getKasir();
                break;
            case "penerimaan":
                data = getPenerimaan();
                break;
            case "beli":
                data = getObatBeli();
                break;
            case "expire":
                data = getExpire();
                break;
        }

        return data;
    }
    
   public Vector getObat()throws Exception{
        Vector<Vector<String>> obat = new Vector<Vector<String>>();

        ResultSet objData = ExecuteQuery("select kd_obat,nama_obat,satuan,stok,tgl_expire,harga,harga_beli from t_obat order by kd_obat asc");

        for (int i = 0; objData.next(); i++){
            Vector<String> obt = new Vector<String>();

            obt.add(objData.getString(1)); 
            obt.add(objData.getString(2)); 
            obt.add(objData.getString(3));
            obt.add(objData.getString(4));
            obt.add(objData.getString(5));
            obt.add(objData.getString(6));
            obt.add(objData.getString(7));
            obat.add(obt);
        }

        return obat;
    }
    
    public Vector getSuplier()throws Exception{
        Vector<Vector<String>> suplier = new Vector<Vector<String>>();

        ResultSet objData = ExecuteQuery("select * from t_suplier order by kd_suplier asc");

        for (int i = 0; objData.next(); i++){
            Vector<String> sp = new Vector<String>();

            sp.add(objData.getString(1)); 
            sp.add(objData.getString(2));
            sp.add(objData.getString(3)); 
            sp.add(objData.getString(4)); 
            sp.add(objData.getString(5)); 
            sp.add(objData.getString(6)); 
           
            suplier.add(sp);
        }

        return suplier;
    }
    
    public Vector getPemesanan()throws Exception{
        Vector<Vector<String>> pesan = new Vector<Vector<String>>();

        ResultSet objData = ExecuteQuery("select kd_pesan from t_pemesanan where jumlah >0 group by kd_pesan asc");

        for (int i = 0; objData.next(); i++){
            Vector<String> gj = new Vector<String>();

            gj.add(objData.getString(1)); 
           
            pesan.add(gj);
        }

        return pesan;
    }
    public Vector getPembelian()throws Exception{
        Vector<Vector<String>> pembelian = new Vector<Vector<String>>();

        ResultSet objData = ExecuteQuery("select kode,nama_obat,satuan,jumlah,tgl_pemesanan from t_pemesanan");

        for (int i = 0; objData.next(); i++){
            Vector<String> pb = new Vector<String>();

            pb.add(objData.getString(1)); 
            pb.add(objData.getString(2)); 
            pb.add(objData.getString(3)); 
            pb.add(objData.getString(4)); 
            pb.add(objData.getString(5)); 
            pembelian.add(pb);
        }

        return pembelian;
    }
    public Vector getKasir()throws Exception{
        Vector<Vector<String>> kasir = new Vector<Vector<String>>();

        ResultSet objData = ExecuteQuery("select * from t_kasir order by id_kasir asc");

        for (int i = 0; objData.next(); i++){
            Vector<String> ks = new Vector<String>();

            ks.add(objData.getString(1)); 
            ks.add(objData.getString(2)); 
            ks.add(objData.getString(3)); 
            ks.add(objData.getString(4)); 
           
            kasir.add(ks);
        }

        return kasir;
    }
    public Vector getPenerimaan()throws Exception{
        Vector<Vector<String>> penerimaan = new Vector<Vector<String>>();

        ResultSet objData = ExecuteQuery("select kd_pesan from t_pembelian group by kd_pesan asc");

        for (int i = 0; objData.next(); i++){
            Vector<String> pn = new Vector<String>();

            pn.add(objData.getString(1)); 
           
            penerimaan.add(pn);
        }

        return penerimaan;
    }
    public Vector getObatBeli()throws Exception{
        Vector<Vector<String>> beli = new Vector<Vector<String>>();

        ResultSet objData = ExecuteQuery("select kd_obat,nama_obat,satuan,stok,tgl_expire,harga from t_obat where ifnull(stok,'') <> '' and tgl_expire > NOW() order by kd_obat asc");

        for (int i = 0; objData.next(); i++){
            Vector<String> obt = new Vector<String>();

            obt.add(objData.getString(1)); 
            obt.add(objData.getString(2)); 
            obt.add(objData.getString(3));
            obt.add(objData.getString(4));
            obt.add(objData.getString(5));
            obt.add(objData.getString(6));
            beli.add(obt);
        }

        return beli;
    }
    public Vector getExpire()throws Exception{
        Vector<Vector<String>> expire = new Vector<Vector<String>>();

        ResultSet objData = ExecuteQuery("select kd_obat,nama_obat,satuan,stok,tgl_expire from t_obat where ifnull(stok,'') <> '' or stok=0 and tgl_expire < NOW() order by kd_obat asc");

        for (int i = 0; objData.next(); i++){
            Vector<String> ex = new Vector<String>();

            ex.add(objData.getString(1)); 
            ex.add(objData.getString(2)); 
            ex.add(objData.getString(3));
            ex.add(objData.getString(4));
            ex.add(objData.getString(5));
            expire.add(ex);
        }

        return expire;
    }
}
