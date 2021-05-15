package servicesNew;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
	String Caption;
	Author writer;
		
	@JsonCreator
	public Book(@JsonProperty("caption") String caption, @JsonProperty("writer") Author writer) {
		this.Caption = caption;
		this.writer = writer;
	}
	
	public String getCaption() {
		return Caption;
	}
	
	public void setCaption(String Caption) {
		this.Caption = Caption;
	}
	
	public Author getWriter() {
		return writer;
	}
	
	public void setWriter(Author writer) {
		this.writer = writer;
	}
}
