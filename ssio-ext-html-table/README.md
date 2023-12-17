# ssio-ext-html-table

Beans <-> Html Table

# Usage

## Maven/Gradle Dependency

Click [this](https://search.maven.org/artifact/com.github.chenjianjx.ssio/ssio-ext-html-table) to find the latest version, and add it to your project with maven or gradle.

## Add annotations to your JavaBean
See [this](../README.md#add-annotations-to-your-javabean)

## One-liners

### Beans -> Html Page

```java
HtmlTableSsioTemplate.defaultInstance().toHtmlPage(players, Player.class, outputStream, "utf8", false);
```

```java
//more customization
HtmlTableSsioTemplate.defaultInstance().toHtmlPage(players, Player.class, outputStream, "utf8",
        "some-title", //title for the html page
        new HashMap<String, String>() {{
            //assign an ID to the table html element
            put("id", "some-id");
            //assign a CSS class to the table html element
            put("class", "my-own-table");
        }},
        ownStyles, //my own css definition string
        false);
```

### Html Table or Page String -> Beans
```java
List<Player> beans = HtmlTableSsioTemplate.defaultInstance().toBeans(htmlString, Player.class, true);
```

## API with more control

### Beans -> Html Table

```java
SaveParam<Player> saveParam =
        new HtmlTableSaveParamBuilder<Player>()
                .setBeans(players)
                .setBeanClass(Player.class)
                .setOutputTarget(outputStream)
                .setCreateHeader(false)  //no header
                .setStillSaveIfDataError(false) //if there is data error, don't write to output
                //put datum error stack trace in the cell
                .setDatumErrDisplayFunction((datumError) -> datumError.getStackTrace())
                .setOutputCharset("utf8") //the charset
                .setHtmlElementAttributes(HtmlElementAttributes
                        .neoBuilder()
                        //assign an ID to the table html element
                        .setOneTableAttribute("id", "some-id")
                        //assign a CSS class to the table html element
                        .setOneTableAttribute("class", "my-own-table")
                        .build())
                .build();

SsioManager ssioManager = new SsioManagerImpl(new HtmlTableWorkbookFactory()); //IoC friendly

SaveResult saveResult = ssioManager.save(saveParam);
//It will tell which bean's which property is wrong and why
saveResult.getDatumErrors().forEach(System.err::println);
```

### Html Table -> Beans

```java
ParseParam<Player> parseParam = new HtmlTableParseParamBuilder<Player>()
        .setBeanClass(Player.class)
        //e.g. column 3 -> bean property "foo"
        .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_INDEX)
        .setSpreadsheetInput(inputStream)
        .setSheetHasHeader(false) //The sheet has no header row
        .setInputCharset("utf8") //the charset
        .build();

ParseResult<Player> parseResult = ssioManager.parse(parseParam);
List<Player> parsedPlayers = parseResult.getBeans();
//It will tell which cell is wrong and why
parseResult.getCellErrors().forEach(System.err::println);
```
