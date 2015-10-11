package de.therapeutenkiller.haushaltsbuch.domaene;


final class Greeter {

  @CoverageIgnore private Greeter() {
    // $COVERAGE-IGNORE$
  }

  public static String sayHello(final String matthias) {
    return "Hello, " + matthias + "!";
  }

  @CoverageIgnore public static void unusedMethod() {

  }
}
