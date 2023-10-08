package neu.zhuoxi.myServer;

public class Album {

    private String artist;
    private String title;
    private String year;
    private String albumID; // This field corresponds to the albumID in imageMetaData

    public Album() {

    }
    public Album(String artist,String title, String year){
        this.year = year;
        this.artist = artist;
        this.title = title;
    }

    // Getters and setters for the fields

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    // You can add any additional methods and logic here as needed
}
