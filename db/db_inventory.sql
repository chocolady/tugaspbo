-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 15 Okt 2018 pada 19.09
-- Versi Server: 10.1.9-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_inventory`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `login`
--

CREATE TABLE `login` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `login`
--

INSERT INTO `login` (`username`, `password`) VALUES
('a', 'a'),
('gudang', 'gudang'),
('admin', 'admin'),
('a', 'a'),
('gudang', 'gudang'),
('admin', 'admin');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_detail_penjualan`
--

CREATE TABLE `t_detail_penjualan` (
  `id_kasir` varchar(20) NOT NULL,
  `tgl_penjualan` date NOT NULL,
  `total` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_obat`
--

CREATE TABLE `t_obat` (
  `kd_obat` varchar(20) NOT NULL,
  `nama_obat` varchar(20) NOT NULL,
  `satuan` varchar(20) NOT NULL,
  `stok` int(20) DEFAULT NULL,
  `tgl_expire` date DEFAULT NULL,
  `harga` int(20) DEFAULT NULL,
  `harga_beli` int(20) DEFAULT NULL,
  `kategori` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_obat`
--

INSERT INTO `t_obat` (`kd_obat`, `nama_obat`, `satuan`, `stok`, `tgl_expire`, `harga`, `harga_beli`, `kategori`) VALUES
('B001', 'Bodrek', 'Lembar', 24, '2015-11-23', 625, 500, 'Umum'),
('B015', 'Antangin', 'Pcs', 90, '2015-08-29', 2500, 2000, 'Umum'),
('Ko011', 'test', 'Strip', NULL, NULL, NULL, NULL, 'Resep'),
('099', 'aass', 'Botol', NULL, NULL, NULL, NULL, 'Umum'),
('test', 'test', 'Botol', 50, '2018-10-25', 1250, 1000, 'Umum'),
('B001', 'Bodrek', 'Lembar', 24, '2015-11-23', 625, 500, 'Umum'),
('B015', 'Antangin', 'Pcs', 90, '2015-08-29', 2500, 2000, 'Umum');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_pembelian`
--

CREATE TABLE `t_pembelian` (
  `kd_pesan` varchar(20) NOT NULL,
  `tgl_penerimaan` date NOT NULL,
  `kd_obat` varchar(20) NOT NULL,
  `jumlah_terima` int(10) NOT NULL,
  `harga_terima` int(20) NOT NULL,
  `nama_suplier` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_pembelian`
--

INSERT INTO `t_pembelian` (`kd_pesan`, `tgl_penerimaan`, `kd_obat`, `jumlah_terima`, `harga_terima`, `nama_suplier`) VALUES
('PM1810001', '2018-10-15', 'test', 50, 1250, 'PT Alim Rugi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_pemesanan`
--

CREATE TABLE `t_pemesanan` (
  `kd_pesan` varchar(20) NOT NULL,
  `kode` varchar(20) NOT NULL,
  `nama_obat` varchar(20) NOT NULL,
  `satuan` varchar(20) NOT NULL,
  `jumlah` int(20) NOT NULL,
  `tgl_pemesanan` date NOT NULL,
  `kd_suplier` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_pemesanan`
--

INSERT INTO `t_pemesanan` (`kd_pesan`, `kode`, `nama_obat`, `satuan`, `jumlah`, `tgl_pemesanan`, `kd_suplier`) VALUES
('PM1810001', 'test', 'test', 'Botol', 0, '2018-10-15', 'PT Alim Rugi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_suplier`
--

CREATE TABLE `t_suplier` (
  `kd_suplier` varchar(20) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `no_tlp` varchar(20) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_suplier`
--

INSERT INTO `t_suplier` (`kd_suplier`, `nama`, `alamat`, `no_tlp`, `no_hp`, `email`) VALUES
('S001', 'PT. Masuk Angin', 'Bandung', '222222', '08993282382', 'Angin@ymail.com'),
('S002', 'PT Alim Rugi', 'Bandung	', '2002', '800', 'alimrugi@yahoo.com'),
('S003', 'PT.Jaya Abadi', 'Bandung', '20090', '0811', 'jayaabadi@gmail.com'),
('S004', 'Biofarma', 'bandung	', '022280008', '08764562819', 'biofarma@gmail.com'),
('S001', 'PT. Masuk Angin', 'Bandung', '222222', '08993282382', 'Angin@ymail.com'),
('S002', 'PT Alim Rugi', 'Bandung	', '2002', '800', 'alimrugi@yahoo.com'),
('S003', 'PT.Jaya Abadi', 'Bandung', '20090', '0811', 'jayaabadi@gmail.com'),
('S004', 'Biofarma', 'bandung	', '022280008', '08764562819', 'biofarma@gmail.com');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
