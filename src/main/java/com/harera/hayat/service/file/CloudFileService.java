package com.harera.hayat.service.file;

import java.io.File;

public interface CloudFileService {

    String uploadFile(File file);

    void deleteFile(String url);
}
