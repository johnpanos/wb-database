package com.team3256.database.controller.hr;

import com.team3256.database.error.InternalServerException;
import com.team3256.database.model.hr.StudentExcelBinding;
import com.team3256.database.model.hr.StudentExcelDTO;
import com.team3256.database.model.hr.UserGender;
import com.team3256.database.service.StudentService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.team3256.database.model.hr.StudentExcelBinding.*;

@RestController
@RequestMapping("/api/hr/student/excel")
public class StudentExcelUploadController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload")
    public List<StudentExcelDTO> generateStudentList(@RequestParam("file") MultipartFile file) {
        // Convert file upload input stream into an excel workbook
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            // Fail and send error 500 code to let
            // the client know the upload failed
            throw new InternalServerException();
        }

        // Access first sheet
        Sheet sheet = workbook.getSheetAt(0);

        ArrayList<StudentExcelDTO> list = new ArrayList<>();

        // Iterate over all rows in sheet
        for (int i = 1; i < sheet.getLastRowNum(); i++) {

            Row currentRow = sheet.getRow(i);

            // Use DTO instead of actual user object
            // because this is not being put in the DB
            StudentExcelDTO studentDto = new StudentExcelDTO();

            Cell checkCell = currentRow.getCell(1);

            // Skip whole row if empty
            if (checkCell.getCellTypeEnum() == CellType.BLANK) {
                continue;
            }

            // Iterate over cells in row to get student
            // information from excel worksheet
            for (int j = 1; j <= 29; j++) {
                Cell cell = currentRow.getCell(j);

                switch (j) {
                    case StudentFirstName:
                        studentDto.StudentFirstName = cell.getStringCellValue();
                        break;
                    case StudentLastName:
                        studentDto.StudentLastName = cell.getStringCellValue();
                        break;
                    case Accepted:
                        studentDto.Accepted = cell.getStringCellValue().equals("YES");
                        break;
                    case StudentPhone:
                        studentDto.StudentPhone = cell.getStringCellValue();
                        break;
                    case StudentCellPhone:
                        studentDto.StudentCellPhone = cell.getStringCellValue();
                        break;
                    case StudentEmail:
                        studentDto.StudentEmail = cell.getStringCellValue();
                        break;
                    case StudentGrade:
                        studentDto.StudentGrade = (int) cell.getNumericCellValue();
                        break;
                    case StudentGender:
                        studentDto.StudentGender = cell.getStringCellValue();
                        break;
                    case StudentNew:
                        studentDto.StudentNew = cell.getStringCellValue().equals("YES");
                        break;
                    case StudentPowerSchool:
                        studentDto.StudentPowerSchool = (int) cell.getNumericCellValue();
                        break;
                    case FatherFirstName:
                        studentDto.FatherFirstName = cell.getStringCellValue();
                        break;
                    case FatherLastName:
                        studentDto.FatherLastName = cell.getStringCellValue();
                        break;
                    case FatherPhone:
                        studentDto.FatherPhone = cell.getStringCellValue();
                        break;
                    case FatherCellPhone:
                        studentDto.FatherCellPhone = cell.getStringCellValue();
                        break;
                    case FatherEmail:
                        studentDto.FatherEmail = cell.getStringCellValue();
                        break;
                    case FatherAddress:
                        studentDto.FatherAddress = cell.getStringCellValue();
                        break;
                    case FatherCity:
                        studentDto.FatherCity = cell.getStringCellValue();
                        break;
                    case FatherState:
                        studentDto.FatherState = cell.getStringCellValue();
                        break;
                    case FatherZip:
                        studentDto.FatherZip = (int) cell.getNumericCellValue();
                        break;
                    case MotherFirstName:
                        studentDto.MotherFirstName = cell.getStringCellValue();
                        break;
                    case MotherLastName:
                        studentDto.MotherLastName = cell.getStringCellValue();
                        break;
                    case MotherPhone:
                        studentDto.MotherPhone = cell.getStringCellValue();
                        break;
                    case MotherCellPhone:
                        studentDto.MotherCellPhone = cell.getStringCellValue();
                        break;
                    case MotherEmail:
                        studentDto.MotherEmail = cell.getStringCellValue();
                        break;
                    case MotherAddress:
                        studentDto.MotherAddress = cell.getStringCellValue();
                        break;
                    case MotherCity:
                        studentDto.MotherCity = cell.getStringCellValue();
                        break;
                    case MotherState:
                        studentDto.MotherState = cell.getStringCellValue();
                        break;
                    case MotherZip:
                        studentDto.MotherZip = (int) cell.getNumericCellValue();
                        break;
                    case ShirtSize:
                        studentDto.StudentShirtSize = cell.getStringCellValue();
                        break;
                }
            }

            list.add(studentDto);
            studentService.create(
                studentDto.StudentFirstName,
                "N/A",
                studentDto.StudentLastName,
                studentDto.StudentCellPhone,
                studentDto.StudentEmail,
                studentDto.StudentLastName.toLowerCase() + studentDto.StudentLastName.length(),
                new Date(),
                studentDto.StudentGender.equals("MALE") ? UserGender.MALE : UserGender.FEMALE,
                studentDto.StudentEmail,
                studentDto.StudentGrade,
                studentDto.StudentPowerSchool,
                studentDto.StudentShirtSize,
                studentDto.StudentShirtSize,
                false,
                false,
                false
            );

        }

        return list;
    }
}
