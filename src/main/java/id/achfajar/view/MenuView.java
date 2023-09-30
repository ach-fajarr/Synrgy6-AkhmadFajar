package id.achfajar.view;

import id.achfajar.utils.Utils;

public class MenuView {
    public static void showMenu(){
        System.out.println(Utils.LINE);
        System.out.println("Selamat datang di BinarFud");
        System.out.println(Utils.LINE);
        System.out.println();
        System.out.println("Silahkan pilih makanan :");
        System.out.println("1. Nasi Goreng \t| 15.000");
        System.out.println("2. Mie Goreng \t| 13.000");
        System.out.println("3. Nasi + Ayam \t| 18.000");
        System.out.println("4. Es Teh Manis | 3.000");
        System.out.println("5. Es Jeruk \t| 5.000");
        System.out.println("99. Pesan dan Bayar");
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
    public static void confirmOrderOption(){
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke Menu Utama");
        System.out.println("0. Keluar Aplikasi");
        System.out.println();
    }
    public static void addNotes(){
        System.out.println("Apakah ada catatan?");
        System.out.println("(input 0 / enter jika tidak ada)");
        System.out.println();
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
    public static void inputOption(){
        System.out.print("=> ");
    }
}
