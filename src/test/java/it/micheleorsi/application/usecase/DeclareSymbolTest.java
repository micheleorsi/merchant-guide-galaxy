package it.micheleorsi.application.usecase;

import it.micheleorsi.application.model.RomanDigit;
import it.micheleorsi.application.model.SymbolMapping;
import it.micheleorsi.application.repository.Repository;
import it.micheleorsi.application.usecase.declareSymbol.DeclareSymbol;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DeclareSymbolTest
{
  private DeclareSymbol underTest;

  @Rule
  public JUnitRuleMockery mockery = new JUnitRuleMockery();

  @Mock
  private Repository<String,SymbolMapping> repository;

  @Before
  public void setup()
  {
    underTest = new DeclareSymbol(repository);
  }

  @Test
  public void whenADeclarationIsPerformed_symbolIsAddedToTheRepo()
  {
    SymbolMapping request = new SymbolMapping("word", RomanDigit.D);

    mockery.checking(new Expectations()
    {{
      oneOf(repository).add(with(request));
    }});
    underTest.execute(request);
  }

}
