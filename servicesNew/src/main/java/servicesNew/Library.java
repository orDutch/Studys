package servicesNew;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class Library {
	List<Book> books;
	List<Author> writers;
	
	Library () {
		this.writers = new ArrayList<Author>();
		this.books = new ArrayList<Book>();

		Author w = new Author("Лев", "Толстой", "Николаевич");
		Book b = new Book();
		b.setCaption("Война и мир");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("Живой труп");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("Анна Каренина");
		b.setWriter(w);
		books.add(b);
		writers.add(w);

		w = new Author("Фёдор", "Достоевский", "Михалович");
		b = new Book();
		b.setCaption("Идиот");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("Братья Карамазовы");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("Бесы");
		b.setWriter(w);
		books.add(b);
		writers.add(w);

		w = new Author("Максим", "Горький", "");
		b = new Book();
		b.setCaption("Старуха Изергиль");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("Фома Гордеев");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("На дне");
		b.setWriter(w);
		books.add(b);
		writers.add(w);
	}
	
	public List<Author> getWriters() {
		return writers; 
	}
	
	public List<Book> getBooks() {
		return books; 
	}

}
