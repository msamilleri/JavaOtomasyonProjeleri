
package trellotest;

import config.TrelloAPIParametreleri;
import pages.BoardPage;
import pages.CardPage;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SenaryoTrello {

    // Test senaryolarndan kullancağımız objecleri oluşturuyoruz.
    public static final Random random = new Random();
    public static final BoardPage boardPage = new BoardPage();
    public static final CardPage cardPage = new CardPage();

    public static String boardId;
    public static int listesiSirasi;
    public static String varolanlistID; // Var olan listenin ID
    public static List<String> olusutrlanKartIDS = new ArrayList<>(); // Oluşturlan kartların ID'leri tutmak için

    public static void main(String[] args) {
        // Tüm teset Senaryolarını çalıştırdığımız fonksiyon
        testSenaryolariCalistir();
    }

    public static void testSenaryolariCalistir() {
        //Trello üzerinde bir board oluşturunuz.
        trelloBoardOlustur();
        boardtanListeAl();

        //Oluşturduğunuz board’a iki tane kart oluşturunuz.
        kartOlustur();

        //Oluşturduğunuz bu iki karttan random olacak sekilde bir tanesini güncelleyiniz.
        rastgeleKartGuncelle();

        //Oluşturduğunuz kartları siliniz.
        kartlariSil();

        //Oluşturduğunuz board’u siliniz.
        trelloBoardSil();

    }

    //Trelloda Board oluşturma FOnksiyonu
    public static void trelloBoardOlustur() {
        //  Trello üzerinden bir board oluşturlur
        System.out.println("\n" + TrelloAPIParametreleri.TRELLO_BOARD_ADI + " Adlı board oluşturuluyor");
        Response boardOlustur = boardPage.createBoard();
        if (boardOlustur.getStatusCode() == 200) {
            boardId = boardPage.getBoardId();
            System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + "Boardu başarıyla oluşturuldu. ID: " + boardId);
        } else {
            System.err.println("Board  oluşturulamadi, Durum Kodu: " + boardOlustur.getStatusCode());
            return; // Hata meydana geldiğinde test senaryosu durdurulur
        }
    }

    //Board oluşan listlerden birinin id  alma  FOnksiyonu
    public static void boardtanListeAl() {
        // Var olan boardaki listelerden birinciyi al
        System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + " var olan listeler alınıyor");
        varolanlistID = boardPage.getSeciliListeElemani(boardId, listesiSirasi);
        if (varolanlistID != null && !varolanlistID.isEmpty()) {
            System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + " var olan liste/ler başarıyla alındı  ");
        } else {
            System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + "  üzerinde liste bulunamamıştır.  ");
            return; // Hata meydana geldiğinde test senaryosu durdurulur
        }
    }

    //Seçili listeye 2 tane kart oluşturma fonksiyonu
    public static void kartOlustur() {
        // Boarda iki tane kart oluşturlur
        System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + " 2 kart oluşturuluyor");
        for (int i = 1; i <= 2; i++) {
            String kartAdi = TrelloAPIParametreleri.KART_AD_BASLIGI + i;
            Response kartDurumKodu = cardPage.createCard(varolanlistID, kartAdi);
            if (kartDurumKodu.getStatusCode() == 200) {
                String kardID = kartDurumKodu.jsonPath().getString("id");
                olusutrlanKartIDS.add(kardID);
                System.out.println("Kart " + i + " oluşturuldu. ID: " + kardID);
            } else {
                System.out.println("Kart " + i + " oluşturma başarısız oldu. Durum Kodu: " + kartDurumKodu.getStatusCode());

            }
        }

        // Kartlar oluştuğundan emin olunur
        if (olusutrlanKartIDS.isEmpty()) {
            System.out.println("Hiç kart oluşturulamadı ve Senaryo durduruluyor. Lütfen Kodunuza debug giriniz !!!");
            return; // Hata meydana geldiğinde test senaryosu durdurulur
        }
    }

    //Oluşturlan kartlardan birini random seçme fonksiyonu
    public static void rastgeleKartGuncelle() {
        // Random bir kartı güncellenir
        System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + " üzeriden random bir kart güncelleniyor");
        String randomKartID = olusutrlanKartIDS.get(random.nextInt(olusutrlanKartIDS.size())); // Var oaln kartlardan bir tanesi seçilir
        String kartAdiGuncelle = TrelloAPIParametreleri.YENI_KART_ADI; // Seçilen karta belirlediğimiz ise verilir.
        Response kartAdGuncelemeDurumu = cardPage.updateCard(randomKartID, kartAdiGuncelle);
        if (kartAdGuncelemeDurumu.getStatusCode() == 200) {
            System.out.println("Kart ID '" + randomKartID + "' başarıyla '" + kartAdiGuncelle + "' olarak güncellendi.");
        } else {
            System.err.println("Kart güncelleme başarısız oldu ");
        }

    }

    //Liste içinde olan kartları silme fonksiyonu
    public static void kartlariSil() {
        //  Oluşturlan kartlar silinir
        System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + ", Oluşturlan kartlar siliniyor");
        List<String> silinecekKartlar = new ArrayList<>(olusutrlanKartIDS);
        for (String cardId : silinecekKartlar) {
            Response silinecekKartDurumKodu = cardPage.deleteCard(cardId);
            if (silinecekKartDurumKodu.getStatusCode() == 200) {
                System.out.println("Kart ID '" + cardId + "' başarıyla silindi.");
                olusutrlanKartIDS.remove(cardId); // Başarıyla silinen kartı orijinal listeden çıkar
            } else {
                System.err.println("HATA: Kart silme başarısız oldu: " + cardId + ". Durum Kodu: " + silinecekKartDurumKodu.getStatusCode());

            }
        }
        if (olusutrlanKartIDS.isEmpty()) {
            System.out.println("Tüm kartlar başarıyla silindi.");
        } else {
            System.err.println("UYARI: Bazı kartlar silinemedi. Kalan kartlar: " + olusutrlanKartIDS.size());
        }

    }

    //Oluşturlan Boardı silme fonksiyonu
    public static void trelloBoardSil() {
        System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + " siliniyor");
        boardPage.setBoardId(boardId);
        Response deleteBoardResponse = boardPage.deleteBoard();
        if (deleteBoardResponse.getStatusCode() == 200) {
            System.out.println(TrelloAPIParametreleri.TRELLO_BOARD_ADI + " başarıyla silindi.");
        } else {
            System.err.println("Board silme başarısız oldu");
        }

    }


}




