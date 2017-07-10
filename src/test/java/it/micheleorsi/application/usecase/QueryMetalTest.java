package it.micheleorsi.application.usecase;

import it.micheleorsi.application.model.GalacticSymbol;
import it.micheleorsi.application.model.Metal;
import it.micheleorsi.application.model.MetalValue;
import it.micheleorsi.application.repository.Repository;
import it.micheleorsi.application.service.GalacticToDecimalMapping;
import it.micheleorsi.application.usecase.queryMetal.MetalNotFoundException;
import it.micheleorsi.application.usecase.queryMetal.QueryMetal;
import it.micheleorsi.application.usecase.queryMetal.QueryMetalRequest;
import it.micheleorsi.application.usecase.queryMetal.QueryMetalResponse;
import it.micheleorsi.util.JUnitRuleClassMockery;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QueryMetalTest
{

  @Rule
  public JUnitRuleMockery mockery = new JUnitRuleClassMockery();

  @Mock
  private Repository<Metal,MetalValue> repository;
  @Mock
  private GalacticToDecimalMapping mapping;

  private QueryMetal underTest;

  @Before
  public void setup()
  {
    underTest = new QueryMetal(repository, mapping);
  }

  @Test(expected = MetalNotFoundException.class)
  public void whenMetalIsNotPresent_ThrowException()
  {
    String[] list = new String[]{"firstSymbol","secondSymbol","thirdSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);
    QueryMetalRequest value = new QueryMetalRequest(
      Metal.Silver,request
    );

    mockery.checking(new Expectations()
    {{
      oneOf(repository).findBy(with(Metal.Silver));
      will(returnValue(Optional.empty()));
      never(mapping).map(with(request));
    }});

    underTest.execute(value);
  }

  @Test
  public void whenMetalIsPresent_invokeMapper()
  {
    String[] list = new String[]{"firstSymbol","secondSymbol","thirdSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);
    QueryMetalRequest value = new QueryMetalRequest(
      Metal.Silver,request
    );

    mockery.checking(new Expectations()
    {{
      allowing(repository).findBy(with(Metal.Silver));
      will(returnValue(Optional.of(new MetalValue(Metal.Silver, BigDecimal.valueOf(10)))));
      oneOf(mapping).map(with(request));
      will(returnValue(Optional.of(2)));
    }});

    underTest.execute(value);
  }

  @Test(expected = SymbolNotFoundException.class)
  public void whenASymbolIsMissing_throwException()
  {
    String[] list = new String[]{"firstSymbol","secondSymbol","thirdSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);
    QueryMetalRequest value = new QueryMetalRequest(
      Metal.Silver,request
    );

    mockery.checking(new Expectations()
    {{
      allowing(repository).findBy(with(Metal.Silver));
      will(returnValue(Optional.of(new MetalValue(Metal.Silver,BigDecimal.valueOf(10)))));
      oneOf(mapping).map(with(request));
      will(returnValue(Optional.empty()));
    }});

    underTest.execute(value);
  }

  @Test
  public void metalValueCalculationHasBeenDoneProperly()
  {
    String[] list = new String[]{"firstSymbol","secondSymbol","thirdSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);
    QueryMetalRequest value = new QueryMetalRequest(
      Metal.Silver,request
    );

    mockery.checking(new Expectations()
    {{
      allowing(repository).findBy(with(Metal.Silver));
      will(returnValue(Optional.of(new MetalValue(Metal.Silver,BigDecimal.valueOf(10)))));
      allowing(mapping).map(with(request));
      will(returnValue(Optional.of(2)));
    }});

    QueryMetalResponse response = underTest.execute(value);
    assertThat(response.value,is(equalTo(BigDecimal.valueOf(20))));
  }

}
