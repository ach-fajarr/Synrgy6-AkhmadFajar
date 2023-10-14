package id.achfajar.view;

import id.achfajar.utils.Utils;

public class ErrorView {
    public static void wrongInputOption() {
        System.out.println("Maaf input yang Anda masukkan salah");
    }
    public static void merchantClose(){
        System.out.println("Maaf merchant ini sedang tutup, silahkan pilih yang lain");
    }
    public static void reInputOption(){
        System.out.println("Masukkan Ulang pilihan anda");
        System.out.print("=> ");
    }
    public static void minimumOrder(){
        System.out.println("Maaf minimal 1 jumlah pesanan");
    }
    public static void showOrderIsEmpty(){
        System.out.println(Utils.LINE);
        System.out.println("Maaf Anda Belum Memesan Makanan");
        System.out.println(Utils.LINE);
        System.out.println();
    }
    public static void failedToPrintReceipt(){
        System.out.println(Utils.LINE2);
        System.out.println("Struk gagal di cetak");
        System.out.println("Anda belum memesan makanan");
        System.out.println(Utils.LINE2+Utils.NEW_LINE);
        System.out.println("Ketik 2 untuk kembali ke menu utama");
        System.out.println("Ketik 0 untuk keluar aplikasi");
        System.out.print("=> ");
    }
    public static void receiptError(){
        System.out.println("Gagal menyimpan struk pembayaran");
    }
}
