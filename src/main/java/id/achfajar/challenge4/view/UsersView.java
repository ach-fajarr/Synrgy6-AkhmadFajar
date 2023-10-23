package id.achfajar.challenge4.view;

import id.achfajar.challenge4.model.Users;

public class UsersView {
    GeneralView g = new GeneralView();
    public void userSetting(Users user){
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
    public void headerReg(){
        System.out.println(g.LINE +
                "\nBinarFuud" +
                g.LINE +
                "\n \nPendaftaran pengguna baru \n"
        );
    }
    public void headerLogin(){
        System.out.println(g.LINE +
                "\nBinarFuud \n" +
                g.LINE +
                "\n \nGunakan email dan password anda \n"
        );
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
    public void delSuccess(){
        System.out.println("Akun anda telah dihapus");
    }
    public void infoSuccess (){
        System.out.println("Akun anda telah dibuat");
    }
}
