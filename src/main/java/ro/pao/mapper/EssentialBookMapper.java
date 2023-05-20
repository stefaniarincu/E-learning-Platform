package ro.pao.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.pao.model.EssentialBook;

import java.util.ArrayList;
import java.util.List;

public class EssentialBookMapper {
    public List<EssentialBook> essentialBookListMapper(ObjectMapper objectMapper, String body) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(body);
        List<EssentialBook> essentialBooks = new ArrayList<>();

        JsonNode itemsNode = jsonNode.get("items");
        if (itemsNode != null && itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                EssentialBook essentialBook = new EssentialBook();

                JsonNode volumeInfoNode = itemNode.get("volumeInfo");
                if (volumeInfoNode != null) {
                    JsonNode titleNode = volumeInfoNode.get("title");
                    if (titleNode != null && titleNode.isTextual()) {
                        essentialBook.setTitle(titleNode.asText());
                    }

                    JsonNode authorsNode = volumeInfoNode.get("authors");
                    if (authorsNode != null && authorsNode.isArray()) {
                        List<String> authors = new ArrayList<>();
                        for (JsonNode authorNode : authorsNode) {
                            if (authorNode.isTextual()) {
                                authors.add(authorNode.asText());
                            }
                        }
                        essentialBook.setAuthors(authors);
                    }

                    JsonNode publisherNode = volumeInfoNode.get("publisher");
                    if (publisherNode != null && publisherNode.isTextual()) {
                        essentialBook.setPublisher(publisherNode.asText());
                    }

                    JsonNode publishedDateNode = volumeInfoNode.get("publishedDate");
                    if (publishedDateNode != null && publishedDateNode.isTextual()) {
                        essentialBook.setPublishedDate(publishedDateNode.asText());
                    }

                    essentialBooks.add(essentialBook);
                }
            }
        }

        return essentialBooks;
    }
}
