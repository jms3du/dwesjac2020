package com.edu.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class FileHandlerService extends AbstractServiceUtils{

	public Blob createBlob(MultipartFile file) {
		
		Blob blob = null;
		try {
			blob = new SerialBlob(file.getInputStream().readAllBytes());
			logger.info("Blob object created succesfully");
		} catch (SQLException | IOException e) {
			logger.debug("Blob object could not be created for File "+ file.getOriginalFilename() );
		}
		return blob;
	}


}
