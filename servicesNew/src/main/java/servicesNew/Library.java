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

		Author w = new Author("���", "�������", "����������");
		Book b = new Book();
		b.setCaption("����� � ���");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("����� ����");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("���� ��������");
		b.setWriter(w);
		books.add(b);
		writers.add(w);

		w = new Author("Ը���", "�����������", "���������");
		b = new Book();
		b.setCaption("�����");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("������ ����������");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("����");
		b.setWriter(w);
		books.add(b);
		writers.add(w);

		w = new Author("������", "�������", "");
		b = new Book();
		b.setCaption("������� ��������");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("���� �������");
		b.setWriter(w);
		books.add(b);
		b = new Book();
		b.setCaption("�� ���");
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
