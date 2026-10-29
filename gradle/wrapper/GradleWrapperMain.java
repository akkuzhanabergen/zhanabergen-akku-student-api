package org.gradle.wrapper;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/** A small Gradle launcher: on first run download and unpack the Gradle distribution. */
public final class GradleWrapperMain {
    private GradleWrapperMain() { }
    public static void main(String[] args) throws Exception {
        Path project = Path.of(System.getProperty("user.dir")).toAbsolutePath();
        Path properties = project.resolve("gradle/wrapper/gradle-wrapper.properties");
        if (!Files.exists(properties)) {
            System.err.println("Missing " + properties);
            System.exit(2);
        }
        Properties settings = new Properties();
        try (InputStream stream = Files.newInputStream(properties)) { settings.load(stream); }
        String url = settings.getProperty("distributionUrl");
        if (url == null || !url.endsWith("-bin.zip")) {
            throw new IOException("Expected a Gradle bin distribution ZIP in gradle-wrapper.properties");
        }
        String zipName = url.substring(url.lastIndexOf('/') + 1);
        String version = zipName.substring("gradle-".length(), zipName.length() - "-bin.zip".length());
        Path cache = Path.of(System.getProperty("user.home"), ".gradle", "personal-wrapper", "gradle-" + version);
        Path home = cache.resolve("gradle-" + version);
        Path executable = home.resolve("bin/gradle");
        if (!Files.isRegularFile(executable)) {
            Files.createDirectories(cache);
            Path archive = cache.resolve(zipName);
            if (!Files.isRegularFile(archive)) {
                System.out.println("Downloading Gradle " + version + " from " + url);
                String proxyMessage = "Check your internet connection and proxy if the download fails.";
                try {
                    if (url.startsWith("file:")) {
                        Files.copy(Path.of(URI.create(url)), archive, StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        HttpClient client = HttpClient.newBuilder()
                            .followRedirects(HttpClient.Redirect.ALWAYS)
                            .connectTimeout(Duration.ofSeconds(20)).build();
                        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                            .timeout(Duration.ofMinutes(10)).GET().build();
                        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(archive));
                        if (response.statusCode() / 100 != 2) {
                            Files.deleteIfExists(archive);
                            throw new IOException("Download failed with HTTP " + response.statusCode() + ". " + proxyMessage);
                        }
                    }
                } catch (Exception e) {
                    Files.deleteIfExists(archive);
                    throw new IOException("Gradle could not be downloaded. " + proxyMessage, e);
                }
            }
            System.out.println("Extracting Gradle " + version + "...");
            try (ZipInputStream input = new ZipInputStream(Files.newInputStream(archive))) {
                ZipEntry entry;
                while ((entry = input.getNextEntry()) != null) {
                    Path target = cache.resolve(entry.getName()).normalize();
                    if (!target.startsWith(cache)) throw new IOException("Invalid ZIP path: " + entry.getName());
                    if (entry.isDirectory()) Files.createDirectories(target);
                    else {
                        Files.createDirectories(target.getParent());
                        Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                    input.closeEntry();
                }
            }
            if (!Files.exists(executable)) throw new IOException("Gradle executable missing in distribution: " + executable);
            executable.toFile().setExecutable(true);
        }
        List<String> command = new ArrayList<>();
        command.add(executable.toString());
        command.addAll(Arrays.asList(args));
        ProcessBuilder pb = new ProcessBuilder(command).directory(project.toFile()).inheritIO();
        int exit = pb.start().waitFor();
        System.exit(exit);
    }
}
