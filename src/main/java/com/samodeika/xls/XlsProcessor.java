package com.samodeika.xls;

import com.samodeika.common.Dog;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface XlsProcessor {

    XSSFWorkbook getXls(List<Dog> dogs);

}
