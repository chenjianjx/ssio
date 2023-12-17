package org.ssio.integrationtest.csv.fordoc;

import org.ssio.api.impl.SsioManagerImpl;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.csv.parse.CsvParseParamBuilder;
import org.ssio.api.interfaces.csv.save.CsvSaveParamBuilder;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.integrationtest.focdoc.Player;
import org.ssio.spi.impl.csv.factory.CsvWorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.ssio.integrationtest.support.SsioITHelper.createSpreadsheetFile;

public class CsvSsioManagerExample {

    public static void main(String args[]) throws IOException {
        File sheetFile = createSpreadsheetFile(CsvSsioManagerExample.class.getSimpleName(), "csv");

        FileOutputStream outputStream = new FileOutputStream(sheetFile);

        List<Player> players = Arrays.asList(Player.beckham(), Player.yaoming());

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


        outputStream.close();
        System.out.println("Beans saved to " + sheetFile);

        /************************************************************************************/

        FileInputStream inputStream = new FileInputStream(sheetFile);


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


        inputStream.close();
        parsedPlayers.forEach(System.out::println);

    }


}
