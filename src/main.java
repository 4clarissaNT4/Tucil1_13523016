import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Apakah ingin input melalui file test case berekstensi .txt? (ya/tidak): ");
        String useFileInput = scanner.nextLine().trim().toLowerCase();

        if (useFileInput.equals("ya")) {
            System.out.print("Masukkan nama file (dengan ekstensi .txt): ");
            String fileName = scanner.nextLine().trim();
            List<String> fileInput = File.readFile(fileName);
            if (fileInput == null) {
                System.out.println("Gagal membaca file. Input akan dilakukan secara manual.");
                useFileInput = "tidak";
            } else {
                processInput(fileInput);
                return;
            }
        }

        if (useFileInput.equals("tidak")) {
            List<String> manualInput = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                manualInput.add(line);
                if (line.equals("stop")) {
                    break;
                }
            }
            processInput(manualInput);
        } else {
            System.out.println("Input tidak valid. Program akan berhenti.");
        }

        scanner.close();
    }

    private static void processInput(List<String> input) {
        Scanner scanner = new Scanner(String.join("\n", input));
        int N = 0, M = 0, P = 0;
        while (true) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split(" ");
            if (parts.length == 3) {
                try {
                    N = Integer.parseInt(parts[0]);
                    M = Integer.parseInt(parts[1]);
                    P = Integer.parseInt(parts[2]);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Input nilai N, M, atau P tidak valid. Mohon diinput ulang.");
                }
            } else {
                System.out.println("Input nilai N, M, atau P tidak valid. Mohon diinput ulang.");
            }
        }
        while (true) {
            String S = scanner.nextLine().trim();
            if (S.equals("DEFAULT")) {
                break;
            } else {
                System.out.println("Input tidak valid! Hanya terdapat pilihan DEFAULT.");
            }
        }

        List<List<String>> listBlok = new ArrayList<>();
        List<String> currBlok = new ArrayList<>();
        char prevChar = ' ';
        int countBlok = 0;
        boolean validInput = true;
        boolean stopMarkerFound = false;
        while (countBlok < P) {
            if (!scanner.hasNextLine()) {
                System.out.println("Jumlah blok tidak sesuai. Mohon diinput ulang.");
                validInput = false;
                break;
            }
            String inputLine = scanner.nextLine().trim();
            if (inputLine.equals("stop")) {
                stopMarkerFound = true;
                break;
            }
            if (inputLine.isEmpty()) {
                System.out.println("Jumlah blok tidak sesuai. Mohon diinput ulang.");
                validInput = false;
                break;
            }
            StringBuilder cleanedInput = new StringBuilder();
            for (char c : inputLine.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    cleanedInput.append(c);
                } else {
                    cleanedInput.append(' '); // Semua input pada blok selain huruf kapital dianggap spasi (' ')
                }
            }
            char currChar = '\0';
            for (char c : cleanedInput.toString().toCharArray()) {
                if (Character.isUpperCase(c)) {
                    currChar = c;
                    break;
                }
            }
            if (currChar == '\0') {
                System.out.println("Blok tidak valid! Mohon diinput ulang.");
                validInput = false;
                break;
            }
            if (currBlok.isEmpty() || currChar != prevChar) {
                if (!currBlok.isEmpty()) {
                    if (!isBlokValid(currBlok)) {
                        System.out.println("Blok tidak valid! Mohon diinput ulang.");
                        validInput = false;
                        break;
                    }
                    listBlok.add(new ArrayList<>(currBlok));
                    countBlok++;
                    if (countBlok == P) {
                        break;
                    }
                }
                currBlok.clear();
            }
            currBlok.add(cleanedInput.toString());
            prevChar = currChar;
        }
        if (!validInput || !stopMarkerFound) {
            System.out.println("Jumlah blok kelebihan! Mohon diinput ulang.");
            return;
        }
        if (!currBlok.isEmpty() && countBlok < P) {
            if (!isBlokValid(currBlok)) {
                System.out.println("Blok tidak valid! Mohon diinput ulang.");
                return;
            }
            listBlok.add(new ArrayList<>(currBlok));
            countBlok++;
        }

        // Call Puzzler to solve the puzzle
        Puzzler puzzler = new Puzzler(N, M, listBlok);
        long startTime = System.currentTimeMillis();
        boolean solved = puzzler.solve();
        long endTime = System.currentTimeMillis();
        if (solved) {
            puzzler.printBoard();
            System.out.println("Papan berhasil diisi penuh.");
        } else {
            System.out.println("Tidak ada solusi.");
        }
        System.out.println("Waktu pencarian: " + (endTime - startTime) + " ms");
        System.out.println("Banyak kasus yang ditinjau: " + puzzler.getCaseCount());

        // Ask if the user wants to save the solution
        System.out.print("Apakah anda ingin menyimpan solusi? (ya/tidak): ");
        String saveSolution = scanner.nextLine().trim().toLowerCase();
        if (saveSolution.equals("ya")) {
            System.out.print("Masukkan nama file untuk menyimpan solusi (dengan ekstensi .txt): ");
            String outputFileName = scanner.nextLine().trim();
            List<String> outputLines = new ArrayList<>();
            outputLines.add("Papan berhasil diisi dengan benar.");
            for (int i = 0; i < N; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < M; j++) {
                    line.append(puzzler.getBoard()[i][j]);
                }
                outputLines.add(line.toString());
            }
            outputLines.add("Waktu pencarian: " + (endTime - startTime) + " ms");
            outputLines.add("Banyak kasus yang ditinjau: " + puzzler.getCaseCount());
            File.writeFile(outputFileName, outputLines);
            System.out.println("Solusi telah disimpan ke " + outputFileName);
        }

        scanner.close();
    }

    // Fungsi untuk mengecek apakah blok valid atau tidak
    public static boolean isBlokValid(List<String> blok) {
        int rows = blok.size();
        int cols = blok.get(0).length();

        for (int i = 0; i < rows; i++) {
            String row = blok.get(i);
            for (int j = 0; j < row.length(); j++) {
                char currChar = row.charAt(j);
                if (!Character.isUpperCase(currChar)) continue;

                boolean bersisian = false;
                if (i > 0 && j < blok.get(i - 1).length() && blok.get(i - 1).charAt(j) == currChar)
                    bersisian = true; // mengecek bagian atas huruf
                if (i < rows - 1 && j < blok.get(i + 1).length() && blok.get(i + 1).charAt(j) == currChar)
                    bersisian = true; // mengecek bagian bawah huruf
                if (j > 0 && row.charAt(j - 1) == currChar)
                    bersisian = true; // mengecek bagian kiri huruf
                if (j < row.length() - 1 && row.charAt(j + 1) == currChar)
                    bersisian = true; // mengecek bagian kanan huruf
                if (!bersisian) return false;
            }
        }
        return true;
    }
}