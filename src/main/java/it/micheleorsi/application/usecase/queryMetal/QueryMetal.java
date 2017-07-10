package it.micheleorsi.application.usecase.queryMetal;

import it.micheleorsi.application.model.Metal;
import it.micheleorsi.application.model.MetalValue;
import it.micheleorsi.application.repository.Repository;
import it.micheleorsi.application.service.GalacticToDecimalMapping;
import it.micheleorsi.application.usecase.SymbolNotFoundException;
import it.micheleorsi.application.usecase.UseCaseRequestReply;

import java.math.BigDecimal;

public class QueryMetal implements UseCaseRequestReply<QueryMetalRequest, QueryMetalResponse>
{
  private final Repository<Metal,MetalValue> repository;
  private final GalacticToDecimalMapping mapping;

  public QueryMetal(Repository<Metal,MetalValue> repository, GalacticToDecimalMapping mapping)
  {
    this.repository = repository;
    this.mapping = mapping;
  }

  @Override
  public QueryMetalResponse execute(QueryMetalRequest request)
  {
    MetalValue metalValue = repository.findBy(request.metal)
                                .orElseThrow(MetalNotFoundException::new);
    Integer numerosity = mapping.map(request.numerosity)
                                .orElseThrow(SymbolNotFoundException::new);
    BigDecimal calculatedValue = BigDecimal.valueOf(numerosity).multiply(metalValue.value);
    return new QueryMetalResponse(request.metal,
      request.numerosity,calculatedValue);
  }

}
