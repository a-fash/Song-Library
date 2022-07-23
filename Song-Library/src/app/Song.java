package app;

//Andrew Fash

public class Song {

	String name;
	String artist;
	String album;
	int year;
	
	public Song(String name, String artist) {
		
		this.name = name;
		this.artist = artist;
	}
	
	public Song(String name, String artist, String album, int year) {
		
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String primaryKey() {
		
		return (this.name + this.artist).toLowerCase();
	}

	public String toString() {
		
		return this.name + " " +this.artist;
	}
	
}
