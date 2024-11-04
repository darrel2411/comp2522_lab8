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
            // 1. Long Country Names: Write "Country names longer than 10 characters:" followed by all
            // country names with more than 10 characters (always one country per line).
            Files.writeString(outputDataFile,
                    "Country names longer than 10 characters: " + System.lineSeparator(),
                    StandardOpenOption.APPEND);

            final List<String> countryLongerThanTenChars;
            countryLongerThanTenChars = filteredStream(countries)
                    .filter(c-> c.length() > 10)
                    .toList();

            // writing data to directory matches/data.txt
            countryLongerThanTenChars.forEach(c-> {
                try {
                    Files.writeString(outputDataFile,  c + System.lineSeparator(), StandardOpenOption.APPEND);
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
                    "Short country names (shorter than 5 characters): " + System.lineSeparator(),
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
                    "Country ending with 'land': " + System.lineSeparator(),
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
                    "Country containing 'United': " + System.lineSeparator(),
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
                    "Country ASCENDING order: " + System.lineSeparator(),
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
                    "Country DESCENDING ORDER: " + System.lineSeparator(),
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
                    "Country by Unique first letters: " + System.lineSeparator(),
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private static Stream<String> filteredStream(final List<String> countries)
    {
        return countries.stream().filter(p-> !p.isEmpty() && !p.isBlank());
    }
}