package it.micheleorsi.application.service;

import it.micheleorsi.application.model.RomanNumber;
import org.junit.Before;

public class RomanToDecimalConverterTestBase
{

  protected RomanToDecimalConverter romanToDecimalConverter;

  @Before
  public void setUp()
  {
    romanToDecimalConverter = new RomanToDecimalConverter();
  }
}
