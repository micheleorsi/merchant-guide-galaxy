package it.micheleorsi.application.usecase.queryMetal;

import it.micheleorsi.application.model.GalacticSymbol;
import it.micheleorsi.application.model.Metal;

import java.math.BigDecimal;

public class QueryMetalResponse
{

  public final Metal metal;
  public final GalacticSymbol numerosity;
  public final BigDecimal value;

  public QueryMetalResponse(Metal metal, GalacticSymbol numerosity, BigDecimal value)
  {
    this.metal = metal;
    this.numerosity = numerosity;
    this.value = value;
  }

}
