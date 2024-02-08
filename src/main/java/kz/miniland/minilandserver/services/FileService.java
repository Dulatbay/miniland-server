package kz.miniland.minilandserver.services;

import kz.miniland.minilandserver.validators.ValidFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
public interface FileService {
    String save(MultipartFile file);
    Resource loadAsResource(String fileName);
    void init();
    void deleteAll();
}
