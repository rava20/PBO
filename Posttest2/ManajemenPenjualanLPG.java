import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class GasLPG {
    private final int id;
    private String namaProduk;
    private double harga;
    private int stok;

    public GasLPG(int id, String namaProduk, double harga, int stok) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
    }

    public int getId() {
        return id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh kurang dari 0");
        }
        this.harga = harga;
    }

    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh kurang dari 0");
        }
        this.stok = stok;
    }
}

public class ManajemenPenjualanLPG {
    private static final ArrayList<GasLPG> daftarGas = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int idCounter = 1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n======================================");
            System.out.println("Sistem Manajemen Penjualan Gas LPG Toko Aziz");
            System.out.println("======================================");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Lihat Produk");
            System.out.println("3. Update Produk");
            System.out.println("4. Hapus Produk");
            System.out.println("5. Keluar");
            System.out.println("======================================");
            System.out.print("Pilih menu: ");
            
            try {
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
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka!");
                scanner.nextLine(); // Membersihkan buffer input
            }
        }
    }

    private static void tambahProduk() {
        try {
            System.out.print("Masukkan nama produk: ");
            String nama = scanner.nextLine();
            System.out.print("Masukkan harga produk: ");
            double harga = scanner.nextDouble();
            System.out.print("Masukkan jumlah stok: ");
            int stok = scanner.nextInt();
            scanner.nextLine();

            daftarGas.add(new GasLPG(idCounter++, nama, harga, stok));
            System.out.println("Produk berhasil ditambahkan!");
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid! Pastikan harga dan stok berupa angka.");
            scanner.nextLine(); // Membersihkan buffer input
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void lihatProduk() {
        if (daftarGas.isEmpty()) {
            System.out.println("Belum ada produk tersedia.");
            return;
        }
        System.out.println("\n==============================================");
        System.out.printf("%-5s | %-15s | %-10s | %-5s\n", "ID", "Nama", "Harga", "Stok");
        System.out.println("==============================================");
        for (GasLPG gas : daftarGas) {
            System.out.printf("%-5d | %-15s | Rp %-8.2f | %-5d\n", gas.getId(), gas.getNamaProduk(), gas.getHarga(), gas.getStok());
        }
        System.out.println("==============================================");
    }

    private static void updateProduk() {
        try {
            System.out.print("Masukkan ID produk yang ingin diperbarui: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            for (GasLPG gas : daftarGas) {
                if (gas.getId() == id) {
                    System.out.print("Masukkan nama produk baru: ");
                    gas.setNamaProduk(scanner.nextLine());
                    System.out.print("Masukkan harga produk baru: ");
                    gas.setHarga(scanner.nextDouble());
                    System.out.print("Masukkan stok baru: ");
                    gas.setStok(scanner.nextInt());
                    scanner.nextLine();
                    System.out.println("Produk berhasil diperbarui!");
                    return;
                }
            }
            System.out.println("Produk dengan ID tersebut tidak ditemukan.");
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid! Pastikan ID, harga, dan stok berupa angka.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void hapusProduk() {
        try {
            System.out.print("Masukkan ID produk yang ingin dihapus: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            for (GasLPG gas : daftarGas) {
                if (gas.getId() == id) {
                    daftarGas.remove(gas);
                    System.out.println("Produk berhasil dihapus!");
                    return;
                }
            }
            System.out.println("Produk dengan ID tersebut tidak ditemukan.");
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid! Pastikan ID berupa angka.");
            scanner.nextLine();
        }
    }
}
