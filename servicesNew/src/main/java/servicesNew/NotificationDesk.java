package servicesNew;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping (path = "/NotificationDesk")
public class NotificationDesk {
	List<Note> Notes;
	
	NotificationDesk() {
		this.Notes = new ArrayList<Note>();
	}
	
	@RequestMapping (path = "/Notes", method = RequestMethod.GET)
	public List<Note> getNotes() {
		return this.Notes;
	}
	
	@RequestMapping (path = "/Notes", method = RequestMethod.POST)
	public String addNote(@RequestBody Note note) {
		Notes.add(note);
		return String.format("Сообщение \"%s\" добавлено в %s", note.message, note.time.toString());
	}
	
	@RequestMapping (path = "/Notes", method = RequestMethod.DELETE)
	public String deleteNote(@RequestBody Note note) {
		for (Note n: Notes)
			if (n.message.equalsIgnoreCase(note.message))
				if (Notes.remove(n))
					return String.format("Сообщение \"%s\" удалено.", note.message);
		return String.format("Сообщение \"%s\" не найдено.", note.message);
	}
	
	@RequestMapping (path = "/Notes/Flush", method = RequestMethod.GET)
	public String clearNotes() {
		Notes.clear();
		return String.format("Очистка...");
	}
}
