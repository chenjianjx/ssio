# Ssio = Simple Spreadsheet I/O

Save javabeans to a spreadsheet, or parse a spreadsheet into javabeans. 

# Features
* Annotation based mapping
* Support Excel, CSV and other spreadsheet types 
* Complex type handling
* Saving features
  * Show errors in cells (if needed) 
  * Optional header
* Parsing features
  * Map by column name or column index
  * Parse a sheet without a header 
  
# Quick start

_The following will use Excel type (`*.xlsx`) as an example.  For other [spreadsheet types](#supported-spreadsheet-types), check corresponding docs._

## Maven/Gradle Dependency 

Click [this](https://search.maven.org/artifact/com.github.chenjianjx.ssio/ssio-ext-office) to find the latest version, and add it to your project with maven or gradle.


## Add annotations to your JavaBean

```java
public class Player {

    @SsColumn(index = 0, name = "Id")
    private long id;

    @SsColumn(index = 1) // the column name will be decided as "Birth Country"
    private String birthCountry;

    @SsColumn(index = 2, typeHandler = FullNameTypeHandler.class) //complex prop type
    private FullName fullName;

    //The enum's name() will be saved. You can use a typeHandler to change it
    @SsColumn(index = 3) 
    private SportType sportType;

    @SsColumn(index = 4, format = "yyyy/MM/dd") //date format
    private LocalDate birthDate;

    //if you prefer saving timestamp as number
    @SsColumn(index = 5, typeHandler = TimestampAsMillisHandler.class)
    private LocalDateTime createdWhen;
    ...
}
```

## Beans to spreadsheet in one line of code

```java
OfficeSsioTemplate.defaultInstance().toSheet(players, Player.class, outputStream, false);
```  


## Spreadsheet to beans in one line of code

```java
List<Player> players = OfficeSsioTemplate.defaultInstance()
                            .toBeans(inputStream, Player.class, true);
```  

# Want more control ?

You can get more control by using more parameters, and can get more details from a result data structure .

_The following will use Excel type (`*.xlsx`) as an example.  For other [spreadsheet types](#supported-spreadsheet-types), check corresponding docs._

## Beans to spreadsheet

```java
SaveParam<Player> saveParam =
    new OfficeSaveParamBuilder<Player>()
        .setBeans(players)
        .setBeanClass(Player.class)
        .setOutputTarget(outputStream)
        .setCreateHeader(false)  //no header
        .setStillSaveIfDataError(false) //if there is data error, don't write to output
        .setDatumErrDisplayFunction(
                //put datum error stack trace in the cell
                (datumError) -> datumError.getStackTrace()) 
        .build();

SsioManager ssioManager = new SsioManagerImpl(new OfficeWorkbookFactory()); //IoC friendly

SaveResult saveResult = ssioManager.save(saveParam);

//It will tell which bean's which property is wrong and why
saveResult.getDatumErrors().forEach(System.err::println); 
```

## Spreadsheet to beans

```java

ParseParam<Player> parseParam = new OfficeParseParamBuilder<Player>()
        .setBeanClass(Player.class)
        //e.g. column 3 -> bean property "foo"
        .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_INDEX) 
        .setSpreadsheetInput(inputStream)
        .setSheetHasHeader(false) //The sheet has no header row
        //In a multi-sheet Excel file, find the target sheet
        .setSheetLocator(SheetLocator.byNameLocator("Sheet0")) 
        .build();

SsioManager ssioManager = new SsioManagerImpl(new OfficeWorkbookFactory()); //IoC friendly

ParseResult<Player> parseResult = ssioManager.parse(parseParam);
List<Player> parsedPlayers = parseResult.getBeans();
//It will tell which cell is wrong and why
parseResult.getCellErrors().forEach(System.err::println); 
```


# Things that may be surprising

* @SsColumn can be applied to getter/setter methods
* @SsColumn defined in parent classes will be inherited
* During parsing, blank rows will be ignored
  
# Supported spreadsheet types

* [Excel](./ssio-ext-office/README.md)
* [Csv](./ssio-ext-csv/README.md)
* [Html table](./ssio-ext-html-table/README.md)


# Credits
* [Sep4j](https://github.com/chenjianjx/sep4j) is a precursor of this library
