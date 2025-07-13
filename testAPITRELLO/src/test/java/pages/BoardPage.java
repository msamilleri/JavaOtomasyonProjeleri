// src/test/java/pages/BoardPage.java
package pages;

import config.TrelloAPIParametreleri;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List; // List için import

public class BoardPage extends BasePage {

    private String boardId;
    private String boardName;
    private int listeSirasi;

    public BoardPage() {
        this.listeSirasi = TrelloAPIParametreleri.TRELLO_KART_LIST_SIRA_NO;
        this.boardName = TrelloAPIParametreleri.TRELLO_BOARD_ADI;
        RestAssured.baseURI = TrelloAPIParametreleri.TRELLO_BASE_URL;
    }


    public Response createBoard() {
        Response response = requestSpec()
                .queryParam("name", boardName)
                .when()
                .post("/boards/");
        boardId = response.jsonPath().getString("id");
        return response;
    }


    public Response deleteBoard() {
        if (boardId == null || boardId.isEmpty()) {
            throw new IllegalStateException("Board ID is not set. Cannot delete board.");
        }
        Response response = requestSpec()
                .when()
                .delete("/boards/" + boardId);
        return response;
    }


    public String getSeciliListeElemani(String boardId, int listeSirasi) {
        if (boardId == null || boardId.isEmpty()) {
            throw new IllegalArgumentException("Board ID cannot be null or empty for getting lists.");
        }
        Response response = requestSpec()
                .when()
                .get("/boards/" + boardId + "/lists");


        if (response.getStatusCode() == 200) {
            List<String> listIds = response.jsonPath().getList("id");
            List<String> listNames = response.jsonPath().getList("name");

            if (!listIds.isEmpty()) {
                return listIds.get(listeSirasi); // Board Üzeriden Otomatik Oluşan Listlerden birincisini seçiyoruz
            } else {
                System.out.println("Board üzerinde liste/ler bulunamadı");
                return null;
            }
        } else {
            System.out.println("BOARD'tan liste/ler alınırken hata meydanda geldi !!!");
            return null;
        }
    }

    public String getBoardId() {
        return boardId;
    }

    public int getListeSirasi() {
        return listeSirasi;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}