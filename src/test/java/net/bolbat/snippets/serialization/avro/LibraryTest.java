package net.bolbat.snippets.serialization.avro;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import net.bolbat.snippets.serialization.avro.library.Author;
import net.bolbat.snippets.serialization.avro.library.Book;
import net.bolbat.snippets.serialization.avro.library.Category;

public class LibraryTest {

	@Test
	public void exampleUsage() throws IOException {
		final Author author = Author.newBuilder()
				.setId(randomId())
				.setName("Yuval Noah Harari")
				.build();
		final Category category = Category.newBuilder()
				.setId(randomId())
				.setName("Anthropology")
				.build();
		final Book book = Book.newBuilder()
				.setId(randomId())
				.setName("Sapiens: A Brief History of Humankind")
				.setAuthors(Collections.singletonList(author.getId()))
				.setCategories(Collections.singletonList(category.getId()))
				.build();

		final byte[] serializedBook = book.toByteBuffer().array();
		final Book restoredBook = Book.fromByteBuffer(ByteBuffer.wrap(serializedBook));

		assertThat(restoredBook, equalTo(book));
		assertThat(restoredBook.getAuthors().size(), equalTo(1));
		assertThat(restoredBook.getAuthors().get(0).toString(), equalTo(author.getId())); // author value in restored list is instance of Utf8.class
		assertThat(restoredBook.getCategories().size(), equalTo(1));
		assertThat(restoredBook.getCategories().get(0).toString(), equalTo(category.getId())); // category value in restored list is instance of Utf8.class
	}

	private String randomId() {
		return UUID.randomUUID().toString();
	}

}
