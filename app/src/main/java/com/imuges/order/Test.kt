package com.imuges.order

import com.imuges.order.excel.ExcelReader

class Test {

}

fun main(args: Array<String>) {
    //测试excel读取
    val er = ExcelReader()
    er.setXlsx(false)
    val excel = er.parse("C:\\Users\\DUOYI\\Desktop\\A.xls")
    println(excel.name)
    for (sheet in excel.sheets) {
        println(sheet.name)
        for (row in sheet.rows) {
            for (cell in row.cells) {
                print(cell.text + " ,")
            }
            println()
        }
    }
}