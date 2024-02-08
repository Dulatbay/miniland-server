package kz.miniland.minilandserver.controllers;

import kz.miniland.minilandserver.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFileAsResourceByFilename(@PathVariable("filename") String filename) {
        var resource = fileService.loadAsResource(filename);
        return ResponseEntity.ok(resource);
    }
}

