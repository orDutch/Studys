package servicesNew;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@RestController

@RequestMapping("/Library")
public class restfull {
	@Autowired
	private Library lib;

	@Autowired
	public restfull(Library lib) {
	    this.lib = lib;
	}
	
	void PostNote(String message) {
		Note n = new Note();
		n.setMessage(message);
		n.setTime(new Date());
		String txt = ClientBuilder.newBuilder()
				.register(JacksonJsonProvider.class)
				.build()
				.target("http://localhost:8888")
				.path("NotificationDesk/Notes")
				.request("application/json")
				.header("Content-Type","application/json")
				.post(Entity.entity(n, MediaType.APPLICATION_JSON), String.class);
		System.out.print(txt);
	}
	
	@RequestMapping(
    		value = "/",
    		method = RequestMethod.GET)
    public Library AsIs() {
		PostNote("Получить всю библиатеку.");
        return lib;
    }

	@RequestMapping(
    		value = "/Books",
    		method = RequestMethod.GET)
    public List<Book> getBooks(@RequestParam(name="search", required = false) String search) {
		if (search != null) {
			PostNote("Найти книгу: "+search);
			List<Book> findedBooks;
			findedBooks = new ArrayList<Book>();
			for (Book b: lib.books) {
				if (b.getCaption().toLowerCase().contains(search.toLowerCase()))
					findedBooks.add(b);
			}
			return findedBooks;
		}
		else
		{
			PostNote("Получить все книги.");
			return lib.books;
		}
    }
	
	@RequestMapping(
    		value = "/Books/{caption}",
    		method = RequestMethod.GET)
    public Book getBook(@PathVariable("caption") String caption) {
		PostNote("Получить книгу: " + caption);
		for (Book b: lib.books)
			if (b.getCaption().equalsIgnoreCase(caption))
				return b;
		return null;
    }
	
	@RequestMapping(
    		value = "/Authors",
    		method = RequestMethod.GET)
    public List<Author> getWriters(@RequestParam(name="search", required = false) String search) {
		if (search!=null) {
			PostNote("Найти автора: "+search);
			List<Author> findedAuthors;
			findedAuthors = new ArrayList<Author>();
			for (Author a: lib.writers)
				if (a.getName().toLowerCase().contains(search.toLowerCase())
				  ||a.getLastName().toLowerCase().contains(search.toLowerCase())
		          ||a.getPatronym().toLowerCase().contains(search.toLowerCase()))
					findedAuthors.add(a);
			return findedAuthors;
		} else {
			PostNote("Получить всех авторов.");
			return lib.getWriters();
		}
    }
	
	@RequestMapping(value = "/Authors/{LastName}",
    		method = RequestMethod.GET)
    public Antology getAntology(@PathVariable("LastName") String LastName) {
		PostNote("Получить все книги автора: "+LastName);
    	for (Author a: lib.writers)
    		if (a.getLastName().equalsIgnoreCase(LastName))
    	        return new Antology(a, lib.books);
    	return new Antology(new Author(null,LastName,null), lib.books);
    }
	
	
}
