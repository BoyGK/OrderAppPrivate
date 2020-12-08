package com.imuges.order.excel

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream

/**
 * @author BGQ
 * excel读取类
 */
class ExcelReader {

    /**
     * 默认解析的excel是OOXML格式档案
     * .xlsx
     */
    private var isXLSX = true

    fun setXlsx(isXlsx: Boolean) {
        isXLSX = isXlsx
    }

    fun parse(path: String): Excel {
        return parse(FileInputStream(path))
    }

    fun parse(file: File): Excel {
        return parse(FileInputStream(file))
    }

    fun parse(fd: FileDescriptor): Excel {
        return parse(FileInputStream(fd))
    }

    /**
     * 统一转换成数据流
     */
    private fun parse(fis: FileInputStream): Excel {
        return if (isXLSX) {
            parseXlsx(fis, XSSFWorkbook(fis))
        } else {
            parseXls(fis, HSSFWorkbook(fis))
        }
    }

    /**
     * 解析.xlsx后缀
     */
    private fun parseXlsx(fis: FileInputStream, workbook: Workbook): Excel {
        try {
            val excelBean = nullExcel()
            splitData(excelBean, workbook)
            return excelBean
        } finally {
            fis.close()
            workbook.close()
        }
    }

    /**
     * 解析.xls后缀
     */
    private fun parseXls(fis: FileInputStream, workbook: Workbook): Excel {
        try {
            val excelBean = nullExcel()
            splitData(excelBean, workbook)
            return excelBean
        } finally {
            fis.close()
            workbook.close()
        }
    }

    /**
     * 拆出单元格数据，合并至Excel对象
     * 期望的Excel数据格式
     * name / percent / unit / imagePath
     */
    private fun splitData(excelBean: Excel, workbook: Workbook) {
        for (sheetIndex in 0 until workbook.numberOfSheets) {
            val sheet = workbook.getSheetAt(sheetIndex)
            val sheetBean = Sheet(sheet.sheetName, mutableListOf())
            for (row in sheet) {
                if (row.rowNum == 0) {
                    //第一行是数据标题
                    continue
                }
                val rowBean = Row(mutableListOf())
                for (cellIndex in 0 until row.physicalNumberOfCells) {
                    val cellContent = row.getCell(cellIndex).stringCellValue
                    val cellBean = Cell(cellContent)
                    rowBean.cells.add(cellBean)
                }
                while (rowBean.cells.size < 4) {
                    rowBean.cells.add(Cell(""))
                }
                sheetBean.rows.add(rowBean)
            }
            excelBean.sheets.add(sheetBean)
        }
    }

    /**
     * 创建空Excel对象
     */
    private fun nullExcel(): Excel {
        return Excel("Excel", mutableListOf())
    }

}