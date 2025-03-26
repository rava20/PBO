import java.util.ArrayList;
import java.util.Scanner;

class GasLPG {
    int id;
    String namaProduk;
    double harga;
    int stok;

    public GasLPG(int id, String namaProduk, double harga, int stok) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nama: " + namaProduk + ", Harga: Rp " + harga + ", Stok: " + stok;
    }
}

public class App {
    private static final ArrayList<GasLPG> daftarGas = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int idCounter = 1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nSistem Manajemen Penjualan Gas LPG Toko Aziz");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Lihat Produk");
            System.out.println("3. Update Produk");
            System.out.println("4. Hapus Produk");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); 

            switch (pilihan) {
                case 1 -> tambahProduk();
                case 2 -> lihatProduk();
                case 3 -> updateProduk();
                case 4 -> hapusProduk();
                case 5 -> {
                    System.out.println("Terima kasih! Keluar dari program.");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid, coba lagi!");
            }
        }
    }

    private static void tambahProduk() {
        System.out.print("Masukkan nama produk: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga produk: ");
        double harga = scanner.nextDouble();
        System.out.print("Masukkan jumlah stok: ");
        int stok = scanner.nextInt();
        scanner.nextLine();

        daftarGas.add(new GasLPG(idCounter++, nama, harga, stok));
        System.out.println("Produk berhasil ditambahkan!");
    }

    private static void lihatProduk() {
        if (daftarGas.isEmpty()) {
            System.out.println("Belum ada produk tersedia.");
            return;
        }
        System.out.println("\nDaftar Produk Gas LPG:");
        for (GasLPG gas : daftarGas) {
            System.out.println(gas);
        }
    }

    private static void updateProduk() {
        System.out.print("Masukkan ID produk yang ingin diperbarui: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (GasLPG gas : daftarGas) {
            if (gas.id == id) {
                System.out.print("Masukkan nama produk baru: ");
                gas.namaProduk = scanner.nextLine();
                System.out.print("Masukkan harga produk baru: ");
                gas.harga = scanner.nextDouble();
                System.out.print("Masukkan stok baru: ");
                gas.stok = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Produk berhasil diperbarui!");
                return;
            }
        }
        System.out.println("Produk dengan ID tersebut tidak ditemukan.");
    }

    private static void hapusProduk() {
        System.out.print("Masukkan ID produk yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (GasLPG gas : daftarGas) {
            if (gas.id == id) {
                daftarGas.remove(gas);
                System.out.println("Produk berhasil dihapus!");
                return;
            }
        }
        System.out.println("Produk dengan ID tersebut tidak ditemukan.");
    }
}