# Tucil1_13523016
Repositori Tugas Kecil 1 Strategi Algoritma, TA 2024-2025

Repositori program IQ Puzzler Pro pada Tugas Kecil 1 Strategi Algoritma. Program IQ Puzzle Pro ini digunakan untuk mencari solusi dari suatu test case yang diberikan di mana tujuannya adalah untuk memberikan output tampilan konfigurasi blok puzzle yang mengisi papan dengan penuh. Solusi yang ditunjukkan hanya 1 solusi dari sekian banyak solusi yang ada dan apabila sama sekali tidak terdapat solusi yang mengisi penuh papan maka dianggap tidak ada solusi. Program IQ Puzzle Pro ini menggunakan algoritma brute force untuk mendapatkan solusi dari masukkan blok-blok pada file test case yang berekstensi .txt ataupun dengan masukkan manual.

## Prerequisites
Java Development Kit (JDK) version 8 or higher
Java perlu diinstalasi pada sistem. Java dapat diunduh melalui website resmi Oracle.

Verifikasi Instalasi Java:
java -version
javac -version

### Installation and Compilation Program
#### Step 1: Clone/Download Repository
git clone https://github.com/4clarissaNT4/Tucil1_13523016.git

#### Step 2: Running The Program
javac src/main.java

## Configuration
### 1. Manual Input Example
Saat Anda memulai program, akan diminta masukkan seperti ini: 

Apakah ingin input melalui file test case berekstensi .txt? (ya/tidak):tidak
5 5 7
DEFAULT
A
AA
B
BB
C
CC
D
DD
EE
EE
E
FF
FF
F
GGG
stop

lalu dilakukan enter setelah input tersebut untuk memulai mencari solusi namun terdapat masalah pada pengeluaran tampilan papan sehingga tidak dapat memunculkan hasil solusi. Lalu pada masukkan tersebut diperlukan kata akhir yaitu "stop" untuk mengehentikan program jika jumlah blok sudah terpenuhi sesuai nilai variabel P pada main.java. Jika jumlah blok tidak terpenuhi maka akan diminta masukkan ulang dari awal.

### 2. File Input
Siapkan file test case pada folder test dan diisi sesuai format masukkan manual di atas

Contoh isi dari file berekstensi .txt di folder test adalah sebagai berikut.
5 5 7
DEFAULT
A
AA
B
BB
C
CC
D
DD
EE
EE
E
FF
FF
F
GGG
stop

Setelah itu jalankan program seperti biasa dan pilih opsi untuk membaca file seperti ini:
Apakah ingin input melalui file test case berekstensi .txt? (ya/tidak): a
Masukkan nama file (dengan ekstensi .txt): tc1.txt


### Author
Clarissa Nethania Tambunan - 13523016