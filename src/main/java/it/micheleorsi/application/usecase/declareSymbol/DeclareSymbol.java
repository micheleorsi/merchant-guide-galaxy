package it.micheleorsi.application.usecase.declareSymbol;

import it.micheleorsi.application.model.SymbolMapping;
import it.micheleorsi.application.repository.Repository;
import it.micheleorsi.application.usecase.UseCaseRequest;

public class DeclareSymbol implements UseCaseRequest<SymbolMapping>
{

  private final Repository<String,SymbolMapping> repository;

  public DeclareSymbol(Repository<String,SymbolMapping> repository)
  {
    this.repository = repository;
  }

  public void execute(SymbolMapping mapping)
  {
    repository.add(mapping);
  }

}
