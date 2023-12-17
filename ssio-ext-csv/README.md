# ssio-ext-csv

Beans <-> CSV

# Usage

## Maven/Gradle Dependency

Click [this](https://search.maven.org/artifact/com.github.chenjianjx.ssio/ssio-ext-csv) to find the latest version, and add it to your project with maven or gradle.

## Add annotations to your JavaBean
See [this](../README.md#add-annotations-to-your-javabean)

## One-liners

### Beans -> CSV String

```java
String csvString = CsvSsioTemplate.defaultInstance().toCsvString(players, Player.class, false);
```

### CSV String -> Beans
```java
List<Player> players = CsvSsioTemplate.defaultInstance().toBeans(csvString, Player.class, true);
```

## API with more control

### Beans -> CSV

```java
SaveParam<Player> saveParam =
        new CsvSaveParamBuilder<Player>()
                .setBeans(players)
                .setBeanClass(Player.class)
                .setOutputTarget(outputStream)
                .setCreateHeader(false)  //no header
                .setStillSaveIfDataError(false) //if there is data error, don't write to output
                //put datum error stack trace in the cell
                .setDatumErrDisplayFunction((datumError) -> datumError.getStackTrace())
                .setOutputCharset("utf8") //the charset
                .setCellSeparator('\t') //instead of `,`
                .build();

SsioManager ssioManager = new SsioManagerImpl(new CsvWorkbookFactory()); //IoC friendly

SaveResult saveResult = ssioManager.save(saveParam);
//It will tell which bean's which property is wrong and why
saveResult.getDatumErrors().forEach(System.err::println);
```

### CSV -> Beans

```java
ParseParam<Player> parseParam = new CsvParseParamBuilder<Player>()
        .setBeanClass(Player.class)
        //e.g. column 3 -> bean property "foo"
        .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_INDEX)
        .setSpreadsheetInput(inputStream)
        .setSheetHasHeader(false) //The sheet has no header row
        .setInputCharset("utf8") //the charset
        .setCellSeparator('\t') //instead of `,`
        .build();

ParseResult<Player> parseResult = ssioManager.parse(parseParam);
List<Player> parsedPlayers = parseResult.getBeans();
//It will tell which cell is wrong and why
parseResult.getCellErrors().forEach(System.err::println);
```
