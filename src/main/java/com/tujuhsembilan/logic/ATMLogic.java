package com.tujuhsembilan.logic;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ATMLogic {

  public static void login() {
    System.out.println("Selamat datang di ATM");
    System.out.println("Silakan masukkan username dan password Anda.");

    System.out.print("Username: ");
    String inputUsername = scanner.nextLine();

    System.out.print("Password: ");
    String inputPassword = scanner.nextLine();

    if (inputUsername.equals(username) && inputPassword.equals(password)) {
      System.out.println("Login berhasil. Selamat datang, " + username + "!");
    } else {
      System.out.println("Login gagal. Silakan coba lagi.");
    }
  }

  public static void accountBalanceInformation() {
    System.out.println("Informasi Saldo Rekening");
    System.out.print("Masukkan nomor rekening: ");
    String accountNumber = scanner.nextLine();

    if (accountBalances.containsKey(accountNumber)) {
      double saldo = accountBalances.get(accountNumber);
      System.out.println("Saldo rekening " + accountNumber + ": " + saldo);
    } else {
      System.out.println("Nomor rekening tidak valid.");
    }
  }

  public static void moneyWithdrawal() {
    System.out.println("Penarikan Uang");
    System.out.print("Masukkan nomor rekening: ");
    String accountNumber = scanner.nextLine();

    if (accountBalances.containsKey(accountNumber)) {
      double saldo = accountBalances.get(accountNumber);
      System.out.print("Masukkan jumlah uang yang akan ditarik: ");
      double withdrawalAmount = scanner.nextDouble();

      if (withdrawalAmount <= saldo) {
        double newSaldo = saldo - withdrawalAmount;
        accountBalances.put(accountNumber, newSaldo);
        System.out.println("Penarikan berhasil. Saldo terkini: " + newSaldo);
      } else {
        System.out.println("Saldo tidak mencukupi.");
      }
    } else {
      System.out.println("Nomor rekening tidak valid.");
    }
  }

  public static void phoneCreditsTopUp() {
    System.out.println("Isi Pulsa");
    System.out.print("Masukkan nomor telepon: ");
    String phoneNumber = scanner.nextLine();

    System.out.print("Masukkan jumlah pulsa yang akan diisi: ");
    double topUpAmount = scanner.nextDouble();

    if (isValidPhoneNumber(phoneNumber)) {
      // Proses pengisian pulsa
      double saldo = accountBalances.get(phoneNumber);
      double newSaldo = saldo + topUpAmount;
      accountBalances.put(phoneNumber, newSaldo);
      System.out.println("Pengisian pulsa berhasil. Saldo terkini: " + newSaldo);
    } else {
      System.out.println("Nomor telepon tidak valid.");
    }
  }

  public static void electricityBillsToken() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Masukkan nomor pelanggan listrik: ");
    String nomorPelanggan = scanner.nextLine();

    double tagihan = getElectricityBillFromServer(nomorPelanggan);

    if (tagihan > 0) {
      System.out.println("Jumlah tagihan listrik: $" + tagihan);

      System.out.println("Masukkan jumlah token yang akan dibeli: ");
      double jumlahToken = scanner.nextDouble();

      if (jumlahToken <= tagihan) {
        double sisaTagihan = tagihan - jumlahToken;
        System.out.println("Pembelian token berhasil!");

        updateElectricityBillOnServer(nomorPelanggan, sisaTagihan);
      } else {
        System.out.println("Jumlah token melebihi tagihan listrik. Pembelian gagal.");
      }
    } else {
      System.out.println("Nomor pelanggan tidak valid atau tidak memiliki tagihan.");
    }
  }

  public static void accountMutation() {
  }

  public static void moneyDeposit() {
  }

}
