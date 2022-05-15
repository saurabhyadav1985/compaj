package com.hiddenproject.compaj.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Permission;
import com.hiddenproject.compaj.core.translator.Translator;
import com.hiddenproject.compaj.core.translator.TranslatorUtils;
import com.hiddenproject.compaj.core.translator.base.GroovyTranslator;
import com.hiddenproject.compaj.core.translator.base.GroovyTranslatorUtils;
import groovy.lang.Binding;

public class CompaJ {

  private static CompaJ INSTANCE;
  private static ExitManager exitManager;

  private final Translator translator;
  private final TranslatorUtils translatorUtils;

  private CompaJ() {
    exitManager = new ExitManager();
    translatorUtils = new GroovyTranslatorUtils();
    translator = new GroovyTranslator(translatorUtils);
  }

  public static CompaJ getInstance() {
    if(INSTANCE == null) {
      INSTANCE = new CompaJ();
    }
    return INSTANCE;
  }

  public static void useCompaJSyntax(boolean f) {
    GroovyTranslatorUtils.enableRawGroovy(!f);
  }

  public static void readFile(String url) throws IOException {
    String script = new String(Files.readAllBytes(Paths.get("/Users/kpekepsalt/compatmental-models/src/main/java/com/hiddenproject/compaj/core/model/base/TestModel.txt")));
    getInstance().getTranslator().evaluate(script);
  }

  public Translator getTranslator() {
    return translator;
  }

  public static void exit() {
    exitManager.reset();
    System.exit(0);
  }

  public static String getTranslatorBindings() {
    return
  }

  private static class ExitManager extends SecurityManager {

    private final SecurityManager DEFAULT;

    public ExitManager() {
      DEFAULT = System.getSecurityManager();
      System.setSecurityManager(this);
    }

    public void reset() {
      System.setSecurityManager(DEFAULT);
    }

    /** Deny permission to exit the VM. */
    public void checkExit(int status) {
      throw( new SecurityException() );
    }

    /** Allow this security manager to be replaced,
     if fact, allow pretty much everything. */
    public void checkPermission(Permission perm) {
    }
  }
}
