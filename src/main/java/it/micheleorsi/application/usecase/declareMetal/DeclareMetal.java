package it.micheleorsi.application.usecase.declareMetal;

import it.micheleorsi.application.model.Metal;
import it.micheleorsi.application.model.MetalValue;
import it.micheleorsi.application.repository.Repository;
import it.micheleorsi.application.service.GalacticToDecimalMapping;
import it.micheleorsi.application.usecase.SymbolNotFoundException;
import it.micheleorsi.application.usecase.UseCaseRequest;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DeclareMetal implements UseCaseRequest<MetalDeclaration>
{

  private final Repository<Metal, MetalValue> metalRepository;
  private final GalacticToDecimalMapping mapping;

  public DeclareMetal(Repository<Metal, MetalValue> metalRepository,
    GalacticToDecimalMapping mapping)
  {
    this.metalRepository = metalRepository;
    this.mapping = mapping;
  }

  @Override
  public void execute(MetalDeclaration declaration)
  {
    Integer numerosity = mapping.map(declaration.symbol)
      .orElseThrow(SymbolNotFoundException::new);
    BigDecimal singleValue = declaration.creditsValue.divide(BigDecimal.valueOf(numerosity));
    MetalValue metalValue = new MetalValue(declaration.metal,singleValue);
    metalRepository.add(metalValue);
  }

}
