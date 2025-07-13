
package pages;

import config.TrelloAPIParametreleri;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

public class BasePage {
    public BasePage() {
        RestAssured.baseURI = TrelloAPIParametreleri.TRELLO_BASE_URL;
    }

    protected RequestSpecification requestSpec() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParam("key", TrelloAPIParametreleri.TRELLO_API_KEY)
                .queryParam("token", TrelloAPIParametreleri.TRELLO_TOKEN);
    }
}