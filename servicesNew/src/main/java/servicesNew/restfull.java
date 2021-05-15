package servicesNew;

import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
		
		TrustManager[] noopTrustManager = new TrustManager[]{new X509ExtendedTrustManager () {
	         public void checkClientTrusted (X509Certificate [] chain, String authType, Socket socket) {

	         }
	         public void checkServerTrusted (X509Certificate [] chain, String authType, Socket socket) {

	         }
	         public void checkClientTrusted (X509Certificate [] chain, String authType, SSLEngine engine) {

	         }
	         public void checkServerTrusted (X509Certificate [] chain, String authType, SSLEngine engine) {

	         }
	         public java.security.cert.X509Certificate [] getAcceptedIssuers () {
	            return null;
	         }
	         public void checkClientTrusted (X509Certificate [] certs, String authType) {
	         }
	         public void checkServerTrusted (X509Certificate [] certs, String authType) {
	         }

	      }};
    	//определяем SSL контекст
		
		SSLContext sc;
		try {
			sc = SSLContext.getInstance("ssl");
			sc.init(null, noopTrustManager, null);
			
			String txt = ClientBuilder.newBuilder()
					//регистрируем SSL контекст
					.sslContext(sc)
					.hostnameVerifier(new HostnameVerifier() {
						
						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							System.out.println(arg0);
							return true;
						}
					})
					.register(JacksonJsonProvider.class)
					.build()
					.target("https://localhost")
					.path("NotificationDesk/Notes")
					.request("application/json")
					.header("Content-Type","application/json")
					.post(Entity.entity(n, MediaType.APPLICATION_JSON), String.class);
					//.get(String.class);
			System.out.print(txt);
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(
    		value = "/",
    		method = RequestMethod.GET)
    public Library AsIs() {
		PostNote("Получить всю библиатеку.");
		lib.refresh();
        return lib;
    }

	@RequestMapping(
    		value = "/Books",
    		method = RequestMethod.GET)
    public List<Book> getBooks(@RequestParam(name="search", required = false) String search) {
		lib.refresh();
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
		lib.refresh();
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
		lib.refresh();
		if (search!=null) {
			PostNote("Найти автора: "+search);
			List<Author> findedAuthors;
			findedAuthors = new ArrayList<Author>();
			for (Author a: lib.authors)
				if (a.getName().toLowerCase().contains(search.toLowerCase())
				  ||a.getLastName().toLowerCase().contains(search.toLowerCase())
		          ||a.getPatronym().toLowerCase().contains(search.toLowerCase()))
					findedAuthors.add(a);
			return findedAuthors;
		} else {
			PostNote("Получить всех авторов.");
			return lib.getAuthors();
		}
    }
	
	@RequestMapping(value = "/Authors/{LastName}",
    		method = RequestMethod.GET)
    public Antology getAntology(@PathVariable("LastName") String LastName) {
		lib.refresh();
		PostNote("Получить все книги автора: "+LastName);
    	for (Author a: lib.authors)
    		if (a.getLastName().equalsIgnoreCase(LastName))
    	        return new Antology(a, lib.books);
    	return new Antology(new Author(null,LastName,null), lib.books);
    }

    @RequestMapping(
    		value = "/Authors",
    		method = RequestMethod.POST)
    public boolean getAuthors(@RequestBody() Author newAuthor) {
    	return lib.AddAuthor(newAuthor.name, newAuthor.lastName, newAuthor.patronym);
    }

    @RequestMapping(value = "/Books",
    		method = RequestMethod.POST)
    public String getBook(@RequestBody() Book b) {
    	return lib.AddBook(b.getWriter().name, b.getWriter().lastName, b.getWriter().patronym, b.getCaption());
    }
    
    @RequestMapping(
    		value = "/AuthorsAdd",
    		method = RequestMethod.GET)
    public String getAuthors(@RequestParam(name="n", required = true) String name,
    		@RequestParam(name="ln", required = true) String lastname,
    		@RequestParam(name="pt", required = true) String patronym) {
    	return String.valueOf(lib.AddAuthor(name, lastname, patronym));
    }
}
