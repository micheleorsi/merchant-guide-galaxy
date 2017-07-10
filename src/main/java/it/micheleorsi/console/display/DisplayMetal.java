package it.micheleorsi.console.display;

import it.micheleorsi.application.usecase.queryMetal.QueryMetalResponse;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DisplayMetal
{
  private final PrintStream printStream;

  public DisplayMetal(PrintStream printStream)
  {
    this.printStream = printStream;
  }

  public void show(QueryMetalResponse response)
  {
    NumberFormat nf = new DecimalFormat("##.###");
    printStream.println(response.numerosity+" "+response.metal+ " is "+
      nf.format(response.value)+" Credits");
  }

  public void symbolNotFound()
  {
    printStream.println("Sorry, I cannot find one of the symbol");
  }

  public void metalNotFound()
  {
    printStream.println("Sorry, I cannot find the metal");
  }

}
