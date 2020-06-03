package com.example.english.service;

import com.example.english.dao.model.EnglishVietnameseEntity;
import com.example.english.dao.repository.EnglishVietnameseRepository;
import com.example.english.dto.EnglishVietnameseDTO;
import org.apache.poi.ss.usermodel.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Iterator;
import java.util.regex.Pattern;

@Service
public class EnglishVietnameseServiceImpl extends AbstractBaseService<EnglishVietnameseEntity, EnglishVietnameseDTO, EnglishVietnameseRepository> implements EnglishVietnameseService {
    private final static Logger logger = LoggerFactory.getLogger(EnglishVietnameseServiceImpl.class);

    private static ModelMapper modelMapper = null;

    @Autowired
    private EnglishVietnameseRepository repository;

    @Override
    protected EnglishVietnameseRepository getRepository() {
        return repository;
    }

    @Override
    protected ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT);
        }
        return modelMapper;
    }

    @Override
    protected Class<EnglishVietnameseEntity> getEntityClass() {
        return EnglishVietnameseEntity.class;
    }

    @Override
    protected Class<EnglishVietnameseDTO> getDtoClass() {
        return EnglishVietnameseDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public Integer readBooksFromExcelFile(MultipartFile file) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            StringBuilder name = new StringBuilder();
            int index = 0;
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                EnglishVietnameseEntity entity = new EnglishVietnameseEntity();

                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            break;
                        case 1:
                            entity.setNewword(nextCell.getStringCellValue());
                            break;
                        case 2:
                            entity.setCategory(nextCell.getStringCellValue());
                            break;
                        case 3:
                            entity.setSpelling(removeAccent(nextCell.getStringCellValue()));
                            break;
                        case 4:
                            entity.setMean(removeAccent(nextCell.getStringCellValue()));
                            break;
                    }

                    columnIndex++;
                    repository.save(entity);

                }
                workbook.close();
                workbook.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public EnglishVietnameseDTO findByNewword(String newword) {
        EnglishVietnameseEntity entity = repository.findEnglishVietnameseEntityByNewword(newword);
        return mapToDTO(entity);
    }


    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
        }

        return null;
    }

    //Ham loai bo dau tieng viet
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
