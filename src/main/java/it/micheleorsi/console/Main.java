package it.micheleorsi.console;

import it.micheleorsi.application.model.Metal;
import it.micheleorsi.application.model.MetalValue;
import it.micheleorsi.application.model.SymbolMapping;
import it.micheleorsi.inmemory.InMemoryMetalValueRepository;
import it.micheleorsi.inmemory.InMemorySymbolMappingRepository;
import it.micheleorsi.application.repository.Repository;
import it.micheleorsi.application.service.GalacticToDecimalMapping;
import it.micheleorsi.application.service.RomanToDecimalConverter;
import it.micheleorsi.application.usecase.declareMetal.DeclareMetal;
import it.micheleorsi.application.usecase.declareSymbol.DeclareSymbol;
import it.micheleorsi.application.usecase.queryMetal.QueryMetal;
import it.micheleorsi.application.usecase.querySymbol.QuerySymbol;
import it.micheleorsi.console.command.DeclareMetalCommand;
import it.micheleorsi.console.command.DeclareSymbolCommand;
import it.micheleorsi.console.command.QueryMetalCommand;
import it.micheleorsi.console.command.QuerySymbolCommand;
import it.micheleorsi.console.display.DisplayMetal;
import it.micheleorsi.console.display.DisplaySymbol;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

public class Main
{
  public static void main(String[] args)
  {

    Repository<String,SymbolMapping> symbolRepo = new InMemorySymbolMappingRepository(
      new HashMap<>()
    );
    Repository<Metal, MetalValue> metalRepo = new InMemoryMetalValueRepository(
      new HashMap<>()
    );
    RomanToDecimalConverter converter = new RomanToDecimalConverter();
    GalacticToDecimalMapping galacticToDecimalMapping = new GalacticToDecimalMapping(symbolRepo, converter);

    Reader reader;
    try
    {
      if(args.length==1)
      {
        reader = new FileReader(args[0]);
      } else
      {
        System.out.println("Welcome to Merchant's Guide to the Galaxy!");
        System.out.println(".. in order to exit from this program you should send a SIGINT (with Ctrl-C for example).");
        reader = new InputStreamReader(System.in);
      }

      new ProgramLoop(
        new DeclareSymbolCommand(
          new DeclareSymbol(symbolRepo)
        ),
        new DeclareMetalCommand(
          new DeclareMetal(metalRepo, galacticToDecimalMapping)
        ),
        new QuerySymbolCommand(
          new QuerySymbol(galacticToDecimalMapping),
          new DisplaySymbol(System.out)
        ),
        new QueryMetalCommand(
          new QueryMetal(metalRepo, galacticToDecimalMapping),
          new DisplayMetal(System.out)
        )
      ).run(reader,System.out);
    }
    catch (FileNotFoundException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
