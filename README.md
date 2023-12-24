## Catatan

Semua requirement challengenya saya letakan di satu aplikasi mas, di BinarFud Main


• **Transactional**: Transaction saya aplikasikan sesuai permintaan challenge mas, saya bikin setiap produk punya stock, kemudian transactionalnya saya letakan di **OrderService** dimana setiap order yang berhasil dia akan mengurangi stock produk dan jika ada error dalam proses order maka stock akan tetap tidak ada perubahan. 


• **Scheduled**: Untuk penjadwalnya saya buat di kelas NotificationPromo mas, jadi untuk user saya buatkan variabel boolean **subscribed**, kemudian di setiap produk ada diskon. Nanti methodnya mengambil semua data produk yg memiliki diskon kemudian dikirim ke email pengguna yang sebscribenya true.


• **Massange Broker & Firebase**: Saya memakai cara firebase seperti mas ilyas kemaren, cuma implementasinya yg beda mas. Untuk tokennya saya simpan di application properties, kemudian saya implementasikan di method update diskon produk. Jadi setiap seller menambahkan diskon baru yg dimana nilainya diatas 0% maka akan muncul pop up notifikasi di firebasenya
