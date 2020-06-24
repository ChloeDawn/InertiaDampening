package dev.sapphic.inertiadampening;

import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.Option;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class InertiaDampening {
  public static final String NAMESPACE = "inertiadampening";

  private static final Logger LOGGER = LogManager.getLogger();
  private static final Path FILE = Paths.get(NAMESPACE + ".txt");
  private static final String ENABLED = "enabled";

  private static boolean enabled = true;

  private InertiaDampening() {
    throw new UnsupportedOperationException();
  }

  public static boolean isEnabled() {
    return enabled;
  }

  public static void setEnabled(final boolean enabled) {
    if (InertiaDampening.enabled != enabled) {
      InertiaDampening.enabled = enabled;
      writeProperties();
    }
  }

  public static Option getOption() {
    return OptionHolder.OPTION;
  }

  @ApiStatus.Internal
  public static void init() {
    readProperties();
  }

  private static void readProperties() {
    LOGGER.debug("Reading properties from {}", FILE);

    final Properties properties = new Properties();

    try (final Reader reader = Files.newBufferedReader(FILE)) {
      properties.load(reader);
    } catch (final NoSuchFileException e) {
      writeProperties();
    } catch (final IOException e) {
      throw new IllegalStateException("Unable to read properties from " + FILE, e);
    } catch (final IllegalArgumentException e) {
      LOGGER.error("Malformed properties in {}", FILE, e);
      writeProperties();
    }

    parseProperties(properties);
  }

  private static void parseProperties(final Properties properties) {
    enabled = Boolean.parseBoolean(properties.getProperty(ENABLED, "true"));
  }

  private static void writeProperties() {
    LOGGER.debug("Writing properties to {}", FILE);

    final Properties properties = new Properties();

    storeProperties(properties);

    try (final Writer writer = Files.newBufferedWriter(FILE)) {
      properties.store(writer, null);
    } catch (final IOException e) {
      throw new IllegalStateException("Unable to write properties to " + FILE, e);
    }
  }

  private static void storeProperties(final Properties properties) {
    properties.setProperty(ENABLED, Boolean.toString(enabled));
  }

  private static final class OptionHolder {
    private static final BooleanOption OPTION =
      new BooleanOption("options." + NAMESPACE, o -> isEnabled(), (o, v) -> setEnabled(v));
  }
}
