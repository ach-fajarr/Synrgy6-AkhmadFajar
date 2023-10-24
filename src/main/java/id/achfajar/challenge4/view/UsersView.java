package id.achfajar.challenge4.view;

import id.achfajar.challenge4.model.Users;

public class UsersView {

    static GeneralView g = new GeneralView();

    public void headerInfo(String info){
        System.out.println(g.LINE +
                "\nBinarFuud\n" +
                g.LINE2 +
                "\n \n"+info);
    }
    public static void userSetting(Users user){
        System.out.println(g.LINE + "\n" +
                "Pengaturan Akun \n"+
                g.LINE2+ "\n \n" +
                "Nama\t\t : "+ user.getUsername()+
                "\nEmail\t\t : "+ user.getEmail()+
                "\nPassword\t : "+ user.getPassword()+ "\n\n"+
                "1. Update akun \n" +
                "2. Hapus akun \n" +
                "3. Kembali \n \n" + g.LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }
    public void fieldEmail (){
        System.out.print("Email \t\t : ");
    }
    public void fieldUserName (){
        System.out.print("UserName \t : ");
    }
    public void fieldPassword (){
        System.out.print("Password \t : ");
    }
    public void updateUser(){
        System.out.println("Perbarui akun \ntekan enter jika tidak ingin merubah kolom tersebut\n");
    }
    public static void delSuccess(){
        System.out.println("Akun anda telah dihapus");
    }
    public void infoSuccess (){
        System.out.println("Akun anda telah dibuat");
    }

    public void userExists() {
        System.out.println("\n\nMaaf email telah terdaftar");
    }
}
