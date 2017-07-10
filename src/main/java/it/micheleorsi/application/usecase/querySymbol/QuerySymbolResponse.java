package it.micheleorsi.application.usecase.querySymbol;

import it.micheleorsi.application.model.GalacticSymbol;

public class QuerySymbolResponse
{
  public final GalacticSymbol symbol;
  public final Integer value;

  public QuerySymbolResponse(GalacticSymbol symbol, Integer value)
  {
    this.symbol = symbol;
    this.value = value;
  }

}
