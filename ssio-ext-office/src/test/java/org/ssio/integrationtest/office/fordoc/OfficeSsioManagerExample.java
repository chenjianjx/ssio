package org.ssio.integrationtest.office.fordoc;

import org.ssio.api.impl.SsioManagerImpl;
import org.ssio.api.impl.common.sheetlocate.SheetLocator;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.office.parse.OfficeParseParamBuilder;
import org.ssio.api.interfaces.office.save.OfficeSaveParamBuilder;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.integrationtest.focdoc.Player;
import org.ssio.spi.impl.office.factory.OfficeWorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.ssio.integrationtest.support.SsioITHelper.createSpreadsheetFile;

public class OfficeSsioManagerExample {

    public static void main(String args[]) throws IOException {
        File sheetFile = createSpreadsheetFile(OfficeSsioManagerExample.class.getSimpleName(), "xlsx");

        FileOutputStream outputStream = new FileOutputStream(sheetFile);

        List<Player> players = Arrays.asList(Player.beckham(), Player.yaoming());

        SaveParam<Player> saveParam =
                new OfficeSaveParamBuilder<Player>()
                        .setBeans(players)
                        .setBeanClass(Player.class)
                        .setOutputTarget(outputStream)
                        .setCreateHeader(false)  //no header
                        .setStillSaveIfDataError(false) //if there is data error, don't write to output
                        .setDatumErrDisplayFunction((datumError) -> datumError.getStackTrace()) //put datum error stack trace in the cell
                        .build();

        SsioManager ssioManager = new SsioManagerImpl(new OfficeWorkbookFactory()); //IoC friendly

        SaveResult saveResult = ssioManager.save(saveParam);
        saveResult.getDatumErrors().forEach(System.err::println); //It will tell which bean's which property is wrong and why


        outputStream.close();
        System.out.println("Beans saved to " + sheetFile);

        /************************************************************************************/

        FileInputStream inputStream = new FileInputStream(sheetFile);


        ParseParam<Player> parseParam = new OfficeParseParamBuilder<Player>()
                .setBeanClass(Player.class)
                .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_INDEX) //e.g. column 3 -> bean property "foo"
                .setSpreadsheetInput(inputStream)
                .setSheetHasHeader(false) //The sheet has no header row
                .setSheetLocator(SheetLocator.byNameLocator("Sheet0")) //In a multi-sheet Excel file, find the target sheet
                .build();

        ParseResult<Player> parseResult = ssioManager.parse(parseParam);
        List<Player> parsedPlayers = parseResult.getBeans();
        parseResult.getCellErrors().forEach(System.err::println); //It will tell which cell is wrong and why


        inputStream.close();
        parsedPlayers.forEach(System.out::println);

    }


}
