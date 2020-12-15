package entitys;

public class Stadium implements EntityDB{
    private int id;
    private String city;
    private String country;
    private String stadiumName;
    private int capacity;


    public Stadium(int id, String city, String country, String stadiumName, int capacity) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.stadiumName = stadiumName;
        this.capacity = capacity;
    }
    public Stadium(){}

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Stadium{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", stadiumName='" + stadiumName + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
