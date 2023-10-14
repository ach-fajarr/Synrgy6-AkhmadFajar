package id.achfajar.view;

import id.achfajar.utils.Utils;

public class ReceiptView {
    public static String receiptHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.LINE).append(Utils.NEW_LINE)
                .append("BinarFud").append(Utils.NEW_LINE)
                .append(Utils.LINE).append(Utils.NEW_LINE)
                .append(Utils.NEW_LINE)
                .append("Terima kasih telah").append(Utils.NEW_LINE)
                .append("memesan makanan di Binarfud").append(Utils.NEW_LINE)
                .append(Utils.NEW_LINE)
                .append("Berikut ini adalah pesanan anda")
                .append(Utils.NEW_LINE);
        return String.valueOf(sb);
    }
    public static String receiptFooter(){
        StringBuilder sb = new StringBuilder();
        sb.append("Pembayaran \t : BinarCash").append(Utils.NEW_LINE)
                .append("Tanggal \t : ").append(Utils.getTimeNow()).append(Utils.NEW_LINE)
                .append(Utils.NEW_LINE)
                .append(Utils.LINE).append(Utils.NEW_LINE)
                .append("Simpan struk ini sebagai").append(Utils.NEW_LINE)
                .append("bukti pembayaran").append(Utils.NEW_LINE)
                .append(Utils.LINE).append(Utils.NEW_LINE)
                .append(Utils.NEW_LINE);
        return String.valueOf(sb);
    }
    public static void receiptSuccess(){
        System.out.println(Utils.LINE2);
        System.out.println("Struk berhasil di cetak");
        System.out.println(Utils.LINE2);
    }
}
