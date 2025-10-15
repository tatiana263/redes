package edu.unbosque.labred.qos_server;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/files")
public class Controller {

    private static final String VIDEO_DIR = "bin/Videos/";

    public Controller() {
        File dir = new File(VIDEO_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    // 📜 Listar videos disponibles
    @GetMapping("/list")
    public List<String> listFiles() {
        File folder = new File(VIDEO_DIR);
        File[] files = folder.listFiles();
        if (files == null) return Collections.emptyList();

        return Arrays.stream(files)
                .filter(File::isFile)
                .map(File::getName)
                .filter(name -> name.matches(".*\\.(mp4|mkv|avi)$"))
                .collect(Collectors.toList());
    }

    // 📥 Descargar archivo
    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) throws IOException {
        File file = new File(VIDEO_DIR + filename);
        if (!file.exists()) return ResponseEntity.notFound().build();

        byte[] data = Files.readAllBytes(file.toPath());
        String contentType = Files.probeContentType(file.toPath());
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(data);
    }
}
