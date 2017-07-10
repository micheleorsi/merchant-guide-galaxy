package it.micheleorsi.console.command;

import it.micheleorsi.application.model.GalacticSymbol;
import it.micheleorsi.application.model.Metal;
import it.micheleorsi.application.usecase.declareMetal.DeclareMetal;
import it.micheleorsi.application.usecase.declareMetal.MetalDeclaration;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeclareMetalCommand implements Command
{
  private final Pattern pattern = Pattern.compile("([a-zA-Z\\s]+)("+ Metal.eitherOneType()+")\\sis\\s([0-9.]+)\\sCredits");

  private final DeclareMetal useCase;

  public DeclareMetalCommand(DeclareMetal useCase)
  {
    this.useCase = useCase;
  }

  @Override
  public boolean recognize(String commandPattern)
  {
    return pattern.matcher(commandPattern).matches();
  }

  @Override
  public void execute(String commandPattern)
  {
    MetalDeclaration metalValue = parse(commandPattern);
    useCase.execute(metalValue);
  }

  private MetalDeclaration parse(String commandPattern)
  {
    Matcher matcher = pattern.matcher(commandPattern);
    matcher.matches();
    String numerosity = matcher.group(1);
    String metal = matcher.group(2);
    String credits = matcher.group(3);

    return new MetalDeclaration(new GalacticSymbol(numerosity),
      Metal.valueOf(metal), BigDecimal.valueOf(Double.valueOf(credits)));
  }

}
