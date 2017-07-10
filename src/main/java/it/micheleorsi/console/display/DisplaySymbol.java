package it.micheleorsi.console.display;

import it.micheleorsi.application.usecase.querySymbol.QuerySymbolResponse;

import java.io.PrintStream;

public class DisplaySymbol
{
  private final PrintStream printStream;

  public DisplaySymbol(PrintStream printStream)
  {
    this.printStream = printStream;
  }

  public void show(QuerySymbolResponse response)
  {
    printStream.println(response.symbol+" is "+response.value);
  }

  public void symbolNotFound()
  {
    printStream.println("Sorry, I cannot find one of the symbol");
  }

}
