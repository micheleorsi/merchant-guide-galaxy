package it.micheleorsi.console.command;

import it.micheleorsi.application.model.RomanDigit;
import it.micheleorsi.application.model.SymbolMapping;
import it.micheleorsi.application.usecase.declareSymbol.DeclareSymbol;
import it.micheleorsi.util.JUnitRuleClassMockery;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DeclareSymbolCommandTest
{

  @Rule
  public JUnitRuleMockery context = new JUnitRuleClassMockery();

  @Mock
  private DeclareSymbol useCase;

  private DeclareSymbolCommand underTest;

  @Before
  public void setup()
  {
    underTest = new DeclareSymbolCommand(useCase);
  }

  @Test
  public void unparseableCommand()
  {
    boolean recognize = underTest.recognize("this is not parsable");
    assertThat(recognize, is(false));
  }

  @Test
  public void parseableCommand()
  {
    boolean recognize = underTest.recognize("glob is I");
    assertThat(recognize, is(true));
  }

  @Test
  public void succesfulExecution()
  {
    SymbolMapping symbol = new SymbolMapping("glob", RomanDigit.I);
    context.checking(new Expectations()
    {{
      oneOf(useCase).execute(with(symbol));
    }});
    underTest.execute("glob is I");
  }
}
