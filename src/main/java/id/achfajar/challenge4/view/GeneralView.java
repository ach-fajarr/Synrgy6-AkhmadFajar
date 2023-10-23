package id.achfajar.challenge4.view;

import id.achfajar.challenge4.model.Users;

public class GeneralView {
    public void welcome(){
        System.out.println(LINE + "\n" +
                "Selamat datang di BinarFud \n"+
                LINE2+ "\n \n" +
                "1. Login \n" +
                "2. Daftar \n \n" + LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }
    public void home(Users user){
        System.out.println(LINE + "\n" +
                "Selamat datang di BinarFud \n"+
                user.getUsername()+ "\n" +
                LINE2+ "\n \n" +
                "1. Pesan Makanan \n" +
                "2. Riwayat Pesanan \n" +
                "3. Toko Saya \n" +
                "4. Akun \n" +
                "5. Keluar \n \n" + LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }
    public static final String LINE = "=====================================";
    public static final String LINE2 = "-------------------------------------";
}
