package id.achfajar;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> pesanan = new HashMap<>();
        Map<String, Integer> hargaMakanan = new HashMap<>();
        hargaMakanan.put("Nasi Goreng   ",15000);
        hargaMakanan.put("Mie Goreng   ",13000);
        hargaMakanan.put("Nasi + Ayam   ",18000);
        hargaMakanan.put("Es Teh Manis",3000);
        hargaMakanan.put("Es Jeruk   ",5000);
        int totalHarga = 0;
        int totalPesanan = 0;
        while (true) {
            System.out.println("===========================");
            System.out.println("Selamat datang di BinarFuud");
            System.out.println("===========================");
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
            int pilihan;
            while (true){
                System.out.print("=> ");
                if (scanner.hasNextInt()){
                    pilihan = scanner.nextInt();
                    break;
                } else {
                    System.out.println("Masukkan angka yang benar");
                    scanner.nextLine();
                }
            }
            if (pilihan == 0) {
                break;
            } else if (pilihan >= 1 && pilihan <= 5) {
                String makanan = "";
                int harga = 0;
                if (pilihan == 1){
                    makanan = "Nasi Goreng   ";
                    harga = 15000;
                } else if (pilihan == 2) {
                    makanan = "Mie Goreng   ";
                    harga = 13000;
                } else if (pilihan == 3) {
                    makanan = "Nasi + Ayam   ";
                    harga = 18000;
                } else if (pilihan == 4) {
                    makanan = "Es Teh Manis";
                    harga = 3000;
                } else if (pilihan == 5) {
                    makanan = "Es jeruk   ";
                    harga = 5000;
                }

                System.out.println("===========================");
                System.out.println("Berapa Pesanan Anda");
                System.out.println("===========================");
                System.out.println();
                System.out.println(makanan+ "\t | "+ harga);
                System.out.println("(input 0 untuk kembali)");
                System.out.println();
                int qty;
                while (true){
                    System.out.print("qty => ");
                    if (scanner.hasNextInt()){
                        qty = scanner.nextInt();
                        break;
                    } else {
                        System.out.println("Masukkan angka yang benar");
                        scanner.nextLine();
                    }
                }
                if (qty == 0) {
                    continue;
                }
                pesanan.put(makanan, pesanan.getOrDefault(makanan,0)+qty);
                totalHarga += qty * harga;

            } else if (pilihan == 99) {
                System.out.println("===========================");
                System.out.println("Konfirmasi & Pembayaran");
                System.out.println("===========================");
                System.out.println();
                for (Map.Entry<String, Integer> entry : pesanan.entrySet()){
                    String makanan = entry.getKey();
                    int harga = hargaMakanan.get(makanan);
                    int qty = entry.getValue();
                    int subtotal = qty * harga;
                    totalPesanan += qty;
                    System.out.println(makanan+ "\t" + qty + "\t \t" + subtotal);
                }
                System.out.println("------------------------------+");
                System.out.println("Total \t \t \t"+ totalPesanan +"\t \t" + totalHarga);
                System.out.println();
                System.out.println("1. Konfirmasi dan Bayar");
                System.out.println("2. Kembali ke Menu Utama");
                System.out.println("0. Keluar Aplikasi");
                System.out.println();
                int konfirmasi;
                while (true){
                    System.out.print("=> ");
                    if (scanner.hasNextInt()){
                        konfirmasi = scanner.nextInt();
                        break;
                    } else {
                        System.out.println("Masukkan angka yang benar");
                        scanner.nextLine();
                    }
                }
                if (konfirmasi == 1) {
                    simpanStruk(pesanan, totalHarga, hargaMakanan, totalPesanan);
                    continue;
                } else if (konfirmasi == 2) {
                    continue;
                } else if (konfirmasi == 0) {
                    break;
                }
            } else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }
    private static void simpanStruk(Map<String, Integer> pesanan, int totalHarga, Map<String, Integer> hargaMakanan, int totalPesanan) {
        try {
            FileWriter fileWriter = new FileWriter("struk_pembayaran.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("===========================");
            printWriter.println("BinarFuud");
            printWriter.println("===========================");
            printWriter.println("");
            printWriter.println("Terima Kasih sudah memesan");
            printWriter.println("di BinarFuud");
            printWriter.println("");
            printWriter.println("Dibawah ini adalah pesanan anda");
            printWriter.println("");

            for (Map.Entry<String, Integer> entry : pesanan.entrySet()) {
                String makanan = entry.getKey();
                int harga = hargaMakanan.get(makanan);
                int qty = entry.getValue();
                int subtotal = qty * harga;
                totalPesanan += qty;
                printWriter.println("|"+makanan+ "\t" + qty + "\t \t" + subtotal);
            }

            printWriter.println("------------------------------+");
            printWriter.println("Total \t \t \t"+ totalPesanan +"\t \t" + totalHarga);
            printWriter.println("");
            printWriter.println("Pembayaran : BinarCash");
            printWriter.println("");
            printWriter.println("===========================");
            printWriter.println("Simpan struk ini sebagai");
            printWriter.println("bukti pembayaran");
            printWriter.println("===========================");

            printWriter.close();
            System.out.println("Struk pembayaran telah disimpan dalam file 'struk_pembayaran.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Gagal menyimpan struk pembayaran.");
        }
    }
}