import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Interface
interface Diskonable {
    double hitungDiskon();
    void tampilkanDiskon();
}

// Abstract Class
abstract class Produk {
    protected final int id;
    protected String namaProduk;
    protected double harga;
    protected int stok;

    public Produk(int id, String namaProduk, double harga, int stok) {
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

    public final void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public void setHarga(double harga) {
        if (harga < 0) throw new IllegalArgumentException("Harga tidak boleh kurang dari 0");
        this.harga = harga;
    }

    public void setHarga(double harga, boolean ppn) {
        if (harga < 0) throw new IllegalArgumentException("Harga tidak boleh kurang dari 0");
        this.harga = ppn ? harga * 1.11 : harga;
    }

    public void setStok(int stok) {
        if (stok < 0) throw new IllegalArgumentException("Stok tidak boleh kurang dari 0");
        this.stok = stok;
    }

    public abstract void displayInfo();
}

// Child Class 1
class GasLPG extends Produk {
    public GasLPG(int id, String namaProduk, double harga, int stok) {
        super(id, namaProduk, harga, stok);
    }

    @Override
    public void displayInfo() {
        System.out.printf("%-5d | %-15s | Rp %-8.2f | %-5d (Subsidi)\n", id, namaProduk, harga, stok);
    }
}

// Child Class 2 + Interface
class GasNonSubsidi extends Produk implements Diskonable {
    public GasNonSubsidi(int id, String namaProduk, double harga, int stok) {
        super(id, namaProduk, harga, stok);
    }

    @Override
    public void displayInfo() {
        System.out.printf("%-5d | %-15s | Rp %-8.2f | %-5d (Non-Subsidi)\n", id, namaProduk, harga, stok);
        tampilkanDiskon();
    }

    @Override
    public double hitungDiskon() {
        return harga * 0.05;
    }

    @Override
    public void tampilkanDiskon() {
        double diskon = hitungDiskon();
        System.out.printf("      -> Diskon: Rp %.2f\n", diskon);
    }
}

// Final Class Program Utama
public final class ManajemenPenjualanLPG {
    private static final ArrayList<Produk> daftarGas = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int idCounter = 1;
    private static int totalProduk = 0; // STATIC variable

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n======================================");
            System.out.println("Sistem Manajemen Penjualan Gas LPG Toko Aziz");
            System.out.println("======================================");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Lihat Produk");
            System.out.println("3. Update Produk");
            System.out.println("4. Hapus Produk");
            System.out.println("5. Lihat Total Produk");
            System.out.println("6. Keluar");
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
                    case 5 -> System.out.println("Total produk yang sudah ditambahkan: " + totalProduk);
                    case 6 -> {
                        System.out.println("Terima kasih! Keluar dari program.");
                        return;
                    }
                    default -> System.out.println("Pilihan tidak valid, coba lagi!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka!");
                scanner.nextLine();
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

            System.out.print("Apakah produk ini subsidi? (y/n): ");
            String jenis = scanner.nextLine().toLowerCase();

            Produk produk;
            if (jenis.equals("y")) {
                produk = new GasLPG(idCounter++, nama, harga, stok);
            } else {
                produk = new GasNonSubsidi(idCounter++, nama, harga, stok);
            }

            daftarGas.add(produk);
            totalProduk++; // STATIC usage
            System.out.println("Produk berhasil ditambahkan! Total produk: " + totalProduk);
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid! Pastikan harga dan stok berupa angka.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void lihatProduk() {
        if (daftarGas.isEmpty()) {
            System.out.println("Belum ada produk tersedia.");
            return;
        }
        System.out.println("\n===========================================================");
        System.out.printf("%-5s | %-15s | %-10s | %-20s\n", "ID", "Nama", "Harga", "Stok");
        System.out.println("===========================================================");
        for (Produk gas : daftarGas) {
            gas.displayInfo();
        }
        System.out.println("===========================================================");
    }

    private static void updateProduk() {
        try {
            System.out.print("Masukkan ID produk yang ingin diperbarui: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            for (Produk gas : daftarGas) {
                if (gas.getId() == id) {
                    System.out.print("Masukkan nama produk baru: ");
                    gas.setNamaProduk(scanner.nextLine());
                    System.out.print("Masukkan harga produk baru: ");
                    double hargaBaru = scanner.nextDouble();
                    System.out.print("Apakah termasuk PPN (y/n)? ");
                    scanner.nextLine();
                    String ppn = scanner.nextLine().toLowerCase();
                    gas.setHarga(hargaBaru, ppn.equals("y"));

                    System.out.print("Masukkan stok baru: ");
                    gas.setStok(scanner.nextInt());
                    scanner.nextLine();
                    System.out.println("Produk berhasil diperbarui!");
                    return;
                }
            }
            System.out.println("Produk dengan ID tersebut tidak ditemukan.");
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid!");
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

            for (Produk gas : daftarGas) {
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
