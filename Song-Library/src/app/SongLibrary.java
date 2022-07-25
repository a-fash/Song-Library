package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//Andrew Fash


public class SongLibrary {
	
	ArrayList<Song> library;
	File loc;
	//File file = new File(System.getProperty("user.dir") + "/src/songList.txt"); 

	public SongLibrary() {
		
		this.library = new ArrayList<Song>();
	}
	
	public void createLibraryFromFile(File f) throws IOException {
		
		loc = new File(f.toString());
		System.out.println(loc.toString());
		BufferedReader br = new BufferedReader(new FileReader(loc)); 
		  
		String st, name, artist, album;
		int year;
		while ((st = br.readLine()) != null) {
			name = st.substring(0, st.indexOf(";"));
			st = st.substring(st.indexOf(";") + 1);
			artist = st.substring(0, st.indexOf(";"));
			st = st.substring(st.indexOf(";") + 1);
			album = st.substring(0, st.indexOf(";"));
			st = st.substring(st.indexOf(";") + 1);
			year = Integer.parseInt(st.substring(0));
			library.add(new Song(name, artist, album, year));
		}
		  
		br.close();
	}
	
	public void createFileFromLibrary() throws FileNotFoundException {
		
		PrintWriter writer = new PrintWriter(loc);
		writer.print("");
		
		
		for(int i = 0; i < library.size(); i++) {
			
			Song s = library.get(i);
			writer.write(s.getName() + ";" + s.getArtist() + ";" + s.getAlbum() + ";" + String.valueOf(s.getYear()) + "\n");
		}
		
		writer.close();
		
	}

	public int getSize() {
		
		return library.size();
	}
	
	public Song getSong(int x) {
		
		return library.get(x);
	}
	
	public Song getSong(String name, String artist) {
		
		return library.get(findSong(name, artist));
	}
	
	public int addSong(Song s) {

		String identifier = s.primaryKey();
		
		for(int i = 0; i < library.size(); i++) {
					
			if(identifier.compareTo(library.get(i).primaryKey()) < 0) {
				
				library.add(i, s);
				return i;
			}
		}
		
		library.add(s);
		
		return library.size();
	}
	
	public void addSong(String name, String artist, String album, int year) {

		library.add(new Song(name, artist, album, year));
	}
	
	public void editSong(String name, String artist, String newName, String newArtist) {
		
		int index = findSong(name, artist);
		
		library.get(index).setName(newName);
		library.get(index).setArtist(newArtist);
		
		return;
	}
	
	public void editSong(String name, String artist, String newName, String newArtist, String newAlbum, int newYear) {
		
		int index = findSong(name, artist);
		
		library.get(index).setName(newName);
		library.get(index).setArtist(newArtist);
		library.get(index).setAlbum(newAlbum);
		library.get(index).setYear(newYear);
		
		return;
	}
	
	public void deleteSong(Song s) {
		
		int index = findSong(s.name, s.artist);
		
		library.remove(index);
		
		return;
	}
	
	public void deleteSong(String name, String artist) {
		
		int index = findSong(name, artist);
		
		library.remove(index);
		
		return;
	}
	
	//returns index of song in library. returns -1 if song is not in library
	public int findSong(Song s) {
		
		String n, a;
		
		for(int i = 0; i < library.size(); i++) {
			
			n = library.get(i).getName().toLowerCase();
			a = library.get(i).getArtist().toLowerCase();
			
			if(s.getName().toLowerCase().equals(n) && s.getArtist().toLowerCase().equals(a)) {
				return i;
			}
		}
		
		return -1;
	}
	
	//returns index of song in library. returns -1 if song is not in library
	public int findSong(String name, String artist) {
	
		String n, a;
		
		for(int i = 0; i < library.size(); i++) {
			
			n = library.get(i).getName().toLowerCase();
			a = library.get(i).getArtist().toLowerCase();
			
			if(name.toLowerCase().equals(n) && artist.toLowerCase().equals(a)) {
				return i;
			}
		}
		
		return -1;
	}
	
	//checks if name and artist are unique in list
	//returns true if song is unique, else false
	public boolean uniqueSong(String name, String artist) {
		
		System.out.println(findSong(name, artist) == -1);
		return findSong(name, artist) == -1;
	}
	
	//checks if year entered is formated properly
	//returns true if no year is entered or if a 4 digit string is entered
	//returns false if 4 digits are not entered
	public boolean checkYear(String year) {
		
		if(year.equals("")) {
			return true;
		}
		
		if(year.length() != 4) {
			return false;
		}
		
		for(int i = 0; i < year.length(); i++) {
			char c = year.charAt(i);
			if( !(c >= '0' && c <= '9')) {
				return false;
			}
		}
		
		return true;
	}
	
}
