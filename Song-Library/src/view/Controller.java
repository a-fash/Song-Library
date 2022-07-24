package view;

//Andrew Fash

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import app.Song;
import app.SongLib;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {
	
	File file = new File(System.getProperty("user.dir") + "/src/songList.txt"); 
	
	@FXML private ListView<String> listView;
	@FXML TextField currentName;
	@FXML TextField currentArtist;
	@FXML TextField currentAlbum;
	@FXML TextField currentYear;
	@FXML Text errorMessage;
	@FXML Button editButton;
	@FXML Button deleteButton;
	@FXML Button addSongButton;
	@FXML Button addButton;
	@FXML Button cancelButton;
	@FXML Button updateButton;
	@FXML Button areYouSureButton;
	
	
	private ObservableList<String> obsList;
	private ArrayList<Song> songLibrary = new ArrayList<Song>();
	private SongLib library = new SongLib();

	public void start(Stage mainStage) throws IOException { 
		
		setTextFieldsEditable(false);
		setVisibleTopButtons(true);	
		setVisibleBottomButtons(false);
		
		library.createLibraryFromFile(file);
		//createLibraryFromFile(file);
		
		updateObservableList();
		
		listView.setItems(obsList); 
		
		// select and show the first item
		listView.getSelectionModel().select(0);
		showItem();

		// set listener for the items
		listView
				.getSelectionModel()
				.selectedIndexProperty()
				.addListener(
						(obs, oldVal, newVal) -> 
						showItem());
	}
	
	public void createLibraryFromFile(File f) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(f)); 
		  
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
			  songLibrary.add(new Song(name, artist, album, year));
		  }
		  
		  br.close();
			
		  updateObservableList();
	}
	
	public void createFileFromLibrary() throws FileNotFoundException {
		
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		
		
		for(int i = 0; i < songLibrary.size(); i++) {
			
			Song s = library.getSong(i);
			//Song s = songLibrary.get(i);
			writer.write(s.getName() + ";" + s.getArtist() + ";" + s.getAlbum() + ";" + String.valueOf(s.getYear()) + "\n");
		}
		
		writer.close();
		
	}
	
	public void updateObservableList() {
		
		obsList = FXCollections.observableArrayList();
		String name, artist;
		
		for(int i = 0; i < library.getSize(); i++) {
			
			name = library.getSong(i).getName();
			artist = library.getSong(i).getArtist();
			obsList.add(name + " - " + artist);
		}
		
		/*
		obsList = FXCollections.observableArrayList();
		String name, artist;
		
		for(int i = 0; i < songLibrary.size(); i++) {
			
			name = songLibrary.get(i).getName();
			artist = songLibrary.get(i).getArtist();
			obsList.add(name + " - " + artist);
		}
		*/
	}
	
	public void showItem() {     
		
		
		
		String item = listView.getSelectionModel().getSelectedItem();
		
		if(item == null) {
			
			currentName.setText("");
			currentArtist.setText("");
			currentAlbum.setText("");
			currentYear.setText("");
			return;
		}
		
		String name = item.substring(0, item.indexOf('-')-1);
		String artist = item.substring(item.indexOf('-')+2);
		
		Song s = library.getSong(name, artist);
		//Song s = getSongFromLibrary(name, artist);
		
		errorMessage.setText("");
		
		currentName.setText(s.getName());
		currentArtist.setText(s.getArtist());
		currentAlbum.setText(s.getAlbum());
		
		if(s.getYear() == 0)
			currentYear.setText("");
		else 
			currentYear.setText(Integer.toString(s.getYear()));
	}
	
	public void setTextFieldsEditable(boolean bts) {
		
		currentName.setEditable(bts);
		currentArtist.setEditable(bts);
		currentAlbum.setEditable(bts);
		currentYear.setEditable(bts);
		
		return;
	}
	
	public void setTextFieldsDisable(boolean blue) {
		
		currentName.setDisable(blue);
		currentArtist.setDisable(blue);
		currentAlbum.setDisable(blue);
		currentYear.setDisable(blue);
		
		return;
	}
	
	public void setVisibleTopButtons(boolean banana){
		
		addSongButton.setVisible(banana);
		editButton.setVisible(banana);
		deleteButton.setVisible(banana);
	}
	
	public void setVisibleBottomButtons(boolean bacon) {
		
		addButton.setVisible(bacon);
		updateButton.setVisible(bacon);
		areYouSureButton.setVisible(bacon);
		cancelButton.setVisible(bacon);
	}
	
	public void clearFields(ActionEvent e) {
		
		setTextFieldsEditable(true);
		
		errorMessage.setText("");
		
		listView.setDisable(true);
		
		currentName.clear();
		currentName.setPromptText("enter song name");
		
		currentArtist.clear();
		currentArtist.setPromptText("enter song artist");
		
		currentAlbum.clear();
		currentAlbum.setPromptText("enter song album");
		
		currentYear.clear();
		currentYear.setPromptText("enter song year");
		
		setVisibleTopButtons(false);
		setVisibleBottomButtons(false);
		
		addButton.setVisible(true);	
		cancelButton.setVisible(true);
	}
	
	public void addSong(ActionEvent e) {
		
		String name, artist, album, year;
		int listIndex, libraryIndex;
		
		name = currentName.getText().trim();
		artist = currentArtist.getText().trim();
		album = currentAlbum.getText().trim();
		year = currentYear.getText().trim();
		
		if(name.equals("") || artist.equals("")) {
			
			errorMessage.setText("Error: every song needs a name and artist :)");
			errorMessage.setFill(Color.RED);
			return;
		}
		
		if((!checkUniqueSong(name, artist) || !checkYear(year))) {
			errorMessage.setText("");
			
			if(!checkUniqueSong(name, artist)) {
				uniqueSongErrorHandler(name, artist);
			}
		
			if(!checkYear(year)) {	
				yearErrorHandler(year);
			}
			return;
		} else {
			
			errorMessage.setText("");
		}
		
		listIndex = addSongToList(name, artist);
		
		if(year.equals("")) {
			libraryIndex = library.addSong(new Song(name, artist, album, 0));
			//libraryIndex = addSongToLibrary(new Song(name, artist, album, 0));
		} else {
			libraryIndex = library.addSong(new Song(name, artist, album, Integer.parseInt(year)));
			//libraryIndex = addSongToLibrary(new Song(name, artist, album, Integer.parseInt(year)));
		}
		
		listView.setDisable(false);
		
		setTextFieldsEditable(false);
		setVisibleTopButtons(true);
		setVisibleBottomButtons(false);
		
		if(listIndex == libraryIndex) {
			listView.getSelectionModel().select(findSongInList(name, artist));
			showItem();
		} else {
			
			System.out.println("Error: song added to list and library at different locations");
		}
	}
	
	public void lockFields(ActionEvent e) {
		
		if(obsList == null || obsList.isEmpty())
			return;
	
		listView.setDisable(true);
		
		setTextFieldsDisable(true);
		setVisibleTopButtons(false);
		setVisibleBottomButtons(false);
		
		areYouSureButton.setVisible(true);
		cancelButton.setVisible(true);
	}
	
	public void delete(ActionEvent e) {

		String item, name, artist;
		
		item = listView.getSelectionModel().getSelectedItem();
		
		if(item == null) {
			
			return;
		}
		name = item.substring(0, item.indexOf('-')-1);
		artist = item.substring(item.indexOf('-')+2);
		
		deleteSongInList(name, artist);
		library.deleteSong(name, artist);
		//deleteSongInLibrary(name, artist);
		
		listView.setItems(obsList);
		
		showItem();
		
		listView.setDisable(false);
		
		setTextFieldsDisable(false);
		setTextFieldsEditable(false);
		
		
		setVisibleTopButtons(true);
		setVisibleBottomButtons(false);
		
		return;
	}

	public void edit(ActionEvent e) {
		
		if(obsList == null || obsList.isEmpty())
			return;
		
		listView.setDisable(true);
		
		setTextFieldsEditable(true);
		setVisibleTopButtons(false);
		setVisibleBottomButtons(false);
		
		updateButton.setVisible(true);
		cancelButton.setVisible(true);
	}
	
	public void update(ActionEvent e) {
		
		String item, name, artist;
		String newName, newArtist, newAlbum, newYear;
		
		item = listView.getSelectionModel().getSelectedItem();
		newName = currentName.getText();
		newArtist = currentArtist.getText();
		newAlbum = currentAlbum.getText();
		newYear = currentYear.getText();
		
		if(item == null) {
			
			return;
		}
		
		name = item.substring(0, item.indexOf('-')-1);
		artist = item.substring(item.indexOf('-')+2);
		
		errorMessage.setText("");
		
		if( !(newName.toLowerCase().equals(name.toLowerCase()) && newArtist.toLowerCase().equals(artist.toLowerCase()))) {
			
			boolean a = uniqueSongErrorHandler(newName, newArtist);
			boolean b = yearErrorHandler(newYear);
			
			if(a || b)
				return;
		} else {
			if(yearErrorHandler(newYear)) {
				return;
			}
		}
		
		editSongInList(
				name,
				artist, 
				newName, 
				newArtist);
		
		if(newYear.equals("")) {
			library.editSong(name, artist, newName, newArtist, newAlbum,0);
			//editSongInLibrary(name, artist, newName, newArtist, newAlbum,0);

		} else {
			library.editSong(name, artist, newName, newArtist, newAlbum, Integer.parseInt(newYear));
			//editSongInLibrary(name, artist, newName, newArtist, newAlbum, Integer.parseInt(newYear));
		}
		
		listView.setItems(obsList);
		listView.getSelectionModel().select(findSongInList(name, artist));
		showItem();
		
		listView.setDisable(false);
		
		setTextFieldsEditable(false);
		setVisibleTopButtons(true);
		setVisibleBottomButtons(false);
		
		return;
	}

	public void cancel(ActionEvent e) {
		
		listView.setDisable(false);
		
		setTextFieldsDisable(false);
		setTextFieldsEditable(false);
		
		errorMessage.setText("");
		
		currentName.setPromptText("");
		currentArtist.setPromptText("");
		currentAlbum.setPromptText("");
		currentYear.setPromptText("");
		
		showItem();
		
		setVisibleTopButtons(true);
		setVisibleBottomButtons(false);
	}
	
	public boolean yearErrorHandler(String year) {
		
		String text = "";
		
		if(!checkYear(year)) {	
			text = "Error: please enter a proper year\nPlease enter a proper year 'YYYY'\n";
			errorMessage.setFill(Color.RED);
			errorMessage.setText(errorMessage.getText() + text);
			return true;
		}
		
		return false;
	}
	
	public boolean uniqueSongErrorHandler(String name, String artist) {
		
		String text = "";
		
		if(!checkUniqueSong(name, artist)) {
			text = "Error: song already exists in library\nPlease enter a unique song name and artist\n";
			errorMessage.setFill(Color.RED);
			errorMessage.setText(errorMessage.getText() + text);
			return true;
		}
		
		/*
		if(!checkUniqueSong(name, artist)) {
			text = "Error: song already exists in library\nPlease enter a unique song name and artist\n";
			errorMessage.setFill(Color.RED);
			errorMessage.setText(errorMessage.getText() + text);
			return true;
		}
		*/
		return false;
	}
	
	public boolean checkUniqueSong(String name, String artist) {
		
		return findSongInList(name, artist) == -1 && findSongInLibrary(name, artist) == -1;
	}
	
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

	public int addSongToList(String name, String artist) {
		
		String s = (name + " - " + artist);
		
		for(int i = 0; i < obsList.size(); i++) {
			
			if(s.toLowerCase().compareTo(obsList.get(i).toLowerCase()) < 0) {
				
				obsList.add(i, s);
				return i;
			}
		}
		
		obsList.add(s);
		
		return obsList.size();
	}
	
	public void editSongInList(String name, String artist, String newName, String newArtist) {
		
		int index = findSongInList(name, artist);
		
		obsList.set(index, newName + " - " + newArtist);
		
		//obsList.remove(index, index);
		//obsList.add(newName + "-" + newArtist);
		
		return;
	}
	
	public void deleteSongInList(String name, String artist) {
		
		int index = findSongInList(name, artist);
		
		obsList.remove(index, index+1);
		
		return;
	}
	
	public int findSongInList(String name, String artist) {
		
		String item, songName, songArtist;
		
		for(int i = 0; i < obsList.size(); i++) {
			
			item = obsList.get(i);
			songName = item.substring(0, item.indexOf('-')-1).toLowerCase();
			songArtist = item.substring(item.indexOf('-')+2).toLowerCase();
			
			if(name.toLowerCase().equals(songName) && artist.toLowerCase().equals(songArtist)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void sortLibrary() {
		
		
	}
}


