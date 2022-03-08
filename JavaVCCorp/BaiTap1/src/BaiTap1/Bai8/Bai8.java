package BaiTap1.Bai8;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Bai8 {
    public static Cache cache;

    public Bai8() {
        cache = new Cache();
    }

    // Tìm thành phố đông dân nhất của từng quốc gia
    public static Map<Country, City> findCityMostPopulationOfCountry() {
        Map<Country, City> countryCityMap = new HashMap<>();
        Map<Country, List<City>> sortCitiesPopulationASC = Cache.sortCitiesPopulationDSC();
        sortCitiesPopulationASC.keySet().stream().forEach(country -> {
            if (sortCitiesPopulationASC.get(country).size() != 0) {
                countryCityMap.put(country, sortCitiesPopulationASC.get(country).get(0));
            } else {
                countryCityMap.put(country, null);
            }
        });
        return countryCityMap;
    }

    public static Map<String, City> findCityMostPopulationOfContinent() {
        Map<String, City> map = new HashMap<>();
        Cache.continents.stream().forEach(s -> {
            List<City> cities = new ArrayList<>();
            findCityMostPopulationOfCountry().keySet().stream().forEach(country -> {
                if (country.getContinent().equals(s)) {
                    if (findCityMostPopulationOfCountry().get(country) != null) {
                        cities.add(findCityMostPopulationOfCountry().get(country));
                    }
                }
            });

            Collections.sort(cities, (o1, o2) -> {
                return o2.getPopulation() - o1.getPopulation();
            });

            if (cities.size() != 0) {
                map.put(s, cities.get(0));
            } else {
                map.put(s, null);
            }
        });
        return map;
    }

    // Tìm thủ đô đông dân nhất
    public static City findCapitalMostPopulation() {
        City[] maxPopulationCity = new City[1];
        maxPopulationCity[0] = new City();
        maxPopulationCity[0].setPopulation(-1);
        Cache.capitals.keySet().stream().forEach(country -> {
            if (Cache.capitals.get(country) != null) {
                if (maxPopulationCity[0].getPopulation() < Cache.capitals.get(country).getPopulation()) {
                    maxPopulationCity[0] = Cache.capitals.get(country);
                }
            }
        });
        return maxPopulationCity[0];
    }

    public static Map<String, City> findCapitalMostPopulationOfContinent() {
        Map<String, City> map = new HashMap<>();
        Cache.continents.stream().forEach(s -> {
            List<City> cities = new ArrayList<>();
            Cache.capitals.keySet().stream().forEach(country -> {
                if (country.getContinent().equals(s)) {
                    if (Cache.capitals.get(country) != null) {
                        cities.add(Cache.capitals.get(country));
                    }
                }
            });

            Collections.sort(cities, (o1, o2) -> o2.getPopulation() - o1.getPopulation());

            if (cities.size() != 0) {
                map.put(s, cities.get(0));
            } else {
                map.put(s, null);
            }
        });
        return map;
    }

    public static List<Country> sortCountriesDSCTotalCities() {
        return Cache.countryCityMap.keySet().stream().map(country -> {
            country.setTotalCity(Cache.countryCityMap.get(country).size());
            return country;
        }).sorted((o1, o2) -> o2.getTotalCity() - o1.getTotalCity())
                .collect(Collectors.toList());
    }

    public static List<Country> sortCountriesDSCPopulation() {
        return Cache.countries.stream().filter(country -> {
            return country.getPopulation() > 0;
        }).sorted((o1, o2) -> o2.getPopulation() - o1.getPopulation())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        Bai8 bai8 = new Bai8();
        System.out.println("Find most population city in country : ");
        Map<Country, City> countryCityMap = findCityMostPopulationOfCountry();
        for (Country c : countryCityMap.keySet()) {
            if (countryCityMap.get(c) != null) {
                System.out.println(c.getName() + " has most population city : " + countryCityMap.get(c).getName());
            } else {
                System.out.println(c.getName() + " has no city");
            }
        }

        System.out.println("-----------------------------------");

        System.out.println("Find most population city in continent : ");
        Map<String, City> continentCityMap = findCityMostPopulationOfContinent();
        for (String s : continentCityMap.keySet()) {
            if (continentCityMap.get(s) != null) {
                System.out.println(s + " has most population city : " + continentCityMap.get(s).getName());
            } else {
                System.out.println(s + " has no city");
            }
        }

        System.out.println("-----------------------------------");

        System.out.print("Most population capital : ");
        System.out.println(findCapitalMostPopulation().getName());

        System.out.println("-----------------------------------");

        System.out.println("Find most population capital in continent : ");
        Map<String, City> capitalsMap = findCapitalMostPopulationOfContinent();
        for (String s : continentCityMap.keySet()) {
            if (capitalsMap.get(s) != null) {
                System.out.println(s + " has most population capital : " + capitalsMap.get(s).getName());
            } else {
                System.out.println(s + " has no capital");
            }
        }

        System.out.println("-----------------------------------");

        System.out.println("Sort country DSC total city  : ");
        for (Country s : sortCountriesDSCTotalCities()) {
            System.out.println(s + " has city : " + s.getTotalCity());
        }

        System.out.println("-----------------------------------");

        System.out.println("Sort country DSC total population  : ");
        for (Country s : sortCountriesDSCPopulation()) {
            System.out.println(s + " has population : " + s.getPopulation());
        }
    }
}
