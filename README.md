# Ssio = Simple Spreadsheet I/O

Save javabeans to a spreadsheet, or parse a spreadsheet into javabeans

# Features
* Annotation based mapping
* Support Excel-like & CSV 
* Complex type handling 
* Saving features
  * Show errors in cells (if needed) 
  * Optional header 
* Parsing features
  * Parse from non-first sheet
  * Map by column name/index
  * Parse sheet without header 
  
# Quick start

Find [ssio in central maven](https://search.maven.org/search?q=a:ssio) and add the dependency to your maven/gradle project.

**Bean definition** 

```java
public class Player {

    @SsColumn(index = 0, name = "Id")
    private long id;

    @SsColumn(index = 1) // the column name will be decided as "Birth Country"
    private String birthCountry;

    @SsColumn(index = 2, typeHandler = FullNameTypeHandler.class) //complex prop type
    private FullName fullName;

    @SsColumn(index = 3) //The enum's name() will be saved. Otherwise, use a typeHandler
    private SportType sportType;

    @SsColumn(index = 4, format = "yyyy/MM/dd") //date format
    private LocalDate birthDate;

    @SsColumn(index = 5, typeHandler = TimestampAsMillisHandler.class)
    //if you prefer saving timestamp as number
    private LocalDateTime createdWhen;
    ...
}
```

**Save** 
```java

      SaveParam<Player> saveParam =
                //Excel-like file. For CSV,  use "new CsvSaveParamBuilder()"
                new OfficeSaveParamBuilder<Player>()  
                        .setBeanClass(Player.class)
                        .setBeans(players)
                        .setOutputTarget(outputStream)
                        .build();

        //Go to the SsioManagerFactory.java to see IoC options
        SsioManager ssioManager = SsioManagerFactory.newInstance();

        SaveResult saveResult = ssioManager.save(saveParam);
        //saveResult.getDatumErrors()

```

**Parse**
```java
        //Excel-like file. For CSV,  use "new CsvParseParamBuilder()"
        ParseParam<Player> parseParam = new OfficeParseParamBuilder()
                .setBeanClass(Player.class)
                .setSpreadsheetInput(inputStream)
                .build();

        ParseResult<Player> parseResult = ssioManager.parse(parseParam);
        List<Player> parsedPlayers = parseResult.getBeans();
        // parseResult.getCellErrors();

```  

# Advanced features

* Explore [SaveParamBuilder](src/main/java/org/ssio/api/external/save/SaveParamBuilder.java) , [ParseParamBuilder](src/main/java/org/ssio/api/external/parse/ParseParamBuilder.java) and their child classes for all options
* Things that may be surprising (to some people)
  * @SsColumn can be applied to getter/setter methods
  * @SsColumn defined in parent classes will be inherited

All features are covered by the [integration tests](/src/test/java/org/ssio/integrationtest/cases)
  
# Extend ssio
It's possible to extend ssio for file types other than Excel-like files and CSV files, e.g., Google Spreadsheet

A sample of extension can be found at https://github.com/chenjianjx/ssio-extension-sample
    

# Credits
* The library is based on [Apache POI](https://poi.apache.org/) and [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/index.html)
* [Sep4j](https://github.com/chenjianjx/sep4j) is a precursor of this lbirary
