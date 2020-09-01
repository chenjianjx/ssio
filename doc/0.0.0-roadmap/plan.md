# Roadmap

* Define the api including the data structure (using builder pattern)   ✔
* saving using header map instead of annotations (default format for date/number in excel and csv) 
  * Basic features  ✔
  * optional header   ✔
  * csv seperator    ✔
* parsing using header map instead of annotations (default format for date/number in excel and csv)
  * Basic features including negative cases ✔
  * enum problem at last ✔
  * Test about non-0 sheet ✔ 
* annotations (default format for date/number in excel and csv)
  * beans2sheet
  * sheet2beans
* format for date in excel and csv
* format for numbers in csv
* optional header during parsing 
* custom serialiser/deserialiser to support any type of field in a bean
* extensible file type  
* fix the api/internal problem and make helper/manager/workers injectable 
* [Release Milestone] shortcut methods  and readme
* explore formula and formula errors
* extract, split and merge