package BaiTap1.Bai8;

public class Country {
    private String code;
    private String name;
    private String continent;
    double surfaceArea;
    int population;
    double gnp;
    int capital;
    int totalCity;

    public Country(String code, String name, String continent, double surfaceArea, int population, double gnp, int capital) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.surfaceArea = surfaceArea;
        this.population = population;
        this.gnp = gnp;
        this.capital = capital;
    }

    public Country() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getGnp() {
        return gnp;
    }

    public void setGnp(double gnp) {
        this.gnp = gnp;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public int getTotalCity() {
        return totalCity;
    }

    public void setTotalCity(int totalCity) {
        this.totalCity = totalCity;
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                ", surfaceArea=" + surfaceArea +
                ", population=" + population +
                ", gnp=" + gnp +
                ", capital=" + capital +
                '}';
    }

    public static Country convertStringToObject(String str) {
        str = str.substring(8, str.length() - 1);
        String s[] = str.split(", ");
        return new Country(s[0].substring(5), s[1].substring(5), s[2].substring(10), Double.parseDouble(s[3].substring(12)),
                Integer.parseInt(s[4].substring(11)), Double.parseDouble(s[5].substring(4)), Integer.parseInt(s[6].substring(8)));
    }
}
