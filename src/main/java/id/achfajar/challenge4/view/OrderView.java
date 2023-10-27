package id.achfajar.challenge4.view;

public class OrderView {
    static GeneralView g = new GeneralView();
    public static void headerInfo(String info){
        System.out.println(g.LINE +
                "\nBinarFuud\n" +
                g.LINE2 +
                "\n \n"+info);
    }
    public static void confirmHeader(){
        System.out.println(g.LINE + "\n" +
                "Konfirmasi pesanan \n"+
                g.LINE2+ "\n" +
                "daftar pesanan kamu");
    }
    public static void orderOption(){
        System.out.println(g.LINE2+ "\n \n" +
                "98. Filter produk yang ditampilkan \n" +
                "99. Konfirmasi pesanan \n" +
                "0. Kembali \n \n" + g.LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }
    public static void confirmOption(){
        System.out.println(
                "\n1. Konfirmasi dan bayar \n" +
                    "2. Batalkan pesanan \n" +
                    "0. Kembali \n \n" + g.LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }
    public static void filterOption(){
        System.out.println("\n1. Berdasarkan nama \n" +
                        "2. Berdasarkan jenis produk \n" +
                        "3. Berdasarkan toko yang buka \n" +
                        "0. Kembali \n \n" + g.LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }

    public static void fieldName (){
        System.out.print("Masukkan nama produk => ");
    }
    public static void quantity(){
        System.out.print("Masukkan jumlah pesanan => ");
    }
    public static void cancel(){
        System.out.println("Apakah anda ingin membatalkan pesanan ini");
        System.out.println("tekan (Y) untuk ya dan (T) untuk kembali ke menu konfirmasi");
        System.out.print("=>  ");
    }

    public static void errorMsg() {
        System.out.println("Maaf anda belum memesan makanan \n" +
                "silahkan pesan terlebih dahulu \n");
    }
    public static void fieldAddress(){
        System.out.print("Masukkan alamat pengiriman => ");
    }
    public static void backOption(){
        System.out.print("tekan apapun untuk kembali => ");
    }
}
