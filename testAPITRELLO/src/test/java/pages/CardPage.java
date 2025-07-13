

package pages;

import config.TrelloAPIParametreleri;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

public class CardPage extends BasePage {

    public CardPage() {
        RestAssured.baseURI = TrelloAPIParametreleri.TRELLO_BASE_URL;
    }


    public Response createCard(String listId, String cardName) {
        if (listId == null || listId.isEmpty()) {
            throw new IllegalStateException("List ID is not set. Cannot create card.");
        }
        Response response = requestSpec()
                .queryParam("idList", listId)
                .queryParam("name", cardName)
                .when()
                .post("/cards/");
        return response;
    }

    public Response updateCard(String cardIdToUpdate, String newCardName) {
        if (cardIdToUpdate == null || cardIdToUpdate.isEmpty()) {
            throw new IllegalArgumentException("Card ID cannot be null or empty for updating card.");
        }
        Response response = requestSpec()
                .queryParam("name", newCardName)
                .when()
                .put("/cards/" + cardIdToUpdate);
        return response;
    }

    public Response deleteCard(String cardIdToDelete) {
        if (cardIdToDelete == null || cardIdToDelete.isEmpty()) {
            throw new IllegalArgumentException("Card ID cannot be null or empty for deleting card.");
        }
        Response response = requestSpec()
                .when()
                .delete("/cards/" + cardIdToDelete);
        return response;
    }
}