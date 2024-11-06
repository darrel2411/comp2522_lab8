import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final int SPECIFIC_LENGTH_BEFORE = 4;
    private static final int BEGINNING = 1;
    private static final String ENDING_LAND = "land";
    private static final String CONTAIN_UNITED = "United";

    public static void main(String[] args) throws FileNotFoundException {
        final File file;
        final Scanner scan;
        final Scanner scanInput;
        final Path allCountriesPath;
        final Path matchesDirPath;
        final Path outputDataFile;
        List<String> countries; // need to be process later on

        allCountriesPath = Paths.get("week8countries.txt");
        matchesDirPath = Paths.get("matches");


        if(Files.exists(matchesDirPath)) {
            System.out.println("Directory exists");
        } else {
            try{
                Files.createDirectory(matchesDirPath);
                System.out.println("Created directory: " + matchesDirPath);
            }
            catch(final IOException e) {
                e.printStackTrace();
            }
        }

        outputDataFile = matchesDirPath.resolve("data.txt");

        if(Files.exists(outputDataFile)) {
            System.out.println("File exists");
        } else {
            try{
                Files.createFile(outputDataFile);
                System.out.println("Created file: " + outputDataFile);
            } catch(final IOException e) {
                e.printStackTrace();
            }
        }

        countries = new ArrayList<>();
        try{
            countries = Files.readAllLines(allCountriesPath);
        } catch(final IOException e) {
            e.printStackTrace();
        }


        try{

            Files.writeString(outputDataFile,
                            "=======================" +
                            System.lineSeparator(),
                    StandardOpenOption.APPEND
            );

            // 1. Long Country Names: Write "Country names longer than 10 characters:" followed by all
            // country names with more than 10 characters (always one country per line).
            Files.writeString(outputDataFile,
                    "1. Country names longer than 10 characters: " + System.lineSeparator(),
                    StandardOpenOption.APPEND);

            final List<String> countryLongerThanTenChars;
            countryLongerThanTenChars = filteredStream(countries)
                    .filter(c-> c.length() > 10)
                    .toList();

            // writing data to directory matches/data.txt
            countryLongerThanTenChars.forEach(c-> {
                try {
                    Files.writeString(outputDataFile,
                            c +
                            System.lineSeparator(),
                            StandardOpenOption.APPEND);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator()
                            + "======================="
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            /*
             * 2. Short Country names:  Write "Country names shorter than 5 characters:"
             * followed by all country names with fewer than 5 characters
             */
            // we can't make optional .toList() ???
//            final Optional<String> shortNamesCountries;
//            shortNamesCountries = filteredStream(countries)
//                    .filter(c->c.length() < 5)
//                    .toList();
            Files.writeString(outputDataFile,
                    "2. Short country names (shorter than 5 characters): " +
                            System.lineSeparator(),
                    StandardOpenOption.APPEND);

            final List<String> shortNameCountries;

            shortNameCountries = filteredStream(countries)
                    .filter(c->c.length() < 5)
                    .toList();

            shortNameCountries.forEach(c-> {
                try{
                    Files.writeString(outputDataFile
                            , c + System.lineSeparator()
                            , StandardOpenOption.APPEND);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator()
                            + "======================="
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            /*
             * 3. Countries starting with letter "A"
             */

            Files.writeString(outputDataFile,
                    "3. Countries starting with letter \"A\"" +
                        System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            final List<String> countryNamesStartWithA;
            countryNamesStartWithA = filteredStream(countries)
                    .filter(c->c.substring(0,1).equalsIgnoreCase("A"))
                    .toList();
            countryNamesStartWithA.forEach(c-> {
                try{
                    Files.writeString(outputDataFile
                            , c + System.lineSeparator()
                            , StandardOpenOption.APPEND);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator()
                            + "======================="
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            /*
             * 4. all countries ending with "land":
             */
            Files.writeString(outputDataFile,
                    "4. Country ending with 'land': " +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND);

            final List<String> countriesWithLand;

            countriesWithLand = filteredStream(countries)
                    .filter(c-> c.endsWith(ENDING_LAND))
                    .toList();

            countriesWithLand.forEach(c-> {
                try{
                    Files.writeString(outputDataFile
                            , c + System.lineSeparator()
                            , StandardOpenOption.APPEND);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator()
                            + "======================="
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            /*
             * 5. Containing "United": all countries containing the word "United"
             */
            Files.writeString(outputDataFile,
                    "5. Country containing 'United': " +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND);

            final List<String> countriesContainingUnited;

            countriesContainingUnited = filteredStream(countries)
                    .filter(c-> c.contains(CONTAIN_UNITED))
                    .toList();

            countriesContainingUnited.forEach(c-> {
                try{
                    Files.writeString(outputDataFile
                            , c + System.lineSeparator()
                            , StandardOpenOption.APPEND);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator()
                            + "======================="
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            /*
             * 6. Sorted Names (Ascending): alphabetical order.
             */
            Files.writeString(outputDataFile,
                    "6. Country ASCENDING order: " + System.lineSeparator(),
                    StandardOpenOption.APPEND);

            final List<String> countriesAscending;

            countriesAscending = filteredStream(countries)
                    .sorted()
                    .toList();

            countriesAscending.forEach(c-> {
                try{
                    Files.writeString(outputDataFile
                            , c + System.lineSeparator()
                            , StandardOpenOption.APPEND);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator()
                            + "======================="
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            /*
             * 7. sorted contry names DESCENDING: Reverse alphabetical
             */
            Files.writeString(outputDataFile,
                    "7. Country DESCENDING ORDER: " + System.lineSeparator(),
                    StandardOpenOption.APPEND);

            final List<String> countriesDescending;

            countriesDescending = filteredStream(countries)
                    .sorted(Comparator.reverseOrder())
                    .toList();

            countriesDescending.forEach(c-> {
                try{
                    Files.writeString(outputDataFile
                            , c + System.lineSeparator()
                            , StandardOpenOption.APPEND);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator()
                            + "======================="
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            /*
             * 8. Unique first letters: list the unique first letters of all country names
             */
            Files.writeString(outputDataFile,
                    "8. Country by Unique first letters: " +
                            System.lineSeparator(),
                    StandardOpenOption.APPEND);

            final Map<Character, List<String>> countriesByUniqueFirstLetter;

            countriesByUniqueFirstLetter = filteredStream(countries)
                    .collect(Collectors.groupingBy(c->c.toUpperCase().charAt(0)));

            countriesByUniqueFirstLetter.forEach((k, v) -> {
                try {
                    String upperCaseCountries = v.stream()
                            .collect(Collectors.joining(System.lineSeparator())); // joining each value with line separator

                    // Write the result to the output file with the key (initial letter) as a header
                    Files.writeString(outputDataFile,
                            k + System.lineSeparator() + upperCaseCountries + System.lineSeparator(),
                            StandardOpenOption.APPEND);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator()
                            + "======================="
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);

            // 9 Count of Countries: Write the total count of country names.

            Files.writeString(outputDataFile,
                    "9. Count of Countries:"
                            + System.lineSeparator()
                    ,StandardOpenOption.APPEND);
            final int countCountries;

            countCountries = countries.size();

            try{
                // Writing the count to the file
                Files.writeString(outputDataFile,
                        countCountries + " countries" +
                        System.lineSeparator(),
                        StandardOpenOption.APPEND);

            } catch (final IOException e){
                e.printStackTrace();
            }

            Files.writeString(outputDataFile,
                    System.lineSeparator() +
                    "=======================" +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND
                    );

            // 10. Longest Country Name: Write the longest country name.
            final Optional<String> longestCountryName;

            longestCountryName = filteredStream(countries).
                    max(Comparator.comparingInt(String::length));

            if(longestCountryName.isPresent()){
                try{
                    Files.writeString(outputDataFile,
                            "10. Country with the longest Name" +
                                    longestCountryName.get() +
                                    System.lineSeparator(),
                            StandardOpenOption.APPEND);
                } catch (final IOException e){
                    e.printStackTrace();
                }
            }

            Files.writeString(outputDataFile,
                    System.lineSeparator() +
                        "=======================" +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND
                    );

            // 11. Shortest Country Name: Write the shortest country name.
            final Optional<String> shortestCountryName;

            shortestCountryName = filteredStream(countries).
                    min(Comparator.comparingInt(String::length));

            if(shortestCountryName.isPresent()){
                try {
                    Files.writeString(outputDataFile,
                            "11. Shortest country name" +
                            shortestCountryName.get() +
                            System.lineSeparator(),
                            StandardOpenOption.APPEND);
                } catch (final IOException e){
                    e.printStackTrace();
                }
            }

            Files.writeString(outputDataFile,
                    System.lineSeparator() +
                        "=======================" +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND
                    );

            // 12. Names in Uppercase: Write all country names converted to uppercase.
            Files.writeString(outputDataFile,
                    "12. Names in uppercase" +
                    System.lineSeparator(),
                    StandardOpenOption.APPEND);

            // Writting each country to UpperCase
            filteredStream(countries).forEach(country -> {
                        try{
                            Files.writeString(outputDataFile,
                                    country.toUpperCase() +
                                    System.lineSeparator(),
                                    StandardOpenOption.APPEND);

                        } catch (final IOException e){
                            e.printStackTrace();
                        }
                });

            Files.writeString(outputDataFile,
                    System.lineSeparator() +
                        "=======================" +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND
            );

            // 13 Countries with More Than One Word: List all country names with more than one word.
            final List<String> twoWordCountries;

            twoWordCountries = countries.stream().
                                filter(country -> country.contains(" ")).
                                toList();

            Files.writeString(outputDataFile,
                                "13. Countries with More Than One Word:" +
                                    System.lineSeparator(),
                            StandardOpenOption.APPEND
                    );

            twoWordCountries.forEach(country -> {
                try{
                    Files.writeString(outputDataFile,
                            country +
                            System.lineSeparator(),
                            StandardOpenOption.APPEND);

                } catch (final IOException e) {
                    e.printStackTrace();
                }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator() +
                        "=======================" +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND
                    );

            // 14. Country Names to Character Count: Map each country name to its character count,
            // writing each name and count as "Country: X characters".

            Files.writeString(outputDataFile,
                    "14. Country Names to Character Count:" +
                    System.lineSeparator(),
                    StandardOpenOption.APPEND);

            filteredStream(countries).forEach(country -> {
                    try{
                        Files.writeString(outputDataFile,
                                country + ": " + country.length() +
                                System.lineSeparator(),
                                StandardOpenOption.APPEND);
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
            });

            Files.writeString(outputDataFile,
                    System.lineSeparator() +
                        "=======================" +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND
                    );

            // 15.  Any Name Starts with "Z": Write "true" if any country name starts with "Z"; otherwise,
            //      "false".

            Files.writeString(outputDataFile,
                    "15. Country name that starts with \"Z\"" +
                    System.lineSeparator(),
                    StandardOpenOption.APPEND);

            filteredStream(countries).
                    filter(country -> country.startsWith("Z")).
                    forEach( country -> {
                        try {
                            Files.writeString(outputDataFile,
                                    country +
                                            System.lineSeparator(),
                                    StandardOpenOption.APPEND);

                        } catch (final IOException e){
                            e.printStackTrace();
                        }
                    }
            );

            Files.writeString(outputDataFile,
                    System.lineSeparator() +
                        "=======================" +
                        System.lineSeparator(),
                    StandardOpenOption.APPEND
                    );

            // 16. All Names Longer Than 3
            Files.writeString(outputDataFile,
                    "16. Names longer than 3 characters:" +
                    System.lineSeparator(),
                    StandardOpenOption.APPEND);

            filteredStream(countries).
                    filter(country -> country.length() > 3).
                    sorted().
                    forEach(country -> {
                        try {

                            Files.writeString(outputDataFile,
                                    country +
                                    System.lineSeparator(),
                                    StandardOpenOption.APPEND);

                        } catch(final IOException e){
                            e.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private static Stream<String> filteredStream(final List<String> countries)
    {
        return countries.stream().filter(p-> !p.isEmpty() && !p.isBlank());
    }
}