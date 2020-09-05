package org.ssio.integrationtest.fordoc;

import org.ssio.api.external.SsioManager;
import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.parse.ParseResult;
import org.ssio.api.external.save.SaveParam;
import org.ssio.api.external.save.SaveResult;
import org.ssio.api.factory.SsioManagerFactory;
import org.ssio.spi.clientexternal.filetypespecific.office.parse.OfficeParseParamBuilder;
import org.ssio.spi.clientexternal.filetypespecific.office.save.OfficeSaveParamBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class PlayerSsioExample {

    public static void main(String args[]) throws IOException {
        File sheetFile = Files.createTempFile("players", ".xlsx").toFile();
        FileOutputStream outputStream = new FileOutputStream(sheetFile);

        List<Player> players = Arrays.asList(Player.beckham(), Player.yaoming());

        SaveParam<Player> saveParam =
                //Excel-like file. For CSV,  use "new CsvSaveParamBuilder()"
                new OfficeSaveParamBuilder<Player>()
                        .setBeanClass(Player.class)
                        .setBeans(players)
                        .setOutputTarget(outputStream)
                        .build();

        //Go to the factory's code to see IoC options
        SsioManager ssioManager = SsioManagerFactory.newInstance();

        SaveResult saveResult = ssioManager.save(saveParam);
        //saveResult.getDatumErrors()

        System.out.println(saveResult);
        outputStream.close();
        System.out.println("Beans saved to " + sheetFile);


        FileInputStream inputStream = new FileInputStream(sheetFile);
        //Excel-like file. For CSV,  use "new CsvParseParamBuilder()"
        ParseParam<Player> parseParam = new OfficeParseParamBuilder()
                .setBeanClass(Player.class)
                .setSpreadsheetInput(inputStream)
                .build();

        ParseResult<Player> parseResult = ssioManager.parse(parseParam);
        List<Player> parsedPlayers = parseResult.getBeans();
        // parseResult.getCellErrors();

        inputStream.close();
        parsedPlayers.forEach(System.out::println);

    }


}
