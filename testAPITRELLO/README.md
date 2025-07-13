# Trello Web Service Otomasyonu Projesi

## Gerekli Araçlar

1. Java
2. Maven
3. IntelliJ IDEA
4. Rest Assured

```
src/
├── main/
│   ├── java/
│   └── resources/                                   
│      
├── test/
│   ├── java/
│   │   └── config/
│   │   │    ├── TrelloAPIParametreleri.java            # Test senaryosu için gerekli parametreleri vardır.(API,TOKEN vb)
│       ├── pages/
│       │    ├── BasePage.java                          #Ortak 
│       │    ├── BoardPage.java                         #Board işlemleri için gerekli RESET-API isteklerini bulundurur
│       │    ├── CardPage.java                          #Card işlemleri için gerekli RESET-API isteklerini bulundurur
│       ├── trelloTest/
│       │    ├── SenaryoTest.java                        #Test Senaryolarının test edildiği sınıfdır.
│
│
│
target/                                                   # Derlenmiş kod ve derleme çıktıları burada saklanır.
.gitignore                                                # Git tarafından yok sayılacak dosya ve dizinleri belirtir.
pom.xml                                                   # Bağımlılıklar ve derleme ayarları için Maven yapılandırma dosyası.
README.md                                                 # Depoya genel bakış ve talimatlar (Bu dosya)
```

## Test Senaryosu

- Trello üzerinde bir board oluşturunuz.
- Oluşturduğunuz board’a iki tane kart oluşturunuz.
- Oluşturduğunuz bu iki karttan random olacak sekilde bir tanesini güncelleyiniz.
- Oluşturduğunuz kartları siliniz.
- Oluşturduğunuz board’u siliniz.
