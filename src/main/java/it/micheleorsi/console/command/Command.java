package it.micheleorsi.console.command;

public interface Command
{
  boolean recognize(String commandPattern);

  void execute(String commandPattern);
}
