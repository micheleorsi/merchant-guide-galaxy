package it.micheleorsi.application.service;

import it.micheleorsi.application.model.GalacticSymbol;
import it.micheleorsi.application.model.RomanDigit;
import it.micheleorsi.application.model.RomanNumber;
import it.micheleorsi.application.model.SymbolMapping;
import it.micheleorsi.application.repository.Repository;

import java.util.Optional;

public class GalacticToDecimalMapping
{
  private final Repository<String,SymbolMapping> repository;
  private final RomanToDecimalConverter romanToDecimalConverter;

  public GalacticToDecimalMapping(
    Repository<String, SymbolMapping> repository, RomanToDecimalConverter romanToDecimalConverter)
  {
    this.repository = repository;
    this.romanToDecimalConverter = romanToDecimalConverter;
  }

  public Optional<Integer> map(GalacticSymbol symbol)
  {
    RomanDigit[] digit = new RomanDigit[symbol.word.length];
    boolean notFullyMapped = map(symbol, digit);
    if (notFullyMapped)
      return Optional.empty();
    RomanNumber number = new RomanNumber(digit);
    return Optional.of(Integer.valueOf(romanToDecimalConverter.convert(number)));
  }

  private boolean map(GalacticSymbol symbol, RomanDigit[] num)
  {
    for(int i = 0; i<symbol.word.length; i++)
    {
      Optional<SymbolMapping> numeral = repository.findBy(symbol.word[i]);
      if(!numeral.isPresent())
      {
        return true;
      }
      num[i]= numeral.get().roman;
    }
    return false;
  }

}
