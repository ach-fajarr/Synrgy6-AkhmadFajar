package id.achfajar.challenge4.view;

public class OrderView {
    GeneralView g = new GeneralView();
    public void headerInfo(String info){
        System.out.println(g.LINE +
                "\nBinarFuud\n" +
                g.LINE2 +
                "\n \n"+info);
    }
    public void orderHeader(){
        System.out.println(g.LINE + "\n" +
                "BinarFud \n"+
                g.LINE2+ "\n" +
                "Daftar menu :");
    }
    public void confirmHeader(){
        System.out.println(g.LINE + "\n" +
                "Konfirmasi pesanan \n"+
                g.LINE2+ "\n" +
                "daftar pesanan kamu");
    }
    public void orderOption(){
        System.out.println(g.LINE2+ "\n \n" +
                "99. Konfirmasi pesanan \n" +
                "0. Kembali \n \n" + g.LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }
    public void confirmOption(){
        System.out.println(g.LINE2+ "\n \n" +
                "1. Konfirmasi dan bayar \n" +
                "2. Batalkan pesanan \n" +
                "0. Kembali \n \n" + g.LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }

    public void quantity(){
        System.out.print("Masukkan jumlah pesanan => ");
    }
    public void cancel(){
        System.out.println("Apakah anda ingin membatalkan pesanan ini");
        System.out.println("tekan (Y) untuk ya dan (T) untuk kembali ke menu konfirmasi");
        System.out.print("=>  ");
    }

    public void errorMsg() {
        System.out.println("Maaf anda belum memesan makanan \n" +
                "silahkan pesan terlebih dahulu \n");
    }
    public void fieldAddress(){
        System.out.print("Masukkan alamat pengiriman => ");
    }
    public void backOption(){
        System.out.print("tekan apapun untuk kembali => ");
    }
}
