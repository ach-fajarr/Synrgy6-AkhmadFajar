package id.achfajar.challenge4.view;

import id.achfajar.challenge4.model.ProductType;

import java.util.List;

public class MerchantView {
    GeneralView g = new GeneralView();

    public void headerInfo(String info){
        System.out.println(g.LINE +
                "\nBinarFuud\n" +
                g.LINE2 +
                "\n \n"+info);
    }
    public void merchantHeader(){
        System.out.println(g.LINE + "\n" +
                "Pengaturan Toko \n"+
                g.LINE2+ "\n" +
                "List Toko yang kamu punya");
    }
    public void merchantOption(){
        System.out.println(g.LINE2+ "\n \n" +
                "1. Buat toko \n" +
                "2. Ubah data toko \n" +
                "3. Buka/Tutup toko \n" +
                "4. Hapus toko \n" +
                "5. Atur produk untuk setiap toko \n" +
                "6. Kembali \n \n" + g.LINE);
        System.out.print("Silahkan masukkan pilihan anda => ");
    }
    public void cancelOption(){
        System.out.println("ketik (x) untuk membatalkan\n");
    }
    public void fieldName(){
        System.out.print("Nama Merchant \t : ");
    }
    public void fieldLocation(){
        System.out.print("Lokasi \t\t\t : ");
    }
    public void statusOption(){
        System.out.print("Tekan 1 untuk buka dan 2 untuk tutup => ");
    }

    //================================================================================================
    public void productHeader(){
        System.out.println(g.LINE + "\n" +
                "Pilih Toko yang mau ditambah produknya \n"+
                g.LINE2);
    }
    public void selectMerchant(){
        System.out.println(g.LINE2+ "\n" +
                "tekan 0 untuk Kembali \n");
        System.out.print("Silahkan masukkan toko pilihan anda => ");
    }
    public static void inputTypeID (List<ProductType> productTypes){
        System.out.println("Silahkan pilih jenis makanan");
        productTypes.forEach(p-> System.out.println(+p.getId()+". "+p.getName()));
        System.out.println("tekan 0 jika telah menyelesaikan");
        System.out.print("Pilih nomer jenis produk => ");
    }
    public static void selectedID (List<ProductType> productTypes){
        if (productTypes.isEmpty()){
            System.out.println("Belum ada yang dipilih\n");
        } else {
            System.out.print("Dipilih : ");
            productTypes.forEach(p-> System.out.print(" | "+p.getName()));
            System.out.println();
        }
    }
    public void fieldProductName (){
        System.out.print("Nama produk \t : ");
    }
    public void fieldProductPrice (){
        System.out.print("Harga \t\t\t : ");
    }
    public void infoCreateOK(String info){
        System.out.println(info+" anda telah dibuat");
    }
    public void infoCreateFail(String info){
        System.out.println("Batal membuat "+info);
    }
}
