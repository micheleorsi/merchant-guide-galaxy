package it.micheleorsi.application.service;

import it.micheleorsi.application.model.GalacticSymbol;
import it.micheleorsi.application.model.RomanDigit;
import it.micheleorsi.application.model.RomanNumber;
import it.micheleorsi.application.model.SymbolMapping;
import it.micheleorsi.application.repository.Repository;
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

public class GalacticToDecimalMappingTest
{

  @Rule
  public JUnitRuleMockery mockery = new JUnitRuleClassMockery();

  @Mock
  private Repository<String,SymbolMapping> repository;
  @Mock
  private RomanToDecimalConverter romanToDecimalConverter;

  private GalacticToDecimalMapping underTest;

  @Before
  public void setup()
  {
    underTest = new GalacticToDecimalMapping(repository, romanToDecimalConverter);
  }

  @Test
  public void singleSymbolRequested_onlyOneLookUpInTheRepo()
  {
    String[] list = new String[]{"singleSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);
    SymbolMapping returnSymbol = new SymbolMapping("singleSymbol", RomanDigit.C);

    mockery.checking(new Expectations()
    {{
      oneOf(repository).findBy(with(request.word[0]));
      will(returnValue(Optional.of(returnSymbol)));
      allowing(romanToDecimalConverter).convert(new RomanNumber("C"));
      will(returnValue("100"));
    }});

    underTest.map(request);
  }

  @Test
  public void multipleSymbolRequested_multipleLookUpInTheRepo()
  {
    String[] list = new String[]{"singleSymbol","singleSymbol","singleSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);
    SymbolMapping returnSymbol = new SymbolMapping("singleSymbol", RomanDigit.C);

    mockery.checking(new Expectations()
    {{
      exactly(3).of(repository).findBy(with("singleSymbol"));
      will(returnValue(Optional.of(returnSymbol)));
      allowing(romanToDecimalConverter).convert(new RomanNumber("CCC"));
      will(returnValue("300"));

    }});

    underTest.map(request);
  }

  @Test
  public void whenSymbolIsNotPresent_theReturnIsEmpty()
  {
    String[] list = new String[]{"firstSymbol","secondSymbol","thirdSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);

    mockery.checking(new Expectations()
    {{
      oneOf(repository).findBy(with(any(String.class)));
      will(returnValue(Optional.empty()));
    }});

    assertThat(underTest.map(request),is(Optional.empty()));
  }

  @Test
  public void whenAllSymbolsArePresent_returnTheValue()
  {
    String[] list = new String[]{"firstSymbol","firstSymbol","firstSymbol"};
    GalacticSymbol request = new GalacticSymbol(list);
    SymbolMapping returnSymbol = new SymbolMapping("singleSymbol", RomanDigit.C);

    mockery.checking(new Expectations()
    {{
      exactly(3).of(repository).findBy(with("firstSymbol"));
      will(returnValue(Optional.of(returnSymbol)));
      allowing(romanToDecimalConverter).convert(new RomanNumber("CCC"));
      will(returnValue("300"));
    }});

    Integer number = underTest.map(request).get();
    assertThat(number,is(300));
  }


}
