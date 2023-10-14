package id.achfajar.view;

import id.achfajar.utils.Utils;

public class MenuView {
    public static void welcome(String username){
        System.out.println(Utils.LINE);
        System.out.println("Selamat datang di BinarFud");
        System.out.println(Utils.LINE);
        System.out.println(username);
        System.out.println();
    }
    public static void menu(){
        System.out.println("98. Pesan dan Bayar");
        System.out.println("99. Riwayat pesanan");
        System.out.println("0. Keluar Aplikasi");
        System.out.println();
    }
    public static void confirmOrderOption(){
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke Menu Utama");
        System.out.println("3. Batalkan pesanan");
        System.out.println("0. Keluar Aplikasi");
        System.out.println();
    }

    public static void confirmQty(){
        System.out.println(Utils.LINE);
        System.out.println("Berapa Pesanan Anda");
        System.out.println(Utils.LINE);
        System.out.println();
    }
    public static void confirmOrder(){
        System.out.println(Utils.LINE);
        System.out.println("Konfirmasi Pesanan Anda");
        System.out.println(Utils.LINE);
    }
    public static void addNotes(){
        System.out.println("Apakah ada catatan?");
        System.out.println("(input 0 / enter jika tidak ada)");
        System.out.println();
    }
    public static void cancel(){
        System.out.println("Apakah anda ingin membatalkan pesanan ini?");
        System.out.print("Y/N =>");
    }
    public static String notes(){
        StringBuilder sb = new StringBuilder();
        sb.append("Catatan :");
        return String.valueOf(sb);
    }
    public static String hideNotes(){
        StringBuilder sb = new StringBuilder();
        sb.append("Catatan : Tidak ada");
        return String.valueOf(sb);
    }
    public static void backOption(){
        System.out.println("(input 0 untuk kembali)");
        System.out.println();
    }
    public static void inputAddress(){
        System.out.println("Masukkan alamat pengiriman");
    }
    public static void inputOption(){
        System.out.print("=> ");
    }
}
