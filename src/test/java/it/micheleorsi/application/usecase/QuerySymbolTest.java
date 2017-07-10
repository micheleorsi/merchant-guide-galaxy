package it.micheleorsi.application.usecase;

import it.micheleorsi.application.model.GalacticSymbol;
import it.micheleorsi.application.model.Metal;
import it.micheleorsi.application.model.MetalValue;
import it.micheleorsi.application.service.GalacticToDecimalMapping;
import it.micheleorsi.application.usecase.queryMetal.QueryMetalRequest;
import it.micheleorsi.application.usecase.querySymbol.QuerySymbol;
import it.micheleorsi.application.usecase.querySymbol.QuerySymbolResponse;
import it.micheleorsi.util.JUnitRuleClassMockery;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuerySymbolTest
{

  @Rule
  public JUnitRuleMockery mockery = new JUnitRuleClassMockery();

  @Mock
  private GalacticToDecimalMapping mapping;

  private QuerySymbol underTest;

  @Before
  public void setup()
  {
    underTest = new QuerySymbol(mapping);
  }

  @Test
  public void useGalacticToDecimalMapping()
  {
    String[] list = new String[]{"firstSymbol","secondSymbol","thirdSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);

    mockery.checking(new Expectations()
    {{
      oneOf(mapping).map(request);
      will(returnValue(Optional.of(10)));
    }});

    underTest.execute(request);
  }

  @Test
  public void returnUseCaseResponse()
  {
    String[] list = new String[]{"firstSymbol","secondSymbol","thirdSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);

    mockery.checking(new Expectations()
    {{
      oneOf(mapping).map(request);
      will(returnValue(Optional.of(10)));
    }});

    QuerySymbolResponse response = underTest.execute(request);
    assertThat(response.symbol,is(request));
    assertThat(response.value,is(10));
  }

  @Test(expected = SymbolNotFoundException.class)
  public void whenASymbolIsMissing_throwException()
  {
    String[] list = new String[]{"firstSymbol","secondSymbol","thirdSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);

    mockery.checking(new Expectations()
    {{
      oneOf(mapping).map(request);
      will(returnValue(Optional.empty()));
    }});

    underTest.execute(request);
  }


}
