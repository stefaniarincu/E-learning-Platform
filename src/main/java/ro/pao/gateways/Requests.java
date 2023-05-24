package ro.pao.gateways;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.pao.application.csv.CsvWriter;
import ro.pao.mapper.EssentialBookMapper;
import ro.pao.model.EssentialBook;
import ro.pao.service.impl.LogServiceImpl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Requests {

    private static HttpClient httpClient = HttpClient.newHttpClient();

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static EssentialBookMapper essentialBookMapper = new EssentialBookMapper();


    public void saveRequestInfo() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://www.googleapis.com/books/v1/volumes?q=essential+university+science+books&fields=items(volumeInfo/title,volumeInfo/authors,volumeInfo/publisher,volumeInfo/publishedDate)"))
                    .GET()
                    .build();

            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<EssentialBook> essentialBooks = essentialBookMapper.essentialBookListMapper(objectMapper, httpResponse.body());
            writeBooksToCSV(essentialBooks);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeBooksToCSV(List<EssentialBook> books) {
        List<String[]> stringList = new ArrayList<>();
        stringList.add(new String[] {"Title" , "Authors", "Publisher", "Published Date"});

        String title = "";
        String authors = "";
        String publisher = "";
        String publishedDate = "";

        for (EssentialBook book : books) {
            if (book.getTitle() != null) {
                title = book.getTitle().replaceAll(",", ";");
            }

            if (book.getAuthors() != null) {
                authors = String.join(", ", book.getAuthors()).replaceAll(",", ";");
            }

            if (book.getPublisher() != null) {
                publisher = book.getPublisher().replaceAll(",", ";");
            }

            if (book.getPublishedDate() != null) {
                publishedDate = book.getPublishedDate().replaceAll(",", ";");
            }

            stringList.add(new String[] {title, authors, publisher, publishedDate});
        }

        try {
            CsvWriter.getInstance().writeLineByLine(stringList, Path.of("essential_books.csv"));
        } catch (Exception e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }
}