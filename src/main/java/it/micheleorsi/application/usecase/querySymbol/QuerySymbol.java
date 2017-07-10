package it.micheleorsi.application.usecase.querySymbol;

import it.micheleorsi.application.model.GalacticSymbol;
import it.micheleorsi.application.service.GalacticToDecimalMapping;
import it.micheleorsi.application.usecase.SymbolNotFoundException;
import it.micheleorsi.application.usecase.UseCaseRequestReply;

public class QuerySymbol implements UseCaseRequestReply<GalacticSymbol,QuerySymbolResponse>
{

  private final GalacticToDecimalMapping mapping;

  public QuerySymbol(GalacticToDecimalMapping mapping)
  {
    this.mapping = mapping;
  }

  public QuerySymbolResponse execute(GalacticSymbol request)
  {
    Integer value = mapping.map(request).orElseThrow(SymbolNotFoundException::new);
    return new QuerySymbolResponse(request, value);
  }

}
