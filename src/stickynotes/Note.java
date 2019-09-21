package stickynotes;

public class Note {
    public int id;
    public String note;
    
    public Note(int id, String note) {
        this.id = id;
        this.note = note;
    }
    
    public int getID() {
        return id;
    }
    
    public String getNote() {
        return note;
    }
}
