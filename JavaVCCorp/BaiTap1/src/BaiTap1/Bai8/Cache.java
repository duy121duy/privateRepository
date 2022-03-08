package BaiTap1.Bai8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class Cache {
    public static List<City> cities;
    public static List<Country> countries;
    public static Map<Country, List<City>> countryCityMap;
    public static Set<String> continents;
    public static Map<Country, City> capitals;

    public Cache() {
        try {
            cities = convertStringToCity(readFileText("BaiTapPhan1/input_8/cities.dat"));
            countries = convertStringToCountry(readFileText("BaiTapPhan1/input_8/countries.dat"));
            countryCityMap = new HashMap<>();
            continents = new HashSet<>();
            capitals = new HashMap<>();
            getContinents();
            getCitesInCountry();
            getCapitalOfCountry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFileText(String fileName) throws IOException {
        BufferedReader bufferedReader = null;
        List<String> strings = new ArrayList<>();
        Reader reader = new FileReader(fileName);
        bufferedReader = new BufferedReader(reader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            strings.add(str);
        }

        if (reader != null) {
            reader.close();
        }
        return strings;
    }

    public static List<City> convertStringToCity(List<String> strings) throws IOException {
        List<City> cities = new ArrayList<>();
        for (String s : readFileText("BaiTapPhan1/input_8/cities.dat")) {
            cities.add(City.convertStringToObject(s));
        }
        return cities;
    }

    public static List<Country> convertStringToCountry(List<String> strings) throws IOException {
        List<Country> countries = new ArrayList<>();
        for (String s : readFileText("BaiTapPhan1/input_8/countries.dat")) {
            countries.add(Country.convertStringToObject(s));
        }
        return countries;
    }

    public static void getCitesInCountry() {
        countries.stream().forEach(country -> {
            List<City> cities1 = new ArrayList<>();
            cities.stream().forEach(city -> {
                if (country.getCode().equals(city.getCountryCode())) {
                    cities1.add(city);
                }
            });
            countryCityMap.put(country, cities1);
        });
    }

    public static void getContinents() {
        continents = countries.stream().map(country -> {
            return country.getContinent();
        }).collect(Collectors.toSet());
    }

    public static Map<Country, List<City>> sortCitiesPopulationDSC() {
        Map<Country, List<City>> sortedCountryCityMap = new HashMap<>();
        countryCityMap.keySet().stream().forEach(country -> {
            List<City> cities = countryCityMap.get(country);
            Collections.sort(cities, (o1, o2) -> o2.getPopulation() - o1.getPopulation());
            sortedCountryCityMap.put(country, cities);
        });
        return sortedCountryCityMap;
    }

    public static void getCapitalOfCountry() {
        Map<Country, List<City>> cities1 = countryCityMap;
        cities1.keySet().stream().forEach(country -> {
            if (cities1.get(country).size() != 0) {
                cities1.get(country).stream().forEach(city -> {
                    if (country.getCapital() == city.getId()) {
                        capitals.put(country, city);
                    }
                });
            } else {
                capitals.put(country, null);
            }
        });
    }
}
